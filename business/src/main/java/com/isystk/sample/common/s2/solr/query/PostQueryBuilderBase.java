package com.isystk.sample.common.s2.solr.query;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

import com.isystk.sample.common.s2.solr.QueryBuilder;
import com.isystk.sample.common.s2.solr.dto.PostSearchDto;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

/**
 * Postの基本QueryBuilder
 *
 * 自動生成のため原則修正禁止!!
 * 
 * @author iseyoshitaka
 */
public class PostQueryBuilderBase {

    public static QueryBuilder queryBuildBase(PostSearchDto.Searcher searcherDto) {
	QueryBuilder qb = new QueryBuilder();

	if (searcherDto.id != null) {
		qb.mustMatch(PostSearchDto.Searcher.ID, searcherDto.id);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.idOrList)) {
		qb.mustStringList(PostSearchDto.Searcher.ID, searcherDto.idOrList, QueryBuilder.OR);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.idNotList)) {
		qb.mustNotStringList(PostSearchDto.Searcher.ID, searcherDto.idNotList);
	}
  
	if (searcherDto.postId != null) {
		qb.mustMatch(PostSearchDto.Searcher.POST_ID, searcherDto.postId);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.postIdOrList)) {
		qb.mustIntegerList(PostSearchDto.Searcher.POST_ID, searcherDto.postIdOrList, QueryBuilder.OR);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.postIdNotList)) {
		qb.mustNotIntegerList(PostSearchDto.Searcher.POST_ID, searcherDto.postIdNotList);
	}
	if (searcherDto.postIdBetween != null) {
		qb.mustBetween(PostSearchDto.Searcher.POST_ID, searcherDto.postIdBetween);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.postIdBetweenAndList)) {
		qb.mustBetweenIntegerList(PostSearchDto.Searcher.POST_ID, searcherDto.postIdBetweenAndList, QueryBuilder.AND);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.postIdBetweenOrList)) {
		qb.mustBetweenIntegerList(PostSearchDto.Searcher.POST_ID, searcherDto.postIdBetweenOrList, QueryBuilder.OR);
	}
  
	if (searcherDto.userId != null) {
		qb.mustMatch(PostSearchDto.Searcher.USER_ID, searcherDto.userId);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.userIdOrList)) {
		qb.mustIntegerList(PostSearchDto.Searcher.USER_ID, searcherDto.userIdOrList, QueryBuilder.OR);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.userIdNotList)) {
		qb.mustNotIntegerList(PostSearchDto.Searcher.USER_ID, searcherDto.userIdNotList);
	}
	if (searcherDto.userIdBetween != null) {
		qb.mustBetween(PostSearchDto.Searcher.USER_ID, searcherDto.userIdBetween);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.userIdBetweenAndList)) {
		qb.mustBetweenIntegerList(PostSearchDto.Searcher.USER_ID, searcherDto.userIdBetweenAndList, QueryBuilder.AND);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.userIdBetweenOrList)) {
		qb.mustBetweenIntegerList(PostSearchDto.Searcher.USER_ID, searcherDto.userIdBetweenOrList, QueryBuilder.OR);
	}
  
	if (searcherDto.title != null) {
		qb.mustMatch(PostSearchDto.Searcher.TITLE, searcherDto.title);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.titleOrList)) {
		qb.mustStringList(PostSearchDto.Searcher.TITLE, searcherDto.titleOrList, QueryBuilder.OR);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.titleNotList)) {
		qb.mustNotStringList(PostSearchDto.Searcher.TITLE, searcherDto.titleNotList);
	}
  
	if (searcherDto.text != null) {
		qb.mustMatch(PostSearchDto.Searcher.TEXT, searcherDto.text);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.textOrList)) {
		qb.mustStringList(PostSearchDto.Searcher.TEXT, searcherDto.textOrList, QueryBuilder.OR);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.textNotList)) {
		qb.mustNotStringList(PostSearchDto.Searcher.TEXT, searcherDto.textNotList);
	}
  
	if (searcherDto.postImageIdList != null) {
		qb.mustMatch(PostSearchDto.Searcher.POST_IMAGE_ID_LIST, searcherDto.postImageIdList);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.postImageIdListAndList)) {
		qb.mustIntegerList(PostSearchDto.Searcher.POST_IMAGE_ID_LIST, searcherDto.postImageIdListAndList, QueryBuilder.AND);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.postImageIdListOrList)) {
		qb.mustIntegerList(PostSearchDto.Searcher.POST_IMAGE_ID_LIST, searcherDto.postImageIdListOrList, QueryBuilder.OR);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.postImageIdListNotList)) {
		qb.mustNotIntegerList(PostSearchDto.Searcher.POST_IMAGE_ID_LIST, searcherDto.postImageIdListNotList);
	}
	if (searcherDto.postImageIdListBetween != null) {
		qb.mustBetween(PostSearchDto.Searcher.POST_IMAGE_ID_LIST, searcherDto.postImageIdListBetween);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.postImageIdListBetweenAndList)) {
		qb.mustBetweenIntegerList(PostSearchDto.Searcher.POST_IMAGE_ID_LIST, searcherDto.postImageIdListBetweenAndList, QueryBuilder.AND);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.postImageIdListBetweenOrList)) {
		qb.mustBetweenIntegerList(PostSearchDto.Searcher.POST_IMAGE_ID_LIST, searcherDto.postImageIdListBetweenOrList, QueryBuilder.OR);
	}
  
	if (searcherDto.postTagIdList != null) {
		qb.mustMatch(PostSearchDto.Searcher.POST_TAG_ID_LIST, searcherDto.postTagIdList);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.postTagIdListAndList)) {
		qb.mustIntegerList(PostSearchDto.Searcher.POST_TAG_ID_LIST, searcherDto.postTagIdListAndList, QueryBuilder.AND);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.postTagIdListOrList)) {
		qb.mustIntegerList(PostSearchDto.Searcher.POST_TAG_ID_LIST, searcherDto.postTagIdListOrList, QueryBuilder.OR);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.postTagIdListNotList)) {
		qb.mustNotIntegerList(PostSearchDto.Searcher.POST_TAG_ID_LIST, searcherDto.postTagIdListNotList);
	}
	if (searcherDto.postTagIdListBetween != null) {
		qb.mustBetween(PostSearchDto.Searcher.POST_TAG_ID_LIST, searcherDto.postTagIdListBetween);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.postTagIdListBetweenAndList)) {
		qb.mustBetweenIntegerList(PostSearchDto.Searcher.POST_TAG_ID_LIST, searcherDto.postTagIdListBetweenAndList, QueryBuilder.AND);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.postTagIdListBetweenOrList)) {
		qb.mustBetweenIntegerList(PostSearchDto.Searcher.POST_TAG_ID_LIST, searcherDto.postTagIdListBetweenOrList, QueryBuilder.OR);
	}
  
	if (searcherDto.postTagNameList != null) {
		qb.mustMatch(PostSearchDto.Searcher.POST_TAG_NAME_LIST, searcherDto.postTagNameList);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.postTagNameListAndList)) {
		qb.mustStringList(PostSearchDto.Searcher.POST_TAG_NAME_LIST, searcherDto.postTagNameListAndList, QueryBuilder.AND);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.postTagNameListOrList)) {
		qb.mustStringList(PostSearchDto.Searcher.POST_TAG_NAME_LIST, searcherDto.postTagNameListOrList, QueryBuilder.OR);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.postTagNameListNotList)) {
		qb.mustNotStringList(PostSearchDto.Searcher.POST_TAG_NAME_LIST, searcherDto.postTagNameListNotList);
	}
  
	if (searcherDto.freeword != null) {
		qb.mustMatch(PostSearchDto.Searcher.FREEWORD, searcherDto.freeword);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.freewordAndList)) {
		qb.mustStringList(PostSearchDto.Searcher.FREEWORD, searcherDto.freewordAndList, QueryBuilder.AND);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.freewordOrList)) {
		qb.mustStringList(PostSearchDto.Searcher.FREEWORD, searcherDto.freewordOrList, QueryBuilder.OR);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.freewordNotList)) {
		qb.mustNotStringList(PostSearchDto.Searcher.FREEWORD, searcherDto.freewordNotList);
	}
  
	if (searcherDto.registTime != null) {
		qb.mustMatch(PostSearchDto.Searcher.REGIST_TIME, searcherDto.registTime);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.registTimeOrList)) {
		qb.mustDateList(PostSearchDto.Searcher.REGIST_TIME, searcherDto.registTimeOrList, QueryBuilder.OR);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.registTimeNotList)) {
		qb.mustNotDateList(PostSearchDto.Searcher.REGIST_TIME, searcherDto.registTimeNotList);
	}
	if (searcherDto.registTimeBetween != null) {
		qb.mustBetween(PostSearchDto.Searcher.REGIST_TIME, searcherDto.registTimeBetween);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.registTimeBetweenAndList)) {
		qb.mustBetweenDateList(PostSearchDto.Searcher.REGIST_TIME, searcherDto.registTimeBetweenAndList, QueryBuilder.AND);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.registTimeBetweenOrList)) {
		qb.mustBetweenDateList(PostSearchDto.Searcher.REGIST_TIME, searcherDto.registTimeBetweenOrList, QueryBuilder.OR);
	}
  
  
	if (searcherDto.indexUpdateTime != null) {
		qb.mustMatch(PostSearchDto.Searcher.INDEX_UPDATE_TIME, searcherDto.indexUpdateTime);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.indexUpdateTimeOrList)) {
		qb.mustDateList(PostSearchDto.Searcher.INDEX_UPDATE_TIME, searcherDto.indexUpdateTimeOrList, QueryBuilder.OR);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.indexUpdateTimeNotList)) {
		qb.mustNotDateList(PostSearchDto.Searcher.INDEX_UPDATE_TIME, searcherDto.indexUpdateTimeNotList);
	}
	if (searcherDto.indexUpdateTimeBetween != null) {
		qb.mustBetween(PostSearchDto.Searcher.INDEX_UPDATE_TIME, searcherDto.indexUpdateTimeBetween);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.indexUpdateTimeBetweenAndList)) {
		qb.mustBetweenDateList(PostSearchDto.Searcher.INDEX_UPDATE_TIME, searcherDto.indexUpdateTimeBetweenAndList, QueryBuilder.AND);
	}
	if (CollectionUtils.isNotEmpty(searcherDto.indexUpdateTimeBetweenOrList)) {
		qb.mustBetweenDateList(PostSearchDto.Searcher.INDEX_UPDATE_TIME, searcherDto.indexUpdateTimeBetweenOrList, QueryBuilder.OR);
	}
  

	return qb;
    }
}
