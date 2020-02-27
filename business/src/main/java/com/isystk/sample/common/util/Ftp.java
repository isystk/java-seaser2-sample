/**
 * 
 */
package com.isystk.sample.common.util;

import static org.apache.commons.net.ftp.FTPReply.isPositiveCompletion;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isystk.sample.common.config.Config;
import com.isystk.sample.common.exception.SystemException;

/**
 * FTP 送信なクラス<br>
 * 
 * @author haseko
 */
public class Ftp {

    /** 普通のポート */
    private static final int DEFAULT_FTP_PORT = 21;

    /** 普通のディレクトリ */
    private static final String DEFAULT_FTP_DIR = "./";

    /** 全部を表す値 */
    private static final String ALL_FILES = "*";

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(Ftp.class);

    /**  */
    private FTPClient client;

    /** 相手 */
    private String host;

    /** ポート */
    private int port = DEFAULT_FTP_PORT;

    /** 接続ユーザ */
    private String user;

    /** パスワード */
    private String pass;

    /** ルートディレクトリ */
    private String dir = DEFAULT_FTP_DIR;;

    /** activemode */
    private boolean activemode = false;

    /**
     * デフォルトコントラクタ
     */
    public Ftp() {
    }

    /**
     * プロパティキー指定型コンストラクタ<br>
     * <br>
     * ${prefix}.ftp.host<br>
     * ${prefix}.ftp.user<br>
     * ${prefix}.ftp.pass<br>
     * ${prefix}.ftp.activemode<br>
     * ${prefix}.ftp.port<br>
     * ${prefix}.ftp.dir
     * 
     * @param prefix
     */
    public Ftp(String prefix) {
	this();
	setHost(Config.getString(prefix + ".ftp.host"));
	setUser(Config.getString(prefix + ".ftp.user"));
	setPass(Config.getString(prefix + ".ftp.pass"));
	setActivemode(getConstBool(prefix + ".ftp.activemode"));
	setPort(getConstInt(prefix + ".ftp.port"));
	setDir(Config.getString(prefix + ".ftp.dir"));
    }

    /**
     * 設定値
     * 
     * @param key キー
     * @return 値
     */
    private boolean getConstBool(String key) {
	if (Config.getBoolean(key) == null) {
	    return false;
	}
	return Config.getBoolean(key);
    }

    /**
     * 設定値
     * 
     * @param key キー
     * @return 値
     */
    private int getConstInt(String key) {
	if (Config.getInteger(key) == null) {
	    return DEFAULT_FTP_PORT;
	}
	return Config.getInteger(key);
    }

    /**
     * 初期化
     */
    private void init() {
	try {
	    client = new FTPClient();

	    logger.debug(getHost() + " : " + getPort());
	    logger.info("接続します...");
	    client.connect(getHost(), getPort());

	    logger.debug(getUser() + " : ******");
	    logger.info("ログインします...");
	    client.login(getUser(), getPass());

	    logger.info("activemode " + isActivemode());
	    if (isActivemode()) {
		client.enterLocalActiveMode();
	    } else {
		client.enterLocalPassiveMode();
	    }

	    logger.info("FTPClient.BINARY_FILE_TYPE");
	    client.setFileType(FTPClient.BINARY_FILE_TYPE);

	    logger.info("cd " + getDir());
	    client.changeWorkingDirectory(getDir());
	} catch (Exception e) {
	    sendError(e);
	}
    }

    /**
     * ディレクトリを指定<br>
     * 中身を全部送信<br>
     * 階層通りに配下も。<br>
     * ディレクトリではない場合無処理
     * 
     * @param localDirectory ディレクトリ
     */
    public void putAllFiles(String localDirectory) {
	if (!(new File(localDirectory)).isDirectory()) {
	    logger.warn("[" + localDirectory + "]はディレクトリではありません");
	    return;
	}
	put(localDirectory);
    }

    /**
     * 指定されたファイルを送信。<br>
     * ディレクトリは階層通りに配下も。
     * 
     * @param files 送信ファイル
     */
    public void put(String... files) {
	for (String file : files) {
	    put(file);
	}
    }

    /**
     * 指定されたファイルを送信。<br>
     * ディレクトリは階層通りに配下も。
     * 
     * @param files 送信ファイル
     */
    public void put(File[] files) {
	for (File file : files) {
	    put(file);
	}
    }

    /**
     * 指定されたファイルを送信。<br>
     * ディレクトリは階層通りに配下も。
     * 
     * @param file 送信ファイル
     */
    public void put(String file) {
	File target = new File(file);
	if (ALL_FILES.equals(target.getName())) {
	    put(target.getParentFile().listFiles(new ValidFileFilter()));
	} else {
	    put(target);
	}
    }

