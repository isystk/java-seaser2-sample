package com.isystk.sample.common.util.externalservice;

import java.util.HashMap;
import java.util.Map;

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
    private static ExternalServiceManager _instance = new ExternalServiceManager();

    private Map<String, ExternalServiceContext> contextMap = new HashMap<String, ExternalServiceContext>();

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
     * @return 外部サービスコンテクスト
     */
    public ExternalServiceContext addExternalService(String serviceName, int maxRetryCount, int heatbeatCycleTime, Predicate heatbeatPredicate) {
	if (contextMap.get(serviceName) != null) {
	    throw new SystemException("既に登録済みのサービスです。serviceName='" + serviceName + "'");
	}

	final ExternalServiceContext context = new ExternalServiceContext(serviceName, maxRetryCount, heatbeatCycleTime, heatbeatPredicate);

	contextMap.put(serviceName, context);

	return context;
    }

    /**
     * 当該サービスが有効かどうか
     * 
     * @param serviceName サービス名（キー）
     * @return　有効な場合true。無効な場合false
     */
    public boolean isEnable(String serviceName) {
	return contextMap.get(serviceName).isEnable;
    }

    /**
     * 当該サービスのコンテクストを取得する
     * 
     * @param serviceName サービス名（キー）
     * @return　サービスのコンテクスト
     */
    public ExternalServiceContext getContext(String serviceName) {
	return contextMap.get(serviceName);
    }

    public static interface Predicate {
	public boolean apply();
    }

    public static interface Execute<T> {
	public T proceed();
    }


}
