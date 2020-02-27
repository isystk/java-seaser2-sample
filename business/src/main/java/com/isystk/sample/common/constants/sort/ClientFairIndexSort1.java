package com.isystk.sample.common.constants.sort;

import com.isystk.sample.common.util.NumberUtil;

/**
 * クライアントのフェアー管理向けに作成したソート用のEnum
 * 
 * @author matsudashogo
 */
public enum ClientFairIndexSort1 {

    ORDER_OPEN_TIME_ASC(1, "開催日時の昇順"), ORDER_OPEN_TIME_DESC(2, "開催日時の降順"), ORDER_TITLE_ASC(3, "タイトルの昇順"), ORDER_TITLE_DESC(4, "タイトルの降順");

    private final int code;
    private final String value;

    ClientFairIndexSort1(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static ClientFairIndexSort1 get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static ClientFairIndexSort1 get(int code) {
	for (ClientFairIndexSort1 value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** JSPから取るための配列 */
    public static final ClientFairIndexSort1[] ITEMS = values();

    /** デフォルト値 */
    public static final ClientFairIndexSort1 DEFAULT = ORDER_OPEN_TIME_ASC;

}
