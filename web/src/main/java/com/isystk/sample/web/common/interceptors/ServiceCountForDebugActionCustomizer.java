package com.isystk.sample.web.common.interceptors;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.seasar.framework.aop.impl.PointcutImpl;
import org.seasar.framework.container.AspectDef;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.customizer.AbstractCustomizer;
import org.seasar.framework.container.factory.AspectDefFactory;
import org.seasar.framework.env.Env;
import org.seasar.struts.annotation.Execute;

/**
 * ServiceCountForDebugActionInterceptorのカスタマイザ。開発環境およびローカル環境のみインターセプターを適用する。
 * 
 */
public class ServiceCountForDebugActionCustomizer extends AbstractCustomizer {

    public ServiceCountForDebugActionCustomizer() {
    }

    ServiceCountForDebugActionInterceptor interceptor = new ServiceCountForDebugActionInterceptor();

    @Override
    protected void doCustomize(final ComponentDef componentDef) {
	if (Env.getValue().equalsIgnoreCase(Env.CT) || Env.getValue().equalsIgnoreCase(Env.UT) || Env.getValue().equalsIgnoreCase("st")) { // stg環境、開発環境およびローカル環境か
	    final Class<?> componentClass = componentDef.getComponentClass();

	    Set<String> methodSet = new HashSet<String>();
	    for (final Method method : componentClass.getMethods()) {
		if (method.isSynthetic() || method.isBridge()) {
		    continue;
		}
		if (method.getDeclaringClass() == Object.class) {
		    continue;
		}

		final Execute methodAttribute = method.getAnnotation(Execute.class);
		if (methodAttribute != null) {
		    methodSet.add(method.getName());
		}
	    }

	    if (methodSet.size() > 0) {
		AspectDef createAspectDef =
		    AspectDefFactory.createAspectDef(interceptor, new PointcutImpl(methodSet.toArray(new String[methodSet.size()])));
		componentDef.addAspectDef(createAspectDef);
	    }
	}
    }

}