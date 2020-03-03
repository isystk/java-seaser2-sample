package com.isystk.sample.common.s2.solr;

import java.net.MalformedURLException;

import org.apache.solr.client.solrj.impl.LBHttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isystk.sample.common.config.AppConfigNames;
import com.isystk.sample.common.config.Config;
import com.isystk.sample.common.s2.solr.dto.PostSearchDto;

/**
 *
 * core毎のLBHttpSolrServerのインスタンスを生成します。 solrへのリクエストはすべてこのクラスで生成されたインスタンスを使用する
 *
 * @author iseyoshitaka
 *
 */
public class LBHttpSolrSearcherServer {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private LBHttpSolrSearcherServer() {
		try {
			String masterUrl = Config.getString(AppConfigNames.SOLR_MASTER_URL.getKey());
			String slaveUrl = Config.getString(AppConfigNames.SOLR_SLAVE_URL.getKey());

			postServer = new LBHttpSolrServer(masterUrl + PostSearchDto.CORE_NAME + "/",
					slaveUrl + PostSearchDto.CORE_NAME + "/");
		} catch (MalformedURLException e) {
			logger.error("LBHttpSolrServerのインスタンス生成に失敗しました。");
		}
	}

	private static LBHttpSolrSearcherServer instance = new LBHttpSolrSearcherServer();

	public static LBHttpSolrSearcherServer getInstance() {
		return instance;
	}

	private LBHttpSolrServer postServer;

	public LBHttpSolrServer getPostServer() {
		return postServer;
	}

}
