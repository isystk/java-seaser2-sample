package com.isystk.sample.common.constants.sort;

import com.isystk.sample.common.util.NumberUtil;

/**
 * エリア検索バナー一覧ソート用のEnum
 */
public enum PrListResultSort1 {

    ORDER_ASC_ID(1, "申込ID"), ORDER_ASC_STATUS(2, "申込ステータス"), ORDER_ASC_CLIENT(3, "クライアント名"), ORDER_ASC_WEDDING(4, "屋号名"), ORDER_ASC_PRDIV(5, "枠区分"), ORDER_ASC_REGION(
	    6, "地域・エリア"), ORDER_ASC_PRICE(7, "単価"), ORDER_ASC_PUBLICATION_START_DATE(8, "掲載開始日"), ORDER_ASC_PUBLICATION_END_DATE(9, "掲載終了日"), ORDER_ASC_PUBLICATION_END_BUSINESS_ADMIN(
	    10, "営業担当");

    private final int code;
    private final String value;

    PrListResultSort1(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static PrListResultSort1 get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static PrListResultSort1 get(int code) {
	for (PrListResultSort1 value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** JSPから取るための配列 */
    public static final PrListResultSort1[] ITEMS = values();

    /** デフォルト値 */
    public static final PrListResultSort1 DEFAULT = ORDER_ASC_ID;

}
