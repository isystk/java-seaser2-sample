package com.isystk.sample.web.userpc.constants;

/**
 * フロントPC共通で使用される定数
 *
 * @author iseyoshitaka
 *
 */
public class Constants {

    //*************************************************
    // フロントPC 共通
    //*************************************************

    /** AjaxのMIMEタイプ */
    public static final String MIME_TYPE_AJAX = "application/json";

    /** パスワードの長さ・最大値 */
    public static final Integer PASSEWORD_MAX_LENGTH = 15;

    /** パスワードの長さ・最小値 */
    public static final Integer PASSEWORD_MIN_LENGTH = 8;

    /** 生年月日デフォルト値 */
    public static final String BIRTHDAY_DEFAULT_YEAR = "1985";

    //*************************************************
    // メール送信処理　メールId
    //*************************************************
    /** 会員仮登録完了メール */
    public static final String MAIL01 = "01";
    /** 会員本登録完了メール */
    public static final String MAIL02 = "02";

}
