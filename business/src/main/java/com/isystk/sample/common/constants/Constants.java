package com.isystk.sample.common.constants;

/**
 * 各プロジェクト共通で使用される定数
 *
 * @author iseyoshitaka
 *
 */
public class Constants {

	/** JSONのMIMEタイプ */
	public static final String MIME_TYPE_JSON = "application/json";

	/** ログイン成功時に一時的に付与されるクッキー */
	public static final String LOGIN_SUCCESS_COOKIE = "_LSC";

	/** ログイン成功時に一時的に付与されるクッキー（ログイン成功後の初めてのリクエストかどうか判定することができる） */
	public static final String LOGIN_SUCCESS_REQUEST = "_LSR";

	/**
	 * トラッキングIDにまつわるパラメータ群.<br>
	 *
	 */
	public interface Tracking {
		/** クッキーパス */
		String COOKIE_PATH = "/";
		/** クッキー名 */
		String COOKIE_NAME = "TRACKING_ID";
		/** 生存時間(2年) */
		Integer COOKIE_AGE = 31536000 * 2; // 2年
	}

}
