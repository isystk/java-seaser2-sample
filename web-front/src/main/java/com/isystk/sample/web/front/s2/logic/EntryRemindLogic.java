package com.isystk.sample.web.front.s2.logic;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.mai.mail.MailAddress;

import com.isystk.sample.common.config.AppConfigNames;
import com.isystk.sample.common.config.Config;
import com.isystk.sample.common.constants.entity.MaxLength;
import com.isystk.sample.common.s2.entity.TUser;
import com.isystk.sample.common.s2.entity.TUserOnetimePass;
import com.isystk.sample.common.s2.logic.MailLogic;
import com.isystk.sample.common.s2.service.TUserOnetimePassService;
import com.isystk.sample.common.s2.service.TUserService;
import com.isystk.sample.common.util.CipherUtils;
import com.isystk.sample.common.util.DateUtils;
import com.isystk.sample.web.front.s2.mai.Mai;
import com.isystk.sample.web.front.s2.mai.dto.PasswordRemindMailDto;

@Component
public class EntryRemindLogic {

	/** 会員Service */
	@Resource
	protected TUserService tUserService;

	/** 会員-パスワード変更Service */
	@Resource
	protected TUserOnetimePassService tUserOnetimePassService;

	/** Mai */
	@Resource
	protected Mai mai;

	/** メールLogic */
	@Resource
	protected MailLogic mailLogic;

	/** ワンタイムキー有効期間 */
	private static final int VALID_TIME = 24;

	/**
	 * 入力されたメールアドレスが、現在有効な会員情報の中に存在するかどうか
	 *
	 * @param mailAddress
	 *            入力メールアドレス
	 * @return 存在する場合：true、存在しない場合：false
	 */
	public boolean checkExistMail(String mailAddress) {

		TUser user = tUserService.findByMail(mailAddress);
		if (null != user) {
			// 入力メールアドレスと、一致するPCメールアドレスが存在する場合
			return true;
		}
		return false;
	}

	/**
	 * URLに記載されたワンタイムキーがDBに存在するかどうか
	 *
	 * @param onetimeKey
	 *            URLに記載されたワンタイムキー
	 * @return 存在する場合：true、存在しない場合：false
	 */
	public boolean checkExistOnetimeKey(String onetimeKey) {
		TUserOnetimePass tUserOnetimePass = tUserOnetimePassService.findByOnetimeKey(onetimeKey);
		if (null != tUserOnetimePass) {
			return true;
		}
		return false;
	}

	/**
	 * ワンタイムキーが記載された、パスワードリマインドメールを送信する
	 *
	 * @param mailAddress
	 *            入力されたメールアドレス
	 */
	public void mail(String mailAddress) {

		// 入力されたメールアドレスより、会員情報を取得する
		TUser user = tUserService.findByMail(mailAddress);

		// ワンタイムキーの生成
		String onetimeKey = this.generateOnetimeKey();

		// 会員 - パスワード変更テーブルへ登録
		TUserOnetimePass deleteTUserOnetimePass = tUserOnetimePassService.findById(user.userId);
		if (null != deleteTUserOnetimePass) {
			// 既にレコードが存在する場合は、以前のデータを消してから新しいデータを作成するので、物理削除する
			tUserOnetimePassService.delete(deleteTUserOnetimePass);
		}
		TUserOnetimePass tUserOnetimePass = new TUserOnetimePass();
		tUserOnetimePass.userId = user.userId;
		tUserOnetimePass.onetimeKey = onetimeKey;
		tUserOnetimePass.onetimeValidTime = DateUtils.addHour(DateUtils.getNow(), VALID_TIME);
		tUserOnetimePassService.insertExcludesNull(tUserOnetimePass);

		// F03メール送信処理
		PasswordRemindMailDto mailDto = this.makeMailDto(user, mailAddress, onetimeKey);
		mai.passwordRemindF03(mailDto);
	}

	/**
	 * 入力されたパスワードで、会員情報を更新する
	 *
	 * @param onetimeKey
	 *            URLに記載されたワンタイムキー
	 * @param password
	 *            入力されたパスワード
	 */
	public void updateTUser(String onetimeKey, String password) {

		// ワンタイムキーより、会員情報を取得する
		TUserOnetimePass tUserOnetimePass = tUserOnetimePassService.findByOnetimeKey(onetimeKey);
		TUser tUser = tUserService.forUpdate(tUserOnetimePass.userId);

		// 更新情報の設定
		tUser.password = CipherUtils.sha1(password).getBytes();
		tUser.updateTime = DateUtils.getNow();

		// ワンタイムキー(会員-パスワード変更)情報を削除する
		tUserOnetimePassService.delete(tUserOnetimePass);

		tUserService.update(tUser);
	}

	/**
	 * ワンタイムキーを生成する
	 *
	 * @return 生成されたワンタイムキー
	 */
	private String generateOnetimeKey() {

		String onetimeKey = "";
		boolean loopFlg = true;

		do {
			// ランダムな文字列を生成する。
			onetimeKey = RandomStringUtils.randomAlphanumeric(MaxLength.T_USER_ONETIME_PASS_ONETIMEKEY);

			// 生成したキーが存在しないか確認する
			if (null == tUserOnetimePassService.findByOnetimeKey(onetimeKey)) {
				loopFlg = false;
			}
		} while (loopFlg);

		return onetimeKey;
	}

	/**
	 * メール送信内容の情報を作成する
	 *
	 * @param user
	 *            会員情報
	 * @param mailAddress
	 *            入力されたメールアドレス
	 * @param onetimeKey
	 *            生成したワンタイムキー
	 * @return 作成したメールDTO
	 */
	private PasswordRemindMailDto makeMailDto(TUser user, String mailAddress, String onetimeKey) {

		PasswordRemindMailDto mailDto = new PasswordRemindMailDto();
		List<MailAddress> toList = new ArrayList<MailAddress>();

		// TO
		toList.add(new MailAddress(mailAddress));
		mailDto.setTo(toList);
		// FROM（システム）
		mailDto.setFrom(new MailAddress(mailLogic.getSystemMailInAdminSite()));
		// ドメイン
		mailDto.setDomain(Config.getString(AppConfigNames.USERPC_DOMAIN));
		// エラーメール送信先
		mailDto.setReturnPath(mailLogic.getErrorsMail01());
		// 本文記載内容
		mailDto.setFamilyName(user.familyName);
		mailDto.setName(user.name);
		mailDto.setOnetimeKey(onetimeKey);

		return mailDto;
	}

}
