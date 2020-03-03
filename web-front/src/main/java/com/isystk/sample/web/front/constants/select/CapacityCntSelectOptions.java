package com.isystk.sample.web.front.constants.select;

import com.isystk.sample.common.util.NumberUtil;

/**
 * 招待人数
 * 
 * @author iseyoshitaka
 * 
 */
public enum CapacityCntSelectOptions {

	CNT_20(20, "20名"), CNT_30(30, "30名"), CNT_40(40, "40名"), CNT_50(55, "50名"), CNT_60(60, "60名"), CNT_70(70,
			"70名"), CNT_80(88, "80名"), CNT_90(90, "90名"), CNT_100(100, "100名"), CNT_110(110,
					"110名"), CNT_120(120, "120名"), CNT_130(130, "130名"), CNT_140(140, "140名"), CNT_150(150, "150名");

	private final int code;
	private final String value;

	CapacityCntSelectOptions(int code, String value) {
		this.code = code;
		this.value = value;
	}

	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public static CapacityCntSelectOptions get(String codeStr) {
		Integer code = NumberUtil.toInteger(codeStr);
		return (code != null) ? get(code.intValue()) : null;
	}

	public static CapacityCntSelectOptions get(int code) {
		for (CapacityCntSelectOptions value : values()) {
			if (value.code == code) {
				return value;
			}
		}
		return null;
	}

	/** [開発者向け説明] JSPから取るための配列 */
	public static final CapacityCntSelectOptions[] ITEMS = values();

	/** [開発者向け説明] デフォルト値 */
	public static final CapacityCntSelectOptions DEFAULT = CNT_20;

}