    /**
     * 指定されたファイルを送信。<br>
     * ディレクトリは階層通りに配下も。
     * 
     * @param file 送信ファイル
     */
    public void put(File file) {
	if (file.isDirectory() && file.canRead()) {
	    logger.debug("[" + file.getName() + "]はディレクトリ");
	    try {
		mkdir(file);

		client.changeWorkingDirectory(file.getName());
		check();

		put(file.listFiles(new ValidFileFilter()));

		client.changeToParentDirectory();
		check();
	    } catch (Exception e) {
		disconnect();
		sendError(e);
	    }
	} else if (file.isFile() && file.canRead()) {
	    logger.debug("[" + file.getName() + "]はファイル");
	    FileInputStream is;
	    try {
		is = new FileInputStream(file);
		put(is, file.getName());
	    } catch (FileNotFoundException e) {
		disconnect();
		sendError(e);
	    }

	} else if (!file.exists() || !file.canRead()) {
	    logger.error("[" + file.getAbsolutePath() + "]は存在しないか読み込めません");
	}
    }

    /**
     * 指定されたインプットストリームを送信。<br>
     * 
     * @param file インプットストリーム
     */
    public void put(InputStream is, String fileName) {

	BufferedInputStream bis = null;
	try {
	    bis = new BufferedInputStream(is);
	    client.storeFile(fileName, bis);
	    logger.info("[" + fileName + "]の画像を作成");
	    check();
	} catch (Exception e) {
	    disconnect();
	    sendError(e);
	} finally {
	    if (bis != null) {
		try {
		    bis.close();
		    bis = null;
		} catch (IOException e) {
		    logger.error(e.getMessage() + e);
		}
	    }
	}

    }

    /**
     * 指定されたファイルを受信。<br>
     * 
     * @param file リモートファイル
     * @param localDirectory 受信先ディレクトリ
     */
    public void get(FTPFile file, String localDirectory) {
	if (!new File(localDirectory).isDirectory()) {
	    logger.warn("[" + localDirectory + "]はディレクトリではありません");
	    return;
	}

	OutputStream outStream = null;
	try {
	    outStream = new BufferedOutputStream(new FileOutputStream(new File(localDirectory, file.getName())));
	    client.retrieveFile(file.getName(), outStream);
	    check();
	} catch (Exception e) {
	    disconnect();
	    sendError(e);
	} finally {
	    if (outStream != null) {
		try {
		    outStream.close();
		    outStream = null;
		} catch (IOException e) {
		    logger.error(e.getMessage() + e);
		}
	    }
	}
    }

    /**
     * 指定されたファイルを削除
     * 
     * @param fileName ファイル名
     */
    public void delete(String fileName) {
	if (StringUtils.isNotEmpty(fileName)) {
	    return;
	}

	try {
	    for (FTPFile file : client.listFiles()) {
		if (!fileName.equals(file.getName())) {
		    continue;
		}
		client.deleteFile(fileName);
	    }
	} catch (IOException e) {
	    logger.error("[" + fileName + "]は削除できません");
	}
    }

    /**
     * 指定されたファイルを削除
     * 
     * @param file ファイル
     */
    public void delete(FTPFile file) {
	if (file == null) {
	    return;
	}

	try {
	    client.deleteFile(file.getName());
	} catch (IOException e) {
	    logger.error("[" + file.getName() + "]は削除できません");
	}
    }

    /**
     * 指定されたファイルを作成
     * 
     * @param fileName ファイル名
     * @param content 内容
     */
    public void store(String fileName, String content) {
	if (StringUtils.isNotEmpty(fileName)) {
	    return;
	} else if (content == null) {
	    content = "";
	}

	ByteArrayInputStream bais = null;
	try {
	    bais = new ByteArrayInputStream(content.getBytes());
	    client.storeFile(fileName, bais);
	    check();
	} catch (Exception e) {
	    disconnect();
	    sendError(e);
	} finally {
	    if (bais != null) {
		try {
		    bais.close();
		    bais = null;
		} catch (IOException e) {
		    disconnect();
		    sendError(e);
		}
	    }
	}
    }

    /**
     * ファイル一覧を取得
     * 
     * @return リモートファイル
     */
    public FTPFile[] listFiles() {
	FTPFile[] files = null;
	try {
	    files = client.listFiles();
	    if (files == null) {
		return new FTPFile[0];
	    }
	} catch (Exception e) {
	    disconnect();
	    sendError(e);
	}
	return files;
    }

