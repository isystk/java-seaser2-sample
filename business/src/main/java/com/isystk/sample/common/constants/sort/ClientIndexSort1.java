package com.isystk.sample.common.constants.sort;

import com.isystk.sample.common.util.NumberUtil;

/**
 * クライアント管理ソート用のEnum
 */
public enum ClientIndexSort1 {

    ORDER_ASC_ID(1, "クライアントIDの昇順"), ORDER_DESC_ID(2, "クライアントIDの降順"), ORDER_ASC_NAME_KANA(3, "クライアント名（カナ）の昇順"), ORDER_DESC_NAME_KANA(4,
	    "クライアント名（カナ）の降順");

    private final int code;
    private final String value;

    ClientIndexSort1(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static ClientIndexSort1 get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static ClientIndexSort1 get(int code) {
	for (ClientIndexSort1 value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** JSPから取るための配列 */
    public static final ClientIndexSort1[] ITEMS = values();

    /** デフォルト値 */
    public static final ClientIndexSort1 DEFAULT = ORDER_DESC_ID;

}
