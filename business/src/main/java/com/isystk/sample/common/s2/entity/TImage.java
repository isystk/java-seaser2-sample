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
 * TImageエンティティクラス
 * 
 */
@Entity
@Generated(value = {"S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.EntityModelFactoryImpl"})
public class TImage implements Serializable {

    private static final long serialVersionUID = 1L;

    /** imageIdプロパティ */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(precision = 10, nullable = false, unique = true)
    public Integer imageId;

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

    /** TPostImageList関連プロパティ */
    @OneToMany(mappedBy = "TImage")
    public List<TPostImage> TPostImageList;

    /**
     * imageIdを返します。
     * 
     * @param imageId
     */
    public Integer getImageId() {
        return imageId;
    }

    /**
     * imageIdを設定します。
     * 
     * @param imageId
     */
    public void setImageId(Integer imageId) {
        this.imageId = imageId;
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
     * TPostImageListを返します。
     * 
     * @param TPostImageList
     */
    public List<TPostImage> getTPostImageList() {
        return TPostImageList;
    }

    /**
     * TPostImageListを設定します。
     * 
     * @param TPostImageList
     */
    public void setTPostImageList(List<TPostImage> TPostImageList) {
        this.TPostImageList = TPostImageList;
    }
}