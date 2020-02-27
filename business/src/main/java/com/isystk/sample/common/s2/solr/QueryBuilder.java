package com.isystk.sample.common.s2.solr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;

/**
 * QueryBuilder
 * 
 * @author iseyoshitaka
 * @author iseyoshitaka
 */
public class QueryBuilder implements Serializable {

	private static final long serialVersionUID = 1566173245182671807L;

	public static final String OR = " OR ";
	public static final String AND = " AND ";
	
    /**
     * 範囲検索用Integer型Dto
     * 
     * @author iseyoshitaka
     * 
     */
    public static class BetweenIntegerDto {
	public Integer from;
	public Integer to;
	public boolean qe = true;
    }

    /**
     * 範囲検索用Date型Dto
     * 
     * @author iseyoshitaka
     * 
     */
    public static class BetweenDateDto {
	public Date from;
	public Date to;
	public boolean qe = true;
    }

	public enum WrapOperation {
		NOP("%s"),
		SHOULD("(%s)"),
		MUST("+(%s)"),
		MUST_NOT("-(%s)");

		private String format_;

		private WrapOperation(String format) {
			format_ = format;
		}

		public String wrap(String query) {
			return String.format(format_, query);
		}
	}

    /** 整形前query一覧 */
    protected List<Query> queries_ = new ArrayList<Query>();

    /** 整形済query一覧 */
    protected List<String> compQueries_ = new ArrayList<String>();

	private final WrapOperation wrapOperation_;


	/**
	 * コンストラクタ.
	 */
	public QueryBuilder() {
		this(WrapOperation.NOP);
	}

	/**
	 * コンストラクタ.
	 * 
	 * <p>
	 * クエリ全体に適用するオペレータ({@link #toQueryString()} または {@link #toString()} で
	 * 使用される)を指定します.
	 * </p>
	 * <p>クエリが a:"xxx" +b:"aaa" の場合、各オペレータ毎に toString() の結果は次のように変化します.</p>
	 * <pre>
	 * NONE     -> a:"xxx" +b:"aaa"
	 * SHOULD   -> (a:"xxx" +b:"aaa")
	 * MUST     -> +(a:"xxx" +b:"aaa")
	 * MUST_NOT -> -(a:"xxx" +b:"aaa")
	 * +()
	 * </pre>
	 * 
	 * @param wrapOperation クエリ全体に適用される条件(SHOULD, MUST, MUST_NOT).
	 */
	public QueryBuilder(WrapOperation wrapOperation) {
		wrapOperation_ = wrapOperation;
	}

    /**
     * String型のフィールドに対する完全一致または部分一致クエリ(AND)を追加します. value に * や ?
     * が含まれている場合は、部分一致(partial match)クエリとなります.
     * 
     * @param field フィールド
     * @param value 値
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustMatch(String field, String value) {
	return match(Occur.MUST, field, value);
    }

    /**
     * String型のフィールドに対する完全一致または部分一致クエリ(NOT)を追加します. value に * や ?
     * が含まれている場合は、部分一致(partial match)クエリとなります.
     * 
     * @param field フィールド
     * @param value 値
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustNotMatch(String field, String value) {
	return match(Occur.MUST_NOT, field, value);
    }

    /**
     * String型のフィールドに対する完全一致または部分一致クエリ(OR)を追加します. value に * や ?
     * が含まれている場合は、部分一致(partial match)クエリとなります.
     * 
     * @param field フィールド
     * @param value 値
     * @return {@link QueryBuilder}
     */
    public QueryBuilder shouldMatch(String field, String value) {
	return match(Occur.SHOULD, field, value);
    }

