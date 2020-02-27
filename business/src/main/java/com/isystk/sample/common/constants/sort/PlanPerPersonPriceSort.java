package com.isystk.sample.common.constants.sort;

import com.isystk.sample.common.util.NumberUtil;

/**
 * プラン一覧 向けに作成したソート用のEnum
 */
public enum PlanPerPersonPriceSort {
    PLAN_20_UNDER_PERSON_PRICE_SORT_ASC(11, "金額の安い順"),
    PLAN_20_UNDER_PERSON_PRICE_SORT_DESC(12, "金額の高い順"),
    PLAN_30_PERSON_PRICE_SORT_ASC(13, "金額の安い順"),
    PLAN_30_PERSON_PRICE_SORT_DESC(14, "金額の高い順"),
    PLAN_40_PERSON_PRICE_SORT_ASC(15, "金額の安い順"),
    PLAN_40_PERSON_PRICE_SORT_DESC(16, "金額の高い順"),
    PLAN_50_PERSON_PRICE_SORT_ASC(17, "金額の安い順"),
    PLAN_50_PERSON_PRICE_SORT_DESC(18, "金額の高い順"),
    PLAN_60_PERSON_PRICE_SORT_ASC(19, "金額の安い順"),
    PLAN_60_PERSON_PRICE_SORT_DESC(20, "金額の高い順"),
    PLAN_70_PERSON_PRICE_SORT_ASC(21, "金額の安い順"),
    PLAN_70_PERSON_PRICE_SORT_DESC(22, "金額の高い順"),
    PLAN_80_PERSON_PRICE_SORT_ASC(23, "金額の安い順"),
    PLAN_80_PERSON_PRICE_SORT_DESC(24, "金額の高い順"),
    PLAN_90_PERSON_PRICE_SORT_ASC(25, "金額の安い順"),
    PLAN_90_PERSON_PRICE_SORT_DESC(26, "金額の高い順"),
    PLAN_100_PERSON_PRICE_SORT_ASC(27, "金額の安い順"),
    PLAN_100_PERSON_PRICE_SORT_DESC(28, "金額の高い順"),
    PLAN_110_PERSON_PRICE_SORT_ASC(29, "金額の安い順"),
    PLAN_110_PERSON_PRICE_SORT_DESC(30, "金額の高い順"),
    PLAN_120_PERSON_PRICE_SORT_ASC(31, "金額の安い順"),
    PLAN_120_PERSON_PRICE_SORT_DESC(32, "金額の高い順"),
    PLAN_130_PERSON_PRICE_SORT_ASC(33, "金額の安い順"),
    PLAN_130_PERSON_PRICE_SORT_DESC(34, "金額の高い順"),
    PLAN_140_PERSON_PRICE_SORT_ASC(35, "金額の安い順"),
    PLAN_140_PERSON_PRICE_SORT_DESC(36, "金額の高い順"),
    PLAN_150_PERSON_PRICE_SORT_ASC(37, "金額の安い順"),
    PLAN_150_PERSON_PRICE_SORT_DESC(38, "金額の高い順");

    private final int code;
    private final String value;

    PlanPerPersonPriceSort(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static PlanPerPersonPriceSort get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static PlanPerPersonPriceSort get(int code) {
	for (PlanPerPersonPriceSort value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** JSPから取るための配列 */
    public static final PlanPerPersonPriceSort[] ITEMS = values();

    /** デフォルト値 */
    public static final PlanPerPersonPriceSort DEFAULT = PLAN_20_UNDER_PERSON_PRICE_SORT_ASC;

}
