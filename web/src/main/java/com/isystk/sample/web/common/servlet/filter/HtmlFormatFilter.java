package com.isystk.sample.web.common.servlet.filter;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.isystk.sample.web.common.servlet.filter.CharResponseWrapper;

/**
 * HTMLに含まれている余分の空白や改行を取り除く。
 *
 * @author iseyoshitaka
 */
public class HtmlFormatFilter implements Filter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		CharResponseWrapper wrapper = new CharResponseWrapper((HttpServletResponse) response);
		chain.doFilter(request, wrapper);

		if (null != wrapper.getContentType() && !"".equals(wrapper.getContentType())) {
			String[] contentType = wrapper.getContentType().split(";");

			if (contentType[0].contains("html")) {

				// HTMLを書き換えます
				String strHtml = wrapper.toString();
				if (null != strHtml && !"".equals(strHtml)) {
					PrintWriter outHtml = response.getWriter();
					try {
						// 前後の空白・タブを除去して、改行だけの行を削除したHTMLに変換する。
						CharArrayWriter caw = new CharArrayWriter();
						caw.write(replaceContent(wrapper.toString()));

						// HTML を出力します。
						outHtml.write(caw.toString());
					} finally {
						outHtml.flush();
						outHtml.close();
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
	}

	/**
	 * 前後の空白・タブを除去して、改行だけの行を削除したHTMLに変換する。
	 * 
	 * @param inBytes
	 *            ソースHTML
	 * @return 所定の文字を取り除いたHTMLテキスト
	 */
	private String replaceContent(String inStr) {
		inStr = trimEx(inStr);
		inStr = trimLine(inStr);
		return inStr;
	}

	/**
	 * 行の両端の空白文字列(半角スペース、タブ文字)を除去します。
	 * <p>
	 * 行末の空白については2文字以上連続した場合にトリムを行います。
	 * 
	 * @param input
	 *            対象HTML文字列
	 * @return 空白を除去した文字列
	 */
	public static final String trimEx(String input) {
		Pattern pattern = Pattern.compile("^[ \t]+|[ \t]+$", Pattern.MULTILINE);
		return pattern.matcher(input).replaceAll("");
	}

	/**
	 * 改行のみの表を削除
	 * 
	 * @param input
	 *            対象HTML文字列
	 * @return 改行を除去した文字列
	 */
	public static final String trimLine(String input) {
		Pattern pattern = Pattern.compile("^(\r\n|\r|\n)$", Pattern.MULTILINE);
		return pattern.matcher(input).replaceAll("");
	}
}