    /**
     * Number型(byte, short, int, long, float, double, BigInteger,
     * BigDecimal)のフィールドに対する完全一致クエリ(AND)を追加します.
     * 
     * @param field フィールド
     * @param value 値
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustMatch(String field, Number value) {
	return match(Occur.MUST, field, value);
    }

    /**
     * Number型(byte, short, int, long, float, double, BigInteger,
     * BigDecimal)のフィールドに対する完全一致否定クエリ(NOT)を追加します.
     * 
     * @param field フィールド
     * @param value 値
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustNotMatch(String field, Number value) {
	return match(Occur.MUST_NOT, field, value);
    }

    /**
     * Number型(byte, short, int, long, float, double, BigInteger,
     * BigDecimal)のフィールドに対する完全一致クエリ(OR)を追加します.
     * 
     * @param field フィールド
     * @param value 値
     * @return {@link QueryBuilder}
     */
    public QueryBuilder shouldMatch(String field, Number value) {
	return match(Occur.SHOULD, field, value);
    }

    /**
     * Boolean のフィールドに対する完全一致クエリ(AND)を追加します.
     * 
     * @param field フィールド
     * @param bool 値(true / false)
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustMatch(String field, Boolean bool) {
	return match(Occur.MUST, field, bool);
    }

    /**
     * Boolean のフィールドに対する完全一致否定クエリ(NOT)を追加します.
     * 
     * @param field フィールド
     * @param bool 値(true / false)
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustNotMatch(String field, Boolean bool) {
	return match(Occur.MUST_NOT, field, bool);
    }

    /**
     * Booleanのフィールドに対する完全一致クエリ(OR)を追加します.
     * 
     * @param field フィールド
     * @param bool 値(true / false)
     * @return {@link QueryBuilder}
     */
    public QueryBuilder shouldMatch(String field, Boolean bool) {
	return match(Occur.SHOULD, field, bool);
    }

    /**
     * Date のフィールドに対する完全一致クエリ(AND)を追加します.
     * 
     * @param field フィールド
     * @param date 値
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustMatch(String field, Date date) {
	return match(Occur.MUST, field, date);
    }

    /**
     * Date のフィールドに対する完全一致否定クエリ(NOT)を追加します.
     * 
     * @param field フィールド
     * @param date 値
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustNotMatch(String field, Date date) {
	return match(Occur.MUST_NOT, field, date);
    }

    /**
     * Dateのフィールドに対する完全一致クエリ(OR)を追加します.
     * 
     * @param field フィールド
     * @param date 値
     * @return {@link QueryBuilder}
     */
    public QueryBuilder shouldMatch(String field, Date date) {
	return match(Occur.SHOULD, field, date);
    }

    private QueryBuilder match(Occur occur, String field, Object value) {
	if (StringUtils.isBlank(field)) {
	    return this;
	}

	if (value instanceof String) {
	    value = escape((String) value, false);
	} else if (value instanceof Date) {
	    value = convertDateToString((Date) value);
	}

	BooleanQuery bq = new BooleanQuery();
	bq.add(new TermQuery(new Term(field, String.valueOf(value))), occur);

	queries_.add(bq);

	return this;
    }

    /**
     * 形態素解析済みフィールドに対する全文検索クエリ(AND)を追加します.
     * 
     * @param field フィールド名
     * @param words 検索ワード
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustFullTextMatch(String field, String... words) {
	return fullTextMatch(Occur.MUST, field, words);
    }

    /**
     * 形態素解析済みフィールドに対する全文検索クエリ(AND)を追加します.
     * 
     * @param field フィールド名
     * @param words 検索ワード
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustNotFullTextMatch(String field, String... words) {
	return fullTextMatch(Occur.MUST_NOT, field, words);
    }

    /**
     * 形態素解析済みフィールドに対する全文検索クエリ(OR)を追加します.
     * 
     * @param field フィールド名
     * @param words 検索ワード
     * @return {@link QueryBuilder}
     */
    public QueryBuilder shouldFullTextMatch(String field, String... words) {
	return fullTextMatch(Occur.SHOULD, field, words);
    }

