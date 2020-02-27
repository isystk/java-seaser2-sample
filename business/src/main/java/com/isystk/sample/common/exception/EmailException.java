/*
 * EmailException.java
 * 2011/03/16 iseyoshitaka
 */
package com.isystk.sample.common.exception;

import java.text.MessageFormat;

import com.isystk.sample.common.config.Message;

/**
 * @author iseyoshitaka
 * 
 */
public class EmailException extends SystemException {

    private static final long serialVersionUID = -8743668188954879255L;

    /**
     * コンストラクタ。
     * 
     * @param message メッセージ
     * @param cause 元の例外
     */
    public EmailException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * プロパティーからメッセージを作成するコンストラクタ
     * 
     * @param key キー
     * @param values 値の配列
     */
    public EmailException(String key, String... values) {
	super(MessageFormat.format(Message.getString(key), values));
    }

}
