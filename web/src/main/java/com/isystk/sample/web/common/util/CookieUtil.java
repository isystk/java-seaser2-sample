package com.isystk.sample.web.common.util;

import javax.servlet.http.Cookie;

import org.seasar.struts.util.RequestUtil;
import org.seasar.struts.util.ResponseUtil;

import com.isystk.sample.common.util.NumberUtil;

public class CookieUtil {

	/**
	 * クッキーを取得します。(文字列)
	 * 
	 * @param name
	 *            保存名
	 * @return Cookie
	 */
	public static String getValue(String name) {
		if (name == null || RequestUtil.getRequest() == null) {
			throw new IllegalArgumentException();
		}
		if (RequestUtil.getRequest().getCookies() == null) {
			return null;
		}
		for (Cookie cookie : RequestUtil.getRequest().getCookies()) {
			if (name.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}

	/**
	 * クッキーを取得します。(数値)
	 * 
	 * @param name
	 *            保存名
	 * @return Cookie
	 */
	public static Integer getValueInteger(String name) {
		String value = getValue(name);

		Integer result = null;
		if (value != null) {
			result = NumberUtil.toInteger(value);
		}

		return result;
	}

	/**
	 * クッキーを保存します。
	 * 
	 * @param name
	 *            保存名
	 * @param value
	 *            値
	 */
	public static void setValue(String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(30);
		cookie.setPath("/");
		cookie.setSecure(true);
		ResponseUtil.getResponse().addCookie(cookie);
	}

	/**
	 * クッキーを保存します。(内容は細かく指定可能)
	 * 
	 * @param key
	 *            Cookie 名
	 * @param value
	 *            値
	 * @param path
	 *            パス
	 * @param maxAge
	 *            寿命
	 * @param domainName
	 *            ドメイン名
	 * @param isSecure
	 *            セキュアクッキーかどうか
	 */
	public static void setCookie(String key, String value, String path, int maxAge, String domainName) {
		Cookie cookie = new Cookie(key, value);
		if (domainName != null) {
			cookie.setDomain(domainName);
		}
		cookie.setMaxAge(maxAge);
		cookie.setSecure(true);
		cookie.setPath(path);

		ResponseUtil.getResponse().addCookie(cookie);
	}

	/**
	 * クッキーを削除します
	 * 
	 * @param key
	 *            Cookie名
	 */
	public static void removeCookie(String key) {
		if (key == null || RequestUtil.getRequest() == null) {
			throw new IllegalArgumentException();
		}
		if (RequestUtil.getRequest().getCookies() == null) {
			return;
		}
		for (Cookie cookie : RequestUtil.getRequest().getCookies()) {
			if (key.equals(cookie.getName())) {
				Cookie targetCookie = new Cookie(key, null);
				targetCookie.setMaxAge(0);
				targetCookie.setPath("/");
				ResponseUtil.getResponse().addCookie(targetCookie);
				return;
			}
		}
	}

}
