/*
 */
package com.isystk.sample.web.userpc.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.util.StringUtil;
import org.seasar.struts.util.RequestUtil;

import com.isystk.sample.common.config.AppConfigNames;
import com.isystk.sample.common.config.Config;
import com.isystk.sample.common.util.StringUtils;
import com.isystk.sample.web.common.sastruts.AbstractRequestProcessor;
import com.isystk.sample.web.common.util.UrlUtil;

/**
 * フロントPCのURL定数群です。 パラメタを渡すことによってクエリ文字列を生成することができます。
 *
 * URLの詳細はこちら
 * <p>
 * 変更になった場合は適宜修正してください
 * <p>
 *
 * @author iseyoshitaka
 *
 */
public enum Url {

	/** ドメイン */
	userpcDomain(Config.getString(AppConfigNames.USERPC_DOMAIN)),

	/** FST001 サイトトップ */
	userpcTop("/"),
	/** FSR007 式場検索トップ */
	userpcSearchTop("/search/top/"),
	/** FTP002 エリアから探す */
	userpcTopArea("/top/area/"),
	/** FTP005 フェアを探す */
	userpcTopFair("/top/fair/"),
	/** FTP003 パッケージプランを探す */
	userpcTopPlan("/top/plan/"),
	/** FTP004 直前オトクプランを探す */
	userpcTopLatestplan("/top/latestplan/"),
	/** FTP006 エリアナビ */
	userpcTopNavi("/top/navi/"),
	/** FTP006 エリアナビ */
	userpcTopAreaNavi("/top/navi/{0}/"),
	/** FSR000 条件検索結果 */
	userpcSearch("/search/"),
	/** FSR004 条件検索結果_式場 */
	userpcSearchWedding("/search/wedding/"),
	/** FSR004 条件検索結果_式場 */
	userpcSearchAreaWedding("/search/wedding/{0}/"),
	/** FSR004 条件検索結果_式場(国内リゾート) */
	userpcSearchWeddingResortin("/search/wedding/resortin/"),
	/** FSR004 条件検索結果_式場(パーティ婚) */
	userpcSearchWeddingParty("/search/wedding/party/"),
	/** FSR004 条件検索結果_式場（全部） */
	userpcSearchWeddingAll("/search/wedding/all/"),
	/** FSR006 条件検索結果_フリーワード */
	userpcSearchFreeword("/search/freeword/"),
	/** FSR002 条件検索結果_フェア */
	userpcSearchFair("/search/fair/"),
	/** FSR002 条件検索結果_フェア(国内リゾート) */
	userpcSearchFairResortin("/search/fair/resortin/"),
	/** FSR002 条件検索結果_フェア(パーティ婚) */
	userpcSearchFairParty("/search/fair/party/"),
	/** FSR002 条件検索結果_フェア(手配会社) */
	userpcSearchFairAbroad("/search/fair/abroad/"),
	/** FSR003 条件検索結果_プラン */
	userpcSearchPlan("/search/plan/"),
	/** FSR003 条件検索結果_プラン(国内リゾート) */
	userpcSearchPlanResortin("/search/plan/resortin/"),
	/** FSR003 条件検索結果_プラン(パーティ婚) */
	userpcSearchPlanParty("/search/plan/party/"),
	/** FSR003 条件検索結果_プラン(手配会社) */
	userpcSearchPlanAbroad("/search/plan/abroad/"),
	/** FSR005 条件検索結果_直近プラン */
	userpcSearchLatestplan("/search/latestplan/"),
	/** FSR005 条件検索結果_直近プラン(国内リゾート) */
	userpcSearchLatestplanResortin("/search/latestplan/resortin/"),
	/** FSR005 条件検索結果_直近プラン(手配会社) */
	userpcSearchLatestplanAbroad("/search/latestplan/abroad/"),
	/** FSR006 条件検索結果_インスピレーション */
	userpcSearchInspiration("/search/inspiration/"),
	/** FSR008 条件検索結果_先輩カップル */
	userpcSearchPeople("/search/people/"),
	/** S007 条件検索結果_手配会社 */
	userpcSearchAgent("/search/agent/"),
	/** S008 条件検索結果_チャペル */
	userpcSearchHall("/search/hall/"),
	/** ランキング TOP */
	userpcRanking("/ranking_w/"),
	/** ランキング 式場 */
	userpcRankingWedding("/ranking_w/{0}/"),
	/** ログイン */
	userpcLogin("/login/"),
	/** FLI002 初回ログイン入力 */
	userpcLoginFirst("/login/first"),
	/** ログアウト */
	userpcLogout("/login/?logout=smt"),
	/** FKT001 規約への同意 */
	userpcEntry("/entry/"),
	/** FKT002 会員登録入力 */
	userpcEntryRegist("/entry/regist/"),
	/** FDT002 基本情報 */
	userpcWedding("/wedding/{0}/"),
	/** FDT003 ブライダルフェア一覧 */
	userpcWeddingFairList("/wedding/fair/list/{0}/"),
	/** FDT016 ブライダルフェア一覧（カレンダー） */
	userpcWeddingFairCalendarList("/wedding/fair/calendar/{0}/"),
	/** FDT004 ブライダルフェア詳細 */
	userpcWeddingFair("/wedding/fair/{0}/"),
	/** FDT019 フェアマスタ */
	userpcWeddingFairInfo("/wedding/fair/info/{0}/"),
	/** FDT005 オススメプラン一覧 */
	userpcWeddingPlanList("/wedding/plan/list/{0}/"),
	/** FDT006 オススメプラン詳細 */
	userpcWeddingPlan("/wedding/plan/{0}/"),
	/** FDT007 ３カ月以内のお得なプラン一覧 */
	userpcWeddingLatestplanList("/wedding/latestplan/list/{0}/"),
	/** FDT008 ３カ月以内のお得なプラン詳細 */
	userpcWeddingLatestplan("/wedding/latestplan/{0}/"),
	/** FDT009 演出 */
	userpcWeddingDirection("/wedding/direction/{0}/"),
	/** FDT009 演出 */
	userpcWeddingDirectionDetail("/wedding/direction/{0}/{1}/"),
	/** FDT010 先輩カップル一覧 */
	userpcWeddingPeople("/wedding/people/{0}/"),
	/** FDT010 先輩カップル詳細 */
	userpcWeddingPeopleDetail("/wedding/people/{0}/{1}/"),
	/** FDT011 地図・アクセス */
	userpcWeddingAccess("/wedding/access/{0}/"),
	/** FDT012 フォトギャラリー */
	userpcWeddingPhoto("/wedding/photo/{0}/"),
	/** FDT012 フォトギャラリー詳細 */
	userpcWeddingPhotoDetail("/wedding/photo/{0}/{1}/"),
	/** FDT013 特典 */
	userpcWeddingSpecial("/wedding/special/{0}/"),
	/** FDT014 見積もり */
	userpcWeddingEstimate("/wedding/estimate/{0}/"),
	/** FDT015 地図・アクセス_印刷用 */
	userpcWeddingMap("/wedding/map/{0}/"),
	/** FQA001 段取り原稿ID */
	userpcWeddingQa("/wedding/qa/{0}/"),
	/** FBF001 ブライダルフェア予約入力 */
	userpcWeddingInquiryFair("/wedding/inquiry/fair/entry/{0}/"),
	/** FBF003 ブライダルフェア予約入力完了 */
	userpcWeddingInquiryFairComplete("/wedding/inquiry/fair/complete/{0}/"),
	/** FRY001 見学予約入力 */
	userpcWeddingVisitFair("/wedding/inquiry/visit/entry/{0}/"),
	/** FRY003 見学予約入力完了 */
	userpcWeddingVisitFairComplete("/wedding/inquiry/visit/complete/{0}/"),
	/** FSS001 資料請求入力 */
	userpcWeddingDocument("/wedding/inquiry/document/entry/{0}/"),
	/** FSS003 資料請求入力完了 */
	userpcWeddingDocumentComplete("/wedding/inquiry/document/complete/{0}/"),
	/** FRQ001 お問い合わせ入力 */
	userpcWeddingCommon("/wedding/inquiry/common/entry/{0}/"),
	/** FRQ003 お問い合わせ入力完了 */
	userpcWeddingCommonComplete("/wedding/inquiry/common/complete/{0}/"),
	/** アンケート登録入力 */
	userpcWeddingEnquete("/wedding/inquiry/enquete/entry/"),
	/** アンケート登録入力完了 */
	userpcWeddingEnqueteComplete("/wedding/inquiry/enquete/complete/"),
	/** FMP001 マイページトップ（クリップメッセージ） */
	userpcMember("/member/"),
	/** FMP001 マイページトップ（申し込み履歴） */
	userpcMemberHistory("/member/history/"),
	/** FMP002 クリップメッセージ詳細 */
	userpcMemberClip("/member/clip/{0}/"),
	/** FMP003 クリップアイテム一覧_式場 */
	userpcMemberClipWedding("/member/clip/wedding/"),
	/** FMP013 クリップアイテム一覧_式場_表組み */
	userpcMemberClipWeddingsimple("/member/clip/weddingsimple/"),
	/** FMP014 クリップアイテム一覧_式場_共有 */
	userpcMemberClipWeddingshare("/member/clip/weddingshare/"),
	/** FMP004 クリップアイテム一覧_ブライダルフェア */
	userpcMemberClipFair("/member/clip/fair/"),
	/** FMP005 クリップアイテム一覧_プラン */
	userpcMemberClipPlan("/member/clip/plan/"),
	/** FMP0011 クリップアイテム一覧_ブライダルフェア_表組み */
	userpcMemberClipFairsimple("/member/clip/fairsimple/"),
	/** FMP0012 クリップアイテム一覧_プラン_表組み */
	userpcMemberClipPlansimple("/member/clip/plansimple/"),
	/** FMP0012 クリップアイテム一覧_手配会社(リスト) */
	userpcMemberClipAgent("/member/clip/agent/"),
	/** FMP0012 クリップアイテム一覧_手配会社(比較) */
	userpcMemberClipAgentsimple("/member/clip/agentsimple/"),
	/** FMP0012 クリップアイテム一覧_手配会社(共有) */
	userpcMemberClipAgentshare("/member/clip/agentshare/"),
	/** FMP0012 クリップアイテム一覧_チャペル */
	userpcMemberClipHall("/member/clip/hall/"),
	/** FMP008 会員情報変更入力 */
	userpcMemberEdit("/member/edit/"),
	/** FPR001 メールアドレス入力 */
	userpcEntyRemind("/entry/remind/"),
	/** FMA001 旧メールアドレス入力 */
	userpcMemberMailEdit("/member/mail/edit/"),
	/** FTK001 退会理由入力 */
	userpcMemberResign("/member/resign/"),
	/** FIB002 ボード詳細 */
	userpcMemberInspiration("/member/inspiration/"),
	/** FIB002 ボード詳細（from サイトトップ） */
	userpcMemberInspirationSearch("/member/inspiration/search"),
	/** FIB002 ボード詳細（地域絞込み） */
	userpcMemberInspirationFilter("/member/inspiration/filter"),
	/** FIB002 ボード詳細画像削除 */
	userpcMemberInspirationDelete("/member/inspiration/delete/"),
	/** FIB004 タグ検索結果（ID指定無し） */
	userpcMemberInspirationTagWithoutId("/member/inspiration/tag/"),
	/** FIB004 タグ検索結果 */
	userpcMemberInspirationTag("/member/inspiration/tag/{0}/"),

