package com.isystk.sample.common.s2.solr.query;

import com.isystk.sample.common.s2.solr.QueryBuilder;
import com.isystk.sample.common.s2.solr.dto.PostSearchDto;

/**
 * PostのQueryBuilder
 *
 * @author iseyoshitaka
 */
public class PostQueryBuilder extends PostQueryBuilderBase {

	public static String queryBuild(PostSearchDto.Searcher searcherDto) {

		QueryBuilder qb = queryBuildBase(searcherDto);

		return qb.toString();
	}
}
