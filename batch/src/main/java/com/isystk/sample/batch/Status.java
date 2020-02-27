/*
 * Status.java
 * 2011/04/06 hnagato
 */
package com.isystk.sample.batch;

/**
 * バッチ終了ステータス.
 * 
 * @author hnagato
 */
public enum Status {

    SUCCESS(0),

    PARTIAL_SUCCESS(1),

    ERROR(2),

    ;

    private final int code_;

    private Status(int code) {
	code_ = code;
    }

    /**
     * @return コード値を返します.
     */
    public int getCode() {
	return code_;
    }
}