    /**
     * 無かったら作るし あったら作らないし
     * 
     * @param file リモートディレクトリ
     * @throws IOException 例外
     */
    private void mkdir(File file) throws IOException {
	boolean remoteExist = false;
	for (FTPFile remote : client.listFiles()) {
	    if (remote.getName().equals(file.getName())) {
		remoteExist = true;
		break;
	    }
	}
	if (!remoteExist) {
	    client.makeDirectory(file.getName());
	    check();
	}
    }

    /**
     * 無かったら作るし あったら作らないし
     * 
     * @param path 画像パス
     * @throws IOException 例外
     */
    public void mkdir(String dirTree) throws IOException {

	boolean dirExists = true;

	String[] directories = dirTree.split("/");
	for (String dir : directories) {
	    if (!dir.isEmpty()) {
		if (dirExists) {
		    dirExists = client.changeWorkingDirectory(dir);
		}
		if (!dirExists) {
		    if (!client.makeDirectory(dir)) {
			throw new SystemException("ディレクトリを作成できません : " + client.getReplyString());
		    }
		    if (!client.changeWorkingDirectory(dir)) {
			throw new SystemException("作成したディレクトリに移動できません : " + client.getReplyString());
		    }
		}
	    }
	}
	logger.info("[" + dirTree + "]のディレクトリを作成");
    }

    /**
     * エラーログと例外発射
     * 
     * @param e 例外
     */
    private void sendError(Exception e) {
	logger.error("FTP Error", e);
	throw new SystemException("FTP Error!! : " + client.getReplyCode(), e);
    }

    /**
     * 接続
     */
    public void connect() {
	init();
    }

    /**
     * 切断
     */
    public void disconnect() {
	try {
	    if (client.isConnected()) {
		logger.info("切断します...");
		client.disconnect();
	    }
	} catch (Exception e) {
	    sendError(e);
	} finally {
	    client = null;
	}
    }

    /**
     * 戻り値チェック
     */
    private void check() {
	if (!isPositiveCompletion(client.getReplyCode())) {
	    throw new SystemException("FTP Error!! : " + client.getReplyCode());
	}
    }

    /**
     * 送信先ディレクトリを取得
     * 
     * @return 送信先ディレクトリ
     */
    public String getDir() {
	return dir;
    }

    /**
     * 送信先ディレクトリを設定
     * 
     * @param dir 送信先ディレクトリ
     */
    public void setDir(String dir) {
	if (StringUtils.isEmpty(dir)) {
	    this.dir = DEFAULT_FTP_DIR;
	} else {
	    this.dir = dir;
	}
    }

    /**
     * 送信先ホストを取得
     * 
     * @return 送信先ホスト
     */
    public String getHost() {
	return host;
    }

    /**
     * 送信先ホストを設定
     * 
     * @param host 送信先ホスト
     */
    public void setHost(String host) {
	this.host = host;
    }

    /**
     * パスワードを返す
     * 
     * @return パスワード
     */
    public String getPass() {
	return pass;
    }

    /**
     * パスワードを設定する
     * 
     * @param pass パスワード
     */
    public void setPass(String pass) {
	this.pass = pass;
    }

    /**
     * ポートを返す
     * 
     * @return ポート
     */
    public int getPort() {
	return port;
    }

    /**
     * ポートを設定する
     * 
     * @param port ポート
     */
    public void setPort(int port) {
	this.port = port;
    }

    /**
     * ユーザーを返す
     * 
     * @return ユーザー
     */
    public String getUser() {
	return user;
    }

    /**
     * ユーザーを設定する
     * 
     * @param user ユーザー
     */
    public void setUser(String user) {
	this.user = user;
    }

    /**
     * activemodeを返す
     * 
     * @return activemode
     */
    public boolean isActivemode() {
	return activemode;
    }

    /**
     * activemodeを設定する
     * 
     * @param activemode activemode
     */
    public void setActivemode(boolean activemode) {
	this.activemode = activemode;
    }

    /**
     * 読めるファイルだけフィルター
     * 
     * @author haseko
     */
    static class ValidFileFilter implements FileFilter {
	public boolean accept(File pathname) {
	    return (!".".equals(pathname.getName())) && (!"..".equals(pathname.getName())) && (pathname.canRead());
	}
    }

    /**
     * TEST
     * 
     * @param args 引数
     */
    public static void main(String[] args) {

	// A
	Ftp feed = new Ftp();

	feed.setHost("192.168.11.221");
	feed.setUser("webmaster");
	feed.setPass("223gMbHX");
	feed.setDir("./hase");

	feed.connect();
	feed.put("C:\\mycom\\sendtest\\*");
	feed.disconnect();

	// B
	Ftp feed2 = new Ftp("FTP_KEY");
	feed2.connect();
	feed2.put("C:\\mycom\\sendtest\\aaa.txt");
	feed2.disconnect();
    }
}
