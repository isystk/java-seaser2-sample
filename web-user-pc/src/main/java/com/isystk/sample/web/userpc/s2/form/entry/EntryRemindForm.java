package com.isystk.sample.web.userpc.s2.form.entry;

import java.io.Serializable;

import org.apache.struts.action.ActionMessages;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;

import com.isystk.sample.common.config.AppMessageNames;
import com.isystk.sample.common.constants.entity.MaxLength;
import com.isystk.sample.web.common.util.ValidateUtil;

/**
 * パスワードリマインダ機能のActionフォーム
 *
 * @author iseyoshitaka
 */
@Component(instance = InstanceType.SESSION)
public class EntryRemindForm implements Serializable {

	private static final long serialVersionUID = 3686262198791559943L;

	/** メールアドレス */
	public String mailAddress;

	/** ワンタイムキー */
	public String onetimeKey;

	/** パスワード */
	public String password;

	/** パスワード(確認用) */
	public String passwordConfirm;

	/** リアルタイムチェック用プロパティ */
	public String propertyName;
	public String propertyValue;

	/**
	 * リセットメソッド<br>
	 * (メールアドレス確認画面へ遷移する際)
	 */
	public void resetConfirm() {
		mailAddress = "";
	}

	/**
	 * バリデーション<br>
	 * (メールアドレス確認画面へ遷移する際)
	 */
	public ActionMessages validateConfirm() {
		ActionMessages messages = new ActionMessages();

		// メールアドレス
		checkMailAddress(messages, mailAddress);

		return messages;
	}

	/**
	 * リセットメソッド<br>
	 * (パスワード設定完了画面へ遷移する際)
	 */
	public void resetComplete() {
		onetimeKey = "";
		password = "";
		passwordConfirm = "";
	}

	/**
	 * バリデーション<br>
	 * (パスワード設定完了画面へ遷移する際)
	 */
	public ActionMessages validateComplete() {
		ActionMessages messages = new ActionMessages();

		// パスワード
		checkPassword(messages, password);

		// パスワード(確認用)
		checkPasswordConfirm(messages, passwordConfirm);

		if (!password.equals(passwordConfirm)) {
			ValidateUtil.add("password", messages, AppMessageNames.ERRORS_PASS.key, "パスワード");
		}
		return messages;
	}

	/**
	 * メールアドレスのバリデーションチェックです。
	 */
	public static ActionMessages checkMailAddress(ActionMessages messages, String mailAddress) {
		// 必須入力チェック
		ValidateUtil.requiredChk("mailAddress", mailAddress, messages, "メールアドレス");
		// 最大桁数チェック
		ValidateUtil.maxLengthChk("mailAddress", mailAddress, MaxLength.T_USER_MAIL, messages, "メールアドレス");
		// メールアドレス形式チェック
		ValidateUtil.matchesEmailChk("mailAddress", mailAddress, messages, "メールアドレス");

		return messages;
	}

	/**
	 * パスワードのバリデーションチェックです。
	 */
	public static ActionMessages checkPassword(ActionMessages messages, String password) {
		// 必須入力チェック
		ValidateUtil.requiredChk("password", password, messages, "パスワード");
		// 桁数チェック
		ValidateUtil.lengthChk("password", password, MaxLength.PASSWORD_MIN, MaxLength.PASSWORD_MAX, messages, "パスワード");
		// 文字チェック
		ValidateUtil.matchesResourceKeyChk("password", password, messages, AppMessageNames.ERRORS_RESOURCEKEY.key,
				"パスワード");

		return messages;
	}

	/**
	 * パスワード(確認用)のバリデーションチェックです。
	 */
	public static ActionMessages checkPasswordConfirm(ActionMessages messages, String passwordConfirm) {
		// 必須入力チェック
		ValidateUtil.requiredChk("passwordConfirm", passwordConfirm, messages, "パスワード(確認用)");
		// 桁数チェック
		ValidateUtil.lengthChk("passwordConfirm", passwordConfirm, MaxLength.PASSWORD_MIN, MaxLength.PASSWORD_MAX,
				messages, "パスワード(確認用)");
		// 文字チェック
		ValidateUtil.matchesResourceKeyChk("passwordConfirm", passwordConfirm, messages,
				AppMessageNames.ERRORS_RESOURCEKEY.key, "パスワード(確認用)");

		return messages;
	}
}
