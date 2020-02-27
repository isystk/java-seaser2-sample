package com.isystk.sample.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.seasar.framework.beans.util.Beans;
import org.seasar.framework.beans.util.Copy;
import org.seasar.framework.beans.util.CreateAndCopy;

import com.isystk.sample.common.constants.Format;

/**
 * ビーンの（作成と）コピーを実施するためのユーティリティ。内部的にはSeaserプロジェクトのBeansを利用しているが、
 * その初期化を自動的に実施するためのラッパーでもある。
 */
public class BeanCopyUtil {

    /**
     * 一般的な日付のフォーマット
     */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(Format.DATE_FORINPUT);
    public static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat(Format.DATE_YYYYMM);
    static {
	DATE_FORMAT.setLenient(false);
	MONTH_FORMAT.setLenient(false);
    }

    /**
     * 手入力するためにスラッシュを省略した日付のフォーマット
     */
    public static final SimpleDateFormat MANUAL_INPUT_DATE_FORMAT = new SimpleDateFormat(Format.DATE_FORMANUALINPUT);
    static {
	MANUAL_INPUT_DATE_FORMAT.setLenient(false);
    }

    /**
     * プロパティをコピーするオブジェクトを作成します。
     * 
     * @param src コピー元
     * @param dest コピー先
     * @return コピー用のオブジェクト
     */
    public static Copy copy(Object src, Object dest) {
	return Beans.copy(src, dest).dateConverter(DATE_FORMAT.toPattern());
    }

    /**
     * JavaBeansやMapを作成しプロパティをコピーするオブジェクトを作成します。
     * 
     * @param <T>
     * 
     * @param destClass 作成対象クラス
     * @param src コピー元
     * @return 作成用のオブジェクト
     */
    public static <T> CreateAndCopy<T> createAndCopy(Class<T> destClass, Object src) {
	return Beans.createAndCopy(destClass, src).dateConverter(DATE_FORMAT.toPattern());
    }

    /**
     * 文字列のリストを数値のリストに変換する
     * 
     * @param from 文字列のリスト
     * @return 数値のリスト
     */
    public static List<Integer> ConvertToIntegerList(List<String> from) {
	if (from == null) {
	    return null;
	}

	List<Integer> result = new ArrayList<Integer>();
	for (String val : from) {
	    result.add(Integer.parseInt(val));
	}

	return result;
    }

    /**
     * 文字列の配列を数値の配列に変換する
     * 
     * @param from 文字列の配列
     * @return 数値の配列
     */
    public static Integer[] ConvertToIntegerArray(String[] from) {
	if (from == null) {
	    return null;
	}

	Integer[] result = new Integer[from.length];
	for (int i = 0; i < from.length; ++i) {
	    result[i] = Integer.parseInt(from[i]);
	}

	return result;
    }

    /**
     * 文字列のリストを日付のリストに変換する
     * 
     * @param from 文字列のリスト
     * @return 日付のリスト
     */
    public static List<Date> ConvertToDateList(List<String> from) {
	if (from == null) {
	    return null;
	}

	List<Date> result = new ArrayList<Date>();
	for (String val : from) {
	    try {
		result.add(DATE_FORMAT.parse(val));
	    } catch (ParseException e) {
		throw new RuntimeException(e);
	    }
	}

	return result;
    }

    /**
     * 文字列の配列を日付の配列に変換する
     * 
     * @param from 文字列の配列
     * @return 日付の配列
     */
    public static Date[] ConvertToDateArray(String[] from) {
	if (from == null) {
	    return null;
	}

	Date[] result = new Date[from.length];
	for (int i = 0; i < from.length; ++i) {
	    try {
		result[i] = DATE_FORMAT.parse(from[i]);
	    } catch (ParseException e) {
		throw new RuntimeException(e);
	    }
	}

	return result;
    }

    /**
     * 数値のリストを文字列のリストに変換する
     * 
     * @param from 数値のリスト
     * @return 文字列のリスト
     */
    public static List<String> ConvertToStringList(List<Integer> from) {
	if (from == null) {
	    return null;
	}

	List<String> result = new ArrayList<String>();
	for (Integer val : from) {
	    result.add(val.toString());
	}

	return result;
    }

    /**
     * 数値の配列を文字列の配列に変換する
     * 
     * @param from 数値の配列
     * @return 配列の配列
     */
    public static String[] ConvertToStringArray(Integer[] from) {
	if (from == null) {
	    return null;
	}

	String[] result = new String[from.length];
	for (int i = 0; i < from.length; ++i) {
	    result[i] = from[i].toString();
	}

	return result;
    }

}
