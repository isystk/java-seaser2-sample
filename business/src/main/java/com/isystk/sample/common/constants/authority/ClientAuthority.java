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
public enum ClientAuthority implements BaseAuthority {

    /** なし */
    NONE(AccountAuthority.NONE),

    /** アカウント発行 */
    ACCOUNT_REGIST(),

    /** アカウント参照 */
    ACCOUNT_VIEW(),

    /** アカウント修正 */
    ACCOUNT_EDIT(),

    /** アカウント削除 */
    ACCOUNT_DELETE(),

    /** 承認依頼権限 */
    ACCEPT_REQUEST(),

    /** 校了権限 */
    COMPLATE_AUTH(),

    /** A/Bテスト用校了原稿作成権限 */
    ABTEST_COMPLATE_REGIST_AUTH(),

    /** A/Bテスト用校了権限 */
    ABTEST_COMPLATE_AUTH(),

    /** A/Bテスト用メイン校了権限 */
    ABTEST_COMPLATE_MAIN_AUTH(),

    /** A/Bテスト用承認依頼原稿作成権限 */
    ABTEST_ACCEPT_REGIST_REQUEST(),

    /** A/Bテスト用承認依頼権限 */
    ABTEST_ACCEPT_REQUEST(),

    /** A/Bテスト用掲載権限 */
    ABTEST_PUBLISH_AUTH(),

    /** 反響した個人情報のDL */
    CUSTOMER_DL(),

    /** サブエリア設定 */
    SUB_REGION_EDIT(),

    /** サブ会場タイプ設定 */
    SUB_HALLTYPE_EDIT(),

    /** ホームページアドレス設定 */
    HOMEPAGE_EDIT(),

    /** 特集会場タグ */
    FEATURE_WEDDING_TAG_EDIT(),

    /** ブランドPR枠一覧権限 */
    BRAND_PR_LIST(),

    /** ブランドPR枠閲覧権限 */
    BRAND_PR_READ(),

    /** ブランドPR枠登録権限 */
    BRAND_PR_REGIST(),

    /** ブランドPR枠編集権限 */
    BRAND_PR_EDIT(),

    /** ブランドPR枠削除権限 */
    BRAND_PR_DELETE(),

    /** ダッシュボード一覧権限 */
    DASHBORD_LIST(),

    /** クライアントアカウント一覧権限 */
    CLIENT_ACCOUNT_LIST(),

    /** クライアントアカウント閲覧権限 */
    CLIENT_ACCOUNT_READ(),

    /** クライアントアカウント登録権限 */
    CLIENT_ACCOUNT_REGIST(),

    /** クライアントアカウント修正権限 */
    CLIENT_ACCOUNT_EDIT(),

    /** クライアントアカウント削除権限 */
    CLIENT_ACCOUNT_DELETE(),

    /** お客様対応一覧権限 */
    CUSTOMER_ANSWER_LIST(),

    /** 来店予約一覧権限 */
    VISIT_LIST(),

    /** 来店予約閲覧権限 */
    VISIT_READ(),

    /** 来店予約登録権限 */
    VISIT_REGIST(),

    /** 来店予約対応権限 */
    VISIT_ANSWER(),

    /** 資料請求一覧権限 */
    DOCUMENT_LIST(),

    /** 資料請求閲覧権限 */
    DOCUMENT_READ(),

    /** 資料請求ラベル印刷権限 */
    DOCUMENT_LABEL_PRINT(),

    /** 資料請求登録権限 */
    DOCUMENT_REGIST(),

    /** 資料請求対応権限 */
    DOCUMENT_ANSWER(),

    /** お問い合わせ一覧権限 */
    INQUIRY_LIST(),

    /** お問い合わせ閲覧権限 */
    INQUIRY_READ(),

    /** お問い合わせ登録権限 */
    INQUIRY_REGIST(),

    /** お問い合わせ対応権限 */
    INQUIRY_ANSWER(),

    /** 直前メール一覧権限 */
    REMIND_MAIL_LIST(),

    /** 直前メール送信権限 */
    REMIND_MAIL_SEND(),

    /** メールテンプレート一覧権限 */
    MAIL_TEMPLATE_LIST(),

    /** メールテンプレート閲覧権限 */
    MAIL_TEMPLATE_READ(),

    /** メールテンプレート登録権限 */
    MAIL_TEMPLATE_REGIST(),

    /** メールテンプレート修正権限 */
    MAIL_TEMPLATE_EDIT(),

    /** メールテンプレート削除権限 */
    MAIL_TEMPLATE_DELETE(),

    /** ジュエリー一覧権限 */
    JEWELRY_LIST(),

    /** ジュエリー閲覧権限 */
    JEWELRY_READ(),

