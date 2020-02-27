package com.isystk.sample.web.userpc.s2.dto.member.post;

import java.util.Date;
import java.util.List;

/**
 * 投稿 詳細Dto
 *
 */
public class PostUserDetailDto {

	/** 投稿ID */
	public Integer postId;

	/** タイトル */
	public String title;

	/** 本文 */
	public String text;

	/** 投稿者ID */
	public Integer userId;

	/** 投稿者名 */
	public String userName;

	/** 投稿写真ID */
	public List<Integer> postImageIdList;

	/** タグID */
	public List<PostUserTagDto> postTagList;

	/** 投稿日時 */
	public Date reistDate;

	/** バージョン */
	public Long version;

}