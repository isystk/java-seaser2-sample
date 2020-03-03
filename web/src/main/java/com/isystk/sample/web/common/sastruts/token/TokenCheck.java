package com.isystk.sample.web.common.sastruts.token;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.isystk.sample.common.config.AppMessageNames;

/**
 * トークンチェックを行わせます。
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TokenCheck {

	/** 失敗したときのメッセージ */
	AppMessageNames message() default AppMessageNames.ERRORS_TOKENINVALIDRUNTIMEEXCEPTION;
}
