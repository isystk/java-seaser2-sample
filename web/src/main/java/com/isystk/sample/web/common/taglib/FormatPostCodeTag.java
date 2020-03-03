package com.isystk.sample.web.common.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.isystk.sample.common.util.StringUtils;

/**
 * 郵便番号のフォーマットを行うタグ value属性に7桁の数値（数値にはハイフンが含まれていてもよい）のフォーマットを行う （例）「〒xxx－xxxx」
 * 
 */
public class FormatPostCodeTag extends SimpleTagSupport {
	private String value;

	/**
	 * フォーマット対象の値を設定する
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * タグの出力処理を行う
	 */
	public void doTag() throws JspException, IOException {
		JspWriter out = this.getJspContext().getOut();

		String result = value;
		try {
			String replace = value.replace("-", "");
			if (replace.length() == 7) {
				result = replace.substring(0, 3) + "-" + replace.substring(3, 7);
			}
		} catch (Exception e) {
			// void
		}

		if (StringUtils.isNullOrEmpty(result) == false) {
			result = "〒" + result;
		}

		out.print(result);
	}
}