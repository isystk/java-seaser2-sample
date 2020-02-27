package com.isystk.sample.common.constants.sort;

import com.isystk.sample.common.util.NumberUtil;

/**
 * 式場原稿テンプレート管理機能向けに作成したソート用のEnum
 */
public enum WtemplateIndexSort1 {

    ORDER_DESC_REGIST_TIME(1, "登録日の新しい順"), ORDER_ASC_REGIST_TIME(2, "登録日の古い順"), ORDER_ASC_TEMPLATE_NAME(3, "テンプレート名の昇順"), ORDER_DESC_TEMPLATE_NAME(4,
	    "テンプレート名の降順");

    private final int code;
    private final String value;

    WtemplateIndexSort1(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static WtemplateIndexSort1 get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static WtemplateIndexSort1 get(int code) {
	for (WtemplateIndexSort1 value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** JSPから取るための配列 */
    public static final WtemplateIndexSort1[] ITEMS = values();

    /** デフォルト値 */
    public static final WtemplateIndexSort1 DEFAULT = ORDER_ASC_REGIST_TIME;

}
