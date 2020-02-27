package com.isystk.sample.web.userpc.s2.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import com.google.common.collect.Lists;
import com.isystk.sample.common.s2.solr.dto.PostSearchDto;
import com.isystk.sample.common.s2.solr.dto.PostSearchDto.Searcher;
import com.isystk.sample.common.s2.solr.logic.PostSearcherLogic;
import com.isystk.sample.common.s2.solr.util.PostSortBuilder;
import com.isystk.sample.common.util.BeanCopyUtil;
import com.isystk.sample.common.util.NumberUtil;
import com.isystk.sample.common.util.Pager;
import com.isystk.sample.common.util.StringUtils;
import com.isystk.sample.web.common.util.CookieUtil;
import com.isystk.sample.web.userpc.constants.select.PagingLimitSelectOption;
import com.isystk.sample.web.userpc.s2.dto.SearchPostResultDto;
import com.isystk.sample.web.userpc.s2.form.IndexForm;

/**
 * サイトトップ アクション
 *
 * @author iseyoshitaka
 */
public class IndexAction {

    /** 検索結果 式場 アクションフォーム */
    @Resource
    @ActionForm
    public IndexForm indexForm;

    /** ページングを行うためのオブジェクト */
    public Pager pager;

    /** 投稿のインデクス検索ロジック */
    @Resource
    protected PostSearcherLogic postSearcherLogic;

    /** 検索結果 */
    public List<SearchPostResultDto> searchResultDtoList;

	/**
	 * 初期表示
	 */
	@Execute(validator = false)
	public String index() {

		return search();

	}

    /**
     * 検索処理
     *
     * @return 遷移先
     */
    private String search() {

	    // フォームの情報を検索用のDTOに転送します。
	    Searcher searcher = transferPostSearchDto();

	    int count = postSearcherLogic.count(searcher);
	    int current = searcher.page;

	    pager = new Pager(count, current, searcher.rows);

	    List<PostSearchDto> postSearchDtoList = null;
	    if (pager.isCountToDisplay()) {
		// 検索条件から検索結果を取得する。
	    	postSearchDtoList = postSearcherLogic.search(searcher);
	    } else {
	    	postSearchDtoList = Lists.newArrayList();
	    }

	    searchResultDtoList = Lists.newArrayList();
		for (PostSearchDto postSearchDto : postSearchDtoList) {
			SearchPostResultDto searchPostResultDto = BeanCopyUtil.createAndCopy(SearchPostResultDto.class, postSearchDto).execute();
			searchResultDtoList.add(searchPostResultDto);
		}

		return showIndex();
	}

    /**
     * フォームの情報を検索用のDTOに転送します。
     *
     * @return 検索用のDTO
     */
    private Searcher transferPostSearchDto() {
	PostSearchDto.Searcher postSearcherDto = new PostSearchDto.Searcher();

	// ページ
	postSearcherDto.page = indexForm.getPageNoInteger();

	// 最大取得件数
	Integer maxCount = NumberUtil.toInteger(indexForm.maxCount);
	if (maxCount == null) {
	    maxCount = CookieUtil.getValueInteger(Pager.COOKIE_KEY);
	    indexForm.maxCount = String.valueOf(maxCount);
	}
	if (maxCount == null) {
	    maxCount = PagingLimitSelectOption.C20.getCode();
	    indexForm.maxCount = String.valueOf(maxCount);
	}
	postSearcherDto.rows = maxCount;

	// フリーワード
	if (!StringUtils.isBlankOrSpace(indexForm.freeword)) {
	    String[] freewords = StringUtils.splitSpace(indexForm.freeword);
	    List<String> freewordAndList = new ArrayList<String>();
	    for (String freeword : freewords) {
		freewordAndList.add("*" + freeword + "*");
	    }
	    postSearcherDto.freewordAndList = freewordAndList;
	}

	// ソート
	postSearcherDto.sort = indexForm.getSortKeyInteger();
	if (postSearcherDto.sort == null) {
	    if (!StringUtils.isBlankOrSpace(indexForm.getFreewords())) {
		// フリーワード検索時は、スコアでソートする
		postSearcherDto.sort = PostSortBuilder.SortOption.DEFAULT.getCode();
//	    } else if (WeddingDiv.get(indexForm.weddingDiv) == WeddingDiv.PARTY) {
//		// パーティ婚の場合はオプション掲載枠数でソートする
//		postSearcherDto.sort = StockSortBuilder.SortOption.OPTION_REQUEST_CNT.getCode();
	    }
	}

	return postSearcherDto;
    }

	/**
	 * サイトトップ
	 */
	private String showIndex() {
		return "index.jsp";
	}

}