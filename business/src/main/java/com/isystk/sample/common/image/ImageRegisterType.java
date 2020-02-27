/**
 * Copyright(c) team-lab</br>
 */
package com.isystk.sample.common.image;

import com.isystk.sample.common.constants.ImageType;

/**
 * 画像登録時の種類.<br>
 *
 * @author iseyoshitaka
 */
public enum ImageRegisterType {

    /**
     * 投稿画像
     */
    PEOPLE_USER(ImageType.ORIGINAL_700, ImageType.SQUARE_400, ImageType.SD_400, ImageType.SD_S, ImageType.SD_L,
	    ImageType.SD_LL);

    /** 保存する画像フォーマット */
    private final ImageType[] imageTypes;

    /**
     * コンストラクタ。
     *
     * @param imagePatterns 保存する画像フォーマット
     */
    ImageRegisterType(ImageType... imageTypes) {
	this.imageTypes = imageTypes;
    }

    /**
     * 保存する画像フォーマットを取得する。
     *
     * @return 保存する画像フォーマット
     */
    public ImageType[] getImageTypes() {
	return imageTypes;
    }
}
