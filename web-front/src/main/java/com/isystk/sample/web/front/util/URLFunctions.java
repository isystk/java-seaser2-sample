/*
 * URLUtil.java 2011/02/01 iseyoshitaka
 */
package com.isystk.sample.web.front.util;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.seasar.struts.util.ActionUtil;
import org.seasar.struts.util.RequestUtil;

import com.isystk.sample.common.config.AppConfigNames;
import com.isystk.sample.common.config.Config;
import com.isystk.sample.common.util.StringUtils;
import com.isystk.sample.web.common.sastruts.AbstractRequestProcessor;
import com.isystk.sample.web.front.constants.Url;

/**
 * url.tldから呼び出されるファンクションメソッド群です。
 * <p>
 * ※ Javaソースでは基本呼び出されません。
 *
 * @author iseyoshitaka
 *
 */
public class URLFunctions {

	/** HTTPスキーマ */
	private static final String PROTOCOL_HTTP = "http://";
	private static final String PROTOCOL_HTTPS = "https://";

	/**
	 * @param url
	 *            {@link URL}キー
	 * @return GETパラメータなしの相対パスURLを生成
	 */
	public static String relative(String url) {
		return getURLKey(url).getRelative();
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param param0
	 *            プレースホルダ置換文字列
	 * @return GETパラメータなしの相対パスURLを生成
	 */
	public static String relative1(String url, String param0) {
		return getURLKey(url).getRelative(param0);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param param0
	 *            プレースホルダ置換文字列
	 * @param param1
	 *            プレースホルダ置換文字列
	 * @return GETパラメータなしの相対パスURLを生成
	 */
	public static String relative2(String url, String param0, String param1) {
		return getURLKey(url).getRelative(param0, param1);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param param0
	 *            プレースホルダ置換文字列
	 * @param param1
	 *            プレースホルダ置換文字列
	 * @param param2
	 *            プレースホルダ置換文字列
	 * @return GETパラメータなしの相対パスURLを生成
	 */
	public static String relative3(String url, String param0, String param1, String param2) {
		return getURLKey(url).getRelative(param0, param1, param2);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param map
	 *            パラメータマップ
	 * @return GETパラメータありの相対パスURLを生成
	 */
	public static String relativeWithMap(String url, Map<String, String> map) {
		return getURLKey(url).getRelative(map);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param map
	 *            パラメータマップ
	 * @param param0
	 *            プレースホルダ置換文字列
	 * @return GETパラメータありの相対パスURLを生成
	 */
	public static String relativeWithMap1(String url, Map<String, String> map, String param0) {
		return getURLKey(url).getRelative(map, param0);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param map
	 *            パラメータマップ
	 * @param param0
	 *            プレースホルダ置換文字列
	 * @param param1
	 *            プレースホルダ置換文字列
	 * @return GETパラメータありの相対パスURLを生成
	 */
	public static String relativeWithMap2(String url, Map<String, String> map, String param0, String param1) {
		return getURLKey(url).getRelative(map, param0, param1);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param map
	 *            パラメータマップ
	 * @param param0
	 *            プレースホルダ置換文字列
	 * @param param1
	 *            プレースホルダ置換文字列
	 * @param param2
	 *            プレースホルダ置換文字列
	 * @return GETパラメータありの相対パスURLを生成
	 */
	public static String relativeWithMap3(String url, Map<String, String> map, String param0, String param1,
			String param2) {
		return getURLKey(url).getRelative(map, param0, param1, param2);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param obj
	 *            パラメータオブジェクト
	 * @return GETパラメータありの相対パスURLを生成
	 */
	public static String relativeWithObj(String url, Object obj) {
		return getURLKey(url).getRelative(obj);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param obj
	 *            パラメータオブジェクト
	 * @param param0
	 *            プレースホルダ置換文字列
	 * @return GETパラメータありの相対パスURLを生成
	 */
	public static String relativeWithObj1(String url, Object obj, String param0) {
		return getURLKey(url).getRelative(obj, param0);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param obj
	 *            パラメータオブジェクト
	 * @param param0
	 *            プレースホルダ置換文字列
	 * @param param1
	 *            プレースホルダ置換文字列
	 * @return GETパラメータありの相対パスURLを生成
	 */
	public static String relativeWithObj2(String url, Object obj, String param0, String param1) {
		return getURLKey(url).getRelative(obj, param0, param1);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param obj
	 *            パラメータオブジェクト
	 * @param param0
	 *            プレースホルダ置換文字列
	 * @param param1
	 *            プレースホルダ置換文字列
	 * @param param2
	 *            プレースホルダ置換文字列
	 * @return GETパラメータありの相対パスURLを生成
	 */
	public static String relativeWithObj3(String url, Object obj, String param0, String param1, String param2) {
		return getURLKey(url).getRelative(obj, param0, param1, param2);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @return GETパラメータなしの絶対パスURLを生成
	 */
	public static String absolute(String url) {
		return getURLKey(url).getAbsolute();
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param param0
	 *            プレースホルダ置換文字列
	 * @return GETパラメータなしの絶対パスURLを生成
	 */
	public static String absolute1(String url, String param0) {
		return getURLKey(url).getAbsolute(param0);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param param0
	 *            プレースホルダ置換文字列
	 * @param param1
	 *            プレースホルダ置換文字列
	 * @return GETパラメータなしの絶対パスURLを生成
	 */
	public static String absolute2(String url, String param0, String param1) {
		return getURLKey(url).getAbsolute(param0, param1);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param param0
	 *            プレースホルダ置換文字列
	 * @param param1
	 *            プレースホルダ置換文字列
	 * @param param2
	 *            プレースホルダ置換文字列
	 * @return GETパラメータなしの絶対パスURLを生成
	 */
	public static String absolute3(String url, String param0, String param1, String param2) {
		return getURLKey(url).getAbsolute(param0, param1, param2);
	}

	/**
	 * @param url
	 *            {@link URL}内に定義されている文字列
	 * @return GETパラメータなしの絶対パスURLを生成
	 */
	public static String absoluteFromString(String url) {
		return Url.getUrlByPath(url).getAbsolute();
	}

	/**
	 * 文字列の相対パスから絶対パスを取得する(URLの存在チェックはしません)
	 *
	 * @param url
	 *            相対パス
	 * @return GETパラメータなしの絶対パスURLを生成
	 */
	public static String absoluteFromStringNocheck(String url) {
		HttpServletRequest request = RequestUtil.getRequest();
		if (request != null) {
			String scheme = AbstractRequestProcessor.isHttpsRequest(request) ? PROTOCOL_HTTPS : PROTOCOL_HTTP;
			return scheme + Config.getString(AppConfigNames.FRONT_DOMAIN) + url;
		} else {
			return "";
		}
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param map
	 *            パラメータマップ
	 * @return GETパラメータありの絶対パスURLを生成
	 */
	public static String absoluteWithMap(String url, Map<String, String> map) {
		return getURLKey(url).getAbsolute(map);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param map
	 *            パラメータマップ
	 * @param param0
	 *            プレースホルダ置換文字列
	 * @return GETパラメータありの絶対パスURLを生成
	 */
	public static String absoluteWithMap1(String url, Map<String, String> map, String param0) {
		return getURLKey(url).getAbsolute(map, param0);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param map
	 *            パラメータマップ
	 * @param param0
	 *            プレースホルダ置換文字列
	 * @param param1
	 *            プレースホルダ置換文字列
	 * @return GETパラメータありの絶対パスURLを生成
	 */
	public static String absoluteWithMap2(String url, Map<String, String> map, String param0, String param1) {
		return getURLKey(url).getAbsolute(map, param0, param1);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param map
	 *            パラメータマップ
	 * @param param0
	 *            プレースホルダ置換文字列
	 * @param param1
	 *            プレースホルダ置換文字列
	 * @param param2
	 *            プレースホルダ置換文字列
	 * @return GETパラメータありの絶対パスURLを生成
	 */
	public static String absoluteWithMap3(String url, Map<String, String> map, String param0, String param1,
			String param2) {
		return getURLKey(url).getAbsolute(map, param0, param1, param2);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param obj
	 *            パラメータオブジェクト
	 * @return GETパラメータありの絶対パスURLを生成
	 */
	public static String absoluteWithObj(String url, Object obj) {
		return getURLKey(url).getAbsolute(obj);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param obj
	 *            パラメータオブジェクト
	 * @param param0
	 *            プレースホルダ置換文字列
	 * @return GETパラメータありの絶対パスURLを生成
	 */
	public static String absoluteWithObj1(String url, Object obj, String param0) {
		return getURLKey(url).getAbsolute(obj, param0);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param obj
	 *            パラメータオブジェクト
	 * @param param0
	 *            プレースホルダ置換文字列
	 * @param param1
	 *            プレースホルダ置換文字列
	 * @return GETパラメータありの絶対パスURLを生成
	 */
	public static String absoluteWithObj2(String url, Object obj, String param0, String param1) {
		return getURLKey(url).getAbsolute(obj, param0, param1);
	}

	/**
	 * @param url
	 *            {@link URL}キー
	 * @param obj
	 *            パラメータオブジェクト
	 * @param param0
	 *            プレースホルダ置換文字列
	 * @param param1
	 *            プレースホルダ置換文字列
	 * @param param2
	 *            プレースホルダ置換文字列
	 * @return GETパラメータありの絶対パスURLを生成
	 */
	public static String absoluteWithObj3(String url, Object obj, String param0, String param1, String param2) {
		return getURLKey(url).getAbsolute(obj, param0, param1, param2);
	}

	private static Url getURLKey(String key) {
		return Url.valueOf(key);
	}

	/**
	 * 現在実行中のアクションからCSSのディレクトリのパスを取得する
	 *
	 * @return CSSのディレクトリのパス
	 */
	public static String currentCssDir() {
		String contextPath = RequestUtil.getRequest().getContextPath();
		StringBuilder sb = new StringBuilder();
		if (contextPath.length() > 1) {
			sb.append(contextPath);
		}
		sb.append("/css");
		sb.append(ActionUtil.calcActionPath());

		return sb.toString();
	}

	/**
	 * 現在実行中のアクションからJavaScriptのディレクトリのパスを取得する
	 *
	 * @return JavaScriptのディレクトリのパス
	 */
	public static String currentJsDir() {
		String contextPath = RequestUtil.getRequest().getContextPath();
		StringBuilder sb = new StringBuilder();
		if (contextPath.length() > 1) {
			sb.append(contextPath);
		}
		sb.append("/js");
		sb.append(ActionUtil.calcActionPath());

		return sb.toString();
	}

	/**
	 * 現在実行中のURLを取得します。
	 *
	 * @return 現在実行中のURL
	 */
	public static String getAbsoluteCurrentUrl() {
		HttpServletRequest request = RequestUtil.getRequest();
		String scheme = AbstractRequestProcessor.isHttpsRequest(request) ? "https" : "http";
		String serverName = request.getServerName();
		String url = scheme + "://" + serverName + Url.getCurrentUrl();

		return url;
	}

	/**
	 * 現在実行中のURLのSPのURLを取得します。
	 *
	 * @return 現在実行中のURLのSPのURL
	 */
	public static String getAbsoluteCurrentUrlForSp() {
		HttpServletRequest request = RequestUtil.getRequest();
		String scheme = AbstractRequestProcessor.isHttpsRequest(request) ? "https" : "http";
		String serverName = request.getServerName();
		String url = scheme + "://" + serverName + "/sp" + Url.getCurrentUrl();

		return url;
	}

	/**
	 * 現在実行中のURLをスキーマを含む絶対パスで取得します。</br>
	 * バリデーションエラーでforwardが行われた場合は、元のクエリー文字列を参照する
	 *
	 * @return 現在実行中のURL
	 */
	public static String getAbsoluteCurrentUrlWithQueryString() {
		HttpServletRequest request = RequestUtil.getRequest();

		String scheme = AbstractRequestProcessor.isHttpsRequest(request) ? "https" : "http";

		String serverName = request.getServerName();
		// int serverPort = request.getServerPort();

		String method = request.getMethod();

		String queryString = null;

		queryString = (String) request.getAttribute("javax.servlet.forward.query_string");

		if (StringUtils.isNullOrEmpty(queryString) && method.equals("get")) {
			// forwardが行われていなかった場合
			queryString = request.getQueryString();
		}

		String currentUrl = Url.getCurrentUrl();
		if (queryString != null) {
			currentUrl += "?" + queryString;
		}
		String url = scheme + "://" + serverName;
		// ":" + serverPort // ポートは不要
		url = url + currentUrl;

		return url;

	}

	/**
	 * 現在実行中のURLを取得します。
	 *
	 * @return 現在実行中のURL
	 */
	public static String getCurrentUrl() {
		return Url.getCurrentUrl();
	}

	/**
	 * 現在実行中のURLを取得します。(クエリ文字列含む)</br>
	 * バリデーションエラーでforwardが行われた場合は、元のクエリー文字列を参照する
	 *
	 * @return 現在実行中のURL
	 */
	public static String getCurrentUrlWithQueryString() {
		HttpServletRequest request = RequestUtil.getRequest();

		String method = request.getMethod();

		String queryString = null;

		queryString = (String) request.getAttribute("javax.servlet.forward.query_string");

		if (StringUtils.isNullOrEmpty(queryString) && method.equals("get")) {
			// forwardが行われていなかった場合
			queryString = request.getQueryString();
		}

		String currentUrl = Url.getCurrentUrl();
		if (queryString != null) {
			currentUrl += "?" + queryString;
		}

		return currentUrl;

	}

	/**
	 * 現在実行中のURLに対するスマホサイトのURLを取得します。※alternateタグ用
	 *
	 * @return 現在実行中のURLに対するスマホサイトのURL
	 */
	public static String getAlternateSpUrl() {
		HttpServletRequest request = RequestUtil.getRequest();
		String scheme = AbstractRequestProcessor.isHttpsRequest(request) ? "https" : "http";
		String serverName = request.getServerName();
		String url = scheme + "://" + serverName + "/sp" + Url.getCurrentUrl();

		return url;
	}

	/**
	 * urlをエンコードする(日本語文字列のみエンコード)
	 *
	 * @return url
	 */
	public static String encodeURL_JP(String url) {

		Pattern pattern = Pattern.compile("[-A-Z0-9+&@#/%?=~_|!:,.;]*");
		String ret = "";
		String[] urls = url.split("");
		for (int i = 0; i < urls.length; i++) {
			String u = urls[i];
			Matcher matcher = pattern.matcher(u);
			if (matcher.matches()) {
				ret = ret.concat(u);
			} else {
				try {
					ret = ret.concat(URLEncoder.encode(u, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					ret = ret.concat(u);
				}
			}
		}
		return ret;
	}

	/**
	 * パラメータを削除します
	 *
	 * @param url
	 * @return
	 */
	public static String trimParameter(String url) {
		if (url.indexOf("/?") == -1) {
			return url;
		}
		return url.substring(0, url.indexOf("/?") + 1);
	}

	/**
	 * 現在実行中のURLをスキーマを含む絶対パスで取得します。</br>
	 * バリデーションエラーでforwardが行われた場合は、元のクエリー文字列を参照する
	 *
	 * @return 現在実行中のURL
	 */
	public static boolean isError() {
		HttpServletRequest request = RequestUtil.getRequest();

		if (0 <= request.getRequestURI().indexOf("/WEB-INF/view/error/")) {
			return true;
		}

		return false;
	}

}
