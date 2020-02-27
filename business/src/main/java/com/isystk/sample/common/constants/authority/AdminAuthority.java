/**
 * Copyright(c) team-lab</br>
 */
package com.isystk.sample.common.constants.authority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 管理者の権限
 * 
 * @author nkawamata
 */
public enum AdminAuthority implements BaseAuthority {

    /** なし */
    NONE(AccountAuthority.NONE),

    /** 全てのID発行権限 */
    ID_PUBLICATION(),

    /** クライアント一覧（式場） */
    CLIENT_LIST_WEDDING(),

    /** クライアント一覧（ジュエリー） */
    CLIENT_LIST(),

    /** クライアント閲覧 */
    CLIENT_READ(),

    /** クライアント登録 */
    CLIENT_REGIST(),

    /** クライアント修正 */
    CLIENT_EDIT(),

    /** クライアントパスワードリセット権限　 */
    CLIENT_PASSWORD_RESET(),

    /** クライアントアカウント出力権限 */
    CLIENT_ACCOUNT_OUTPUT(),

    /** クライアントアカウント出力権限(ウェディング) */
    CLIENT_ACCOUNT_OUTPUT_WEDDING(),

    /** 会員一覧 */
    USER_LIST(),

    /** 会員閲覧 */
    USER_READ(),

    /** 屋号登録 */
    WEDDING_REGIST(),

    /** 屋号一覧権限 */
    WEDDING_LIST(),

    /** 屋号修正 */
    WEDDING_EDIT(),

    /** 屋号削除 */
    WEDDING_DELETE(),

    /** 屋号BP連携承認 */
    WEDDING_ACCEPT_BP(),

    /** 手配会社登録 */
    AGENT_REGIST(),

    /** 手配会社一覧権限 */
    AGENT_LIST(),

    /** 手配会社修正 */
    AGENT_EDIT(),

    /** 手配会社削除 */
    AGENT_DELETE(),

    /** 掲載枠登録（屋号選択一覧）権限 */
    REQUEST_SELECT_LIST(),

    /** 掲載枠一覧権限 */
    REQUEST_LIST(),

    /** 掲載枠閲覧権限 */
    REQUEST_READ(),

    /** 掲載指示の登録 */
    REQUEST_REGIST(),

    /** 掲載指示の修正 */
    REQUEST_EDIT(),

    /** 掲載指示の承認 */
    REQUEST_ACCEPT(),

    /** 承認済み掲載指示の修正 */
    REQUEST_ACCEPT_EDIT(),

    /** 地図アクセス一覧権限 */
    ACCESS_LIST(),

    /** 地図アクセス登録権限 */
    ACCESS_REGIST(),

    /** 地図アクセス修正権限 */
    ACCESS_EDIT(),

    /** 地図アクセス削除権限 */
    ACCESS_DELETE(),

    /** オプション掲載枠一覧権限 */
    OPTION_REQUEST_LIST(),

    /** オプション掲載枠閲覧権限 */
    OPTION_REQUEST_READ(),

    /** オプション指示の登録 */
    OPTION_REQUEST_REGIST(),

    /** オプション指示の承認 */
    OPTION_REQUEST_ACCEPT(),

    /** 承認済みオプション指示の修正 */
    OPTION_REQUEST_ACCEPT_EDIT(),

    /** 更新パトロール */
    PATROL_AUTH(),

    /** 会員管理 */
    USER_AUTH(),

    /** マイポ管理 */
    MYPO_AUTH(),

    /** PR枠一覧権限 */
    PR_REQUEST_LIST(),

    /** PR枠閲覧権限 */
    PR_REQUEST_READ(),

    /** PR指示の登録 */
    PR_REQUEST_REGIST(),

    /** PR指示の承認 */
    PR_REQUEST_ACCEPT(),

    /** 承認済みPR指示の修正 */
    PR_REQUEST_ACCEPT_EDIT(),

    /** 特集の修正 */
    FEATURE_AUTH(),

    /** 先輩カップル一覧権限 */
    PEOPLE_LIST(),

    /** 先輩カップル閲覧権限 */
    PEOPLE_READ(),

