/**
 *
 */
package com.isystk.sample.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.StringUtil;

import com.isystk.sample.common.exception.SystemException;

/**
 * 文字列に関するユーティリティ。<br>
 *
 * @author iseyoshitaka
 */
public final class StringUtils {

	/** 全て半角カタカナか判定するPattern */
	private static final Pattern PTN_HALF_WIDTH_KATAKANA = Pattern.compile("[ｱ-ﾝﾞﾟ]");

	/** 半角カタカナ以外か判定するPattern */
	private static final Pattern PTN_EXCEPT_HALF_WIDTH_KATAKANA = Pattern.compile("[ｱ-ﾝﾞﾟ]");

	/** 全て全角文字か判定するPattern */
	private static final Pattern PTN_FULL_WIDTH = Pattern.compile("([\\p{Digit}\\p{Alnum}\\p{Punct}ｱ-ﾝﾞﾟ])");

	/** 全て全角カタカナか判定するPattern */
	private static final Pattern PTN_FULL_WIDTH_KATAKANA = Pattern.compile("[\\p{InKatakana} 　]*");

	/** 全てひらがなか判定するPattern */
	private static final Pattern PTN_HIRAGANA = Pattern.compile("[\\p{InHIRAGANA}ー゛゜　 ]*");

	/** 半角英数字か判定するPattern */
	private static final Pattern PTN_HALF_WIDTH_ALPHA_OR_DIGIT = Pattern.compile("[\\p{Alnum}]*");

	/** 整数か判定するPattern */
	private static final Pattern PTN_HALF_WIDTH_DIGIT_PERMITS_NEGATIVE = Pattern.compile("-?[\\p{Digit}]+");

	/** 小数か判定するPattern */
	private static final Pattern PTN_HALF_WIDTH_DECIMAL_PERMITS_NEGATIVE = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");

	/** 半角数字か判定するPattern */
	private static final Pattern PTN_HALF_WIDTH_DIGIT = Pattern.compile("[\\p{Digit}]*");

	/** 小数か判定するPattern */
	private static final Pattern PTN_HALF_WIDTH_DECIMAL = Pattern.compile("[0-9]+(\\.[0-9]+)?");

	/** 半角全角数字以外か判定するPattern */
	private static final Pattern PTN_EXCEPT_DIGIT = Pattern.compile("([\\p{Digit}０１２３４５６７８９])");

	/** 半角全角数字以外か判定するPattern */
	private static final Pattern PTN_EXCEPT_ALPHA = Pattern.compile("[^\\p{Alpha}Ａ-Ｚａ-ｚ]*");

	/** 英数記号か判定するPattern */
	private static final Pattern PTN_DIGIT_OR_ALPHA_OR_PUNCT = Pattern.compile("[\\p{Digit}\\p{Alnum}\\p{Punct}]*");

	/** 有効なプロトコルのみのURLかどうかを判定するPattern */
	private static final Pattern PTN_URL_PROTOCOL_ONLY = Pattern.compile("(https?://)$");

	/** 電話番号判定Pattern */
	private static final Pattern PTN_TEL_NUMBER = Pattern.compile("^(\\d{2,5})-(\\d{2,4})-(\\d{4})$");

	/** URL */
	public static final Pattern MATCH_URL = Pattern
			.compile("^(https?|ftp)(:\\/\\/[-_.!~*\\'()a-zA-Z0-9;\\/?:\\@&=+\\$,%#]+)$");

	/** 制御文字・改行コード・タブ文字 */
	private static final Pattern CONTROL_CHARACTER = Pattern
			.compile("[\r\n|\t|\u0000-\u0008|\u000b-\u000c|\u000e-\u001f|\ufdd0-\ufdef|\ufffe|\uffff]");

	/** 共有空配列 */
	private static final Integer[] EMPTY_INT_ARR = new Integer[0];

	/** URLの文字コード */
	private static final String URL_ENC = "UTF-8";

	/** カンマ */
	public static final String COMMA = ",";

	/**
	 * <pre>
	 * 電話番号検索用に全角半角互換でハイフンみたいなのを除去します.
	 * </pre>
	 *
	 * @param str
	 *            文字列
	 * @return 除去後文字列
	 */
	public static String adjustmentTelSearch(String str) {

		if (isNullOrEmpty(str)) {
			return null;
		}

		return str.replaceAll("０", "0").replaceAll("１", "1").replaceAll("２", "2").replaceAll("３", "3")
				.replaceAll("４", "4").replaceAll("５", "5").replaceAll("６", "6").replaceAll("７", "7")
				.replaceAll("８", "8").replaceAll("９", "9").replaceAll("[ー－‐―-]", "");
	}

