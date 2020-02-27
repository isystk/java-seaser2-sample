/*
 * BooleanUtil.java
 * 2011/04/26 mmatsumoto
 */
package com.isystk.sample.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.util.StringUtil;

/**
 * @author iseyoshitaka
 * 
 */
public class UrlUtil {

    /**
     * 現在表示中のURLを返す(フルパス)
     */
    public static String getUrl() {
	S2Container container = SingletonS2ContainerFactory.getContainer();
	HttpServletRequest request = (HttpServletRequest) container.getComponent(HttpServletRequest.class);
	if (request == null) {
	    return "";
	}
	return getUrl(request.isSecure());
    }

    /**
     * 現在表示中のURLを返し、Httpsかどうかを切り替えることができる(フルパス)
     * 
     * @param isSecure httpsかどうか
     * @return　フルパス
     */
    public static String getUrl(boolean isSecure) {
	S2Container container = SingletonS2ContainerFactory.getContainer();
	HttpServletRequest request = (HttpServletRequest) container.getComponent(HttpServletRequest.class);
	String protcol = isSecure ? "https://" : "http://";
	String host = request.getServerName();
	String port = (request.getLocalPort() == 8080) ? ":8080" : "";
	String context = request.getContextPath();

	return protcol + host + port + context + currentRequestUrl();
    }

    /**
     * 現在のコンテキストパスを返す
     * 
     * @return　コンテキストパス
     */
    public static String currentContextPath() {
	S2Container container = SingletonS2ContainerFactory.getContainer();
	HttpServletRequest request = (HttpServletRequest) container.getComponent(HttpServletRequest.class);
	return request.getContextPath();
    }

    /**
     * 現在のリクエストのURLを作成する
     */
    public static String currentRequestUrl() {
	S2Container container = SingletonS2ContainerFactory.getContainer();
	@SuppressWarnings("rawtypes")
	Map requestScope = (Map) container.getComponent("requestScope");
	String servletPath = (String) requestScope.get("javax.servlet.forward.servlet_path");
	String queryString = (String) requestScope.get("javax.servlet.forward.query_string");

	String backUrl = servletPath + (queryString == null ? "" : "?" + queryString);
	return backUrl;
    }

    /**
     * 現在のリクエストのパスを作成する
     */
    public static String currentRequestUri() {
	S2Container container = SingletonS2ContainerFactory.getContainer();
	@SuppressWarnings("rawtypes")
	Map requestScope = (Map) container.getComponent("requestScope");
	return (String) requestScope.get("javax.servlet.forward.request_uri");
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
     * URLから指定したクエリーを除去する
     * 
     * @param url Url.getCurrentUrlで生成したURL
     * @return 静的化後のURL
     */
    public static String removeURLFromObject(String searchUrl, String removeQuery) {

	// クエリ全体
	String queryString = "";
	// 加工不要なパラメータを保持
	List<String> params = new ArrayList<String>();

	String url = makeUrlParamMap(searchUrl).get("URL");
	String formParam = makeUrlParamMap(searchUrl).get("PARAM");

	String[] formParams = (StringUtil.isEmpty(formParam)) ? new String[0] : formParam.split("&");

	for (int i = 0, len = formParams.length; i < len; i++) {
	    int idx = formParams[i].indexOf("=");
	    if (idx >= 0) {
		String query = formParams[i].substring(0, idx);
		if (query.equals(removeQuery)) {
		    continue;
		}
	    }
	    params.add(formParams[i]);
	}

	if (0 < params.size()) {
	    queryString += '?' + StringUtils.join(params, "&");
	}

	return url + queryString;
    }

    /**
     * requestScope 中のパラメータつきURLから指定したクエリーを除去する
     * 
     * @param url 元の url
     * @param requestScope requestScope
     * @param removeQueries 抜き出すパラメータ
     * @return 指定のパラメータを抜き出した URL
     */
    @SuppressWarnings("rawtypes")
    public static String getUrlWithQueryString(String url, Map requestScope, String... removeQueries) {
	if (url == null || requestScope == null) {
	    return null;
	}
	String queryString = (String) requestScope.get("javax.servlet.forward.query_string");
	if (StringUtil.isNotEmpty(queryString)) {
	    url = url + "?" + queryString;
	    if (removeQueries != null) {
		for (String removeQuery : removeQueries) {
		    url = removeURLFromObject(url, removeQuery);
		}
	    }
	}
	return url;
    }

    /**
     * urlに302リダイレクト用のクエリを追加する
     * 
     * @param url 元の url
     * @return 302リダイレクト用クエリを追加した URL
     */
    public static String addRedirectParam(String url) {

	if (StringUtil.isEmpty(url)) {
	    return "";
	}

	String redirectStr = "redirect=true";
	StringBuilder redirectUrl = new StringBuilder(url);

	if (0 <= url.indexOf("?")) {
	    redirectUrl.append("&").append(redirectStr);
	} else {
	    redirectUrl.append("?").append(redirectStr);
	}
	return redirectUrl.toString();
    }

}
