/**
 *
 */
package com.isystk.sample.common.image.procedure;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isystk.sample.common.config.AppConfigNames;
import com.isystk.sample.common.config.Config;
import com.isystk.sample.common.exception.SystemException;
import com.isystk.sample.common.image.ImageInfo;
import com.isystk.sample.common.image.ImageManager;
import com.isystk.sample.common.util.ShellExecutor;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.filters.Canvas;
import net.coobird.thumbnailator.geometry.Coordinate;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * @author iseyoshitaka
 */
public class BasicImageProcedure implements ImageProcedure {

	private static final Logger logger = LoggerFactory.getLogger(BasicImageProcedure.class);

	/** 使用可能なimage typeのSet */
	private static final Set<String> USABLE_IMAGE_TYPES;
	static {
		Set<String> s = new HashSet<String>(2);
		s.add("jpg");
		s.add("jpeg");
		s.add("png");
		USABLE_IMAGE_TYPES = Collections.unmodifiableSet(s);
	}

	/** リサイズ後の幅 */
	private int width;

	/** リサイズ後の高さ */
	private int height;

	/** サフィックス */
	private String suffix;

	/**
	 * @param width
	 *            リサイズ後の幅
	 * @param height
	 *            リサイズ後の高さ
	 * @param suffix
	 *            サフィックス
	 */
	public BasicImageProcedure(final int width, final int height, final String suffix) {
		super();
		this.width = width;
		this.height = height;
		this.suffix = suffix;
	}

	/**
	 * chmod
	 *
	 * @param parent
	 *            dir
	 */
	protected void chmod(File parent) {

		if (!doChangemod()) {
			return;
		}
		logger.info("## chmod " + parent.getAbsolutePath() + " start... ##");

		ShellExecutor se = new ShellExecutor();
		try {
			se.execute(new String[] { "chmod", "777", parent.getAbsolutePath() });
		} catch (IOException e) {
			logger.error("chmod faled !!", e);
		}
		logger.info("## chmod " + parent.getAbsolutePath() + " end ##");
	}

	/**
	 * chmod ?
	 *
	 * @return true chmod
	 */
	private boolean doChangemod() {
		return "true".equals(Config.getString(AppConfigNames.IMAGE_UPLOAD_CHMOD.key));
	}

	public void crop(File file, File dst, Integer x, Integer y, Integer width, Integer height) throws IOException {
		logger.info("x座標:" + x + ", y座標:" + y + " でトリミングします");
		logger.info("トリミング後の横幅:" + width + ", 縦幅:" + height);
		Coordinate coord = new Coordinate(-x, -y);

		Thumbnails.of(file).crop(coord).size(width, height).keepAspectRatio(true).toFile(dst);
	}

	public void cropZoom(File file, File dst, Integer x1, Integer y1, Integer x2, Integer y2, Integer width,
			Integer height) throws IOException {
		logger.info("x1座標:" + x1 + ", y1座標:" + y1 + " x2座標:" + x2 + ", y2座標:" + y2 + " でトリミングします");
		logger.info("トリミング後の横幅:" + width + ", 縦幅:" + height);

		Thumbnails.of(file).sourceRegion(x1, y1, x2, y2).size(x2, y2).toFile(dst);

		if ((9 < Math.abs(x2.intValue() - width.intValue())) || (9 < Math.abs(y2.intValue() - height.intValue()))) {
			// 切り取る範囲が出力幅よりも小さい場合は白背景にします。

			if (y2 <= (x2 * height / width)) {
				Thumbnails.of(dst).width(width).addFilter(new Canvas(width, height, Positions.CENTER, Color.white))
						.toFile(dst);
			} else {
				Thumbnails.of(dst).height(height).addFilter(new Canvas(width, height, Positions.CENTER, Color.white))
						.toFile(dst);
			}
		}
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.isystk.sample.common.image.procedure.ImageProcedure#convert(com.isystk.sample.common.image.ImageInfo,
	 *      java.io.File) {@inheritDoc}
	 */
	public void convert(ImageInfo img, File workFile) throws Exception {

		File dst = this.getLocalFile(img);

		BufferedImage bimage = ImageIO.read(workFile);

		setOriginalSize(img, bimage);

		// 縦長と横長の場合とでリサイズ
		if (bimage.getHeight() > bimage.getWidth()) {
			// 縦長画像
			logger.info("縦長画像のため中心をトリミングします");
			if (width == height) {
				// 1:1画像の場合はそのまま中央をトリミング
				Thumbnails.of(bimage).crop(Positions.CENTER).size(width, height).keepAspectRatio(true)
						.outputQuality(0.9f).toFile(dst);
			} else {
				// 1:1画像でない場合は中央をトリミング後、左右に白背景を設定
				Thumbnails.of(bimage).crop(Positions.CENTER).size(width, width).keepAspectRatio(true)
						.outputQuality(0.9f).toFile(dst);
				BufferedImage wkimage = ImageIO.read(dst);
				Thumbnails.of(wkimage).height(height)
						.addFilter(new Canvas(width, height, Positions.CENTER, Color.white)).keepAspectRatio(true)
						.outputQuality(0.95f).toFile(dst);
			}
		} else {
			// 横長画像
			logger.info("横長画像のため中心をトリミングします");
			Thumbnails.of(bimage).crop(Positions.CENTER).size(width, height).keepAspectRatio(true).outputQuality(0.9f)
					.toFile(dst);
		}

		logger.info("width:" + this.width + " height:" + this.height + " path:" + dst);
	}

