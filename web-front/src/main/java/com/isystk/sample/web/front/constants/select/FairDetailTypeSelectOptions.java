package com.isystk.sample.web.front.constants.select;

import com.isystk.sample.common.util.NumberUtil;

/**
 * フェアの内容(詳細)
 * 
 * @author iseyoshitaka
 *
 */
public enum FairDetailTypeSelectOptions {

	CONSULT(1, "模擬挙式"), FASHION(7, "コーディネート見学"), MOCK_CEREMONY(2, "模擬披露宴"), FOOD_TASTING_JP(5, "衣装試着"), FOOD_TASTING(3,
			"試食会"), FOOD_TASTING_WE(6, "相談会");

	private final int code;
	private final String value;

	FairDetailTypeSelectOptions(int code, String value) {
		this.code = code;
		this.value = value;
	}

	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public static FairDetailTypeSelectOptions get(String codeStr) {
		Integer code = NumberUtil.toInteger(codeStr);
		return (code != null) ? get(code.intValue()) : null;
	}

	public static FairDetailTypeSelectOptions get(int code) {
		for (FairDetailTypeSelectOptions value : values()) {
			if (value.code == code) {
				return value;
			}
		}
		return null;
	}

	/** [開発者向け説明] JSPから取るための配列 */
	public static final FairDetailTypeSelectOptions[] ITEMS = values();

	/** [開発者向け説明] デフォルト値 */
	public static final FairDetailTypeSelectOptions DEFAULT = CONSULT;

}
