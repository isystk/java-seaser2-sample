package com.isystk.sample.web.common.interceptors;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * アクション内での、実行時間とサービス呼び出し回数をカウントするためのインターセプタ（サービスに対して適用する必要がある）
 * 
 * @author iseyoshitaka
 */
public class ServiceCountForDebugServiceInterceptor extends AbstractInterceptor {

	public static ThreadLocal<Integer> countThreadLocal = new ThreadLocal<Integer>();

	private static final long serialVersionUID = 3943321039873243655L;
	private static final Logger logger = LoggerFactory.getLogger(ServiceCountForDebugServiceInterceptor.class);

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Integer count = ServiceCountForDebugServiceInterceptor.countThreadLocal.get();
		if (count != null) {
			ServiceCountForDebugServiceInterceptor.countThreadLocal.set(count + 1);
		}

		Object proceed = invocation.proceed();
		return proceed;
	}
}