    /** 先輩カップル承認権限 */
    PEOPLE_APPROVAL(),

    /** 先輩カップル修正権限 */
    PEOPLE_EDIT(),

    /** 更新パトロール一覧権限 */
    PATROL_LIST(),

    /** フォト共有アプリ一覧権限 */
    PHOTOAPPLI_LIST(),

    /** テイスト別TOP一覧権限 */
    TASTETOP_LIST(),

    /** テイスト別TOP会場一覧権限 */
    TASTETOP_WEDDING_LIST(),

    /** キャッシュクリア権限 */
    CACHE_CLEAR(),

    /** チャペル登録 */
    CHAPEL_REGIST(),

    /** チャペル一覧 */
    CHAPEL_LIST(),

    /** チャペル修正 */
    CHAPEL_EDIT(),

    /** チャペル削除 */
    CHAPEL_DELETE(),

    /** 掲載枠一覧権限 */
    RING_REQUEST_LIST(),

    /** 掲載枠閲覧権限 */
    RING_REQUEST_READ(),

    /** 掲載枠登録権限 */
    RING_REQUEST_REGIST(),

    /** 掲載枠承認権限 */
    RING_REQUEST_APPROVAL(),

    /** 掲載枠修正権限 */
    RING_REQUEST_EDIT(),
    
    /** プラチナプラン掲載枠一覧権限 */
    RING_PLATINUM_REQUEST_LIST(),

    /** プラチナプラン掲載枠閲覧権限 */
    RING_PLATINUM_REQUEST_READ(),

    /** プラチナプラン掲載枠登録権限 */
    RING_PLATINUM_REQUEST_REGIST(),

    /** プラチナプラン掲載枠承認権限 */
    RING_PLATINUM_REQUEST_APPROVAL(),

    /** プラチナプラン掲載枠修正権限 */
    RING_PLATINUM_REQUEST_EDIT(),

    /** 360度商品画像掲載枠一覧権限 */
    RING_OBJECTVR_REQUEST_LIST(),

    /** 360度商品画像掲載枠閲覧権限 */
    RING_OBJECTVR_REQUEST_READ(),

    /** 360度商品画像掲載枠登録権限 */
    RING_OBJECTVR_REQUEST_REGIST(),

    /** 360度商品画像掲載枠承認権限 */
    RING_OBJECTVR_REQUEST_APPROVAL(),

    /** 360度商品画像掲載枠修正権限 */
    RING_OBJECTVR_REQUEST_EDIT(),

    /** スペシャルレポート掲載枠一覧権限 */
    RING_SPECIAL_REPORT_LIST(),

    /** スペシャルレポート掲載枠閲覧権限 */
    RING_SPECIAL_REPORT_READ(),

    /** スペシャルレポート掲載枠登録権限 */
    RING_SPECIAL_REPORT_REGIST(),

    /** スペシャルレポート掲載枠承認権限 */
    RING_SPECIAL_REPORT_APPROVAL(),

    /** スペシャルレポート掲載枠修正権限 */
    RING_SPECIAL_REPORT_EDIT(),

    /** クチコミ一覧権限 */
    RING_REVIEW_LIST(),

    /** クチコミ閲覧権限 */
    RING_REVIEW_READ(),

    /** クチコミ登録権限 */
    RING_REVIEW_REGIST(),

    /** クチコミ承認権限 */
    RING_REVIEW_APPROVAL(),

    /** クチコミ修正権限 */
    RING_REVIEW_EDIT(),

    /** オプション掲載枠一覧権限 */
    RING_OPTION_REQUEST_LIST(),

    /** オプション掲載枠閲覧権限 */
    RING_OPTION_REQUEST_READ(),

    /** オプション掲載枠登録権限 */
    RING_OPTION_REQUEST_REGIST(),

    /** オプション掲載枠承認権限 */
    RING_OPTION_REQUEST_APPROVAL(),

    /** オプション掲載枠修正権限 */
    RING_OPTION_REQUEST_EDIT(),

    /** PR枠一覧権限 */
    PR_LIST(),

