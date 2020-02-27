package com.isystk.sample.web.userpc.s2.action;

import org.seasar.struts.annotation.Execute;

/**
 * サイトトップ アクション
 *
 * @author iseyoshitaka
 */
public class IndexAction {

	/**
	 * 初期表示
	 */
	@Execute(validator = false)
	public String index() {

		return showIndex();

	}

	/**
	 * サイトトップ
	 */
	private String showIndex() {
		return "index.jsp";
	}

}