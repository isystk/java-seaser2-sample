package com.isystk.sample.common.s2.solr;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.LBHttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

/**
 *
 * LBHttpSolrServer の Wrapper Class です. Solrサーバへの検索インターフェースを提供します.
 *
 * @author iseyoshitaka
 *
 */
public class SolrSearcherServer {

	protected LBHttpSolrServer server_;

	/**
	 * @param server
	 *            {@link LBHttpSolrServer}
	 */
	public SolrSearcherServer(LBHttpSolrServer server) {
		server_ = server;
	}

	/**
	 * 検索を実行します.
	 *
	 * @param query
	 *            {@link SolrQuery}
	 * @param clazz
	 *            結果を格納するDTOのクラス
	 * @return 検索結果が格納されたDTOのリスト
	 */
	public <T> List<T> search(SolrQuery query, Class<T> clazz) {
		try {
			QueryResponse response = server_.query(query, METHOD.POST);

			return response.getBeans(clazz);

		} catch (SolrServerException e) {
			throw new RuntimeSolrServerException(e);
		}
	}

	/**
	 * facet検索を実行します.
	 *
	 * @param query
	 *            {@link SolrQuery}
	 * @param clazz
	 *            結果を格納するDTOのクラス
	 * @return 検索結果が格納されたDTOのリスト
	 */
	public List<FacetField> facet(SolrQuery query) {
		try {
			QueryResponse response = server_.query(query, METHOD.POST);

			return response.getFacetFields();

		} catch (SolrServerException e) {
			throw new RuntimeSolrServerException(e);
		}
	}

	/**
	 * クエリに一致する検索結果の件数を取得します.
	 *
	 * @param query
	 *            {@link SolrQuery}
	 * @return クエリに一致する検索結果の件数
	 */
	public int count(SolrQuery query) {
		try {
			// com.isystk.sample.search.solr.response.CountResponseWriter
			// query.setParam("wt", "count");
			query.setRows(0);

			QueryResponse response = server_.query(query, METHOD.POST);

			return (int) response.getResults().getNumFound();

		} catch (SolrServerException e) {
			throw new RuntimeSolrServerException(e);
		}
	}

}
