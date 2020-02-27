package com.isystk.sample.common.s2.service;

import static com.isystk.sample.common.s2.entity.names.MPostTagNames.postTagId;
import static org.seasar.extension.jdbc.operation.Operations.asc;

import java.util.List;

import javax.annotation.Generated;

import org.apache.commons.collections.CollectionUtils;
import org.seasar.extension.jdbc.where.SimpleWhere;

import com.google.common.collect.Lists;
import com.isystk.sample.common.constants.Flg;
import com.isystk.sample.common.s2.entity.MPostTag;
import com.isystk.sample.common.s2.entity.names.MPostTagNames;

/**
 * {@link MPostTag}のサービスクラスです。
 *
 */
@Generated(value = { "S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.ServiceModelFactoryImpl" })
public class MPostTagService extends AbstractService<MPostTag> {

	/**
	 * 識別子でエンティティを検索します。
	 *
	 * @param postTagId
	 *            識別子
	 * @return エンティティ
	 */
	public MPostTag findById(Integer postTagId) {
		return select().id(postTagId).getSingleResult();
	}

	/**
	 * 識別子で排他制御を行います。
	 *
	 * @param postTagId
	 *            識別子
	 * @return エンティティ
	 */
	public MPostTag forUpdate(Integer postTagId) {
		return select().forUpdate().id(postTagId).getSingleResult();
	}

	/**
	 * 識別子で排他制御を行います。
	 *
	 * @param postTagIdList
	 *            識別子
	 * @return エンティティのリスト
	 */
	public List<MPostTag> forUpdate(List<Integer> postTagIdList) {
		if (CollectionUtils.isEmpty(postTagIdList)) {
			return Lists.newArrayList();
		}
		return select().forUpdate().where(new SimpleWhere().in(postTagId(), postTagIdList)).getResultList();
	}

	/**
	 * 識別子の昇順ですべてのエンティティを検索します。
	 *
	 * @return エンティティのリスト
	 */
	public List<MPostTag> findAllOrderById() {
		return select().orderBy(asc(postTagId())).getResultList();
	}

	/**
	 * 有効な状態の投稿タグリストを取得します。
	 *
	 * @return
	 */
	public List<MPostTag> findEnablePostTagList() {
		return select().where(new SimpleWhere().eq(MPostTagNames.deleteFlg(), Flg.OFF.getCode())).getResultList();
	}

	/**
	 * 引数で指定したタグIDのリストを取得します。
	 *
	 * @return
	 */
	public List<MPostTag> findByIdList(List<Integer> postTagIdList) {
		return select().where(new SimpleWhere().in(MPostTagNames.postTagId(), postTagIdList)).getResultList();
	}

	/**
	 * 有効なタグのリストを取得します。
	 *
	 * @return
	 */
	public List<MPostTag> findByIdList() {
		return select().where(new SimpleWhere().eq(MPostTagNames.deleteFlg(), Flg.OFF.getCode())).getResultList();
	}

	/**
	 * 既に登録済みのタグが含まれているかどうかをチェックします。
	 *
	 * @param postTagNameList
	 *            識別子
	 * @return エンティティのリスト
	 */
	public boolean existsByTagNameList(List<String> postTagNameList) {
		if (CollectionUtils.isEmpty(postTagNameList)) {
			return false;
		}
		return 0 < select().where(new SimpleWhere().in(MPostTagNames.name(), postTagNameList)
				.eq(MPostTagNames.deleteFlg(), Flg.OFF.getCode())).getCount();
	}

}