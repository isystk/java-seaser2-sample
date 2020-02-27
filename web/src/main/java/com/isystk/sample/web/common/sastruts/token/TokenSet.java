package com.isystk.sample.web.common.sastruts.token;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.seasar.framework.aop.annotation.Interceptor;

/**
 * {@link TokenInterceptor}をSAStrutsのアクションメソッドに適用するアノテーションです。
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Interceptor("tokenSetInterceptor")
public @interface TokenSet {
}