	/** FPO001 投稿型先輩カップル登録 */
	userpcMemberPeopleRegist("/member/people/regist/"),
	/** FPO002 投稿型先輩カップル修正 */
	userpcMemberPeopleEdit("/member/people/edit/"),

	/** FPA001 パーティ婚トップ */
	userpcParty("/party/"),
	/** FMM001 マイポ申請入力 */
	userpcMypoApply("/mypo/apply/"),
	/** サイトマップ */
	userpcSitemap("/sitemap/"),
	/** 掲載会場一覧 */
	userpcWeddinglist("/weddinglist/"),
	/** Openid mypo LoginUrl */
	userpcOpenidMypoLogin("/login/mypoLogin"),
	/** Openid mypo ReturnUrl */
	userpcOpenidMypoReturnUrl("/login/mypoopenid"),
	/** PR枠 */
	userpcWeddingPr("/wedding/pr/"),
	/** 利用規約の静的ページ */
	userpcTermsOfService("/guide/rules/kiyaku.html"),
	/** 会員規約の静的ページ */
	userpcTermsOfUser("/guide/rules/member.html"),
	/** 個人情報の取り扱いについての静的ページ */
	userpcHandlingOfPersonalInformation("/guide/rules/personalinfo.html"),

	/** パーティ婚特集 */
	userpcContentsParty("/contents/party/"),
	/** FFE001 特集テーマ一覧 */
	userpcFeatureThemeList("/feature/theme/list/"),
	/** FFE002 特集 */
	userpcFeature("/feature/{0}/"),
	/** FFE004 特集検索 */
	userpcFeatureSearch("/feature/search/"),
	/** 写真スライドショー */
	userpcPhotoSlide("/photo/slide/{0}/"),
	/** FMC001 会員用メルマガ解除 */
	userpcMemberMagazineEditCancel("/member/magazine/edit/cancel/"),
	/** FMC002 会員用メルマガ解除 */
	userpcDocumentMailEditCancel("/wedding/inquiry/magazine/edit/cancel/"),
	/** Tポイント利用手続き */
	userpcTpointEntry("/tpoint/entry/"),

