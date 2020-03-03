package com.isystk.sample.web.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.util.TokenProcessor;
import org.seasar.struts.util.RequestUtil;

/**
 * token関連のユーティリティ
 * 
 * @author iseyoshitaka
 * 
 */
public class TokenUtil {

	/**
	 * tokenを取得する( @TokenSet で 設定済みであることを想定したtokenの取得に使用)
	 * 
	 * @return token
	 */
	public static String getToken() {
		String token = null;
		HttpServletRequest request = RequestUtil.getRequest();
		HttpSession session = request.getSession(false);
		if (null == session) {
			return token;
		}
		token = (String) session.getAttribute(Globals.TRANSACTION_TOKEN_KEY);
		return token;
	}

	/**
	 * tokenを設定する(トークンを @TokenSet + インターセプターで設定しないでトークンを設定する場合に使用する )
	 */
	public static void saveToken() {
		HttpServletRequest request = RequestUtil.getRequest();
		TokenProcessor.getInstance().saveToken(request);
	}

}
