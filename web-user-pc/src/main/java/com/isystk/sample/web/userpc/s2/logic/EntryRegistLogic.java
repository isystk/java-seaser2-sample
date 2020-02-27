package com.isystk.sample.web.userpc.s2.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.mai.mail.MailAddress;

import com.google.common.collect.Lists;
import com.isystk.sample.common.config.AppConfigNames;
import com.isystk.sample.common.config.AppMessageNames;
import com.isystk.sample.common.config.Config;
import com.isystk.sample.common.constants.Prefecture;
import com.isystk.sample.common.constants.column.UserStatus;
import com.isystk.sample.common.constants.entity.MaxLength;
import com.isystk.sample.common.exception.IllegalMailAddressException;
import com.isystk.sample.common.s2.dto.SelectBoxDto;
import com.isystk.sample.common.s2.entity.TUser;
import com.isystk.sample.common.s2.entity.TUserOnetimeValid;
import com.isystk.sample.common.s2.logic.MailLogic;
import com.isystk.sample.common.s2.service.TUserOnetimeValidService;
import com.isystk.sample.common.s2.service.TUserService;
import com.isystk.sample.common.util.BeanCopyUtil;
import com.isystk.sample.common.util.DateUtils;
import com.isystk.sample.common.util.NumberUtil;
import com.isystk.sample.common.util.StringUtils;
import com.isystk.sample.web.userpc.constants.Constants;
import com.isystk.sample.web.userpc.s2.dto.entry.EntryRegistDetailDto;
import com.isystk.sample.web.userpc.s2.mai.Mai;
import com.isystk.sample.web.userpc.s2.mai.dto.EntryRegistMailDto;
import com.ozacc.mail.MailSendException;
import com.sun.mail.smtp.SMTPAddressFailedException;

/**
 * 会員登録ロジック
 *
 * @author iseyoshitaka
 */
@Component
public class EntryRegistLogic {

	/** 会員Service */
	@Resource
	protected TUserService tUserService;

	/** 会員-初期承認Service */
	@Resource
	protected TUserOnetimeValidService tUserOnetimeValidService;

	/** メールLogic */
	@Resource
	protected MailLogic mailLogic;

	/** Mai */
	@Resource
	protected Mai mai;

	/** クラス定数 */
	/** 有効な時間 */
	private static final int VALID_TIME = 72;

	/**
	 * 西暦Mapを取得する
	 *
	 * @return 西暦Map
	 */
	public Map<String, String> getYearMap() {

		// 現在の年を取得する
		Date nowDate = DateUtils.getNow();
		Calendar instance = Calendar.getInstance();
		instance.setTime(nowDate);
		Integer year = instance.get(Calendar.YEAR);

		// 西暦Mapを作成する
		Map<String, String> yearMap = new TreeMap<String, String>();
		for (int i = year; i <= year + 2; i++) {
			String strYear = String.valueOf(i);
			yearMap.put(strYear, strYear);
		}

		// 西暦Mapを返却する
		return yearMap;

	}

	/**
	 * 月Mapを取得する
	 *
	 * @return 月Map
	 */
	public Map<String, String> getMonthMap() {

		// 月Mapを生成する
		Map<String, String> monthMap = new TreeMap<String, String>();
		for (int i = 1; i <= 12; i++) {
			String strMonth = String.valueOf(i);
			if (strMonth.length() == 1) {
				strMonth = "0" + strMonth;
			}
			monthMap.put(strMonth, String.valueOf(i));
		}

		// 月Mapを返却する
		return monthMap;

	}

	/**
	 * 会員を登録し、メールを送信する
	 *
	 * @param detailDto
	 *            会員登録詳細Dto
	 */
	public synchronized TUserOnetimeValid insertTUser(EntryRegistDetailDto detailDto) {

		// #9226 すでに仮登録状態のメールアドレスが登録されている場合は、古いデータを除去する
		List<TUser> temporaryUserList = tUserService.findTemporaryUserByMail(detailDto.mail);
		if (!CollectionUtils.isEmpty(temporaryUserList)) {
			List<Integer> idList = CollectionsUtil.newArrayList();
			for (TUser temporaryUser : temporaryUserList) {
				idList.add(temporaryUser.userId);
			}
			tUserOnetimeValidService.deleteByIdList(idList);
			tUserService.deleteBatch(temporaryUserList);
		}

		// 会員エンティティを生成する
		TUser tUser = new TUser();

		// 現在時刻を取得する
		Date now = DateUtils.getNow();

		// DTOからエンティティに値を入替える
		BeanCopyUtil.copy(detailDto, tUser).execute();

		// ステータス
		tUser.status = UserStatus.TEMPORARY.getCode();

		// 登録日時
		tUser.registTime = now;

		// 更新日時
		tUser.updateTime = now;

		// 会員を仮登録する
		tUserService.insertExcludesNull(tUser);

		// 会員-初期承認を登録する
		TUserOnetimeValid tUserOnetimeValid = new TUserOnetimeValid();
		tUserOnetimeValid.userId = tUser.userId;
		String onetimeKey = generateOnetimeKey();
		tUserOnetimeValid.onetimeKey = onetimeKey;
		tUserOnetimeValid.onetimeValidTime = DateUtils.addHour(DateUtils.getNow(), VALID_TIME);
		tUserOnetimeValidService.insertExcludesNull(tUserOnetimeValid);

		// メール送信
		EntryRegistMailDto mailDto = getMailDto(Constants.MAIL01, tUser, onetimeKey);
		try {
			mai.entryRegistF01(mailDto);
		} catch (IllegalArgumentException e) {
			throw new IllegalMailAddressException(AppMessageNames.ERRORS_SENDMAIL.key, "");
		} catch (MailSendException e) {
			Throwable cause = e.getCause();
			if (cause != null) {
				if ((cause.getCause()) instanceof SMTPAddressFailedException) {
					throw new IllegalMailAddressException(AppMessageNames.ERRORS_SENDMAIL.key, "");
				}
			}
			throw e;
		}

		return tUserOnetimeValid;
	}

