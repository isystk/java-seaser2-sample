/**
 * Copyright(c) isystk.com</br>
 */
package com.isystk.sample.common.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ThreadLocalに関するユーティリティ。<br>
 * ソフトウェアのレイヤ構造を無視しかねないので扱いには注意すること。
 * 
 * @author iseyoshitaka, nkawamata
 */
public final class ThreadLocalUtils {

    /**
     * the logger.
     */
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(ThreadLocalUtils.class);

    /**
     * ユーティリティのためコンストラクタを封殺。
     */
    private ThreadLocalUtils() {
    }

    /**
     * ThreadLocalの実装。
     */
    private static final ThreadLocal<Map<String, Object>> MY_THREAD_LOCAL = new ThreadLocal<Map<String, Object>>() {
	/**
	 * initialValueをオーバーライドします.
	 * 
	 * @see java.lang.ThreadLocal#initialValue()
	 */
	@Override
	protected Map<String, Object> initialValue() {
	    if (logger.isDebugEnabled()) {
		logger.debug("ThreadLocalを初期化します");
	    }
	    return new HashMap<String, Object>();
	}
    };

    /**
     * ThreadLocalから値を取得する。
     * 
     * @param <C> クラス
     * @param key キー
     * @return 値
     */
    @SuppressWarnings("unchecked")
    public static <C> C getThreadLocalValue(String key) {
	return (C) MY_THREAD_LOCAL.get().get(key);
    }

    /**
     * ThreadLocalに値を設定する。
     * 
     * @param key キー
     * @param value 値
     * @return 既に設定されている値。なければnull。
     */
    public static Object setThreadLocalValue(String key, Object value) {
	return MY_THREAD_LOCAL.get().put(key, value);
    }

    /**
     * ThreadLocalの値をすべてクリアする。
     */
    public static void clearThreadLocalValues() {
	MY_THREAD_LOCAL.get().clear();
    }
}
