/**
 * rights reserved.
 */
package com.isystk.sample.web.common.constants;

import org.seasar.framework.env.Env;

/**
 * @author iseyoshitaka
 * 
 */
public final class WebConstants {

    public static final class SelectOption {
	/** プルダウンで空の場合に表示する値 */
	public static final String VOID_VALUE = "----";

	/** プルダウンで空の場合に表示する値 */
	public static final String MUST_SELECT_VALUE = "選択してください";

	/** プルダウンで空の場合に表示する値 */
	public static final String JUST_HYPHEN_VALUE = "-";

	/** プルダウンで空の場合に表示する値 */
	public static final String VOID_UNDECIDED = "未定";

	/** プルダウンで空の場合に表示する値 */
	public static final String ALL = "すべて";

	/** プルダウンで空の場合に表示する値 */
	public static final String DATE_SELECT_VALUE = "日付";

	/** プルダウンで空の場合に表示する値 */
	public static final String TIME_SELECT_VALUE = "時間";

	/** エリア用プルダウンで空の場合に表示する値 */
	public static final String ALL_AREA_VALUE = "全てのエリア";

	/** プルダウンで空の場合に表示する値 */
	public static final String ALL_OVER_JAPAN = "全国";

    }

    /**
     * サンプル
     */
    public static final class Sample {

	/** 連結文字 */
	public static final String TEST = "Webテスト";
    }

    /**
     * 環境
     */
    public static final class ENV {

	public static final boolean PRODUCT = Env.PRODUCT.equals(Env.getValue());

    }

    /**
     * クッキー
     */
    public static final class Cookie {

	/** 広域エリアID */
	public static final String WIDE_REGION_AREA_ID_COOKIE_KEY = "warea";

	/** お気に入り式場ID */
	public static final String CLIPWEDDING_COOKIE_KEY = "ClipWedding";
	
	/** お気に入り画像ID */
	public static final String CLIPIMAGE_COOKIE_KEY = "ClipImage";
	
	/** インスピレーション画像ID */
	public static final String CLIPIINSPIRATIONMAGE_COOKIE_KEY = "ClipInspirationImage";
	
	/** お気に入りフェアID */
	public static final String CLIPFAIR_COOKIE_KEY = "ClipFair";
	
	/** お気に入りプランID */
	public static final String CLIPPLAN_COOKIE_KEY = "ClipPlan";
	
	/** お気に入り直近プランID */
	public static final String CLIPLATESTPLAN_COOKIE_KEY = "ClipLatestPlan";
	
	/** お気に入り手配会社ID */
	public static final String CLIPAGENT_COOKIE_KEY = "ClipAgent";
	
	/** お気に入りチャペルID */
	public static final String CLIPCHAPEL_COOKIE_KEY = "ClipChapel";
	
	/** お気に入り先輩カップルID */
	public static final String CLIPPEOPLE_COOKIE_KEY = "ClipPeople";

	/** クリップした最新のID */
	public static final String CLIPNEW_COOKIE_KEY = "ClipNew";

	/** 初回お気に入り登録 */
	public static final String CLIPFIRST_COOKIE_KEY = "ClipFirst";

	/** ABテスト区分 */
	public static final String VIEW_STOCK_DIV = "viewStockDiv";
    }

}
