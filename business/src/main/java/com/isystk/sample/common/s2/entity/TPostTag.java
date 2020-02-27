package com.isystk.sample.common.s2.entity;

import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * TPostTagエンティティクラス
 * 
 */
@Entity
@Generated(value = {"S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.EntityModelFactoryImpl"})
public class TPostTag implements Serializable {

    private static final long serialVersionUID = 1L;

    /** postIdプロパティ */
    @Id
    @Column(precision = 10, nullable = false, unique = false)
    public Integer postId;

    /** postTagIdプロパティ */
    @Id
    @Column(precision = 10, nullable = false, unique = false)
    public Integer postTagId;

    /** TPost関連プロパティ */
    @ManyToOne
    @JoinColumn(name = "POST_ID", referencedColumnName = "POST_ID")
    public TPost TPost;

    /** MPostTag関連プロパティ */
    @ManyToOne
    @JoinColumn(name = "POST_TAG_ID", referencedColumnName = "POST_TAG_ID")
    public MPostTag MPostTag;

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
     * TPostを返します。
     * 
     * @param TPost
     */
    public TPost getTPost() {
        return TPost;
    }

    /**
     * TPostを設定します。
     * 
     * @param TPost
     */
    public void setTPost(TPost TPost) {
        this.TPost = TPost;
    }

    /**
     * MPostTagを返します。
     * 
     * @param MPostTag
     */
    public MPostTag getMPostTag() {
        return MPostTag;
    }

    /**
     * MPostTagを設定します。
     * 
     * @param MPostTag
     */
    public void setMPostTag(MPostTag MPostTag) {
        this.MPostTag = MPostTag;
    }
}