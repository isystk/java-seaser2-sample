package com.isystk.sample.web.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import org.seasar.framework.beans.util.BeanMap;
import org.seasar.framework.util.ClassLoaderUtil;
import org.seasar.framework.util.ClassTraversal.ClassHandler;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.FieldUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.ResourcesUtil;
import org.seasar.framework.util.ResourcesUtil.Resources;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.struts.util.ServletContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * constantsパッケージのクラスの定数を自動登録します
 *
 * @author iseyoshitaka
 */
public class ConstantsRegister {

	private static final Logger logger = LoggerFactory.getLogger(ConstantsRegister.class);

	private List<String> packageNames = CollectionsUtil.newArrayList();

	/**
	 * 初期化処理
	 */
	private void initialize() {
		for (String s : packageNames) {
			Resources[] resources = ResourcesUtil.getResourcesTypes(s);
			// 親クラスローダからも取得されるので、常にindex: 0のResourceを使用しておく
			resources[0].forEach(new RegisterClassHandler());
		}
	}

	/**
	 * @param packageName
	 *            ルートになるパッケージ名を登録、初期化を行います
	 */
	public void addPackageName(String packageName) {
		packageNames.add(packageName);
		initialize();
	}

	/**
	 * ServletContextに定数の参照を登録
	 *
	 * @param className
	 *            クラス名
	 */
	protected void add(String className) {
		Class<?> clazz = ClassLoaderUtil.loadClass(ResourceUtil.getClassLoader(), className);
		BeanMap beanMap = new BeanMap();
		for (Field f : clazz.getFields()) {
			if (f.getDeclaringClass() != clazz) {
				continue;
			}
			int mod = f.getModifiers();
			if (Modifier.isPublic(mod) && Modifier.isStatic(mod)) {
				beanMap.put(f.getName(), FieldUtil.get(f, null));
			}
		}
		if (beanMap.size() > 0) {
			String shortClassName = ClassUtil.getShortClassName(className);
			ServletContextUtil.getServletContext().setAttribute(shortClassName, beanMap);
			// logger.info("applicationScopeに登録しました:" + "[" + shortClassName + "]" + ":" +
			// beanMap + clazz.getName());
		}
	}

	/**
	 * 定数登録クラス処理用ハンドラです。
	 *
	 * @author iseyoshitaka
	 */
	protected class RegisterClassHandler implements ClassHandler {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void processClass(String packageName, String shortClassName) {
			String className = packageName + "." + shortClassName;
			try {
				add(className);
			} catch (Throwable e) {
				e.printStackTrace();
				logger.info("applicationScopeに登録失敗しました:" + className);
			}
		}
	}
}
