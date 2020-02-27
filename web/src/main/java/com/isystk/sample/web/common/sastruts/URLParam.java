/*
 * Param.java
 * 2011/01/31 iseyoshitaka
 */
package com.isystk.sample.web.common.sastruts;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DTOやFormクラスで、GETパラメータとして使用されるフィールドに使用されるアノテーション。<br>
 * 各プロジェクトのURLクラスで、このアノテーションを付けたクラスを引数に渡すと、 各フィールドをGETパラメータとしてURL文字列にセットします。
 * 
 * 
 * @author iseyoshitaka
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface URLParam {

    /** パラメタ名 */
    String name() default "";
}
