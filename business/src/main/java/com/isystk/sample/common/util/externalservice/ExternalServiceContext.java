package com.isystk.sample.common.util.externalservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isystk.sample.common.util.externalservice.ExternalServiceManager.Execute;
import com.isystk.sample.common.util.externalservice.ExternalServiceManager.Predicate;

/**
 * 外部サービスの状態とアクセスを管理するオブジェクト
 * 
 * @author iseyoshitaka
 */
public class ExternalServiceContext {

    private static final Logger logger = LoggerFactory.getLogger(ExternalServiceContext.class);

    public String serviceName;
    public int maxRetryCount;
    public int heatbeatCycleTime;
    public boolean isEnable;
    public Predicate heatbeatPredicate;
    public Thread heartbeatCheckThread;

    /**
     * コンストラクタ
     * 
     * @param serviceName　サービス名（キー名）
     * @param maxRetryCount　エラー発生時にリトライする回数
     * @param heatbeatCycleTime サービスが切り離されてしまった際に、死活チェックを行うサイクル時間（ミリ秒）
     * @param heatbeatPredicate サービスの死活チェックを行うための論理値を返す関数オブジェクト
     */
    public ExternalServiceContext(String serviceName, int maxRetryCount, int heatbeatCycleTime, Predicate heatbeatPredicate) {
	this.serviceName = serviceName;
	this.maxRetryCount = maxRetryCount;
	this.heatbeatCycleTime = heatbeatCycleTime;
	this.heatbeatPredicate = heatbeatPredicate;

	this.initialize();
    }

    /**
     * 外部サービスにアクセスします。
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
    public <T> T call(Execute<T> execute) {

	if (isEnable == false) {
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
	} while (success == false && retryCount <= maxRetryCount);

	if (success) {
	    return result;
	} else {
	    transiteToDisable();

	    if (fail instanceof RuntimeException) {
		throw (RuntimeException) fail;
	    } else {
		throw new RuntimeException(fail);
	    }
	}
    }

    private void initialize() {
	boolean enable = false;
	try {
	    enable = heatbeatPredicate.apply();
	} catch (Throwable t) {
	    StackTraceElement[] ste = t.getStackTrace();
	    logger.warn("ExternalService Call failed:"
		+ t.getMessage()
		+ " class="
		+ ste[0].getClassName()
		+ " method="
		+ ste[0].getMethodName()
		+ " file="
		+ ste[0].getFileName()
		+ " line="
		+ ste[0].getLineNumber());
	}

	isEnable = enable == false; // フラグが逆でないとtransiteToＸＸＸ()が動作しないので、一旦逆のbool値を設定する
	if (enable) {
	    transiteToEnable();
	} else {
	    transiteToDisable();
	}
    }

    private void transiteToEnable() {
	synchronized (this) {
	    if (isEnable == false) {
		logger.info("ExternalService is Eabled NOW. serviceName='" + serviceName + "'");
		isEnable = true;
	    }
	}
    }

    private void transiteToDisable() {
	synchronized (this) {
	    if (isEnable) {
		logger.error("ExternalService is Disabled NOW. serviceName='" + serviceName + "'");

		isEnable = false;
		heartbeatCheckThread = new Thread() {
		    @Override
		    public void run() {
			while (isEnable == false) {
			    try {
				Thread.sleep(heatbeatCycleTime);
			    } catch (InterruptedException e) {
				// void
			    }
			    checkHeartbeat();
			}
			heartbeatCheckThread = null;
		    }
		};
		heartbeatCheckThread.start();
	    }
	}
    }

    private boolean checkHeartbeat() {
	boolean enable = false;
	try {
	    enable = heatbeatPredicate.apply();
	} catch (Throwable t) {
	    StackTraceElement[] ste = t.getStackTrace();
	    logger.warn(
		"ExternalService HeartbeatCheck failed:"
		    + t.getMessage()
		    + " class="
		    + ste[0].getClassName()
		    + " method="
		    + ste[0].getMethodName()
		    + " file="
		    + ste[0].getFileName()
		    + " line="
		    + ste[0].getLineNumber());
	}
	if (enable) {
	    transiteToEnable();
	} else {
	    logger.warn("ExternalService is still disabled. serviceName='" + serviceName + "'");
	}

	return enable;
    }

}