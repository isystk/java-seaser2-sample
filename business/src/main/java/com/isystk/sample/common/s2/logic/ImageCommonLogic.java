package com.isystk.sample.common.s2.logic;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isystk.sample.common.constants.Flg;
import com.isystk.sample.common.constants.ImageType;
import com.isystk.sample.common.constants.entity.MaxLength;
import com.isystk.sample.common.image.ImageInfo;
import com.isystk.sample.common.image.ImageManager;
import com.isystk.sample.common.image.ImageRegisterType;
import com.isystk.sample.common.s2.entity.TImage;
import com.isystk.sample.common.s2.service.TImageService;
import com.isystk.sample.common.util.DateUtils;

/**
 * 画像ロジック （共通）
 *
 * @author iseyoshitaka
 */
public class ImageCommonLogic {

	/** ロガー */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/** TImageService */
	@Resource
	protected TImageService tImageService;

	/**
	 * ID生成
	 *
	 * @return 生成されたID
	 */
	public Integer generateID(int maxLength) {
		Integer id = 0;
		boolean loopFlg = true;

		do {
			id = Integer.valueOf(RandomStringUtils.randomNumeric(maxLength));

			// 生成したIDが存在しない場合、そのIDを返却する
			boolean isExist = this.isExistImageId(id);

			if (!isExist) {
				loopFlg = false;
			}
		} while (loopFlg);

		return id;
	}

	/**
	 * 画像識別子が存在するかどうかを判定する
	 *
	 * @param imageId
	 *            画像識別子
	 * @return 存在する:true 存在しない:false
	 */
	public boolean isExistImageId(Integer imageId) {
		return tImageService.findById(imageId) != null;
	}

	/**
	 *
	 * オリジナル画像を指定したアスペクト比で中央部分を切り抜きします。
	 *
	 * @param imageId
	 * @param imageType
	 * @param aspectX
	 * @param aspectY
	 */
	public void cropDefaultImage(Integer imageId, ImageType imageType, int aspectX, int aspectY) {

		// オリジナル画像
		BufferedImage image = ImageManager.getImageInfo(imageId);

		int x1 = 0;
		int y1 = 0;
		int x2 = 0;
		int y2 = 0;
		int outputWidth = 0;
		int outputHeight = 0;

		if ((image.getWidth() < image.getHeight()) && (aspectY < aspectX)) {
			// 縦長の画像を横長で出力する場合

			x1 = 0;
			y1 = 0;
			x2 = image.getWidth();
			y2 = image.getHeight();

			outputWidth = image.getWidth();
			outputHeight = (outputWidth / aspectX * aspectY);

		} else {
			double vWidth = image.getHeight() / aspectY * aspectX;
			if (vWidth < image.getWidth()) {
				// 横長画像の中央部分を切り取る。

				double width = vWidth;
				double height = image.getHeight();
				x1 = BigDecimal.valueOf((image.getWidth() - width) / 2).intValue();
				y1 = 0;
				x2 = BigDecimal.valueOf(width).intValue();
				y2 = BigDecimal.valueOf(height).intValue();

				outputHeight = image.getHeight();
				outputWidth = (outputHeight * x2 / y2);
			} else {
				// 縦長画像の中央部分を切り取る。

				double width = image.getWidth();
				double height = width / aspectX * aspectY;
				x1 = 0;
				y1 = BigDecimal.valueOf((image.getHeight() - height) / 2).intValue();
				x2 = BigDecimal.valueOf(width).intValue();
				y2 = BigDecimal.valueOf(height).intValue();

				outputWidth = image.getWidth();
				outputHeight = (outputWidth * aspectY / aspectX);

			}

		}

		// 画像トリミング処理の実装
		ImageManager.cropZoomImageFile(imageId, x1, y1, x2, y2, outputWidth, outputHeight, imageType);

	}

	/**
	 * 画像をコピーして別画像IDを生成します。
	 *
	 * @param sourceImageId
	 * @param type
	 * @param mediaFileDiv
	 * @return
	 */
	public ImageInfo copyImageFile(Integer sourceImageId, ImageRegisterType type) {
		Integer newImageId = generateID(MaxLength.T_IMAGE_IMAGEID);
		return copyImageFile(sourceImageId, newImageId, type);
	}

	/**
	 * 画像をコピーして別画像IDを生成します。
	 *
	 * @param sourceImageId
	 * @param destImageId
	 * @param type
	 * @return
	 */
	public ImageInfo copyImageFile(Integer sourceImageId, Integer destImageId, ImageRegisterType type) {

		if (destImageId == null) {
			destImageId = generateID(MaxLength.T_IMAGE_IMAGEID);
		}

		ImageInfo newImageInfo = ImageManager.copyImageFile(sourceImageId, destImageId, type);

		return newImageInfo;
	}

	public ImageInfo createImage(InputStream is, String fileName) {
		ImageInfo imageInfo = null;

		// 画像の拡張子
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

		// 画像idを生成する
		Integer imgId = generateID(Integer.valueOf(9));

		try {
			// 画像の生成
			imageInfo = ImageManager.writeImageFile(imgId, is, extension, ImageRegisterType.PEOPLE_USER);

			// 画像テーブルに登録
			TImage tImage = new TImage();
			tImage.imageId = imgId;
			tImage.deleteFlg = Flg.OFF.getValue();
			tImage.registTime = DateUtils.getNow();
			tImage.updateTime = DateUtils.getNow();
			tImageService.insert(tImage);

		} catch (Exception e) {
			logger.error("画像の生成に失敗しました。", e);
		}

		return imageInfo;
	}

}
