package com.isystk.sample.common.s2.service;

import static com.isystk.sample.common.s2.entity.names.TPostTagNames.postId;
import static com.isystk.sample.common.s2.entity.names.TPostTagNames.postTagId;
import static org.seasar.extension.jdbc.operation.Operations.asc;

import java.util.List;

import javax.annotation.Generated;

import org.seasar.extension.jdbc.where.SimpleWhere;

import com.isystk.sample.common.s2.entity.TPostTag;
import com.isystk.sample.common.s2.entity.names.TPostTagNames;

/**
 * {@link TPostTag}のサービスクラスです。
 *
 */
@Generated(value = { "S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.ServiceModelFactoryImpl" })
public class TPostTagService extends AbstractService<TPostTag> {

	/**
	 * 識別子でエンティティを検索します。
	 *
	 * @param postId
	 *            識別子
	 * @param postTagId
	 *            識別子
	 * @return エンティティ
	 */
	public TPostTag findById(Integer postId, Integer postTagId) {
		return select().id(postId, postTagId).getSingleResult();
	}

	/**
	 * 識別子の昇順ですべてのエンティティを検索します。
	 *
	 * @return エンティティのリスト
	 */
	public List<TPostTag> findAllOrderById() {
		return select().orderBy(asc(postId()), asc(postTagId())).getResultList();
	}

	/**
	 * 識別子でエンティティを検索します。
	 *
	 * @param postId
	 *            識別子
	 * @return エンティティ
	 */
	public List<TPostTag> findListByPostId(Integer postId) {
		return select().where(new SimpleWhere().eq(TPostTagNames.postId(), postId)).getResultList();
	}

	/**
	 * 識別子に紐付いたエンティティを削除します。
	 *
	 * @param postId
	 *            識別子
	 * @return 更新した行数
	 */
	public int deleteByPostId(Integer postId) {
		if (postId == null) {
			return 0;
		}
		int count = 0;
		List<TPostTag> list = findListByPostId(postId);
		for (TPostTag entity : list) {
			count = +jdbcManager.delete(entity).execute();
		}
		return count;
	}

}