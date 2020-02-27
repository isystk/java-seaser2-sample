package com.isystk.sample.web.common.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;

import javax.naming.ServiceUnavailableException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;
import org.seasar.framework.util.StringUtil;
import org.seasar.struts.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isystk.sample.common.config.AppMessageNames;
import com.isystk.sample.common.config.Message;
import com.isystk.sample.common.exception.StatusException;
import com.isystk.sample.common.util.StringUtils;
import com.isystk.sample.web.common.util.CmnFunctions;

/**
 * 例外発生時のハンドラ.
 *
 * @author iseyoshitaka
 */
public class OriginalExceptionHandler extends ExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(OriginalExceptionHandler.class);

	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(Exception ex, ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance,
			HttpServletRequest request, HttpServletResponse response) throws ServletException {

		logger.info("OriginalExceptionHandler#execute");

		if (response.isCommitted()) { // 既にレスポンスが出力され、コミットされている場合（ファイル出力やAjaxなどの出力の最中にエラーが発生した場合）
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		} else {
			try {
				response.reset();
			} catch (Throwable t) {
				// void
			}
			// ローカルと社内開発環境のみデバッグ情報を出力
			debug(ex, request);
		}

		if (ex instanceof StatusException) {
			response.setStatus(((StatusException) ex).getStatusCode());
		} else {
			if (ex instanceof ServiceUnavailableException) {
				response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			if (StringUtils.isNullOrEmpty(ex.getMessage())) {
				logger.error("内部エラーが発生しました", ex);
			} else {
				logger.error(ex.getMessage(), ex);
			}
		}
		if (isAjax(request)) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ResponseUtil.write("");
			return null;
		}

		return super.execute(ex, ae, mapping, formInstance, request, response);
	}

	/**
	 * @param request
	 *            request
	 * @return true or false
	 */
	private boolean isAjax(HttpServletRequest request) {
		return StringUtil.equals("XMLHttpRequest", request.getHeader("X-Requested-With"));
	}

	/**
	 * @param Exception
	 *            ex
	 * @param request
	 *            request
	 */
	private void debug(Exception ex, HttpServletRequest request) {
		if (!CmnFunctions.isEnvProduct()) {
			request.setAttribute("ERROR.MESSAGE", ex.getMessage());
		}

		// 本番環境ではAuthorityExceptionの場合404用のメッセージを表示
		if (CmnFunctions.isEnvProduct()) {
			if ("AuthorityException".equals(ex.getClass().getSimpleName())) {
				request.setAttribute("ERROR.MESSAGE",
						MessageFormat.format(Message.getString(AppMessageNames.ERRORS_NOTEXIST), "画面"));
			}
		}

		if (CmnFunctions.isEnvUt()) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			try {
				if (sw != null) {
					sw.flush();
					sw.close();
				}
				if (pw != null) {
					pw.flush();
					pw.close();
				}
			} catch (IOException e) {
				logger.warn("デバッグ情報の出力ができませんでした");
			}
			request.setAttribute("ERROR.TRACE", sw.toString());
			request.setAttribute("ERROR.REFERER", request.getHeader("Referer"));
			request.setAttribute("ERROR.COOKIE", request.getHeader("Cookie"));
		}
	}
}
