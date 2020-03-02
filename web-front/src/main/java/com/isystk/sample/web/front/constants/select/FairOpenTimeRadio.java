package com.isystk.sample.web.front.constants.select;

import com.isystk.sample.common.util.NumberUtil;

/**
 * フェア開始時間 チェックボックス で使用するEnum
 */
public enum FairOpenTimeRadio {

    /** 7時 */
    TIME_7(700, "7時"),
    /** 8時 */
    TIME_8(800, "8時"),
    /** 9時 */
    TIME_9(900, "9時"),
    /** 10時 */
    TIME_10(1000, "10時"),
    /** 11時 */
    TIME_11(1100, "11時"),
    /** 12時 */
    TIME_12(1200, "12時"),
    /** 13時 */
    TIME_13(1300, "13時"),
    /** 14時 */
    TIME_14(1400, "14時"),
    /** 15時 */
    TIME_15(1500, "15時"),
    /** 16時 */
    TIME_16(1600, "16時"),
    /** 17時 */
    TIME_17(1700, "17時"),
    /** 18時 */
    TIME_18(1800, "18時"),
    /** 19時 */
    TIME_19(1900, "19時"),
    /** 20時 */
    TIME_20(2000, "20時");

    private final int code;
    private final String value;

    FairOpenTimeRadio(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static FairOpenTimeRadio get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static FairOpenTimeRadio get(int code) {
	for (FairOpenTimeRadio value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** JSPから取るための配列 */
    public static final FairOpenTimeRadio[] ITEMS = values();

    /** デフォルト値 */
    public static final FairOpenTimeRadio DEFAULT = TIME_9;

}
