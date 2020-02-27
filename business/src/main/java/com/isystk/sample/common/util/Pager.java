/*
 * Pager.java
 * 
 * 2011/03/30 mnakamura
 */
package com.isystk.sample.common.util;

import java.io.Serializable;

/**
 * @author mnakamura
 * 
 */
public class Pager implements Serializable {

    private static final long serialVersionUID = 6808400444409736454L;

    /** 1ページあたりの件数をクッキーに持つ際のキー */
    public static final String COOKIE_KEY = "pageLimit";

    /** 1ページあたりの件数をクッキーに持つ際のキー 本体と最大表示件数の種類が異なるため、別クッキーを用意した。 */
    public static final String COOKIE_KEY_DJ = "pageLimitDj";

    /** 1ページあたりの件数の最大数 */
    public static final int MAX_LIMIT = 100;

    /** 総件数 */
    protected long count;

    /** ページあたりの表示件数 */
    protected int limit = 20;

    /** 現在のページ番号 */
    protected int current;

    /** 最大総件数 */
    // FIXME iseyoshitaka
    protected long maxCount = 200000;

    /**
     * ページ送りの数<br>
     * <br>
     * [ ... 5 6 _7_ 8 9 ... ]<br>
     * ... (現在-nのP) (現在-n+1のP) (現在のP) (現在+n-1のP) (現在+nのP) ...<br>
     * のように使用する場合の n を指します<br>
     */
    protected long tipCount = 2;

    protected TipType tipType = TipType.LIQUID;

    /**
     * コンストラクタ。
     */
    public Pager() {
    }

    /**
     * コンストラクタ。
     * 
     * @param count 総件数
     * @param current 現在のページ番号
     * @param limit ページあたりの表示件数
     */
    public Pager(long count, int current, Integer limit) {
	super();
	this.count = count;

	if (limit != null) {
	    this.limit = Math.min(limit, MAX_LIMIT);
	}

	this.current = Math.max(1, current);
//	this.current = Math.min(this.current, getPageCount());
    }

    /**
     * <pre>
     * コンストラクタ。
     * ページの表示件数最大値を制御できます。
     * </pre>
     * 
     * @param count 総件数
     * @param current 現在のページ番号
     * @param limit ページあたりの表示件数
     * @param maxLimit ページあたりの表示件数の最大値
     */
    public Pager(long count, int current, Integer limit, Integer maxLimit) {
	super();
	this.count = count;

	if (limit != null) {
	    this.limit = Math.min(limit, maxLimit);
	}

	this.current = Math.max(1, current);
//	this.current = Math.min(this.current, getPageCount());
    }

    /**
     * コンストラクタ。
     * 
     * @param count 総件数
     * @param limit ページあたりの表示件数
     * @param current 現在のページ番号
     * @param tipCount ページ送りの数
     * @param maxCount 最大総件数
     */
    public Pager(long count, int limit, int current, long tipCount, long maxCount) {
	super();
	this.count = count;
	this.limit = limit;
	this.current = current;
	this.tipCount = tipCount;
	this.maxCount = maxCount;
    }

    /**
     * コンストラクタ。
     * 
     * @param count 総件数
     * @param limit ページあたりの表示件数
     * @param current 現在のページ番号
     * @param tipCount ページ送りの数
     * @param tipType チップタイプ
     */
    public Pager(long count, int limit, int current, long tipCount, TipType tipType) {
	super();
	this.count = count;
	this.limit = limit;
	this.current = current;
	this.tipCount = tipCount;
	this.tipType = tipType;
    }

    /**
     * ################################### 件数
     * ###################################
     */

    /**
     * @return 総件数 を取得します。
     */
    public long getCount() {
	return count;
    }

    /**
     * @param count 総件数を設定します。
     */
    public void setCount(long count) {
	this.count = count;
    }

    /**
     * @return 最大総件数 を取得します。
     */
    public long getMaxCount() {
	return maxCount;
    }

