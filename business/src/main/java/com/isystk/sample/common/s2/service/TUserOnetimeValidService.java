package com.isystk.sample.common.s2.service;

import static com.isystk.sample.common.s2.entity.names.TUserOnetimeValidNames.userId;
import static org.seasar.extension.jdbc.operation.Operations.asc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.collections.CollectionUtils;
import org.seasar.extension.jdbc.where.SimpleWhere;

import com.google.common.collect.Lists;
import com.isystk.sample.common.s2.entity.TUserOnetimeValid;
import com.isystk.sample.common.s2.entity.names.TUserOnetimeValidNames;
import com.isystk.sample.common.util.DateUtils;

/**
 * {@link TUserOnetimeValid}のサービスクラスです。
 *
 */
@Generated(value = { "S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.ServiceModelFactoryImpl" })
public class TUserOnetimeValidService extends AbstractService<TUserOnetimeValid> {

	/** 削除SQL */
	private static final String DELETE_BY_ID_LIST = "sql/TUserOnetimeValidService_deleteByIdList.sql";

	/**
	 * 識別子でエンティティを検索します。
	 *
	 * @param userId
	 *            識別子
	 * @return エンティティ
	 */
	public TUserOnetimeValid findById(Integer userId) {
		return select().id(userId).getSingleResult();
	}

	/**
	 * 識別子で排他制御を行います。
	 *
	 * @param userId
	 *            識別子
	 * @return エンティティ
	 */
	public TUserOnetimeValid forUpdate(Integer userId) {
		return select().forUpdate().id(userId).getSingleResult();
	}

	/**
	 * 識別子で排他制御を行います。
	 *
	 * @param userIdList
	 *            識別子
	 * @return エンティティのリスト
	 */
	public List<TUserOnetimeValid> forUpdate(List<Integer> userIdList) {
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
	public List<TUserOnetimeValid> findAllOrderById() {
		return select().orderBy(asc(userId())).getResultList();
	}

	/**
	 * ワンタイムキーをキーにエンティティを検索します。
	 *
	 * @return エンティティのリスト
	 */
	public TUserOnetimeValid findByOnetimeKey(String onetimeKey) {
		return select().where(new SimpleWhere().eq(TUserOnetimeValidNames.onetimeKey(), onetimeKey)
				.ge(TUserOnetimeValidNames.onetimeValidTime(), DateUtils.getNow())).getSingleResult();
	}

	/**
	 * エンティティを削除します。
	 *
	 * @param userIdList
	 *            会員IDリスト
	 * @return 削除件数
	 */
	public int deleteByIdList(List<Integer> userIdList) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userIdList", userIdList);
		return jdbcManager.updateBySqlFile(DELETE_BY_ID_LIST, param).execute();
	}

}