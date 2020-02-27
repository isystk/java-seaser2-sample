/*
 * HtmlMail.java
 * 2011/02/17 iseyoshitaka
 */
package com.isystk.sample.common.s2.mai.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * HTMLメールを送信する場合に、メールオブジェクト ({@link BaseMail}継承クラス) に設定します。
 * 
 * @author iseyoshitaka
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HtmlMail {

}