    /** PR枠閲覧権限 */
    PR_READ(),

    /** PR枠登録権限 */
    PR_REGIST(),

    /** PR枠承認権限 */
    PR_APPROVAL(),

    /** PR枠修正権限 */
    PR_EDIT(),

    /** ブランド一覧権限 */
    BRAND_LIST(),

    /** ブランド閲覧権限 */
    BRAND_READ(),

    /** ブランド登録権限 */
    BRAND_REGIST(),

    /** ブランド修正権限 */
    BRAND_EDIT(),

    /** ブランド削除権限 */
    BRAND_DELETE(),

    /** 店舗一覧権限 */
    SHOP_LIST(),

    /** 店舗閲覧権限 */
    SHOP_READ(),

    /** 店舗登録権限 */
    SHOP_REGIST(),

    /** 店舗掲載権限 */
    SHOP_PUBLICATION(),

    /** 店舗修正権限 */
    SHOP_EDIT(),

    /** 店舗削除権限 */
    SHOP_DELETE(),

    /** コンバージョン出力権限 */
    CONVERSION_OUTPUT(),

    /** 外部連携ファイルダウンロード権限 */
    RING_EXTENDTOOL(),

    /** 外部連携ファイルダウンロード権限（CV系） */
    RING_EXTENDTOOL_CV(),
    
    /** エリア別コンバージョン出力権限 */
    AREA_CONVERSION_OUTPUT(),

    /** 掲載ログ確認権限 */
    RING_PUBLICATION_LOG(),

    /** 反響レポート（ウエディング） */
    GAREPORT(),

    /** 反響レポート（ジュエリー） */
    RING_GAREPORT(),

    /** 月額コスト登録（ジュエリー） */
    RING_CPAREGIST(),

    /** ランキング一覧権限 */
    RANKING_LIST(),

    /** ランキング登録権限 */
    RANKING_REGIST(),

    /** ランキング修正権限 */
    RANKING_EDIT(),

    /** ランキング削除権限 */
    RANKING_DELETE(),

    /** Adminアカウント管理 */
    ADMIN_ACCOUNT_AUTH(),

    /** 完了メール一覧権限 */
    THANKS_MAIL_LIST(),

    /** 完了メールプレビュー権限 */
    THANKS_MAIL_PREVIEW(),

    /** メールマガジンデータ出力 */
    MAGAZINE_DATA_EXPORT(),

    /** 検索レート設定権限 */
    SEARCHRATE_AUTH(),

    /** エリア設定権限 */
    SEARCHAREA_AUTH(),

    /** 縮小版フラグ設定権限 */
    MINI_FLG_AUTH(),
    
    /** Tポイン編集 */
    TPOINT_EDIT(),

    /** Tポイン付与一覧閲覧権限 */
    TPOINT_LIST(),

    /** Tポイン請求一覧閲覧権限 */
    TPOINT_BILL_LIST(),

    /** CV者向けメールテンプレート閲覧権限 */
    CV_MAIL_TEMPLATE_READ(),

    /** CV者向けメールテンプレート修正権限 */
    CV_MAIL_TEMPLATE_EDIT(),

    /** ランキングPV増減値設定閲覧権限 */
    RANKPV_INCDEC_READ(),

    /** ランキングPV増減値設定登録権限 */
    RANKPV_INCDEC_REGIST(),

    /** ランキングPV増減値設定削除権限 */
    RANKPV_INCDEC_DELETE(),

    /** 商品PV増減値設定閲覧権限 */
    JEWELRY_RANKPV_INCDEC_READ(),

    /** 商品PV増減値設定登録権限 */
    JEWELRY_RANKPV_INCDEC_REGIST(),

    /** 商品PV増減値設定削除権限 */
    JEWELRY_RANKPV_INCDEC_DELETE(),

    /** リダイレクト設定権限 */
    REDIRECT_SET(),

    /** サロン成果確認権限 */
    SALON_RESULT(),
    
    /** サロン成果お客様氏名確認権限 */
    SALON_RESULT_PERSONAL_INFOMATION(),

    /** クライアント一覧（クロスセル） */
    CLIENT_LIST_CROSSSELL(),

