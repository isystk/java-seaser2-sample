package com.isystk.sample.common.constants.sort;

import com.isystk.sample.common.util.NumberUtil;

/**
 * クライアントのフェアーマスタ管理向けに作成したソート用のEnum
 * 
 * @author moriyataeko
 */
public enum ClientFairDateIndexSort1 {

    ORDER_OPEN_TIME_ASC(1, "開催時間の昇順"), ORDER_OPEN_TIME_DESC(2, "開催時間の降順");

    private final int code;
    private final String value;

    ClientFairDateIndexSort1(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static ClientFairDateIndexSort1 get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static ClientFairDateIndexSort1 get(int code) {
	for (ClientFairDateIndexSort1 value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** JSPから取るための配列 */
    public static final ClientFairDateIndexSort1[] ITEMS = values();

    /** デフォルト値 */
    public static final ClientFairDateIndexSort1 DEFAULT = ORDER_OPEN_TIME_ASC;

}
