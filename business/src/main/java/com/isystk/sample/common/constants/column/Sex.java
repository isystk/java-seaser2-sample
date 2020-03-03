package com.isystk.sample.common.constants.column;

import com.isystk.sample.common.util.NumberUtil;

/**
 * 性別
 * 
 * @author iseyoshitaka
 * 
 */
public enum Sex {

	FEMALE(2, "女"), MALE(1, "男");

	private final int code;
	private final String value;

	Sex(int code, String value) {
		this.code = code;
		this.value = value;
	}

	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public static Sex get(String codeStr) {
		Integer code = NumberUtil.toInteger(codeStr);
		return (code != null) ? get(code.intValue()) : null;
	}

	public static Sex get(int code) {
		for (Sex value : values()) {
			if (value.code == code) {
				return value;
			}
		}
		return null;
	}

	/** [開発者向け説明] JSPから取るための配列 */
	public static final Sex[] ITEMS = values();

	/** [開発者向け説明] デフォルト値 */
	public static final Sex DEFAULT = FEMALE;
}
