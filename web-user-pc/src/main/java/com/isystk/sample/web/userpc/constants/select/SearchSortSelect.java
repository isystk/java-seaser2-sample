package com.isystk.sample.web.userpc.constants.select;


/**
 * フロント検索のソート順で使用するEnum
 */
public enum SearchSortSelect {

    /** 新着情報更新順(式場原稿：コンテンツ更新日時の降順) */
    STOCK_CONTENT_UPDATE_TIME_DESC(1, "新着情報更新順"),

    /** プランナーのひとこと更新順(詳細未定) */
    PLANNER_DESC(2, "プランナーのひとこと更新順"),

    /** フェアの更新順(ブライダルフェア：コンテンツ更新日時の降順) */
    FAIR_CONTENT_UPDATE_TIME_DESC(3, "フェアの更新順"),

    ;

    private int code;
    private String value;

    public int getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    SearchSortSelect(int code, String value) {
	this.code = code;
	this.value = value;
	}

    /** JSPから取るための配列 */
    public static final SearchSortSelect[] ITEMS = values();

}
