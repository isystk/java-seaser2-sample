package com.isystk.sample.common.constants.authority;

import com.isystk.sample.common.util.NumberUtil;

/**
 * アドミンアカウント区分
 * 
 * @author itakuranaoki
 * 
 */
public enum AdminAccountDiv {
    SYSTEM_ADMIN(1, "システム管理者"),
    BUSINESS_ADMIN(2, "営業"),
    BUSINESS_MGR_ADMIN(3, "事業推進"),
    EDIT_ADMIN(4, "編集"),
    SALON(5, "サロン");

    private final int code;
    private final String value;

    AdminAccountDiv(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static AdminAccountDiv get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static AdminAccountDiv get(int code) {
	for (AdminAccountDiv value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** JSPから取るための配列 */
    public static final AdminAccountDiv[] ITEMS = values();

    /** デフォルト値 */
    public static final AdminAccountDiv DEFAULT = SYSTEM_ADMIN;
}
