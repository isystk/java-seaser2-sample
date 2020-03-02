/**
 * MailBase.java
 * 2010/10/15 iseyoshitaka
 * (c) Copyright 2009 isystk.com Inc. All rights reserved.
 */
package com.isystk.sample.web.front.s2.mai.dto;

import com.isystk.sample.common.s2.mai.dto.BaseMailDto;

/**
 * パスワードリマインド(ワンタイムURL記載メール)DTO
 * 
 * @author iseyoshitaka
 */
public class PasswordRemindMailDto extends BaseMailDto {

    private static final long serialVersionUID = -3091176034355052523L;

    /** お名前(姓) */
    private String familyName;

    /** お名前(名) */
    private String name;

    /** ワンタイムキー */
    private String onetimeKey;

    /** サーバ名 */
    private String domain;

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
