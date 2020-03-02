/*
 * StatusException.java
 * 2011/02/08 iseyoshitaka
 */
package com.isystk.sample.common.exception;

/**
 * HTTPステータスコードを持つ各種例外発生時に、この例外でラップして上記の呼び出し階層へthrowしてください。
 *
 * @author iseyoshitaka
 *
 */
public abstract class StatusException extends RuntimeException {

    private static final long serialVersionUID = 2773267532559581368L;

    /**
	 *
	 * コンストラクタ。
	 */
    public StatusException() {
	assertStackTrace();
    }

    /**
     * @param message メッセージ
     */
    public StatusException(String message) {
	super(message);
	assertStackTrace();
    }

    /**
     * @param message メッセージ
     * @param cause 原因となった例外
     */
    public StatusException(String message, Throwable cause) {
	super(message, cause);
	assertStackTrace();
    }

    /**
     * @return ステータスコードを取得
     */
    public abstract int getStatusCode();

    /**
     * Form / Action 以外をこのクラスの呼び出し元にすることは出来ない →開発時に起こらないように実装する必要があります
     */
    protected void assertStackTrace() {
	// TODO iseyoshitaka 本番環境はエラー画面を出せないのでそのまま返却
	//if (Server.PRODUCT.equals(Server.getValue()))
	//return;

	StackTraceElement[] stackTraces = this.getStackTrace();

	if (stackTraces != null) {
//	    String className = stackTraces[0].getClassName();
//	    // 直接の呼び出し元が Form / Action / Interceptor クラスでない場合、AssertionErrorを投げる
//	    if (!className
			// .matches("^jp\\.mynavi\\.wedding\\.web\\.(admin|client|front)\\.s2\\.(form|action|interceptor)\\..+(Form|Action|Interceptor)$"))
//		throw new AssertionError("class [" + className + "] は StatusException を投げることは出来ません。");
	}
    }

}
