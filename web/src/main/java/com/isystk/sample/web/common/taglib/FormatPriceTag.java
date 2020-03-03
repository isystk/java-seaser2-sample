package com.isystk.sample.web.common.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * 価格のフォーマットを行うタグ value属性に数値を入れると、3桁ごとにカンマ区切りで最後に'円'がついた形で表示される （例）「1,234,567円」
 * ただし、値が空の場合には空文字列で表示される。
 * 
 */
public class FormatPriceTag extends SimpleTagSupport {
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
			result = String.format("%1$,3d", Integer.parseInt(value.replace(",", "")));
		} catch (Exception e) {
			// void
		}

		if (StringUtils.isEmpty(result) == false) {
			out.print(result + "円");
		}

	}

}