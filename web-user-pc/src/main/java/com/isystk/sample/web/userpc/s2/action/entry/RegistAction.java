package com.isystk.sample.web.userpc.s2.action.entry;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.extension.jdbc.exception.SOptimisticLockException;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.util.StringUtil;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.exception.ActionMessagesException;
import org.seasar.struts.util.RequestUtil;
import org.seasar.struts.util.ResponseUtil;
import org.seasar.struts.util.S2ActionMappingUtil;

import com.isystk.sample.common.config.AppMessageNames;
import com.isystk.sample.common.constants.Constants;
import com.isystk.sample.common.constants.column.Sex;
import com.isystk.sample.common.exception.IllegalMailAddressException;
import com.isystk.sample.common.exception.NotFoundException;
import com.isystk.sample.common.s2.dto.SelectBoxDto;
import com.isystk.sample.common.s2.entity.TUserOnetimeValid;
import com.isystk.sample.common.util.BeanCopyUtil;
import com.isystk.sample.common.util.CipherUtils;
import com.isystk.sample.web.common.annotation.NoAllowDirectAccessCheck;
import com.isystk.sample.web.common.annotation.SSL;
import com.isystk.sample.web.common.sastruts.token.TokenCheck;
import com.isystk.sample.web.common.sastruts.token.TokenSet;
import com.isystk.sample.web.common.util.CookieUtil;
import com.isystk.sample.web.common.util.SSLRequestUtil;
import com.isystk.sample.web.common.util.ValidateUtil;
import com.isystk.sample.web.userpc.s2.dto.UserDto;
import com.isystk.sample.web.userpc.s2.dto.entry.EntryRegistDetailDto;
import com.isystk.sample.web.userpc.s2.form.entry.EntryForm;
import com.isystk.sample.web.userpc.s2.form.entry.EntryRegistForm;
import com.isystk.sample.web.userpc.s2.logic.EntryRegistLogic;
import com.isystk.sample.web.userpc.s2.logic.WeddingCommonLogic;

import net.sf.json.JSONObject;

/**
 * 会員登録アクション
 *
 * @author iseyoshitaka
 */
public class RegistAction {

	/** 会員登録ActionForm */
	@Resource
	@ActionForm
	public EntryRegistForm entryRegistForm;

	/** 会員登録Logic */
	@Resource
	protected EntryRegistLogic entryRegistLogic;

	/** フロントPC共通Logic */
	@Resource
	protected WeddingCommonLogic weddingCommonLogic;

	/** 年Map */
	public Map<String, String> yearMap;

	/** 月Map */
	public Map<String, String> monthMap;

	/** 西暦リスト（プルダウン表示用） */
	public List<SelectBoxDto> listYyyy;

	/** 月リスト（プルダウン表示用） */
	public List<SelectBoxDto> listMm;

	/** 日リスト（プルダウン表示用） */
	public List<SelectBoxDto> listDd;

	public boolean isEmailFixed;

	/**
	 * 初期表示処理
	 *
	 * @return 会員登録入力 画面のPath
	 * @throws IOException
	 */
	@SSL
	@TokenSet
	@Execute(validator = false, reset = "resetIndex")
	public String index() throws IOException {

		// 論理チェックを行う
		chkLogical();

		// 初期設定を行う
		// 性別を設定する
		if (StringUtil.isEmpty(entryRegistForm.sex)) {
			entryRegistForm.sex = String.valueOf(Sex.FEMALE.getCode());
		}

		// 予約フォームから受け継いだ入力情報がセッション存在する場合は、フォームに詰め替える
		EntryForm entryForm = (EntryForm) RequestUtil.getRequest().getSession().getAttribute(EntryForm.class.getName());
		if (entryForm != null) {
			BeanCopyUtil.copy(entryForm, entryRegistForm).execute();
			RequestUtil.getRequest().getSession().removeAttribute(EntryForm.class.getName());
		}

		// 会員登録入力 画面を表示する
		return showIndex();

	}

