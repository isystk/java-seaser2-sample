package com.isystk.sample.common.constants.sort;

import com.isystk.sample.common.util.NumberUtil;

public enum SalonClaimSort {

    DATE_ASC(           1, "請求月の昇順"),
    DATE_DESC(          2, "請求月の降順"),
    ANSWER_FLG_OFF(     3, "マイナビ対応状況未対応"),
    ANSWER_FLG_ON(      4, "マイナビ対応状況対応済み"),
    SALON_NAME_ASC(     5, "対応サロンの昇順"),
    SALON_NAME_DESC(    6, "対応サロンの降順"),
    RESULT_STATUS_ASC(  7, "成果ステータスの昇順"),
    RESULT_STATUS_DESC( 8, "成果ステータスの降順"),
    WEDDING_DATE_ASC(   9, "挙式日の昇順"),
    WEDDING_DATE_DESC( 10, "挙式日の降順"),
    CONTRACT_DATE_ASC( 11, "成約日の昇順"),
    CONTRACT_DATE_DESC(12, "成約日の降順"),
    WEDDING_STYLE_ASC( 13, "挙式スタイルの昇順"),
    WEDDING_STYLE_DESC(14, "挙式スタイルの降順"),
    ;

    private final int code;
    private final String value;

    SalonClaimSort(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static SalonClaimSort get(String codeStr) {
	try {
	    Integer code = NumberUtil.toInteger(codeStr);
	    return (code != null) ? get(code.intValue()) : null;
	} catch (Exception e) {
	    return null;
	}
    }

    public static SalonClaimSort get(int code) {
    for (SalonClaimSort value : values()) {
        if (value.code == code) {
        return value;
        }
    }
    return null;
    }

    /** JSPから取るための配列 */
    public static final SalonClaimSort[] CLIENT_ITEMS = {
	DATE_ASC,
	DATE_DESC,
	ANSWER_FLG_OFF,
	ANSWER_FLG_ON,
	SALON_NAME_ASC,
	SALON_NAME_DESC,
	RESULT_STATUS_ASC,
	RESULT_STATUS_DESC
    };
    public static final SalonClaimSort[] ADMIN_ITEMS = {
	ANSWER_FLG_OFF,
	ANSWER_FLG_ON,
	WEDDING_DATE_ASC,
	WEDDING_DATE_DESC,
	CONTRACT_DATE_ASC,
	CONTRACT_DATE_DESC,
	SALON_NAME_ASC,
	SALON_NAME_DESC,
	WEDDING_STYLE_ASC,
	WEDDING_STYLE_DESC
    };

    /** デフォルト値 */
    public static final SalonClaimSort DEFAULT = DATE_DESC;

}
