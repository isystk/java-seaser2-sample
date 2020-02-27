package com.isystk.sample.common.constants.sort;

import com.isystk.sample.common.util.NumberUtil;

/**
 * メールテンプレート向けに作成したソート用のEnum
 */
public enum MailTemplateSort1 {

    ORDER_DESC_REGIST_TIME(1, "登録日の新しい順"), 
    ORDER_ASC_REGIST_TIME(2, "登録日の古い順"), 
    ORDER_ASC_MAIL_TEMPLATE_DIV(3, "メールテンプレート区分の昇順"), 
    ORDER_DESC_MAIL_TEMPLATE_DIV(4, "メールテンプレート区分の降順");

    private final int code;
    private final String value;

    MailTemplateSort1(int code, String value) {
	this.code = code;
	this.value = value;
    }

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public static MailTemplateSort1 get(String codeStr) {
	Integer code = NumberUtil.toInteger(codeStr);
	return (code != null) ? get(code.intValue()) : null;
    }

    public static MailTemplateSort1 get(int code) {
	for (MailTemplateSort1 value : values()) {
	    if (value.code == code) {
		return value;
	    }
	}
	return null;
    }

    /** JSPから取るための配列 */
    public static final MailTemplateSort1[] ITEMS = values();

    /** デフォルト値 */
    public static final MailTemplateSort1 DEFAULT = ORDER_ASC_REGIST_TIME;

}
