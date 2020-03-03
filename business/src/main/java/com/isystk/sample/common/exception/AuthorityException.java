/*
 * AuthorityException.java
 * 2012/04/23 iseyoshitaka
 */
package com.isystk.sample.common.exception;

import java.text.MessageFormat;

import com.isystk.sample.common.config.Message;

/**
 * @author iseyoshitaka
 * 
 */
public class AuthorityException extends StatusException {

	private static final long serialVersionUID = 2046191985881842811L;

	/**
	 * コンストラクタ。
	 * 
	 * @param message
	 *            メッセージ
	 * @param cause
	 *            元の例外
	 */
	public AuthorityException(String message, Throwable cause) {
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
	public AuthorityException(String key) {
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
	public AuthorityException(String key, String... values) {
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