    /** ジュエリー登録権限 */
    JEWELRY_REGIST(),

    /** ジュエリー承認依頼権限 */
    JEWELRY_APPROVAL_REQUEST(),

    /** ジュエリー承認依頼登録権限 */
    JEWELRY_APPROVAL_REQUEST_REGIST(),

    /** ジュエリー校了権限 */
    JEWELRY_FINISHING_PROOF(),

    /** ジュエリー校了登録権限 */
    JEWELRY_FINISHING_PROOF_REGIST(),

    /** ジュエリー代行権限 */
    JEWELRY_MYNV_AGENT(),

    /** ジュエリー代行登録権限 */
    JEWELRY_MYNV_AGENT_REGIST(),

    /** ジュエリー修正権限 */
    JEWELRY_EDIT(),

    /** ジュエリー削除権限 */
    JEWELRY_DELETE(),

    /** ジュエリーテンプレート一覧権限 */
    JEWELRY_TEMPLATE_LIST(),

    /** ジュエリーテンプレート閲覧権限 */
    JEWELRY_TEMPLATE_READ(),

    /** ジュエリーテンプレート登録権限 */
    JEWELRY_TEMPLATE_REGIST(),

    /** ジュエリーテンプレート代行権限 */
    JEWELRY_TEMPLATE_MYNV_AGENT(),

    /** ジュエリーテンプレート代行登録権限 */
    JEWELRY_TEMPLATE_MYNV_AGENT_REGIST(),

    /** ジュエリーテンプレート修正権限 */
    JEWELRY_TEMPLATE_EDIT(),

    /** ジュエリーテンプレート削除権限 */
    JEWELRY_TEMPLATE_DELETE(),

    /** ブランドお知らせ一覧権限 */
    BRAND_INFO_LIST(),

    /** ブランドお知らせ閲覧権限 */
    BRAND_INFO_READ(),

    /** ブランドお知らせ登録権限 */
    BRAND_INFO_REGIST(),

    /** ブランドお知らせ承認依頼権限 */
    BRAND_INFO_APPROVAL_REQUEST(),

    /** ブランドお知らせ校了権限 */
    BRAND_INFO_FINISHING_PROOF(),

    /** ブランドお知らせ修正権限 */
    BRAND_INFO_EDIT(),

    /** ブランドお知らせ削除権限 */
    BRAND_INFO_DELETE(),

    /** ブランド原稿一覧権限 */
    BRAND_LIST(),

    /** ブランド原稿閲覧権限 */
    BRAND_READ(),

    /** ブランド原稿登録権限 */
    BRAND_REGIST(),

    /** ブランド原稿承認依頼権限 */
    BRAND_APPROVAL_REQUEST(),

    /** ブランド原稿承認依頼登録権限 */
    BRAND_APPROVAL_REQUEST_REGIST(),

    /** ブランド原稿校了権限 */
    BRAND_FINISHING_PROOF(),

    /** ブランド原稿校了登録権限 */
    BRAND_FINISHING_PROOF_REGIST(),

    /** ブランド原稿代行権限 */
    BRAND_MYNV_AGENT(),

    /** ブランド原稿代行登録権限 */
    BRAND_MYNV_AGENT_REGIST(),

    /** ブランド原稿修正権限 */
    BRAND_EDIT(),

    /** ブランド原稿削除権限 */
    BRAND_DELETE(),

    /** ブランド原稿テンプレート一覧権限 */
    BRAND_TEMPLATE_LIST(),

    /** ブランド原稿テンプレート閲覧権限 */
    BRAND_TEMPLATE_READ(),

    /** ブランド原稿テンプレート登録権限 */
    BRAND_TEMPLATE_REGIST(),

    /** ブランド原稿テンプレート代行権限 */
    BRAND_TEMPLATE_MYNV_AGENT(),

    /** ブランド原稿テンプレート代行登録権限 */
    BRAND_TEMPLATE_MYNV_AGENT_REGIST(),

    /** ブランド原稿テンプレート修正権限 */
    BRAND_TEMPLATE_EDIT(),

    /** ブランド原稿テンプレート削除権限 */
    BRAND_TEMPLATE_DELETE(),

    /** 店舗お知らせ一覧権限 */
    SHOP_INFO_LIST(),

    /** 店舗お知らせ閲覧権限 */
    SHOP_INFO_READ(),

    /** 店舗お知らせ登録権限 */
    SHOP_INFO_REGIST(),

    /** 店舗お知らせ承認依頼権限 */
    SHOP_INFO_APPROVAL_REQUEST(),

    /** 店舗お知らせ校了権限 */
    SHOP_INFO_FINISHING_PROOF(),

