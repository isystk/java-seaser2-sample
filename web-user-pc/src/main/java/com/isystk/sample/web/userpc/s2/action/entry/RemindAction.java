package com.isystk.sample.web.userpc.s2.action.entry;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionMessages;
import org.seasar.extension.jdbc.exception.SOptimisticLockException;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.exception.ActionMessagesException;
import org.seasar.struts.util.RequestUtil;
import org.seasar.struts.util.ResponseUtil;
import org.seasar.struts.util.S2ActionMappingUtil;

import com.isystk.sample.common.config.AppMessageNames;
import com.isystk.sample.common.constants.Constants;
import com.isystk.sample.common.exception.NotFoundException;
import com.isystk.sample.common.util.AgentCipher;
import com.isystk.sample.common.util.StringUtils;
import com.isystk.sample.web.common.annotation.NoAllowDirectAccessCheck;
import com.isystk.sample.web.common.annotation.SSL;
import com.isystk.sample.web.common.sastruts.token.TokenCheck;
import com.isystk.sample.web.common.sastruts.token.TokenSet;
import com.isystk.sample.web.common.util.CookieUtil;
import com.isystk.sample.web.common.util.ValidateUtil;
import com.isystk.sample.web.userpc.constants.Url;
import com.isystk.sample.web.userpc.s2.form.entry.EntryRemindForm;
import com.isystk.sample.web.userpc.s2.logic.EntryRemindLogic;

import net.sf.json.JSONObject;

/**
 * パスワードリマインダ機能のアクション
 *
 * @author iseyoshitaka
 */
public class RemindAction {

	@Resource
	@ActionForm
	public EntryRemindForm entryRemindForm;

	@Resource
	protected EntryRemindLogic entryRemindLogic;

	/**
	 * 初期表示
	 */
	@SSL
	@TokenSet
	@Execute(validator = false)
	public String index() {

		// セッションからアクションフォームを削除する
		RequestUtil.getRequest().getSession()
				.removeAttribute(S2ActionMappingUtil.getActionMapping().getActionFormComponentDef().getComponentName());

		// メールアドレス入力画面を表示
		return showIndex();
	}

	/**
	 * 確認画面へ
	 */
	@SSL
	@Execute(validate = "validateConfirm", input = "showIndex", reset = "resetConfirm")
	public String confirm() {

		// メールアドレスのチェック
		this.checkMailAddress();

		// メールアドレス確認画面を表示
		return showConfirm();
	}

	/**
	 * 戻って修正する
	 *
	 * @return
	 */
	@SSL
	@Execute(validator = false)
	public String returnInput() {

		// メールアドレス入力画面を表示
		return showIndex();
	}

	/**
	 * 送信する
	 */
	@SSL
	@TokenCheck
	@Execute(validate = "validateConfirm", input = "showIndex")
	public String sendmail() {

		// メールアドレスのチェック
		this.checkMailAddress();

		// ワンタイムキーの発行、F03メール送信処理
		entryRemindLogic.mail(entryRemindForm.mailAddress);

		// セッションからアクションフォームを削除する
		RequestUtil.getRequest().getSession()
				.removeAttribute(S2ActionMappingUtil.getActionMapping().getActionFormComponentDef().getComponentName());

		// メール送信完了画面を表示
		return showSendMail();
	}

	/**
	 * メールに記載されているURLから遷移
	 */
	@SSL
	@Execute(validator = false, urlPattern = "{onetimeKey}")
	public String config() {

		// ワンタイムキーのチェック
		this.checkOnetimeKey();

		// ログインしている場合は、ログオフする
		HttpSession session = RequestUtil.getRequest().getSession(false);
		if (session != null) {
			session.invalidate();
		}

		String cookieRingKey = CookieUtil.getValue(com.isystk.sample.common.constants.Constants.Ring.COOKIE_PC);
		String decodeCookieRingkey = AgentCipher.decodeByDefaultKey(cookieRingKey);
		RequestUtil.getRequest().getSession().getServletContext().removeAttribute(decodeCookieRingkey);

		return Url.ringuserpcLogout.getAbsolute() + "?returnUrl=" + Url.userpcEntyRemind.getAbsolute()
				+ "showConfig/?onetimeKey=" + entryRemindForm.onetimeKey + "&redirect=true";

	}

