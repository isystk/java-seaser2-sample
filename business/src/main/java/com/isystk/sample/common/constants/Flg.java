package com.isystk.sample.common.constants;

import com.isystk.sample.common.util.NumberUtil;

/**
 * フラグ
 * 
 * SHOW_FLG：1表示、0非表示 
 * DELETE_FLG：1削除済み、0未削除 
 * SEND_MAIL_FLG：1送信済み、0未送信
 * SEND_MAIL_STATUS_FLG：1送信する、0送信しない 
 * INDEX_COMPLATE_FLG：1生成済み、0未生成
 * UNFIX_FLG：1未定、0決定
 * 
 * @author iseyoshitaka
 * 
 */
public enum Flg {

    ON(1, Boolean.TRUE.booleanValue()), OFF(0, Boolean.FALSE.booleanValue());

    private final int code;
    private final Boolean value;

    /**
     * コンストラクタ。
     * 
     * @param code コード値
     * @param value バリュー値
     */
    Flg(int code, Boolean value) {
	this.code = code;
	this.value = value;
    }

    /**
     * @return コード値を取得
     */
    public int getCode() {
	return code;
    }

    /**
     * @return バリュー値を取得
     */
    public Boolean getValue() {
	return value;
    }

    public static Flg get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static Flg get(Integer code) {
	if (code == null) {
	    return DEFAULT;
	}
	for (Flg value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return DEFAULT;
    }

    public static Flg get(Boolean bool) {
	if (bool == null) {
	    return Flg.OFF;
	}
	return bool ? Flg.ON : Flg.OFF;
    }

    public static final Flg[] ITEMS = values();

    public static final Flg DEFAULT = OFF;
}
