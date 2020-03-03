package com.isystk.sample.common.s2.solr.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isystk.sample.common.s2.solr.LBHttpSolrSearcherServer;
import com.isystk.sample.common.s2.solr.QueryBuilder;
import com.isystk.sample.common.s2.solr.SolrSearcherServer;
import com.isystk.sample.common.s2.solr.dto.PostSearchDto;
import com.isystk.sample.common.s2.solr.query.PostQueryBuilder;
import com.isystk.sample.common.s2.solr.util.PostSortBuilder;

/**
 * 投稿のインデクス検索
 *
 * @author iseyoshitaka
 *
 */
public class PostSearcherLogic {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * solr検索の検索結果を取得します
	 *
	 * @param searcherDto
	 * @return resultDocList
	 */
	public List<PostSearchDto> search(PostSearchDto.Searcher searcherDto) {
		return search(searcherDto, null);
	}

	/**
	 * solr検索の検索結果を取得します
	 *
	 * @param searcherDto
	 * @return resultDocList
	 */
	public List<PostSearchDto> search(PostSearchDto.Searcher searcherDto, String dynamicSortKey) {

		List<PostSearchDto> resultDocList = CollectionsUtil.newArrayList();

		SolrSearcherServer server = new SolrSearcherServer(LBHttpSolrSearcherServer.getInstance().getPostServer());
		resultDocList = server.search(makeSolrQuery(searcherDto, dynamicSortKey), PostSearchDto.class);

		return resultDocList;
	}

	/**
	 * solr検索の検索結果件数を取得します
	 *
	 * @param searcherDto
	 * @return resultCount
	 */
	public int count(PostSearchDto.Searcher searcherDto) {
		int resultCount = 0;
		SolrSearcherServer server = new SolrSearcherServer(LBHttpSolrSearcherServer.getInstance().getPostServer());
		resultCount = server.count(makeSolrQuery(searcherDto, null));

		return resultCount;
	}

	/**
	 * searcherDtoを基にsolrへの検索条件を生成します。
	 *
	 * @param searcherDto
	 * @return query
	 */
	private SolrQuery makeSolrQuery(PostSearchDto.Searcher searcherDto, String dynamicSortKey) {

		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(PostQueryBuilder.queryBuild(searcherDto));
		solrQuery.addFilterQuery(new QueryBuilder().toString());
		// solrQuery.setQuery(new QueryBuilder().toString());
		// solrQuery.addFilterQuery(PostQueryBuilder.queryBuild(searcherDto));
		solrQuery.setRows(searcherDto.rows);
		solrQuery.setStart(searcherDto.getStart());
		solrQuery.setRows(searcherDto.rows);
		if (searcherDto.freeword != null || searcherDto.freewordAndList != null || searcherDto.freewordNotList != null
				|| searcherDto.freewordOrList != null) {
			solrQuery.setFields("*,score");
		}
		PostSortBuilder.setSort(solrQuery, searcherDto.sort, dynamicSortKey);

		return solrQuery;
	}

	/**
	 * solr検索のフィールド毎の検索結果件数を取得します
	 *
	 * @param searcherDto
	 * @return resultMap
	 */
	public Map<String, Long> facet(PostSearchDto.Searcher searcherDto) {

		SolrSearcherServer server = new SolrSearcherServer(LBHttpSolrSearcherServer.getInstance().getPostServer());
		// facetのテスト
		SolrQuery query = makeSolrQuery(searcherDto, null);
		query.addFacetField(PostSearchDto.Searcher.POST_TAG_ID_LIST);

		Map<String, Long> resultMap = new HashMap<String, Long>();

		List<FacetField> facetList = server.facet(query);
		for (FacetField facet : facetList) {
			if (facet.getValues() == null) {
				continue;
			}
			for (Count cnt : facet.getValues()) {
				resultMap.put(facet.getName() + "_" + cnt.getName(), cnt.getCount());
			}
		}

		return resultMap;
	}

}
