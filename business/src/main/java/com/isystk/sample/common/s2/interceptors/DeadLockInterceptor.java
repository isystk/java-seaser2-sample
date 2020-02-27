package com.isystk.sample.common.s2.interceptors;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.exception.SQLRuntimeException;
import org.seasar.framework.exception.SSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isystk.sample.common.exception.SQLDeadLockException;

/**
 * デッドロックのSQLRuntimeExceptionを、専用の例外にラップするためのインターセプター。サービス呼び出し時に適用されることを想定している。
 * 
 * @author iseyoshitaka
 */
public class DeadLockInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = -1294862873149224434L;

    private static final Logger logger = LoggerFactory.getLogger(DeadLockInterceptor.class);

    public Object invoke(MethodInvocation invocation) throws Throwable {
	try {
	    return invocation.proceed();
	} catch (SQLRuntimeException e) {
	    if (e.getCause() instanceof SSQLException) {
		int errorCode = ((SSQLException) e.getCause()).getErrorCode();
		if (errorCode == 1213) { //1213　MySQL デッドロックコード
		    throw new SQLDeadLockException(e.getLocalizedMessage(), e);
		}
	    }

	    throw e;
	}
    }
}