package com.isystk.sample.web.front.s2.form;

import java.io.Serializable;

import com.isystk.sample.common.util.NumberUtil;
import com.isystk.sample.common.util.StringUtils;
import com.isystk.sample.web.common.sastruts.URLParam;

/**
 * サイトトップ アクションフォーム
 *
 * @author iseyoshitaka
 */
public class IndexForm implements Serializable {

    /** ページング向けのページNo */
    @URLParam
    public String pageNo;

    /** ソート向けに、ソート順を判定するキーです。 */
    @URLParam
    public String sortKey;

    /** 最大取得件数 */
    @URLParam
    public String maxCount;

    /** フリーワード */
    @URLParam
    public String freeword;

    public String getFreewords() {
	return this.freeword;
    }
    /**
     * ページング向けのページNo を取得します。
     *
     * @return ページング向けのページNo
     */
    public Integer getPageNoInteger() {
	return NumberUtil.toInteger(pageNo, 1);
    }

    public Integer getSortKeyInteger() {
	if (!StringUtils.isBlankOrSpace(this.sortKey)) {
	    return NumberUtil.toInteger(this.sortKey);
	}
	return null;
    }

}
