package com.isystk.sample.javalib.cve;

import org.apache.commons.beanutils.BeanUtilsBean;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Struts1,Struts2 &lt;a
 * href="http://www.ipa.go.jp/security/ciadr/vul/20140417-struts.html"
 * target="blank"&gt;CVE-2014-0094&lt;/a&gt;
 * <p/>
 * web.xml &lt;listener&gt;
 * &lt;listener-class&gt;com.isystk.sample.javalib.cve.CVE20140094SafeResolverListener&lt;/listener-class&gt;
 * &lt;/listener&gt;
 * <p/>
 * origin:&lt;a href="https://gist.github.com/nakamura-to/11347570"
 * target="blank"&gt;https://gist.github.com/nakamura-to/11347570&lt;/a&gt;
 *
 * @author kyamada
 */
public class CVE20140094SafeResolverListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		CVE20140094SafeResolver resolver = new CVE20140094SafeResolver();
		BeanUtilsBean.getInstance().getPropertyUtils().setResolver(resolver);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}
}