    /**
     * @param maxCount 最大総件数を設定します。
     */
    public void setMaxCount(long maxCount) {
	this.maxCount = maxCount;
    }

    /**
     * @return 現在のページの開始件数を取得します。
     */
    public int getStartCount() {
	return (getCurrent() - 1) * getLimit() + 1;
    }

    /**
     * @return 現在のページの終了件数を取得します。
     */
    public int getEndCount() {
	long cnt = (getCurrent() * getLimit());
	return (int) ((cnt > getCount()) ? getCount() : cnt);
    }

    /**
     * @return オフセットを取得します。
     */
    public int getOffset() {
	return getStartCount() - 1;
    }

    /**
     * @return ページあたりの表示件数 を取得します。
     */
    public int getLimit() {
	return limit;
    }

    /**
     * @param limit ページあたりの表示件数 を設定します。
     */
    public void setLimit(int limit) {
	this.limit = limit;
    }

    /**
     * ################################### ページ
     * ###################################
     */

    /**
     * 総ページ数を取得します。
     * 
     * @return 総ページ数
     */
    public int getPageCount() {
	if (getLimit() <= 0) {
	    return 1;
	}
	long cnt = ((getCount() % getLimit()) == 0) ? 0 : 1;
	long pageCount = (getCount() / getLimit()) + cnt;
	return (int) ((pageCount > 0) ? pageCount : 1);
    }

    /**
     * @return 現在のページ番号を取得します。
     */
    public int getCurrent() {
	return current;
    }

    /**
     * @param current 現在のページ番号を設定します。
     * @deprecated current が 範囲外の場合は、isError メソッドで判定してエラーとするため、このメソッドは使わない。
     */
    public void setCurrent(int current) {
	if (current > getPageCount())
	    current = getPageCount();
	this.current = current;
    }

    /**
     * 存在しないページを判定します。
     * 
     * @return true / false
     */
    public boolean isError() {
	return current <= 0 || current > getPageCount();
    }

    /**
     * ################################### 前後ページ
     * ###################################
     */

    /**
     * 先頭のページかどうかを取得します。
     * 
     * @return 先頭のページの場合は{@code true}、先頭でない場合は{@code false}
     */
    public boolean isFirstPage() {
	return getCurrent() == 1;
    }

    /**
     * 最後のページかどうかを取得します。
     * 
     * @return 最後のページの場合は{@code true}、最後でない場合は{@code false}
     */
    public boolean isLastPage() {
	return getCurrent() == getPageCount();
    }

    /**
     * 前のページがあるかどうかを取得します。
     * 
     * @return 前のページがある場合は{@code true}、ない場合は{@code false}
     */
    public boolean isHasPrev() {
	return getCurrent() > 1;
    }

    /**
     * 次のページがあるかどうかを取得します。
     * 
     * @return 次のページがある場合は{@code true}、ない場合は{@code false}
     */
    public boolean isHasNext() {
	return getCurrent() < getPageCount();
    }

    /**
     * 件数が最大件数を超えているかどうかを取得します。
     * 
     * @return 最大件数を超えている場合は{@code true}、超えていない場合は{@code false}
     */
    public boolean isCountOver() {
	return getCount() > getMaxCount();
    }

    /**
     * 件数がゼロ件であるかどうかを取得します
     * 
     * @return 総件数がゼロの場合は{@code true}、ゼロでない場合は{@code false}
     */
    public boolean isCountZezo() {
	return getCount() == 0;
    }

    /**
     * ページ表示するのに適切な総件数であるかどうかを取得します 具体的には、総件数がゼロではなく、最大総件数も超えていないかどうかを取得します。
     * 
     * @return ページ表示するのに総件数が適している場合は{@code true}、適していない場合は{@code false}
     */
    public boolean isCountToDisplay() {
	return isCountOver() == false && isCountZezo() == false;
    }

    /**
     * @return 前ページ番号のページ番号を取得します。
     */
    public long getPrev() {
	if (isHasPrev())
	    return getCurrent() - 1;
	return 1;
    }

