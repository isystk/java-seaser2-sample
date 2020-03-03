package com.isystk.sample.web.front.constants.select;

import com.isystk.sample.common.util.NumberUtil;

public enum PagingLimitQuarterSelectOption {

	C15(15, "15件"), C30(30, "30件"), C45(45, "45件");

	private final int code;
	private final String value;

	PagingLimitQuarterSelectOption(int code, String value) {
		this.code = code;
		this.value = value;
	}

	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public static PagingLimitQuarterSelectOption get(String codeStr) {
		Integer code = NumberUtil.toInteger(codeStr);
		return (code != null) ? get(code.intValue()) : null;
	}

	public static PagingLimitQuarterSelectOption get(int code) {
		for (PagingLimitQuarterSelectOption value : values()) {
			if (value.code == code) {
				return value;
			}
		}
		return null;
	}

	public static final PagingLimitQuarterSelectOption[] ITEMS = values();

	public static final PagingLimitQuarterSelectOption DEFAULT = C15;

}
