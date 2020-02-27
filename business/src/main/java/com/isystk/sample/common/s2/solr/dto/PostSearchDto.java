package com.isystk.sample.common.s2.solr.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

import com.isystk.sample.common.s2.solr.QueryBuilder;
import org.apache.solr.client.solrj.beans.Field;

/**
 * PostのDto
 *
 * 自動生成のため原則修正禁止!!
 * 
 * @author iseyoshitaka
 */
public class PostSearchDto {

	public static final String CORE_NAME = "post";
	
	@Field("score")
	public Float score;

	@Field("id")
	public String id;

	@Field("post_id")
	public Integer postId;

	@Field("user_id")
	public Integer userId;

	@Field("title")
	public String title;

	@Field("text")
	public String text;

	@Field("post_image_id_list")
	public List<Integer> postImageIdList;

	@Field("post_tag_id_list")
	public List<Integer> postTagIdList;

	@Field("post_tag_name_list")
	public List<String> postTagNameList;

	@Field("freeword")
	public List<String> freeword;

	@Field("regist_time")
	public Date registTime;


	@Field("index_update_time")
	public Date indexUpdateTime;



	public static class Searcher {
  
		public String id;
		public List<String> idOrList;
		public List<String> idNotList;
		public static final String ID = "id";
	
		public Integer postId;
		public List<Integer> postIdOrList;
		public List<Integer> postIdNotList;
		public QueryBuilder.BetweenIntegerDto postIdBetween;
		public List<QueryBuilder.BetweenIntegerDto> postIdBetweenAndList;
		public List<QueryBuilder.BetweenIntegerDto> postIdBetweenOrList;
		public static final String POST_ID = "post_id";
	
		public Integer userId;
		public List<Integer> userIdOrList;
		public List<Integer> userIdNotList;
		public QueryBuilder.BetweenIntegerDto userIdBetween;
		public List<QueryBuilder.BetweenIntegerDto> userIdBetweenAndList;
		public List<QueryBuilder.BetweenIntegerDto> userIdBetweenOrList;
		public static final String USER_ID = "user_id";
	
		public String title;
		public List<String> titleOrList;
		public List<String> titleNotList;
		public static final String TITLE = "title";
	
		public String text;
		public List<String> textOrList;
		public List<String> textNotList;
		public static final String TEXT = "text";
	
		public Integer postImageIdList;
		public List<Integer> postImageIdListAndList;
		public List<Integer> postImageIdListOrList;
		public List<Integer> postImageIdListNotList;
		public QueryBuilder.BetweenIntegerDto postImageIdListBetween;
		public List<QueryBuilder.BetweenIntegerDto> postImageIdListBetweenAndList;
		public List<QueryBuilder.BetweenIntegerDto> postImageIdListBetweenOrList;
		public static final String POST_IMAGE_ID_LIST = "post_image_id_list";
	
		public Integer postTagIdList;
		public List<Integer> postTagIdListAndList;
		public List<Integer> postTagIdListOrList;
		public List<Integer> postTagIdListNotList;
		public QueryBuilder.BetweenIntegerDto postTagIdListBetween;
		public List<QueryBuilder.BetweenIntegerDto> postTagIdListBetweenAndList;
		public List<QueryBuilder.BetweenIntegerDto> postTagIdListBetweenOrList;
		public static final String POST_TAG_ID_LIST = "post_tag_id_list";
	
		public String postTagNameList;
		public List<String> postTagNameListAndList;
		public List<String> postTagNameListOrList;
		public List<String> postTagNameListNotList;
		public static final String POST_TAG_NAME_LIST = "post_tag_name_list";
	
		public String freeword;
		public List<String> freewordAndList;
		public List<String> freewordOrList;
		public List<String> freewordNotList;
		public static final String FREEWORD = "freeword";
	
		public Date registTime;
		public List<Date> registTimeOrList;
		public List<Date> registTimeNotList;
		public QueryBuilder.BetweenDateDto registTimeBetween;
		public List<QueryBuilder.BetweenDateDto> registTimeBetweenAndList;
		public List<QueryBuilder.BetweenDateDto> registTimeBetweenOrList;
		public static final String REGIST_TIME = "regist_time";
	
		public static final String RANDOM = "random";
	
		public Date indexUpdateTime;
		public List<Date> indexUpdateTimeOrList;
		public List<Date> indexUpdateTimeNotList;
		public QueryBuilder.BetweenDateDto indexUpdateTimeBetween;
		public List<QueryBuilder.BetweenDateDto> indexUpdateTimeBetweenAndList;
		public List<QueryBuilder.BetweenDateDto> indexUpdateTimeBetweenOrList;
		public static final String INDEX_UPDATE_TIME = "index_update_time";
	
		public Integer sort;

		public Integer page;
		
		public Integer rows;

		public Integer getStart() {
			if (page == null || page <= 0 || rows == null || rows <= 0) return 0;
			return rows * (page - 1);
		}
		
	}
}