package com.isystk.sample.web.common.servlet.filter;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CharResponseWrapper extends HttpServletResponseWrapper {

    private CharArrayWriter cawout;

    private PrintWriter writer;

    public CharResponseWrapper(HttpServletResponse response) {
	super(response);
	cawout = new CharArrayWriter();
	writer = new PrintWriter(cawout);
    }

    public String toString() {
	return cawout.toString();
    }

    public PrintWriter getWriter() {
	return writer;
    }

    /**
     * @deprecated
     */
    public String encodeRedirectUrl(String arg0) {
	return super.encodeRedirectUrl(arg0);
    }

    /**
     * @deprecated
     */
    public String encodeUrl(String arg0) {
	return super.encodeUrl(arg0);
    }

    /**
     * @deprecated
     */
    public void setStatus(int arg0, String arg1) {
	super.setStatus(arg0, arg1);
    }
}