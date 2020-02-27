package com.isystk.sample.common.constants.sort;

import com.isystk.sample.common.util.NumberUtil;

//[開発者向け説明] サンプル機能向けに作成したソート用のEnum
//[開発者向け説明] ソート用のEnumはこのフォルダに配置する。システム全体で共用されることに注意。
/**
 * サンプル機能向けに作成したソート用のEnum
 */
public enum SampleFunctionIndexSort1 {

    ORDER_ASC(1, "昇順"), ORDER_DESC(2, "降順");

    private final int code;
    private final String value;

    SampleFunctionIndexSort1(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static SampleFunctionIndexSort1 get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static SampleFunctionIndexSort1 get(int code) {
	for (SampleFunctionIndexSort1 value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** [開発者向け説明] JSPから取るための配列 */
    public static final SampleFunctionIndexSort1[] ITEMS = values();

    /** [開発者向け説明] デフォルト値 */
    public static final SampleFunctionIndexSort1 DEFAULT = ORDER_ASC;

}
