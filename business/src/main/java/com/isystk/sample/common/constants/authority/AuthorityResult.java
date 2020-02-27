package com.isystk.sample.common.constants.authority;

import com.isystk.sample.common.util.NumberUtil;

/**
 * 権限結果
 * 
 * @author iseyoshitaka
 * 
 */
public enum AuthorityResult {
    SUCCESS(1, "権限あり"), ERRORS_INVALIDID(2, "IDの形式不正"), ERRORS_NOTEXIST(3, "存在しない"), ERRORS_NOTAUTHORITY(4, "権限なし");

    private final int code;
    private final String value;

    AuthorityResult(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static AuthorityResult get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static AuthorityResult get(int code) {
	for (AuthorityResult value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** JSPから取るための配列 */
    public static final AuthorityResult[] ITEMS = values();

    /** デフォルト値 */
    public static final AuthorityResult DEFAULT = SUCCESS;
}
