package com.isystk.sample.web.userpc.constants.select;

import com.isystk.sample.common.util.NumberUtil;

/**
 * ゲスト人数区分
 * 
 * @author iseyoshitaka
 * 
 */
public enum GuestCntSelectOptions {

    CNT_20(2, "20名"),
    CNT_30(3, "30名"),
    CNT_40(4, "40名"),
    CNT_50(5, "50名"),
    CNT_60(6, "60名"),
    CNT_70(7, "70名"),
    CNT_80(8, "80名"),
    CNT_90(9, "90名"),
    CNT_100(10, "100名"),
    CNT_110(11, "110名"),
    CNT_120(12, "120名"),
    CNT_130(13, "130名"),
    CNT_140(14, "140名"),
    CNT_150(15, "150名");


    private final int code;
    private final String value;

    GuestCntSelectOptions(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static GuestCntSelectOptions get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static GuestCntSelectOptions get(int code) {
	for (GuestCntSelectOptions value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    public static GuestCntSelectOptions getByValue(String valueStr) {
	for (GuestCntSelectOptions value : values()) {
	    if (value.value.equals(valueStr)) {
		return value;
	    }
	}
	return null;
    }

    /** [開発者向け説明] JSPから取るための配列 */
    public static final GuestCntSelectOptions[] ITEMS = values();

    /** [開発者向け説明] デフォルト値 */
    public static final GuestCntSelectOptions DEFAULT = CNT_20;
    
}
