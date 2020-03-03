/**
 * Copyright(c) isystk.com</br>
 */
package com.isystk.sample.common.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isystk.sample.common.image.procedure.BasicImageProcedure;

/**
 * シェルを実行するためのクラス。<br>
 * インスタンスは基本的に使い捨て。
 * 
 * @author iseyoshitaka
 */
public class ShellExecutor {
	/** the logger. */
	private static final Logger logger = LoggerFactory.getLogger(BasicImageProcedure.class);

	/**
	 * デフォルトコンストラクタ。
	 */
	public ShellExecutor() {
	}

	/**
	 * シェルを指定するコンストラクタ。
	 * 
	 * @param path
	 *            シェルのパス
	 */
	public ShellExecutor(String path) {
		this(new File(path));
	}

	/**
	 * シェルのディレクトリとファイルを指定するコンストラクタ。
	 * 
	 * @param dir
	 *            シェルのディレクトリ
	 * @param file
	 *            シェルのファイル
	 */
	public ShellExecutor(String dir, String file) {
		this(new File(dir, file));
	}

	/**
	 * シェルのファイルを指定するコンストラクタ。
	 * 
	 * @param file
	 *            シェルのファイル
	 */
	public ShellExecutor(File file) {
		setShellFile(file);
	}

	/**
	 * シェルを実行する。 結果や出力はこのインスタンスに保持される。
	 * 
	 * @throws IOException
	 *             入出力例外が発生した場合
	 */
	public void execute() throws IOException {
		execute(getCommandArray());
	}

	/**
	 * シェルを実行する。 結果や出力はこのインスタンスに保持される。
	 * 
	 * @param cmdarray
	 *            コマンド
	 * @throws IOException
	 *             入出力例外が発生した場合
	 */
	public void execute(String... cmdarray) throws IOException {
		Runtime r = Runtime.getRuntime();
		if (logger.isDebugEnabled()) {
			logger.debug("execute commmand from here.");
			for (String s : cmdarray) {
				logger.debug("cmd:" + s);
			}
			logger.debug("execute commmand to here.");
		}
		Process process = null;

		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		ByteArrayOutputStream stderr = new ByteArrayOutputStream();
		try {

			process = r.exec(cmdarray);
			process.waitFor();

			setExitValue(process.exitValue());

			clearStream(process.getInputStream(), stdout);
			clearStream(process.getErrorStream(), stderr);

		} catch (InterruptedException e) {
			logger.warn(e.getMessage(), e);
		} finally {

			if (process != null) {
				process.getErrorStream().close();
				process.getInputStream().close();
				process.getOutputStream().close();
				process.destroy();
				process = null;

			}
			// ストリームのクローズ処理
			stdout.close();
			stderr.close();

			setStdOut(stdout.toByteArray());
			setStdErr(stderr.toByteArray());
		}
	}

	/**
	 * 入力ストリームを読み込んで、書き出しを行う。
	 * 
	 * @param input
	 *            入力ストリーム
	 * @param out
	 *            書き出し用ストリーム
	 * @throws IOException
	 *             IO例外
	 */
	private static void clearStream(InputStream input, ByteArrayOutputStream out) throws IOException {
		if (input.available() == 0) {
			return;
		}
		// Streamの書き出し
		byte[] buffer = new byte[8192];
		int amount;
		while ((amount = input.read(buffer)) > 0) {
			out.write(buffer, 0, amount);
			if (input.available() == 0) {
				return;
			}
		}
	}

	/**
	 * 実行するcommandをString配列で取得する。
	 * 
	 * @return 実行するcommand
	 */
	private String[] getCommandArray() {
		return new String[] { getShellFile().getAbsolutePath() };
	}

	/**
	 * 標準出力の内容を文字列で取得する。
	 * 
	 * @return 標準出力の内容
	 */
	public String getStdOutString() {
		return new String(getStdOut());
	}

	/**
	 * エラー出力の内容を文字列で取得する。
	 * 
	 * @return エラー出力の内容
	 */
	public String getStdErrString() {
		return new String(getStdErr());
	}

	/*
	 * shellFile File 実行するshellのFile exitValue int 終了コード stdOut byte[] 標準出力 stdErr
	 * byte[] エラー出力
	 */
	/** 実行するshellのFile */
	private File shellFile;

	/** 終了コード */
	private int exitValue;

	/** 標準出力 */
	private byte[] stdOut;

	/** エラー出力 */
	private byte[] stdErr;

	/**
	 * 実行するshellのFileを取得する。
	 * 
	 * @return 実行するshellのFile
	 */
	public File getShellFile() {
		return shellFile;
	}

	/**
	 * 終了コードを取得する。
	 * 
	 * @return 終了コード
	 */
	public int getExitValue() {
		return exitValue;
	}

	/**
	 * 標準出力を取得する。
	 * 
	 * @return 標準出力
	 */
	public byte[] getStdOut() {
		return stdOut;
	}

	/**
	 * エラー出力を取得する。
	 * 
	 * @return エラー出力
	 */
	public byte[] getStdErr() {
		return stdErr;
	}

	/**
	 * 実行するshellのFileを設定する。
	 * 
	 * @param shellFile
	 *            実行するshellのFile
	 */
	public void setShellFile(File shellFile) {
		this.shellFile = shellFile;
	}

	/**
	 * 終了コードを設定する。
	 * 
	 * @param exitValue
	 *            終了コード
	 */
	public void setExitValue(int exitValue) {
		this.exitValue = exitValue;
	}

	/**
	 * 標準出力を設定する。
	 * 
	 * @param stdOut
	 *            標準出力
	 */
	public void setStdOut(byte[] stdOut) {
		this.stdOut = stdOut;
	}

	/**
	 * エラー出力を設定する。
	 * 
	 * @param stdErr
	 *            エラー出力
	 */
	public void setStdErr(byte[] stdErr) {
		this.stdErr = stdErr;
	}
}
