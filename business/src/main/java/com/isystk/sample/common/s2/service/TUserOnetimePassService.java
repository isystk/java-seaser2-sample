package com.isystk.sample.common.s2.service;

import static com.isystk.sample.common.s2.entity.names.TUserOnetimePassNames.userId;
import static org.seasar.extension.jdbc.operation.Operations.asc;

import java.util.List;

import javax.annotation.Generated;

import org.apache.commons.collections.CollectionUtils;
import org.seasar.extension.jdbc.where.SimpleWhere;

import com.google.common.collect.Lists;
import com.isystk.sample.common.s2.entity.TUserOnetimePass;
import com.isystk.sample.common.s2.entity.names.TUserOnetimePassNames;
import com.isystk.sample.common.util.DateUtils;

/**
 * {@link TUserOnetimePass}のサービスクラスです。
 *
 */
@Generated(value = { "S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.ServiceModelFactoryImpl" })
public class TUserOnetimePassService extends AbstractService<TUserOnetimePass> {

	/**
	 * 識別子でエンティティを検索します。
	 *
	 * @param userId
	 *            識別子
	 * @return エンティティ
	 */
	public TUserOnetimePass findById(Integer userId) {
		return select().id(userId).getSingleResult();
	}

	/**
	 * 識別子で排他制御を行います。
	 *
	 * @param userId
	 *            識別子
	 * @return エンティティ
	 */
	public TUserOnetimePass forUpdate(Integer userId) {
		return select().forUpdate().id(userId).getSingleResult();
	}

	/**
	 * 識別子で排他制御を行います。
	 *
	 * @param userIdList
	 *            識別子
	 * @return エンティティのリスト
	 */
	public List<TUserOnetimePass> forUpdate(List<Integer> userIdList) {
		if (CollectionUtils.isEmpty(userIdList)) {
			return Lists.newArrayList();
		}
		return select().forUpdate().where(new SimpleWhere().in(userId(), userIdList)).getResultList();
	}

	/**
	 * 識別子の昇順ですべてのエンティティを検索します。
	 *
	 * @return エンティティのリスト
	 */
	public List<TUserOnetimePass> findAllOrderById() {
		return select().orderBy(asc(userId())).getResultList();
	}

	/**
	 * 指定されたワンタイムキーの件数を取得する
	 *
	 * @param onetimeKey
	 *            ワンタイムキー
	 * @return 検索結果の件数
	 */
	public long countOnetimeKey(String onetimeKey) {
		return select().where(new SimpleWhere().eq(TUserOnetimePassNames.onetimeKey(), onetimeKey)).getCount();
	}

	/**
	 * ワンタイムキーをキーにエンティティを検索します。
	 *
	 * @return エンティティのリスト
	 */
	public TUserOnetimePass findByOnetimeKey(String onetimeKey) {
		return select().where(new SimpleWhere().eq(TUserOnetimePassNames.onetimeKey(), onetimeKey)
				.ge(TUserOnetimePassNames.onetimeValidTime(), DateUtils.getNow())).getSingleResult();
	}

	/**
	 * 会員IDのリストに紐付いたエンティティを削除します。
	 *
	 * @param userIds
	 *            識別子のリスト
	 *
	 */
	public void deleteByUserIds(List<Integer> userIds) {
		if (userIds == null || userIds.size() == 0) {
			return;
		}
		List<TUserOnetimePass> list = select().where(new SimpleWhere().in(userId(), userIds)).getResultList();
		if (list == null || list.size() == 0) {
			return;
		}
		jdbcManager.deleteBatch(list).execute();
	}

}