	/** ここから、海外リゾートのURLです */

	/** T002 リゾートトップ */
	userpcResortIn("/resort/in/"),
	/** T003 海外リゾートTOP */
	userpcResortInAbroad("/resort/in/abroad/"),
	/** FRD001・T004 国内リゾートトップ */
	userpcResortInDomestic("/resort/in/domestic/"),
	/** S001 ハワイTOP */
	userpcResortAbroadHawaii("/resort/abroad/hawaii/"),
	/** S002 グアムTOP */
	userpcResortAbroadGuam("/resort/abroad/guam/"),
	/** A001 手配会社_基本情報 */
	userpcResortAbroadAgent("/resort/abroad/agent/{0}/"),
	/** A002 手配会社_チャペル一覧 */
	userpcResortAbroadAgentHall("/resort/abroad/agent/hall/{0}/"),
	/** A003 手配会社_ブライダルフェア一覧 */
	userpcResortAbroadAgentFair("/resort/abroad/agent/fair/{0}/"),
	/** A005 手配会社_プラン一覧 */
	userpcResortAbroadAgentPlan("/resort/abroad/agent/plan/{0}/"),
	/** A006 手配会社_プラン詳細 */
	userpcResortAbroadAgentPlanDetail("/resort/abroad/agent/plan/{0}/{1}/"),
	/** A007 手配会社_直近プラン一覧 */
	userpcResortAbroadAgentLatestplan("/resort/abroad/agent/latestplan/{0}/"),
	/** A008 手配会社_直近プラン詳細 */
	userpcResortAbroadAgentLatestplanDetail("/resort/abroad/agent/latestplan/{0}/{1}/"),
	/** A009 手配会社_先輩カップル一覧 */
	userpcResortAbroadAgentPeople("/resort/abroad/agent/people/{0}/"),
	/** A010 手配会社_先輩カップル詳細 */
	userpcResortAbroadAgentPeopleDetail("/resort/abroad/agent/people/{0}/{1}/"),
	/** A012 手配会社_演出一覧 */
	userpcResortAbroadAgentDirection("/resort/abroad/agent/direction/{0}/"),
	/** A012 手配会社_演出詳細 */
	userpcResortAbroadAgentDirectionDetail("/resort/abroad/agent/direction/{0}/{1}"),
	/** A013 手配会社_フォトギャラリー一覧 */
	userpcResortAbroadAgentPhoto("/resort/abroad/agent/photo/{0}/"),
	/** A014 手配会社_フォトギャラリー詳細 */
	userpcResortAbroadAgentPhotoDetail("/resort/abroad/agent/photo/{0}/{1}/"),
	/** A015 手配会社_地図&アクセス */
	userpcResortAbroadAgentAccess("/resort/abroad/agent/access/{0}/"),
	/** A016 手配会社_店舗詳細（ポップアップ） */
	userpcResortAbroadAgentAccessDetail("/resort/abroad/agent/access/{0}/{1}/"),
	/** A017 手配会社_特典 */
	userpcResortAbroadAgentSpecial("/resort/abroad/agent/special/{0}/"),
	/** A22 手配会社_ダンドリ */
	userpcResortAbroadAgentQa("/resort/abroad/agent/qa/{0}/"),
	/** C002 チャペル_基本情報 */
	userpcResortAbroadHall("/resort/abroad/hall/{0}/"),
	/** C003 チャペル_手配会社一覧 */
	userpcResortAbroadHallAgent("/resort/abroad/hall/agent/{0}/"),
	/** C004 チャペル_先輩カップル一覧 */
	userpcResortAbroadHallPeople("/resort/abroad/hall/people/{0}/"),
	/** C005 チャペル_プラン一覧 */
	userpcResortAbroadHallPlan("/resort/abroad/hall/plan/{0}/"),
	/** C006 チャペル_直前オトクプラン一覧 */
	userpcResortAbroadHallLatestplan("/resort/abroad/hall/latestplan/{0}/"),
	/** C007 チャペル_フォトギャラリ一覧 */
	userpcResortAbroadHallPhoto("/resort/abroad/hall/photo/{0}/"),