	/**
	 * 会員の本登録更新処理また、このタイミングでログインするので最終ログイン時間も変更する。
	 *
	 * @param onetimeKey
	 *            ワンタイムキー
	 */
	public void updateTUser(TUserOnetimeValid tUserOnetimeValid) {
		// 更新対象のエンティティを取得します。
		TUser tUser = tUserService.forUpdate(tUserOnetimeValid.userId);

		// 現在時刻を取得する
		Date now = DateUtils.getNow();

		// ステータス
		tUser.status = UserStatus.VALID.getCode();

		// 更新日時
		tUser.updateTime = now;

		// 最終ログイン日時
		tUser.lastLoginTime = now;

		// エンティティを更新します。
		tUserService.update(tUser);

		// メール送信
		EntryRegistMailDto mailDto = getMailDto(Constants.MAIL02, tUser, "");
		mai.entryRegistF02(mailDto);

		// 会員-初期承認を削除する
		tUserOnetimeValidService.delete(tUserOnetimeValid);

	}

	/**
	 * 会員-初期承認Entityを取得する
	 *
	 * @param onetimeKey
	 *            ワンタイムキー
	 * @return 会員Entity
	 */
	public TUserOnetimeValid getTUserOnetimeValid(String onetimeKey) {
		return tUserOnetimeValidService.findByOnetimeKey(onetimeKey);
	}

	/**
	 * メールアドレスの存在チェック
	 *
	 * @param mail
	 *            メールアドレス
	 * @return true:存在する、false:存在しない
	 */
	public boolean checkExistenceMail(String mail) {

		TUser entity = tUserService.findByMail(mail);

		if (null == entity) {
			// 存在しない
			return false;
		}

		// 存在する
		return true;

	}

	/**
	 * ワンタイムキーの存在チェック
	 *
	 */
	public boolean checkExistenceOnetimeKey(String onetimeKey) {

		TUserOnetimeValid entity = getTUserOnetimeValid(onetimeKey);

		if (null == entity) {
			// 存在しない
			return false;
		}

		// 存在する
		return true;

	}

	/**
	 * メールを送受信するための情報を取得します。
	 *
	 * @param id
	 *            メール一覧ID情報
	 * @param entity
	 *            資料請求エンティティ
	 * @return メール本文用Dto
	 */
	private EntryRegistMailDto getMailDto(String mailId, TUser entity, String onetimeKey) {

		// メール本文用Dtoを生成する
		EntryRegistMailDto mailDto = new EntryRegistMailDto();
		List<MailAddress> toList = new ArrayList<MailAddress>();

		// 個別項目を設定する
		// 会員仮登録完了メール
		if (Constants.MAIL01.equals(mailId)) {
			// TO（入力されたメールアドレス）
			toList.add(new MailAddress(entity.mail));
			mailDto.setTo(toList);
			// エラーメール送信先
			mailDto.setReturnPath(mailLogic.getErrorsMail01());
			// お名前（姓）
			mailDto.setFamilyName(entity.familyName);
			// お名前（名）
			mailDto.setName(entity.name);
			// ワンタイムキー
			mailDto.setOnetimeKey(onetimeKey);
		}
		// 会員本登録完了メール
		if (Constants.MAIL02.equals(mailId)) {
			// TO（入力されたメールアドレス）
			toList.add(new MailAddress(entity.mail));
			mailDto.setTo(toList);
			// エラーメール送信先
			mailDto.setReturnPath(mailLogic.getErrorsMail01());
			// お名前（姓）
			mailDto.setFamilyName(entity.familyName);
			// お名前（名）
			mailDto.setName(entity.name);
		}

		// 共通項目を設定する
		// FROM（システム）
		mailDto.setFrom(new MailAddress(mailLogic.getSystemMailInAdminSite()));
		// 受付No
		mailDto.setUserId(String.valueOf(entity.userId));
		// ドメイン
		mailDto.setDomain(Config.getString(AppConfigNames.USERPC_DOMAIN));

		return mailDto;

	}

