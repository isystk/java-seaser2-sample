package com.isystk.sample.common.constants.sort;

import com.isystk.sample.common.util.NumberUtil;

/**
 * 特集機能向けに作成したソート用のEnum
 */
public enum FeatureIndexSort {

//    ORDER_THEME_NAME_KANA_ASC(1, "まとめグループ名の昇順"),
//    ORDER_THEME_NAME_KANA_DESC(2, "まとめグループ名の降順"),
    ORDER_UPDATE_TIME_DESC(3, "更新日の降順"),
    ORDER_UPDATE_TIME_ASC(4, "更新日の昇順"),
    ORDER_REGIST_TIME_DESC(5, "作成日の降順"),
    ORDER_REGIST_TIME_ASC(6, "作成日の昇順");

    private final int code;
    private final String value;

    FeatureIndexSort(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static FeatureIndexSort get(String codeStr) {
        Integer code = NumberUtil.toInteger(codeStr);
        return (code != null) ? get(code.intValue()) : null;
    }

    public static FeatureIndexSort get(int code) {
    for (FeatureIndexSort value : values()) {
        if (value.code == code) {
        return value;
        }
    }
    return null;
    }

    /** JSPから取るための配列 */
    public static final FeatureIndexSort[] ITEMS = values();

    /** デフォルト値 */
    public static final FeatureIndexSort DEFAULT = ORDER_REGIST_TIME_DESC;

}
