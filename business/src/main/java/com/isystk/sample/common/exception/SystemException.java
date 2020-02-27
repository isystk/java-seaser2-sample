/*
 * SystemException.java
 * 2011/01/27 hnagato
 */
package com.isystk.sample.common.exception;

import java.text.MessageFormat;

import com.isystk.sample.common.config.Message;

/**
 * アプリケーション内部で発生した例外を catch & throw するための実行時例外です。
 * 
 * <p>
 * エラーログから何が起きたのかを把握しやすくするために、 出来る限り要因別に異なるサブクラスを作成し、throw する際にエラー発生時の
 * 詳細な情報をコンストラクタの message へ設定してください。
 * </p>
 * <p>
 * この例外、およびこの例外を継承した例外はフレームワークで catch されます。 特に理由が無い限りこの例外を catch しないよう注意してください。<br/>
 * ※ Throwable, Exception, RuntimeException を catch すると、 意図せず
 * SystemException(またはそのサブクラス)を catch してしまう 可能性があるので注意してください。
 * 
 * <pre>
 * try {
 *     // do something
 * } catch (Exception e) {
 *     // e =&gt; SystemException ?
 * }
 * </pre>
 * 
 * </p>
 * 
 * @author iseyoshitaka
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 8462188377363105093L;

    /**
     * @param message この例外に関する詳細な情報.
     * @param cause 原因となった例外
     */
    public SystemException(String message, Throwable cause) {
	super(message, cause);
	if ((message == null || message.trim().length() == 0) && cause == null) {
	    throw new IllegalArgumentException("Either of message or cause must be not null.");
	}
    }

    /**
     * プロパティーからメッセージを作成するコンストラクタ
     * 
     * @param key キー
     * @param values 値の配列
     */
    public SystemException(String key, String... values) {
	super(MessageFormat.format(Message.getString(key), values));
    }

    /**
     * メッセージを指定するコンストラクタ。
     * 
     * @param message メッセージ
     */
    public SystemException(String message) {
	super(message);
    }

    /**
     * 根源の例外を指定するコンストラクタ。
     * 
     * @param cause 根源の例外
     */
    public SystemException(Throwable cause) {
	super(cause);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Throwable#toString()
     */
    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder(getClass().getName());

	if (getCause() != null) {
	    sb.append(": ").append(getCause().getClass().getName());
	}

	String message = getLocalizedMessage();
	if (message != null) {
	    sb.append(": ").append(message);
	}

	return sb.toString();
    }
}
