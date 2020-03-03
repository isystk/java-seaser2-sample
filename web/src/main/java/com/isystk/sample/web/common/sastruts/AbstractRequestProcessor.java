package com.isystk.sample.web.common.sastruts;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.net.util.SubnetUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.TokenProcessor;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.IllegalPropertyRuntimeException;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.env.Env;
import org.seasar.struts.action.S2RequestProcessor;
import org.seasar.struts.config.S2ActionMapping;
import org.seasar.struts.config.S2ExecuteConfig;
import org.seasar.struts.util.RequestUtil;
import org.seasar.struts.util.S2ExecuteConfigUtil;
import org.seasar.struts.util.URLEncoderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.isystk.sample.common.config.AppMessageNames;
import com.isystk.sample.common.config.Message;
import com.isystk.sample.common.constants.Constants;
import com.isystk.sample.common.exception.AuthorityException;
import com.isystk.sample.common.exception.NotFoundException;
import com.isystk.sample.common.exception.SystemException;
import com.isystk.sample.common.exception.TokenCheckException;
import com.isystk.sample.common.s2.dto.BaseUserDto;
import com.isystk.sample.common.util.NumberUtil;
import com.isystk.sample.common.util.StringUtils;
import com.isystk.sample.common.util.UrlUtil;
import com.isystk.sample.web.common.util.CmnFunctions;
import com.isystk.sample.web.common.util.CookieUtil;
import com.isystk.sample.web.common.util.IPAddressUtil;
import com.isystk.sample.web.common.util.SSLRequestUtil;

/**
 * 共通リクエストプロセッサー 各種のActionのアノテーションに関する特別な動作を提供する。 （ログインチェック、権限チェック、SSLチェック）
 */
public abstract class AbstractRequestProcessor extends S2RequestProcessor {

	public static String VIEW_STATE_LIKEAS_SESSIONFORM_KEY = "_viewstate";
	public static String VIEW_STATE_IKEAS_ADDITIONAL_URL_PARAMETER_KEY = "_viewstate_aup";

	private Logger logger = LoggerFactory.getLogger(AbstractRequestProcessor.class);

	private static ThreadLocal<Boolean> inForward = new ThreadLocal<Boolean>();

