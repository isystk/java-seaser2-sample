package com.isystk.sample.web.common.util;

import javax.servlet.http.HttpServletResponse;

import org.seasar.framework.util.StringUtil;
import org.seasar.struts.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author iseyoshitaka
 *
 */
public class HttpResponseUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpResponseUtil.class);

	/**
	 * 301リダイレクト設定
	 */
	public static void setRedirect301(String url) {
		// 301リダイレクト設定
		HttpServletResponse response = ResponseUtil.getResponse();
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader("Location", url);
	}

	/**
	 * 302リダイレクト設定
	 */
	public static void setRedirect302(String url) {
		// 301リダイレクト設定
		HttpServletResponse response = ResponseUtil.getResponse();
		response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", url);
	}

	/**
	 * Akamaiでキャッシュしないように設定
	 */
	public static void setNoStore() {
		HttpServletResponse response = ResponseUtil.getResponse();
		response.setHeader("Edge-Control", "no-store");
	}

	/**
	 * Akamaiキャッシュタグを付与する
	 */
	public static void setAkamaiCacheTag(String value) {
		if (StringUtil.isNotEmpty(value)) {
			HttpServletResponse response = ResponseUtil.getResponse();
			response.setHeader("Edge-Cache-Tag", value);
		}
	}

	/**
	 * API(ajax)をクロスドメイン対応にするように設定
	 */
	public static void setCrossDomainSupport() {
		HttpServletResponse response = ResponseUtil.getResponse();
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("AMP-Access-Control-Allow-Source-Origin", "*");
		response.setHeader("Access-Control-Expose-Headers", "AMP-Access-Control-Allow-Source-Origin");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
	}

}
