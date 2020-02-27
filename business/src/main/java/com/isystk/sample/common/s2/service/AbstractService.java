package com.isystk.sample.common.s2.service;

import java.util.List;

import javax.annotation.Generated;

import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.extension.jdbc.service.S2AbstractService;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.util.StringUtil;

/**
 * サービスの抽象クラスです。
 * 
 * @param <ENTITY> エンティティの型
 */
@Generated(value = { "S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.AbstServiceModelFactoryImpl" }, date = "2012/04/17 23:04:59")
public abstract class AbstractService<ENTITY> extends S2AbstractService<ENTITY> {

    /**
     * エンティティを挿入します。
     * 
     * @param entity エンティティ
     * @return 更新した行数
     */
    public int insertExcludesNull(ENTITY entity) {
	return jdbcManager.insert(entity).excludesNull().execute();
    }

    /**
     * エンティティを更新します。
     * 
     * @param entity エンティティ
     * @return 更新した行数
     */
    public int updateExcludesNull(ENTITY entity) {
	return jdbcManager.update(entity).excludesNull().execute();
    }

    /**
     * 自動バッチ挿入を作成します
     * 
     * @param entityList エンティティのリスト
     * @return 更新件数
     */
    public int[] insertBatch(List<ENTITY> entityList) {
	if (entityList == null || entityList.isEmpty()) {
	    return new int[0];
	}
	return jdbcManager.insertBatch(entityList).execute();
    }

    /**
     * 自動バッチ削除を作成します
     * 
     * @param entityList エンティティのリスト
     * @return 削除件数
     */
    public int[] deleteBatch(List<ENTITY> entityList) {
	if (entityList == null || entityList.isEmpty()) {
	    return new int[0];
	}
	return jdbcManager.deleteBatch(entityList).execute();
    }

    @Override
    protected void setEntityClass(Class<ENTITY> entityClass) {
	this.entityClass = entityClass;
	sqlFilePathPrefix = StringUtil.replace(entityClass.getName(), ".", "/") + "/";
    }
}