package com.isystk.sample.web.front.constants.select;

import com.isystk.sample.common.util.NumberUtil;

/**
 * 会費
 *
 * @author iseyoshitaka
 *
 */
public enum MembershipFeeSelectOptions {

    FEE_5000(5000, "5,000円"),
    FEE_6000(6000, "6,000円"),
    FEE_7000(7000, "7,000円"),
    FEE_8000(8000, "8,000円"),
    FEE_9000(9000, "9,000円"),
    FEE_10000(10000, "10,000円"),
    FEE_11000(11000, "11,000円"),
    FEE_12000(12000, "12,000円"),
    FEE_13000(13000, "13,000円"),
    FEE_14000(14000, "14,000円"),
    FEE_15000(15000, "15,000円"),
    FEE_16000(16000, "16,000円"),
    FEE_17000(17000, "17,000円"),
    FEE_18000(18000, "18,000円"),
    FEE_19000(19000, "19,000円"),
    FEE_20000(20000, "20,000円"),
    FEE_21000(21000, "21,000円"),
    FEE_22000(22000, "22,000円"),
    FEE_23000(23000, "23,000円"),
    FEE_24000(24000, "24,000円"),
    FEE_25000(25000, "25,000円"),
    FEE_26000(26000, "26,000円"),
    FEE_27000(27000, "27,000円"),
    FEE_28000(28000, "28,000円"),
    FEE_29000(29000, "29,000円"),
    FEE_30000(30000, "30,000円");

    private final int code;
    private final String value;

    MembershipFeeSelectOptions(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static MembershipFeeSelectOptions get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static MembershipFeeSelectOptions get(int code) {
	for (MembershipFeeSelectOptions value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** JSPから取るための配列 */
    public static final MembershipFeeSelectOptions[] ITEMS = values();

    /** デフォルト値 */
    public static final MembershipFeeSelectOptions DEFAULT = FEE_10000;

    /** 最小値 */
    public static final MembershipFeeSelectOptions MIN = FEE_5000;

    /** 最大値 */
    public static final MembershipFeeSelectOptions MAX = FEE_30000;

}
