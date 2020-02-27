package com.isystk.sample.web.common.util;

import javax.servlet.http.HttpServletRequest;

import org.seasar.struts.util.RequestUtil;

import com.isystk.sample.common.config.AppConfigNames;
import com.isystk.sample.common.config.Config;

public class IPAddressUtil {

    /**
     * 接続元のIPアドレスを取得します。
     * 
     * @return IPアドレス
     */
    public static String getRealIP() {
	return getRealIP(RequestUtil.getRequest());
    }

    /**
     * 接続元のIPアドレスを取得します。
     * 
     * @param リクエスト情報
     * @return IPアドレス
     */
    public static String getRealIP(HttpServletRequest request) {
	if (request == null) {
	    throw new IllegalArgumentException();
	}
	
	String env = Config.getString(AppConfigNames.ENV);
	String ip = "";
	
	if (env.equals("product") || env.equals("st")) {
	    // 本番・STG1はAkamaiのヘッダーから取得
	    // X-Real-User-IP：[クライアントのソースIP]
	    ip = request.getHeader("X-Real-User-IP");
	} else {
	    ip = request.getRemoteAddr();
	}

	return ip;
    }
}
