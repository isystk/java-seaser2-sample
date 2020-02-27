package com.isystk.sample.common.constants.column;

import com.isystk.sample.common.util.NumberUtil;

/**
 * 会員ステータス
 *
 * @author iseyoshitaka
 *
 */
public enum UserStatus {

    TEMPORARY(0, "仮登録"),
    VALID(1, "有効"),
    INVALID(2, "利用停止"),
    WITHDRAW(3, "退会者");

    private final int code;
    private final String value;

    UserStatus(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static UserStatus get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static UserStatus get(int code) {
	for (UserStatus value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** [開発者向け説明] JSPから取るための配列 */
    public static final UserStatus[] ITEMS = values();

    /** [開発者向け説明] デフォルト値 */
    public static final UserStatus DEFAULT = VALID;
}
