package com.isystk.sample.web.userpc.s2.mai.dto;


/**
 * 見学予約複数申し込み情報Dto
 * 
 * @author suzukiyohei
 * 
 */
public class InquiryVisitMultiDto {

    /** 受付No */
    private String visitId;

    /** 式場名 */
    private String weddingName;

    /** 屋号ID */
    private String weddingId;

    /** 下見希望日(第１希望日) */
    private String inspection1Date;

    /** 下見希望日(第１希望時間) */
    private String inspection1Time;

    /** 下見希望日(第２希望日) */
    private String inspection2Date;

    /** 下見希望日(第２希望時間) */
    private String inspection2Time;

    /** 下見希望日(第３希望日) */
    private String inspection3Date;

    /** 下見希望日(第３希望時間) */
    private String inspection3Time;

    /** 所在地 */
    private String accessLocation;

    /** 最寄駅 */
    private String nearestStation;

    /** ドメイン */
    private String domain;

    /** 受付No */
    private String registTime;

    /** 電話番号(ない場合は空値) */
    private String accessTel;

    /** お問い合わせ先URL */
    private String inquiryWeddingUrl;

    /** Tpoint */
    int tpoint;
    
    /** サロンのアクセスデータ名 */
    private String salonAccessDataName;

    /** 地図＆アクセスURL */
    private String weddingAccessUrl;

    /** 手配会社かどうか */
    private boolean isAgent;

    /** アンケートURL */
    public String linkEnqueteUrl;

    public String getLinkEnqueteUrl() {
	return linkEnqueteUrl;
    }

    public void setLinkEnqueteUrl(String linkEnqueteUrl) {
	this.linkEnqueteUrl = linkEnqueteUrl;
    }

    public String getVisitId() {
	return visitId;
    }

    public void setVisitId(String visitId) {
	this.visitId = visitId;
    }

    public String getWeddingName() {
	return weddingName;
    }

    public void setWeddingName(String weddingName) {
	this.weddingName = weddingName;
    }

    public String getWeddingId() {
	return weddingId;
    }

    public void setWeddingId(String weddingId) {
	this.weddingId = weddingId;
    }

    public String getInspection1Date() {
	return inspection1Date;
    }

    public void setInspection1Date(String inspection1Date) {
	this.inspection1Date = inspection1Date;
    }

    public String getInspection1Time() {
	return inspection1Time;
    }

    public void setInspection1Time(String inspection1Time) {
	this.inspection1Time = inspection1Time;
    }

    public String getInspection2Date() {
	return inspection2Date;
    }

    public void setInspection2Date(String inspection2Date) {
	this.inspection2Date = inspection2Date;
    }

    public String getInspection2Time() {
	return inspection2Time;
    }

    public void setInspection2Time(String inspection2Time) {
	this.inspection2Time = inspection2Time;
    }

    public String getInspection3Date() {
	return inspection3Date;
    }

    public void setInspection3Date(String inspection3Date) {
	this.inspection3Date = inspection3Date;
    }

    public String getInspection3Time() {
	return inspection3Time;
    }

    public void setInspection3Time(String inspection3Time) {
	this.inspection3Time = inspection3Time;
    }

    public String getAccessLocation() {
	return accessLocation;
    }

    public void setAccessLocation(String accessLocation) {
	this.accessLocation = accessLocation;
    }

    public String getNearestStation() {
	return nearestStation;
    }

    public void setNearestStation(String nearestStation) {
	this.nearestStation = nearestStation;
    }

    public String getDomain() {
	return domain;
    }

    public void setDomain(String domain) {
	this.domain = domain;
    }

    public String getRegistTime() {
	return registTime;
    }

    public void setRegistTime(String registTime) {
	this.registTime = registTime;
    }

    public String getAccessTel() {
	return accessTel;
    }

    public void setAccessTel(String tel) {
	this.accessTel = tel;
    }

    public String getInquiryWeddingUrl() {
	return inquiryWeddingUrl;
    }

    public void setInquiryWeddingUrl(String inquiryWeddingUrl) {
	this.inquiryWeddingUrl = inquiryWeddingUrl;
    }

    public int getTpoint() {
        return tpoint;
    }

    public void setTpoint(int tpoint) {
        this.tpoint = tpoint;
    }
    
    public String getWeddingAccessUrl() {
	return weddingAccessUrl;
    }

    public void setWeddingAccessUrl(String weddingAccessUrl) {
	this.weddingAccessUrl = weddingAccessUrl;
    }

    public String getSalonAccessDataName() {
	return salonAccessDataName;
    }

    public void setSalonAccessDataName(String salonAccessDataName) {
	this.salonAccessDataName = salonAccessDataName;
    }

    public boolean getIsAgent() {
	return isAgent;
    }

    public void setIsAgent(boolean isAgent) {
	this.isAgent = isAgent;
    }
}