	/** ここから、SP版のURLです */

	/** SST001 サイトトップ */
	userspTop("/sp/"),
	/** SDT011 地図・アクセス */
	userspWeddingAccess("/sp/wedding/access/{0}/"),
	/** SDT003 ブライダルフェア一覧 */
	userspWeddingFairList("/sp/wedding/fair/list/{0}/"),
	/** SDT018 フェアマスタ */
	userspWeddingFairInfo("/sp/wedding/fair/info/{0}/"),
	/** SSR003 条件検索結果_フェア */
	userspSearchFair("/sp/search/fair/"),
	/** SSR003 条件検索結果_フェア(国内リゾート) */
	userspSearchFairResortin("/sp/search/fair/resortin/"),
	/** SSR003 条件検索結果_フェア(パーティ婚) */
	userspSearchFairParty("/sp/search/fair/party/"),
	/** SSR003 条件検索結果_フェア(手配会社) */
	userspSearchFairAbroad("/sp/search/fair/abroad/"),
	/** SSR004 条件検索結果_プラン */
	userspSearchPlan("/sp/search/plan/"),
	/** SSR004 条件検索結果_プラン(国内リゾート) */
	userspSearchPlanResortin("/sp/search/plan/resortin/"),
	/** SSR004 条件検索結果_プラン(パーティ婚) */
	userspSearchPlanParty("/sp/search/plan/party/"),
	/** SSR004 条件検索結果_プラン(手配会社) */
	userspSearchPlanAbroad("/sp/search/plan/abroad/"),
	/** SSR005 条件検索結果_式場 */
	userspSearchWedding("/sp/search/wedding/"),
	/** SSR005 条件検索結果_式場(国内リゾート) */
	userspSearchWeddingResortin("/sp/search/wedding/resortin/"),
	/** SSR005 条件検索結果_式場(パーティ婚) */
	userspSearchWeddingParty("/sp/search/wedding/party/"),
	/** 条件検索結果_チャペル */
	userspSearchHall("/sp/search/hall/"),
	/** 条件検索結果_手配会社 */
	userspSearchAgent("/sp/search/agent/"),
	/** SSR006 条件検索結果_直近プラン */
	userspSearchLatestplan("/sp/search/latestplan/"),
	/** SSR006 条件検索結果_直近プラン(国内リゾート) */
	userspSearchLatestplanResortin("/sp/search/latestplan/resortin/"),
	/** SSR006 条件検索結果_直近プラン(手配会社) */
	userspSearchLatestplanAbroad("/sp/search/latestplan/abroad/"),
	/** プレミアム 条件検索結果_式場 */
	userspPremiumSearchWedding("/sp/premium/search/wedding/"),
	/** プレミアム 条件検索結果_フェア */
	userspPremiumSearchFair("/sp/premium/search/fair/"),
	/** プレミアム 条件検索結果_プラン */
	userspPremiumSearchPlan("/sp/premium/search/plan/"),
	/** プレミアム 条件検索結果_直近プラン */
	userspPremiumSearchLatestplan("/sp/premium/search/latestplan/"),
	/** プレミアム 条件検索結果_披露宴会場 */
	userspPremiumSearchBanquet("/sp/premium/search/banquet/"),
	/** テイスト別（LUXE）一覧 */
	userspPremiumLuxeList("/sp/premium/luxe/list/"),
	/** 特集AMP */
	userspFeatureAmp("/sp/feature/{0}/amp/"),
	/** SDT002 基本情報(Amp) */
	userspWeddingAmp("/sp/wedding/{0}/amp/"),
	/** A001 手配会社_基本情報AMP */
	userspResortAbroadAgentAmp("/sp/resort/abroad/agent/{0}/amp/"),
	/** A003 手配会社_ブライダルフェア一覧 */
	userspResortAbroadAgentFair("/sp/resort/abroad/agent/fair/{0}/"),
	/** ランキング TOP */
	userspRanking("/sp/ranking_w/"),
	/** ランキング 式場 */
	userspRankingWedding("/sp/ranking_w/{0}/"),