	/**
	 * 文字列をBigDecimalに変換する。
	 *
	 * @param str
	 *            変換する文字列
	 * @return 文字列をBigDecimalに変換したもの。変換に失敗したらnull。
	 */
	public static BigDecimal bigDecimalValue(String str) {
		try {
			return new BigDecimal(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * <pre>
	 * 先頭を大文字にします.
	 * </pre>
	 *
	 * @see org.apache.commons.lang.StringUtils#capitalize(String)
	 * @param str
	 *            変換前文字列
	 * @return 変換後文字列
	 */
	public static String capitalize(String str) {
		return StringUtil.capitalize(str);
	}

	/**
	 * <pre>
	 * チェックボックスの値がチェックされているかどうかを検証します.
	 * </pre>
	 *
	 * @param cb
	 *            チェックボックスの値
	 * @return onが含まれているときtrue;
	 */
	public static boolean checked(String[] cb) {

		if (cb == null) {
			return false;
		}

		for (String c : cb) {
			if ("on".equalsIgnoreCase(c)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 文字列の長さが指定範囲かどうかをチェックする
	 *
	 * @param str
	 *            検査文字列
	 * @param min
	 *            最小文字数
	 * @param max
	 *            最大文字数
	 * @return -1 : 短すぎる; 0 : 範囲内; 1 : 長すぎる
	 * @author kawamata
	 */
	public static int checkLengthRange(String str, int min, int max) {
		int len = 0;
		int retval = 0;
		if (str == null) {
			len = 0;
		} else {
			len = str.length();
		}

		if (len < min) {
			retval = -1;
		} else if (len > max) {
			retval = 1;
		}
		return retval;
	}

	/**
	 * 文字の最大長をチェックする
	 *
	 * @param str
	 *            検査文字列
	 * @param len
	 *            最大文字数
	 * @return true: 範囲内（OK）; false: 範囲外（NG）
	 * @author kawamata
	 */
	public static boolean checkMaxLength(String str, int len) {
		return checkLengthRange(str, Integer.MIN_VALUE, len) == 0;
	}

	/**
	 * <pre>
	 * 半角を0.5文字と数える最大桁数チェック.
	 * X.5のために文字列に1を足してから計算
	 * </pre>
	 *
	 * @param str
	 *            文字列
	 * @param len
	 *            最大桁数
	 * @return 文字列が最大桁数以下のときtrue, そうでなければfalse
	 */
	public static boolean checkMaxLengthByte(String str, String len) {
		return checkMaxLengthByte(str, intValue(len));
	}

	/**
	 * <pre>
	 * 半角を0.5文字と数える最大桁数チェック.
	 * X.5のために文字列に1を足してから計算
	 * </pre>
	 *
	 * @param str
	 *            文字列
	 * @param len
	 *            最大桁数
	 * @return 文字列が最大桁数以下のときtrue, そうでなければfalse
	 */
	public static boolean checkMaxLengthByte(String str, int len) {

		if (countAsByte(str) > len) {
			return false;
		}
		return true;
	}

	/**
	 * <pre>
	 * 半角を0.5文字と数える.
	 * X.5のために文字列に1を足してから計算
	 * </pre>
	 *
	 * @param str
	 *            文字列
	 * @return 桁数
	 */
	public static int countAsByte(String str) {
		return (str.replaceAll("\r?\n", "\n").replaceAll("[^\\p{ASCII}ｱ-ﾝｦｧｨｩｪｫｯｬｭｮﾞﾟ]", "..").length() + 1) / 2;
	}

	/**
	 * 文字の最大長をチェックする
	 *
	 * @param str
	 *            検査文字列
	 * @param len
	 *            最小文字数
	 * @return true: 範囲内（OK）; false: 範囲外（NG）
	 * @author kawamata
	 */
	public static boolean checkMinLength(String str, int len) {
		return checkLengthRange(str, len, Integer.MAX_VALUE) == 0;
	}

	/**
	 * URLかどうかをチェックする
	 *
	 * @param str
	 *            チェックする文字列
	 * @return URLのとき＆空白のときTRUE 違うとき
	 */
	public static boolean checkURL(String str) {

		if (isNullOrEmpty(str)) {
			return true;
		}

		return str.matches("(https?|ftp)(:\\/\\/[-_.!~*\\'()a-zA-Z0-9;\\/?:\\@&=+\\$,%#]+)");
	}

	/**
	 * <pre>
	 * 文字列同士を比較する。nullなら空文字にして比較する。
	 * </pre>
	 *
	 * @param str1
	 *            比較対象文字列
	 * @param str2
	 *            比較対象文字列
	 * @return 差異がなければtrue 差異があればfalse
	 */
	public static boolean compareString(String str1, String str2) {
		return nullToEmpty(str1).equals(nullToEmpty(str2));
	}

	/**
	 * @param string
	 *            対象の文字列
	 * @param needle
	 *            キーワード
	 * @return stringにneedleのいづれかが含まれるときにtrue
	 */
	public static boolean containAny(String string, Collection<String> needle) {

		if (isNullOrEmpty(string)) {
			return false;
		}
		for (String ndl : needle) {
			if (isNullOrEmpty(ndl)) {
				continue;
			}
			if (string.indexOf(ndl) >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * wave dash の置換など
	 *
	 * @param target
	 *            対象
	 * @return 置換された文字列
	 */
	public static String convertWaveDash(String target) {
		return target
				// WAVE DASH
				.replaceAll("\uFF5E", "\u301C")
				// EM DASH
				.replaceAll("\u2015", "\u2014")
				// DOUBLE VERTICAL LINE
				.replaceAll("\u2225", "\u2016")
				// MINUS SIGN
				.replaceAll("\uFF0D", "\u2212")
				// CENT SIGN
				.replaceAll("\uFFE0", "\u00A2")
				// POUND SIGN
				.replaceAll("\uFFE1", "\u00A3")
				// NOT SIGN
				.replaceAll("\uFFE2", "\u00AC");
	}

	/**
	 * wave dash の逆置換など
	 *
	 * @param target
	 *            対象
	 * @return 逆置換された文字列
	 */
	public static String convertWaveDashRev(String target) {
		return target
				// WAVE DASH
				.replaceAll("\u301C", "\uFF5E")
				// EM DASH
				.replaceAll("\u2014", "\u2015")
				// DOUBLE VERTICAL LINE
				.replaceAll("\u2016", "\u2225")
				// MINUS SIGN
				.replaceAll("\u2212", "\uFF0D")
				// CENT SIGN
				.replaceAll("\u00A2", "\uFFE0")
				// POUND SIGN
				.replaceAll("\u00A3", "\uFFE1")
				// NOT SIGN
				.replaceAll("\u00AC", "\uFFE2");
	}

	/**
	 * wave dash の置換など
	 *
	 * @param target
	 *            対象
	 * @return 置換された文字列
	 */
	public static String convertWaveDashWindows31J(String target) {
		return target
				// WAVE DASH
				.replaceAll("\u301C", "\uFF5E")
				// MINUS SIGN
				.replaceAll("\u2015", "\uFF0D")
				// DOUBLE VERTICAL LINE
				.replaceAll("\u2016", "\u2225")
				// MINUS SIGN
				.replaceAll("\u2212", "\uFF0D")
				// CENT SIGN
				.replaceAll("\u00A2", "\uFFE0")
				// POUND SIGN
				.replaceAll("\u00A3", "\uFFE1")
				// NOT SIGN
				.replaceAll("\u00AC", "\uFFE2");
	}

	/**
	 * 空文字の場合にNULLに置き換える
	 *
	 * @param str
	 *            文字列
	 * @return strが空文字の場合にNULL。そうでなければstr
	 */
	public static String emptyToNull(String str) {
		if (isNullOrEmpty(str)) {
			return null;
		}
		return str;
	}

	/**
	 * 空文字の場合に空文字に置き換える
	 *
	 * @param str
	 *            文字列
	 * @return strが空文字の場合にNULL。そうでなければstr
	 */
	public static String emptyToBlank(String str) {
		if (isNullOrEmpty(str)) {
			return "";
		}
		return str;
	}

	/**
	 * <pre>
	 * LIKE用のエスケープをします.
	 * </pre>
	 *
	 * @param list
	 *            文字列リスト
	 * @return エスケープされたリスト
	 */
	public static List<String> escapeSqlLikeCauses(Collection<String> list) {
		if (list == null) {
			return Collections.emptyList();
		}

		List<String> result = new ArrayList<String>();
		for (String str : list) {
			if (isNullOrEmpty(str)) {
				continue;
			}
			result.add(escapeSqlLikeCauses(str));
		}
		return result;
	}

	/**
	 * LIKE検索用に文字列をエスケープする
	 *
	 * @param likeStr
	 *            エスケープする文字列
	 * @return エスケープされた文字列
	 */
	public static String escapeSqlLikeCauses(String likeStr) {
		if (isNullOrEmpty(likeStr)) {
			return null;
		}
		return likeStr.replaceAll("[%]", "\\\\%").replaceAll("[_]", "\\\\_");
	}

	/**
	 * 文字列をIntegerに変換する。
	 *
	 * @param obj
	 *            変換する文字列
	 * @return 文字列をIntegerに変換したもの。変換に失敗したらnull。
	 */
	public static Integer integerValue(Object obj) {
		if (obj instanceof Integer)
			return (Integer) obj;
		try {
			return Integer.valueOf(obj.toString());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 文字列をLongに変換する。
	 *
	 * @param str
	 *            変換する文字列
	 * @return 文字列をLongに変換したもの。変換に失敗したらnull。
	 */
	public static Long longValue(String str) {
		try {
			return Long.valueOf(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * String配列をInteger配列に変換する。
	 *
	 * @param strArray
	 *            変換する文字列
	 * @return 文字列をIntegerに変換したもの。変換に失敗したら空配列。
	 */
	public static Integer[] integerValues(String[] strArray) {
		try {
			Integer[] intArray = new Integer[strArray.length];
			for (int i = 0; i < strArray.length; i++) {
				intArray[i] = Integer.valueOf(strArray[i]);
			}
			return intArray;
		} catch (Exception e) {
			return EMPTY_INT_ARR;
		}
	}

	/**
	 * 文字列をIntegerに変換する。
	 *
	 * @param str
	 *            変換する文字列
	 * @return 文字列をIntegerに変換したもの。変換に失敗したら0。
	 */
	public static int intValue(String str) {
		try {
			return Integer.valueOf(str);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * <pre>
	 * スペース文字のみで構成されているかチェックします.
	 * </pre>
	 *
	 * @param str
	 *            文字列
	 * @return 空かスペース構成文字のみのときtrue
	 */
	public static boolean isBlankOrSpace(String str) {

		if (str == null) {
			return true;
		}

		return isNullOrEmpty(str.replaceAll("[\t 　]", ""));

	}

	/**
	 * <pre>
	 * スペース文字以外で構成されているかチェックします.
	 * </pre>
	 *
	 * @param str
	 *            文字列
	 * @return 空かスペース構成文字のみ以外のときtrue
	 */
	public static boolean isNotBlankOrSpace(String str) {
		return !isBlankOrSpace(str);
	}

	/**
	 * <pre>
	 * すべての値が、スペース文字のみで構成されているかチェックします.
	 * </pre>
	 *
	 * @param str
	 *            文字列
	 * @return すべての値が、空かスペース構成文字のみのときtrue
	 */
	public static boolean isAllBlankOrSpace(String[] str) {

		if (str == null) {
			return true;
		}

		boolean result = true;
		for (String a : str) {
			if (!isBlankOrSpace(a)) {
				result = false;
				break;
			}
		}
		return result;
	}

	/**
	 * ホワイトスペースのみで構成されているかをチェックします。
	 *
	 * @param str
	 *            文字列
	 * @return ホワイトスペースのみで構成されている場合のみ true。それ以外の場合は false
	 */
	public static boolean isWhitespace(String str) {

		// 指定文字列が null または空文字の場合
		if ((str == null) || isNullOrEmpty(str)) {

			// ホワイトスペース以外
			return false;

		}

		// 全文字を検索する
		for (final char strChar : str.toCharArray()) {

			// ホワイトスペース以外の場合
			if (!Character.isWhitespace(strChar)) {

				// ホワイトスペース以外
				return false;

			}

		}

		// ホワイトスペースのみ
		return true;

	}

	/**
	 * プロトコル(http, https)のみのURL表記かどうかを取得する。
	 *
	 * @param str
	 *            チェックする文字列
	 * @return プロトコル(http, https)のみのURL表記の場合は true。それ以外は false
	 */
	public static boolean isProtocolOnlyUrl(String str) {

		// 空文字の場合
		if (isNullOrEmpty(str)) {

			// プロトコルオンリー以外
			return false;

		}

		// プロトコルのみURLかどうかを返す
		return PTN_URL_PROTOCOL_ONLY.matcher(str).matches();

	}

	/**
	 * 有効な電話番号表記かどうかを取得する。
	 *
	 * 電話番号はハイフン付き電話番号を指定すること。
	 *
	 * @param str
	 *            チェックする文字列
	 * @return 有効な電話番号表記の場合は true。それ以外は false
	 */
	public static boolean isValidTelNumber(final String str) {

		// 空文字の場合
		if (isNullOrEmpty(str)) {

			// 無効
			return false;

		}

		// マッチング結果を返す
		return PTN_TEL_NUMBER.matcher(str).matches();

	}

	/**
	 * 文字列が数値変換可能か判断する
	 *
	 * @param str
	 *            変換する文字列
	 * @return 変換できた場合true.出来なかった場合false.
	 */
	public static boolean isDecimal(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 英数記号か判定する
	 *
	 * @param target
	 *            対象文字列
	 * @return 英数記号であれば true
	 */
	public static boolean isDigitOrAlphaOrPunct(String target) {
		return PTN_DIGIT_OR_ALPHA_OR_PUNCT.matcher(target).matches();
	}

	/**
	 * 英字以外の文字かを判定する
	 *
	 * @param target
	 *            対象文字列
	 * @return すべて英字以外の文字の場合true.そうでなければfalse.
	 */
	public static boolean isExceptAlpha(String target) {
		return PTN_EXCEPT_ALPHA.matcher(target).matches();
	}

	/**
	 * 半角全角数字以外か判定する
	 *
	 * @param target
	 *            対象文字列
	 * @return true 半角カタカナ以外
	 */
	public static boolean isExceptDigit(String target) {
		return !PTN_EXCEPT_DIGIT.matcher(target).find();
	}

	/**
	 * 半角カタカナ以外か判定する
	 *
	 * @param target
	 *            対象文字列
	 * @return true 半角カタカナ以外
	 */
	public static boolean isExceptHalfWidthKatakana(String target) {
		return !PTN_EXCEPT_HALF_WIDTH_KATAKANA.matcher(target).find();
	}

	/**
	 * 全て全角文字か判定する
	 *
	 * @param target
	 *            対象文字列
	 * @return true 全て全角文字列
	 */
	public static boolean isFullWidth(String target) {
		return !PTN_FULL_WIDTH.matcher(target).find();
	}

	/**
	 * 全て全角カタカナか判定する
	 *
	 * @param target
	 *            対象文字列
	 * @return true 全て全角カタカナ
	 */
	public static boolean isFullWidthKatakana(String target) {
		return PTN_FULL_WIDTH_KATAKANA.matcher(target).matches();
	}

	/**
	 * 半角英数字か判定する
	 *
	 * @param target
	 *            対象文字列
	 * @return true 全て半角英数字
	 */
	public static boolean isHalfWidthAlphaOrDigit(String target) {
		return PTN_HALF_WIDTH_ALPHA_OR_DIGIT.matcher(target).matches();
	}

	/**
	 * 小数か判定する
	 *
	 * @param target
	 *            対象文字列
	 * @return true 全て半角英数字
	 */
	public static boolean isHalfWidthDecimal(String target) {
		return PTN_HALF_WIDTH_DECIMAL.matcher(target).matches();
	}

	/**
	 * 小数か判定する
	 *
	 * @param target
	 *            対象文字列
	 * @return true 全て半角英数字
	 */
	public static boolean isHalfWidthDecimalPermitsNegative(String target) {
		return PTN_HALF_WIDTH_DECIMAL_PERMITS_NEGATIVE.matcher(target).matches();
	}

	/**
	 * 半角数字か判定する
	 *
	 * @param target
	 *            対象文字列
	 * @return true 全て半角数字
	 */
	public static boolean isHalfWidthDigit(String target) {
		return PTN_HALF_WIDTH_DIGIT.matcher(target).matches();
	}

	/**
	 * 整数か判定する
	 *
	 * @param target
	 *            対象文字列
	 * @return true 全て半角英数字
	 */
	public static boolean isHalfWidthDigitPermitsNegative(String target) {
		return PTN_HALF_WIDTH_DIGIT_PERMITS_NEGATIVE.matcher(target).matches();
	}

	/**
	 * 全て半角カタカナか判定する
	 *
	 * @param target
	 *            対象文字列
	 * @return true 全て半角カタカナ
	 */
	public static boolean isHalfWidthKatakana(String target) {
		return PTN_HALF_WIDTH_KATAKANA.matcher(target).matches();
	}

	/**
	 * 全てひらがなか判定する
	 *
	 * @param target
	 *            対象文字列
	 * @return true 全てひらがな
	 */
	public static boolean isHiragana(String target) {
		return PTN_HIRAGANA.matcher(target).matches();
	}

	/**
	 * 文字列に指定した文字が含まれるか判定する。
	 *
	 * @param target
	 *            対象文字列
	 * @param str
	 *            含まれる文字
	 * @return 指定した文字が含まれる場合true.そうでなければfalse.
	 */
	public static boolean isIncluded(String target, String str) {
		return (target.indexOf(str) >= 0);
	}

	/**
	 * 指定した文字列がnullまたは空文字列であるか否かを取得する。
	 *
	 * @param string
	 *            対象文字列
	 * @return 文字列がnullまたは空文字列の場合true.そうでなければfalse.
	 */
	public static boolean isNullOrEmpty(Object string) {
		return string == null || string.toString().length() == 0;
	}

	/**
	 * 指定した文字列がnullまたは空文字列ではないか否かを取得する。
	 *
	 * @param string
	 *            対象文字列
	 * @return 文字列がnullまたは空文字列ではない場合true.そうでなければfalse.
	 */
	public static boolean isNotNullOrEmpty(Object string) {
		return !isNullOrEmpty(string);
	}

	/**
	 * 文字を連結する
	 *
	 * @param separator
	 *            分割文字列
	 * @param objects
	 *            Stringの集合
	 * @return 連結後の文字列
	 */
	public static String join(String separator, Object... objects) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Object object : objects) {
			String str = nullToEmpty(object);
			if (str.length() != 0) {
				if (!first) {
					sb.append(separator);
				}
				sb.append(str);
				first = false;
			}
		}
		return sb.toString();
	}

	/**
	 * 配列の文字列を連結する
	 *
	 * @param array
	 *            文字列の配列
	 * @param separator
	 *            区切り文字
	 * @return 区切り文字で区切って連結した文字列(NULLや空配列の場合は空文字)
	 *
	 * @author kawamata
	 */
	public static String joinArray(String[] array, String separator) {

		String retval = null;
		if (array == null || array.length == 0) {
			// NULLのか空ときは空文字を返す
			retval = "";

		} else {
			// 連結して返却する
			StringBuilder sb = new StringBuilder().append(array[0]);
			for (int i = 1; i < array.length; i++) {
				sb.append(separator).append(array[i]);
			}
			retval = sb.toString();

		}

		return retval;
	}

	/**
	 * Listの文字列を連結する
	 *
	 * @param list
	 *            List
	 * @param separator
	 *            区切り文字
	 * @return 区切り文字で区切って連結した文字列(NULLや空配列の場合は空文字)
	 */
	public static String join(List<?> list, String separator) {
		return org.apache.commons.lang3.StringUtils.join(list, separator);
	}

	/**
	 * フリーワード用文字列をスペース区切りの部分検索用トークンに分割します
	 *
	 * @param string
	 *            検索文字列
	 * @return List
	 */
	public static List<String> likeSearchToken(String string) {

		if (isNullOrEmpty(string)) {
			return Collections.emptyList();
		}

		String[] tokenzed = splitSpace(string);
		for (int i = 0; i < tokenzed.length; i++) {
			tokenzed[i] = "%" + escapeSqlLikeCauses(tokenzed[i]) + "%";
		}
		return Arrays.asList(tokenzed);
	}

	/**
	 * <pre>
	 * 左トリミングします.
	 * </pre>
	 *
	 * @param str
	 *            右トリミングされる文字列
	 * @param trim
	 *            トリミングする文字列
	 * @return 右トリミングされた文字列
	 */
	public static String ltrim(String str, String trim) {
		while (str.startsWith(trim)) {
			str = substr(str, trim.length());
		}
		return str;
	}

	/**
	 * 正規表現にマッチしているかを判定する
	 *
	 * @param value
	 *            検査対象文字列
	 * @param pattern
	 *            正規表現文字列
	 * @return true:マッチ, false:アンマッチ
	 * @deprecated 静的正規表現はstaticの{@link java.util.regex.Pattern}を使ってください。
	 */
	public static boolean matches(String value, String pattern) {

		if (pattern == null) {
			return true;
		}
		Pattern p = Pattern.compile(pattern);
		Matcher matcher = p.matcher(value);

		return matcher.matches();
	}

	/**
	 * nullの場合空文字列に変換する。そうでなければそのままtoString.valueOf()
	 *
	 * @param o
	 *            値
	 * @return oがnullの場合空文字列。そうでなければString.valueOf(o)
	 */
	public static String nullToEmpty(Object o) {
		if (o == null) {
			return "";
		}
		if (o instanceof String) {
			return (String) o;
		}
		return String.valueOf(o);
	}

	/**
	 * 10000以上の場合X万円に変換する。10000以下の場合はX円を返す。
	 *
	 * @param target
	 *            対象数値
	 * @return targetが10000以上の場合万に変換する。
	 */
	public static String numberFormatToJP(Integer target) {
		if (target == null) {
			return "";
		}

		DecimalFormat df = new DecimalFormat("#万");
		DecimalFormat df2 = new DecimalFormat("#円");

		if (target < 10000) {
			return df2.format(target);
		}

		Integer number = target / 10000;
		Integer decimal = target % 10000;

		String formatNumber = df.format(number);
		if (decimal != 0) {
			formatNumber += df2.format(decimal);
		} else {
			formatNumber += "円";
		}

		return formatNumber;
	}

	/**
	 * <i>target</i>を1,234,567円の形式でフォーマットする
	 *
	 * @param target
	 *            金額
	 * @return フォーマットされた値
	 */
	public static String numberFormat(Integer target) {

		if (target == null) {
			return "";
		}

		return new DecimalFormat("###,###,###").format(target);

	}

	/**
	 * <i>target</i>を1,234,567円の形式でフォーマットする(long用)
	 *
	 * @param target
	 *            金額
	 * @return フォーマットされた値
	 */
	public static String numberFormatLong(Long target) {

		if (target == null) {
			return "";
		}

		return new DecimalFormat("###,###,###").format(target);

	}

	/**
	 * <i>target</i>を1,234,567円の形式でフォーマットする
	 *
	 * @param target
	 *            金額
	 * @return フォーマットされた値
	 */
	public static String numberFormatYen(Integer target) {

		if (target == null) {
			return "";
		}

		return new DecimalFormat("###,###,###円").format(target);

	}

	/**
	 * <i>target</i>を1,234,567円の形式でフォーマットする(long用)
	 *
	 * @param target
	 *            金額
	 * @return フォーマットされた値
	 */
	public static String numberFormatYenLong(Long target) {

		if (target == null) {
			return "";
		}

		return new DecimalFormat("###,###,###円").format(target);

	}

	/**
	 * 最後のデリミタ削除
	 *
	 * @param org
	 *            対象
	 * @param delimiter
	 *            区切り文字
	 * @return 最後のデリミタ削除文字列
	 */
	public static String removeLastDelimiter(String org, String delimiter) {
		if (!isNullOrEmpty(org)) {
			return substr(org, 0, org.length() - delimiter.length());
		} else {
			return org;
		}
	}

	/**
	 * <pre>
	 * 右トリミングします.
	 * </pre>
	 *
	 * @param str
	 *            右トリミングされる文字列
	 * @param trim
	 *            トリミングする文字列
	 * @return 右トリミングされた文字列
	 */
	public static String rtrim(String str, String trim) {
		while (str.endsWith(trim)) {
			str = substr(str, 0, str.length() - trim.length());
		}
		return str;
	}

	/**
	 * valueをスペース(半角・全角)で区切り、String型の配列で返す
	 *
	 * @param value
	 *            split対象文字列
	 * @return String型配列 valueが存在しない場合、nullを返却
	 * @author kamoshita
	 */
	public static String[] splitSpace(String value) {
		if (!StringUtils.isNullOrEmpty(value)) {
			// 全角スペースを半角スペースに置き換えて、trimしてからsplit
			String replaced = value.replaceAll("　", " ");
			String[] splits = replaced.trim().split("[ 　]+");
			return splits;
		}
		return null;
	}

	/**
	 * {@link String#substring(int)}のバグ回避策。
	 *
	 * @param str
	 *            基文字列
	 * @param beginIndex
	 *            はじめ
	 * @param endIndex
	 *            おわり
	 * @return substring
	 */
	public static String substr(String str, int beginIndex) {
		return str.substring(beginIndex);
	}

	/**
	 * {@link String#substring(int, int)}のバグ回避策。
	 *
	 * @param str
	 *            基文字列
	 * @param beginIndex
	 *            はじめ
	 * @param endIndex
	 *            おわり
	 * @return substring
	 */
	public static String substr(String str, int beginIndex, int endIndex) {
		return str.substring(beginIndex, endIndex);
	}

	/**
	 * <pre>
	 * トリミングします.
	 * </pre>
	 *
	 * @param str
	 *            トリミングされる文字列
	 * @param trim
	 *            トリミングする文字列
	 * @return トリミングされた文字列
	 */
	public static String trim(String str, String trim) {
		return rtrim(ltrim(str, trim), trim);

	}

	/**
	 * Trim
	 *
	 * @param str
	 *            the String to be trimmed, may be null
	 * @return the trimmed string, {@code null} if null String input
	 */
	public static String trim(String str) {
		return org.apache.commons.lang3.StringUtils.trim(str);
	}

	/**
	 * SubStringBefore
	 *
	 * @param str
	 *            the String to get a substring from, may be null
	 * @param separator
	 *            the String to search for, may be null
	 * @return the substring before the first occurrence of the separator,
	 *         {@code null} if null String input
	 */
	public static String substringBefore(String str, String separator) {
		return org.apache.commons.lang3.StringUtils.substringBefore(str, separator);
	}

	/**
	 * 文字列を一定長にまるめる
	 *
	 * @param string
	 *            対象の文字列
	 * @param len
	 *            丸める長さ
	 * @return まるめた文字列
	 */
	public static String truncateString(String string, int len) {
		return truncateString(string, len, "");
	}

	/**
	 * 文字列を一定長にまるめる
	 *
	 * @param string
	 *            対象の文字列
	 * @param len
	 *            丸める長さ
	 * @param padding
	 *            まるめた際にあとにつける文字列
	 * @return まるめた文字列
	 */
	public static String truncateString(String string, int len, String padding) {
		string = nullToEmpty(string);
		if (!checkMaxLength(string, len)) {
			return substr(string, 0, Math.max(0, len - padding.length())).concat(padding);
		}
		return string;
	}

	/**
	 * URLデコードを行います.
	 *
	 * @param s
	 *            変換対象
	 * @return 変換結果
	 */
	public static String URLDecode(String s) {
		if (isNullOrEmpty(s)) {
			return "";
		}
		try {
			return URLDecoder.decode(s, URL_ENC);
		} catch (UnsupportedEncodingException e) {
			throw new SystemException(e);
		}
	}

	/**
	 * URLエンコードを行います.
	 *
	 * @param s
	 *            変換対象
	 * @return 変換結果
	 */
	public static String URLEncode(String s) {
		if (isNullOrEmpty(s)) {
			return "";
		}
		try {
			return URLEncoder.encode(s, URL_ENC);
		} catch (UnsupportedEncodingException e) {
			throw new SystemException(e);
		}
	}

	/**
	 * null じゃなかったら toString
	 *
	 * @param i
	 * @return
	 */
	public static String stringValue(Object o) {
		if (o != null) {
			return o.toString();
		} else {
			return null;
		}
	}

	/**
	 * null じゃなかったら default
	 *
	 * @param i
	 * @return
	 */
	public static String stringValue(Object o, String init) {
		if (o != null) {
			return o.toString();
		} else {
			return init;
		}
	}

	/**
	 * 先頭に文字列を足しこみます.
	 *
	 * @param body
	 *            足されるもの
	 * @param str
	 *            足すもの
	 * @param maxlen
	 *            最大桁数
	 * @return 足しこまれた文字列
	 */
	public static String insertHead(String body, String str, int maxlen) {

		StringBuffer sb = new StringBuffer();

		body = StringUtils.nullToEmpty(body);

		int length = body.length();
		if (length < maxlen) {
			for (int i = 0; i < (maxlen - length); i++) {
				sb.append(str);
			}
		}
		sb.append(body);

		return sb.toString();
	}

	/**
	 * 文字列内のtabを削除する.
	 *
	 * @param str
	 *            文字列
	 * @return tabが削除された文字列
	 */
	public static String deleteTab(String str) {
		if (str == null) {
			return null;
		} else {
			return str.replaceAll("\t", "");
		}
	}

	public static String defaultValue(Object value, String def) {
		return isNullOrEmpty(value) ? def : value.toString();
	}

	/**
	 * 指定文字列を繰り返し表示する。
	 *
	 * @param str
	 *            文字列
	 * @param repeat
	 *            繰り返し回数
	 * @return 繰り返し回数分の文字列
	 */
	public static String repeat(String str, int repeat) {
		return org.apache.commons.lang.StringUtils.repeat(str, repeat);
	}

	/**
	 * staticのみのユーティリティのため、コンストラクタを封殺
	 */
	private StringUtils() {
	}

	/**
	 * 文字列内の改行を削除する.
	 *
	 * @param str
	 *            文字列
	 * @return 改行が削除された文字列
	 */
	public static String removeBreak(String str) {
		if (isNullOrEmpty(str)) {
			return str;
		}
		return str.replaceAll("\r\n", "").replaceAll("\n", "");
	}

	/**
	 * 文字列内の改行を半角スペースに変換する.
	 *
	 * @param str
	 *            文字列
	 * @return 改行を半角スペースに変換した文字列
	 */
	public static String breakToBlank(String str) {
		if (isNullOrEmpty(str)) {
			return str;
		}
		return str.replaceAll("\r\n", " ").replaceAll("\n", " ");
	}

	/**
	 * 文字列内の指定した正規表現にマッチする文字列を削除する.
	 *
	 * @param str
	 *            文字列
	 * @return 削除したい対象となる正規表現
	 */
	public static String removeString(String str, String target) {
		if (isNullOrEmpty(str)) {
			return str;
		}
		return str.replaceAll(target, "");
	}

	/**
	 * URL表記かどうかを取得する。
	 *
	 * @param str
	 *            チェックする文字列
	 * @return URL表記の場合は true。それ以外は false
	 */
	public static boolean isUrl(String str) {
		// 空文字の場合
		if (isNullOrEmpty(str)) {
			return false;
		}
		// URLかどうかを返す
		return MATCH_URL.matcher(str).matches();

	}

	/**
	 * 標準デリミタ文字カンマで分割する。
	 *
	 * @param str
	 * @return
	 */
	public static String[] split(String str) {
		return split(str, COMMA);
	}

	/**
	 * 指定デリミタ文字で分割する。
	 *
	 * @param str
	 * @param deli
	 * @return 空の配列を返す
	 */
	public static String[] split(String str, String deli) {
		if (StringUtils.isNullOrEmpty(str)) {
			return new String[] {};
		}
		return str.split(deli);
	}

	/**
	 * 圧縮ファイル名形式を取得する。
	 *
	 * @param fileName
	 *            ファイル名
	 * @return 圧縮ファイル名
	 */
	public static String getMinFileName(String fileName) {
		String suffix = getSuffix(fileName);
		if (suffix == null) {
			return null;
		}
		String name = fileName.replace("." + suffix, "");
		if (name == null) {
			return null;
		}
		return name + "-min." + suffix;
	}

	/**
	 * ファイル名から拡張子を返す。
	 *
	 * @param fileName
	 *            ファイル名
	 * @return ファイルの拡張子
	 */
	public static String getSuffix(String fileName) {
		if (fileName == null) {
			return null;
		}
		int point = fileName.lastIndexOf(".");
		if (point != -1) {
			return fileName.substring(point + 1);
		}
		return fileName;
	}

	/**
	 * サロゲートペアを考慮した文字数を取得する
	 *
	 * @param value
	 * @return 文字数
	 */
	public static int getCodePointCount(String... value) {
		int resultCount = 0;
		if (value == null || value.length == 0) {
			return resultCount;
		}
		for (String str : value) {
			if (str == null) {
				continue;
			}
			resultCount += str.codePointCount(0, str.length());
		}
		return resultCount;
	}

	/**
	 * 文字内が、改行・半角スペース・全角スペース・タブのみの場合、空にして返す
	 *
	 * @param String
	 * @return String
	 */
	public static String excludesWhitespace(String str) {
		if (str != null) {
			String replaceStr = str.replaceAll("[\t\r\n 　]", "");
			if (replaceStr.length() == 0) {
				return replaceStr;
			}
		}
		return str;
	}

	/**
	 * 引数で渡したDTOで文字内が、改行・半角スペース・全角スペース・タブのみの場合、空にする
	 *
	 * @param src
	 */
	public static void excludesWhitespace(Object src) {
		if (src == null) {
			return;
		}

		BeanDesc srcBeanDesc = BeanDescFactory.getBeanDesc(src.getClass());
		int size = srcBeanDesc.getPropertyDescSize();
		if (size == 0) {
			return;
		}

		for (int i = 0; i < size; i++) {
			PropertyDesc srcPropertyDesc = srcBeanDesc.getPropertyDesc(i);
			Object value = srcPropertyDesc.getValue(src);

			// String
			if (srcPropertyDesc.getPropertyType() == String.class && value != null) {
				// スペース除去
				value = excludesWhitespace(value.toString());
				srcPropertyDesc.setValue(src, value);
				continue;
			}

			// DTO
			if (srcPropertyDesc.getPropertyType().getSimpleName().endsWith("Dto") && value != null) {
				// 自身を呼び出す
				excludesWhitespace(value);
				continue;
			}

			// List
			if (srcPropertyDesc.getPropertyType() == List.class && value != null) {
				List<Object> valueList = (List<Object>) value;
				if (valueList.size() == 0) {
					continue;
				}
				for (Object obj : valueList) {
					if (obj.getClass() == String.class && obj != null) {
						// スペース除去
						obj = excludesWhitespace(value.toString());
					} else if (obj.getClass().getSimpleName().endsWith("Dto") && obj != null) {
						// 自分自身を呼び出す
						excludesWhitespace(obj);
					}
				}
			}

		}
	}

	/**
	 * 指定された文字列がIPアドレスフォーマットか判定する
	 *
	 * @param src
	 */
	public static boolean isIpv4Address(String str) {
		Pattern V4_FORMAT = Pattern
				.compile("((([01]?\\d{1,2})|(2[0-4]\\d)|(25[0-5]))\\.){3}(([01]?\\d{1,2})|(2[0-4]\\d)|(25[0-5]))");
		return V4_FORMAT.matcher(str).matches();
	}

	/**
	 * 入力文字列から制御文字を排除する
	 *
	 * @param src
	 */
	public static String replaceControlCharacter(String str) {

		if (isNullOrEmpty(str)) {
			return str;
		}

		return CONTROL_CHARACTER.matcher(str).replaceAll("");
	}

	/**
	 * 文字列ひらがなを文字列カタカナにして返す
	 *
	 * @param s
	 *            変換前文字列
	 * @return 変換後文字列
	 */
	public static String zenkakuHiraganaToZenkakuKatakana(String s) {

		StringBuffer sb = new StringBuffer(s);

		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);
			if (c >= 'ぁ' && c <= 'ん') {
				sb.setCharAt(i, (char) (c - 'ぁ' + 'ァ'));
			}
		}

		return sb.toString();

	}
}