    private QueryBuilder fullTextMatch(Occur occur, String field, String... words) {
	if (StringUtils.isBlank(field) || words.length == 0) {
	    return this;
	}

	BooleanQuery bq = new BooleanQuery();
	for (String word : words) {
	    word = escape(word, true);
	    if (word.length() > 0) {
		bq.add(new TermQuery(new Term(field, word)), occur);
	    }
	}

	queries_.add(bq);

	return this;
    }

    /**
     * 範囲指定のクエリ(AND)を追加します.
     * 
     * @param field フィールド名
     * @param dto 範囲検索用DTO
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustBetween(String field, BetweenIntegerDto dto) {
	return between(Occur.MUST, field, dto.from, dto.to, dto.qe);
    }

    /**
     * 範囲指定のクエリ(NOT)を追加します.
     * 
     * @param field フィールド名
     * @param dto 範囲検索用DTO
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustNotBetween(String field, BetweenIntegerDto dto) {
	return between(Occur.MUST_NOT, field, dto.from, dto.to, dto.qe);
    }

    /**
     * 範囲指定のクエリ(OR)を追加します.
     * 
     * @param field フィールド名
     * @param dto 範囲検索用DTO
     * @return {@link QueryBuilder}
     */
    public QueryBuilder shouldBetween(String field, BetweenIntegerDto dto) {
	return between(Occur.SHOULD, field, dto.from, dto.to, dto.qe);
    }

    /**
     * 範囲指定のクエリ(AND)を追加します.
     * 
     * @param field フィールド名
     * @param dto 範囲検索用DTO
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustBetween(String field, BetweenDateDto dto) {
	return between(Occur.MUST, field, dto.from, dto.to, dto.qe);
    }

    /**
     * 範囲指定のクエリ(NOT)を追加します.
     * 
     * @param field フィールド名
     * @param dto 範囲検索用DTO
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustNotBetween(String field, BetweenDateDto dto) {
	return between(Occur.MUST_NOT, field, dto.from, dto.to, dto.qe);
    }

    /**
     * 範囲指定のクエリ(OR)を追加します.
     * 
     * @param field フィールド名
     * @param dto 範囲検索用DTO
     * @return {@link QueryBuilder}
     */
    public QueryBuilder shouldBetween(String field, BetweenDateDto dto) {
	return between(Occur.SHOULD, field, dto.from, dto.to, dto.qe);
    }

    /**
     * 
     * @param field フィールド名
     * @param from 最小値
     * @param to 最大値
     * @param qe 境界を含む場合はtrue（from <= field <= to）
     * @return {@link QueryBuilder}
     */
    private QueryBuilder between(Occur occur, String field, Object from, Object to, boolean qe) {
	if (StringUtils.isBlank(field) || (from == null && to == null)) {
	    return this;
	}

	String strfrom = null, strto = null;

	if (from != null) {
	    if (from instanceof Integer) {
		strfrom = String.valueOf(from);
	    } else if (from instanceof Date) {
		strfrom = convertDateToString((Date) from);
	    }
	}
	if (to != null) {
	    if (to instanceof Integer) {
		strto = String.valueOf(to);
	    } else if (to instanceof Date) {
		strto = convertDateToString((Date) to);
	    }
	}

	BooleanQuery bq = new BooleanQuery();
	bq.add(new TermRangeQuery(field, strfrom, strto, qe, qe), occur);

	queries_.add(bq);

	return this;
    }

