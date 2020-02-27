package com.isystk.sample.common.s2.solr;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;

/**
 * SolrServer の Wrapper Class です. Solrサーバへの更新インターフェースを提供します.
 *
 * @author iseyoshitaka
 */
public class SolrIndexerServer {

    private static final int STATUS_OK = 0;

    protected SolrServer server_;


    /**
     * @param server {@link SolrServer}
     */
    public SolrIndexerServer(SolrServer server) {
	server_ = server;
    }

    /**
     * 指定されたクエリに一致するインデックスデータを削除します.
     *
     * @param query 削除対象を絞り込むクエリ
     * @throws SolrServerException
     */
    public void delete(String query) throws SolrServerException {
	UpdateResponse response = null;

	try {
	    response = server_.deleteByQuery(query);
	} catch (IOException e) {
	    throw new SolrServerException(e);
	}

	if (response.getStatus() != STATUS_OK) {
	    throw new SolrServerException("Failed to update. Status-code is '" + response.getStatus() + "'.");
	}
	}

    /**
     * インデックスデータを更新します.
     *
     * @param data 更新対象データのリスト
     */
    public void update(List<?> data) throws SolrServerException {
	UpdateResponse response = null;

	try {
	    response = server_.addBeans(data);
	} catch (IOException e) {
	    throw new SolrServerException(e);
	}

	if (response.getStatus() != STATUS_OK) {
	    throw new SolrServerException("Failed to update. Status-code is '" + response.getStatus() + "'.");
	}
	}

    /**
     * optimize を実行します.
     */
    public void optimize() throws SolrServerException {
	try {
	    server_.optimize();
	} catch (IOException e) {
	    throw new SolrServerException(e);
	}
    }

    /**
     * commit を実行します.
     */
    public void commit() throws SolrServerException {
	try {
	    server_.commit();
	} catch (IOException e) {
	    throw new SolrServerException(e);
	}
    }

    /**
     * rollback を実行します.
     */
    public void rollback() throws SolrServerException {
	try {
	    server_.rollback();
	} catch (IOException e) {
	    throw new SolrServerException(e);
	}
    }
}
