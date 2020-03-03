package com.isystk.sample.web.front.constants.select;

import com.isystk.sample.common.util.NumberUtil;

/**
 * 収容人数 選択タイプ
 * 
 * @author iseyoshitaka
 * 
 */
public enum GuestMaxCntSelectOption {

	SEAT_OR_STANDUP(0, "着席or立食"), SEAT_ONLY(1, "着席"), STANDUP_ONLY(2, "立食");

	private final int code;
	private final String value;

	GuestMaxCntSelectOption(int code, String value) {
		this.code = code;
		this.value = value;
	}

	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public static GuestMaxCntSelectOption get(String codeStr) {
		Integer code = NumberUtil.toInteger(codeStr);
		return (code != null) ? get(code.intValue()) : null;
	}

	public static GuestMaxCntSelectOption get(int code) {
		for (GuestMaxCntSelectOption value : values()) {
			if (value.code == code) {
				return value;
			}
		}
		return null;
	}

	/** [開発者向け説明] JSPから取るための配列 */
	public static final GuestMaxCntSelectOption[] ITEMS = values();

	/** [開発者向け説明] デフォルト値 */
	public static final GuestMaxCntSelectOption DEFAULT = SEAT_OR_STANDUP;

}
