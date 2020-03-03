/*
 * ExternalAPIFailureException.java
 * 2011/02/01 iseyoshitaka
 */
package com.isystk.sample.common.exception;

import java.text.MessageFormat;

import com.isystk.sample.common.config.Message;

/**
 * 外部APIに起因するシステム障害が発生していることを示す例外です.
 * 
 * @author iseyoshitaka
 */
public class ExternalAPIFailureException extends SystemException {

	private static final long serialVersionUID = -3616252830228705665L;

	/**
	 * @param message
	 *            この例外に関する詳細な情報.
	 * @param cause
	 *            外部API連携時に発生した例外
	 */
	public ExternalAPIFailureException(String message, Exception cause) {
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
	public ExternalAPIFailureException(String key, String... values) {
		super(MessageFormat.format(Message.getString(key), values));
	}

}
