/**
 *
 */
package com.isystk.sample.common.image.procedure;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isystk.sample.common.file.FileManager;
import com.isystk.sample.common.image.ImageInfo;
import com.isystk.sample.common.image.ImageSuffix;

import net.coobird.thumbnailator.Thumbnails;

/**
 * @author iseyoshitaka
 *
 */
public class FixedWidthImageProcedure extends BasicImageProcedure {

    private static final Logger logger = LoggerFactory.getLogger(FixedWidthImageProcedure.class);

    /**
     * @param width リサイズ後の幅
     * @param height リサイズ後の高さ
     * @param suffix サフィックス
     */
    public FixedWidthImageProcedure(int width, int height, String suffix) {
	super(width, height, suffix);
    }

    /**
     * (non-Javadoc)
     *
     * java.lang.String, java.lang.String) {@inheritDoc}
     */
    @Override
    public void convert(ImageInfo img, File workFile) throws Exception {

	File dst = this.getLocalFile(img);
	BufferedImage bimage = ImageIO.read(workFile);

	super.setOriginalSize(img, bimage);

	// オリジナル画像はそのまま保存
	if (this.getSuffix() == ImageSuffix.ORIGINAL.getSuffix()) {
	    FileManager.copyFile(workFile, dst);
	} else {
	    if (getHeight() == 0) {
		logger.info("横幅固定でリサイズします");
		Thumbnails.of(bimage).width(getWidth()).outputQuality(0.9f).toFile(dst);
		logger.info("width:" + getWidth() + " height:" + getHeight() + " path:" + dst);
	    }
	}


    }

}
