/**
 * Copyright(c) isystk.com</br>
 */
package com.isystk.sample.common.file;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

import com.isystk.sample.common.config.AppConfigNames;
import com.isystk.sample.common.config.Config;
import com.isystk.sample.common.exception.SystemException;

/**
 * ファイル管理のクラス.<br>
 * 
 * @author iseyoshitaka
 */
public class FileManager {

    /** ユーティリティクラスのためコンストラクタを封殺。 */
    private FileManager() {
    }

    /** バッファサイズ */
    private static final int BUFFER_SIZE = 1024 * 64;

    /**
     * 閉じる。
     * 
     * @param closeable 閉じるもの
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
     * 静的ファイルディレクトリを取得する。
     * 
     * @return 静的ファイルディレクトリ
     */
    public static File getDir() {
	return getDir("");
    }

    /**
     * 静的ファイルディレクトリを取得する。
     * 
     * @return 静的ファイルディレクトリ
     */
    public static File getDir(String subDir) {
	File dir = new File(Config.getString(AppConfigNames.SYSTEM_HTDOCSDIR) + subDir);
	if (dir.exists()) {
	    return dir;
	}
	if (dir.mkdirs()) {
	    return dir;
	}

	throw new SystemException("ファイルディレクトリ " + dir + "が作成できません。");
    }

    /**
     * ファイルをコピーする
     * 
     * @param source コピー元ファイル
     * @param dest コピー先ファイル
     * @throws IOException
     */
    public static void copyFile(File source, File dest) throws IOException {
	FileChannel inChannel = new FileInputStream(source).getChannel();
	FileChannel outChannel = new FileOutputStream(dest).getChannel();
	try {
	    inChannel.transferTo(0, inChannel.size(), outChannel);
	} catch (IOException e) {
	    throw e;
	} finally {
	    if (inChannel != null)
		inChannel.close();
	    if (outChannel != null)
		outChannel.close();
	}
    }

    /**
     * ファイルを書き込む
     * 
     * @param name ファイル名
     * @param div ファイルタイプ
     * @param is ファイルデータ
     * @return ファイル情報。ファイルでなければnull。
     */
    public static void writeFile(String name, InputStream is, String subDir) {
	File file = new File(getDir(subDir), name);
	writeFile(is, file);
    }
    public static void writeFile(InputStream is, File file) {
	try {
	    FileOutputStream fos = null;
	    try {
		fos = new FileOutputStream(file);
		byte[] buf = new byte[BUFFER_SIZE];
		int read;
		while ((read = is.read(buf)) > 0) {
		    fos.write(buf, 0, read);
		}
		fos.flush();
	    } catch (IOException e) {
		throw new SystemException("ファイルの格納に失敗しました。" + file, e);
	    } finally {
		close(fos);
	    }

	} catch (SystemException e) {
	    throw e;
	} catch (Exception e) {
	    throw new SystemException(e);
	}

    }

    /**
     * ファイル存在チェック
     * 
     * @param name
     * @param is
     * @param subDir
     * @return
     */
    public static boolean checkReadfile(String name, String subDir) {
	File file = new File(getDir(subDir), name);
	if (file.exists()) {
	    if (file.isFile() && file.canRead()) {
		return true;
	    }
	}
	return false;
    }

}
