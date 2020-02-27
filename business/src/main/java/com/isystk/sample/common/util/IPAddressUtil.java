package com.isystk.sample.common.util;

import javax.servlet.http.HttpServletRequest;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

import com.isystk.sample.common.config.AppConfigNames;
import com.isystk.sample.common.config.Config;

public class IPAddressUtil {

    /**
     * 接続元のIPアドレスを取得します。
     * 
     * @return IPアドレス
     */
    public static String getRealIP() {
	S2Container container = SingletonS2ContainerFactory.getContainer();
	HttpServletRequest request = (HttpServletRequest) container.getComponent(HttpServletRequest.class);
	
	return getRealIP(request);
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