    /** クライアント閲覧（クロスセル） */
    CLIENT_READ_CROSSSELL(),
    
    /** クライアント登録（クロスセル） */
    CLIENT_REGIST_CROSSSELL(),
    
    /** クライアント修正（クロスセル） */
    CLIENT_EDIT_CROSSSELL(),

    /** 請求確認（クロスセル） */
    CLAIM_LIST_CROSSSELL(),
    
    /** アンケートデータ出力 */
    ENQUETE_DATA_EXPORT(),
    
    /** アンケートデータ出力(管理権限) */
    ENQUETE_DATA_ADMIN_EXPORT();
    

    /** すべての権限を持つアカウントの表現文字列 */
    public static final String SUPER_ADMIN = "*";

    /**
     * nameで権限を見つけます。
     * 
     * @param name 権限名
     * @return 権限
     */
    public static AdminAuthority find(String name) {
	if (name == null) {
	    return null;
	}
	for (AdminAuthority a : values()) {
	    if (a.name().equals(name)) {
		return a;
	    }
	}
	return null;
    }

    /**
     * 権限を持っているかどうかを判定します
     * 
     * @param authes 権限の集合
     * @param auth 権限
     * @return 持っているときtrue
     */
    public static boolean hasAuthority(AdminAuthority[] authes, AdminAuthority auth) {

	if (auth == null) {
	    throw new IllegalArgumentException();
	}

	if (NONE.equals(auth)) {
	    return true;
	}

	if (authes == null || authes.length <= 0) {
	    return false;
	}

	for (AdminAuthority a : authes) {
	    if (a == null) {
		continue;
	    }
	    if (a.equals(auth)) {
		return true;
	    }
	}
	return false;
    }

    /**
     * AccountAuthorityに変換します.
     * 
     * @param authes AdminAuthority[]
     * @return AccountAuthority[]
     */
    public static AccountAuthority[] toAccountAuthority(AdminAuthority[] authes) {

	if (authes == null) {
	    throw new IllegalArgumentException();
	}

	Set<AccountAuthority> list = new HashSet<AccountAuthority>();
	for (AdminAuthority a : authes) {
	    if (a == null || a.account == null) {
		continue;
	    }
	    list.addAll(Arrays.asList(a.account));
	}

	AccountAuthority[] arr = list.toArray(new AccountAuthority[list.size()]);

	Arrays.sort(arr);
	return arr;

    }

    /**
     * @param authes　権限
     * @return 権限文字列
     */
    public static String toString(AdminAuthority[] authes) {
	if (authes == null || authes.length <= 0) {
	    return "";
	}

	boolean first = true;
	StringBuilder sb = new StringBuilder();
	for (AdminAuthority a : authes) {
	    if (a == null) {
		continue;
	    }
	    if (first) {
		first = false;
	    } else {
		sb.append(",");
	    }
	    sb.append(a.name());
	}

	return sb.toString();
    }

    /**
     * 文字列から権限を起こします
     * 
     * @param values 権限文字列
     * @return 権限
     */
    public static AdminAuthority[] valuesOf(String values) {
	if (values == null || values.length() <= 0) {
	    return EMPTY;
	}

	if (SUPER_ADMIN.equals(values)) {
	    return values();
	}

	List<AdminAuthority> list = new ArrayList<AdminAuthority>();
	for (String name : values.split("\\s*,\\s*")) {
	    AdminAuthority a = find(name.trim());
	    if (a == null) {
		continue;
	    }
	    list.add(a);
	}

	if (list.isEmpty()) {
	    return EMPTY;
	}

	AdminAuthority[] arr = list.toArray(new AdminAuthority[list.size()]);
	Arrays.sort(arr);

	return arr;
    }

    /** AccountAuthorityの対応する権限 */
    private final AccountAuthority[] account;

    /** 空 */
    private static final AdminAuthority[] EMPTY = new AdminAuthority[0];

    /**
     * @param account AccountAuthorityの対応する権限
     */
    private AdminAuthority(AccountAuthority... account) {
	this.account = account;
    }
}
