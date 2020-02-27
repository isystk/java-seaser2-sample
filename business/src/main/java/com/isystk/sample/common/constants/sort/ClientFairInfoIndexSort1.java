package com.isystk.sample.common.constants.sort;

import com.isystk.sample.common.util.NumberUtil;

/**
 * クライアントのフェアーマスタ管理向けに作成したソート用のEnum
 * 
 * @author moriyataeko
 */
public enum ClientFairInfoIndexSort1 {

    ORDER_UPDATE_TIME_DESC(1, "更新日時の降順"),
    ORDER_UPDATE_TIME_ASC(2, "更新日時の昇順"),
    ORDER_REGIST_TIME_DESC(3, "登録日時の降順"),
    ORDER_REGIST_TIME_ASC(4, "登録日時の昇順");

    private final int code;
    private final String value;

    ClientFairInfoIndexSort1(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static ClientFairInfoIndexSort1 get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static ClientFairInfoIndexSort1 get(int code) {
	for (ClientFairInfoIndexSort1 value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** JSPから取るための配列 */
    public static final ClientFairInfoIndexSort1[] ITEMS = values();

    /** デフォルト値 */
    public static final ClientFairInfoIndexSort1 DEFAULT = ORDER_UPDATE_TIME_DESC;

}
