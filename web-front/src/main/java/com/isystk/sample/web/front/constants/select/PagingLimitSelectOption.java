package com.isystk.sample.web.front.constants.select;

import com.isystk.sample.common.util.NumberUtil;

public enum PagingLimitSelectOption {

	C20(20, "20件"), C30(30, "30件"), C50(50, "50件"), C80(80, "80件");

	private final int code;
	private final String value;

	PagingLimitSelectOption(int code, String value) {
		this.code = code;
		this.value = value;
	}

	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public static PagingLimitSelectOption get(String codeStr) {
		Integer code = NumberUtil.toInteger(codeStr);
		return (code != null) ? get(code.intValue()) : null;
	}

	public static PagingLimitSelectOption get(int code) {
		for (PagingLimitSelectOption value : values()) {
			if (value.code == code) {
				return value;
			}
		}
		return null;
	}

	public static final PagingLimitSelectOption[] ITEMS = { C20, C50, C80 };
	public static final PagingLimitSelectOption[] ITEMS_ALL = values();

	public static final PagingLimitSelectOption DEFAULT = C20;

}
