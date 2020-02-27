package com.isystk.sample.common.constants.sort;

import com.isystk.sample.common.util.NumberUtil;

/**
 * クライアントアカウント管理機能向けに作成したソート用のEnum
 */
public enum ClientAccountIndexSort1 {

    ORDER_ASC_ID(1, "ログインIDの昇順"), 
    ORDER_DESC_ID(2, "ログインIDの降順"), 
    ORDER_ASC_NAME(3, "クライアントアカウント名（カナ）の昇順"), 
    ORDER_DESC_NAME(4, "クライアントアカウント名（カナ）の降順");

    private final int code;
    private final String value;

    ClientAccountIndexSort1(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static ClientAccountIndexSort1 get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static ClientAccountIndexSort1 get(int code) {
	for (ClientAccountIndexSort1 value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** JSPから取るための配列 */
    public static final ClientAccountIndexSort1[] ITEMS = values();

    /** デフォルト値 */
    public static final ClientAccountIndexSort1 DEFAULT = ORDER_DESC_ID;

}
