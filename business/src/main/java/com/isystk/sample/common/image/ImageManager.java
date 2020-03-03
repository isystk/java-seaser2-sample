/**
 * Copyright(c) isystk.com</br>
 */
package com.isystk.sample.common.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;

import org.apache.commons.io.IOUtils;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.common.byteSources.ByteSource;
import org.apache.sanselan.common.byteSources.ByteSourceFile;
import org.apache.sanselan.formats.jpeg.JpegImageParser;
import org.apache.sanselan.formats.jpeg.segments.UnknownSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectory;
import com.isystk.sample.common.config.AppConfigNames;
import com.isystk.sample.common.config.Config;
import com.isystk.sample.common.constants.ImageType;
import com.isystk.sample.common.exception.SystemException;
import com.isystk.sample.common.file.FileManager;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 画像管理のクラス.<br>
 *
 * @author iseyoshitaka
 */
public class ImageManager {

	private static final Logger logger = LoggerFactory.getLogger(ImageManager.class);

	/** ユーティリティクラスのためコンストラクタを封殺。 */
	private ImageManager() {
	}

	/** バッファサイズ */
	private static final int BUFFER_SIZE = 1024 * 64;

	/**
	 * 閉じる。
	 *
	 * @param closeable
	 *            閉じるもの
	 */
	private static void close(Closeable closeable) {
		if (closeable == null) {
			return;
		}
		try {
			closeable.close();
		} catch (IOException e) {
			throw new SystemException("閉じれません");
		}
	}

	/**
	 * ワークを消す
	 *
	 * @param workFile
	 *            ワーク
	 */
	private static void deleteFile(File workFile) {
		if (workFile.delete()) {
			return;
		}
	}

