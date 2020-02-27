/*
 * SlowMehtodLoggingInterceptor.java
 * 2011/06/20 hnagato
 */
package com.isystk.sample.common.s2.interceptors;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.util.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author iseyoshitaka
 */
public class SlowMehtodLoggingInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 3956233531447675704L;

    private static final Logger logger = LoggerFactory.getLogger(SlowMehtodLoggingInterceptor.class);

    /**
     * コレクションの最大サイズです。
     */
    protected int maxLengthOfCollection = 10;

    /**
     * 引数や戻り値が{@link Collection}や配列だった場合Max何件出力するかどうか設定します。
     * 
     * @param maxLengthOfCollection -
     */
    public void setMaxLengthOfCollection(final int maxLengthOfCollection) {
	this.maxLengthOfCollection = maxLengthOfCollection;
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
	if (!logger.isDebugEnabled()) {
	    return invocation.proceed();
	}

	final StringBuilder buf = new StringBuilder(100);
	buf.append(getTargetClass(invocation).getName());
	buf.append("#").append(invocation.getMethod().getName()).append("(");
	final Object[] args = invocation.getArguments();
	if (args != null && args.length > 0) {
	    for (int i = 0; i < args.length; ++i) {
		appendObject(buf, args[i]).append(", ");
	    }
	    buf.setLength(buf.length() - 2);
	}
	buf.append(")");
	Object ret = null;
	Throwable cause = null;
	logger.debug("BEGIN {}", buf);
	long start = System.nanoTime();
	try {
	    ret = invocation.proceed();
	    buf.append(" : ");
	    appendObject(buf, ret);
	} catch (final Throwable t) {
	    buf.append(" Throwable:").append(t);
	    cause = t;
	}
	double d = (System.nanoTime() - start) / 1000d / 1000d;

	if (d > 1000) {
	    logger.warn("■■■■■ {}[ms] - {}", d, buf);
	}
	logger.debug("END {}", buf);
	if (cause == null) {
	    return ret;
	}
	throw cause;
    }

    /**
     * オブジェクトのトレース情報を追加します。
     * 
     * @param buf バッファ
     * @param arg オブジェクト
     * @return 結果のバッファ
     */
    @SuppressWarnings("rawtypes")
    protected StringBuilder appendObject(final StringBuilder buf, final Object arg) {
	if (arg == null) {
	    buf.append("null");
	} else if (arg.getClass().isArray()) {
	    if (arg.getClass().getComponentType().isPrimitive()) {
		appendList(buf, Arrays.asList(ArrayUtil.toObjectArray(arg)));
	    } else {
		appendList(buf, Arrays.asList((Object[]) arg));
	    }
	} else if (arg instanceof Collection) {
	    appendList(buf, (Collection) arg);
	} else {
	    buf.append(arg);
	}
	return buf;
    }

    /**
     * コレクションのトレース情報を追加します。
     * 
     * @param buf バッファ
     * @param collection コレクション
     * @return 結果のバッファ
     */
    protected StringBuilder appendList(final StringBuilder buf, @SuppressWarnings("rawtypes") final Collection collection) {
	buf.append("[");
	int count = 0;
	for (@SuppressWarnings("rawtypes")
	final Iterator it = collection.iterator(); it.hasNext() && count < maxLengthOfCollection; ++count) {
	    appendObject(buf, it.next()).append(", ");
	}
	if (count > 0) {
	    buf.setLength(buf.length() - 2);
	}
	buf.append("]");
	return buf;
    }
}
