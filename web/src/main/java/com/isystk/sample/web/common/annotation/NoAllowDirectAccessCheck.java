/**
 * Copyright(c) isystk.com</br>
 */
package com.isystk.sample.web.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Executeアノテーションを持つメソッドで、直接アクセスを許さないためのアノテーション。（主にshowXXX()メソッドに対して付与する）
 * 
 * @author iseyoshitaka
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface NoAllowDirectAccessCheck {
}
