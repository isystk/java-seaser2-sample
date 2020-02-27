package com.isystk.sample.web.common.taglib;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.struts.taglib.S2Functions;

import com.isystk.sample.common.constants.ImageType;
import com.isystk.sample.common.util.NumberUtil;
import com.isystk.sample.common.util.StringUtils;
import com.isystk.sample.web.common.util.CmnFunctions;

/**
 * 画像のカスタムタグを生成
 *
 * @author iseyoshitaka
 *
 */
public class ImageThumbTag extends SimpleTagSupport {

	private String imageId;
	private String styleId;
	private String styleClass;
	private String alt;
	private String lazyLoading;
	private ImageType imageType;
	private String playTime;
	private String gaaction;
	private String galabel;
	private ImageType loadingImageType;

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public void setLazyLoading(String lazyLoading) {
		this.lazyLoading = lazyLoading;
	}

	public void setImageType(ImageType imageType) {
		this.imageType = imageType;
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}

	public void setGaaction(String gaaction) {
		this.gaaction = gaaction;
	}

	public void setGalabel(String galabel) {
		this.galabel = galabel;
	}

	public void setLoadingImageType(ImageType loadingImageType) {
		this.loadingImageType = loadingImageType;
	}

	public void doTag() throws JspException, IOException {
		super.doTag();

		String width = (imageType.getWidth() == 0) ? "" : "width='" + String.valueOf(imageType.getWidth()) + "' ";
		String id = (styleId == null) ? "" : "id='" + styleId + "' ";
		String class_str = (styleClass == null) ? "" : styleClass;
		String alt_str = (alt == null) ? "" : "alt='" + S2Functions.h(alt) + "' ";
		String playTime_str = (playTime == null) ? "" : "data-playtime='" + S2Functions.h(playTime) + "' ";
		String gaaction_str = (gaaction == null) ? "" : "data-gaaction='" + S2Functions.h(gaaction) + "' ";
		String galabel_str = (galabel == null) ? "" : "data-galabel='" + S2Functions.h(galabel) + "' ";
		boolean isMovie = (0 <= class_str.indexOf("movie"));

		JspWriter out = this.getJspContext().getOut();

		// Noイメージ画像
		if (StringUtils.isBlankOrSpace(imageId)) {
			if (isMovie) {
				String src = CmnFunctions.getNoMovieUrl(ImageType.SD.getSuffix());
				class_str = class_str.replaceAll("movie", "");
				out.print("<img src='" + src + "'" + id + "class='" + class_str + "'" + alt_str + width + "/>");
			} else {
				String src = getNoimageSrc(ImageType.SD);
				out.print("<img src='" + src + "'" + id + "class='" + class_str + "'" + alt_str + width + "/>");
			}
			return;
		}

		String src = "";
		String osrc_str = "";
		String imageUrl = CmnFunctions.getImageUrl(String.valueOf(imageId), imageType.getSuffix());
		// 動画サムネイルは遅延ローダー対象外にしました。
		// ジュエリー画像読み込み処理追加
		if (lazyLoading == "true" && !isMovie) {
			if (loadingImageType != null) {
				// refs#4054 画像表示速度改善
				src = getLoadingimageSrc(loadingImageType);
			} else {
				src = getLoadingimageSrc(imageType);
			}
			osrc_str = "osrc='" + imageUrl + "'";
			class_str = class_str + " js-lazyLoading";
		} else if (NumberUtil.toInteger(imageId) != null && NumberUtil.toInteger(imageId).intValue() != 0) {
			src = imageUrl;
		}

		// クライアントサイトとプレビュー画面は画像キャッシュさせない
		S2Container container = SingletonS2ContainerFactory.getContainer();
		Map requestScope = (Map) container.getComponent("requestScope");
		String url = (String) requestScope.get("javax.servlet.forward.request_uri");
		if (url.indexOf("/client") != -1 || url.indexOf("/preview") != -1) {
			src += "?" + new Date().getTime();
		}

		out.print("<img src='" + src + "' " + osrc_str + id + "class='" + class_str + "' " + alt_str + width
				+ playTime_str + gaaction_str + galabel_str + "/>");

	}

	/**
	 * NoImageのsrcを取得する
	 *
	 * @param imageType
	 * @return NoImageのsrc
	 */
	public static String getNoimageSrc(ImageType imageType) {
		return CmnFunctions.getNoImageUrl(imageType.getSuffix());
	}

	/**
	 * LoadingImageのsrcを取得する
	 *
	 * @param imageType
	 * @return LoadingImageのsrc
	 */
	public static String getLoadingimageSrc(ImageType imageType) {
		return CmnFunctions.getNowloadingImageUrl(imageType.getSuffix());
	}

	/**
	 * ジュエリーのLoadingImageのsrcを取得する
	 *
	 * @param imageType
	 * @return LoadingImageのsrc
	 */
	public static String getLoadingimageSrcRing(ImageType imageType) {
		return CmnFunctions.getNowloadingImageUrlRing(imageType.getSuffix());
	}

}