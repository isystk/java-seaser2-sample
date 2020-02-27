/*
 * DataUpdateFailureException.java 2011/04/18 iseyoshitaka
 */
package com.isystk.sample.common.exception;

import java.text.MessageFormat;

import com.isystk.sample.common.config.Message;

/**
 * DBへの挿入や更新で失敗した場合に使用します。 500エラー例外をthrowします。
 * 
 * @author iseyoshitaka
 * 
 */
public class DataUpdateFailureException extends StatusException {

    private static final long serialVersionUID = 1399323669480367528L;

    /**
     * 
     * コンストラクタ。
     */
    public DataUpdateFailureException() {
	assertStackTrace();
    }

    /**
     * @param message メッセージ
     */
    public DataUpdateFailureException(String message) {
	super(message);
	assertStackTrace();
    }

    /**
     * @param message メッセージ
     * @param cause 原因となった例外
     */
    public DataUpdateFailureException(String message, Throwable cause) {
	super(message, cause);
	assertStackTrace();
    }

    /**
     * プロパティーからメッセージを作成するコンストラクタ
     * 
     * @param key キー
     * @param values 値の配列
     */
    public DataUpdateFailureException(String key, String... values) {
	super(MessageFormat.format(Message.getString(key), values));
	assertStackTrace();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStatusCode() {
	return 500;
    }
}
