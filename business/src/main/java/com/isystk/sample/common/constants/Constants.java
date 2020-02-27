package com.isystk.sample.common.constants;

import com.isystk.sample.common.constants.authority.AdminAuthorityDiv;
import com.isystk.sample.common.constants.authority.ClientAuthorityDiv;

/**
 * 各プロジェクト共通で使用される定数
 * 
 * @author iseyoshitaka
 * 
 */
public class Constants {

    /** クライアント登録時に発行されるクライアントアクントの権限ID */
//    public static final Integer MAIN_CLIENT_ACCOUNT_AUTH_ID = 11;
    public static final Integer MAIN_CLIENT_ACCOUNT_AUTH_ID = ClientAuthorityDiv.COMPANY_OFFICER.getClientAccountAuthId();

    /** クライアント登録時に発行されるクライアントアクントの権限ID(ジュエリー) */
//    public static final Integer DJ_MAIN_CLIENT_ACCOUNT_AUTH_ID = 101;
    public static final Integer DJ_MAIN_CLIENT_ACCOUNT_AUTH_ID = ClientAuthorityDiv.RING_COMPANY_OFFICER.getClientAccountAuthId();

    /** 直近プラン空席状況画面に表示するカレンダーの数 */
    public static final Integer LATEST_PLAN_VACANCY_CALENDAR_COUNT = 6;

    /** プラン管理画面制御識別子 */
    public static final String PLAN_LATESTPLAN_DISPLAY_KBN_SELECT = "select";

    /** Adminダッシュボード　最新の10件検索用定数 */
    public static final Integer ADMIN_SEARCH_LIMIT = 10;
    public static final Integer ADMIN_SEARCH_OFFSET = 0;

    /** 権限：営業 */
//    public static final int BUSINESS_ADMIN_AUTH_ID = 2;
    public static final int BUSINESS_ADMIN_AUTH_ID = AdminAuthorityDiv.EIGYO.getAdminAuthId();

    /** 権限：営業mgr(承認者) */
//    public static final int BUSINESS_MGR_ADMIN_AUTH_ID = 3;
    public static final int BUSINESS_MGR_ADMIN_AUTH_ID = AdminAuthorityDiv.EIGYO_MANAGER.getAdminAuthId();

    /** 【Client】ダッシュボード カレンダー表示数 */
    public static final int CLIENT_CALENDAR_DISP_FOUR = 4;
    /** 【Client】ダッシュボード 月固定文言 */
    public static final String CLIENT_MONTH_LABEL = "月";

    /** JSONのMIMEタイプ */
    public static final String MIME_TYPE_JSON = "application/json";

    /** ログイン成功時に一時的に付与されるクッキー */
    public static final String LOGIN_SUCCESS_COOKIE = "_LSC";
    /** ログイン成功時に一時的に付与されるクッキー（ログイン成功後の初めてのリクエストかどうか判定することができる） */
    public static final String LOGIN_SUCCESS_REQUEST = "_LSR";

    /** プレミアムまとめ用画像イメージディレクトリ名 */
    public static final String PREMIUM_FEATURE_IMAGE_DIR = "feature";

    /** 新着情報のHTMLのファイル名 */
    public static final String LATEST_INFORMATION_FILE_NAME = "latestInformation.html";
    /** 直後メール内のHTMLのファイル名（フェア） */
    public static final String AFTER_MAIL_FAIR_GREETING_FILE_NAME = "afterMailFairGreeting.html";
    public static final String AFTER_MAIL_FAIR_TYPE2_FILE_NAME = "afterMailFairType2.html";
    public static final String AFTER_MAIL_FAIR_EDITORIAL_FILE_NAME = "afterMailFairEditorial.html";
    /** 直後メール内のHTMLのファイル名（見学） */
    public static final String AFTER_MAIL_VISIT_GREETING_FILE_NAME = "afterMailVisitGreeting.html";
    public static final String AFTER_MAIL_VISIT_TYPE2_FILE_NAME = "afterMailVisitType2.html";
    public static final String AFTER_MAIL_VISIT_EDITORIAL_FILE_NAME = "afterMailVisitEditorial.html";

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
    
    /**
     * 代行ログインにまつわるパラーメータ郡.<br>
     * 
     * @author nkawamata
     */
    public interface Vicarious {
	/** アクションのパス */
	String CLIENT_ACTION = "/client/login";
	/** アクションのメソッド */
	String CLIENT_ACTION_METHOD = "loginVicarious";
	/** URLパラメータ */
	String PARAMETER = "param";
	/** クッキーパス */
	String COOKIE_PATH = CLIENT_ACTION;
	/** クッキー名 */
	String COOKIE = "VICARIOUS";
    }

    /**
     * ジュエリードレス用ログインにまつわるパラーメータ郡.<br>
     * 
     */
    public interface Ring {
	/** クッキーパス */
	String COOKIE_PATH = "/";
	/** クッキー名 */
	String COOKIE_ADMIN = "RING_ADMIN";
	/** クッキー名 */
	String COOKIE_PC = "RING_PC";
	/** クッキー名 */
	String COOKIE_SP = "RING_SP";
    }

    /**
     * トラッキングIDにまつわるパラメータ群.<br>
     * 
     */
    public interface Tracking {
	/** クッキーパス */
	String COOKIE_PATH = "/";
	/** クッキー名 */
	String COOKIE_NAME = "TRACKING_ID";
	/** 生存時間(2年) */
	Integer COOKIE_AGE = 31536000 * 2; // 2年
    }

}
