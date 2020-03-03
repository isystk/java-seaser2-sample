/*
 * TokenCheckException.java
 * 2012/04/23 iseyoshitaka
 */
package com.isystk.sample.common.exception;

import java.text.MessageFormat;

import com.isystk.sample.common.config.Message;

/**
 * トークンチェックの失敗時に発生する例外
 * 
 * @author iseyoshitaka
 * 
 */
public class TokenCheckException extends StatusException {

	private static final long serialVersionUID = 2046191985881842811L;

	/**
	 * コンストラクタ。
	 * 
	 * @param message
	 *            メッセージ
	 * @param cause
	 *            元の例外
	 */
	public TokenCheckException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * プロパティーからメッセージを作成するコンストラクタ
	 * 
	 * @param key
	 *            キー
	 * @param values
	 *            値の配列
	 */
	public TokenCheckException(String key) {
		super(Message.getString(key));
	}

	/**
	 * プロパティーからメッセージを作成するコンストラクタ
	 * 
	 * @param key
	 *            キー
	 * @param values
	 *            値の配列
	 */
	public TokenCheckException(String key, String... values) {
		super(MessageFormat.format(Message.getString(key), values));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getStatusCode() {
		return 404;
	}

}
