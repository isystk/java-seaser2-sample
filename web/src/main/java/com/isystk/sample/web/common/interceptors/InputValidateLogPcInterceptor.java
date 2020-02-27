package com.isystk.sample.web.common.interceptors;

import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isystk.sample.web.common.util.IPAddressUtil;
import com.isystk.sample.web.common.util.ValidateUtil;

/**
 * お問い合わせ系画面のエラー内容をログ出力するためのインターセプター（PC用）
 * 
 * @author iseyoshitaka
 * 
 */
public class InputValidateLogPcInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 971866752833326829L;
    private static final Logger logger = LoggerFactory.getLogger(InputValidateLogPcInterceptor.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
	String targetClassName = getTargetClass(invocation).getName();

	FormDiv formDiv = FormDiv.get(targetClassName);
	if (null == formDiv) {
	    // ありえない
	    return invocation.proceed();
	}

	String ipAddress = "," + IPAddressUtil.getRealIP();
	String title = formDiv.getTitle();

	Execute anno = invocation.getMethod().getAnnotation(Execute.class);
	if (null == anno) {
	    logger.debug(ipAddress + title + "対象外のメソッドが呼ばれました");
	    return invocation.proceed();
	}

	ActionMessages messages = (ActionMessages) RequestUtil.getRequest().getAttribute(Globals.ERROR_KEY);
	if (null == messages) {
	    logger.debug(ipAddress + title + "エラーメッセージが存在しませんでした");
	    return invocation.proceed();
	}

	List<String> messageList = ValidateUtil.getMessageStrings(messages);
	for (String str : messageList) {
	    logger.info(ipAddress + title + "," + str);
	}

	return invocation.proceed();
    }

    private enum FormDiv {

	/** PC-資料請求 */
	PC_DOCUMENT("DocumentAction", ".userpc", "資料請求", "PC"),
	/** PC-見学予約 */
	PC_VISIT("VisitAction", ".userpc", "見学予約", "PC"),
	/** PC-お問合せ */
	PC_COMMON("CommonAction", ".userpc", "お問合せ", "PC"),
	/** PC-フェア予約 */
	PC_INQUIRY("FairAction", ".userpc", "フェア予約", "PC");

	private final String clazzName;
	private final String pkgPrefix;
	private final String displayName;
	private final String pcspKbnName;

	FormDiv(String clazzName, String pkgPrefix, String displayName, String pcspKbnName) {
	    this.clazzName = clazzName;
	    this.pkgPrefix = pkgPrefix;
	    this.displayName = displayName;
	    this.pcspKbnName = pcspKbnName;
	}

	public String getTitle() {
	    return "," + this.pcspKbnName + "," + "WEDDING" + "," + this.displayName;
	}

	public static FormDiv get(String targetClassName) {
	    if (null == targetClassName) {
		return null;
	    }
	    for (FormDiv value : values()) {
		if (targetClassName.contains(value.clazzName) && targetClassName.contains(value.pkgPrefix)) {
		    return value;
		}
	    }
	    return null;
	}
    }
}
