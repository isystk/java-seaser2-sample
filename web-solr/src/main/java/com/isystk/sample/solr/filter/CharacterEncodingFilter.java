/**
 * Copyright(c) team-lab</br>
 */
package com.isystk.sample.solr.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter {

    /**
     * {@inheritDoc}
     * @see javax.servlet.Filter#destroy()
     * @author nkawamata
     */
    public void destroy() {
        // nop
    }

    /** 文字コード */
    private String charset;

    /**
     * {@inheritDoc}
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     * @author nkawamata
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (charset == null || charset.length() <= 0) {
            chain.doFilter(request, response);
            return;
        }

        request.setCharacterEncoding(charset);
        chain.doFilter(request, response);
    }

    /**
     * {@inheritDoc}
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     * @author nkawamata
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        charset = filterConfig.getInitParameter("charset");
    }

}
