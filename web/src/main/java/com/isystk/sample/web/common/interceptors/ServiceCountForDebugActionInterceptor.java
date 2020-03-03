package com.isystk.sample.web.common.interceptors;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.struts.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * アクション内での、実行時間とサービス呼び出し回数をカウントするためのインターセプタ（アクションに対して適用する必要がある）
 * 
 * @author iseyoshitaka
 */
public class ServiceCountForDebugActionInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 3943321039873243655L;
	private static final Logger logger = LoggerFactory.getLogger(ServiceCountForDebugActionInterceptor.class);

	public static final String COUNT = ServiceCountForDebugActionInterceptor.class.getSimpleName() + "_COUNT";
	public static final String TIME = ServiceCountForDebugActionInterceptor.class.getSimpleName() + "_TIME";

	public Object invoke(MethodInvocation invocation) throws Throwable {

		Integer isFirst = ServiceCountForDebugServiceInterceptor.countThreadLocal.get();
		if (isFirst == null) { // 初めてのActionメソッド呼び出しかどうか
			long starttime = System.currentTimeMillis();

			try {
				ServiceCountForDebugServiceInterceptor.countThreadLocal.set(0); // ゼロクリア

				Object proceed = invocation.proceed();

				// リクエストに呼び出し回数をセット
				Integer count = ServiceCountForDebugServiceInterceptor.countThreadLocal.get();
				RequestUtil.getRequest().setAttribute(COUNT, count);

				// 実行時間をセット
				RequestUtil.getRequest().setAttribute(TIME, System.currentTimeMillis() - starttime);

				return proceed;

			} finally {
				ServiceCountForDebugServiceInterceptor.countThreadLocal.set(null); // 初期化しておく
			}

		} else {
			Object proceed = invocation.proceed();
			return proceed;
		}

	}
}
