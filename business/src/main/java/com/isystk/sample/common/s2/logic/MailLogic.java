package com.isystk.sample.common.s2.logic;

import com.isystk.sample.common.config.AppConfigNames;
import com.isystk.sample.common.config.Config;

/**
 * 各種メールの送信先のアドレスを取得するロジック
 *
 * @author iseyoshitaka
 *
 */
public class MailLogic {

	/**
	 * システム（Adminサイト）メールアドレスを取得する
	 *
	 * @return
	 */
	public String getSystemMailInAdminSite() {
		return Config.getString(AppConfigNames.ADMIN_SYSTEM_MAIL);
	}

	/**
	 * メールのエラーメール転送先メールアドレスを取得する
	 *
	 * @return
	 */
	public String getErrorsMail01() {
		return Config.getString(AppConfigNames.ERRORS_MAIL_01);
	}

}
