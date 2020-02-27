/**
 * 
 */
package com.isystk.sample.common.exception;

import java.text.MessageFormat;

import com.isystk.sample.common.config.Message;

/**
 * 復号化失敗
 * 
 * @author iseyoshitaka
 */
public class FailsDecryptException extends SystemException {

    /** serialVersionUID */
    private static final long serialVersionUID = -9109999117367793255L;

    /**
     * メッセージと根源の例外を指定するコンストラクタ。
     * 
     * @param message メッセージ
     * @param cause 根源の例外
     */
    public FailsDecryptException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * メッセージを指定するコンストラクタ。
     * 
     * @param message メッセージ
     */
    public FailsDecryptException(String message) {
	super(message);
    }

    /**
     * 根源の例外を指定するコンストラクタ。
     * 
     * @param cause 根源の例外
     */
    public FailsDecryptException(Throwable cause) {
	super(cause);
    }

    /**
     * プロパティーからメッセージを作成するコンストラクタ
     * 
     * @param key キー
     * @param values 値の配列
     */
    public FailsDecryptException(String key, String... values) {
	super(MessageFormat.format(Message.getString(key), values));
    }

}
