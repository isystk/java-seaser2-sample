/*
 * NumberUtil.java 2011/03/28 iseyoshitaka
 */
package com.isystk.sample.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.media.jai.JAI;

import org.apache.commons.io.FilenameUtils;

import com.sun.media.jai.codec.MemoryCacheSeekableStream;

/**
 * @author iseyoshitaka
 * 
 */
public class FileUtil {

    /**
     * ファイルの拡張子をチェックします。
     * 
     * @param file ファイル
     * @param extensions 対応する拡張子
     * @return true / false
     */
    public static boolean isExtension(File file, String[] extensions) {
	if (file == null) {
	    return false;
	}
	String fileName = file.getName();
	if (extensions == null || extensions.length == 0) {
	    return (FilenameUtils.indexOfExtension(fileName) == -1);
	}
	String fileExt = getExtension(fileName);
	for (int i = 0; i < extensions.length; i++) {
	    if (fileExt.equalsIgnoreCase(extensions[i])) {
		return true;
	    }
	}
	return false;
    }

    /**
     * ファイルの拡張子を取得します。
     * 
     * @param file ファイル
     */
    public static String getExtension(String fileName) {
	if (StringUtils.isBlankOrSpace(fileName)) {
	    return "";
	}
	String fileExt = FilenameUtils.getExtension(fileName);
	return fileExt.toLowerCase();
    }

    /**
     * ファイルサイズをチェックします。
     * 
     * @param file ファイル
     * @param maxSize 最大のファイルサイズ（バイト）
     * @return true / false
     */
    public static boolean maxFileSize(File file, long maxSize) {
	if (file == null) {
	    return false;
	}
	if (maxSize < file.length()) {
	    return false;
	}
	return true;
    }

    /**
     * 画像ファイルかどうかをチェックします。
     * 
     * @param imageFile 画像ファイル
     * @return true / false
     */
    public static boolean isImageFile(File imageFile) {
	if (imageFile == null) {
	    return false;
	}
	ImageInputStream is = null;
	try {
	    is = ImageIO.createImageInputStream(new FileInputStream(imageFile));
	    Iterator<ImageReader> i = ImageIO.getImageReaders(is);
	    while (i.hasNext()) {
		return true;
	    }
	} catch (IOException e) {
	} finally {
	    if (is != null) {
		try {
		    is.close();
		} catch (IOException e1) {
		}
	    }
	}
	return false;
    }

    /**
     * BufferedImage を取得する
     * 
     * @param stream
     * @return BufferedImage
     * @throws IOException
     */
    public static BufferedImage readJAI(InputStream stream) throws IOException {
	return JAI.create("stream", new MemoryCacheSeekableStream(stream)).getAsBufferedImage();
    }

}
