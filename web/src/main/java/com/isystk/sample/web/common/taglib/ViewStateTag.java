package com.isystk.sample.web.common.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.taglib.html.HiddenTag;
import org.seasar.struts.util.RequestUtil;

import com.isystk.sample.web.common.sastruts.AbstractRequestProcessor;
import com.isystk.sample.web.common.util.UrlUtil;

/**
 * ViewStateの機能を有効にする。formの中に配置され、hiddenタグを生成しアクションフォームの内容を埋め込む。
 * アクションフォーム内容を疑似的に維持することが可能となるため、セッションを使わないようにすることができる。
 * 
 * １．cmn:viewStateタグをフォーム内に配置することで、 ２．アクションフォームの内容がフォーム内にシリアライズされ、
 * ３．フォームの送信時には、ViewStateでの内容が自動的にアクションフォームに展開されて、
 * ４．その後その他のURLパラメータがアクションフォームに展開されることになる。
 * 
 * LikeAsSessionFormとLikeAsAdditionalUrlParameterの２つのモードがあり、
 * LikeAsSessionFormモードではセッションフォームのような扱いであり、
 * LikeAsAdditionalUrlParameterモードでは、フォームの内容を追加のUrlパラメータとして付加するような扱いになる。
 * 
 * 具体的にはLikeAsSessionFormモードでは、アクションフォームのリセットメソッドの前にシリアライズされたフォームの内容が反映され、
 * すぐにリセットメソッドによりクリアされる可能性がある。 LikeAsAdditionalUrlParameterモードでは、
 * アクションフォームのリセットメソッドの後にシリアライズされたフォームの内容が反映される。
 * 
 * 
 * @author iseyoshitaka
 * 
 */
public class ViewStateTag extends SimpleTagSupport {

	private boolean likeAsAdditionalUrlParameter = false;

	public void setLikeAsAdditionalUrlParameter(boolean likeAsAdditionalUrlParameter) {
		this.likeAsAdditionalUrlParameter = likeAsAdditionalUrlParameter;
	}

	public void doTag() throws JspException, IOException {

		super.doTag();

		ActionMapping mapping = (ActionMapping) RequestUtil.getRequest().getAttribute(Globals.MAPPING_KEY);

		int scope = PageContext.SESSION_SCOPE;
		if ("request".equalsIgnoreCase(mapping.getScope())) {
			scope = PageContext.REQUEST_SCOPE;
		}

		Object bean = getJspContext().getAttribute(mapping.getName(), scope);
		String serializeParam = UrlUtil.getURLFromObject("", bean);
		if (serializeParam != null && serializeParam.length() > 0) { // ?が頭につくので除いておく
			serializeParam = serializeParam.substring(1);
		}

		HiddenTag hiddenTag = new HiddenTag();
		hiddenTag.setPageContext((PageContext) getJspContext());
		if (likeAsAdditionalUrlParameter) {
			hiddenTag.setProperty(AbstractRequestProcessor.VIEW_STATE_IKEAS_ADDITIONAL_URL_PARAMETER_KEY);
		} else {
			hiddenTag.setProperty(AbstractRequestProcessor.VIEW_STATE_LIKEAS_SESSIONFORM_KEY);
		}
		hiddenTag.setValue(serializeParam);
		hiddenTag.doStartTag(); // これで出力される

	}

}