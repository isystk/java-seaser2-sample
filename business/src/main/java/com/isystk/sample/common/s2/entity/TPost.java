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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * TPostエンティティクラス
 * 
 */
@Entity
@Generated(value = {"S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.EntityModelFactoryImpl"})
public class TPost implements Serializable {

    private static final long serialVersionUID = 1L;

    /** postIdプロパティ */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(precision = 10, nullable = false, unique = true)
    public Integer postId;

    /** userIdプロパティ */
    @Column(precision = 10, nullable = false, unique = false)
    public Integer userId;

    /** titleプロパティ */
    @Column(length = 100, nullable = false, unique = false)
    public String title;

    /** textプロパティ */
    @Column(length = 500, nullable = false, unique = false)
    public String text;

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

    /** versionプロパティ */
    @Version
    @Column(precision = 19, nullable = false, unique = false)
    public Long version;

    /** TUser関連プロパティ */
    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    public TUser TUser;

    /** TPostImageList関連プロパティ */
    @OneToMany(mappedBy = "TPost")
    public List<TPostImage> TPostImageList;

    /** TPostTagList関連プロパティ */
    @OneToMany(mappedBy = "TPost")
    public List<TPostTag> TPostTagList;

    /**
     * postIdを返します。
     * 
     * @param postId
     */
    public Integer getPostId() {
        return postId;
    }

    /**
     * postIdを設定します。
     * 
     * @param postId
     */
    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    /**
     * userIdを返します。
     * 
     * @param userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * userIdを設定します。
     * 
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * titleを返します。
     * 
     * @param title
     */
    public String getTitle() {
        return title;
    }

    /**
     * titleを設定します。
     * 
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * textを返します。
     * 
     * @param text
     */
    public String getText() {
        return text;
    }

    /**
     * textを設定します。
     * 
     * @param text
     */
    public void setText(String text) {
        this.text = text;
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
     * versionを返します。
     * 
     * @param version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * versionを設定します。
     * 
     * @param version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * TUserを返します。
     * 
     * @param TUser
     */
    public TUser getTUser() {
        return TUser;
    }

    /**
     * TUserを設定します。
     * 
     * @param TUser
     */
    public void setTUser(TUser TUser) {
        this.TUser = TUser;
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