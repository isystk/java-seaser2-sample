package com.isystk.sample.web.common.sastruts.token;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.struts.util.TokenProcessor;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.struts.util.RequestUtil;

import com.isystk.sample.common.config.AppMessageNames;
import com.isystk.sample.common.exception.TokenCheckException;

/**
 * アノテーションを見てトークンチェックしたりをセットしたりするインターセプタ
 * 
 * @author iseyoshitaka
 * 
 */
public class TokenInterceptor extends AbstractInterceptor {

    /**
     * シリアル
     */
    private static final long serialVersionUID = -3983224453746953437L;

    ThreadLocal<Boolean> duplicateThreadLocal = new ThreadLocal<Boolean>(); // すでにトークンインターセプタを実行中である場合には、重複して実行しないようにするためのフラグ。基本的にトークンチェックのためのものでトークンセットでは重複実行を許すようにしている。

    /**
     * トークンをチェックしてセットする.
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {
	boolean duplicate = Boolean.TRUE.equals(duplicateThreadLocal.get());

	HttpServletRequest request = RequestUtil.getRequest();

	// トークンをチェックしつつ、トークンを開放する
	// なお、トークンチェック自体は、リクエストプロセッサでも実施することで、２重送信時に入力チェックよりも優先して別途実施している。
	TokenCheck tokenCheck = invocation.getMethod().getAnnotation(TokenCheck.class);
	if (duplicate == false && tokenCheck != null) {
	    if (!TokenProcessor.getInstance().isTokenValid(request, true)) {
		throw new TokenCheckException(AppMessageNames.ERRORS_TOKENINVALIDRUNTIMEEXCEPTION.key);
	    }
	}

	try {
	    
	    Object ret = null;
	    try {
		duplicateThreadLocal.set(true);
		ret = invocation.proceed();
	    } finally {
		duplicateThreadLocal.set(false);
	    }

	    //	// トークンチェックのメソッドが正常終了したら、トークンをリセットする
	    //	if (invocation.getMethod().isAnnotationPresent(TokenCheck.class)) {
	    //	    TokenProcessor.getInstance().resetToken(request);
	    //	}

	    // トークンセットの場合には、正常終了時にトークンをセットする
	    if (invocation.getMethod().isAnnotationPresent(TokenSet.class)) {
		TokenProcessor.getInstance().saveToken(request);
	    }

	    return ret;
	} catch (Exception e) {
	    // トークンチェックのメソッド中に、処理中に例外が発生した場合はロールバックしているはずなので、無効にしていたトークン改めて処理を再発行して実行できるようにする。
	    if (duplicate == false && tokenCheck != null) {
		TokenProcessor.getInstance().saveToken(request);
	    }

	    throw e;
	}
    }
}
