package com.isystk.sample.web.front.constants.select;

import com.isystk.sample.common.util.NumberUtil;

public enum AgentPhotoSortSelect {

    SORT_RANKING_ASC(1, "人気順"), SORT_REGIST_DESC(2, "新着順");

    private final int code;
    private final String value;

    AgentPhotoSortSelect(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static AgentPhotoSortSelect get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static AgentPhotoSortSelect get(int code) {
	for (AgentPhotoSortSelect value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** JSPから取るための配列 */
    public static final AgentPhotoSortSelect[] ITEMS = values();

    /** デフォルト値 */
    public static final AgentPhotoSortSelect DEFAULT = SORT_RANKING_ASC;
}
