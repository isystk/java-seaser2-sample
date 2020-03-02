package com.isystk.sample.web.front.s2.logic;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.seasar.extension.jdbc.exception.SOptimisticLockException;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.struts.exception.ActionMessagesException;
import org.seasar.struts.util.RequestUtil;
import org.seasar.struts.util.ResponseUtil;

import com.isystk.sample.common.config.AppMessageNames;
import com.isystk.sample.common.constants.Constants;
import com.isystk.sample.common.s2.entity.TUser;
import com.isystk.sample.common.s2.service.TUserService;
import com.isystk.sample.common.util.DateUtils;
import com.isystk.sample.web.common.util.CookieUtil;
import com.isystk.sample.web.common.util.SSLRequestUtil;
import com.isystk.sample.web.front.s2.dto.UserDto;

@Component
public class LoginLogic {

	@Resource
	protected TUserService tUserService;

	/**
	 * ユーザ名（メールアドレス）とパスワードからTUserを検索し、正しい場合には、そのTUserを返し、 正しくない場合にはNullを返す。
	 *
	 * @param loginUserName
	 *            ログインユーザ名（メールアドレス）
	 * @param loginUserPassword
	 *            ログインパスワード
	 * @return 正しい場合には、そのTUserを返す。正しくない場合にはNullを返す。
	 */
	public TUser login(String loginUserName, String loginUserPassword) {
		return tUserService.findByMailAndPassword(loginUserName, loginUserPassword);
	}

	public void processlogin(TUser successUser) {
		// すでにユーザーがログインしている場合はまずログオフして新しいUserDtoにする
		HttpSession session = RequestUtil.getRequest().getSession(false);
		if (session != null) {
			session.invalidate();
		}

		try {
			successUser.lastLoginTime = DateUtils.getNow();
			tUserService.update(successUser);
		} catch (SOptimisticLockException e) {
			throw new ActionMessagesException(AppMessageNames.ERRORS_OPTIMISTICLOCK.key);
		}

		UserDto userDto = SingletonS2Container.getComponent(UserDto.class);
		userDto.userId = successUser.userId;
		SSLRequestUtil.setSessionSecureCookie(RequestUtil.getRequest(), ResponseUtil.getResponse(), false);
		CookieUtil.setCookie(Constants.LOGIN_SUCCESS_COOKIE, Boolean.TRUE.toString(), "/", -1, null);

	}

}
