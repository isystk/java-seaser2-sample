package com.isystk.sample.common.util.httpclient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.seasar.framework.util.Base64Util;

import com.isystk.sample.common.util.httpclient.handler.BasicResponseHandler;

/**
 * HttpClientUtil
 */
public class HttpClientUtil {

    protected static final Log LOGGER = LogFactory.getLog(HttpClientUtil.class);

    public static final char SEP_CHAR = '\n';

    public static final String SEP_LINE = "-------------------------------------------------------\n";

    public static final String ENCODING_SHIFT_JIS = "Shift-JIS";

    public static final String ENCODING_UTF8 = "UTF-8";
    
    public static final Set<String> LOG_MASK_TARGET_PARAMETER = new HashSet<String>();
    static {
	LOG_MASK_TARGET_PARAMETER.add("password");
    }

    /**
     * HTTPリクエスト（GET）を発行する
     * 
     * @param handler レスポンスハンドラ
     * @throws Exception
     */
    public static void get(BasicResponseHandler handler) throws Exception {
	//リクエストパラメータ設定
	StringBuilder url = new StringBuilder();
	url.append(handler.url).append("?");
	if (!MapUtils.isEmpty(handler.paramMap)) {
	    for (Map.Entry<String, String> entry : handler.paramMap.entrySet()) {
		url.append(entry.getKey()).append("=").append(entry.getValue());
	    }
	}

	HttpGet get = new HttpGet(url.toString());

	request(handler, get);
    }

    /**
     * HTTPリクエスト（POST）を発行する
     * 
     * @param handler レスポンスハンドラ
     * @throws Exception
     */
    public static void post(BasicResponseHandler handler) throws Exception {
	HttpPost post = new HttpPost(handler.url);

	//リクエストパラメータ設定
	if (!MapUtils.isEmpty(handler.paramMap)) {
	    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
	    for (Map.Entry<String, String> entry : handler.paramMap.entrySet()) {
		formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue() == null ? "" : entry.getValue()));
	    }
	    post.setEntity(new UrlEncodedFormEntity(formparams, handler.encoding));
	}

	request(handler, post);
    }

    /**
     * HTTPリクエストを発行する
     * 
     * @param handler レスポンスハンドラ
     * @param method メソッド
     * @throws Exception
     */
    private static void request(BasicResponseHandler handler, HttpRequestBase method) throws Exception {

	// リクエスト内容確認ログ
	if (LOGGER.isInfoEnabled()) {
	    LOGGER.info(handler.requestLog);
	}

	//タイムアウト設定
	HttpParams httpParams = new BasicHttpParams();
	HttpConnectionParams.setConnectionTimeout(httpParams, handler.connectionTimeoutMillis);
	HttpConnectionParams.setSoTimeout(httpParams, handler.socketTimeoutMillis);

	HttpClient client = new DefaultHttpClient(httpParams);

	try {
	    //UserAgent設定
	    if (StringUtils.isNotEmpty(handler.userAgent)) {
		method.setHeader("User-Agent", handler.userAgent);
	    }

	    //Basic認証
	    if (StringUtils.isNotEmpty(handler.basicAuthUser)) {
		String basicAuthKey = Base64Util.encode((handler.basicAuthUser + ":" + handler.basicAuthPassword).getBytes());
		method.setHeader("Authorization", "Basic " + basicAuthKey);
	    }

	    //リクエスト発行
	    client.execute(method, handler);

	    //レスポンス内容確認ログ
	    if (LOGGER.isInfoEnabled()) {
		LOGGER.info(handler.responseLog);
	    }

	} finally {
	    client.getConnectionManager().shutdown();
	}
    }

    /**
     * HTTPリクエスト（GET）を発行する
     * 
     * @param handler レスポンスハンドラ
     * @param isLocal 実行環境がローカル環境か否か
     * @throws Exception
     */
    public static void get(BasicResponseHandler handler, boolean isLocal) throws Exception {
	//リクエストパラメータ設定
	StringBuilder url = new StringBuilder();
	url.append(handler.url).append("?");
	if (!MapUtils.isEmpty(handler.paramMap)) {
	    for (Map.Entry<String, String> entry : handler.paramMap.entrySet()) {
		url.append(entry.getKey()).append("=").append(entry.getValue());
	    }
	}
	HttpGet get = new HttpGet(url.toString());

	if (isLocal) {
	    requestEgnoreSsl(handler, get);
	} else {
	    request(handler, get);
	}
    }

    /**
     * HTTPリクエストを発行する（ローカルテスト実施時のみ使用）
     * 
     * @param handler レスポンスハンドラ
     * @param method メソッド
     * @throws Exception
     */
    private static void requestEgnoreSsl(BasicResponseHandler handler, HttpRequestBase method) throws Exception {

	// リクエスト内容確認ログ
	if (LOGGER.isInfoEnabled()) {
	    LOGGER.info(handler.requestLog);
	}

	//タイムアウト設定
	HttpParams httpParams = new BasicHttpParams();
	HttpConnectionParams.setConnectionTimeout(httpParams, handler.connectionTimeoutMillis);
	HttpConnectionParams.setSoTimeout(httpParams, handler.socketTimeoutMillis);

	//SSL証明書検証無効化設定
	TrustStrategy trustStrategy = new TrustSelfSignedStrategy();
	HostnameVerifier hostnameVerifier = new AllowAllHostnameVerifier();
	SSLSocketFactory sslSf = new SSLSocketFactory(trustStrategy, (X509HostnameVerifier) hostnameVerifier);
	SchemeRegistry schemeRegistry = new SchemeRegistry();
	schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
	schemeRegistry.register(new Scheme("https", 8443, (SchemeSocketFactory) sslSf));
	schemeRegistry.register(new Scheme("https", 443, (SchemeSocketFactory) sslSf));
	ClientConnectionManager connection = new PoolingClientConnectionManager(schemeRegistry);
	HttpClient client = new DefaultHttpClient(connection, httpParams);

	try {
	    //UserAgent設定
	    if (StringUtils.isNotEmpty(handler.userAgent)) {
		method.setHeader("User-Agent", handler.userAgent);
	    }

	    //Basic認証
	    if (StringUtils.isNotEmpty(handler.basicAuthUser)) {
		String basicAuthKey = Base64Util.encode((handler.basicAuthUser + ":" + handler.basicAuthPassword).getBytes());
		method.setHeader("Authorization", "Basic " + basicAuthKey);
	    }

	    //リクエスト発行
	    client.execute(method, handler);

	    //レスポンス内容確認ログ
	    if (LOGGER.isInfoEnabled()) {
		LOGGER.info(handler.responseLog);
	    }

	} finally {
	    client.getConnectionManager().shutdown();
	}
    }

}