    /** 店舗お知らせ修正権限 */
    SHOP_INFO_EDIT(),

    /** 店舗お知らせ削除権限 */
    SHOP_INFO_DELETE(),

    /** 店舗原稿一覧権限 */
    SHOP_LIST(),

    /** 店舗原稿閲覧権限 */
    SHOP_READ(),

    /** 店舗原稿登録権限 */
    SHOP_REGIST(),

    /** 店舗原稿承認依頼権限 */
    SHOP_APPROVAL_REQUEST(),

    /** 店舗原稿校了権限 */
    SHOP_FINISHING_PROOF(),

    /** 店舗原稿代行権限 */
    SHOP_MYNV_AGENT(),

    /** 店舗原稿代行登録権限 */
    SHOP_MYNV_AGENT_REGIST(),

    /** 店舗原稿修正権限 */
    SHOP_EDIT(),

    /** 店舗原稿削除権限 */
    SHOP_DELETE(),

    /** 店舗原稿テンプレート一覧権限 */
    SHOP_TEMPLATE_LIST(),

    /** 店舗原稿テンプレート閲覧権限 */
    SHOP_TEMPLATE_READ(),

    /** 店舗原稿テンプレート登録権限 */
    SHOP_TEMPLATE_REGIST(),

    /** 店舗原稿テンプレート代行権限 */
    SHOP_TEMPLATE_MYNV_AGENT(),

    /** 店舗原稿テンプレート代行登録権限 */
    SHOP_TEMPLATE_MYNV_AGENT_REGIST(),

    /** 店舗原稿テンプレート修正権限 */
    SHOP_TEMPLATE_EDIT(),

    /** 店舗原稿テンプレート削除権限 */
    SHOP_TEMPLATE_DELETE(),

    /** 特典一覧権限 */
    BENEFIT_LIST(),

    /** 特典閲覧権限 */
    BENEFIT_READ(),

    /** 特典登録権限 */
    BENEFIT_REGIST(),

    /** 特典修正権限 */
    BENEFIT_EDIT(),

    /** 特典削除権限 */
    BENEFIT_DELETE(),

    /** オリジナル特典一覧権限 */
    ORIGINAL_BENEFIT_LIST(),

    /** オリジナル特典閲覧権限 */
    ORIGINAL_BENEFIT_READ(),

    /** オリジナル特典登録権限 */
    ORIGINAL_BENEFIT_REGIST(),

    /** オリジナル特典修正権限 */
    ORIGINAL_BENEFIT_EDIT(),

    /** オリジナル特典削除権限 */
    ORIGINAL_BENEFIT_DELETE(),

    /** オリジナル特典一時停止権限 */
    ORIGINAL_BENEFIT_SUSPEND(),

    /** 店舗並び替え権限 */
    SHOP_SORT(),

    /** 婚約指輪並び替え権限 */
    ENG_SORT(),

    /** 結婚指輪並び替え権限 */
    MRG_SORT(),

    /** ジュエリー先輩カップル並び替え権限 */
    RING_PEOPLE_SORT(),

    /** 反響数一覧権限 */
    MEASURE_LIST(),

    /** 各種設定権限 */
    CONFIG_EDIT(),

    /** フォト一覧権限 */
    PHOTO_LIST(),

    /** フォト閲覧権限 */
    PHOTO_READ(),

    /** フォト登録権限 */
    PHOTO_REGIST(),

    /** フォト修正権限 */
    PHOTO_EDIT(),

    /** フォト削除権限 */
    PHOTO_DELETE(),

    /** 先輩カップル原稿一覧権限 */
    RING_PEOPLE_LIST(),

    /** 先輩カップル原稿閲覧権限 */
    RING_PEOPLE_READ(),

    /** 先輩カップル原稿登録権限 */
    RING_PEOPLE_REGIST(),

    /** 先輩カップル原稿修正権限 */
    RING_PEOPLE_EDIT(),

    /** 先輩カップル原稿校了権限 */
    RING_PEOPLE_FINISHING_PROOF(),

    /** 先輩カップル校了登録権限 */
    RING_PEOPLE_FINISHING_PROOF_REGIST(),

    /** 先輩カップル承認依頼権限 */
    RING_PEOPLE_APPROVAL_REQUEST(),

    /** 先輩カップル承認依頼登録権限 */
    RING_PEOPLE_APPROVAL_REQUEST_REGIST(),

    /** マイページお知らせ一覧権限 */
    RING_CLIP_LIST(),

    /** マイページお知らせ閲覧権限 */
    RING_CLIP_READ(),

    /** マイページお知らせ登録権限 */
    RING_CLIP_REGIST(),