    /**
     * @return 次ページ番号のページ番号を取得します。
     */
    public long getNext() {
	if (isHasNext())
	    return getCurrent() + 1;
	return getCurrent();
    }

    /**
     * ################################### チップ共通
     * ###################################
     */

    /**
     * @return tipCount を取得します。
     */
    public long getTipCount() {
	return tipCount;
    }

    /**
     * @param tipCount を設定します。
     */
    public void setTipCount(int tipCount) {
	this.tipCount = tipCount;
    }

    /**
     * @return 1ページ目と、ページ送り開始番号の間が飛んでいるか否かを取得します。
     */
    public boolean isHasTipPrevSkip() {
	return getTipStart() > 1 + 1;
    }

    /**
     * @return 最終ページ目と、ページ送り終了番号の間が飛んでいるか否かを取得します。
     */
    public boolean isHasTipNextSkip() {
	return getTipEnd() < getPageCount() - 1;
    }

    /**
     * 前ページ送りを表示するか否かを取得します。
     * 
     * @return 表示する場合は{@code true}、そうでない場合は{@code false}
     */
    public boolean isHasTipPrev() {
	return getTipCount() > 0 && getCurrent() > 1;
    }

    /**
     * 次ページ送りを表示するか否かを取得します。
     * 
     * @return 表示する場合は{@code true}、そうでない場合は{@code false}
     */
    public boolean isHasTipNext() {
	return getTipCount() > 0 && getCurrent() < getPageCount();
    }

    /**
     * @return ページ送り開始番号を取得します。
     */
    public long getTipStart() {
	switch (tipType) {
	case FIX:
	    return getTipStartFix();
	case LIQUID:
	    return getTipStartLiquid();
	default:
	    return getTipStartFix();
	}
    }

    /**
     * @return ページ送り終了番号を取得します。
     */
    public long getTipEnd() {
	switch (tipType) {
	case FIX:
	    return getTipEndFix();
	case LIQUID:
	    return getTipEndLiquid();
	default:
	    return getTipEndFix();
	}
    }

    /**
     * ################################### チップパターン
     * ###################################
     */

    /**
     * @return ページ送り開始番号を取得します。（チップ数固定）
     */
    public long getTipStartFix() {
	if (getCurrent() - getTipCount() >= 1) {
	    return getCurrent() - getTipCount();
	} else {
	    return 1;
	}
    }

    /**
     * @return ページ送り終了番号を取得します。（チップ数固定）
     */
    public long getTipEndFix() {
	if (getCurrent() + getTipCount() <= getPageCount()) {
	    return getCurrent() + getTipCount();
	} else {
	    return getPageCount();
	}
    }

    /**
     * @return ページ送り開始番号を取得します。（後ろのチップ数が少ない場合、その分を加える）
     */
    public long getTipStartLiquid() {
	if (getCurrent() - getTipCount() >= 1) {
	    long tmpTipStart = getCurrent() - getTipCount();
	    long tmpTipEnd = getCurrent() + getTipCount();
	    long tipStart = (tmpTipEnd > getPageCount()) ? tmpTipStart + (getPageCount() - tmpTipEnd) : tmpTipStart;
	    return (tipStart >= 1) ? tipStart : 1;
	} else {
	    return 1;
	}
    }

    /**
     * @return ページ送り終了番号を取得します。（前のチップ数が少ない場合、その分を加える）
     */
    public long getTipEndLiquid() {
	if (getCurrent() + getTipCount() <= getPageCount()) {
	    long tmpTipStart = getCurrent() - getTipCount();
	    long tmpTipEnd = getCurrent() + getTipCount();
	    long tipEnd = (tmpTipStart < 1) ? tmpTipEnd + (1 - tmpTipStart) : tmpTipEnd;
	    return (tipEnd <= getPageCount()) ? tipEnd : getPageCount();
	} else {
	    return getPageCount();
	}
    }

    /**
     * チップタイプ
     */
    public static enum TipType {
	FIX, LIQUID;
    }

}
