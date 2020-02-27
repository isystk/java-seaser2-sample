package com.isystk.sample.common.constants.authority;

import com.isystk.sample.common.util.NumberUtil;

/**
 * クライアントアカウント区分
 * 
 * @author moriyataeko
 * 
 */
public enum ClientAccountDiv {
    SYSTEM_ADMIN(1, "システム管理者代行"),
    BUSINESS_ADMIN(2, "営業代行"),
    EDIT_ADMIN(3, "編集代行"),
    SALON_ADMIN(4, "サロン代行"),
    CORP_CLIENT(11, "企業責任者"),
    HALL_CLIENT(12, "マネージャー"),
    PLANNER_CLIENT(13, "店舗スタッフ"),
    OPERATOR_CLIENT(14, "オペレーター"),
    PLANNER_SALON_CLIENT(15, "店舗スタッフ(サロン)");

    private final int code;
    private final String value;

    ClientAccountDiv(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static ClientAccountDiv get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static ClientAccountDiv get(int code) {
	for (ClientAccountDiv value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** JSPから取るための配列 */
    public static final ClientAccountDiv[] ITEMS = values();

    /** デフォルト値 */
    public static final ClientAccountDiv DEFAULT = SYSTEM_ADMIN;
}
