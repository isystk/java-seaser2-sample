package com.isystk.sample.web.front.s2.form.member;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionMessages;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;

import com.isystk.sample.common.config.AppMessageNames;
import com.isystk.sample.common.constants.column.Sex;
import com.isystk.sample.common.constants.entity.MaxLength;
import com.isystk.sample.common.util.NumberUtil;
import com.isystk.sample.web.common.util.ValidateUtil;
import com.isystk.sample.web.front.s2.form.login.LoginFirstFormBase;

/**
 * 会員情報変更処理機能のアクションフォーム
 *
 * @author iseyoshitaka
 *
 */
@Component(instance = InstanceType.SESSION)
public abstract class MemberEditFormBase extends LoginFirstFormBase implements Serializable {
	private static final long serialVersionUID = -1923173202459666897L;

	/** 郵便番号 */
	public String zip;

	/** 都道府県 */
	public String prefectureId;

	/** 市区町村 */
	public String area;

	/** 町名番地 */
	public String address;

	/** 建物名 */
	public String building;

	/** 電話番号 */
	public String tel;

	/** 性別 */
	public String sex;

	/** 生年月日 */
	public String birthday;

	/** 生年月日（年） */
	public String birthdayYyyy;
	/** 生年月日（月） */
	public String birthdayMm;
	/** 生年月日（日） */
	public String birthdayDd;

	/**
	 * リセット
	 */
	public void resetIndex() {
		super.resetInput();
		zip = "";
		prefectureId = "";
		area = "";
		address = "";
		building = "";
		tel = "";
		sex = "";
		birthday = "";
	}

	/**
	 * バリデーション
	 */
	public ActionMessages validate() {
		ActionMessages messages = super.validate();

		// 郵便番号
		checkZip(messages, zip);

		// 市区町村
		checkArea(messages, area);

		// 町名番地
		checkAddress(messages, address);

		// 建物名
		checkBuilding(messages, building);

		// 電話番号
		checkTel(messages, tel);

		// 性別
		// 必須入力チェック
		ValidateUtil.requiredChk("sex", sex, messages, "性別");
		// 存在チェック
		if (Sex.get(sex) == null) {
			ValidateUtil.add("sex", messages, AppMessageNames.ERRORS_INVALID.key, "性別");
		}

		// 生年月日
		birthday = editBirthDay();
		checkBirthday(messages, birthday);

		return messages;

	}

	/**
	 * 生年月日プルダウンの値から生年月日の文字列を生成するメソッドです。
	 */
	private String editBirthDay() {
		String rtn = "";
		if (StringUtils.isNotEmpty(birthdayYyyy) && StringUtils.isNotEmpty(birthdayMm)
				&& StringUtils.isNotEmpty(birthdayDd)) {
			rtn = rtn + birthdayYyyy;
			rtn = rtn + String.format("%1$02d", NumberUtil.toInteger(birthdayMm, null));
			rtn = rtn + String.format("%1$02d", NumberUtil.toInteger(birthdayDd, null));
		}
		return rtn;
	}

	/**
	 * 郵便番号のバリデーションチェックです。
	 */
	public static ActionMessages checkZip(ActionMessages messages, String zip) {
		// 郵便番号チェック
		ValidateUtil.matchesPostCodeChk("zip", zip, messages, "郵便番号");

		return messages;
	}

	/**
	 * 市区町村のバリデーションチェックです。
	 */
	public static ActionMessages checkArea(ActionMessages messages, String area) {
		// 最長文字数チェック
		ValidateUtil.maxLengthChk("area", area, MaxLength.T_USER_AREA, messages, "市区町村");

		return messages;
	}

	/**
	 * 町名番地のバリデーションチェックです。
	 */
	public static ActionMessages checkAddress(ActionMessages messages, String address) {
		// 最長文字数チェック
		ValidateUtil.maxLengthChk("address", address, MaxLength.T_USER_ADDRESS, messages, "町名番地");

		return messages;
	}

	/**
	 * 建物名のバリデーションチェックです。
	 */
	public static ActionMessages checkBuilding(ActionMessages messages, String building) {
		// 最長文字数チェック
		ValidateUtil.maxLengthChk("building", building, MaxLength.T_USER_BUILDING, messages, "建物名");

		return messages;
	}

	/**
	 * 電話番号のバリデーションチェックです。
	 */
	public static ActionMessages checkTel(ActionMessages messages, String tel) {
		// 最長文字数チェック
		ValidateUtil.maxLengthChk("tel", tel, MaxLength.T_USER_TEL, messages, "電話番号");
		// 電話番号チェック
		ValidateUtil.matchesPhoneSevereChk("tel", tel, messages, "電話番号");

		return messages;
	}

	/**
	 * 生年月日のバリデーションチェックです。
	 */
	public static ActionMessages checkBirthday(ActionMessages messages, String birthday) {
		// 日付妥当性チェック
		ValidateUtil.matchesManualEntryDateChk("birthday", birthday, messages, "生年月日");

		return messages;
	}

}
