/*
 * NotFoundException.java 2011/02/08 iseyoshitaka
 */
package com.isystk.sample.common.exception;

import java.text.MessageFormat;

import com.isystk.sample.common.config.Message;

/**
 * 404エラー例外をthrowします。
 * 
 * @author iseyoshitaka
 * 
 */
public class NotFoundException extends StatusException {

    private static final long serialVersionUID = 1399323669480367528L;

    /**
     * 
     * コンストラクタ。
     */
    public NotFoundException() {
	assertStackTrace();
    }

    /**
     * @param message メッセージ
     */
    public NotFoundException(String message) {
	super(message);
	assertStackTrace();
    }

    /**
     * @param message メッセージ
     * @param cause 原因となった例外
     */
    public NotFoundException(String message, Throwable cause) {
	super(message, cause);
	assertStackTrace();
    }

    /**
     * プロパティーからメッセージを作成するコンストラクタ
     * 
     * @param key キー
     * @param values 値の配列
     */
    public NotFoundException(String key, String... values) {
	super(MessageFormat.format(Message.getString(key), values));
	assertStackTrace();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStatusCode() {
	return 404;
    }
}