	/**
	 * 確認画面表示処理
	 *
	 * @return 会員登録内容確認 画面のPath
	 * @throws IOException
	 */
	@SSL
	@Execute(validate = "validate", input = "showIndex", reset = "resetConfirm")
	public String confirm() throws IOException {

		// 論理チェックを行う
		chkLogical();

		// エラーメッセージを生成する
		// コンストラクタではErrorMessageを設定できない為、Dummy値でインスタンス作成後に一度クリアし、ErrorMessageを登録する
		ActionMessages messages = new ActionMessages();
		ActionMessagesException exception = new ActionMessagesException("dummy", "dummy");
		exception.getMessages().clear();

		// メールアドレス
		// 2重登録防止チェック
		if (entryRegistLogic.checkExistenceMail(entryRegistForm.mail)) {
			ValidateUtil.add("mail", messages, AppMessageNames.ERRORS_REGISTRATION.key, "メールアドレス");
			exception.getMessages().add(messages);
			throw exception;
		}

		// 都道府県
		// 存在チェック
		if (!weddingCommonLogic.checkExistencePrefecture(entryRegistForm.prefectureId)) {
			ValidateUtil.add("prefectureId", messages, AppMessageNames.ERRORS_INVALID.key, "都道府県");
			exception.getMessages().add(messages);
			throw exception;
		}

		// 会員登録内容確認 画面を表示する
		return showConfirm();

	}

	/**
	 * メール送信後画面表示処理
	 *
	 * @return 会員仮登録メール送信完了 画面のPath
	 * @throws IOException
	 */
	@SSL
	@TokenCheck
	@Execute(validate = "validate", input = "showIndex")
	public String sendmail() throws IOException {

		// 論理チェックを行う
		chkLogical();

		// 会員登録詳細Dtoを生成する
		EntryRegistDetailDto detailDto = BeanCopyUtil.createAndCopy(EntryRegistDetailDto.class, entryRegistForm)
				.dateConverter(BeanCopyUtil.MANUAL_INPUT_DATE_FORMAT.toPattern(), "birthday").excludes("password")
				.execute();

		// SHA1ハッシュパスワードを生成する
		detailDto.password = CipherUtils.sha1(entryRegistForm.password).getBytes();

		try {
			// 会員を登録し、メールを送信する
			entryRegistLogic.insertTUser(detailDto);
		} catch (IllegalMailAddressException e) {
			ActionMessagesException exception = new ActionMessagesException("dummy", "dummy");
			exception.getMessages().clear();
			exception.addMessage("registErrorMessage", new ActionMessage(AppMessageNames.ERRORS_SENDMAIL.key));
			throw exception;
		}

		// 会員仮登録メール送信完了
		return showSendMail();
	}

	protected String executeComplete(String onetimeKey) {
		// 会員登録後に自動でログインする
		HttpSession session = RequestUtil.getRequest().getSession(false);
		if (session != null) {
			session.invalidate();
		}

		UserDto userDto = SingletonS2Container.getComponent(UserDto.class);
		TUserOnetimeValid tUserOnetimeValid = entryRegistLogic.getTUserOnetimeValid(onetimeKey);
		userDto.userId = tUserOnetimeValid.userId;
		SSLRequestUtil.setSessionSecureCookie(RequestUtil.getRequest(), ResponseUtil.getResponse(), false);
		CookieUtil.setCookie(Constants.LOGIN_SUCCESS_COOKIE, Boolean.TRUE.toString(), "/", -1, null);

		try {
			// エンティティを更新する
			entryRegistLogic.updateTUser(tUserOnetimeValid);
		} catch (SOptimisticLockException e) {
			// 取得後すぐに削除するので楽観ロック(Version)は考慮しないが、デットロックが発生する可能性がある為、Try-Catchを念のため実装
			// ActionMessagesExceptionはExecuteアノテーションのinputで指定した画面に遷移するが、ほぼ起こらないのでこのままで
			throw new ActionMessagesException(AppMessageNames.ERRORS_OPTIMISTICLOCK.key);
		}

		// 会員登録完了
		return showComplete();
	}

	/**
	 * 会員登録完了処理
	 *
	 * @return 会員登録完了 画面のPath
	 */
	@SSL
	@Execute(validator = false, urlPattern = "{onetimeKey}")
	public String complete() {

		// 論理チェック(完了)を行う
		chkCompleteLogical();

		return this.executeComplete(entryRegistForm.onetimeKey);
	}

	/**
	 * FKT002 会員登録入力
	 */
	@SSL
	@NoAllowDirectAccessCheck
	@Execute(validator = false)
	public String showIndex() {

		// 生年月日の西暦を取得
		listYyyy = weddingCommonLogic.getBirthdayYyyyList(entryRegistForm.birthdayYyyy);

		// 生年月日の月を取得
		listMm = weddingCommonLogic.getBirthdayMmList(entryRegistForm.birthdayMm);

		// 生年月日の日を取得
		listDd = weddingCommonLogic.getBirthdayDdList(entryRegistForm.birthdayDd);

		setCommonProperty();
		return "regist_index.jsp";
	}

	/**
	 * FKT003 会員登録内容確認
	 */
	@SSL
	@Execute(validator = false)
	@NoAllowDirectAccessCheck
	public String showConfirm() {
		setCommonProperty();
		return "regist_confirm.jsp";
	}