	/**
	 * 上記の内容で登録する
	 */
	@SSL
	@TokenCheck
	@Execute(validate = "validateComplete", input = "showConfig", reset = "resetComplete")
	public String complete() {

		// ワンタイムキーのチェック
		this.checkOnetimeKey();

		try {
			// 入力されたパスワードで会員情報を更新する
			entryRemindLogic.updateTUser(entryRemindForm.onetimeKey, entryRemindForm.password);

		} catch (SOptimisticLockException e) {
			// 楽観的ロックにより他のユーザーの更新を検出した場合の例外
			throw new ActionMessagesException(AppMessageNames.ERRORS_OPTIMISTICLOCK.key);
		}

		// セッションからアクションフォームを削除する
		RequestUtil.getRequest().getSession()
				.removeAttribute(S2ActionMappingUtil.getActionMapping().getActionFormComponentDef().getComponentName());

		// パスワード設定完了画面を表示
		return showComplete();
	}

	/**
	 * メールアドレスに関する論理チェックを行う
	 */
	private void checkMailAddress() {

		if (!entryRemindLogic.checkExistMail(entryRemindForm.mailAddress)) {
			// 入力されたメールアドレスが、有効な会員情報の中に存在しない場合はエラー
			throw new ActionMessagesException(AppMessageNames.ERRORS_NOTREGISTMAILADDRESS.key);
		}
	}

	/**
	 * ワンタイムキーに関する論理チェックを行う
	 */
	private void checkOnetimeKey() {

		// ワンタイムキーがnullかどうか
		if (StringUtils.isNullOrEmpty(entryRemindForm.onetimeKey)) {
			throw new NotFoundException(AppMessageNames.ERRORS_INVALID.key, "ワンタイムキー");
		}

		// ワンタイムキーがDBに存在するかどうか
		if (!entryRemindLogic.checkExistOnetimeKey(entryRemindForm.onetimeKey)) {
			throw new NotFoundException(AppMessageNames.ERRORS_INVALID.key, "ワンタイムキー");
		}
	}

	/**
	 * メールアドレス入力画面を表示する
	 */
	@SSL
	@NoAllowDirectAccessCheck
	@Execute(validator = false)
	public String showIndex() {
		return "remind_index.jsp";
	}

	/**
	 * メールアドレス確認画面を表示する
	 */
	@SSL
	@NoAllowDirectAccessCheck
	@Execute(validator = false)
	public String showConfirm() {
		return "remind_confirm.jsp";
	}

	/**
	 * パスワード再設定URL記載メール送信完了画面を表示する
	 */
	@SSL
	@NoAllowDirectAccessCheck
	@Execute(validator = false)
	public String showSendMail() {
		return "remind_sendmail.jsp";
	}

	/**
	 * 新パスワード設定画面を表示する
	 */
	@SSL
	@TokenSet
	@Execute(validator = false)
	public String showConfig() {
		return "remind_config.jsp";
	}

	/**
	 * 新パスワード設定完了画面を表示する
	 */
	@SSL
	@NoAllowDirectAccessCheck
	@Execute(validator = false)
	public String showComplete() {
		return "remind_complete.jsp";
	}

	/**
	 * テキストボックスの入力値をリアルタイムにチェックする(Ajaxトリガーで実行)
	 *
	 * @return null
	 */
	@SSL
	@Execute(validator = false)
	public String callRealtimeCheck() {

		// JSONオブジェクトを生成する
		JSONObject json = new JSONObject();

		// validateチェック
		ActionMessages messages = new ActionMessages();

		// --> メールアドレス
		if ("mailAddress".equals(entryRemindForm.propertyName)) {
			EntryRemindForm.checkMailAddress(messages, entryRemindForm.propertyValue);
		}
		// --> パスワード
		if ("password".equals(entryRemindForm.propertyName)) {
			EntryRemindForm.checkPassword(messages, entryRemindForm.propertyValue);
		}
		// --> パスワード(確認用)
		if ("passwordConfirm".equals(entryRemindForm.propertyName)) {
			EntryRemindForm.checkPasswordConfirm(messages, entryRemindForm.propertyValue);
		}

		// ハッシュコード値よりメッセージ内容を取得
		List<String> messageList = ValidateUtil.getMessageStrings(messages);

		json.element("message", messageList);
		json.element("result", (0 != messageList.size()));

		ResponseUtil.write(json.toString(), Constants.MIME_TYPE_JSON);
		return null;

	}

}
