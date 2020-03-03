package com.isystk.sample.common.s2.entity;

import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * TPostImageエンティティクラス
 * 
 */
@Entity
@Generated(value = { "S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.EntityModelFactoryImpl" })
public class TPostImage implements Serializable {

	private static final long serialVersionUID = 1L;

	/** postIdプロパティ */
	@Id
	@Column(precision = 10, nullable = false, unique = false)
	public Integer postId;

	/** imageIdプロパティ */
	@Id
	@Column(precision = 10, nullable = false, unique = false)
	public Integer imageId;

	/** TImage関連プロパティ */
	@ManyToOne
	@JoinColumn(name = "IMAGE_ID", referencedColumnName = "IMAGE_ID")
	public TImage TImage;

	/** TPost関連プロパティ */
	@ManyToOne
	@JoinColumn(name = "POST_ID", referencedColumnName = "POST_ID")
	public TPost TPost;

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
	 * TImageを返します。
	 * 
	 * @param TImage
	 */
	public TImage getTImage() {
		return TImage;
	}

	/**
	 * TImageを設定します。
	 * 
	 * @param TImage
	 */
	public void setTImage(TImage TImage) {
		this.TImage = TImage;
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
}