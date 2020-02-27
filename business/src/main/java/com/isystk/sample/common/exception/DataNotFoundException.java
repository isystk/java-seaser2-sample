/*
 * DataNotFoundException.java
 * 2011/02/07 hnagato
 */
package com.isystk.sample.common.exception;

import java.text.MessageFormat;

import com.isystk.sample.common.config.Message;

/**
 * 指定されたデータが存在しないことを示すビジネスロジック上の例外を表します.
 * 
 * @author iseyoshitaka
 */
public class DataNotFoundException extends ApplicationException {

    private static final long serialVersionUID = -7760694858063102841L;

    /**
     * @param message この例外に関する詳細な情報.
     * @param cause 原因となった例外
     */
    public DataNotFoundException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * @param message この例外に関する詳細な情報.
     */
    public DataNotFoundException(String message) {
	super(message);
    }

    /**
     * プロパティーからメッセージを作成するコンストラクタ
     * 
     * @param key キー
     * @param values 値の配列
     */
    public DataNotFoundException(String key, String... values) {
	super(MessageFormat.format(Message.getString(key), values));
    }
}