    /** マイページお知らせ修正権限 */
    RING_CLIP_EDIT(),

    /** マイページお知らせ削除権限 */
    RING_CLIP_DELETE(),
    
    /** 編集部原稿閲覧権限 */
    WEDDING_EDITOR_VIEW(),
    
    /** 編集部原稿登録権限 */
    WEDDING_EDITOR_REGIST(),
    
    /** 編集部原稿修正権限 */
    WEDDING_EDITOR_EDIT(),
    
    /** 月間アクセス数集計 (全屋号) */
    MEASUREALL_ACCESS(),
    
    /** Tポイント付与権限フラグ編集 */
    TPOINT_AUTH_EDIT(),

    /** Tポイント付与一覧閲覧権限 */
    TPOINT_LIST(),

    /** Tポイン請求一覧閲覧権限 */
    TPOINT_BILL_LIST(),

    /** Tポイント付与権限フラグ編集 */
    RING_TPOINT_AUTH_EDIT(),

    /** Tポイント付与一覧閲覧権限 */
    RING_TPOINT_LIST(),

    /** Tポイン請求一覧閲覧権限 */
    RING_TPOINT_BILL_LIST(),

    /** 各種設定 */
    VARIOUS_SET(),

    /** 掲載情報の登録 */
    PUBLISH_INFO_REGIST(),

    /** 並び順設定 */
    SORT_SET(),

    /** 効果測定 */
    MEASURE_INFO(),

    /** 画像管理 */
    IMAGE_MANAGE(),

    /** 動画管理 */
    MOVIE_MANAGE(),

    /** プレミアム管理 */
    PREMIUM_MANAGE(),

    /** サロン予約・請求権限編集 */
    SALON_AUTH_EDIT(),

    /** サロン予約・請求権限（代行ログイン） */
    SALON_AUTH_ADMIN(),

    /** クロスセル一覧権限 */
    CROSSSELL_LIST,

    /** クロスセル登録権限 */
    CROSSSELL_REGIST,

    /** クチコミ一覧権限 */
    REVIEW_LIST(),

    /** クチコミ閲覧権限 */
    REVIEW_READ(),

    /** クチコミ返信権限 */
    REVIEW_REPLY(),

    /** カスタマイズ設問設定権限 */
    RING_CUSTOMIZE_INQUIRY_SET(),

    /** アンケートデータ出力権限 */
    ENQUETE_DATA_CLIENT_EXPORT(),
    ;

    /** すべての権限を持つアカウントの表現文字列 */
    public static final String SUPER_ADMIN = "*";

    /**
     * nameで権限を見つけます。
     * 
     * @param name 権限名
     * @return 権限
     */
    public static ClientAuthority find(String name) {
	if (name == null) {
	    return null;
	}
	for (ClientAuthority a : values()) {
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
    public static boolean hasAuthority(ClientAuthority[] authes, ClientAuthority auth) {

	if (auth == null) {
	    throw new IllegalArgumentException();
	}

	if (NONE.equals(auth)) {
	    return true;
	}

	if (authes == null || authes.length <= 0) {
	    return false;
	}

	for (ClientAuthority a : authes) {
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
    public static AccountAuthority[] toAccountAuthority(ClientAuthority[] authes) {

	if (authes == null) {
	    throw new IllegalArgumentException();
	}

	Set<AccountAuthority> list = new HashSet<AccountAuthority>();
	for (ClientAuthority a : authes) {
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
    public static String toString(ClientAuthority[] authes) {
	if (authes == null || authes.length <= 0) {
	    return "";
	}

	boolean first = true;
	StringBuilder sb = new StringBuilder();
	for (ClientAuthority a : authes) {
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
    public static ClientAuthority[] valuesOf(String values) {
	if (values == null || values.length() <= 0) {
	    return EMPTY;
	}

	if (SUPER_ADMIN.equals(values)) {
	    return values();
	}

	List<ClientAuthority> list = new ArrayList<ClientAuthority>();
	for (String name : values.split("\\s*,\\s*")) {
	    ClientAuthority a = find(name.trim());
	    if (a == null) {
		continue;
	    }
	    list.add(a);
	}

	if (list.isEmpty()) {
	    return EMPTY;
	}

	ClientAuthority[] arr = list.toArray(new ClientAuthority[list.size()]);
	Arrays.sort(arr);

	return arr;
    }

    /** AccountAuthorityの対応する権限 */
    private final AccountAuthority[] account;

    /** 空 */
    private static final ClientAuthority[] EMPTY = new ClientAuthority[0];

    /**
     * @param account AccountAuthorityの対応する権限
     */
    private ClientAuthority(AccountAuthority... account) {
	this.account = account;
    }
}
