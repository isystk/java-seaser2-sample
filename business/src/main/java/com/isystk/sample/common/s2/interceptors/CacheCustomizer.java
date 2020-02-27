package com.isystk.sample.common.s2.interceptors;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.customizer.AbstractCustomizer;
import org.seasar.framework.container.factory.AspectDefFactory;

import com.isystk.sample.common.anotation.Cache;

/**
 * CacheInterceptorのカスタマイザ。@Cacheアノテーション付きのメソッドに対してのみインターセプターを適用する。
 * 
 */
public class CacheCustomizer extends AbstractCustomizer {


    public CacheCustomizer() {
    }

    Map<String, CacheInterceptor> cacheInterceptorMap = new HashMap<String, CacheInterceptor>();

    @Override
    protected void doCustomize(final ComponentDef componentDef) {


	final Class<?> componentClass = componentDef.getComponentClass();

	for (final Method method : componentClass.getMethods()) {
	    if (method.isSynthetic() || method.isBridge()) {
		continue;
	    }
	    if (method.getDeclaringClass() == Object.class) {
		continue;
	    }

	    final Cache methodAttribute = method.getAnnotation(Cache.class);
	    if (methodAttribute != null) {

		CacheInterceptor cacheInterceptor = cacheInterceptorMap.get(componentDef.getComponentClass().getName());
		if (cacheInterceptor == null) {
		    cacheInterceptor = new CacheInterceptor(componentDef);
		    cacheInterceptorMap.put(componentDef.getComponentClass().getName(), cacheInterceptor);
		}

		componentDef.addAspectDef(AspectDefFactory.createAspectDef(cacheInterceptor, method));
	    }
	}
    }

}