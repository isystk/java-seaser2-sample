/*
 * EmailException.java
 * 2011/03/16 iseyoshitaka
 */
package com.isystk.sample.common.exception;

import java.text.MessageFormat;

import com.isystk.sample.common.config.Message;

/**
 * メール送信実行時にメールアドレスが不正な場合に発生する例外
 * 
 * @author moriyataeko
 * 
 */
public class IllegalMailAddressException extends SystemException {

    private static final long serialVersionUID = -8743668188954879255L;

    /**
     * コンストラクタ。
     * 
     * @param message メッセージ
     * @param cause 元の例外
     */
    public IllegalMailAddressException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * プロパティーからメッセージを作成するコンストラクタ
     * 
     * @param key キー
     * @param values 値の配列
     */
    public IllegalMailAddressException(String key, String... values) {
	super(MessageFormat.format(Message.getString(key), values));
    }

}
