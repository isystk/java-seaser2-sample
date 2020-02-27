package com.isystk.sample.web.userpc.sastruts;

import org.seasar.struts.util.RequestUtil;
import org.seasar.struts.util.ResponseUtil;

import com.isystk.sample.common.constants.Constants;
import com.isystk.sample.web.common.sastruts.AbstractRequestProcessor;
import com.isystk.sample.web.common.util.CookieUtil;
import com.isystk.sample.web.common.util.SSLRequestUtil;
import com.isystk.sample.web.userpc.constants.Url;

public class UserPcRequestProcessor extends AbstractRequestProcessor {

	public static final String TRACKING_ID_KEY = "trackingId";

	@Override
	public String getLoginUrl() {
		return Url.userpcLogin.getAbsolute();
	}

	@Override
	protected void processLoginSuccess() {
		String value = CookieUtil.getValue(Constants.LOGIN_SUCCESS_COOKIE);
		if (Boolean.TRUE.toString().equalsIgnoreCase(value)) {
			SSLRequestUtil.setSessionSecureCookie(RequestUtil.getRequest(), ResponseUtil.getResponse(), false);
		}
		super.processLoginSuccess();
	}

}
