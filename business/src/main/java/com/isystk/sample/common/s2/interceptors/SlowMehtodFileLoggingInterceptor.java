/*
 * SlowMehtodLoggingInterceptor.java
 * 2011/06/20 iseyoshitaka
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
public class SlowMehtodFileLoggingInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 3956233531447675704L;

    private static final Logger logger = LoggerFactory.getLogger(SlowMehtodFileLoggingInterceptor.class);

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

	// デバッグの場合のみ処理
	if (!logger.isDebugEnabled()) {
	    return invocation.proceed();
	}

	// 出力文字の準備
	final StringBuilder buf = new StringBuilder(100);
	String className = getTargetClass(invocation).getName();
	String methodName = invocation.getMethod().getName();

	// クラス種類
	if (className.endsWith("Action")) {
	    buf.append("Action");
	} else if (className.endsWith("Logic")) {
	    buf.append("Logic");
	} else if (className.endsWith("Service")) {
	    buf.append("Service");
	} else {

	    buf.append("Other");
	}
	buf.append("\t");

	// クラス+メソッド名
	buf.append(className).append("#").append(methodName).append("\t");

	// クラス+メソッド名+引数
	buf.append(className).append("#").append(methodName).append("(");
	final Object[] args = invocation.getArguments();
	if (args != null && args.length > 0) {
	    for (int i = 0; i < args.length; ++i) {
		appendObject(buf, args[i]).append(", ");
	    }
	    buf.setLength(buf.length() - 2);
	}
	buf.append(")");

	// 処理実行
	Object ret = null;
	Throwable cause = null;
	long start = System.currentTimeMillis();
	long dif = 0;
	try {
	    ret = invocation.proceed();
	    dif = System.currentTimeMillis() - start;
	    buf.append(" : ");
	    appendObject(buf, ret);
	} catch (final Throwable t) {
	    buf.append(" Throwable:").append(t);
	    cause = t;
	}

	// 出力
	if (dif > 10) {
	    logger.info("{}\t{}", dif, buf);
	}

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
