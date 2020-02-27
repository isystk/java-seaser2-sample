package com.isystk.sample.web.userpc.s2.mai.dto;

import com.isystk.sample.common.s2.mai.dto.BaseMailDto;

/**
 * 中間CVメールDto
 * 
 * @author matsudashogo
 * 
 */
public class MidwayCVMailDto extends BaseMailDto {

    private static final long serialVersionUID = 8537276322235377707L;

    /** クリップ項目 */
    private String clipItem;

    /**
     * クリップ項目を取得します
     * 
     * @return クリップ項目
     */
    public String getClipItem() {
	return this.clipItem;
    }

    /**
     * クリップ項目を設定します。
     * 
     * @param clipItem
     */
    public void setClipItem(String clipItem) {
	this.clipItem = clipItem;
    }

    /** クライアント名 */
    private String clientName;

    /**
     * クライアント名を取得します。
     * 
     * @return クライアント名
     */
    public String getClientName() {
	return this.clientName;
    }

    /**
     * クライアント名を設定します。
     * 
     * @param clientName クライアント名
     */
    public void setClientName(String clientName) {
	this.clientName = clientName;
    }

    /** 屋号Id */
    private String weddingId;

    /**
     * 屋号Idを取得します。
     * 
     * @return 屋号Id
     */
    public String getWeddingId() {
	return this.weddingId;
    }

    /**
     * 屋号Idを設定します。
     * 
     * @param weddingId 屋号Id
     */
    public void setWeddingId(String weddingId) {
	this.weddingId = weddingId;
    }

    /** 式場名 */
    private String weddingName;

    /**
     * 式場名を取得します。
     * 
     * @return 式場名
     */
    public String getWeddingName() {
	return this.weddingName;
    }

    /**
     * 式場名を設定します。
     * 
     * @param weddingName 式場名
     */
    public void setWeddingName(String weddingName) {
	this.weddingName = weddingName;
    }

    /** サーバー名 */
    private String domain;

    /**
     * サーバー名 を取得します。
     * 
     * @return サーバー名
     */
    public String getDomain() {
	return this.domain;
    }

    /**
     * サーバー名 を設定します。
     * 
     * @param domain サーバー名
     */
    public void setDomain(String domain) {
	this.domain = domain;
    }

    /** クリップ対象URL */
    private String targetUrl;

    /**
     * クリップ対象URL を取得します。
     * 
     * @return クリップ対象URL
     */
    public String getTargetUrl() {
	return this.targetUrl;
    }

    /**
     * クリップ対象URL を設定します。
     * 
     * @param targetUrl クリップ対象URL
     */
    public void setTargetUrl(String targetUrl) {
	this.targetUrl = targetUrl;
    }

    /** 契約区分名（ウェディング区分） */
    private String weddingDivName;

    public String getWeddingDivName() {
	return weddingDivName;
    }

    public void setWeddingDivName(String weddingDivName) {
	this.weddingDivName = weddingDivName;
    }

    /** お気に入り日時 */
    private String clipDateTime;

    /**
     * お気に入り日時を取得します
     * 
     * @return お気に入り日時
     */
    public String getClipDateTime() {
	return this.clipDateTime;
    }

    /**
     * お気に入り日時を設定します
     * 
     * @param clipDateTime お気に入り日時
     */
    public void setClipDateTime(String clipDateTime) {
	this.clipDateTime = clipDateTime;
    }

}
