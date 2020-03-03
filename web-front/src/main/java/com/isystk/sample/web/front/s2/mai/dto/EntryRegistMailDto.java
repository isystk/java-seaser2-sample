/**
 * MailBase.java
 * 2010/10/15 iseyoshitaka
 * (c) Copyright 2009 isystk.com Inc. All rights reserved.
 */
package com.isystk.sample.web.front.s2.mai.dto;

import com.isystk.sample.common.s2.mai.dto.BaseMailDto;

/**
 * 会員登録機能から送信するメール本文用Dto
 * 
 * @author iseyoshitaka
 */
public class EntryRegistMailDto extends BaseMailDto {

	/** お名前(姓) */
	private String familyName;

	/** お名前(名) */
	private String name;

	/** 受付No */
	private String userId;

	/** ワンタイムキー */
	private String onetimeKey;

	/** サーバ名 */
	private String domain;

	private static final long serialVersionUID = -3091176034355052523L;

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOnetimeKey() {
		return onetimeKey;
	}

	public void setOnetimeKey(String onetimeKey) {
		this.onetimeKey = onetimeKey;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

}