    // mIto Listを入れ子にした検索条件の生成方法はもっと良いやり方があるはず
    /**
     * List<String>型を繋いだクエリ（AND or OR）を追加します
     * 
     * @param field フィールド名
     * @param list リスト
     * @param type AND or OR
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustStringList(String field, List<String> list, String type) {
	List<Object> objList = new ArrayList<Object>();
	for (String str : list) {
	    objList.add(str);
	}
	return matchList(Occur.MUST, field, objList, type);
    }

    /**
     * List<Integer>型を繋いだクエリ（AND or OR）を追加します
     * 
     * @param field フィールド名
     * @param list リスト
     * @param type AND or OR
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustIntegerList(String field, List<Integer> list, String type) {
	List<Object> objList = new ArrayList<Object>();
	for (Integer integer : list) {
	    objList.add(integer);
	}
	return matchList(Occur.MUST, field, objList, type);
    }

    /**
     * List<Date>型を繋いだクエリ（AND or OR）を追加します
     * 
     * @param field フィールド名
     * @param list リスト
     * @param type AND or OR
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustDateList(String field, List<Date> list, String type) {
	List<Object> objList = new ArrayList<Object>();
	for (Date date : list) {
	    objList.add(date);
	}
	return matchList(Occur.MUST, field, objList, type);
    }

    /**
     * List<String>型を繋いだクエリ（NOT）を追加します
     * 
     * @param field フィールド名
     * @param list リスト
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustNotStringList(String field, List<String> list) {
	List<Object> objList = new ArrayList<Object>();
	for (String str : list) {
	    objList.add(str);
	}
	return matchList(Occur.MUST_NOT, field, objList, QueryBuilder.OR);
    }

    /**
     * List<Integer>型を繋いだクエリ（NOT）を追加します
     * 
     * @param field フィールド名
     * @param list リスト
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustNotIntegerList(String field, List<Integer> list) {
	List<Object> objList = new ArrayList<Object>();
	for (Integer integer : list) {
	    objList.add(integer);
	}
	return matchList(Occur.MUST_NOT, field, objList, QueryBuilder.OR);
    }

    /**
     * List<Date>型を繋いだクエリ（NOT）を追加します
     * 
     * @param field フィールド名
     * @param list リスト
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustNotDateList(String field, List<Date> list) {
	List<Object> objList = new ArrayList<Object>();
	for (Date date : list) {
	    objList.add(date);
	}
	return matchList(Occur.MUST_NOT, field, objList, QueryBuilder.OR);
    }

    /**
     * List<BigDecimal>型を繋いだクエリ（NOT）を追加します
     * 
     * @param field フィールド名
     * @param list リスト
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustNotBigDecimalList(String field, List<BigDecimal> list) {
	List<Object> objList = new ArrayList<Object>();
	for (BigDecimal date : list) {
	    objList.add(date);
	}
	return matchList(Occur.MUST_NOT, field, objList, QueryBuilder.OR);
    }

    /**
     * List<BetweenIntegerDto>型を繋いだクエリ（AND or OR）を追加します
     * 
     * @param field フィールド名
     * @param list リスト
     * @param type AND or OR
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustBetweenIntegerList(String field, List<BetweenIntegerDto> list, String type) {
	List<Object> objList = new ArrayList<Object>();
	for (BetweenIntegerDto date : list) {
	    objList.add(date);
	}
	return matchList(Occur.MUST, field, objList, type);
    }

    /**
     * List<BigDecimal>型を繋いだクエリ（AND or OR）を追加します
     * 
     * @param field フィールド名
     * @param list リスト
     * @param type AND or OR
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustBigDecimalList(String field, List<BigDecimal> list, String type) {
	List<Object> objList = new ArrayList<Object>();
	for (BigDecimal date : list) {
	    objList.add(date);
	}
	return matchList(Occur.MUST, field, objList, type);
    }

    /**
     * List<BetweenDateDto>型を繋いだクエリ（AND or OR）を追加します
     * 
     * @param field フィールド名
     * @param list リスト
     * @param type AND or OR
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustBetweenDateList(String field, List<BetweenDateDto> list, String type) {
	List<Object> objList = new ArrayList<Object>();
	for (BetweenDateDto date : list) {
	    objList.add(date);
	}
	return matchList(Occur.MUST, field, objList, type);
    }

    private QueryBuilder matchList(Occur occur, String field, List<Object> list, String type) {

	if (StringUtils.isBlank(field)) {
	    return this;
	}
	if (list == null || list.size() <= 0) {
	    return this;
	}

	StringBuilder sb = new StringBuilder("");
	int cnt = 1;
	sb.append("(");
	for (Object value : list) {
	    if (value == null) {
		return this;
	    }
	    if (value instanceof String) {
		// 文字列検索
		value = escape((String) value, false);
	    } else if (value instanceof Date) {
		// 日付検索
		value = convertDateToString((Date) value);
	    } else if (value instanceof BetweenIntegerDto || value instanceof BetweenDateDto) {
		// 範囲(数値or日付)検索

		String from = "*";
		String to = "*";
		boolean qe = true;
		if (value instanceof BetweenIntegerDto) {
		    from = ((BetweenIntegerDto) value).from == null ? from : ((BetweenIntegerDto) value).from.toString();
		    to = ((BetweenIntegerDto) value).to == null ? to : ((BetweenIntegerDto) value).to.toString();
		    qe = ((BetweenIntegerDto) value).qe;
		} else if (value instanceof BetweenDateDto) {
		    from = ((BetweenDateDto) value).from == null ? from : convertDateToString(((BetweenDateDto) value).from).toString();
		    to = ((BetweenDateDto) value).to == null ? to : convertDateToString(((BetweenDateDto) value).to).toString();
		    qe = ((BetweenDateDto) value).qe;
		}
		String startStr = "[";
		String endStr = "]";
		if (!qe) {
		    startStr = "{";
		    endStr = "}";
		}
		StringBuilder sb2 = new StringBuilder("");
		sb2.append(startStr);
		sb2.append(from);
		sb2.append(" TO ");
		sb2.append(to);
		sb2.append(endStr);

		value = sb2.toString();
	    }

	    sb.append(String.valueOf(value));
	    if (cnt++ < list.size()) {
		sb.append(type);
	    }
	}
	sb.append(")");

	BooleanQuery bq = new BooleanQuery();
	bq.add(new TermQuery(new Term(field, sb.toString())), occur);

	queries_.add(bq);

	return this;
    }

    /**
     * クエリを結合します
     * 
     * @param list
     * @param type
     * @return {@link QueryBuilder}
     */
    public QueryBuilder concatQuery(List<String> list, String type) {
	if (list != null && list.size() > 0) {
	    int cnt = 1;
	    StringBuilder sb = new StringBuilder("");
	    for (String query : list) {
		sb.append(query);
		if (cnt++ < list.size()) {
		    sb.append(type);
		}
	    }
	    compQueries_.add(sb.toString());
	}
	return this;
    }

