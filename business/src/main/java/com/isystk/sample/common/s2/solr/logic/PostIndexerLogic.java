package com.isystk.sample.common.s2.solr.logic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.seasar.framework.util.tiger.CollectionsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isystk.sample.common.constants.Prefecture;
import com.isystk.sample.common.s2.entity.TPost;
import com.isystk.sample.common.s2.entity.TUser;
import com.isystk.sample.common.s2.solr.dto.PostSearchDto;

/**
 * ブライダルフェアのインデクス生成
 *
 * @author iseyoshitaka
 *
 */
public class PostIndexerLogic {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * インデクス更新データのリストを取得します。
     *
     * @param tUserList
     * @param tPostMap
     * @param mPrefectureMap
     * @param freewordMap
     * @param batchExecDate
     * @return インデクス更新データのリスト
     */
    public List<PostSearchDto> getIndexDtoList(List<TUser> tUserList, Map<Integer, List<TPost>> tPostMap,
        	Map<Integer, Prefecture> mPrefectureMap,
        	Map<Integer, List<String>> freewordMap,
	    Date batchExecDate) {
	List<PostSearchDto> list = CollectionsUtil.newArrayList();

	for (TUser tUser : tUserList) {
	    // 投稿
	    if (tPostMap.get(tUser.userId) == null || tPostMap.get(tUser.userId).size() <= 0) {
		// ユーザーは存在するが、投稿が存在しないので生成不要
		continue;
	    }

	    for (TPost tPost : tPostMap.get(tUser.userId)) {

		PostSearchDto dto = new PostSearchDto();

		// 共通
		dto.id = String.valueOf(tPost.postId);

		dto.postId = tPost.postId;
		dto.userId = tPost.userId;
		dto.title = tPost.title;
		dto.text = tPost.text;

		// ソート用
		dto.registTime = tPost.registTime;

		// その他
		dto.indexUpdateTime = batchExecDate;

		list.add(dto);
	    }
	}
	return list;

    }

}
