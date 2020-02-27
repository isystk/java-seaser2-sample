/**
 * S2MaiInterceptor.java
 * 2010/06/16 iseyoshitaka
 * Copyright (c) 2010 TEAM LAB Inc. All rights reserved.
 */
package com.isystk.sample.common.s2.mai.interceptors;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Locale;

import javax.mail.internet.InternetAddress;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.env.Env;
import org.seasar.framework.exception.ResourceNotFoundRuntimeException;
import org.seasar.framework.util.MethodUtil;
import org.seasar.mai.mail.SendMail;
import org.seasar.mai.mail.Transport;
import org.seasar.mai.meta.MaiMetaData;
import org.seasar.mai.meta.MaiMetaDataFactory;
import org.seasar.mai.property.PropertyWriterForBean;
import org.seasar.mai.template.ContextHelper;
import org.seasar.mai.template.TemplateProcessor;
import org.seasar.mai.util.MailTextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isystk.sample.common.config.AppConfigNames;
import com.isystk.sample.common.config.Config;
import com.isystk.sample.common.exception.EmailException;
import com.isystk.sample.common.s2.mai.annotation.HtmlMail;
import com.ozacc.mail.Mail;

/**
 * <pre>
 * {@link org.seasar.mai.interceptors.S2MaiInterceptor#invoke(MethodInvocation)} メソッドを拡張
 *   ※ 元ソースで private メソッドで定義されているため、クラスごと上書きしています。
 *
 * {@link HtmlMail} アノテーションがあったら、テキスト＋HTMLメールとして送信します。
 * </pre>
 *
 * @author iseyoshitaka
 *
 */
