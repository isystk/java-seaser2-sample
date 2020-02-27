package com.isystk.sample.common.s2.service;

import java.util.List;
import javax.annotation.Generated;

import static com.isystk.sample.common.s2.entity.names.TImageNames.*;
import static org.seasar.extension.jdbc.operation.Operations.*;

import org.seasar.extension.jdbc.where.SimpleWhere;

import org.apache.commons.collections.CollectionUtils;
import com.google.common.collect.Lists;
import com.isystk.sample.common.s2.entity.TImage;

/**
 * {@link TImage}のサービスクラスです。
 * 
 */
@Generated(value = {"S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.ServiceModelFactoryImpl"})
public class TImageService extends AbstractService<TImage> {

    /**
     * 識別子でエンティティを検索します。
     * 
     * @param imageId 識別子
     * @return エンティティ
     */
    public TImage findById(Integer imageId) {
        return select().id(imageId).getSingleResult();
    }
    /**
     * 識別子で排他制御を行います。
     * 
     * @param imageId 識別子
     * @return エンティティ
     */
    public TImage forUpdate(Integer imageId) {
        return select().forUpdate().id(imageId).getSingleResult();
    }
    
    /**
     * 識別子で排他制御を行います。
     * 
     * @param imageIdList 識別子
     * @return エンティティのリスト
     */
    public List<TImage> forUpdate(List<Integer> imageIdList) {
        if (CollectionUtils.isEmpty(imageIdList)) {
            return Lists.newArrayList();
        }
        return select().forUpdate().where(new SimpleWhere().in(imageId(), imageIdList)).getResultList();
    }

    /**
     * 識別子の昇順ですべてのエンティティを検索します。
     * 
     * @return エンティティのリスト
     */
    public List<TImage> findAllOrderById() {
        return select().orderBy(asc(imageId())).getResultList();
    }
    
    
}