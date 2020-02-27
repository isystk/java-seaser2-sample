package com.isystk.sample.common.constants.sort;

import com.isystk.sample.common.util.NumberUtil;

/**
 * ランキング管理ソート用Enum
 * 
 * @author iseyoshitaka
 * 
 */
public enum RankingIndexSort1 {

    ORDER_DESC_UPDATE_DATE(1, "更新日の降順"),
    ORDER_ASC_UPDATE_DATE(2, "更新日の昇順"),
    ORDER_DESC_REGIST_DATE(3, "登録日の降順"),
    ORDER_ACS_REGIST_DATE(4, "登録日の昇順");

    private final int code;
    private final String value;

    RankingIndexSort1(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static RankingIndexSort1 get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static RankingIndexSort1 get(int code) {
	for (RankingIndexSort1 value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** JSPから取るための配列 */
    public static final RankingIndexSort1[] ITEMS = values();

    /** デフォルト値 */
    public static final RankingIndexSort1 DEFAULT = ORDER_DESC_UPDATE_DATE;

}
