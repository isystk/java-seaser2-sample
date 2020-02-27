package com.isystk.sample.web.userpc.constants.select;

import com.isystk.sample.common.util.NumberUtil;

/**
 * マイページのまとめて操作プルダウンで使用するEnum
 * 
 * @author iseyoshitaka
 * 
 */
public enum CollectOperateSelectOptions {

    /** お気に入りから削除する */
    DELETE_CLIP(1, "お気に入りから削除する");

    private final int code;
    private final String value;

    CollectOperateSelectOptions(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static CollectOperateSelectOptions get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static CollectOperateSelectOptions get(int code) {
	for (CollectOperateSelectOptions value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** JSPから取るための配列 */
    public static final CollectOperateSelectOptions[] ITEMS = values();

    /** デフォルト値 */
    public static final CollectOperateSelectOptions DEFAULT = DELETE_CLIP;
}
