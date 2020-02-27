package com.isystk.sample.common.util.httpclient.handler;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.isystk.sample.common.util.httpclient.HttpClientUtil;

/**
 * StringResponseHandler
 * 
 */
public class StringResponseHandler extends BasicResponseHandler {

    public String body;

    /**
     * コンストラクタ
     * 
     * @param url リクエスト先URL
     * @param encoding 文字コード指定
     * @param connectionTimeoutMillis 接続タイムアウト(msec)
     * @param socketTimeoutMillis 処理タイムアウト(msec)
     * @param paramMap リクエストパラメータ情報
     * @param userAgent UserAgent
     */
    public StringResponseHandler(String url, String encoding, int connectionTimeoutMillis, int socketTimeoutMillis, Map<String, String> paramMap,
	    String userAgent) {
	super(url, encoding, connectionTimeoutMillis, socketTimeoutMillis, paramMap, userAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer handleResponse(HttpResponse response) throws IOException {

	//ステータスコード設定
	this.status = response.getStatusLine().getStatusCode();

	//レスポンス取得
	this.body = EntityUtils.toString(response.getEntity(), this.encoding);
	EntityUtils.consume(response.getEntity());

	//Header解析
	String hederLog = parseHeader(response.getAllHeaders());

	//レスポンス内容確認ログ
	StringBuilder log = new StringBuilder();
	log.append(HttpClientUtil.SEP_CHAR).append(HttpClientUtil.SEP_LINE);
	log.append("[Http-Client-String-Response]").append(HttpClientUtil.SEP_CHAR);
	log.append("Status: ").append(this.status).append(HttpClientUtil.SEP_CHAR).append(HttpClientUtil.SEP_CHAR);
	log.append(hederLog).append(HttpClientUtil.SEP_CHAR);
	log.append("### BODY ###").append(HttpClientUtil.SEP_CHAR);
	log.append(this.body).append(HttpClientUtil.SEP_CHAR);
	log.append(HttpClientUtil.SEP_LINE);

	this.responseLog = log.toString();

	return this.status;
    }
}
