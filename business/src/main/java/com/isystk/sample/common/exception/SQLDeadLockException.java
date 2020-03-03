/*
 * SQLDeadLockException.java
 * 2012/05/31 iseyoshitaka
 */
package com.isystk.sample.common.exception;

import org.seasar.extension.jdbc.exception.SOptimisticLockException;

/**
 * データベースにてデッドロックが発生した場合の例外。
 * 概念的には、別トランザクションが同じレコードを変更しているために発生するエラーであるために、楽観的ロック例外
 * （SOptimisticLockException）を継承している。
 * 
 * @author iseyoshitaka
 * 
 */
public class SQLDeadLockException extends SOptimisticLockException {
	private String message;
	private Throwable cause;

	private static final long serialVersionUID = -1018353601958087212L;

	/**
	 * コンストラクタ。
	 * 
	 * @param message
	 *            メッセージ
	 * @param cause
	 *            元の例外
	 */
	public SQLDeadLockException(String message, Throwable cause) {
		super();
		this.message = message;
		this.cause = cause;
	}

	@Override
	public Throwable getCause() {
		return cause;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
