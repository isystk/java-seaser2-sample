package com.isystk.sample.web.front.s2.logic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

import com.google.inject.internal.Lists;
import com.isystk.sample.common.constants.Flg;
import com.isystk.sample.common.exception.SystemException;
import com.isystk.sample.common.s2.entity.MPostTag;
import com.isystk.sample.common.s2.entity.TPost;
import com.isystk.sample.common.s2.entity.TPostImage;
import com.isystk.sample.common.s2.entity.TPostTag;
import com.isystk.sample.common.s2.entity.TUser;
import com.isystk.sample.common.s2.service.MPostTagService;
import com.isystk.sample.common.s2.service.TPostImageService;
import com.isystk.sample.common.s2.service.TPostService;
import com.isystk.sample.common.s2.service.TPostTagService;
import com.isystk.sample.common.s2.service.TUserService;
import com.isystk.sample.common.util.BeanCopyUtil;
import com.isystk.sample.common.util.DateUtils;
import com.isystk.sample.common.util.StringUtils;
import com.isystk.sample.web.front.s2.dto.UserDto;
import com.isystk.sample.web.front.s2.dto.member.post.PostUserDetailDto;
import com.isystk.sample.web.front.s2.dto.member.post.PostUserTagDto;

/**
 * 投稿(ユーザー型)関連のロジック
 *
 */
public class MemberPostLogic {

	/** 投稿サービス */
	@Resource
	protected TPostService tPostService;

	/** 投稿タグサービス */
	@Resource
	protected TPostTagService tPostTagService;

	/** 投稿画像サービス */
	@Resource
	protected TPostImageService tPostImageService;

	/** ユーザーサービス */
	@Resource
	protected TUserService tUserService;

	/** 投稿タグサービス */
	@Resource
	protected MPostTagService mPostTagService;

	/** 投稿共通ロジック */
	@Resource
	protected PostCommonLogic postCommonLogic;

	/**
	 * 投稿(ユーザー投稿型) データを取得します
	 *
	 * @return
	 */
	public PostUserDetailDto findPostDetailUser(TPost tPost) {
		if (tPost == null) {
			return new PostUserDetailDto();
		}

		// 投稿テーブルの情報を転送
		PostUserDetailDto result = BeanCopyUtil.createAndCopy(PostUserDetailDto.class, tPost).execute();

		// 投稿画像テーブルの情報を転送
		result.postImageIdList = Lists.newArrayList();
		if (!CollectionUtils.isEmpty(tPost.TPostImageList)) {
			for (TPostImage tPostImage : tPost.TPostImageList) {
				result.postImageIdList.add(tPostImage.imageId);
			}
		}

		Map<Integer, MPostTag> postTagMap = null;
		if (tPost.TPostTagList != null) {
			postTagMap = postCommonLogic
					.getPostTagIdMap(Lists.newArrayList(CollectionUtils.collect(tPost.TPostTagList, new Transformer() {
						public Object transform(Object o) {
							return ((TPostTag) o).postTagId;
						}
					})));
		}

		// 投稿タグテーブルの情報を転送
		result.postTagList = Lists.newArrayList();
		if (!CollectionUtils.isEmpty(tPost.TPostTagList)) {
			for (TPostTag tag : tPost.TPostTagList) {
				PostUserTagDto dto = new PostUserTagDto();
				dto.postTagId = tag.postTagId;
				dto.name = postTagMap.get(tag.postTagId).getName();
				result.postTagList.add(dto);
			}
		}

		// 投稿者
		if (tPost.userId != null) {
			TUser tUser = tUserService.findById(tPost.userId);
			result.userName = StringUtils.join(" ", tUser.familyName, tUser.name);
		}

		return result;
	}

	/**
	 * 投稿(ユーザー投稿型) データを登録・更新します
	 *
	 * @param detailDto
	 * @param version
	 */
	public TPost regist(PostUserDetailDto detailDto) {
		if (detailDto == null) {
			throw new IllegalArgumentException("引数不正");
		}

		boolean isNew = false;
		if (detailDto.postId == null) {
			isNew = true;
		}

		TPost tPost = null;
		// 投稿
		{
			if (isNew) {
				// 新規
				tPost = BeanCopyUtil.createAndCopy(TPost.class, detailDto).execute();
				UserDto userDto = UserDto.getUserDto();
				tPost.userId = userDto.getUserId();
				Date now = DateUtils.getNow();
				tPost.registTime = now;
				tPost.updateTime = now;
				tPostService.insertExcludesNull(tPost);

				// 生成された postId を設定
				detailDto.postId = tPost.postId;
			} else {
				// 変更
				tPost = tPostService.forUpdate(detailDto.postId);
				BeanCopyUtil.copy(detailDto, tPost).execute();
				UserDto userDto = UserDto.getUserDto();
				tPost.userId = userDto.getUserId();
				Date now = DateUtils.getNow();
				tPost.updateTime = now;
				tPostService.update(tPost);
			}
		}

		// 投稿（画像）Delete→Insert
		{
			tPostImageService.deleteByPostId(detailDto.postId);
			if (!CollectionUtils.isEmpty(detailDto.postImageIdList)) {
				for (Integer postImageId : detailDto.postImageIdList) {
					TPostImage tPostImage = new TPostImage();
					tPostImage.postId = detailDto.postId;
					tPostImage.imageId = postImageId;
					tPostImageService.insert(tPostImage);
				}
			}
		}

		// 投稿（タグ）Delete→Insert
		{
			tPostTagService.deleteByPostId(detailDto.postId);
			if (!CollectionUtils.isEmpty(detailDto.postTagList)) {
				for (PostUserTagDto tag : detailDto.postTagList) {
					TPostTag tPostTag = new TPostTag();
					tPostTag.postId = detailDto.postId;
					tPostTag.postTagId = tag.postTagId;
					tPostTagService.insert(tPostTag);
				}
			}
		}

		return tPost;
	}

	/**
	 * タグIDの名称を取得します。
	 *
	 * @param tagIdList
	 * @return
	 */
	public List<PostUserTagDto> findPeopleUserTagDto(List<Integer> tagIdList) {
		return Lists.newArrayList(CollectionUtils.collect(mPostTagService.findByIdList(tagIdList), new Transformer() {
			public Object transform(Object o) {
				PostUserTagDto dto = new PostUserTagDto();
				dto.postTagId = ((MPostTag) o).postTagId;
				dto.name = ((MPostTag) o).name;
				return dto;
			}
		}));
	}

	/**
	 * タグを新規に追加申請します。
	 */
	public List<PostUserTagDto> addPostTag(List<String> tagNameList) {

		// 既に登録済みのタグが含まれているかどうかをチェックします。
		boolean exists = mPostTagService.existsByTagNameList(tagNameList);

		if (exists) {
			throw new SystemException("既に存在するタグが追加申請されました。tagNameList:" + tagNameList + "]");
		}

		List<PostUserTagDto> mPostTagList = Lists.newArrayList();
		for (String tagName : tagNameList) {
			MPostTag mPostTag = new MPostTag();
			mPostTag.name = tagName;
			mPostTag.registTime = DateUtils.getNow();
			mPostTag.updateTime = DateUtils.getNow();
			mPostTag.deleteFlg = Flg.OFF.getValue();
			mPostTagService.insert(mPostTag);

			PostUserTagDto dto = new PostUserTagDto();
			dto.postTagId = mPostTag.postTagId;
			dto.name = mPostTag.name;
			mPostTagList.add(dto);
		}

		return mPostTagList;
	}

}