	/**
	 * FKT004 会員仮登録メール送信完了
	 */
	@SSL
	@NoAllowDirectAccessCheck
	@Execute(validator = false)
	public String showSendMail() {

		// セッションからアクションフォームを削除する
		RequestUtil.getRequest().getSession()
				.removeAttribute(S2ActionMappingUtil.getActionMapping().getActionFormComponentDef().getComponentName());

		return "regist_sendmail.jsp";
	}

	/**
	 * FKT005 会員登録完了
	 */
	@SSL
	@NoAllowDirectAccessCheck
	@Execute(validator = false)
	public String showComplete() {

		// セッションからアクションフォームを削除する
		RequestUtil.getRequest().getSession()
				.removeAttribute(S2ActionMappingUtil.getActionMapping().getActionFormComponentDef().getComponentName());

		return "regist_complete.jsp";
	}

	/**
	 * 確認画面から登録画面へ戻る
	 */
	@SSL
	@Execute(validator = false)
	public String moveBackFromConfirmToIndex() {
		setCommonProperty();
		return showIndex();
	}

	/**
	 * 論理チェックメソッド
	 */
	private void chkLogical() {
	}

	/**
	 * 論理チェックメソッド(完了)
	 */
	private void chkCompleteLogical() {

		// ワンタイムキーがNULL場合
		if (StringUtil.isEmpty(entryRegistForm.onetimeKey)) {
			throw new NotFoundException(AppMessageNames.ERRORS_INVALID.key, "ワンタイムキー");
		}

		// OneTimeKeyが存在するか確認する
		if (!entryRegistLogic.checkExistenceOnetimeKey(entryRegistForm.onetimeKey)) {
			throw new NotFoundException(AppMessageNames.ERRORS_DELETED.key, "ワンタイムキー");
		}

	}

	/**
	 * 共通プロパティを設定する
	 */
	private void setCommonProperty() {

		// 年Mapを取得する
		yearMap = entryRegistLogic.getYearMap();

		// 月Mapを取得する
		monthMap = entryRegistLogic.getMonthMap();

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

		// --> お名前・姓
		if ("familyName".equals(entryRegistForm.propertyName)) {
			EntryRegistForm.checkFamilyName(messages, entryRegistForm.propertyValue);
		}
		// --> お名前・名
		if ("name".equals(entryRegistForm.propertyName)) {
			EntryRegistForm.checkName(messages, entryRegistForm.propertyValue);
		}
		// --> お名前・姓（フリガナ）
		if ("familyNameKana".equals(entryRegistForm.propertyName)) {
			EntryRegistForm.checkFamilyNameKana(messages, entryRegistForm.propertyValue);
		}
		// --> お名前・名（フリガナ）
		if ("nameKana".equals(entryRegistForm.propertyName)) {
			EntryRegistForm.checkNameKana(messages, entryRegistForm.propertyValue);
		}
		// --> 郵便番号
		if ("zip".equals(entryRegistForm.propertyName)) {
			EntryRegistForm.checkZip(messages, entryRegistForm.propertyValue);
		}
		// --> 市区町村
		if ("area".equals(entryRegistForm.propertyName)) {
			EntryRegistForm.checkArea(messages, entryRegistForm.propertyValue);
		}
		// --> 番地
		if ("address".equals(entryRegistForm.propertyName)) {
			EntryRegistForm.checkAddress(messages, entryRegistForm.propertyValue);
		}
		// --> 建物名
		if ("building".equals(entryRegistForm.propertyName)) {
			EntryRegistForm.checkBuilding(messages, entryRegistForm.propertyValue);
		}
		// --> メールアドレス
		if ("mail".equals(entryRegistForm.propertyName)) {
			EntryRegistForm.checkMail(messages, entryRegistForm.propertyValue);
		}
		// --> パスワード
		if ("password".equals(entryRegistForm.propertyName)) {
			EntryRegistForm.checkPassword(messages, entryRegistForm.propertyValue);
		}
		// --> パスワード(確認用)
		if ("passwordConf".equals(entryRegistForm.propertyName)) {
			EntryRegistForm.checkPasswordConf(messages, entryRegistForm.propertyValue);
		}
		// --> 電話番号
		if ("tel".equals(entryRegistForm.propertyName)) {
			EntryRegistForm.checkTel(messages, entryRegistForm.propertyValue);
		}
		// ハッシュコード値よりメッセージ内容を取得
		List<String> messageList = ValidateUtil.getMessageStrings(messages);

		json.element("message", messageList);
		json.element("result", (0 != messageList.size()));

		ResponseUtil.write(json.toString(), Constants.MIME_TYPE_JSON);
		return null;

	}

}
