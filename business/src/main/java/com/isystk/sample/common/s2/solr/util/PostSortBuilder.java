package com.isystk.sample.common.s2.solr.util;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;

import com.isystk.sample.common.s2.solr.dto.PostSearchDto;

/**
 * 投稿用インデクスへのソート条件を生成します
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

	if (SortOption.POST_REGIST_TIME_DESC == sortOption) {
	    query.addSortField(PostSearchDto.Searcher.REGIST_TIME, ORDER.desc);
	} else if (SortOption.RANDOM == sortOption) {
	    query.addSortField(PostSearchDto.Searcher.RANDOM + String.valueOf(System.currentTimeMillis()), ORDER.asc);
	}

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
	POST_REGIST_TIME_DESC(1, "登録日時降順"),

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
	public static final SortOption DEFAULT = POST_REGIST_TIME_DESC;
    }
}

