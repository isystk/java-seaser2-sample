package com.isystk.sample.common.util.httpclient.handler;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.ResponseHandler;

import com.isystk.sample.common.util.httpclient.HttpClientUtil;

/**
 * BasicResponseHandler
 * 
 */
public abstract class BasicResponseHandler implements ResponseHandler<Integer> {

	public String url;
	public String encoding;
	public int connectionTimeoutMillis;
	public int socketTimeoutMillis;
	public Map<String, String> paramMap;
	public String userAgent;
	public String basicAuthUser;
	public String basicAuthPassword;
	public int status;
	public Map<String, String> header;
	public String requestLog;
	public String responseLog;

	/**
	 * コンストラクタ
	 * 
	 * @param url
	 *            リクエスト先URL
	 * @param encoding
	 *            文字コード指定
	 * @param connectionTimeoutMillis
	 *            接続タイムアウト(msec)
	 * @param socketTimeoutMillis
	 *            処理タイムアウト(msec)
	 * @param paramMap
	 *            リクエストパラメータ情報
	 * @param userAgent
	 *            UserAgent
	 */
	public BasicResponseHandler(String url, String encoding, int connectionTimeoutMillis, int socketTimeoutMillis,
			Map<String, String> paramMap, String userAgent) {
		super();
		this.url = url;
		this.encoding = encoding;
		this.connectionTimeoutMillis = connectionTimeoutMillis;
		this.socketTimeoutMillis = socketTimeoutMillis;
		this.paramMap = paramMap;

		// リクエスト内容確認ログ設定
		StringBuilder log = new StringBuilder();
		log.append(HttpClientUtil.SEP_CHAR).append(HttpClientUtil.SEP_LINE);
		log.append("[Http-Client-Request]").append(HttpClientUtil.SEP_CHAR).append(HttpClientUtil.SEP_CHAR);
		log.append("### Info ###").append(HttpClientUtil.SEP_CHAR);
		log.append("Url: ").append(url).append(HttpClientUtil.SEP_CHAR);
		log.append("Parameter: { ");
		if (!MapUtils.isEmpty(paramMap)) {
			List<String> keyValueList = new ArrayList<String>();
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				String value;
				if (HttpClientUtil.LOG_MASK_TARGET_PARAMETER.contains(entry.getKey())) {
					value = "xxxxxxx";
				} else {
					value = entry.getValue();
				}
				keyValueList.add(entry.getKey() + "=" + value);
			}
			log.append(StringUtils.join(keyValueList, ", ")).append(" ");
		}
		log.append("}").append(HttpClientUtil.SEP_CHAR);
		if (StringUtils.isNotEmpty(userAgent)) {
			log.append("UserAgent: ").append(userAgent).append(HttpClientUtil.SEP_CHAR);
		}
		log.append(HttpClientUtil.SEP_LINE);
		this.requestLog = log.toString();
	}

	/**
	 * Header解析
	 * 
	 * @param headers
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected String parseHeader(Header[] headers) throws UnsupportedEncodingException {
		this.header = new HashMap<String, String>();
		StringBuilder log = new StringBuilder();
		log.append("### Header ###").append(HttpClientUtil.SEP_CHAR);
		for (Header header : headers) {
			String decodeValue = URLDecoder.decode(header.getValue(), this.encoding);
			log.append(header.getName()).append(": ").append(decodeValue).append(HttpClientUtil.SEP_CHAR);
			this.header.put(header.getName(), decodeValue);
		}
		return log.toString();
	}
}
