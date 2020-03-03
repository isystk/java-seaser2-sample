package com.isystk.sample.common.anotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * キャッシュが必要なメソッドに付けられるアノテーション
 *
 * @author iseyoshitaka
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
public @interface Cache {
	/** キャッシュの保持期間（ミリ秒） */
	int time() default 600000;

	/** キャッシュの内容を自動更新するかどうかtrueの場合、自動的にキャッシュ内容が更新される */
	boolean autoReload() default false;

	/** キャッシュの内容を自動更新するけど、アクセスが全くない場合にキャッシュを無効にするまでの時間 デフォルト２時間 */
	int autoReloadInvalidTime() default 7200000;
}