	/**
	 * 画像IDからパスを表すハッシュを取得する。
	 *
	 * @param imgId
	 *            画像ID
	 * @return ハッシュ
	 */
	public static String getHash(Integer imgId) {
		int h;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] d = md.digest(new DecimalFormat("00000000").format(imgId).getBytes());
			h = d[0];
			h += d[1] << 8;
		} catch (NoSuchAlgorithmException e) {
			throw new SystemException(e);
		}
		Formatter f = new Formatter();
		f.format("/%02x/%02x/", (h & 0xff), ((h >> 8) & 0xff));
		return f.toString();
	}

	/**
	 * 画像IDからパスを生成する。
	 *
	 * @param imgId
	 *            画像ID
	 * @return パス
	 */
	private static String getHashDir(Integer imgId) {
		return getHash(imgId);
	}

	/**
	 * 画像ワークディレクトリを取得する。
	 *
	 * @return 画像ワークディレクトリ
	 */
	public static File getImageWorkDir() {
		File workDir = new File(Config.getString(AppConfigNames.SYSTEM_WORKDIR));
		File imageWorkDir = new File(workDir, Config.getString(AppConfigNames.IMAGE_DIR));
		if (imageWorkDir.exists()) {
			return imageWorkDir;
		}
		if (imageWorkDir.mkdirs()) {
			return imageWorkDir;
		}

		throw new SystemException("画像ワークディレクトリ " + imageWorkDir + "が作成できません。");
	}

	/**
	 * リサイズ画像のディレクトリパスを取得する。
	 *
	 * @param imageId
	 * @param imageType
	 * @return path (例:/thumb/93/78/)
	 */
	public static String getImageDirPath(Integer imageId) {
		return "/" + Config.getString(AppConfigNames.IMAGE_DIR) + ImageManager.getHash(imageId);
	}

	/**
	 * リサイズ画像のファイルパスを取得する。
	 *
	 * @param imageId
	 * @param imageType
	 * @return path (例:/thumb/93/78/727863499_sd.jpg)
	 */
	public static String getImageFilePath(Integer imageId, String suffix) {
		return getImageDirPath(imageId) + imageId + suffix + ".jpg";
	}

	/**
	 * リサイズ画像URLの取得
	 *
	 * @param imageId
	 * @param imageType
	 * @param extension(※「.」付きで指定すること)
	 * @return 画像のURL
	 */
	public static String getImageUrl(Integer imageId, String suffix) {
		String protocol = "//";
		String domain = Config.getString(AppConfigNames.IMG_DOMAIN);
		String filepath = getImageFilePath(imageId, suffix);
		String result = protocol + domain + filepath;
		return result;
	}

	/**
	 * リサイズ画像URLの取得
	 *
	 * @param imageId
	 * @param suffix
	 * @param protcol
	 * @return 画像のURL
	 */
	public static String getImageOrNoImageUrl(Integer imageId, String suffix, String noimageName) {
		if (imageId == null) {
			return "//" + Config.getString(AppConfigNames.USERPC_DOMAIN) + "/img/" + noimageName + ".jpg";
		} else {
			return getImageUrl(imageId, suffix);
		}
	}

	/**
	 * リサイズ画像URLの取得
	 *
	 * @param imageId
	 * @param suffix
	 * @return 画像のURL
	 */
	public static String getImageOrNoImageUrl(Integer imageId, String suffix) {
		return getImageOrNoImageUrl(imageId, suffix, "img_noimage" + suffix);
	}

	/**
	 * x座標から画像をトリミングする
	 *
	 * @param imageId
	 *            画像ID
	 * @param x1
	 *            X1座標
	 * @param y1
	 *            Y1座標
	 * @param x2
	 *            X2座標
	 * @param y2
	 *            Y2座標
	 * @param width
	 *            切り取る横幅
	 * @param height
	 *            切り取る縦幅
	 * @param type
	 *            画像タイプ
	 * @return
	 * @throws Exception
	 */
	public static ImageInfo cropZoomImageFile(Integer imageId, Integer x1, Integer y1, Integer x2, Integer y2,
			Integer width, Integer height, ImageType type) {
		ImageInfo result = new ImageInfo();

		File file = getLocalImage(imageId, "");
		File dst = getLocalImage(imageId, type.getSuffix());

		if (!file.exists()) {
			return result;
		}

		try {
			type.cropZoom(file, dst, x1, y1, x2, y2, width, height);
		} catch (Exception e) {
			throw new SystemException("トリミングできませんでした", e);
		}

		return result;
	}

	/**
	 * 画像をコピーする
	 *
	 * @param baseImageId
	 *            コピー元画像ID
	 * @param newImageId
	 *            新画像ID
	 * @return 新画像IDと新ファイルパスを設定したImageInfo
	 */
	public static ImageInfo copyImageFile(Integer baseImageId, Integer newImageId, ImageRegisterType type) {

		// 画像の共通パス
		String imageDir = Config.getString(AppConfigNames.SYSTEM_HTDOCSDIR)
				+ Config.getString(AppConfigNames.IMAGE_DIR);

		// コピー先のディレクトリパス作成
		String newImageDir = new StringBuilder().append(getHashDir(newImageId)).toString();

		// クライアントでの画像登録時の種類
		// ファイルを１つずつコピー ※ ひとつのディレクトリに複数のweddingIDの画像が存在することがあるのでちゃんとimageIdを指定してコピーする
		for (ImageType it : type.getImageTypes()) {

			// コピー元のファイルオブジェクト取得
			File baseFile = getLocalImage(baseImageId, it.getSuffix());

			if (!baseFile.exists()) {
				continue;
			}

			// 新しいファイル名を作成する
			String newName = baseFile.getName().replaceAll(baseImageId.toString(), newImageId.toString());

			FileInputStream fis = null;
			FileOutputStream fos = null;
			try {

				// コピー元の画像ファイルのデータを読み込む
				fis = new FileInputStream(baseFile);

				// 読み込んだ画像ファイルデータを新しいファイルに書き込む
				File dir = new File(imageDir + newImageDir);
				dir.mkdirs();
				File newFile = new File(dir, newName);

				fos = new FileOutputStream(newFile);
				byte[] buf = new byte[BUFFER_SIZE];
				int read;
				while ((read = fis.read(buf)) > 0) {
					fos.write(buf, 0, read);
				}
				fos.flush();

			} catch (Exception e) {
				throw new SystemException("ファイルのコピーができませんでした" + baseFile, e);
			} finally {
				close(fis);
				close(fos);
			}
		}

		ImageInfo ii = new ImageInfo();
		ii.setImageId(newImageId);
		ii.setPath(new StringBuilder().append(newImageDir).append(newImageId).toString());

		return ii;
	}

	/**
	 * 登録タイプに応じたファイルを作成する。
	 *
	 * @param imgId
	 *            画像ID
	 * @param untouchedFile
	 *            手付かずの画像
	 * @param workFile
	 *            ワークファイル（ある程度画像処理済み）
	 * @return 画像情報。画像でなければnull。
	 * @throws Exception
	 *             何らかの例外が発生した場合
	 */
	private static ImageInfo writeEachImageFile(Integer imgId, File untouchedFile, File workFile,
			ImageRegisterType type) throws Exception {

		ImageInfo ii = new ImageInfo();
		ii.setImageId(imgId);
		ii.setSuffix("jpg");
		ii.setPath(new StringBuilder().append(getHashDir(imgId)).append(imgId).toString());

		for (ImageType it : type.getImageTypes()) {
			if (it.supported(ii)) {
				continue;
			}
			return null;
		}

		// store image file each size.
		for (ImageType it : type.getImageTypes()) {
			it.convert(ii, workFile);
		}
		return ii;
	}

	public static ImageInfo writeImageFile(Integer imgId, InputStream is, String extension, ImageRegisterType type) {
		File dir = getWorkDir();

		// とりあえず手付かずの画像を保存
		File untouchedImage = new File(dir, String.valueOf(imgId) + "." + extension);
		FileManager.writeFile(is, untouchedImage);

		File workFile = new File(dir, String.valueOf(imgId) + "_work." + "jpg");
		try {

			// PNGやGIF画像はJPEGに変換
			if (extension.equalsIgnoreCase("PNG") || extension.equalsIgnoreCase("GIF")) {
				BufferedImage image = ImageIO.read(untouchedImage);
				BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D off = tmp.createGraphics();
				off.drawImage(image, 0, 0, Color.WHITE, null);
				ImageIO.write(tmp, "jpg", workFile);
			} else {
				FileManager.copyFile(untouchedImage, workFile);
			}

			return writeEachImageFile(imgId, untouchedImage, workFile, type);
		} catch (SystemException e) {
			throw e;
		} catch (Exception e) {
			throw new SystemException(e);
		} finally {
			deleteFile(workFile);
			deleteFile(untouchedImage);
		}
	}

	private static File getWorkDir() {
		String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
		File dir = new File(getImageWorkDir(), today);
		dir.mkdirs();
		return dir;
	}

	private static File createWorkImage(File untouchedImage, File workImageFile) {
		// システムで利用しやすいサイズにリサイズ。回転も行う
		try {
			BufferedImage bimage = readImage(untouchedImage);
			Metadata meta = ImageManager.getMetadata(untouchedImage);
			int resizeWidth = 700;
			// 横幅が700以下ならアップロード画像のサイズのまま保存
			if (bimage.getWidth() <= 700) {
				resizeWidth = bimage.getWidth();
			}
			if (meta != null) {
				Orientation orie = ImageManager.getOrientation(meta);
				int degree = 360 - orie.degree();
				if (!Orientation.HORIZONTAL.equals(orie)) {
					logger.info(degree + "度回転します");
					Thumbnails.of(bimage).width(resizeWidth).rotate(degree).outputQuality(1.0f).toFile(workImageFile);
				} else {
					Thumbnails.of(bimage).width(resizeWidth).outputQuality(1.0f).toFile(workImageFile);
				}
			} else {
				Thumbnails.of(bimage).width(resizeWidth).outputQuality(1.0f).toFile(workImageFile);
			}
		} catch (IOException e) {
			throw new SystemException("ワークに保存した画像のリサイズに失敗しました。" + untouchedImage, e);
		} catch (ImageProcessingException e) {
			throw new SystemException("回転情報の取得に失敗しました" + untouchedImage, e);
		} catch (ImageReadException e) {
			throw new SystemException("画像の読み込みに失敗しました" + untouchedImage, e);
		}

		return workImageFile;
	}

	/**
	 * 画像ファイルからEXIF情報を抽出します.
	 *
	 * @param imageFile
	 *            画像ファイル
	 * @return {@link Metadata}
	 * @throws ImageProcessingException
	 *             フォーマットが不正な場合
	 */
	public static Metadata getMetadata(File imageFile) throws ImageProcessingException {
		try {
			return ImageMetadataReader.readMetadata(imageFile);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * EXIF から回転情報(Orientation)を取得します.
	 *
	 * @param metadata
	 *            {@link Metadata}
	 * @return {@link Orientation}
	 */
	public static Orientation getOrientation(Metadata metadata) {
		if (metadata != null) {
			Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);
			if (exifDirectory != null) {
				try {
					int orientation = exifDirectory.getInt(ExifDirectory.TAG_ORIENTATION);
					if (orientation > 0 && orientation <= Orientation.values().length) {
						return Orientation.find(orientation);
					}
				} catch (MetadataException e) {
					logger.info("回転情報を取得できませんでした");
				}
			}
		}
		return Orientation.HORIZONTAL;
	}

	/**
	 * EXIF情報(Directory)を取得します.
	 *
	 * @param metadata
	 *            {@link Metadata}
	 * @return {@link Directory}
	 */
	public static Directory getExifDirectory(Metadata metadata) {
		return metadata.getDirectory(ExifDirectory.class);
	}

	/**
	 * 画像の縦長判定
	 *
	 * @param imageId
	 * @return boolean 縦長:true 横長:false
	 * @throws IOException
	 */
	public static boolean isLongerWidth(Integer imageId) {

		BufferedImage image = getImageInfo(imageId);

		// 画像取得できない場合は編集ボタンを表示させない
		if (image == null) {
			return false;
		}

		return (image.getWidth() >= image.getHeight()) ? true : false;
	}

	/**
	 * 画像の情報を取得
	 *
	 * @param imageId
	 * @return boolean 縦長:true 横長:false
	 * @throws IOException
	 */
	public static BufferedImage getImageInfo(Integer imageId) {

		BufferedImage image = null;
		try {
			File file = getLocalImage(imageId, "");
			if (!file.exists()) {
				return null;
			}
			image = ImageIO.read(file);
		} catch (IOException e) {
			logger.warn("画像を取得できませんでした" + imageId, e);
		}

		return image;
	}

	/**
	 * 画像ファイル取得
	 *
	 * @param imageId
	 *            画像ID
	 * @return
	 */
	public static File getLocalImage(Integer imageId, String suffix) {
		String imageBaseDir = Config.getString(AppConfigNames.SYSTEM_HTDOCSDIR)
				+ Config.getString(AppConfigNames.IMAGE_DIR);
		String path = getHash(imageId) + imageId + suffix + ".jpg";

		return new File(imageBaseDir, path);
	}

	/**
	 * 画像読み込み。CMYK形式のものはRGB形式に変換して返す
	 *
	 * @param image
	 * @return BufferedImage
	 * @throws ImageReadException
	 * @throws IOException
	 */
	public static BufferedImage readImage(File file) throws ImageReadException, IOException {

		BufferedImage result = null;
		try {
			// RGB形式の画像はそのまま返す
			return ImageIO.read(file);
		} catch (Exception e) {

			FileInputStream in = null;
			try {
				in = new FileInputStream(file);
				result = JAI.create("fileload", file.getAbsolutePath()).getAsBufferedImage();
				result = convertCMYKtoRGB(result);
			} finally {
				IOUtils.closeQuietly(in);
			}
			// invert
			if (checkAdobeMarker(file)) {
				int w = result.getWidth();
				int h = result.getHeight();
				for (int i = 0; i < w; i++) {
					for (int j = 0; j < h; j++) {
						int rgb = result.getRGB(i, j);
						int r = (rgb & 0xff0000) >> 16;
						int g = (rgb & 0x00ff00) >> 8;
						int b = (rgb & 0x0000ff);
						rgb = ((Math.abs(r - 255) << 16) + (Math.abs(g - 255) << 8) + (Math.abs(b - 255)));
						result.setRGB(i, j, rgb);
					}
				}
			}
			logger.info("CMYK形式の画像だったため、RGB形式に変換しました");
		}
		return result;
	}

	/**
	 * 色の反転処理が必要かどうか判別する
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws ImageReadException
	 */
	private static boolean checkAdobeMarker(File file) throws IOException, ImageReadException {
		JpegImageParser parser = new JpegImageParser();
		ByteSource byteSource = new ByteSourceFile(file);
		@SuppressWarnings("rawtypes")
		ArrayList segments = parser.readSegments(byteSource, new int[] { 0xffee }, true);
		if (segments != null && segments.size() >= 1) {
			UnknownSegment app14Segment = (UnknownSegment) segments.get(0);
			byte[] data = app14Segment.bytes;
			if (data.length >= 12 && data[0] == 'A' && data[1] == 'd' && data[2] == 'o' && data[3] == 'b'
					&& data[4] == 'e') {
				return true;
			}
		}
		return false;
	}

	/**
	 * CMYK画像をRGB画像に変換する
	 *
	 * @param readImage
	 * @return
	 * @throws IOException
	 */
	private static BufferedImage convertCMYKtoRGB(BufferedImage readImage) throws IOException {
		InputStream icc = Thread.currentThread().getContextClassLoader().getResourceAsStream("icc/JapanColor2011.icc");
		ICC_Profile p = ICC_Profile.getInstance(icc);
		ColorSpace cmykCS = new ICC_ColorSpace(p);
		BufferedImage rgbImage = new BufferedImage(readImage.getWidth(), readImage.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		ColorSpace rgbCS = rgbImage.getColorModel().getColorSpace();
		ColorConvertOp cmykToRgb = new ColorConvertOp(cmykCS, rgbCS, null);
		cmykToRgb.filter(readImage, rgbImage);

		return rgbImage;
	}

}
