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
import com.isystk.sample.common.constants.authority.BaseAuthority;
import com.isystk.sample.common.image.ImageDiv;
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
     * 現在のユーザが、指定の権限を持っているかどうか
     *
     * @param authority 指定の権限
     * @return 指定の権限を持っているかどうか
     */
    public static boolean hasAuthority(BaseAuthority authority) {
	return hasAuthority(getCurrentUserAuthorityList(), authority);
    }

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
     * 現在のユーザが保持している、権限のリストを取得します。 ホットデプロイに対応するために、リフレクションを用いている
     *
     * @return　現在のユーザが保持している権限のリスト
     */
    public static BaseAuthority[] getCurrentUserAuthorityList() {
	Object obj = SingletonS2Container.getComponent(BaseUserDto.COMPONENT_NAME);

	BeanDesc beanDesc = BeanDescFactory.getBeanDesc(obj.getClass());
	return (BaseAuthority[]) beanDesc.invoke(obj, "getAuthorityList", null);
    }

    /**
     * 権限を持っているかどうかを判定します
     *
     * @param authes 権限の集合
     * @param auth 権限
     * @return 持っているときtrue
     */
    public static boolean hasAuthority(BaseAuthority[] authes, BaseAuthority auth) {

	if (auth == null) {
	    throw new IllegalArgumentException();
	}

	if (authes == null || authes.length <= 0) {
	    return false;
	}

	for (BaseAuthority a : authes) {
	    if (a == null) {
		continue;
	    }
	    if (a.equals(auth)) {
		return true;
	    }
	}

	return false;
    }

    /**
     * リサイズ画像URLの取得 (URLにプロコトル付きで返却する)<br />
     * ※SSL化対応済みなので、なるべくgetImageUrlWithSslProtocolを利用してください
     *
     * @param imageId
     * @param suffix
     * @return 画像のURL
     */
    @Deprecated
    public static String getImageUrlWithProtocol(String imageId, String suffix) {
	HttpServletRequest request = RequestUtil.getRequest();
	String protcol = (request.isSecure()) ? Config.getString(AppConfigNames.HTTPS_PROTCOL) : Config.getString(AppConfigNames.HTTP_PROTCOL);
	return protcol + getImageUrl(imageId, suffix);
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
     * リサイズ画像URLの取得(ジュエリー用)
     *
     * @param imageId
     * @param suffix
     * @return 画像のURL
     */
    public static String getImageUrlRing(String imageId, String suffix) {
	if (StringUtil.isNotEmpty(imageId)) {
	    return ImageManager.getImageUrl(Integer.valueOf(imageId), suffix, ImageDiv.RING);
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
	return protcol + domain + "/img/img_noimage" + suffix + ".jpg";
    }

    /**
     * NOムービー画像URLの取得
     *
     * @param imageId
     * @param suffix
     * @return 画像のURL
     */
    public static String getNoMovieUrl(String suffix) {
	String protcol = "//";
	String domain = Config.getString("");
	return protcol + domain + "/img/img_nomovie" + suffix + ".jpg";
    }

    /**
     * Loading画像URLの取得
     *
     * @param imageId
     * @param suffix
     * @return 画像のURL
     */
    public static String getNowloadingImageUrl(String suffix) {
	String protcol = "//";
	String domain = Config.getString("");
	return protcol + domain + "/img/img_nowloading" + suffix + ".jpg";
    }

    /**
     * Loading画像URLの取得(ジュエリーのイメージ取得用)
     *
     * @param imageId
     * @param suffix
     * @return 画像のURL
     */
    public static String getNowloadingImageUrlRing(String suffix) {
	String protcol = "//";
	String domain = Config.getString("");
	return protcol + domain + "/ring/img/img_nowloading" + suffix + ".jpg";
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
     * 実行環境がステージング1環境かどうか
     *
     * @return ステージング1環境かどうか
     */
    public static boolean isEnvSt1() {
	return Config.getString(AppConfigNames.ENV).equalsIgnoreCase("st");
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
     * 実行環境が結合テスト環境かどうか
     *
     * @return 結合テスト環境かどうか
     */
    public static boolean isEnvCt() {
	return Env.getValue().equalsIgnoreCase(Env.CT);
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
