package com.isystk.sample.common.s2.service;

import static com.isystk.sample.common.s2.entity.names.TPostNames.postId;
import static org.seasar.extension.jdbc.operation.Operations.asc;

import java.util.List;

import javax.annotation.Generated;

import org.apache.commons.collections.CollectionUtils;
import org.seasar.extension.jdbc.where.SimpleWhere;

import com.google.common.collect.Lists;
import com.isystk.sample.common.s2.entity.TPost;
import com.isystk.sample.common.s2.entity.names.TPostNames;

/**
 * {@link TPost}のサービスクラスです。
 *
 */
@Generated(value = { "S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.ServiceModelFactoryImpl" })
public class TPostService extends AbstractService<TPost> {

	/**
	 * 識別子でエンティティを検索します。
	 *
	 * @param postId
	 *            識別子
	 * @return エンティティ
	 */
	public TPost findById(Integer postId) {
		return select().id(postId).getSingleResult();
	}

	/**
	 * 識別子で排他制御を行います。
	 *
	 * @param postId
	 *            識別子
	 * @return エンティティ
	 */
	public TPost forUpdate(Integer postId) {
		return select().forUpdate().id(postId).getSingleResult();
	}

	/**
	 * 識別子で排他制御を行います。
	 *
	 * @param postIdList
	 *            識別子
	 * @return エンティティのリスト
	 */
	public List<TPost> forUpdate(List<Integer> postIdList) {
		if (CollectionUtils.isEmpty(postIdList)) {
			return Lists.newArrayList();
		}
		return select().forUpdate().where(new SimpleWhere().in(postId(), postIdList)).getResultList();
	}

	/**
	 * 識別子の昇順ですべてのエンティティを検索します。
	 *
	 * @return エンティティのリスト
	 */
	public List<TPost> findAllOrderById() {
		return select().orderBy(asc(postId())).getResultList();
	}

	/**
	 * 引数で指定したIDの投稿を取得します。
	 *
	 * @return
	 */
	public TPost findPostListByPostId(Integer postId) {
		if (postId == null) {
			return null;
		}
		return select().leftOuterJoin(TPostNames.TPostImageList()).leftOuterJoin(TPostNames.TPostTagList())
				.where(new SimpleWhere().eq(TPostNames.postId(), postId)).getSingleResult();
	}

	/**
	 * 引数で指定したIDの投稿リストを取得します。
	 *
	 * @return
	 */
	public List<TPost> findPostListByUserId(List<Integer> userIdList) {
		if (CollectionUtils.isEmpty(userIdList)) {
			return Lists.newArrayList();
		}
		return select().leftOuterJoin(TPostNames.TPostImageList()).leftOuterJoin(TPostNames.TPostTagList())
				.where(new SimpleWhere().in(TPostNames.userId(), userIdList)).getResultList();
	}

}