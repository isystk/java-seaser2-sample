package com.isystk.sample.common.constants.sort;

import com.isystk.sample.common.util.NumberUtil;

/**
 * カスタマー対応機能向けに作成したソート用のEnum
 */
public enum CustomerIndexSort1 {

    ORDER_REGIST_TIME_ASC(1, "受付日時の昇順"), ORDER_REGIST_TIME_DESC(2, "受付日時の降順"), ORDER_CUSTOMER_NAME_KANA_ASC(3, "お客様カナ名の昇順"), ORDER_CUSTOMER_NAME_KANA_DESC(
	    4, "お客様カナ名の降順");

    private final int code;
    private final String value;

    CustomerIndexSort1(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static CustomerIndexSort1 get(String codeStr) {
        Integer code = NumberUtil.toInteger(codeStr);
        return (code != null) ? get(code.intValue()) : null;
    }

    public static CustomerIndexSort1 get(int code) {
    for (CustomerIndexSort1 value : values()) {
        if (value.code == code) {
        return value;
        }
    }
    return null;
    }

    /** JSPから取るための配列 */
    public static final CustomerIndexSort1[] ITEMS = values();

    /** デフォルト値 */
    public static final CustomerIndexSort1 DEFAULT = ORDER_REGIST_TIME_DESC;

}
