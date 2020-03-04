package com.isystk.sample.common.constants;

import java.util.List;

import com.google.common.collect.Lists;
import com.isystk.sample.common.util.NumberUtil;

/**
 * 都道府県グループ
 *
 * @author iseyoshitaka
 *
 */
public enum PrefectureGroup {

	HOKKAIDO_TOHOKU(1, "北海道・東北"), // 北海道・東北
	TOKYO(2, "東京"), // 東京
	KANTO_OTHER_TOKYO(3, "東京以外の関東"), // 東京以外の関東
	HOKURIKU_KOUSHINETSU(4, "北陸・甲信越"), // 北陸・甲信越
	TOKAI(5, "東海"), // 東海
	KANSAI(6, "関西"), // 関西
	CHUGOKU(7, "中国"), // 中国
	SHIKOKU(8, "四国"), // 四国
	KYUSHU_OKINAWA(9, "九州・沖縄"); // 九州・沖縄

	// 都道府県ID
	private final Integer code;
	private final String value;

	PrefectureGroup(Integer code, String value) {
		this.code = code;
		this.value = value;
	}

	public Integer getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	/**
	 * 紐づく都道府県を取得する。
	 *
	 * @return
	 */
	public List<Prefecture> getPrefectureList() {
		List<Prefecture> prefectureList = Lists.newArrayList();
		for (Prefecture prefecture : Prefecture.ITEMS) {
			if (prefecture.getPrefectureGroup().getCode() == code) {
				prefectureList.add(prefecture);
			}
		}
		return prefectureList;
	}

	public static PrefectureGroup get(String codeStr) {
		Integer code = NumberUtil.toInteger(codeStr);
		return (code != null) ? get(code.intValue()) : null;
	}

	public static PrefectureGroup get(Integer code) {
		if (code == null) {
			return null;
		}
		for (PrefectureGroup value : values()) {
			if (value.code.intValue() == code.intValue()) {
				return value;
			}
		}
		return null;
	}

	public static final PrefectureGroup[] ITEMS = values();
}
