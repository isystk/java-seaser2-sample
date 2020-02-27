package com.isystk.sample.common.constants.sort;

import com.isystk.sample.common.util.NumberUtil;

/**
 * アルバム機能向けに作成したソート用のEnum
 */
public enum AlbumIndexSort1 {

    ORDER_UPDATE_TIME_ASC(1, "更新日時での昇順"), ORDER_UPDATE_TIME_DESC(2, "更新日時での降順"), ORDER_SORT_ASC(3, "sortの値の昇順");

    private final int code;
    private final String value;

    AlbumIndexSort1(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static AlbumIndexSort1 get(String codeStr) {
        Integer code = NumberUtil.toInteger(codeStr);
        return (code != null) ? get(code.intValue()) : null;
    }

    public static AlbumIndexSort1 get(int code) {
    for (AlbumIndexSort1 value : values()) {
        if (value.code == code) {
        return value;
        }
    }
    return null;
    }

    /** JSPから取るための配列 */
    public static final AlbumIndexSort1[] ITEMS = values();

    /** デフォルト値 */
    public static final AlbumIndexSort1 DEFAULT = ORDER_UPDATE_TIME_ASC;

}
