/**
 * rights reserved.
 */
package com.isystk.sample.web.common.constants;

import org.seasar.framework.env.Env;

/**
 * @author iseyoshitaka
 *
 */
public final class WebConstants {

	public static final class SelectOption {
		/** プルダウンで空の場合に表示する値 */
		public static final String VOID_VALUE = "----";

	}

	/**
	 * 環境
	 */
	public static final class ENV {

		public static final boolean PRODUCT = Env.PRODUCT.equals(Env.getValue());

	}

	/**
	 * クッキー
	 */
	public static final class Cookie {

	}

}
