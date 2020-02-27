package com.isystk.sample.common.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isystk.sample.common.exception.SystemException;

/**
 * 外部サービスマネージャ。
 * <p>
 * 外部サービスのような信頼性があまり高くないサービスを使用する際に、自身のシステムに負担をかけずに、必要に応じてサービス切り離しや、
 * 死活監視を行いながらの復活を実現するためのマネージャ。 １．外部サービスをあらかじめ登録しておく。<br/>
 * ２．サービスで例外が発生した際に、自動で繰り返しあらかじめ決められた数の再リクエストを行なう。サービスがそれでも繰り返し例外を発生する場合には、利用不可能
 * な サ ー ビ ス として 切 り 離 す。<br/>
 * ３．切り離されたサービスは、あらかじめ決められたサイクル時間ごとに死活監視が行われ、復活した際には自動的に利用可能となる。
 * 
 * @author iseyoshitaka
 */
public class ExternalServiceManager {
    private static final Logger logger = LoggerFactory.getLogger(ExternalServiceManager.class);

    private static ExternalServiceManager _instance = new ExternalServiceManager();

    private Map<String, ExternalServiceConfig> configMap = new HashMap<String, ExternalServiceConfig>();

    /**
     * 外部サービスマネージャを取得する
     * 
     * @return 外部サービスマネージャ
     */
    public static ExternalServiceManager getInstance() {
	return _instance;
    }

    /**
     * 外部サービスを登録します。
     * 
     * @param serviceName　サービス名（キー名）
     * @param maxRetryCount　エラー発生時にリトライする回数
     * @param heatbeatCycleTime サービスが切り離されてしまった際に、死活チェックを行うサイクル時間（ミリ秒）
     * @param heatbeatPredicate サービスの死活チェックを行うための論理値を返す関数オブジェクト
     * 
     * @return 外部サービス設定
     */
    public ExternalServiceConfig addExternalService(String serviceName, int maxRetryCount, int heatbeatCycleTime, Predicate heatbeatPredicate) {
	if (configMap.get(serviceName) != null) {
	    throw new SystemException("既に登録済みのサービスです。serviceName='" + serviceName + "'");
	}

	final ExternalServiceConfig config = new ExternalServiceConfig();

	config.serviceName = serviceName;
	config.maxRetryCount = maxRetryCount;
	config.heatbeatCycleTime = heatbeatCycleTime;
	config.heatbeatPredicate = heatbeatPredicate;

	configMap.put(serviceName, config);

	boolean enable = false;
	try {
	    enable = config.heatbeatPredicate.apply();
	} catch (Throwable t) {
	    // void
	}

	config.isEnable = enable == false; // フラグが逆でないとtransiteToＸＸＸ()が動作しないので、一旦逆のbool値を設定する
	if (enable) {
	    transiteToEnable(config);
	} else {
	    transiteToDisable(config);
	}

	return config;
    }

    /**
     * 外部サービスマネージャを使用して、サービスにアクセスします。
     * <p>
     * 実行したいコードをExecuteオブジェクトを通して渡します。 この中で例外を検出すると、あらかじめ設定されたリトライ回数を試し、
     * それでもだめな場合には、サービスを切り離して、死活チェックを行って復活を待つモードになります。
     * 
     * サービスにアクセスする前に、isEnable()を使用して利用可能かチェックするようにしてください。
     * なお、サービスが死んでいる場合には、nullを返します。
     * 
     * @param serviceName
     * @param execute
     * @return
     * @throws Throwable
     * 
     */
    public <T> T callExternalService(String serviceName, Execute<T> execute) throws Throwable {
	ExternalServiceConfig config = configMap.get(serviceName);

	if (config == null) {
	    throw new SystemException("登録されていないサービスの呼び出しを検出しました。servicename='" + serviceName + "'");
	}

	if (config.isEnable == false) {
	    return null;
	}

	T result = null;
	Throwable fail = null;

	int retryCount = 1;
	boolean success = false;
	do {
	    try {
		result = execute.proceed();
		success = true;
	    } catch (Throwable t) {
		logger.warn("ExternalService Exception. serviceName='" + serviceName + "'");
		fail = t;

		++retryCount;
	    }
	} while (success == false && retryCount <= config.maxRetryCount);

	if (success) {
	    return result;
	} else {
	    transiteToDisable(config);
	    throw fail;
	}
    }

    /**
     * 当該サービスが有効かどうか
     * 
     * @param serviceName サービス名（キー）
     * @return　有効な場合true。無効な場合false
     */
    public boolean isEnable(String serviceName) {
	return configMap.get(serviceName).isEnable;
    }

    private boolean checkHeartbeat(ExternalServiceConfig config) {
	boolean enable = false;
	try {
	    enable = config.heatbeatPredicate.apply();
	} catch (Throwable t) {
	    // void
	}
	if (enable) {
	    transiteToEnable(config);
	} else {
	    logger.warn("ExternalService is still disabled. serviceName='" + config.serviceName + "'");
	}

	return enable;
    }

    private void transiteToEnable(ExternalServiceConfig config) {
	synchronized (config) {
	    if (config.isEnable == false) {
		logger.info("ExternalService is Eabled NOW. serviceName='" + config.serviceName + "'");
		config.isEnable = true;
	    }
	}
    }

    private void transiteToDisable(final ExternalServiceConfig config) {
	synchronized (config) {
	    if (config.isEnable) {
		logger.error("ExternalService is Disabled NOW. serviceName='" + config.serviceName + "'");

		config.isEnable = false;
		config.heartbeatCheckThread = new Thread() {
		    @Override
		    public void run() {
			while (config.isEnable == false) {
			    try {
				Thread.sleep(config.heatbeatCycleTime);
			    } catch (InterruptedException e) {
				// void
			    }
			    checkHeartbeat(config);
			}
			config.heartbeatCheckThread = null;
		    }
		};
		config.heartbeatCheckThread.start();
	    }
	}
    }

    public static interface Predicate {
	public boolean apply();
    }

    public static interface Execute<T> {
	public T proceed();
    }

    public static class ExternalServiceConfig {
	public String serviceName;
	int maxRetryCount;
	int heatbeatCycleTime;
	boolean isEnable;
	Predicate heatbeatPredicate;
	Thread heartbeatCheckThread;
    }

}
