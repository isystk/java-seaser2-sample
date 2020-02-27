package com.isystk.sample.common.constants.sort;

import com.isystk.sample.common.util.NumberUtil;

/**
 * カスタマーFB対応機能向けに作成したソート用のEnum
 */
public enum FairReserveIndexSort1 {

    ORDER_REGIST_TIME_ASC(1, "登録日時での昇順"), ORDER_REGIST_TIME_DESC(2, "登録日時での降順");

    private final int code;
    private final String value;

    FairReserveIndexSort1(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static FairReserveIndexSort1 get(String codeStr) {
        Integer code = NumberUtil.toInteger(codeStr);
        return (code != null) ? get(code.intValue()) : null;
    }

    public static FairReserveIndexSort1 get(int code) {
    for (FairReserveIndexSort1 value : values()) {
        if (value.code == code) {
        return value;
        }
    }
    return null;
    }

    /** JSPから取るための配列 */
    public static final FairReserveIndexSort1[] ITEMS = values();

    /** デフォルト値 */
    public static final FairReserveIndexSort1 DEFAULT = ORDER_REGIST_TIME_ASC;

}
