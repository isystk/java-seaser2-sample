package com.isystk.sample.web.common.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.seasar.struts.taglib.S2Functions;

import com.isystk.sample.common.constants.ImageType;
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
	private ImageType imageType;

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

	public void setImageType(ImageType imageType) {
		this.imageType = imageType;
	}

	public void doTag() throws JspException, IOException {
		super.doTag();

		String width = (imageType.getWidth() == 0) ? "" : "width='" + String.valueOf(imageType.getWidth()) + "' ";
		String id = (styleId == null) ? "" : "id='" + styleId + "' ";
		String class_str = (styleClass == null) ? "" : styleClass;
		String alt_str = (alt == null) ? "" : "alt='" + S2Functions.h(alt) + "' ";

		JspWriter out = this.getJspContext().getOut();

		// Noイメージ画像
		if (StringUtils.isBlankOrSpace(imageId)) {
			String src = getNoimageSrc(ImageType.SD);
			out.print("<img src='" + src + "'" + id + "class='" + class_str + "'" + alt_str + width + "/>");
			return;
		}

		String osrc_str = "";
		String src = CmnFunctions.getImageUrl(String.valueOf(imageId), imageType.getSuffix());

		out.print("<img src='" + src + "' " + osrc_str + id + "class='" + class_str + "' " + alt_str + width + "/>");

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

}