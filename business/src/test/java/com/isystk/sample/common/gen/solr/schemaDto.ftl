package com.isystk.sample.common.s2.solr.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

import com.isystk.sample.common.s2.solr.QueryBuilder;
import org.apache.solr.client.solrj.beans.Field;

/**
 * ${schemaDto.coreName}のDto
 *
 * 自動生成のため原則修正禁止!!
 * 
 * @author iseyoshitaka
 */
public class ${schemaDto.className} {

	public static final String CORE_NAME = "${schemaDto.coreName?lower_case}";
	
	@Field("score")
	public Float score;

<#list schemaDto.fieldDtoList as fieldDto>
	<#if fieldDto.type != "random" >
	@Field("${fieldDto.name}")
	    <#if fieldDto.dynamicField >
	public Map<String, ${fieldDto.type}> ${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")?replace("*", "")}_;
	    <#elseif fieldDto.multiValued >
	public List<${fieldDto.type}> ${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")};
	    <#else>
	public ${fieldDto.type} ${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")};
	    </#if>
	</#if>

</#list>


	public static class Searcher {
  
<#list schemaDto.fieldDtoList as fieldDto>
	<#if fieldDto.indexed >
	<#if fieldDto.type != "random" >
		<#if fieldDto.dynamicField >
		public Map<String, ${fieldDto.type}> ${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")?replace("*", "")}AndMap;
		public Map<String, List<${fieldDto.type}>> ${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")?replace("*", "")}AndMapList;
		public Map<String, ${fieldDto.type}> ${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")?replace("*", "")}OrMap;
		public Map<String, List<${fieldDto.type}>> ${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")?replace("*", "")}OrMapList;
		<#else>
		public ${fieldDto.type} ${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")};
			<#if fieldDto.multiValued >
		public List<${fieldDto.type}> ${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")}AndList;
			</#if>
		public List<${fieldDto.type}> ${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")}OrList;
		public List<${fieldDto.type}> ${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")}NotList;
			<#if fieldDto.type = "Integer" || fieldDto.type = "Date">
		public QueryBuilder.Between${fieldDto.type}Dto ${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")}Between;
		public List<QueryBuilder.Between${fieldDto.type}Dto> ${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")}BetweenAndList;
		public List<QueryBuilder.Between${fieldDto.type}Dto> ${fieldDto.name?replace("_", " ")?capitalize?uncap_first?replace(" ", "")}BetweenOrList;
			</#if>
		</#if>
	</#if>
		public static final String ${fieldDto.name?upper_case?replace("*", "")} = "${fieldDto.name?replace("*", "")}";
	</#if>  
	
</#list>
		public Integer sort;

		public Integer page;
		
		public Integer rows;

		public Integer getStart() {
			if (page == null || page <= 0 || rows == null || rows <= 0) return 0;
			return rows * (page - 1);
		}
		
	}
}