	/** ここから、管理画面の URL です */
	/** FFP011 特集プレビュー */
	userpcFeaturePreview("/feature/preview/{0}/"),
	/** FFP012 特集プレビュー（プレミアム） */
	userpcPremiumFeaturePreview("/premium/feature/preview/{0}/"),

	/** ここから、プレミアムのURLです */

	/** プレミアムグローバルTOP */
	userpcPremium("/premium/"),
	/** プレミアム ランキング */
	userpcPremiumRanking("/premium/ranking/"),
	/** プレミアム ランキング カテゴリー別 */
	userpcPremiumRankingCategory("/premium/ranking/category/{0}/"),
	/** プレミアム ランキング カテゴリー別（ホテルウェディング） */
	userpcPremiumRankingCategoryHotel("/premium/ranking/category/hotel/"),
	/** プレミアム ランキング カテゴリー別（レストラン・料亭ウェディング） */
	userpcPremiumRankingategoryRestaurant("/premium/ranking/category/restaurant/"),
	/** プレミアム ランキング カテゴリー別（ゲストハウスウェディング） */
	userpcPremiumRankingCategoryHouse("/premium/ranking/category/house/"),
	/** プレミアム ランキング カテゴリー別（専門式場・チャペル・神社） */
	userpcPremiumRankingCategorySenmonChapelShrine("/premium/ranking/category/senmonChapelShrine/"),
	/** プレミアム ランキング カテゴリー別（国内リゾート） */
	userpcPremiumRankingCategoryResort("/premium/ranking/category/resort/"),
	/** プレミアム ランキング テイスト別 */
	userpcPremiumRankingStyle("/premium/ranking/style/{0}/"),
	/** プレミアム ランキング テイスト別（LUXE） */
	userpcPremiumRankingStyleLuxe("/premium/ranking/style/luxe/"),
	/** プレミアム ランキング テイスト別（モダンスタイリッシュ） */
	userpcPremiumRankingStyleModernstylish("/premium/ranking/style/modernstylish/"),
	/** プレミアム ランキング テイスト別（大人可愛い） */
	userpcPremiumRankingStyleAdultcute("/premium/ranking/style/otonakawaii/"),
	/** プレミアム ランキング テイスト別（ナチュラル） */
	userpcPremiumRankingStyleNatural("/premium/ranking/style/natural/"),
	/** プレミアム ランキング テイスト別（ヨーロピアン） */
	userpcPremiumRankingStyleEuropean("/premium/ranking/style/european/"),
	/** プレミアム ランキング テイスト別（和婚） */
	userpcPremiumRankingStyleJapanese("/premium/ranking/style/japanese/"),
	/** プレミアム ランキング テイスト別（エンターテインメント） */
	userpcPremiumRankingStyleEntertainment("/premium/ranking/style/entertainment/"),
	/** プレミアム 条件検索結果_式場 */
	userpcPremiumSearchWedding("/premium/search/wedding/"),
	/** プレミアム 条件検索結果_フェア */
	userpcPremiumSearchFair("/premium/search/fair/"),
	/** プレミアム 条件検索結果_プラン */
	userpcPremiumSearchPlan("/premium/search/plan/"),
	/** プレミアム 条件検索結果_直近プラン */
	userpcPremiumSearchLatestplan("/premium/search/latestplan/"),
	/** プレミアム 条件検索結果_披露宴会場 */
	userpcPremiumSearchBanquet("/premium/search/banquet/"),
	/** プレミアム カテゴリー別 */
	userpcPremiumCategory("/premium/category/"),
	/** プレミアム カテゴリー別（ホテルウェディング） */
	userpcPremiumCategoryHotel("/premium/category/hotel/"),
	/** プレミアム カテゴリー別（レストラン・料亭ウェディング） */
	userpcPremiumCategoryRestaurant("/premium/category/restaurant/"),
	/** プレミアム カテゴリー別（ゲストハウスウェディング） */
	userpcPremiumCategoryHouse("/premium/category/house/"),
	/** プレミアム カテゴリー別（専門式場・チャペル・神社） */
	userpcPremiumCategorySenmonChapelShrine("/premium/category/senmonChapelShrine/"),
	/** プレミアム カテゴリー別（国内リゾート） */
	userpcPremiumCategoryResort("/premium/category/resort/"),
	/** プレミアム式場TOP */
	userpcPremiumWedding("/premium/wedding/{0}/"),
	/** モデルコーディネート詳細 */
	userpcPremiumWeddingStories("/premium/wedding/{0}/stories/{1}/"),
	/** 披露宴会場 */
	userpcPremiumWeddingBanquet("/premium/wedding/{0}/banquet/{1}/"),
	/** 編集部edition */
	userpcPremiumWeddingExtra("/premium/wedding/{0}/extra/"),
	/** アイテム一覧 */
	userpcPremiumWeddingItemlist("/premium/wedding/{0}/itemlist/"),
	/** FFP001 特集 */
	userpcPremiumFeature("/premium/feature/{0}/"),
	/** FFP002 特集検索 */
	userpcPremiumFeatureSearch("/premium/feature/search/"),
	/** テイスト別（LUXE） */
	userpcPremiumLuxe("/premium/luxe/"),
	/** テイスト別TOP */
	userpcPremiumStyle("/premium/style/"),
	/** テイスト別（モダンスタイリッシュ）TOP */
	userpcPremiumStyleModernstylish("/premium/style/modernstylish/"),
	/** テイスト別（大人可愛い）TOP */
	userpcPremiumStyleAdultcute("/premium/style/otonakawaii/"),
	/** テイスト別（ナチュラル）TOP */
	userpcPremiumStyleNatural("/premium/style/natural/"),
	/** テイスト別（ヨーロピアン）TOP */
	userpcPremiumStyleEuropean("/premium/style/european/"),
	/** テイスト別（和婚）TOP */
	userpcPremiumStyleJapanese("/premium/style/japanese/"),
	/** テイスト別（エンターテインメント）TOP */
	userpcPremiumStyleEntertainment("/premium/style/entertainment/"),
	/** テイスト別一覧 */
	userpcPremiumStyleList("/premium/style/list/"),
	/** テイスト別（モダンスタイリッシュ）一覧 */
	userpcPremiumStyleModernstylishList("/premium/style/list/modernstylish/"),
	/** テイスト別（大人可愛い）一覧 */
	userpcPremiumStyleAdultcuteList("/premium/style/list/otonakawaii/"),
	/** テイスト別（ナチュラル）一覧 */
	userpcPremiumStyleNaturalList("/premium/style/list/natural/"),
	/** テイスト別（ヨーロピアン）一覧 */
	userpcPremiumStyleEuropeanList("/premium/style/list/european/"),
	/** テイスト別（和婚）一覧 */
	userpcPremiumStyleJapaneseList("/premium/style/list/japanese/"),
	/** テイスト別（エンターテインメント）一覧 */
	userpcPremiumStyleEntertainmentList("/premium/style/list/entertainment/"),
	/** 少人数TOP */
	userpcPremiumSmall("/premium/small/"),

