package com.isystk.sample.common.util;

import org.seasar.framework.util.StringUtil;

import com.isystk.sample.common.config.AppConfigNames;
import com.isystk.sample.common.config.Config;
import com.isystk.sample.common.image.ImageDiv;
import com.isystk.sample.common.image.ImageManager;

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
		String domain = Config.getString(AppConfigNames.USERPC_DOMAIN);
		return protcol + domain + "/img/img_noimage" + suffix + ".jpg";
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
		String domain = Config.getString(AppConfigNames.USERPC_DOMAIN);
		return protcol + domain + "/img/img_nowloading" + suffix + ".jpg";
	}

	/**
	 *
	 * @return JSON形式の祝日一覧
	 */
	public static String getHolidayJsonStr() {
		return HolidayUtil.getHolidayJsonStr();
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
}
