/**
 * Copyright(c) isystk.com</br>
 */
package com.isystk.sample.common.util;

import java.io.StringWriter;
import java.util.Locale;

import com.isystk.sample.common.exception.SystemException;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.Template;

/**
 * テンプレートエンジン(freeMarker)ユーティリティークラス。<br>
 * 
 * @author iseyoshitaka
 * 
 */
public final class TemplateUtil {

	private static Configuration config = null;

	/**
	 * テンプレートから文字列を取得する
	 * 
	 * @param templateName
	 *            テンプレートファイル名
	 * @param data
	 *            テンプレートに埋め込むデータ
	 * @return
	 */
	public static String process(String templateName, SimpleHash data) {
		if (config == null) {
			config = new Configuration();
			config.setTemplateLoader(new ClassTemplateLoader(TemplateUtil.class, "/template"));
			config.setObjectWrapper(new DefaultObjectWrapper());
			config.setEncoding(Locale.JAPANESE, "UTF-8");
		}

		Template temp;

		try {
			temp = config.getTemplate(templateName);
		} catch (Exception e) {
			throw new SystemException("テンプレートの取得に失敗しました。", e);
		}

		StringWriter sw = new StringWriter();

		try {
			temp.process(data, sw);
		} catch (Exception e) {
			throw new SystemException("テンプレートの生成に失敗しました。", e);
		}

		return sw.toString();
	}

	// サンプル
	public static void main(String[] args) throws Exception {
		SimpleHash data = new SimpleHash();
		data.put("user", "Big Joe");
		data.put("url", "google");
		data.put("name", "test");

		String test = TemplateUtil.process("test.ftl", data);

	}

}