	/** CCS021 見学予約アクション詳細 */
	clientCustomerVisit("/client/customer/visit/{0}/"),
	/** CCS035 BF予約アクション詳細 */
	clientCustomerFair("/client/customer/fair/{0}/"),

	/** ここから、ジュエリーのURLです */

	/** サイトトップ */
	userpcRingTop("/ring/"),
	/** P-ENG-S-20 条件検索結果 */
	userpcRingSearchJewelryEng("/ring/search/jewelry/eng/"),
	/** P-ENG-S-20 条件検索結果_結婚指輪 */
	userpcRingSearchJewelryMrg("/ring/search/jewelry/mrg/"),
	/** P-BND-S-20 ブランド検索結果一覧画面 */
	userpcRingSearchBrand("/ring/search/brand/"),
	/** P-SHP-S-20 店舗検索結果一覧画面 */
	userpcRingSearchShop("/ring/search/shop/"),
	/** ジュエリーログイン */
	ringuserpcLogin("/ring/login/"),
	/** ジュエリーログイン(タッチ) */
	ringuserpcLoginTouch("/ring/login/touch/"),
	/** ジュエリーログアウト */
	ringuserpcLogout("/ring/login/logout/"),
	/** ジュエリー マイページトップ */
	userpcRingMember("/ring/member/"),

