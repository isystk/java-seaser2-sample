package com.isystk.sample.web.front.s2.logic;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.seasar.framework.container.annotation.tiger.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.isystk.sample.common.s2.entity.MPostTag;
import com.isystk.sample.common.s2.service.MPostTagService;
import com.isystk.sample.common.util.BeanCopyUtil;
import com.isystk.sample.web.front.s2.dto.member.post.PostUserTagDto;

/**
 * 投稿共通 ロジック。
 *
 * @author iseyoshitaka
 */
@Component
public class PostCommonLogic {

	/** MPostTagService */
	@Resource
	protected MPostTagService mPostTagService;

	/**
	 * タグIDリストを元にタグのマップを取得します。
	 *
	 * @param tagIdList
	 * @return
	 */
	public Map<String, Integer> getPostTagNameMap(List<Integer> tagIdList) {

		Map<String, Integer> result = Maps.newHashMap();

		List<MPostTag> mPostTagList = mPostTagService.findByIdList(tagIdList);
		for (MPostTag mPostTag : mPostTagList) {
			result.put(mPostTag.getName(), mPostTag.getPostTagId());
		}

		return result;
	}

	/**
	 *
	 * タグIDリストを元にタグのマップを取得します。
	 *
	 * @param tagIdList
	 * @return
	 */
	public Map<Integer, MPostTag> getPostTagIdMap(List<Integer> tagIdList) {

		Map<Integer, MPostTag> result = Maps.newHashMap();

		List<MPostTag> mPostTagList = mPostTagService.findByIdList(tagIdList);
		for (MPostTag mPostTag : mPostTagList) {
			result.put(mPostTag.getPostTagId(), mPostTag);
		}

		return result;
	}

	/**
	 * タグIDリストを元にタグのマップを取得します。
	 *
	 * @return
	 */
	public List<PostUserTagDto> getPostTagList() {

		List<MPostTag> mPostTagList = mPostTagService.findByIdList();

		List<PostUserTagDto> postTagList = Lists.newArrayList();
		for (MPostTag mPostTag : mPostTagList) {
			postTagList.add(BeanCopyUtil.createAndCopy(PostUserTagDto.class, mPostTag).execute());
		}

		return postTagList;
	}

}