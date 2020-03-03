package com.isystk.sample.javalib.cve;

import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Struts1,Struts2 &lt;a
 * href="http://www.ipa.go.jp/security/ciadr/vul/20140417-struts.html"
 * target="blank"&gt;CVE-2014-0094&lt;/a&gt;
 *
 * web.xml &lt;filter&gt; &lt;filter-name&gt;CVE-2014-0094&lt;/filter-name&gt;
 * &lt;filter-class&gt;com.tamlab.javalib.cve.CVE20140094ServletFilter&lt;/filter-class&gt;
 * &lt;/filter&gt;
 *
 * &lt;filter-mapping&gt; &lt;filter-name&gt;CVE-2014-0094&lt;/filter-name&gt;
 * &lt;url-pattern&gt;/*&lt;/url-pattern&gt; &lt;/filter-mapping&gt;
 *
 * @author cawa origin:&lt;a href="http://www.mbsd.jp/img/testFilter.java"
 *         target="blank"&gt;http://www.mbsd.jp/img/testFilter.java&lt;/a&gt;
 *
 */
public class CVE20140094ServletFilter implements Filter {

	private static Pattern EXLUDE_PARAMS = Pattern.compile("(^|\\W)[cC]lass\\W");

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filter)
			throws IOException, ServletException {
		HttpServletRequest httpreq = (HttpServletRequest) req;
		Enumeration<?> params = httpreq.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			if (isAttack(paramName)) {
				throw new IllegalArgumentException("Attack: " + paramName);
			}
		}
		Cookie cookies[] = httpreq.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				String cookieName = c.getName();
				if (isAttack(cookieName)) {
					throw new IllegalArgumentException("Attack: " + cookieName);
				}
			}
		}
		filter.doFilter(req, res);
	}

	private static boolean isAttack(String target) {
		return EXLUDE_PARAMS.matcher(target).find();
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