	userpcRingYahooConnect("/ring/yahoo/connect/");

	private static final String PROTOCOL_HTTP = "http://";

	private static final String PROTOCOL_HTTPS = "https://";

	private final String path;

	public String getPath() {
		return path;
	}

	/**
	 *
	 * コンストラクタ。
	 * <p>
	 * 非SSL / ホスト名はリソースから取得
	 *
	 * @param path
	 *            パス
	 */
	private Url(String path) {
		this.path = path;
	}

	/**
	 * 引数のpathを元にUrl Enum値を返却する
	 *
	 * @param str
	 *            pathの文字列
	 * @throws IllegalArgumentException
	 *             引数のpathが未定義の場合
	 * @return Url enum値
	 */
	public static Url getUrlByPath(String str) {
		for (Url v : values()) {
			if (v.getPath().equals(str)) {
				return v;
			}
		}
		throw new IllegalArgumentException("undefined : " + str);
	}

	/**
	 * @param params
	 *            プレースホルダの置換文字列群
	 * @return GETパラメータなしの相対パスURLを生成
	 */
	public String getRelative(String... params) {
		return replaceMarker(getRelativeURL(), params);
	}

	/**
	 * @param params
	 *            プレースホルダの置換文字列群
	 * @return GETパラメータなしの絶対パスURLを生成
	 */
	public String getAbsolute(String... params) {
		return replaceMarker(getAbsoluteURL(), params);
	}

	/**
	 * @param map
	 *            パラメータマップ
	 * @param params
	 *            プレースホルダの置換文字列群
	 * @return GETパラメータありの相対パスURLを生成
	 */
	public String getRelative(Map<String, String> map, String... params) {
		return getURLFromMap(getRelative(params), map);
	}

	/**
	 * @param map
	 *            パラメータマップ
	 * @param params
	 *            プレースホルダの置換文字列群
	 * @return GETパラメータありの絶対パスURLを生成
	 */
	public String getAbsolute(Map<String, String> map, String... params) {
		return getURLFromMap(getAbsolute(params), map);
	}

	/**
	 * @param obj
	 *            パラメータオブジェクト
	 * @param params
	 *            プレースホルダの置換文字列群
	 * @return GETパラメータありの相対パスURLを生成
	 */
	public String getRelative(Object obj, String... params) {
		return UrlUtil.getURLFromObject(getRelative(params), obj);
	}

	/**
	 * @param obj
	 *            パラメータオブジェクト
	 * @param params
	 *            プレースホルダの置換文字列群
	 * @return GETパラメータありの絶対パスURLを生成
	 */
	public String getAbsolute(Object obj, String... params) {
		return UrlUtil.getURLFromObject(getAbsolute(params), obj);
	}

	private String getRelativeURL() {
		return path;
	}

	private String getAbsoluteURL() {
		HttpServletRequest request = RequestUtil.getRequest();
		if (request != null) {
			String scheme = AbstractRequestProcessor.isHttpsRequest(request) ? PROTOCOL_HTTPS : PROTOCOL_HTTP;
			return scheme + Config.getString(AppConfigNames.USERPC_DOMAIN) + path;
		} else {
			return "";
		}
	}

	/**
	 * プレースホルダの文字列置換を実行
	 *
	 * @param orginal
	 *            オリジナル文字列
	 * @param params
	 *            置換文字列
	 * @return {0}、{1}... のプレースホルダの変換
	 */
	private static String replaceMarker(String original, String[] params) {

		if (params != null && params.length > 0) {
			String ret = original;
			for (int i = 0; i < params.length; i++) {
				String marker = "{" + i + "}";
				if (ret.contains(marker))
					ret = ret.replace(marker, params[i]);
			}
			return ret;
		}
		return original;
	}

