/**
 *
 */
package com.isystk.sample.common.image.procedure;

import java.io.File;

import com.isystk.sample.common.image.ImageInfo;

/**
 * 画像変換プロシージャのインターフェース
 * 
 * @author iseyoshitaka
 */
public interface ImageProcedure {

	/**
	 * 画像を変換します
	 *
	 * @param img
	 *            ImageInfo
	 * @param workFile
	 *            File
	 * @throws Exception
	 *             例外
	 */
	void convert(ImageInfo img, File workFile) throws Exception;

	/**
	 * 画像をトリミングします
	 *
	 * @param file
	 *            編集ファイル
	 * @param dst
	 *            保存先ファイル
	 * @param x
	 *            X座標
	 * @param x
	 *            Y座標
	 * @param width
	 *            切り取る横幅
	 * @param height
	 *            切り取る縦幅
	 * @throws Exception
	 *             例外
	 */
	@Deprecated
	void crop(File file, File dst, Integer x, Integer y, Integer width, Integer height) throws Exception;

	/**
	 * 画像をトリミングします
	 *
	 * @param file
	 *            編集ファイル
	 * @param dst
	 *            保存先ファイル
	 * @param x1
	 *            X座標
	 * @param x1
	 *            Y座標
	 * @param x2
	 *            X座標
	 * @param x2
	 *            Y座標
	 * @param width
	 *            切り取る横幅
	 * @param height
	 *            切り取る縦幅
	 * @throws Exception
	 *             例外
	 */
	void cropZoom(File file, File dst, Integer x1, Integer y1, Integer x2, Integer y2, Integer width, Integer height)
			throws Exception;

	/**
	 * 変換後の画像の名称を取得します
	 *
	 * @param img
	 *            ImageInfo
	 * @return String
	 */
	String getFileName(ImageInfo img);

	/**
	 * 変換後の画像のパスを取得します
	 * 
	 * @param img
	 *            ImageInfo
	 * @return String
	 */
	String getFilePath(ImageInfo img);

	/**
	 * @return height
	 */
	int getHeight();

	/**
	 * @return suffix
	 */
	String getSuffix();

	/**
	 * 変換後の画像のURIを取得します
	 *
	 * @param img
	 *            ImageInfo
	 * @return String
	 */
	String getUri(ImageInfo img);

	/**
	 * @return width
	 */
	int getWidth();

	/**
	 * サポートするかどうか判断します.
	 * 
	 * @param img
	 *            ImageInfo
	 * @return サポートするときtrue
	 * @throws Exception
	 *             -
	 */
	boolean supported(ImageInfo img) throws Exception;

}
