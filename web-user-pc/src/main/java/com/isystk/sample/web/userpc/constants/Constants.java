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
    /** 日付が決定していない場合の表示文言 */
    public static final String USER_PC_DATE_UNFIX = "未定";

    /** AjaxのMIMEタイプ */
    public static final String MIME_TYPE_AJAX = "application/json";

    /** サジェストの表示件数上限 */
    public static final int LIMIT_SUGGEST_KEYWORD = 10;

    /** パスワードの長さ・最大値 */
    public static final Integer PASSEWORD_MAX_LENGTH = 15;

    /** パスワードの長さ・最小値 */
    public static final Integer PASSEWORD_MIN_LENGTH = 8;

    /** 生年月日デフォルト値 */
    public static final String BIRTHDAY_DEFAULT_YEAR = "1985";

    /** 人気キーワードの表示件数上限 */
    public static final int LIMIT_RANKING = 6;

    /** フリーワード */
    public static final int MAXLENGTH_FREEWORD = 100;
    
    //*************************************************
    // メール送信処理　メールId
    //*************************************************
    /** 会員仮登録完了メール */
    public static final String MAIL01 = "01";
    /** 会員本登録完了メール */
    public static final String MAIL02 = "02";
    /** 資料請求サンクスメール */
    public static final String MAIL05 = "05";
    /** BF予約サンクスメール */
    public static final String MAIL06 = "06";
    /** お問い合わせサンクスメール */
    public static final String MAIL08 = "08";
    /** 資料請求通知メール */
    public static final String MAIL09 = "09";
    /** BF予約通知メール */
    public static final String MAIL10 = "10";
    /** お問い合わせ通知メール */
    public static final String MAIL12 = "12";
    /** お問い合わせ空き確認通知メール */
    public static final String MAIL13 = "13";

    //*************************************************
    // レコメンド関連
    //*************************************************
    /** サンクスメールレコメンド情報表示件数 */
    public static final String NUMBER_OF_RECOMMENDATION_WEDDING_FOR_THANKS = "3";
    /** 各ページレコメンド情報表示件数 */
    public static final String NUMBER_OF_RECOMMENDATION_WEDDING_FOR_FRONT = "4";

    /** ログイン成功時に一時的に付与されるクッキー */
    public static final String LOGIN_SUCCESS_COOKIE = "_LSC";
    /** ログイン成功時に一時的に付与されるクッキー（ログイン成功後の初めてのリクエストかどうか判定することができる） */
    public static final String LOGIN_SUCCESS_REQUEST = "_LSR";

    //*************************************************
    // イベント取得用キー
    //*************************************************

    /** CvCRMイベント取得用のキー */
    public static final String EVENT_CVCRM_KEY = "_CVCRM";

    //*************************************************
    // その他（画面特有のもの）
    //*************************************************

    /** 【ブライダルフェア詳細画面】 */
    public static final String FAIR_INDEX_NO_LIMIT_CHAR = "期限なし";

    /** 【3カ月以内のお得なプラン一覧画面】【3カ月以内のお得なプラン詳細画面】カレンダー表示対象月の数 */
    public static final Integer LATEST_PLAN_VACANCY_CALENDAR_COUNT = 4;

    /** 【初回ログイン入力】画面名文言 */
    public static final String LOGIN_FIRST_SCREEN_NAME = "初回ログイン";

    /** 【初回ログイン入力】メルマガの文言 */
    public static final String LOGIN_FIRST_MAIL_MAGAGINE_MESSAGE = "PCメールアドレスでメールマガジンを登録する";

    /** 【初回ログイン入力】メルマガの文言 */
    public static final String LOGIN_FIRST_MAIL_MAGAGINE_NO_MESSAGE = "PCメールアドレスでメールマガジンを登録しない";

    /** 【トップページ】プルダウンリゾートエリア用ID　＊北海道＊ */
    public static final Integer RESORT_AREA_ID_FOR_PULLDOWN_HOKKAIDO = 0;

    /** 【トップページ】プルダウンリゾートエリア用ID　＊軽井沢・甲信越＊ */
    public static final Integer RESORT_AREA_ID_FOR_PULLDOWN_KARUIZAWA_KOSHINNETSU = 1;

    /** 【トップページ】プルダウンリゾートエリア用ID　＊関東＊ */
    public static final Integer RESORT_AREA_ID_FOR_PULLDOWN_KANTO = 2;

    /** 【トップページ】プルダウンリゾートエリア用ID　＊沖縄＊ */
    public static final Integer RESORT_AREA_ID_FOR_PULLDOWN_OKINAWA = 3;

    /** マイナビウエディングサロン番号掲載の文言 */
    public static final String ADMINSALON_TEL_PUBLICATION_MESSAGE = "※マイナビウエディングサロンにて\nご手配いたします（火曜定休）";

    /** マイナビウエディングサロン番号掲載の文言 */
    public static final String ADMINSALON_TEL_PUBLICATION_MESSAGE_ONELINE = "※マイナビウエディングサロンにて ご手配いたします（火曜定休）";

    /** マイナビウエディングサロン番号掲載の文言 */
    public static final String ADMINSALON_TEL_PUBLICATION_MESSAGE_BRDAY = "※マイナビウエディングサロンにてご手配いたします\n（火曜定休）";

    //*************************************************
    // ランキング 式場画面 URL文字列
    //*************************************************
    /** ランキング 式場画面 URL文字列 Tポイント対象会場 */
    public static final String RANKING_WEDDING_URLWORD_TPOINT = "tpoint";

    /** ランキング 式場画面 URL文字列 パノラマあり */
    public static final String RANKING_WEDDING_URLWORD_PANORAMA = "panorama";

    /** ランキング 式場画面 URL文字列 直前オトクプランあり */
    public static final String RANKING_WEDDING_URLWORD_LATESTPLAN = "latestplan";

    /** ランキング 式場画面 URL文字列 写真充実 */
    public static final String RANKING_WEDDING_URLWORD_SUFFICLIENTIMAGE = "photog";
    
}