	/**
	 * マップからクエリ文字列を生成
	 *
	 * @param path
	 *            パス
	 * @param map
	 *            パラメータマップ
	 * @return URL文字列
	 */
	private String getURLFromMap(String path, Map<String, String> map) {
		if (map == null || map.size() == 0)
			return path;

		StringBuilder sb = new StringBuilder(path);
		sb.append('?');
		for (Iterator<Entry<String, String>> it = map.entrySet().iterator(); it.hasNext();) {
			Entry<String, String> entry = it.next();
			sb.append(entry.getKey()).append('=').append(entry.getValue());
			if (it.hasNext())
				sb.append('&');
		}
		return sb.toString();
	}

	/**
	 * 現在実行中のURLをコンテクストルートを含めて取得します。
	 * また、@URLParamのついたBeanの内容をパラメータとして追加します。さらに指定されたアクションのメソッドが実行されるURLとします。
	 *
	 * @param dataObj
	 * @URLParamのついたBean
	 * @param methodName
	 *            実行したいアクションのメソッド名
	 * @return
	 */
	public static String getCurrentUrl(Object dataObj, String methodName) {
		String currentUrl = getCurrentUrl(dataObj);
		if (currentUrl.indexOf("?") < 0) {
			currentUrl = currentUrl + "?" + methodName + "=smt";
		} else {
			currentUrl = currentUrl + "&" + methodName + "=smt";
		}
		return currentUrl;
	}

	/**
	 * 現在実行中のURLをコンテクストルートを含めて取得します。 また、@URLParamのついたBeanの内容をパラメータとして追加します。
	 *
	 * @param dataObj
	 * @URLParamのついたBean
	 * @return 現在実行中のURL
	 */
	public static String getCurrentUrl(Object dataObj) {
		return UrlUtil.getURLFromObject(getCurrentUrl(), dataObj);
	}

	/**
	 * 現在実行中のURLをコンテクストルートを含めて取得します。
	 *
	 * @return 現在実行中のURL
	 */
	public static String getCurrentUrl() {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		Map requestScope = (Map) container.getComponent("requestScope");
		String result = (String) requestScope.get("javax.servlet.forward.request_uri");
		// 本番だと詳細→一覧の遷移で/が増えていく現象があるため
		return result.replaceAll("//", "/");
	}

	/**
	 * 現在実行中のURLのクエリ文字列を取得します。
	 *
	 * @return 現在実行中のURLのクエリ文字列
	 */
	public static String getCurrentQueryString() {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		Map requestScope = (Map) container.getComponent("requestScope");
		String result = (String) requestScope.get("javax.servlet.forward.query_string");
		return result;
	}

	/**
	 * 現在のURLから、指定したクエリー文字列を除いたURLを取得します。
	 *
	 * @param withoutQuery
	 * @return
	 */
	public static String getCurrentUrlWidthQuery(String[] withoutQuery) {
		String currentUrl = getCurrentUrl();
		String queryString = getCurrentQueryString();
		if (queryString != null) {
			currentUrl += "?" + queryString;
		}
		String uri = makeUrlParamMap(currentUrl).get("URL");
		String formParam = makeUrlParamMap(currentUrl).get("PARAM");

		String[] formParams = (StringUtil.isEmpty(formParam)) ? new String[0] : formParam.split("&");

		// 不要なパラメータを除去
		List<String> params = new ArrayList<String>();
		for (int i = 0, len = formParams.length; i < len; i++) {
			int idx = formParams[i].indexOf("=");
			String value = formParams[i].substring(idx + 1);
			if (!formParams[i].matches("(" + StringUtils.joinArray(withoutQuery, "|") + ")=.*")) {
				params.add(formParams[i]);
			}
		}

		// クエリ全体
		if (!params.isEmpty()) {
			uri += '?' + StringUtils.join(params, "&");
		}

		return uri;
	}

	/**
	 * urlからパラメータを分離 戻り値Mapのkey="URL"でurlを取得 戻り値Mapのkey="PARAM"でパラメータを取得
	 *
	 * @param url
	 * @return map
	 */
	public static Map<String, String> makeUrlParamMap(String url) {

		Map<String, String> map = new HashMap<String, String>();
		int idx = 0;

		idx = url.indexOf("?");
		map.put("URL", (idx < 0) ? url : url.substring(0, idx)); // ?より前の部分のURLを抽出

		// すでにgetパラメータがあるならそのまま配列に突っ込む
		map.put("PARAM", (idx < 0) ? "" : url.substring(idx + 1));

		return map;
	}

	/**
	 * データオブジェクトからクエリ文字列を生成
	 *
	 * @param path
	 *            パス
	 * @param obj
	 *            パラメータオブジェクト
	 * @return URL文字列
	 */
	public static String getURLFromObject(String path, Object obj) {
		return UrlUtil.getURLFromObject(path, obj);
	}

}
