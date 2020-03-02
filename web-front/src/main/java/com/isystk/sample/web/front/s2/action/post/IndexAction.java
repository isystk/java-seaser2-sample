package com.isystk.sample.web.front.s2.action.post;

import org.seasar.struts.annotation.Execute;

/**
 * 投稿詳細 アクション
 *
 * @author iseyoshitaka
 */
public class IndexAction {

	/**
	 * 初期表示
	 */
	@Execute(urlPattern = "{postId}", validator = false)
	public String index() {

		return showIndex();

	}

	/**
	 * 投稿詳細
	 */
	private String showIndex() {
		return "index.jsp";
	}

}