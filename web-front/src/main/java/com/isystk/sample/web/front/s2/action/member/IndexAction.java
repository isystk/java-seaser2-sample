package com.isystk.sample.web.front.s2.action.member;

import org.seasar.struts.annotation.Execute;

import com.isystk.sample.web.common.annotation.LoginCheck;
import com.isystk.sample.web.common.annotation.NoAllowDirectAccessCheck;
import com.isystk.sample.web.common.annotation.SSL;
import com.isystk.sample.web.common.sastruts.token.TokenSet;

/**
 * マイページトップアクション
 *
 * @author iseyoshitaka
 */
@SSL
@LoginCheck
public class IndexAction {

	/**
	 * 初期表示時
	 */
	@TokenSet
	@Execute(validator = false)
	public String index() {

		// 論理チェック
		chkLogical();

		return showIndex();
	}

	/**
	 * 論理チェック
	 */
	private void chkLogical() {
	}

	/**
	 * 基本情報入力①画面を表示
	 */
	@NoAllowDirectAccessCheck
	@Execute(validator = false)
	public String showIndex() {

		return "index.jsp";
	}

}
