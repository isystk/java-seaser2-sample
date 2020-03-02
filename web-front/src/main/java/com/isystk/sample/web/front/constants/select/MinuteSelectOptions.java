package com.isystk.sample.web.front.constants.select;

import java.util.ArrayList;
import java.util.List;

import com.isystk.sample.common.util.NumberUtil;

/**
 * 「分」プルダウンで使用するEnum
 */
public enum MinuteSelectOptions {

    /** 00 */
    _00(0, "00分"),
    /** 05 */
    _05(5, "05分"),
    /** 10 */
    _10(10, "10分"),
    /** 15 */
    _15(15, "15分"),
    /** 20 */
    _20(20, "20分"),
    /** 25 */
    _25(25, "25分"),
    /** 30 */
    _30(30, "30分"),
    /** 35 */
    _35(35, "35分"),
    /** 40 */
    _40(40, "40分"),
    /** 45 */
    _45(45, "45分"),
    /** 50 */
    _50(50, "50分"),
    /** 55 */
    _55(55, "55分");

    private final int code;
    private final String value;

    MinuteSelectOptions(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static MinuteSelectOptions get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static MinuteSelectOptions get(int code) {
	for (MinuteSelectOptions value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /**
     * ブライダルフェア 有効な開始時刻(分)を取得 10:30開始の場合30~55(5分刻み)を返す
     * 
     * @param minute
     * @return
     */
    public static List<MinuteSelectOptions> getReceptionStartMinuteValues(MinuteSelectOptions minute) {
	List<MinuteSelectOptions> resultList = new ArrayList<MinuteSelectOptions>();
	MinuteSelectOptions[] values = MinuteSelectOptions.values();
	for (int i = 0; i < values.length; i++) {
	    MinuteSelectOptions item = values[i];
	    if (item.code == minute.code) {
		for (int j = i; j < values.length; j++) {
		    resultList.add(values[j]);
		}
		break;
	    }
	}
	return resultList;
    }

    /**
     * ブライダルフェア 有効な終了時刻(分)を取得 16:15の場合0~15(5分刻み)を返す
     * 
     * @param minute
     * @return
     */
    public static List<MinuteSelectOptions> getReceptionEndMinuteValues(MinuteSelectOptions minute) {
	List<MinuteSelectOptions> resultList = new ArrayList<MinuteSelectOptions>();
	MinuteSelectOptions[] values = MinuteSelectOptions.values();
	for (int i = 0; i < values.length; i++) {
	    MinuteSelectOptions item = values[i];
	    if (item.code == minute.code) {
		for (int j = 0; j <= i; j++) {
		    resultList.add(values[j]);
		}
		break;
	    }
	}
	return resultList;
    }

    /** JSPから取るための配列 */
    public static final MinuteSelectOptions[] ITEMS = values();

    /** デフォルト値 */
    public static final MinuteSelectOptions DEFAULT = _00;

}