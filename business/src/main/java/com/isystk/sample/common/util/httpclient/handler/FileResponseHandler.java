package com.isystk.sample.common.util.httpclient.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

import com.isystk.sample.common.util.httpclient.HttpClientUtil;

/**
 * FileResponseHandler
 * 
 */
public class FileResponseHandler extends BasicResponseHandler {

    public File file;

    /**
     * コンストラクタ
     * 
     * @param url リクエスト先URL
     * @param encoding 文字コード指定
     * @param connectionTimeoutMillis 接続タイムアウト(msec)
     * @param socketTimeoutMillis 処理タイムアウト(msec)
     * @param paramMap リクエストパラメータ情報
     * @param userAgent UserAgent
     * @param file レスポンスを保存するファイル
     */
    public FileResponseHandler(String url, String encoding, int connectionTimeoutMillis, int socketTimeoutMillis, Map<String, String> paramMap,
	    String userAgent, File file) {
	super(url, encoding, connectionTimeoutMillis, socketTimeoutMillis, paramMap, userAgent);
	this.file = file;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer handleResponse(HttpResponse response) throws IOException {

	//ステータスコード設定
	this.status = response.getStatusLine().getStatusCode();

	//200の場合のみ、レスポンスをファイルに保存
	HttpEntity entity = response.getEntity();
	if (this.status == HttpStatus.SC_OK) {
	    if (entity != null) {
		InputStream is = null;
		FileOutputStream fos = null;
		try {
		    is = entity.getContent();
		    fos = new FileOutputStream(file);
		    byte[] bs = new byte[1024 * 1024];
		    int read = 0;
		    while ((read = is.read(bs)) != -1) {
			fos.write(bs, 0, read);
			fos.flush();
		    }
		} catch (Exception e) {
		    throw new IOException(e);
		} finally {
		    if (fos != null)
			fos.close();
		    if (is != null)
			is.close();
		}
	    }
	}
	EntityUtils.consume(entity);

	//Header解析
	String hederLog = parseHeader(response.getAllHeaders());

	//レスポンス内容確認ログ
	StringBuilder log = new StringBuilder();
	log.append(HttpClientUtil.SEP_CHAR).append(HttpClientUtil.SEP_LINE);
	log.append("[Http-Client-File-Response]").append(HttpClientUtil.SEP_CHAR);
	log.append("Status: ").append(this.status).append(HttpClientUtil.SEP_CHAR).append(HttpClientUtil.SEP_CHAR);
	log.append(hederLog);
	if (this.status == HttpStatus.SC_OK) {
	    log.append(HttpClientUtil.SEP_CHAR);
	    log.append("### FILE ###").append(HttpClientUtil.SEP_CHAR);
	    log.append("Path: ").append(file.getAbsolutePath()).append(HttpClientUtil.SEP_CHAR);
	    log.append("Size: ").append(file.length()).append(" byte").append(HttpClientUtil.SEP_CHAR);
	}
	log.append(HttpClientUtil.SEP_LINE);

	this.responseLog = log.toString();

	return this.status;
    }
}
