/*
 * SSL.java
 * 2011/01/28 iseyoshitaka
 */
package com.isystk.sample.web.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * OEMサイトに開放しているExecuteメソッドに付けられるアノテーション
 * 
 * @author yanagimotokeita
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
public @interface OEM {

}
