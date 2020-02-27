/*
 * ApplicationException.java
 * 2011/02/01 hnagato
 */
package com.isystk.sample.common.exception;

import java.text.MessageFormat;

import com.isystk.sample.common.config.Message;

/**
 * ビジネスロジックにおいて発生した例外を表す抽象クラスです.
 * 
 * <p>
 * 具象クラスは、”適切な“名称でこのクラスを継承して作成してください。
 * </p>
 * <p>
 * ApplicationException の具象サブクラスは、SystemException とは異なり ExceptionHandler
 * での処理の対象となりません。<br/>
 * 
 * Logic / Service / Entity クラスなどの内部でこの例外を throw する場合、 <b>必ず</b> Action
 * もしくは同等のコントローラ層に属するクラスで catch し、 ログ出力・遷移先決定などの処理を行うよう実装してください.
 * </p>
 * 
 * @author iseyoshitaka
 */
public abstract class ApplicationException extends Exception {

    private static final long serialVersionUID = -4092440344741440634L;

    /**
     * @param message この例外に関する詳細な情報.
     * @param cause 原因となった例外
     */
    public ApplicationException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * @param message この例外に関する詳細な情報.
     */
    public ApplicationException(String message) {
	super(message);
    }

    /**
     * プロパティーからメッセージを作成するコンストラクタ
     * 
     * @param key キー
     * @param values 値の配列
     */
    public ApplicationException(String key, String... values) {
	super(MessageFormat.format(Message.getString(key), values));
    }
}
