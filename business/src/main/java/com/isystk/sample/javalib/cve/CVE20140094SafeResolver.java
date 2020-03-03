package com.isystk.sample.javalib.cve;

import org.apache.commons.beanutils.expression.DefaultResolver;

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
public class CVE20140094SafeResolver extends DefaultResolver {

	@Override
	public String next(String expression) {
		String property = super.next(expression);
		if ("class".equalsIgnoreCase(property)) {
			return "";
		}
		return property;
	}
}
