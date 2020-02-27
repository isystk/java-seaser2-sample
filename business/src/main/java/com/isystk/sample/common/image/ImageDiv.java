package com.isystk.sample.common.image;

/**
 * @author iseyoshitaka
 * 
 */
public enum ImageDiv {

    // 本体
    ORIGINAL(""),

    // ジュエリー
    RING("/ring"),

    // ジュエリー(クチコミ)
    RING_REVIEW("/ring/review"),

    // プレミアム
    PREMIUM("/premium"),

    // プレミアム特集
    PREMIUM_FEATURE("/premium/feature");

    private String path;

    ImageDiv(String path) {
	this.path = path;
    }

    public String getPath() {
	return path;
    }

    public void setPath(String path) {
	this.path = path;
    }

}
