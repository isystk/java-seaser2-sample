package com.isystk.sample.web.front.s2.form.login;

import com.isystk.sample.common.constants.entity.MaxLength;
import com.isystk.sample.web.common.sastruts.URLParam;
import com.isystk.sample.web.common.util.ValidateUtil;

import org.apache.struts.action.ActionMessages;

public class LoginForm {

    public String loginUserName;

    public String loginUserPassword;

    @URLParam
    public String backUrl;

    public ActionMessages validateLogin() {
	ActionMessages messages = new ActionMessages();

	ValidateUtil.requiredChk("loginUserName", loginUserName, messages, "メールアドレス");
	ValidateUtil.maxLengthChk("loginUserName", loginUserName, MaxLength.T_USER_MAIL, messages, "メールアドレス");
	ValidateUtil.requiredChk("loginUserPassword", loginUserPassword, messages, "パスワード");
	ValidateUtil.maxLengthChk("loginUserPassword", loginUserPassword, MaxLength.T_USER_PASSWORD, messages, "パスワード");

	return messages;
    }

    public ActionMessages validateMypoBindAuthentication() {
	ActionMessages messages = new ActionMessages();

	ValidateUtil.requiredChk("loginUserPassword", loginUserPassword, messages, "パスワード");
	ValidateUtil.maxLengthChk("loginUserPassword", loginUserPassword, MaxLength.T_USER_PASSWORD, messages, "パスワード");

	return messages;
    }

}