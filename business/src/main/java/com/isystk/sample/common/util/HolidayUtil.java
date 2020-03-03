package com.isystk.sample.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.isystk.sample.common.exception.SystemException;

import net.sf.json.JSONObject;

/**
 * 休日を取得するためのユーティリティ。 「holiday.dat」ファイルに休日が記述されており、そのデータを取得する。 日付はDate型となっている。
 * 
 * @author iseyoshitaka
 * 
 */
public class HolidayUtil {
	private static Set<Date> holidaySet = null;
	private static String holidayJsonStr = null;

	/**
	 * 休日のSetを取得する
	 * 
	 * @return 休日のSet
	 */
	public static Set<Date> getHolidaySet() {
		if (holidaySet == null) {
			holidaySet = createHolidaySet();
		}

		return holidaySet;
	}

	/**
	 * 休日SetのJSON形式文字列を取得する
	 * 
	 * @return 休日SetのJSON形式文字列
	 */
	public static String getHolidayJsonStr() {
		if (holidayJsonStr == null) {
			holidayJsonStr = createHolidayJsonStr();
		}

		return holidayJsonStr;
	}

	/**
	 * 休日のSetを作成する
	 * 
	 * @return 休日のSet
	 */
	private static Set<Date> createHolidaySet() {
		Set<Date> result = new HashSet<Date>();

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(HolidayUtil.class.getClassLoader().getResourceAsStream("holiday.dat")));

		try {
			while (reader.ready()) {
				String holiday = reader.readLine().trim();
				if (!StringUtils.isNullOrEmpty(holiday)) {
					result.add(DateUtils.toDateSlash(holiday));
				}
			}
		} catch (Exception e) {
			throw new SystemException("休日ファイル(holiday.dat)の読み込みに失敗しました", e);
		}

		return result;
	}

	/**
	 * 休日SetのJSON形式文字列を作成する
	 * 
	 * @return 休日SetのJSON形式文字列
	 */
	private static String createHolidayJsonStr() {
		JSONObject json = new JSONObject();

		Set<String> holidaySetStr = new HashSet<String>();
		for (Date holiday : getHolidaySet()) {
			holidaySetStr.add(DateUtils.toDateFormat(holiday));
		}

		json.element("holidays", holidaySetStr);

		return json.toString();
	}

}
