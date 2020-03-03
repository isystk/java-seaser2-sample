/**
 * Copyright(c) isystk.com</br>
 */
package com.isystk.sample.common.image;

import java.io.Serializable;

import com.isystk.sample.common.constants.ImageType;

/**
 * 画像ファイルの情報.<br>
 * 
 * @author iseyoshitaka
 */
public class ImageInfo implements Serializable {

	private static final long serialVersionUID = 5240631105448959885L;

	/** 画像ID */
	private Integer imageId;

	/** パス */
	private String path;

	/** 接尾文字 */
	private String suffix;

	/** 横幅 */
	private Integer width;

	/** 高さ */
	private Integer height;

	/**
	 * コンストラクタ
	 */
	public ImageInfo() {
		super();
	}

	/**
	 * @param imageId
	 *            Integer
	 * @param path
	 *            String
	 * @param suffix
	 *            String
	 */
	public ImageInfo(Integer imageId, String path, String suffix) {
		super();
		this.imageId = imageId;
		this.path = path;
		this.suffix = suffix;
	}

	/**
	 * 画像IDを取得する。
	 * 
	 * @return 画像ID
	 */
	public Integer getImageId() {
		return imageId;
	}

	/**
	 * パスを取得する。
	 * 
	 * @return パス
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 接尾文字を取得する。
	 * 
	 * @return 接尾文字
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * 画像のURLを取得する。
	 *
	 * @param imageType
	 *            imageType
	 * @return URL
	 */
	public String getUrl(ImageType imageType) {
		if (imageId == null) {
			return imageType.getNoImagePath();
		}
		return imageType.getUri(new ImageInfo(imageId, path, imageType.getSuffix()));
	}

	/**
	 * 画像IDを設定する。
	 * 
	 * @param imageId
	 *            画像ID
	 */
	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	/**
	 * パスを設定する。
	 * 
	 * @param path
	 *            パス
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 接尾文字を設定する。
	 * 
	 * @param suffix
	 *            接尾文字
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

}