package com.isystk.sample.common.constants.entity;

/**
 * MaxLengthエンティティのMaxLength クラス
 *
 * 自動生成のため原則修正禁止!!
 * 
 * @author iseyoshitaka
 */
public class MaxLength {

    /** 日付の共通maxlength */
    public static final int DATE = 10;

    /** 手入力日付の共通maxlength */
    public static final int MANUAL_ENTRY_DATE = 8;

    /** DBに存在しない価格入力の共通maxlength */
    public static final int PRICE = 8;

    /** 残り期間の共通maxlength */
    public static final int LIMIT_DATE = 2;
    
    /** オプション申請の掲載枠数の共通maxlength */
    public static final int OPTION_REQUEST_COUNT = 2;

    /** 郵便番号の共通maxlength */
    public static final int POST_CODE = 8;
    
    /** パスワードの共通maxlength */
    public static final int PASSWORD_MAX = 15;
    /** パスワードの共通minlength */
    public static final int PASSWORD_MIN = 8;

    /** 投稿タグID */
    public static final int M_POST_TAG_POSTTAGID = 9;
    /** 名称 */
    public static final int M_POST_TAG_NAME = 20;
    /** 画像ID */
    public static final int T_IMAGE_IMAGEID = 9;
    /** 投稿ID */
    public static final int T_POST_POSTID = 9;
    /** 会員ID */
    public static final int T_POST_USERID = 9;
    /** タイトル */
    public static final int T_POST_TITLE = 100;
    /** 本文 */
    public static final int T_POST_TEXT = 500;
    /** 楽観チェック用バージョン */
    public static final int T_POST_VERSION = 19;
    /** 投稿ID */
    public static final int T_POST_IMAGE_POSTID = 9;
    /** 画像ID */
    public static final int T_POST_IMAGE_IMAGEID = 9;
    /** 投稿ID */
    public static final int T_POST_TAG_POSTID = 9;
    /** 投稿タグID */
    public static final int T_POST_TAG_POSTTAGID = 9;
    /** 会員ID */
    public static final int T_USER_USERID = 9;
    /** メールアドレス */
    public static final int T_USER_MAIL = 256;
    /** パスワード */
    public static final int T_USER_PASSWORD = 40;
    /** 姓 */
    public static final int T_USER_FAMILYNAME = 20;
    /** 名 */
    public static final int T_USER_NAME = 20;
    /** 姓（カナ） */
    public static final int T_USER_FAMILYNAMEKANA = 20;
    /** 名（カナ） */
    public static final int T_USER_NAMEKANA = 20;
    /** 郵便番号 */
    public static final int T_USER_ZIP = 11;
    /** 都道府県 */
    public static final int T_USER_PREFECTUREID = 9;
    /** 市区町村 */
    public static final int T_USER_AREA = 100;
    /** 町名番地 */
    public static final int T_USER_ADDRESS = 100;
    /** 建物名 */
    public static final int T_USER_BUILDING = 100;
    /** 電話番号 */
    public static final int T_USER_TEL = 13;
    /** 性別 */
    public static final int T_USER_SEX = 9;
    /** ステータス */
    public static final int T_USER_STATUS = 9;
    /** 楽観チェック用バージョン */
    public static final int T_USER_VERSION = 19;
    /** 会員ID */
    public static final int T_USER_ONETIME_PASS_USERID = 9;
    /** ワンタイムキー */
    public static final int T_USER_ONETIME_PASS_ONETIMEKEY = 32;
    /** 会員ID */
    public static final int T_USER_ONETIME_VALID_USERID = 9;
    /** ワンタイムキー */
    public static final int T_USER_ONETIME_VALID_ONETIMEKEY = 32;
}