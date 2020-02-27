/**
 * MailBase.java
 * 2010/10/15 iseyoshitaka
 * (c) Copyright 2009 TEAM LAB Inc. All rights reserved.
 */
package com.isystk.sample.common.s2.mai.dto;

import java.io.Serializable;
import java.util.List;

import org.seasar.framework.env.Env;
import org.seasar.mai.mail.MailAddress;

/**
 * @author iseyoshitaka
 *
 */
public class BaseMailDto implements Serializable {

	private static final long serialVersionUID = -4573654636739914248L;

	protected static final String ENV_VALUE = Env.getValue();

	/**
	 * コンストラクタ。
	 */
	public BaseMailDto() {
	}

	/** 実行環境名 */
	private String env = ENV_VALUE;
	/** from */
	private MailAddress from;
	/** to */
	private List<MailAddress> to;
	/** cc */
	private List<MailAddress> cc;
	/** エラーメールの送信先 */
	private String returnPath;
	/** bcc */
	private List<MailAddress> bcc;
	/** ホスト名 */
	private String hostName;
	/** IPアドレス */
	private String ipAddress;
	/** バッチ開始時間 */
	private String start;
	/** バッチ終了時間 */
	private String end;
	/** マイナビお問い合わせフォームURL */
	private String inquiryUrl;

	/**
	 * @return env を取得します。
	 */
	public String getEnv() {
		return env;
	}

	/**
	 * @return from を取得します。
	 */
	public MailAddress getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            を設定します。
	 */
	public void setFrom(MailAddress from) {
		this.from = from;
	}

	/**
	 * @return to を取得します。
	 */
	public List<MailAddress> getTo() {
		return to;
	}

	/**
	 * @param to
	 *            を設定します。
	 */
	public void setTo(List<MailAddress> to) {
		this.to = to;
	}

	public String getReturnPath() {
		return returnPath;
	}

	public void setReturnPath(String returnPath) {
		this.returnPath = returnPath;
	}

	/**
	 * @return cc を取得します。
	 */
	public List<MailAddress> getCc() {
		return cc;
	}

	/**
	 * @param cc
	 *            を設定します。
	 */
	public void setCc(List<MailAddress> cc) {
		this.cc = cc;
	}

	/**
	 * @return bcc を取得します。
	 */
	public List<MailAddress> getBcc() {
		return bcc;
	}

	/**
	 * @param bcc
	 *            を設定します。
	 */
	public void setBcc(List<MailAddress> bcc) {
		this.bcc = bcc;
	}

	/**
	 * @return hostName を取得します。
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @param hostName
	 *            を設定します。
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * @return ipAddress を取得します。
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress
	 *            を設定します。
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
	}

}