public class S2MaiInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 5053273826772833194L;

	private static final Logger logger = LoggerFactory.getLogger(S2MaiInterceptor.class);

	private static final String isystkMail = "@isystk.com";

	private MaiMetaDataFactory maiMetaDataFactory;

	private SendMail sendMail;

	private PropertyWriterForBean propertyWriter;

	private ContextHelper contextHelper;

	private TemplateProcessor templateProcessor;

	private Transport transport;

	/** システム環境 */
	private String ENV = Config.getString(AppConfigNames.ENV);

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		if (!MethodUtil.isAbstract(method)) {
			return invocation.proceed();
		}
		if (isGetSendMail(method)) {
			return sendMail.clone();
		}
		final Locale current = Locale.getDefault();
		try {
			Locale.setDefault(Locale.ROOT);
			sendMail(invocation);
		} finally {
			Locale.setDefault(current);
		}

		return null;
	}

	private void sendMail(MethodInvocation invocation) throws UnsupportedEncodingException {
		Method method = invocation.getMethod();
		init();
		MaiMetaData metaData = maiMetaDataFactory.getMaiMetaData(getTargetClass(invocation));
		Object bean = getBean(invocation);
		Object context = contextHelper.createContext(bean);

		Mail mail = null;
		if (!method.isAnnotationPresent(HtmlMail.class)) {
			mail = createMail(method, context, metaData);
		} else {
			mail = createHtmlMail(method, context, metaData);
		}

		propertyWriter.setMailProperty(mail, bean);

		// エラーメール送付先が設定されているか
		if (mail.getReturnPath() == null) {
			String maiClass = method.getDeclaringClass().getCanonicalName();
			// batchはmailProperties.diconのreturnPathを参照する
			if (maiClass.indexOf("batch") == -1) {
				throw new EmailException("ReturnPathは必ず指定する必要があります。");
			}
		}

		// 送信するメールアドレスをログに出力する。
		printMailAdress(mail);

		// 本番機以外は@isystk.comではない宛先に送信しない
		if (!Env.getValue().equals(Env.PRODUCT)) {
			boolean isValid = isValidMailAddress(mail);
			if (!isValid) {
				logger.warn("本番機以外では@isystk.com以外を宛先に指定してはいけません。");
				return;
			}

			if (ENV.equalsIgnoreCase("ut")) {
				mail.setSubject("【local】" + mail.getSubject());
			} else if (ENV.equalsIgnoreCase("st")) {
				mail.setSubject("【STG1】" + mail.getSubject());
			}
		}
		SendMail mailSender = (SendMail) sendMail.clone();
		propertyWriter.setServerProperty(mailSender, bean);

		// isystkからのメールには差出人名を設定
		if ("no-reply@isystk.com".equals(mail.getFrom().getAddress())) {
			mail.setFrom("no-reply@isystk.com", "運営事務局【送信専用※返信できません】");
		}

		send(mail, mailSender);
	}

	private boolean isGetSendMail(Method method) {
		return "getSendMail".equals(method.getName()) && SendMail.class.equals(method.getReturnType());
	}

	private Object getBean(MethodInvocation invocation) {
		Object[] arguments = invocation.getArguments();
		if (arguments == null || arguments.length == 0) {
			return null;
		}
		return arguments[0];
	}

	private void send(Mail mail, SendMail sendMail) {
		transport.send(mail, sendMail);
	}

	/**
	 * <pre>
	 * ContentType:text/plain で、プレーンテキスト形式のメールを送信
	 *
	 *   プレーンテキストは [インターフェース]_[メソッド].(vm|ftl) ファイルをテンプレートにする
	 * </pre>
	 *
	 * @param method
	 *            実行メソッド
	 * @param context
	 *            データオブジェクト
	 * @param metaData
	 *            mai情報
	 * @return メールオブジェクト
	 */
	private Mail createMail(Method method, Object context, MaiMetaData metaData) {
		Mail mail = metaData.getMail(method);
		String path = metaData.getTemplatePath(method);
		String text = templateProcessor.processResource(path, context);
		String subject = MailTextUtil.getSubject(text);
		text = MailTextUtil.getText(text);
		if (subject != null) {
			mail.setSubject(subject);
		}
		mail.setText(text);
		return mail;

	}

	/**
	 * <pre>
	 * ContentType:multipart/alternative で、プレーンテキストとHTML形式のメールを送信
	 *
	 *   プレーンテキストは [インターフェース]_[メソッド].(vm|ftl) ファイルをテンプレートにする
	 *   HTMLは [インターフェース]_[メソッド]_html.(vm|ftl) ファイルをテンプレートにする
	 *   ※ プレーンテキスト用テンプレートがない場合は、HTML形式テキストのみ ContentType:text/html で送信されます
	 *   ※ プレーンテキスト用／HTML用テンプレートにSubject定義がある場合、HTMLテンプレートの内容が優先されます
	 * </pre>
	 *
	 * @param method
	 *            実行メソッド
	 * @param context
	 *            データオブジェクト
	 * @param metaData
	 *            mai情報
	 * @return メールオブジェクト
	 */
	private Mail createHtmlMail(Method method, Object context, MaiMetaData metaData) {
		Mail mail = metaData.getMail(method);

		// テキストメール用のテキスト設定
		try {
			mail = createMail(method, context, metaData);
		} catch (ResourceNotFoundRuntimeException e) {
			// なければ無視する
			logger.info(e.getMessage(), e);
		}

		// HTMLメール用のテキスト設定
		// subjectはhtmlテンプレートが優先される
		String htmlPath = metaData.getTemplatePath(method).replaceAll("\\.ftl$", "_html.ftl");
		String html = templateProcessor.processResource(htmlPath, context);
		String subject = MailTextUtil.getSubject(html);
		html = MailTextUtil.getText(html);
		if (subject != null) {
			mail.setSubject(subject);
		}
		mail.setHtmlText(html);

		return mail;
	}

	private boolean isValidMailAddress(Mail mail) {
		for (InternetAddress to : mail.getTo()) {
			// stg環境では、"@isystk.com" 以外のドメインはメール送信対象外とする。
			if (Env.getValue().equalsIgnoreCase("st")) {
				if (stSendOkMailAddress(to.toString())) {
					return false;
				}
				// ローカルや開発環境では、"@isystk.com" 以外のドメインはメール送信対象外とする。
			} else {
				if (to.toString().indexOf(isystkMail) == -1) {
					return false;
				}
			}
		}

		for (InternetAddress cc : mail.getCc()) {
			// stg環境では、"@isystk.com" 以外のドメインはメール送信対象外とする。
			if (Env.getValue().equalsIgnoreCase("st")) {
				if (stSendOkMailAddress(cc.toString())) {
					return false;
				}
				// ローカルや開発環境では、"@isystk.com" 以外のドメインはメール送信対象外とする。
			} else {
				if (cc.toString().indexOf(isystkMail) == -1) {
					return false;
				}
			}
		}

		for (InternetAddress bcc : mail.getBcc()) {
			// stg環境では、"@isystk.com" 以外のドメインはメール送信対象外とする。
			if (Env.getValue().equalsIgnoreCase("st")) {
				if (stSendOkMailAddress(bcc.toString())) {
					return false;
				}
				// ローカルや開発環境では、"@isystk.com" 以外のドメインはメール送信対象外とする。
			} else {
				if (bcc.toString().indexOf(isystkMail) == -1) {
					return false;
				}
			}
		}
		return true;
	}

	private void printMailAdress(Mail mail) {
		for (InternetAddress to : mail.getTo()) {
			// 個人あてのメールアドレスは、情報漏えい防止の為ログ出力しない。
			if (stSendOkMailAddress(to.toString())) {
				continue;
			}
			logger.info("to: " + to.getAddress());
		}
		for (InternetAddress cc : mail.getCc()) {
			// 個人あてのメールアドレスは、情報漏えい防止の為ログ出力しない。
			if (stSendOkMailAddress(cc.toString())) {
				continue;
			}
			logger.info("cc: " + cc.getAddress());
		}
		for (InternetAddress bcc : mail.getBcc()) {
			// 個人あてのメールアドレスは、情報漏えい防止の為ログ出力しない。
			if (stSendOkMailAddress(bcc.toString())) {
				continue;
			}
			logger.info("bcc: " + bcc.getAddress());
		}
	}

	/**
	 * メールの送信許可を判別
	 *
	 * @param mailAddress
	 * @return
	 */
	private boolean stSendOkMailAddress(String mailAddress) {
		return mailAddress.indexOf(isystkMail) == -1;
	}

	private void init() {
		templateProcessor.init();
	}

	public void setMaiMetaDataFactory(MaiMetaDataFactory maiMetaDataFactory) {
		this.maiMetaDataFactory = maiMetaDataFactory;
	}

	public void setSendMail(SendMail sendMail) {
		this.sendMail = sendMail;
	}

	/**
	 * @param propertyWriter
	 *            The propertyWriter to set.
	 */
	public void setPropertyWriter(PropertyWriterForBean propertyWriter) {
		this.propertyWriter = propertyWriter;
	}

	public void setContextHelper(ContextHelper contextHelper) {
		this.contextHelper = contextHelper;
	}

	public void setTemplateProcessor(TemplateProcessor templateProcessor) {
		this.templateProcessor = templateProcessor;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}
}