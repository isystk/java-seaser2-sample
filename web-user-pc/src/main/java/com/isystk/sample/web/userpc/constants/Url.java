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

	/** トップ */
	userpcTop("/"),
	/** 投稿登録 */
	userpcMemberPeopleRegist("/member/people/regist/"),
	/** 投稿修正 */
	userpcMemberPeopleEdit("/member/people/edit/"),
	/** ログイン */
	userpcLogin("/login/"),
	/** ログアウト */
	userpcLogout("/logout/"),
	/** 会員登録 */
	userpcEntryRegist("/entry/regist/"),
	/** 仮会員登録用アドレス */
	userpcEntyRemind("/entry/remind/"),
	/** マイページ */
	userpcMember("/member/");

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
