package com.isystk.sample.common.constants.sort;

import com.isystk.sample.common.util.NumberUtil;

/**
 * Adminアカウント管理向けに作成したソート用のEnum
 * 
 * @author yanagimotokeita
 * 
 */
public enum AdminAccountIndexSort {
    ORDER_REGIST_TIME_ASC(1, "登録日時での昇順"),
    ORDER_REGIST_TIME_DESC(2, "登録日時での降順"),
    ORDER_FULL_NAME_KANA_ASC(3, "氏名（カナ）の昇順"),
    ORDER_FULL_NAME_KANA_DESC(4, "氏名（カナ）の降順");

    private final int code;
    private final String value;

    private AdminAccountIndexSort(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static AdminAccountIndexSort get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static AdminAccountIndexSort get(int code) {
	for (AdminAccountIndexSort value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** JSPから取るための配列 */
    public static final AdminAccountIndexSort[] ITEMS = values();

    /** デフォルト値 */
    public static final AdminAccountIndexSort DEFAULT = ORDER_REGIST_TIME_ASC;
}
