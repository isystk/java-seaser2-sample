/**
 * MailBase.java
 * 2010/10/15 iseyoshitaka
 * (c) Copyright 2009 TEAM LAB Inc. All rights reserved.
 */
package com.isystk.sample.web.userpc.s2.mai.dto;

import com.isystk.sample.common.s2.mai.dto.BaseMailDto;

/**
 * @author iseyoshitaka
 * 
 */
public class SampleMailDto extends BaseMailDto {

    private static final long serialVersionUID = 8135965273448808623L;

    private String name;

    /**
     * @return name を取得します。
     */
    public String getName() {
	return name;
    }

    /**
     * @param name を設定します。
     */
    public void setName(String name) {
	this.name = name;
    }

}
