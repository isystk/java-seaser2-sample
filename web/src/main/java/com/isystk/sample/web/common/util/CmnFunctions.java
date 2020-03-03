package com.isystk.sample.web.common.util;

import javax.servlet.http.HttpServletRequest;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.env.Env;
import org.seasar.framework.util.StringUtil;
import org.seasar.struts.util.RequestUtil;

import com.isystk.sample.common.config.AppConfigNames;
import com.isystk.sample.common.config.Config;
import com.isystk.sample.common.image.ImageManager;
import com.isystk.sample.common.s2.dto.BaseUserDto;
import com.isystk.sample.common.util.StringUtils;

/**
 * cmn.tldから呼び出されるファンクションメソッド群です。
 * <p>
 * ※ Javaソースでは基本呼び出されません。
 *
 * @author iseyoshitaka
 *
 */
public class CmnFunctions {

	/**
	 * ログインしているかどうか。ホットデプロイに対応するために、リフレクションを用いている
	 *
	 * @return ログインしているかどうか
	 */
	public static boolean isLogined() {
		try {
			Object obj = SingletonS2Container.getComponent(BaseUserDto.COMPONENT_NAME);

			BeanDesc beanDesc = BeanDescFactory.getBeanDesc(obj.getClass());
			return (Boolean) beanDesc.invoke(obj, "hasLogined", null);
		} catch (ComponentNotFoundRuntimeException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * リサイズ画像URLの取得 (URLにhttpsプロコトル付きで返却する)
	 *
	 * @param imageId
	 * @param suffix
	 * @return 画像のURL
	 */
	public static String getImageUrlWithSslProtocol(String imageId, String suffix) {
		return Config.getString(AppConfigNames.HTTPS_PROTCOL) + getImageUrl(imageId, suffix);
	}

	/**
	 * リサイズ画像URLの取得
	 *
	 * @param imageId
	 * @param suffix
	 * @return 画像のURL
	 */
	public static String getImageUrl(String imageId, String suffix) {
		if (StringUtil.isNotEmpty(imageId)) {
			return ImageManager.getImageUrl(Integer.valueOf(imageId), suffix);
		} else {
			return getNoImageUrl(suffix);
		}
	}

	/**
	 * NOイメージ画像URLの取得
	 *
	 * @param imageId
	 * @param suffix
	 * @return 画像のURL
	 */
	public static String getNoImageUrl(String suffix) {
		String protcol = "//";
		String domain = Config.getString("");
		return protcol + domain + "/images/noimage" + suffix + ".jpg";
	}

	/**
	 * 文字列を渡された文字数で切り取る
	 *
	 * @param value
	 * @param length
	 * @return 切り取った文字列
	 */
	public static String substring(String value, int length) {
		if (value == null || length >= value.length() || length == 0) {
			return value;
		} else {
			return value.substring(0, length) + "...";
		}
	}

	/**
	 * 文字列が指定文字数を超える場合は「...」を足した状態で指定文字数内になるように収める
	 *
	 * @param value
	 * @param length
	 * @return 文字列
	 */
	public static String substringInLg(String value, int length) {
		String end = "...";
		if (value == null || length >= value.length() || length == 0 || length <= end.length()) {
			return value;
		} else {
			return value.substring(0, length - end.length()) + end;
		}
	}

	/**
	 * 渡された文字数から改行コードを取り除く。
	 *
	 * @param value
	 * @return 改行コードを取り除いた文字列
	 */
	public static String removeLineFeed(String value) {
		if (value == null || value.length() == 0) {
			return value;
		} else {
			return StringUtils.breakToBlank(value);
		}
	}

	/**
	 * 実行環境がステージング環境かどうか
	 *
	 * @return ステージング環境かどうか
	 */
	public static boolean isEnvSt() {
		return Env.getValue().equalsIgnoreCase("st");
	}

	/**
	 * 実行環境が単体テスト環境かどうか
	 *
	 * @return 単体テスト環境かどうか
	 */
	public static boolean isEnvUt() {
		return Env.getValue().equalsIgnoreCase(Env.UT);
	}

	/**
	 * 実行環境が本番環境かどうか
	 *
	 * @return 本番環境かどうか
	 */
	public static boolean isEnvProduct() {
		return Env.getValue().equalsIgnoreCase(Env.PRODUCT);
	}

	/**
	 * HTTPSかどうか
	 *
	 * @return HTTPSかどうか
	 */
	public static boolean isSecure() {
		HttpServletRequest request = RequestUtil.getRequest();
		return request.isSecure();
	}

}