	/**
	 * オリジナル画像のwidth,heightを設定する
	 *
	 * @param img
	 * @param bimage
	 */
	protected void setOriginalSize(ImageInfo img, BufferedImage bimage) {
		if (img.getWidth() == null) {
			img.setWidth(bimage.getWidth());
		}
		if (img.getHeight() == null) {
			img.setHeight(bimage.getHeight());
		}
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.isystk.sample.common.image.procedure.ImageProcedure#getFileName(com.isystk.sample.common.image.ImageInfo)
	 *      {@inheritDoc}
	 */
	public String getFileName(ImageInfo img) {
		return this.getLocalFile(img).getName();
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.isystk.sample.common.image.procedure.ImageProcedure#getFilePath(com.isystk.sample.common.image.ImageInfo)
	 *      {@inheritDoc}
	 */
	public String getFilePath(ImageInfo img) {
		return this.getLocalFile(img).getAbsolutePath();
	}

	/**
	 * @return height
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * 画像の基底ディレクトリを取得します
	 *
	 * @return File
	 */
	protected File getImageBaseDir(ImageInfo img) {
		String imagePath = Config.getString(AppConfigNames.IMAGE_DIR);
		File imageBaseDir = new File(new File(Config.getString(AppConfigNames.SYSTEM_HTDOCSDIR)), imagePath);
		if (!imageBaseDir.exists()) {
			if (!imageBaseDir.mkdirs()) {
				throw new SystemException("failed to create image base dir. :" + imageBaseDir);
			}
		}
		return imageBaseDir;

	}

	/**
	 * ローカルのファイルを取得します
	 *
	 * @param img
	 *            File
	 * @return File
	 */
	protected File getLocalFile(ImageInfo img) {
		File localFile = new File(this.getImageBaseDir(img),
				this.getLocalRelativePath(img.getPath(), this.suffix, img.getSuffix()));

		File parent = localFile.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
			chmod(parent);
		}

		return localFile;
	}

	/**
	 * ローカルの相対パスを取得します
	 *
	 * @param path
	 *            String
	 * @param suffix
	 *            String
	 * @param extension
	 *            String
	 * @return String
	 */
	protected String getLocalRelativePath(String path, String suffix, String extension) {
		return String.format("%1$s%2$s.%3$s", path, suffix, extension);
	}

	/**
	 * @return suffix
	 */
	public String getSuffix() {
		return this.suffix;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see com.isystk.sample.common.image.procedure.ImageProcedure#getUri(com.isystk.sample.common.image.ImageInfo)
	 *      {@inheritDoc}
	 */
	public String getUri(ImageInfo img) {
		return ImageManager.getImageUrl(img.getImageId(), img.getSuffix());
	}

	/**
	 * @return width
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.isystk.sample.common.image.procedure.ImageProcedure#supported(com.isystk.sample.common.image.ImageInfo)
	 * @author iseyoshitaka
	 */
	public boolean supported(ImageInfo img) throws Exception {

		return USABLE_IMAGE_TYPES.contains(img.getSuffix().toLowerCase());
	}

}
