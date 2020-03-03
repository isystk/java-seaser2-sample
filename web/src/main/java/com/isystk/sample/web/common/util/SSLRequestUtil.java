/*
 * SSLRequestUtil.java
 * 2011/01/28 iseyoshitaka
 */
package com.isystk.sample.web.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;

import com.isystk.sample.common.config.AppConfigNames;
import com.isystk.sample.common.config.Config;

/**
 * SSL接続に関するユーティリティ
 * 
 * @author iseyoshitaka
 */
public class SSLRequestUtil {

	public static final String SSL_REQUEST = "SSL_REQUEST";

	/**
	 * セッションクッキーのセキュア、非セキュアを変更します。
	 * 
	 * @param httpRequest
	 * @param httpResponse
	 * @param isSequre
	 *            セキュアにするかどうか
	 */
	public static void setSessionSecureCookie(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			boolean isSequre) {
		final HttpSession session = httpRequest.getSession(false);
		if (session != null) {
			final Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
			sessionCookie.setMaxAge(Config.getInteger(AppConfigNames.COOKIE_EXPIRES));
			sessionCookie.setSecure(isSequre);
			String contextPath = httpRequest.getContextPath();
			if (contextPath == "") { // default context
				contextPath = "/";
			}

			sessionCookie.setPath(contextPath);
			sessionCookie.setSecure(true);
			httpResponse.addCookie(sessionCookie);
		}
	}

	/**
	 * Secure-Cookieを発行します.
	 */
	public static void publishSecureCookie(HttpServletRequest request) {
		String cookieValue = cookieValue();
		String contextPath = request.getContextPath();
		if (contextPath == "") { // default context
			contextPath = "/";
		}

		CookieUtil.setCookie(SSL_REQUEST, cookieValue, contextPath, Config.getInteger(AppConfigNames.COOKIE_EXPIRES),
				null);
		request.getSession(true).setAttribute(SSL_REQUEST, cookieValue);
	}

	/**
	 * SSL接続によるリクエストであるかどうかを返します.
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @return Secure-Cookieの読み取りに成功した場合 <code>true</code>.
	 */
	public static boolean isSecureRequest(HttpServletRequest request) {
		String cookieVal = CookieUtil.getValue(SSL_REQUEST);
		String sessionVal = (String) request.getSession(true).getAttribute(SSL_REQUEST);

		return cookieVal != null && cookieVal.equals(sessionVal);
	}

	/**
	 * @return Secure-Cookie の値を取得
	 */
	protected static String cookieValue() {
		return RandomStringUtils.randomAlphanumeric(20);
	}
}
