package com.isystk.sample.common.constants.sort;

import com.isystk.sample.common.util.NumberUtil;

/**
 * 直近プランテンプレート管理機能向けに作成したソート用のEnum
 */
public enum LptemplateIndexSort1 {

    ORDER_DESC_REGIST_TIME(1, "登録日の新しい順"), ORDER_ASC_REGIST_TIME(2, "登録日の古い順"), ORDER_ASC_PLAN_TITLE(3, "テンプレート名の昇順"), ORDER_DESC_PLAN_TITLE(4,
	    "テンプレート名の降順");

    private final int code;
    private final String value;

    LptemplateIndexSort1(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static LptemplateIndexSort1 get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static LptemplateIndexSort1 get(int code) {
	for (LptemplateIndexSort1 value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** JSPから取るための配列 */
    public static final LptemplateIndexSort1[] ITEMS = values();

    /** デフォルト値 */
    public static final LptemplateIndexSort1 DEFAULT = ORDER_ASC_REGIST_TIME;

}