	/**
	 * ログイン時に作成されるUserDtoのコンポーネント名
	 *
	 * @return UserDtoのコンポーネント名
	 */
	protected String getUserDtoComponentName() {
		return BaseUserDto.COMPONENT_NAME;
	}

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			super.process(request, response);
		} catch (Throwable t) {
			// RequestProcessorでのエラー発生時にエラーメールが送信されるようにする
			logger.error(t.getMessage(), t);
		}
	}

	/**
	 * アクセスしたURL情報をログにはく
	 */
	@Override
	public boolean processPreprocess(HttpServletRequest request, HttpServletResponse response) {
		logger.debug(UrlUtil.getUrl());
		logger.debug("Protocol scheme : '" + request.getScheme() + "', IsSecure : '" + request.isSecure()
				+ "', X_VIASSL : '" + request.getHeader("X_VIASSL") + "'");

		return super.processPreprocess(request, response);
	}

	// 権限のチェックを実施する。ホットデプロイに対応するために、リフレクションを用いて冗長に記述されていることに注意すること。
	@Override
	protected boolean processRoles(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping)
			throws IOException, ServletException {

		S2ExecuteConfig executeConfig = S2ExecuteConfigUtil.getExecuteConfig();

		HashSet<Annotation> annotationSet = getAnnotationSet(executeConfig);

		for (Annotation annotation : annotationSet) {
			if (checkAuthority(request, response, mapping, annotation) == false) {
				return false; // 認証失敗時の処理はすでに行われているのでfalseを返して通常処理を停止する。
			}
		}

		processLoginSuccess();

		return super.processRoles(request, response, mapping);
	}

	/**
	 * アノテーションのセット。クラスのアノテーションとメソッドのアノテーションを保持する。
	 * また、クラスのアノテーションはメソッドのアノテーションで上書きされて取得される。
	 *
	 * @param executeConfig
	 * @return アノテーションのセット
	 */
	public HashSet<Annotation> getAnnotationSet(S2ExecuteConfig executeConfig) {
		HashSet<Annotation> annotationSet = new HashSet<Annotation>();
		Annotation[] classAnnotationList = executeConfig.getMethod().getDeclaringClass().getAnnotations();
		annotationSet.addAll(Arrays.asList(classAnnotationList));
		Annotation[] methodAnnotationList = executeConfig.getMethod().getAnnotations();
		annotationSet.addAll(Arrays.asList(methodAnnotationList));

		return annotationSet;
	}

	/**
	 * アノテーション情報を元に、各種のチェック（ログインチェック、権限チェック、SSLチェック）を実施する。
	 */
	public boolean checkAuthority(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping,
			Annotation annotation) throws IOException, ServletException {
		String annotationName = annotation.annotationType().getName();
		if (annotationName.equals("com.isystk.sample.web.common.annotation.LoginCheck")) {
			return checkLogin(request, response, mapping);
		} else if (annotationName.equals("com.isystk.sample.web.common.annotation.TradeNameSelectionCheck")) {
			return checkTradeNameSelection(request, response, mapping);
		} else if (annotationName.equals("com.isystk.sample.web.common.annotation.SSL")) {
			return checkSsl(request, response, mapping);
		} else if (annotationName.equals("com.isystk.sample.web.common.sastruts.token.TokenCheck")) {
			return checkToken(request, response, mapping);
		} else if (annotationName.equals("com.isystk.sample.web.common.annotation.NoAllowDirectAccessCheck")) {
			return CheckNoAllowDirectAccess(request, response, mapping);
		}

		return true;
	}

	/**
	 * SSLリクエストのセキュアクッキーをチェックする
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	public boolean checkSslRequest(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping)
			throws IOException, ServletException {
		if (isHttpsRequest(request)) {
			Boolean isForward = inForward.get();
			if (isForward == null) {
				isForward = Boolean.FALSE;
			}

			// 内部フォワードによる呼び出しの場合にはチェックをしない。
			if (Boolean.FALSE.equals(isForward)) {
				HttpSession session = request.getSession(false);

				String sslrequest = null;
				if (session != null) {
					sslrequest = (String) session.getAttribute(SSLRequestUtil.SSL_REQUEST);
				}

				if (sslrequest != null) {
					if (!SSLRequestUtil.isSecureRequest(request)) {
						logger.info("不正なアクセスのためログオフする");
						// 不正なアクセスを検知したらセッションを無効にする
						session.invalidate();

						redirectToLogin(request, response, mapping, "/");
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * SSLで接続しているかどうかをチェックし、していなければ例外を出力する
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	public boolean checkSsl(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping)
			throws IOException, ServletException {
		if (isHttpsRequest(request) == false) {
			if (Env.getValue().equals(Env.PRODUCT) || Env.getValue().equalsIgnoreCase("st")) {
				ActionForward forward = ((S2ActionMapping) mapping).createForward(UrlUtil.getUrl(true), true);
				processForwardConfig(request, response, forward);
				return false;
			} else {
				Exception exception = new AuthorityException(AppMessageNames.ERRORS_INVALIDNOSSLACCESS.key);
				ActionForward forward = processException(request, response, exception,
						processActionForm(request, response, mapping), mapping);
				processForwardConfig(request, response, forward);
				return false;
			}
		}

		return true;
	}

	/**
	 * HTTPSリクエストかどうか
	 *
	 * @param request
	 * @return
	 */
	public static boolean isHttpsRequest(HttpServletRequest request) {
		return // request.isSecure() ||
		request.getScheme().toLowerCase().contains("https") || "on".equalsIgnoreCase(request.getHeader("X_VIASSL"));
	}

	/**
	 * トークンがセットされているかどうかをチェックする
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	public boolean checkToken(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping)
			throws IOException, ServletException {
		// トークンをチェックする。ただしこのタイミングではリセットはしない。リセットを行うのは、TokenInterceptorの中で正常終了した場合のみ。
		if (!TokenProcessor.getInstance().isTokenValid(request, false)) {
			Exception exception = new TokenCheckException(AppMessageNames.ERRORS_TOKENINVALIDRUNTIMEEXCEPTION.key);
			ActionForward forward = processException(request, response, exception,
					processActionForm(request, response, mapping), mapping);
			processForwardConfig(request, response, forward);
			return false;
		}

		return true;
	}

	/**
	 * ダイレクトアクセスされていないか（内部フォワードは許す）どうかをチェックする
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	public boolean CheckNoAllowDirectAccess(HttpServletRequest request, HttpServletResponse response,
			ActionMapping mapping) throws IOException, ServletException {
		Boolean isForward = inForward.get();
		if (isForward == null) {
			isForward = Boolean.FALSE;
		}

		if (Boolean.FALSE.equals(isForward)) {
			Exception exception = new NotFoundException(
					Message.getString(AppMessageNames.ERRORS_NOALLOWDIRECTACCESSEXCEPTION));
			ActionForward forward = processException(request, response, exception,
					processActionForm(request, response, mapping), mapping);
			processForwardConfig(request, response, forward);
			return false;
		}

		return true;
	}

	/**
	 * SSLで接続していないかチェックし、していればHTTPへリダイレクトする(ただし本番環境以外はエラーとする)
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	public boolean checkNonSsl(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping)
			throws IOException, ServletException {
		if (isHttpsRequest(request)) {
			if (Env.getValue().equals(Env.PRODUCT) || Env.getValue().equalsIgnoreCase("st")) {
				// ↓もしマイナビIP・ラボIPであればtrueを返す(SSL化までの1時的な対応 refs#16997)
				SubnetUtils mynaviSubnet = new SubnetUtils("210.190.113.128/25");
				SubnetUtils labSubnet = new SubnetUtils("203.179.90.192/28");
				String ip = IPAddressUtil.getRealIP(request);
				if (StringUtils.isIpv4Address(ip)
						&& (mynaviSubnet.getInfo().isInRange(ip) || labSubnet.getInfo().isInRange(ip))) {
					return true;
				}
				// ↑refs#16997
				ActionForward forward = ((S2ActionMapping) mapping).createForward(UrlUtil.getUrl(false), true);
				processForwardConfig(request, response, forward);
				return false;
			} else {
				Exception exception = new AuthorityException(AppMessageNames.ERRORS_INVALIDSSLACCESS.key);
				ActionForward forward = processException(request, response, exception,
						processActionForm(request, response, mapping), mapping);
				processForwardConfig(request, response, forward);
				return false;
			}
		}

		return true;
	}

	/**
	 * ログインしているかどうかをチェックし、ログインしていなければログイン画面へリダイレクトする
	 */
	public boolean checkLogin(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping)
			throws IOException, ServletException {
		// ログインしていない場合には、login画面へリダイレクトする。
		boolean logined = CmnFunctions.isLogined();
		if (logined == false) {
			// 失敗したリクエストと先のURLは保持しておく。
			String backUrl = UrlUtil.getUrl();

			redirectToLogin(request, response, mapping, backUrl);
		}

		return logined;
	}

	/**
	 * 屋号ログインしているかどうかをチェックし、していなければログイン画面へリダイレクトする
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	private boolean checkTradeNameSelection(HttpServletRequest request, HttpServletResponse response,
			ActionMapping mapping) throws IOException, ServletException {
		// ログインしているかどうか。
		boolean selected = checkLogin(request, response, mapping);
		if (!selected) {
			return false;
		}
		try {
			Object obj = SingletonS2Container.getComponent(BaseUserDto.COMPONENT_NAME);

			BeanDesc beanDesc = BeanDescFactory.getBeanDesc(obj.getClass());
			selected = beanDesc.getFieldValue("tradeNameId", obj) != null;
		} catch (ComponentNotFoundRuntimeException e) {
			// ログインしていない場合
			selected = false;
		} catch (Exception e) {
			selected = false;
		}

		if (selected == false) {
			// 失敗したリクエストと先のURLは保持しておく。
			String backUrl = UrlUtil.getUrl();

			redirectToLogin(request, response, mapping, backUrl);
		}

		return selected;
	}

	/** ログイン画面へリダイレクト */
	private void redirectToLogin(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping,
			String backUrl) throws IOException, ServletException {
		ActionForward forward = ((S2ActionMapping) mapping).createForward(
				getLoginUrl() + (backUrl == null ? "" : "?backUrl=" + URLEncoderUtil.encode(backUrl)), true);

		// デバッグ機能として、自動ログインを使用する場合には自動でログインするURLへリダイレクトする。
		if (BaseUserDto.USE_AUTO_LOGIN_FOR_DEBUG) {
			boolean isPost = request.getMethod().equals("POST");

			if (backUrl == null || isPost) {
				backUrl = BaseUserDto.AUTO_LOGIN_LOGIN_PATH;
			}

			forward = ((S2ActionMapping) mapping).createForward(
					"/login/login" + "?backUrl=" + URLEncoderUtil.encode(backUrl) + "&loginUserName="
							+ BaseUserDto.AUTO_LOGIN_USER_NAME + "&loginUserPassword="
							+ BaseUserDto.AUTO_LOGIN_USER_PASSWORD + "&tradeNameId="
							+ BaseUserDto.AUTO_LOGIN_TRADE_NAME_ID + "&weddingId=" + BaseUserDto.AUTO_LOGIN_WEDDING_ID,
					true);
		}

		processForwardConfig(request, response, forward);
	}

	@Override
	protected ActionForward processActionPerform(HttpServletRequest request, HttpServletResponse response,
			Action action, ActionForm form, ActionMapping mapping) throws IOException, ServletException {

		ActionForward result = super.processActionPerform(request, response, action, form, mapping);

		// SSLによるリクエストで、セキュアクッキーが発行されていない場合には、発行を行う。
		if (isHttpsRequest(request)) {
			Boolean isForward = inForward.get();
			if (isForward == null) {
				isForward = Boolean.FALSE;
			}

			// 内部フォワードによる呼び出しの場合には対象外
			if (Boolean.FALSE.equals(isForward)) {
				// セキュアクッキーが発行されていない場合
				if (SSLRequestUtil.isSecureRequest(request) == false) {
					// セキュアクッキーの発行
					SSLRequestUtil.publishSecureCookie(request);
				}
			}
		}

		return result;
	}

	/**
	 * ログイン成功したことを示すクッキーが一時的に発行されるが、
	 * このクッキーが付いたリクエストが届いた場合には、リクエストのAttributeにフラグを立ててクッキーの削除を行う。
	 * このリクエストのAttributeにフラグがたっている場合には、ログイン成功後の初めてのリクエストであることが判定できる
	 */
	protected void processLoginSuccess() {
		String value = CookieUtil.getValue(Constants.LOGIN_SUCCESS_COOKIE);
		if (Boolean.TRUE.toString().equalsIgnoreCase(value)) {
			RequestUtil.getRequest().setAttribute(Constants.LOGIN_SUCCESS_REQUEST, true);
			CookieUtil.setCookie(Constants.LOGIN_SUCCESS_COOKIE, "void", "/", 0, null);
		}
	}

	/**
	 * ログインのURL
	 *
	 * @return ログインのURL
	 */
	public abstract String getLoginUrl();

	@Override
	protected void doForward(String uri, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Boolean isForward = inForward.get();
		try {

			if (isForward == null || isForward == false) {
				inForward.set(true);
			}

			// IEのキャッシュ問題解決のためETagを設定する
			// response.setHeader("ETag", "\"" +
			// String.valueOf(DateUtils.getNow().getTime()) + "\"");
			// response.setHeader("Cache-Control", "max-age=0");

			try {
				super.doForward(uri, request, response);
			} catch (Exception exception) {
				handleForwardException(request, response, exception);
			}

		} finally {
			inForward.set(isForward);
		}
	}

	/**
	 * ViewState(LikeAsSessionForm)のために、オーバーライド
	 */
	protected void processPopulate(HttpServletRequest request, HttpServletResponse response, ActionForm form,
			ActionMapping mapping) throws ServletException {
		if (form != null) {
			// ViewStateがあれば適用を追加する
			Object viewStateValueObj = request.getParameterValues(VIEW_STATE_LIKEAS_SESSIONFORM_KEY);
			if (viewStateValueObj != null) {
				S2ActionMapping actionMapping = (S2ActionMapping) mapping;

				String viewStateValue = ((String[]) viewStateValueObj)[0];
				Map<String, String[]> viewStateMap = splitQuery(viewStateValue);
				for (Map.Entry<String, String[]> viewStateEntry : viewStateMap.entrySet()) {
					if (request.getParameterValues(viewStateEntry.getKey()) == null) { // リクエストパラメータによる上書きがなければViewStateの値を適用する。
						try {
							setProperty(actionMapping.getActionForm(), viewStateEntry.getKey(),
									viewStateEntry.getValue());
						} catch (Throwable t) {
							throw new IllegalPropertyRuntimeException(
									actionMapping.getActionFormBeanDesc().getBeanClass(), viewStateEntry.getKey(), t);
						}
					}
				}
			}
		}

		super.processPopulate(request, response, form, mapping);
	}

	/**
	 * ViewState(LikeAsAdditionalUrlParameter)のために、オーバーライド
	 */
	protected Map<String, Object> getAllParameters(HttpServletRequest request,
			MultipartRequestHandler multipartHandler) {
		Map<String, Object> result = super.getAllParameters(request, multipartHandler);

		// ViewStateがあれば適用を追加する
		Object viewStateValueObj = result.get(VIEW_STATE_IKEAS_ADDITIONAL_URL_PARAMETER_KEY);
		if (viewStateValueObj != null) {
			String viewStateValue = ((String[]) viewStateValueObj)[0];
			Map<String, String[]> viewStateMap = splitQuery(viewStateValue);
			for (Map.Entry<String, String[]> viewStateEntry : viewStateMap.entrySet()) {
				if (result.get(viewStateEntry.getKey()) == null) { // リクエストによる上書きがなければViewStateの値を適用する。
					result.put(viewStateEntry.getKey(), viewStateEntry.getValue());
				}
			}
		}

		return result;
	}

	public static Map<String, String[]> splitQuery(String query) {
		try {
			Map<String, String[]> result = new LinkedHashMap<String, String[]>();
			if (StringUtils.isBlankOrSpace(query)) {
				return result;
			}
			String[] pairs = query.split("&");
			for (String pair : pairs) {
				int idx = pair.indexOf("=");
				String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
				String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");

				String[] currentValueArray = result.get(key);
				if (currentValueArray == null) {
					result.put(key, new String[] { value });
				} else {
					String[] newValueArray = new String[currentValueArray.length + 1];
					System.arraycopy(currentValueArray, 0, newValueArray, 0, currentValueArray.length);
					newValueArray[currentValueArray.length] = value;

					result.put(key, newValueArray);
				}
			}
			return result;
		} catch (Exception e) {
			throw new SystemException("ViewStateの不正な書き換えを検出しました", e);
		}
	}

	private void handleForwardException(HttpServletRequest request, HttpServletResponse response, Exception exception)
			throws ServletException, IOException {
		if (isInNandleForwardException(exception)) { // エラーページの表示中にエラが発生した場合に、再帰的にエラーページを表示するような問題がないようにする。
			if (exception instanceof RuntimeException) {
				throw (RuntimeException) exception;
			} else {
				throw new RuntimeException(exception);
			}
		}

		ExceptionConfig config = moduleConfig.findExceptionConfig(Exception.class.getName());

		ExceptionHandler handler = null;
		try {
			handler = (ExceptionHandler) RequestUtils.applicationInstance(config.getHandler());
		} catch (Exception e) {
			// void
		}

		// エラー画面へ遷移
		ActionForward forword = (handler.execute(exception, config, null, null, request, response));
		super.processForwardConfig(request, response, forword);
	}

	private boolean isInNandleForwardException(Exception exception) {
		return Iterables.any(Lists.newArrayList(exception.getStackTrace()), new Predicate<StackTraceElement>() {
			@Override
			public boolean apply(StackTraceElement input) {
				return AbstractRequestProcessor.class.getName().equals(input.getClassName())
						&& "handleForwardException".equals(input.getMethodName());
			}
		});
	}

	/**
	 * プロパティの値を設定します。
	 *
	 * @param bean
	 *            JavaBeans
	 * @param name
	 *            パラメータ名
	 * @param value
	 *            パラメータの値
	 * @throws ServletException
	 *             何か例外が発生した場合。
	 */
	@Override
	protected void setProperty(Object bean, String name, Object value) {

		// solr がUTF8Readerでドキュメントを読み込む際に受け付けない文字があるためその文字が来た場合には省く
		if (value != null) {
			if (value instanceof String[]) {
				String[] array = (String[]) value;
				String[] newArray = new String[array.length];
				for (int i = 0; i < array.length; i++) {
					newArray[i] = checkUTF8(array[i]);
				}
				value = (Object) newArray;
			}
		}

		// パラメータ名が不正な場合はプロパティに値を設定しない
		String regex = "(\\[([^\\[\\]]*)\\]|\\(([^\\(\\)]*)\\))";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(name);
		while (matcher.find()) {
			String s1 = matcher.group(2); // [] にヒットする場合の中身
			if (s1 != null && NumberUtil.toInteger(s1) == null) {
				return;
			}
			// ※ こっちははじいたらまずかったのでコメントアウト
			// String s2 = matcher.group(3); // () にヒットする場合の中身
			// if (s2 != null && NumberUtil.toInteger(s2) == null) {
			// return;
			// }
		}

		super.setProperty(bean, name, value);
	}

	/**
	 * solr がUTF8Readerでドキュメントを読み込む際に受け付けない文字があるためその文字が来た場合には省く
	 *
	 * @param value
	 *            文字列
	 * @return 省いた文字列
	 */
	protected String checkUTF8(String value) {
		if (value == null)
			return null;

		char[] array = value.toCharArray();
		char[] newArray = new char[array.length];
		for (int i = 0; i < array.length; i++) {
			int c = (int) array[i];
			if (c >= 0xFFFE) {
				newArray[i] = ' ';
			} else {
				newArray[i] = (char) c;
			}
		}
		return new String(newArray);
	}

}
