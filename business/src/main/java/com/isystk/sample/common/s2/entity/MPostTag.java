package com.isystk.sample.common.s2.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MPostTagエンティティクラス
 * 
 */
@Entity
@Generated(value = {"S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.EntityModelFactoryImpl"})
public class MPostTag implements Serializable {

    private static final long serialVersionUID = 1L;

    /** postTagIdプロパティ */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(precision = 10, nullable = false, unique = true)
    public Integer postTagId;

    /** nameプロパティ */
    @Column(length = 20, nullable = false, unique = false)
    public String name;

    /** registTimeプロパティ */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, unique = false)
    public Date registTime;

    /** updateTimeプロパティ */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, unique = false)
    public Date updateTime;

    /** deleteFlgプロパティ */
    @Column(nullable = false, unique = false)
    public Boolean deleteFlg;

    /** TPostTagList関連プロパティ */
    @OneToMany(mappedBy = "MPostTag")
    public List<TPostTag> TPostTagList;

    /**
     * postTagIdを返します。
     * 
     * @param postTagId
     */
    public Integer getPostTagId() {
        return postTagId;
    }

    /**
     * postTagIdを設定します。
     * 
     * @param postTagId
     */
    public void setPostTagId(Integer postTagId) {
        this.postTagId = postTagId;
    }

    /**
     * nameを返します。
     * 
     * @param name
     */
    public String getName() {
        return name;
    }

    /**
     * nameを設定します。
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * registTimeを返します。
     * 
     * @param registTime
     */
    public Date getRegistTime() {
        return registTime;
    }

    /**
     * registTimeを設定します。
     * 
     * @param registTime
     */
    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    /**
     * updateTimeを返します。
     * 
     * @param updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * updateTimeを設定します。
     * 
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * deleteFlgを返します。
     * 
     * @param deleteFlg
     */
    public Boolean isDeleteFlg() {
        return deleteFlg;
    }

    /**
     * deleteFlgを設定します。
     * 
     * @param deleteFlg
     */
    public void setDeleteFlg(Boolean deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    /**
     * TPostTagListを返します。
     * 
     * @param TPostTagList
     */
    public List<TPostTag> getTPostTagList() {
        return TPostTagList;
    }

    /**
     * TPostTagListを設定します。
     * 
     * @param TPostTagList
     */
    public void setTPostTagList(List<TPostTag> TPostTagList) {
        this.TPostTagList = TPostTagList;
    }
}