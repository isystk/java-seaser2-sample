/*
 * BooleanUtil.java
 * 2011/04/26 mmatsumoto
 */
package com.isystk.sample.web.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.struts.action.ActionFormWrapper;
import org.seasar.struts.action.BeanWrapper;
import org.seasar.struts.util.RequestUtil;
import org.seasar.struts.util.URLEncoderUtil;

import com.isystk.sample.web.common.sastruts.URLParam;

/**
 * @author iseyoshitaka
 * 
 */
public class UrlUtil {

	/**
	 * 現在表示中のURLを返す(フルパス)
	 */
	public static String getUrl() {
		HttpServletRequest request = RequestUtil.getRequest();
		return getUrl(request.isSecure());
	}

	/**
	 * 現在表示中のURLを返し、Httpsかどうかを切り替えることができる(フルパス)
	 * 
	 * @param isSecure
	 *            httpsかどうか
	 * @return フルパス
	 */
	public static String getUrl(boolean isSecure) {
		HttpServletRequest request = RequestUtil.getRequest();
		String protcol = isSecure ? "https://" : "http://";
		String host = request.getServerName();
		String port = (request.getLocalPort() == 8080) ? ":8080" : "";
		String context = request.getContextPath();

		return protcol + host + port + context + currentRequestUrl();
	}

	/**
	 * 現在のリクエストのURLを作成する
	 */
	public static String currentRequestUrl() {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		@SuppressWarnings("rawtypes")
		Map requestScope = (Map) container.getComponent("requestScope");
		String servletPath = (String) requestScope.get("javax.servlet.forward.servlet_path");
		String queryString = (String) requestScope.get("javax.servlet.forward.query_string");

		String backUrl = servletPath + (queryString == null ? "" : "?" + queryString);
		return backUrl;
	}

	/**
	 * データオブジェクトからクエリ文字列を生成
	 * 
	 * @param path
	 *            パス
	 * @param obj
	 *            パラメータオブジェクト
	 * @return URL文字列
	 */
	public static String getURLFromObject(String path, Object obj) {
		if (obj == null)
			return path;

		StringBuilder sb = new StringBuilder(path);
		sb.append('?');

		BeanDesc beanDesc = BeanDescFactory.getBeanDesc(obj.getClass());
		if (obj instanceof BeanWrapper) {
			beanDesc.getField("bean").setAccessible(true);
			try {
				obj = beanDesc.getField("bean").get(obj);
			} catch (Exception e) {
				// あり得ない
				throw new IllegalStateException(e.getMessage(), e);
			}
		}
		if (obj instanceof ActionFormWrapper) {
			beanDesc.getField("actionForm").setAccessible(true);
			try {
				obj = beanDesc.getField("actionForm").get(obj);
			} catch (Exception e) {
				// あり得ない
				throw new IllegalStateException(e.getMessage(), e);
			}
		}

		beanDesc = BeanDescFactory.getBeanDesc(obj.getClass());
		int size = beanDesc.getFieldSize();
		for (int i = 0; i < size; i++) {
			Field field = beanDesc.getField(i);

			// Paramアノテーションがない場合
			if (!field.isAnnotationPresent(URLParam.class))
				continue;
			// publicフィールドでない場合
			if (!Modifier.isPublic(field.getModifiers()))
				continue;

			URLParam paramAnnotation = field.getAnnotation(URLParam.class);
			String key = paramAnnotation.name();
			if (StringUtils.isEmpty(key))
				key = field.getName();

			try {
				Object value = field.get(obj);
				if (value instanceof Object[]) {
					for (Object o : ((Object[]) value)) {
						String valString = String.valueOf(o);
						if (StringUtils.isNotEmpty(valString) && !"null".equals(valString)) {
							sb.append(key).append('=').append(URLEncoderUtil.encode(valString)).append('&');
						}
					}
				} else if (value instanceof Map<?, ?>) {
					for (Entry<?, ?> o : ((Map<?, ?>) value).entrySet()) {
						String valString = String.valueOf(o.getValue());
						if (StringUtils.isNotEmpty(valString) && !"null".equals(valString)) {
							sb.append(key + "(" + URLEncoderUtil.encode(o.getKey().toString()) + ")").append('=')
									.append(URLEncoderUtil.encode(valString)).append('&');
						}
					}
				} else {
					String valString = String.valueOf(value);
					if (StringUtils.isNotEmpty(valString) && !"null".equals(valString)) {
						sb.append(key).append('=').append(URLEncoderUtil.encode(valString)).append('&');
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);// LOGGER.warn(field.getName() + "の取得に失敗しました.", e);
			}
		}

		sb.setLength(sb.length() - 1);
		return sb.toString();
	}
}
