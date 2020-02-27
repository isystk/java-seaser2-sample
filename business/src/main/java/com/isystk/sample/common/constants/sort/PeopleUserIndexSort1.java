package com.isystk.sample.common.constants.sort;

import com.isystk.sample.common.util.NumberUtil;

/**
 * 先輩カップル機能向けに作成したソート用のEnum
 */
public enum PeopleUserIndexSort1 {

    ORDER_UPDATE_TIME_DESC(1, "更新日の降順"),
    ORDER_UPDATE_TIME_ASC(2, "更新日の昇順"),
    ORDER_POSTING_DATE_DESC(3, "投稿日の降順"),
    ORDER_POSTING_DATE_ASC(4, "投稿日の昇順");

    private final int code;
    private final String value;

    PeopleUserIndexSort1(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static PeopleUserIndexSort1 get(String codeStr) {
        Integer code = NumberUtil.toInteger(codeStr);
        return (code != null) ? get(code.intValue()) : null;
    }

    public static PeopleUserIndexSort1 get(int code) {
    for (PeopleUserIndexSort1 value : values()) {
        if (value.code == code) {
        return value;
        }
    }
    return null;
    }

    /** JSPから取るための配列 */
    public static final PeopleUserIndexSort1[] ITEMS = values();

    /** デフォルト値 */
    public static final PeopleUserIndexSort1 DEFAULT = ORDER_UPDATE_TIME_DESC;

}