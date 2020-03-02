package com.isystk.sample.web.front.s2.form.entry;

import java.io.Serializable;

import org.apache.struts.action.ActionMessages;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;

import com.isystk.sample.common.config.AppMessageNames;
import com.isystk.sample.common.constants.entity.MaxLength;
import com.isystk.sample.web.common.util.ValidateUtil;
import com.isystk.sample.web.front.s2.form.member.MemberEditFormBase;

/**
 * 会員登録アクションフォーム
 *
 * @author iseyoshitaka
 */
@Component(instance = InstanceType.SESSION)
public class EntryRegistForm extends MemberEditFormBase implements Serializable {

	private static final long serialVersionUID = -867718326396000135L;

	/** メールアドレス */
	public String mail;

	/** パスワード */
	public String password;

	/** パスワード(確認用) */
	public String passwordConf;

	/** ワンタイムキー */
	public String onetimeKey;

	/**
	 * 会員登録入力 画面用リセット
	 */
	public void resetIndex() {
		super.resetIndex();
		mail = ""; // メールアドレス
		password = ""; // パスワード
		passwordConf = ""; // パスワード(確認用)
		birthdayYyyy = "";
		birthdayMm = "";
		birthdayDd = "";
	}

	/**
	 * 会員登録内容確認 画面用リセット
	 */
	public void resetConfirm() {
	}

	/**
	 * バリデーション
	 *
	 * @return アクションメッセージ
	 */
	public ActionMessages validate() {
		// アクションメッセージを生成する
		ActionMessages messages = super.validate();

		// メールアドレス
		checkMail(messages, mail);

		// パスワード
		checkPassword(messages, password);

		// パスワード(確認用)
		checkPasswordConf(messages, passwordConf);

		// パスワード確認用と比較
		if (!password.equals(passwordConf)) {
			ValidateUtil.add("password", messages, AppMessageNames.ERRORS_PASS.key, "パスワード");
		}

		// アクションメッセージを返却する
		return messages;

	}

	/**
	 * パスワードのバリデーションチェックです。
	 */
	public static ActionMessages checkPassword(ActionMessages messages, String password) {
		// 必須入力チェック
		ValidateUtil.requiredChk("password", password, messages, "パスワード");
		// 文字数チェック
		ValidateUtil.lengthChk("password", password, MaxLength.PASSWORD_MIN, MaxLength.PASSWORD_MAX, messages, "パスワード");
		// 半角英数、アンダースコア、ハイフン、ドットのマッチチェック
		ValidateUtil.matchesResourceKeyChk("password", password, messages, AppMessageNames.ERRORS_RESOURCEKEY.key,
				"パスワード");

		return messages;
	}

	/**
	 * パスワード（確認用）のバリデーションチェックです。
	 */
	public static ActionMessages checkPasswordConf(ActionMessages messages, String passwordConf) {
		// 必須入力チェック
		ValidateUtil.requiredChk("passwordConf", passwordConf, messages, "パスワード（確認用）");
		// 文字数チェック
		ValidateUtil.lengthChk("passwordConf", passwordConf, MaxLength.PASSWORD_MIN, MaxLength.PASSWORD_MAX, messages,
				"パスワード（確認用）");
		// 半角英数、アンダースコア、ハイフン、ドットのマッチチェック
		ValidateUtil.matchesResourceKeyChk("passwordConf", passwordConf, messages,
				AppMessageNames.ERRORS_RESOURCEKEY.key, "パスワード（確認用）");

		return messages;
	}
}
