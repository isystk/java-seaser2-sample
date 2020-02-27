package com.isystk.sample.web.userpc.s2.form.login;

import java.io.Serializable;

import com.isystk.sample.common.constants.entity.MaxLength;
import com.isystk.sample.web.common.util.ValidateUtil;

import org.apache.struts.action.ActionMessages;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;

/**
 * 初回ログイン入力　アクションフォーム
 * 
 * @author hayashitakehiko
 */
@Component(instance = InstanceType.SESSION)
public abstract class LoginFirstFormBase implements Serializable {

    private static final long serialVersionUID = -8071538955027280254L;

    /** 姓 */
    public String familyName;

    /** 名 */
    public String name;

    /** 姓（カナ） */
    public String familyNameKana;

    /** 名（カナ） */
    public String nameKana;
    
    /** リアルタイムチェック用プロパティ */
    public String propertyName;
    public String propertyValue;

    /**
     * リセットメソッド
     */
    public void resetInput() {
	// 姓
	familyName = "";
	// 名
	name = "";
	// 姓（カナ）
	familyNameKana = "";
	// 名（カナ）
	nameKana = "";
    }

    /**
     * 初回ログイン入力画面のバリデーションチェックです。
     */
    public ActionMessages validate() {

	// エラーメッセージの内容
	ActionMessages messages = new ActionMessages();
	// 姓
	checkFamilyName(messages, familyName);
	// 名
	checkName(messages, name);
	// 姓（カナ）
	checkFamilyNameKana(messages, familyNameKana);
	// 名（カナ）
	checkNameKana(messages, nameKana);

	return messages;
    }

    /**
     * お名前・姓のバリデーションチェックです。
     */
    public static ActionMessages checkFamilyName(ActionMessages messages, String familyName) {

	// 必須入力チェック
	ValidateUtil.requiredChk("familyName", familyName, messages, "お名前・姓");
	// 最長文字数チェック
	ValidateUtil.maxLengthChk("familyName", familyName, MaxLength.T_USER_FAMILYNAME, messages, "お名前・姓");

	return messages;
    }

    /**
     * お名前・名のバリデーションチェックです。
     */
    public static ActionMessages checkName(ActionMessages messages, String name) {

	// 必須入力チェック
	ValidateUtil.requiredChk("name", name, messages, "お名前・名");
	// 最長文字数チェック
	ValidateUtil.maxLengthChk("name", name, MaxLength.T_USER_NAME, messages, "お名前・名");

	return messages;
    }

    /**
     * お名前カナ・セイのバリデーションチェックです。
     */
    public static ActionMessages checkFamilyNameKana(ActionMessages messages, String familyNameKana) {

	// 必須入力チェック
	ValidateUtil.requiredChk("familyNameKana", familyNameKana, messages, "お名前カナ・セイ");

	// 最長文字数チェック
	ValidateUtil.maxLengthChk("familyNameKana", familyNameKana, MaxLength.T_USER_FAMILYNAMEKANA, messages, "お名前カナ・セイ");

	// 全角カタカナチェック
	ValidateUtil.matchesFullWidthKatakanaChk("familyNameKana", familyNameKana, messages, "お名前カナ・セイ");

	return messages;
    }

    /**
     * お名前カナ・メイのバリデーションチェックです。
     */
    public static ActionMessages checkNameKana(ActionMessages messages, String nameKana) {

	// 必須入力チェック
	ValidateUtil.requiredChk("nameKana", nameKana, messages, "お名前カナ・メイ");

	// 最長文字数チェック
	ValidateUtil.maxLengthChk("nameKana", nameKana, MaxLength.T_USER_NAMEKANA, messages, "お名前カナ・メイ");

	// 全角カタカナチェック
	ValidateUtil.matchesFullWidthKatakanaChk("nameKana", nameKana, messages, "お名前カナ・メイ");

	return messages;
    }

    /**
     * メールアドレスのバリデーションチェックです。
     */
    public static ActionMessages checkMail(ActionMessages messages, String mail) {
	// 必須入力チェック
	ValidateUtil.requiredChk("mail", mail, messages, "メールアドレス");
	// 最長文字数チェック
	ValidateUtil.maxLengthChk("mail", mail, MaxLength.T_USER_MAIL, messages, "メールアドレス");
	// メールアドレスチェック
	ValidateUtil.matchesEmailChk("mail", mail, messages, "メールアドレス");

	return messages;
    }
}
