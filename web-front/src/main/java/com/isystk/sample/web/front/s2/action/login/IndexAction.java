package com.isystk.sample.web.front.s2.action.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.util.RequestUtil;

import com.isystk.sample.common.config.AppMessageNames;
import com.isystk.sample.common.s2.entity.TUser;
import com.isystk.sample.common.s2.service.TUserService;
import com.isystk.sample.web.common.annotation.NoAllowDirectAccessCheck;
import com.isystk.sample.web.common.annotation.SSL;
import com.isystk.sample.web.front.constants.Url;
import com.isystk.sample.web.front.s2.dto.UserDto;
import com.isystk.sample.web.front.s2.form.login.LoginForm;
import com.isystk.sample.web.front.s2.logic.LoginLogic;

public class IndexAction {

	@Resource
	@ActionForm
	public LoginForm loginForm;

	@Resource
	protected LoginLogic loginLogic;

	@Resource
	protected TUserService tUserService;

	@SSL
	@Execute(validator = false)
	public String index() {

		// 既にログイン済みの場合には、トップページへ遷移する。
		if (UserDto.getUserDto() != null) {
			return Url.frontTop.getAbsolute() + "?redirect=true";
		}

		return showIndex();
	}

	@SSL
	@NoAllowDirectAccessCheck
	@Execute(validator = false)
	public String showIndex() {
		return "index_index.jsp";
	}
	//
	// @SSL
	// @NoAllowDirectAccessCheck
	// @Execute(validator = false)
	// public String showMypoBindAuthentication() {
	// return "index_mypoBindAuthentication.jsp";
	// }

	@Resource
	protected HttpServletRequest request;

	protected String executeLogin(TUser successUser, Url redirect) {
		if (successUser != null) {

			loginLogic.processlogin(successUser);

			if (redirect != null) {
				return redirect.getAbsolute() + "?redirect=true";
			}

			if (StringUtils.isEmpty(loginForm.backUrl)) {
				return Url.frontTop.getAbsolute() + "?redirect=true";
			}
			String ret = loginForm.backUrl;
			if (ret.indexOf("?") < 0) {
				ret += "?";
			} else {
				ret += "&";
			}
			return ret + "redirect=true";
		}
		loginForm.loginUserPassword = "";

		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage(AppMessageNames.ERRORS_LOGIN_INVALIDIDORPASSWORD.key));
		RequestUtil.getRequest().setAttribute(Globals.ERROR_KEY, messages);

		return showIndex();
	}

	@SSL
	@Execute(validate = "validateLogin", input = "showIndex")
	public String login() {
		TUser successUser = loginLogic.login(loginForm.loginUserName, loginForm.loginUserPassword);
		return this.executeLogin(successUser, null);
	}

	@SSL
	@Execute(validator = false)
	public String logout() {
		// ログオフ
		HttpSession session = RequestUtil.getRequest().getSession(false);
		if (session != null) {
			session.invalidate();
		}

		String returnUrl = loginForm.backUrl;

		if (StringUtils.isEmpty(loginForm.backUrl)) {
			returnUrl = Url.frontTop.getAbsolute();
		}

		if (returnUrl != null && StringUtils.isNotEmpty(returnUrl)) {
			return returnUrl + "?redirect=true";
		} else {
			return Url.frontTop.getAbsolute() + "?redirect=true";
		}
	}

}
