/*
 * SlowMehtodLoggingInterceptor.java
 * 2011/06/20 iseyoshitaka
 */
package com.isystk.sample.web.common.interceptors;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.util.ArrayUtil;
import org.seasar.struts.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isystk.sample.web.common.util.CookieUtil;

/**
 * @author yamashitayu
 */
public class ConversionSlowMehtodLoggingInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 6719966787884868625L;

    private static final Logger logger = LoggerFactory.getLogger(ConversionSlowMehtodLoggingInterceptor.class);

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

	String targetClassName = getTargetClass(invocation).getName();
	FormDiv formDiv = FormDiv.get(targetClassName);
	if (null == formDiv) {
	    // ありえない
	    return invocation.proceed();
	}

	final String trackingId = CookieUtil.getValue("TRACKING_ID");
	final String weddingId = (String) RequestUtil.getRequest().getParameter("weddingId");
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
	logger.debug("{} ■BEGIN：{}", formDiv.getTitle(trackingId, weddingId), buf);
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
	logger.debug("{} ■END：{}", formDiv.getTitle(trackingId, weddingId), buf);
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

    private enum FormDiv {

	/** 式場-PC-資料請求 */
	PC_DOCUMENT("DocumentAction", ".userpc", "PC式場資料請求"),
	/** 式場-PC-見学予約 */
	PC_VISIT("VisitAction", ".userpc", "PC式場見学予約"),
	/** 式場-PC-お問合せ */
	PC_COMMON("CommonAction", ".userpc", "PC式場お問合せ"),
	/** 式場-PC-フェア予約 */
	PC_INQUIRY("FairAction", ".userpc", "PC式場フェア予約"),
	/** 式場-SP-資料請求 */
	SP_DOCUMENT("DocumentAction", ".usersp", "SP式場資料請求"),
	/** 式場-SP-見学予約 */
	SP_VISIT("VisitAction", ".usersp", "SP式場見学予約"),
	/** 式場-SP-お問合せ */
	SP_COMMON("CommonAction", ".usersp", "SP式場お問合せ"),
	/** 式場-SP-フェア予約 */
	SP_INQUIRY("FairAction", ".usersp", "SP式場フェア予約");

	private final String clazzName;
	private final String pkgPrefix;
	private final String displayName;

	FormDiv(String clazzName, String pkgPrefix, String displayName) {
	    this.clazzName = clazzName;
	    this.pkgPrefix = pkgPrefix;
	    this.displayName = displayName;

	}

	public String getTitle(String trackingId, String weddingId) {
	    StringBuilder builder = new StringBuilder();
	    builder.append("【■画面名：");
	    builder.append(this.displayName);
	    builder.append(" ■TRACKING_ID：");
	    builder.append(trackingId);
	    builder.append(" ■式場ID：");
	    builder.append(weddingId);
	    builder.append("】");
	    return builder.toString();
	}

	public static FormDiv get(String targetClassName) {
	    if (null == targetClassName) {
		return null;
	    }
	    for (FormDiv value : values()) {
		if (targetClassName.contains(value.clazzName) && targetClassName.contains(value.pkgPrefix)) {
		    return value;
		}
	    }
	    return null;
	}
    }
}