	/**
	 * ワンタイムキー生成
	 *
	 * @return 生成されたワンタイムキー
	 */
	private String generateOnetimeKey() {
		String onetimeKey = "";
		boolean loopFlg = true;

		do {
			// ランダムな文字列を生成する。
			onetimeKey = RandomStringUtils.randomAlphanumeric(MaxLength.T_USER_ONETIME_VALID_ONETIMEKEY);

			// 生成したキーが存在しないか確認する
			if (null == getTUserOnetimeValid(onetimeKey)) {
				loopFlg = false;
			}
		} while (loopFlg);

		return onetimeKey;
	}

	/**
	 * 都道府県存在チェック
	 *
	 * @param prefectureId
	 *            都道府県ID
	 * @return true：存在する都道府県ID、false:存在しない都道府県ID
	 */
	public boolean checkExistencePrefecture(String prefectureId) {

		// 空の場合は正常とし、確認を行わない
		if (StringUtils.isBlankOrSpace(prefectureId)) {
			return true;
		}

		// Integer型か確認する
		Integer intPreId = NumberUtil.toInteger(prefectureId, null);

		// Integer型でない場合、不正値とする
		if (null == intPreId) {
			return false;
		}

		// 都道府県エンティティリストを取得する
		List<Prefecture> entityList = Lists.newArrayList(Prefecture.ITEMS);

		// 引数の都道府県IDが存在するかチェックする
		for (Prefecture entity : entityList) {
			if (intPreId.equals(entity.getCode())) {
				// 存在する
				return true;
			}
		}

		// 存在しない
		return false;

	}

	/**
	 * 生年月日入力時に使用する西暦リストを取得する
	 *
	 * @param 選択中の値
	 * @return 4桁の西暦リスト（60年前～13年前）
	 */
	public List<SelectBoxDto> getBirthdayYyyyList(String selectVal) {

		// 戻り値
		List<SelectBoxDto> lst = new ArrayList<SelectBoxDto>();

		// 西暦MINを取得（※60年前）
		int yyyyMin = Integer.parseInt(DateUtils.toDateFormat(DateUtils.getNow()).substring(0, 4)) - 60;

		// 西暦MAXを取得（※13年前）
		int yyyyMax = Integer.parseInt(DateUtils.toDateFormat(DateUtils.getNow()).substring(0, 4)) - 13;

		// 西暦リストを生成
		for (int i = yyyyMin; i <= yyyyMax; i++) {
			SelectBoxDto dto = new SelectBoxDto();
			dto.id = String.valueOf(i);
			dto.value = String.valueOf(i);
			if (!StringUtils.isNullOrEmpty(selectVal)) {
				if (selectVal.equals(dto.id)) {
					dto.isDefault = true;
				} else {
					dto.isDefault = false;
				}
			} else {
				if (Constants.BIRTHDAY_DEFAULT_YEAR.equals(dto.id)) {
					dto.isDefault = true;
				} else {
					dto.isDefault = false;
				}
			}
			lst.add(dto);
		}
		return lst;
	}

	/**
	 * 生年月日入力時に使用する月リストを取得する
	 *
	 * @param 選択中の値
	 * @return 2桁の月リスト
	 */
	public List<SelectBoxDto> getBirthdayMmList(String selectVal) {

		// 戻り値
		List<SelectBoxDto> lst = new ArrayList<SelectBoxDto>();

		// 月リストを生成
		for (int i = 1; i <= 12; i++) {
			SelectBoxDto dto = new SelectBoxDto();
			dto.id = String.valueOf(i);
			dto.value = String.valueOf(i);
			if (!StringUtils.isNullOrEmpty(selectVal)) {
				if (selectVal.equals(dto.id)) {
					dto.isDefault = true;
				} else {
					dto.isDefault = false;
				}
			} else {
				dto.isDefault = false;
			}
			lst.add(dto);
		}
		return lst;
	}

	/**
	 * 生年月日入力時に使用する日リストを取得する
	 *
	 * @param 選択中の値
	 * @return 2桁の日リスト
	 */
	public List<SelectBoxDto> getBirthdayDdList(String selectVal) {

		// 戻り値
		List<SelectBoxDto> lst = new ArrayList<SelectBoxDto>();

		// 日リストを生成
		for (int i = 1; i <= 31; i++) {
			SelectBoxDto dto = new SelectBoxDto();
			dto.id = String.valueOf(i);
			dto.value = String.valueOf(i);
			if (!StringUtils.isNullOrEmpty(selectVal)) {
				if (selectVal.equals(dto.id)) {
					dto.isDefault = true;
				} else {
					dto.isDefault = false;
				}
			} else {
				dto.isDefault = false;
			}
			lst.add(dto);
		}
		return lst;
	}

}