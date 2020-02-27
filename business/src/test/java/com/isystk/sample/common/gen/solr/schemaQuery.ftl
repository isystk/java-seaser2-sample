package com.isystk.sample.common.s2.solr.query;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

import com.isystk.sample.common.s2.solr.QueryBuilder;
import com.isystk.sample.common.s2.solr.dto.${schemaDto.coreName}SearchDto;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

/**
 * ${schemaDto.coreName}の基本QueryBuilder
 *
 * 自動生成のため原則修正禁止!!
 * 
 * @author iseyoshitaka
 */
public class ${schemaDto.className} {

    public static QueryBuilder queryBuildBase(${schemaDto.coreName}SearchDto.Searcher searcherDto) {
	QueryBuilder qb = new QueryBuilder();

<#list schemaDto.fieldDtoList as fieldDto>
<#if fieldDto.indexed && fieldDto.type != "random" && !fieldDto.name?starts_with("customsearch_") >
  <#if fieldDto.dynamicField >
	if (MapUtils.isNotEmpty(searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")?replace("*", "")}AndMap)) {
		qb.mustDynamicField(searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")?replace("*", "")}AndMap, QueryBuilder.AND);
	}
	if (MapUtils.isNotEmpty(searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")?replace("*", "")}OrMap)) {
		qb.mustDynamicField(searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")?replace("*", "")}OrMap, QueryBuilder.OR);
	}
	if (MapUtils.isNotEmpty(searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")?replace("*", "")}OrMapList)) {
		qb.mustDynamicFieldList(searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")?replace("*", "")}OrMapList, QueryBuilder.OR);
	}
  <#else>      
	if (searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")} != null) {
		qb.mustMatch(${schemaDto.coreName}SearchDto.Searcher.${fieldDto.name?upper_case}, searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")});
	}
	<#if fieldDto.multiValued >
	if (CollectionUtils.isNotEmpty(searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")}AndList)) {
		qb.must${fieldDto.type}List(${schemaDto.coreName}SearchDto.Searcher.${fieldDto.name?upper_case}, searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")}AndList, QueryBuilder.AND);
	}
	</#if>
	if (CollectionUtils.isNotEmpty(searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")}OrList)) {
		qb.must${fieldDto.type}List(${schemaDto.coreName}SearchDto.Searcher.${fieldDto.name?upper_case}, searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")}OrList, QueryBuilder.OR);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")}NotList)) {
		qb.mustNot${fieldDto.type}List(${schemaDto.coreName}SearchDto.Searcher.${fieldDto.name?upper_case}, searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")}NotList);
	}
    <#if fieldDto.type = "Integer" || fieldDto.type = "Date" >
	if (searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")}Between != null) {
		qb.mustBetween(${schemaDto.coreName}SearchDto.Searcher.${fieldDto.name?upper_case}, searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")}Between);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")}BetweenAndList)) {
		qb.mustBetween${fieldDto.type}List(${schemaDto.coreName}SearchDto.Searcher.${fieldDto.name?upper_case}, searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")}BetweenAndList, QueryBuilder.AND);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")}BetweenOrList)) {
		qb.mustBetween${fieldDto.type}List(${schemaDto.coreName}SearchDto.Searcher.${fieldDto.name?upper_case}, searcherDto.${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")}BetweenOrList, QueryBuilder.OR);
	}
	</#if>
  </#if>
</#if>
  
</#list>

	return qb;
    }
}
