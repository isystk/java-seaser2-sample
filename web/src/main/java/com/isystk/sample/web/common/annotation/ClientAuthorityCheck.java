/**
 * Copyright(c) team-lab</br>
 */
package com.isystk.sample.web.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.isystk.sample.common.constants.authority.ClientAuthority;

/**
 * 権限設定をするためのアノテーション
 * 
 * @author nkawamata
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface ClientAuthorityCheck {

    ClientAuthority[] value() default {};

}
