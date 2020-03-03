package com.isystk.sample.common.constants;

import com.isystk.sample.common.util.NumberUtil;

/**
 * 都道府県
 * 
 * @author iseyoshitaka
 * 
 */
public enum Prefecture {

	AREA1(1, "北海道", PrefectureGroup.HOKKAIDO_TOHOKU), // 北海道
	AREA2(2, "青森県", PrefectureGroup.HOKKAIDO_TOHOKU), // 青森県
	AREA3(3, "岩手県", PrefectureGroup.HOKKAIDO_TOHOKU), // 岩手県
	AREA4(4, "宮城県", PrefectureGroup.HOKKAIDO_TOHOKU), // 宮城県
	AREA5(5, "秋田県", PrefectureGroup.HOKKAIDO_TOHOKU), // 秋田県
	AREA6(6, "山形県", PrefectureGroup.HOKKAIDO_TOHOKU), // 山形県
	AREA7(7, "福島県", PrefectureGroup.HOKKAIDO_TOHOKU), // 福島県

	AREA8(8, "茨城県", PrefectureGroup.KANTO_OTHER_TOKYO), // 茨城県
	AREA9(9, "栃木県", PrefectureGroup.KANTO_OTHER_TOKYO), // 栃木県
	AREA10(10, "群馬県", PrefectureGroup.KANTO_OTHER_TOKYO), // 群馬県
	AREA11(11, "埼玉県", PrefectureGroup.KANTO_OTHER_TOKYO), // 埼玉県
	AREA12(12, "千葉県", PrefectureGroup.KANTO_OTHER_TOKYO), // 千葉県
	AREA13(13, "東京都", PrefectureGroup.TOKYO), // 東京都
	AREA14(14, "神奈川県", PrefectureGroup.KANTO_OTHER_TOKYO), // 神奈川県

	AREA15(15, "山梨県", PrefectureGroup.HOKURIKU_KOUSHINETSU), // 山梨県
	AREA16(16, "新潟県", PrefectureGroup.HOKURIKU_KOUSHINETSU), // 新潟県
	AREA17(17, "富山県", PrefectureGroup.HOKURIKU_KOUSHINETSU), // 富山県
	AREA18(18, "石川県", PrefectureGroup.HOKURIKU_KOUSHINETSU), // 石川県
	AREA19(19, "福井県", PrefectureGroup.HOKURIKU_KOUSHINETSU), // 福井県
	AREA20(20, "長野県", PrefectureGroup.HOKURIKU_KOUSHINETSU), // 長野県
	AREA21(21, "岐阜県", PrefectureGroup.TOKAI), // 岐阜県
	AREA22(22, "静岡県", PrefectureGroup.TOKAI), // 静岡県
	AREA23(23, "愛知県", PrefectureGroup.TOKAI), // 愛知県
	AREA24(24, "三重県", PrefectureGroup.TOKAI), // 三重県

	AREA25(25, "滋賀県", PrefectureGroup.KANSAI), // 滋賀県
	AREA26(26, "京都府", PrefectureGroup.KANSAI), // 京都府
	AREA27(27, "大阪府", PrefectureGroup.KANSAI), // 大阪府
	AREA28(28, "兵庫県", PrefectureGroup.KANSAI), // 兵庫県
	AREA29(29, "奈良県", PrefectureGroup.KANSAI), // 奈良県
	AREA30(30, "和歌山県", PrefectureGroup.KANSAI), // 和歌山県

	AREA31(31, "鳥取県", PrefectureGroup.CHUGOKU), // 鳥取県
	AREA32(32, "島根県", PrefectureGroup.CHUGOKU), // 島根県
	AREA33(33, "岡山県", PrefectureGroup.CHUGOKU), // 岡山県
	AREA34(34, "広島県", PrefectureGroup.CHUGOKU), // 広島県
	AREA35(35, "山口県", PrefectureGroup.CHUGOKU), // 山口県
	AREA36(36, "徳島県", PrefectureGroup.SHIKOKU), // 徳島県
	AREA37(37, "香川県", PrefectureGroup.SHIKOKU), // 香川県
	AREA38(38, "愛媛県", PrefectureGroup.SHIKOKU), // 愛媛県
	AREA39(39, "高知県", PrefectureGroup.SHIKOKU), // 高知県

	AREA40(40, "福岡県", PrefectureGroup.KYUSHU_OKINAWA), // 福岡県
	AREA41(41, "佐賀県", PrefectureGroup.KYUSHU_OKINAWA), // 佐賀県
	AREA42(42, "長崎県", PrefectureGroup.KYUSHU_OKINAWA), // 長崎県
	AREA43(43, "熊本県", PrefectureGroup.KYUSHU_OKINAWA), // 熊本県
	AREA44(44, "大分県", PrefectureGroup.KYUSHU_OKINAWA), // 大分県
	AREA45(45, "宮崎県", PrefectureGroup.KYUSHU_OKINAWA), // 宮崎県
	AREA46(46, "鹿児島県", PrefectureGroup.KYUSHU_OKINAWA), // 鹿児島県
	AREA47(47, "沖縄県", PrefectureGroup.KYUSHU_OKINAWA); // 沖縄県

	// 都道府県ID
	private final Integer code;
	private final String value;
	private final PrefectureGroup prefectureGroup;

	Prefecture(Integer code, String value, PrefectureGroup prefectureGroup) {
		this.code = code;
		this.value = value;
		this.prefectureGroup = prefectureGroup;
	}

	public Integer getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public PrefectureGroup getPrefectureGroup() {
		return prefectureGroup;
	}

	public static Prefecture get(String codeStr) {
		Integer code = NumberUtil.toInteger(codeStr);
		return (code != null) ? get(code.intValue()) : null;
	}

	public static Prefecture get(Integer code) {
		if (code == null) {
			return null;
		}
		for (Prefecture value : values()) {
			if (value.code.intValue() == code.intValue()) {
				return value;
			}
		}
		return null;
	}

	public static final Prefecture[] ITEMS = values();
}
