package com.isystk.sample.web.front.s2.form.login;

import java.io.Serializable;

import com.isystk.sample.common.config.AppMessageNames;
import com.isystk.sample.common.constants.entity.MaxLength;
import com.isystk.sample.web.common.util.ValidateUtil;
import com.isystk.sample.web.front.constants.Constants;

import org.apache.struts.action.ActionMessages;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;

/**
 * 初回ログイン入力　アクションフォーム
 * 
 * @author iseyoshitaka
 */
@Component(instance = InstanceType.SESSION)
public class LoginFirstForm extends LoginFirstFormBase implements Serializable {

    private static final long serialVersionUID = -8071538955027280254L;

    /** パスワード */
    public String password;

    /** パスワード（確認用） */
    public String passwordConfirm;

    /** メールアドレス */
    public String mail;

    /** メルマガ受信フラグ */
    public Boolean mailMagazineFlg;

    /** 会員規約とプライバシーポリシーに同意する */
    public Boolean isAgree;

    /** メールアドレス */
    public Boolean isMailValidateOn;

    /**
     * リセットメソッド
     */
    public void resetInput() {
	super.resetInput();
	// パスワード
	password = "";
	// パスワード（確認用）
	passwordConfirm = "";
	// メールアドレス
	mail = "";
	// メルマガ受信フラグ
	mailMagazineFlg = false;
	// 会員規約とプライバシーポリシーに同意する
	isAgree = false;
    }

    /**
     * リセットメソッド
     */
    public void resetConfirm() {
	// メルマガ受信フラグ
	mailMagazineFlg = false;
	// 会員規約とプライバシーポリシーに同意する
	isAgree = false;
    }

    /**
     * 初回ログイン入力画面のバリデーションチェックです。
     */
    public ActionMessages validate() {
	// エラーメッセージの内容
	ActionMessages messages = super.validate();
	// パスワード
	checkPassword(messages, password);

	// パスワード(確認用)
	checkPasswordConfirm(messages, passwordConfirm);

	// パスワードとパスワード（確認用）を参照
	if (password.equals(passwordConfirm)) {
	    // パスワードとパスワード（確認用）が一致の場合
	    // --> 最小桁数チェック
	    ValidateUtil.minLengthChk("password", password, Constants.PASSEWORD_MIN_LENGTH, messages, "パスワード");
	    // --> 使用可能文字列のチェック
	    ValidateUtil.matchesResourceKeyChk("password", password, messages, AppMessageNames.ERRORS_RESOURCEKEY.key, "パスワード");

	} else {
	    // パスワードとパスワード（確認用）が不一致の場合
	    // --> エラーメッセージに追加
	    ValidateUtil.add("password", messages, AppMessageNames.ERRORS_PASS.key, "パスワード");
	}

	// メールアドレス
	checkMail(messages);

	// 会員規約とプライバシーポリシーに同意する
	checkIsAgree(messages);

	return messages;
    }
    
    /**
     * メールアドレスのバリデーションチェックです。
     */
    private ActionMessages checkMail(ActionMessages messages) {

	if (isMailValidateOn) {
	    checkMail(messages, mail);
	}
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
	ValidateUtil.matchesResourceKeyChk("password", password, messages, AppMessageNames.ERRORS_RESOURCEKEY.key, "パスワード");

	return messages;
    }

    /**
     * パスワード(確認用)のバリデーションチェックです。
     */
    public static ActionMessages checkPasswordConfirm(ActionMessages messages, String passwordConfirm) {

	// 必須入力チェック
	ValidateUtil.requiredChk("passwordConfirm", passwordConfirm, messages, "パスワード（確認用）");
	// 文字数チェック
	ValidateUtil.lengthChk("passwordConfirm", passwordConfirm, MaxLength.PASSWORD_MIN, MaxLength.PASSWORD_MAX, messages, "パスワード（確認用）");
	// 半角英数、アンダースコア、ハイフン、ドットのマッチチェック
	ValidateUtil.matchesResourceKeyChk("passwordConfirm", passwordConfirm, messages, AppMessageNames.ERRORS_RESOURCEKEY.key, "パスワード（確認用）");

	return messages;
    }

    /**
     * 会員規約とプライバシーポリシーに同意するのバリデーションチェックです。
     */
    private ActionMessages checkIsAgree(ActionMessages messages) {

	if (!isAgree) {
	    // 会員規約とプライバシーポリシーに同意するがチェックOFFの場合
	    // --> エラーメッセージに追加
	    ValidateUtil.add("isAgree", messages, AppMessageNames.ERRORS_ANY.key, "「利用規約」「会員規約」「個人情報の取り扱いについて」に同意して頂く必要があります。");
	}
	return messages;
    }
}
