package com.isystk.sample.common.s2.service;

import static com.isystk.sample.common.s2.entity.names.TPostImageNames.imageId;
import static com.isystk.sample.common.s2.entity.names.TPostImageNames.postId;
import static org.seasar.extension.jdbc.operation.Operations.asc;

import java.util.List;

import javax.annotation.Generated;

import org.seasar.extension.jdbc.where.SimpleWhere;

import com.isystk.sample.common.s2.entity.TPostImage;
import com.isystk.sample.common.s2.entity.names.TPostImageNames;

/**
 * {@link TPostImage}のサービスクラスです。
 *
 */
@Generated(value = { "S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.ServiceModelFactoryImpl" })
public class TPostImageService extends AbstractService<TPostImage> {

	/**
	 * 識別子でエンティティを検索します。
	 *
	 * @param postId
	 *            識別子
	 * @param imageId
	 *            識別子
	 * @return エンティティ
	 */
	public TPostImage findById(Integer postId, Integer imageId) {
		return select().id(postId, imageId).getSingleResult();
	}

	/**
	 * 識別子の昇順ですべてのエンティティを検索します。
	 *
	 * @return エンティティのリスト
	 */
	public List<TPostImage> findAllOrderById() {
		return select().orderBy(asc(postId()), asc(imageId())).getResultList();
	}

	/**
	 * 識別子でエンティティを検索します。
	 *
	 * @param postId
	 *            識別子
	 * @return エンティティ
	 */
	public List<TPostImage> findListByPostId(Integer postId) {
		return select().where(new SimpleWhere().eq(TPostImageNames.postId(), postId)).getResultList();
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
		List<TPostImage> list = findListByPostId(postId);
		for (TPostImage entity : list) {
			count = +jdbcManager.delete(entity).execute();
		}
		return count;
	}

}