    /**
     * dynamicFieldのクエリを追加します
     * 
     * @param map
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustDynamicField(Map<String, Integer> map, String type) {
	if (map != null && map.size() > 0) {
	    int cnt = 1;
	    StringBuilder sb = new StringBuilder("");
	    sb.append("+(");
	    for (Map.Entry<String, Integer> entry : map.entrySet()) {
		sb.append(makeDynamicFieldQuery(entry.getKey(), entry.getValue()));
		if (cnt++ < map.size()) {
		    sb.append(type);
		}
	    }
	    sb.append(")");
	    compQueries_.add(sb.toString());
	}
	return this;
    }

    /**
     * dynamicFieldのクエリを追加します
     * 
     * @param map
     * @return {@link QueryBuilder}
     */
    public QueryBuilder mustDynamicFieldList(Map<String, List<Integer>> map, String type) {
	if (map != null && map.size() > 0) {
	    int cnt = 1;
	    StringBuilder sb = new StringBuilder("");
	    sb.append("+(");
	    for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
		int cnt2 = 1;
		for (Integer value : entry.getValue()) {
		    sb.append(makeDynamicFieldQuery(entry.getKey(), value));
		    if (cnt < map.size() || cnt2++ < entry.getValue().size()) {
			sb.append(type);
		    }
		}
		cnt++;
	    }
	    sb.append(")");
	    compQueries_.add(sb.toString());
	}
	return this;
    }

    private String makeDynamicFieldQuery(String field, Object value) {

	if (StringUtils.isBlank(field)) {
	    return "";
	}
	if (value instanceof String) {
	    value = escape((String) value, false);
	} else if (value instanceof Date) {
	    value = convertDateToString((Date) value);
	}

	return new TermQuery(new Term(field, String.valueOf(value).toString())).toString();
    }

    /**
     * すでに整形済みのqueryを設定します
     * 
     * @param queryList
     */
    public void addQueryString(List<String> queryList) {
	compQueries_.addAll(queryList);
    }

    /**
     * クエリ文字列を返します.
     * 
     * 何も指定されていない状態で実行した場合は 全件検索クエリ *:* を返します.
     * 
     * @return クエリ文字列.
     */
    public String toQueryString() {
	if (!hasQuery()) {
	    return "*:*";
	}

	StringBuilder sb = new StringBuilder();
	if (!queries_.isEmpty()) {
	    for (Query q : queries_) {
		sb.append(q.toString()).append(' ');
	    }
	}

	if (!(compQueries_ == null || compQueries_.size() <= 0)) {
	    for (String s : compQueries_) {
		sb.append(s).append(' ');
	    }
	}
	sb.setLength(sb.length() - 1);

	return wrapOperation_.wrap(sb.toString());
    }

    public boolean hasQuery() {
	return !queries_.isEmpty() || (compQueries_ != null && compQueries_.size() > 0);
    }

    @Override
    public String toString() {
	return toQueryString();
    }

	/*
	 * Special Characters
	 * refs. http://lucene.apache.org/java/2_4_0/queryparsersyntax.html#Escaping%20Special%20Characters
	 */
	private static final String[] specialCharsAll = {
		"\\\\", "\\+", "\\-", "\\&\\&", "\\|\\|", "\\!", "\\(", "\\)", "\\{", "\\}", "\\[", "\\]", "\\^", "\\\"", "\\~", "\\*", "\\?", "\\:"
	};

	private static final String[] specialCharsWithoutWildCard = {
		"\\\\", "\\+", "\\-", "\\&\\&", "\\|\\|", "\\!", "\\(", "\\)", "\\{", "\\}", "\\[", "\\]", "\\^", "\\\"", "\\~", "\\:"
	};

	/**
	 * Date型の値をsolrのqueryで使用する日付形式の文字列変換します．
	 * @param date
	 * @return solrのquery用日付文字列
	 */
	public static String convertDateToString(Date date){
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z-9HOUR'");
	    return escape(sdf.format(date), false);
	}
	
	/**
	 * 特殊文字をエスケープします.
	 * @param word 対象文字列
	 * @param all ワイルドカード文字(*, ?)もエスケープする場合は <code>true</code>
	 * @return	エスケープした文字列
	 */
	public static String escape(String word, boolean all) {
		if (StringUtils.isEmpty(word)) {
			return word;
		}

		String[] specialChars = all ? specialCharsAll : specialCharsWithoutWildCard;

		for (int i = 0; i < specialChars.length; i++) {
			word = word.replaceAll(specialChars[i], "\\\\" + specialChars[i]);
		}

		// all=true の場合は前後の半角・全角空白を削除し、ダブルクォートで囲む
		return all ? word.replaceAll("^[　\\s]*|[　\\s]*$", "\"") : word;
	}

	/**
	 * 特殊文字のエスケープを元へ戻します.
	 * @param word 対象文字列
	 * @param all ワイルドカード文字(*, ?)もエスケープされている場合は <code>true</code>
	 * @return エスケープされていない文字列
	 */
	public static String unescape(String word, boolean all) {
		if (StringUtils.isEmpty(word)) {
			return word;
		}

		word = word.replaceAll("^\"|\"$", "");

		String[] specialChars = all ? specialCharsAll : specialCharsWithoutWildCard;

		for (int i = 0; i < specialChars.length; i++) {
			word = word.replaceAll("\\\\" + specialChars[i], specialChars[i]);
		}

		return all ? "\"" + word + "\"" : word;
	}
}
