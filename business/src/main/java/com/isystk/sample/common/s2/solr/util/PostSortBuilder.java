package com.isystk.sample.common.s2.solr.util;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;

import com.isystk.sample.common.s2.solr.dto.PostSearchDto;

/**
 * プラン用インデクスへのソート条件を生成します
 *
 * @author iseyoshitaka
 *
 */
public class PostSortBuilder {

    /**
     * ソート条件を取得
     *
     * @return
     */
    public static void setSort(SolrQuery query, Integer sortOptionCode, String dynamicSortKey) {

	SortOption sortOption = SortOption.get(sortOptionCode);
//
//	if (SortOption.POST_REGIST_TIME_DESC == sortOption) {
//	    query.addSortField(PostSearchDto.Searcher.POST_REGIST_TIME, ORDER.desc);
//	} else if (SortOption.PER_PERSON2_19_PRICE_ASC == sortOption) {
//	    query.addSortField(PostSearchDto.Searcher.PER_PERSON2_19_PRICE, ORDER.asc);
//	} else if (SortOption.PER_PERSON2_19_PRICE_DESC == sortOption) {
//	    query.addSortField(PostSearchDto.Searcher.PER_PERSON2_19_PRICE, ORDER.desc);
//	} else if (SortOption.POST_SORT_ASC == sortOption) {
//	    query.addSortField(PostSearchDto.Searcher.POST_SORT, ORDER.asc);
//	    query.addSortField(PostSearchDto.Searcher.REGIST_TIME, ORDER.desc);
//	} else if (SortOption.POST_SORT_DESC == sortOption) {
//	    query.addSortField(PostSearchDto.Searcher.POST_SORT, ORDER.desc);
//	    query.addSortField(PostSearchDto.Searcher.REGIST_TIME, ORDER.desc);
//	} else if (SortOption.NEW == sortOption) {
//	    query.addSortField(PostSearchDto.Searcher.STOCK_CONTENT_UPDATE_TIME, ORDER.desc);
//	    query.addSortField(PostSearchDto.Searcher.PostNER_CONTENT_UPDATE_TIME, ORDER.desc);
//	    query.addSortField(PostSearchDto.Searcher.FAIR_CONTENT_UPDATE_TIME, ORDER.desc);
//	} else if (SortOption.SEARCH_RATE == sortOption) {
//	    query.addSortField(PostSearchDto.Searcher.SEARCH_RATE_VAL, ORDER.desc);
//	    query.addSortField(PostSearchDto.Searcher.NEW_NEWS_UPDATE_TIME, ORDER.desc);
////	    query.addSortField(PostSearchDto.Searcher.WEDDING_ID, ORDER.asc);
//	    query.addSortField(PostSearchDto.Searcher.RANDOM, ORDER.asc);
//	} else if (SortOption.SELF_PAY_MONEY_ASC == sortOption) {
//	    if (dynamicSortKey != null) {
//		query.addSortField(PostSearchDto.Searcher.PER_PERSON_PRICE_ + dynamicSortKey, ORDER.asc);
//	    }
//	} else if (SortOption.SELF_PAY_MONEY_DESC == sortOption) {
//	    if (dynamicSortKey != null) {
//		query.addSortField(PostSearchDto.Searcher.PER_PERSON_PRICE_ + dynamicSortKey, ORDER.desc);
//	    }
//	} else if (SortOption.PER_PERSON_20_UNDER_PRICE_ASC == sortOption) {
//	    query.addSortField(PostSearchDto.Searcher.PER_PERSON_20_UNDER_PRICE, ORDER.asc);
//	} else if (SortOption.PER_PERSON_20_UNDER_PRICE_DESC == sortOption) {
//	    query.addSortField(PostSearchDto.Searcher.PER_PERSON_20_UNDER_PRICE, ORDER.desc);
//	} else if (SortOption.SCORE_DESC == sortOption) {
//	    // ※基本FilterQueryで条件を絞るためscoreは全件1.0となる
//	    // ※Queryに条件を指定した場合のみ意味をなす
//	    query.addSortField("score", ORDER.desc);
//	} else if (SortOption.RANDOM_ONEDAY == sortOption) {
//	    query.addSortField(PostSearchDto.Searcher.RANDOM, ORDER.asc);
//	} else if (SortOption.RANDOM == sortOption) {
//	    query.addSortField(PostSearchDto.Searcher.RANDOM + String.valueOf(System.currentTimeMillis()), ORDER.asc);
//	}


	// 必ずIDの昇順でソートする
	query.addSortField(PostSearchDto.Searcher.POST_ID, ORDER.asc);

    }

    /**
     * ソート用のENUM
     *
     * @author iseyoshitaka
     *
     */
    public static enum SortOption {

	/**
	 * 登録日時降順
	 */
	POST_REGIST_TIME_DESC(1, "プラン登録日時降順"),
	/**
	 * 2名料金昇順順
	 */
	PER_PERSON2_19_PRICE_ASC(2, "2名料金昇順"),
	/**
	 * 2名料金降順
	 */
	PER_PERSON2_19_PRICE_DESC(3, "2名料金降順"),

	/**
	 * 並び順昇順
	 */
	POST_SORT_ASC(4, "並び順昇順"),
	/**
	 * 並び順降順
	 */
	POST_SORT_DESC(5, "並び順降順"),
	/**
	 * 新着情報更新順(式場原稿：コンテンツ更新日時の降順) プランナーのひとこと更新順(詳細未定)
	 * フェアの更新順(ブライダルフェア：コンテンツ更新日時の降順)
	 */
	NEW(10, "新着情報更新順/プランナーのひとこと更新順/フェアの更新順"),

	/**
	 * プラン更新日時昇順
	 */
	POST_UPDATE_ASC(11, "プラン更新日時昇順"),
	/**
	 * プラン更新日時降順
	 */
	POST_UPDATE_DESC(12, "プラン更新日時降順"),

	/** 検索レート順/日次単位でのランダム */
	SEARCH_RATE(14, "検索レート順/日次単位でのランダム"),

	/**
	 * 自己負担金額昇順
	 */
	SELF_PAY_MONEY_ASC(15, "自己負担金額昇順"),

	/**
	 * 自己負担金額降順
	 */
	SELF_PAY_MONEY_DESC(16, "自己負担金額降順"),

	/**
	 * プラン提供価格昇順
	 */
	PER_PERSON_20_UNDER_PRICE_ASC(17, "プラン提供価格昇順"),

	/**
	 * プラン提供価格降順
	 */
	PER_PERSON_20_UNDER_PRICE_DESC(18, "プラン提供価格降順"),

	/** スコア降順ソート */
	SCORE_DESC(90, "スコア降順"),

	/** 日次単位でのランダムソート */
	RANDOM_ONEDAY(98, "日次単位でのランダム"),

	/** ランダムソート */
	RANDOM(99, "ランダム")

	;

	private int code;
	private String value;

	public int getCode() {
	    return code;
	}

	public String getValue() {
	    return value;
	}

	public static SortOption get(Integer code) {
	    if (code == null) {
		return DEFAULT;
	    }

	    for (SortOption value : values()) {
		if (value.code == code) {
		    return value;
		}
	    }

	    return DEFAULT;
	}

	SortOption(int code, String value) {
	    this.code = code;
	    this.value = value;
	}

	/** [開発者向け説明] デフォルト値 */
	public static final SortOption DEFAULT = SEARCH_RATE;
    }
}

