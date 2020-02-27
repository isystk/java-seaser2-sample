/**
 *
 */
package com.isystk.sample.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.isystk.sample.common.config.AppConfigNames;
import com.isystk.sample.common.config.Config;
import com.isystk.sample.common.constants.Format;
import com.isystk.sample.common.exception.SystemException;

/**
 * 日付に関するユーティリティ。<br>
 *
 * @author kaneko
 */
public final class DateUtils {

	/** mysqlの最小DATETIME */
	private static final Date MYSQL_MIN_DATETIME;
	/** mysqlの最大DATETIME */
	private static final Date MYSQL_MAX_DATETIME;
	static {
		try {
			MYSQL_MIN_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1000-01-01 00:00:00");
			MYSQL_MAX_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("9999-12-31 23:59:59");
		} catch (final ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/** ThreadLocalに保存するためのキー */
	private static final String THREAD_LOCAL_KEY = DateUtils.class.getName();

	/** 日付変換に使用するフォーマット */
	public static final String FORMAT = "yyyy/MM/dd";

	/** 日付変換に使用するフォーマット (スラッシュなし) */
	private static final String FORMAT_NO_SLASH = "yyyyMMdd";

	/** 日付変換に使用するフォーマット(ハイフン) */
	private static final String FORMAT_HYPHEN = "yyyy-MM-dd";

	/** 日付変換に使用するフォーマット：年月日 */
	public static final String FORMAT_FULLDATE = "yyyy年M月d日";

	/** 日付変換に使用するフォーマット：年月日（曜日） */
	private static final String FORMAT_FULLDATE_WITH_WEEK = "yyyy年M月d日(E)";

	/** 日付変換に使用するフォーマット：年月日 時：分 */
	private static final String FORMAT_FULLDATE_WITH_TIME = "yyyy年M月d日 HH:mm";

	/** 日付変換に使用するフォーマット：年月 */
	private static final String FORMAT_YEAR_MONTH = "yyyyMM";

	/** 日付変換に使用するフォーマット：年月 */
	private static final String FORMAT_YEAR_MONTH_SHORT = "yyyy年MM月";

	/** 日付変換に使用するフォーマット：年月 (スラッシュ) */
	private static final String FORMAT_YEAR_MONTH_SHORT_SLASH = "yyyy/MM";

	/** 日付変換に使用するフォーマット：月日 */
	private static final String FORMAT_MONTHDAY = "M/d";

	/** 日付変換に使用するフォーマット：月日（曜日） */
	public static final String FORMAT_MONTHDAY_WITH_WEEK = "M月d日(E)";

	/** 日付変換に使用するフォーマット：日（曜日） */
	private static final String FORMAT_ONLYDATE_WITH_WEEK = "d日(E)";

	/** 時刻変換に使用するフォーマット (分単位) */
	private static final String FORMAT_TIME = "HH:mm";

	/** 時刻変換に使用するフォーマット (秒単位) */
	private static final String FORMAT_SECOND_TIME = "HH:mm:ss";

	/** 時刻変換に使用するフォーマット (ミリ秒単位) */
	private static final String FORMAT_MILLISECOND_TIME = "HH:mm:ss:SSS";

	/** 和暦変換に使用するフォーマット(年号) */
	private static final String JP_CALENDER_ERAYEAR = "GGGGyyyy";

	/** 変換用定数 : 月 */
	private static final int CONVERT_YEAR_TO_MONTH = 12;

	/** 変換用定数 : 日 */
	private static final int CONVERT_MILLISECOND_TO_DATE = 24 * 60 * 60 * 1000;

	/** 変換用定数 : 時 */
	private static final int CONVERT_MILLISECOND_TO_HOUR = 60 * 60 * 1000;

	/** 変換用定数 : 分 */
	private static final int CONVERT_MILLISECOND_TO_MINUTE = 60 * 1000;

	/** 変換用定数 : 秒 */
	private static final int CONVERT_MILLISECOND_TO_SECOND = 1000;

	/** 昭和平成境界年 */
	public static final int SHOWA_HEISEI_BORDER_YEAR = 1989;

	/** 昭和平成境界日 */
	public static final int SHOWA_HEISEI_BORDER_DAY = 7;

	/**
	 * 指定した日付に、指定した日数を加算した日付を返します。<br>
	 *
	 * @param date
	 *            日付
	 * @param addDays
	 *            加算する日数
	 * @return Date 日付+加算する日数の日付
	 */
	public static Date addDate(final Date date, final int addDays) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, addDays);
		return cal.getTime();
	}

	/**
	 * 指定した日付に、指定した週数を加算した日付を返します。<br>
	 *
	 * @param date
	 *            日付
	 * @param addDays
	 *            加算する週数
	 * @return Date 日付+加算する週数の日付
	 */
	public static Date addWeek(final Date date, final int addWeeks) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, addWeeks * 7);
		return cal.getTime();
	}

	/**
	 * 指定した日付に、指定した時間を加算した日付を返します。<br>
	 *
	 * @param date
	 *            日付
	 * @param addHours
	 *            加算する時間
	 * @return Date 日付+加算する時間の日付
	 */
	public static Date addHour(final Date date, final int addHours) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, addHours);
		return cal.getTime();
	}

	/**
	 * 指定した日付に、指定した月数を加算した日付を返します。<br>
	 *
	 * @param date
	 *            日付
	 * @param month
	 *            加算する月数
	 * @return Date 日付+加算する月数した日付
	 */
	public static Date addMonth(final Date date, final int month) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);
		return cal.getTime();
	}

	/**
	 * 指定した日付に、指定した分数を加算した日付を返します。<br>
	 *
	 * @param date
	 *            日付
	 * @param minute
	 *            加算する分数
	 * @return Date 日付+加算する分数の日付
	 */
	public static Date addMinute(final Date date, final int minute) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minute);
		return cal.getTime();
	}

	/**
	 * 指定した日付に、指定した秒数を加算した日付を返します。<br>
	 *
	 * @param date
	 *            日付
	 * @param second
	 *            加算する秒数
	 * @return Date 日付+加算する秒数の日付
	 */
	public static Date addSecond(final Date date, final int second) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, second);
		return cal.getTime();
	}

	/**
	 * 指定した日付に、指定した年数を加算した日付を返します。<br>
	 *
	 * @param date
	 *            日付
	 * @param year
	 *            加算する年数
	 * @return Date 日付+加算する年数の日付
	 */
	public static Date addYear(final Date date, final int year) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, year);
		return cal.getTime();
	}

	/**
	 * 指定した日付に、指定した時間と分数を加算した日付を返します。<br>
	 *
	 * @param date
	 *            既存の日付
	 * @param hourStr
	 *            加算する時間
	 * @param minuteStr
	 *            加算する分数
	 * @return 日付 + 加算する時間 + 加算する分数 の日付
	 */
	public static Date addHourAndMinute(final Date date, final String hourStr, final String minuteStr) {
		if (date == null) {
			// 日付が入力されていなければ null を返す
			return null;
		} else {
			Date addDate = date;

			// 時を数字に変換して date に追加
			addDate = DateUtils.addHour(addDate, NumberUtil.toInteger(hourStr, 0));

			// 分を数字に変換して date に追加
			addDate = DateUtils.addMinute(addDate, NumberUtil.toInteger(minuteStr, 0));

			return addDate;
		}
	}

	/**
	 * <pre>
	 * 開始日の日付条件を調整します.
	 * </pre>
	 *
	 * @param startDate
	 *            調整前開始日付
	 * @return 調整後開始日付
	 */
	public static Date fixStartDate(final Date startDate) {

		if (startDate == null) {
			return null;
		}

		final Calendar cal = Calendar.getInstance();

		cal.setTime(startDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return new Date(cal.getTimeInMillis());

	}

	/**
	 * 年齢を返す
	 *
	 * @param birthday
	 *            誕生日
	 * @return 年齢
	 */
	public static Integer getAge(final Date birthday) {
		if (birthday == null) {
			return null;
		}

		final Calendar today = Calendar.getInstance();
		final Calendar birth = Calendar.getInstance();
		birth.setTime(birthday);

		if (today.before(birth)) {
			return -1;
		}

		if (today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
			return today.get(Calendar.YEAR) - birth.get(Calendar.YEAR) - 1;
		}
		return today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
	}

	/**
	 * <pre>
	 * 現在の日付に指定した時差(プロパティーファイル設定値)を追加したものを返す。
	 * </pre>
	 *
	 * @return 現在の日付に指定した時差を追加したもの
	 */
	public static Date getNow() {
		return DateUtils.addHour(new Date(), Config.getInteger(AppConfigNames.SERVER_TIME_DIFFERENCE));
	}

	/**
	 * <pre>
	 * Webコールのトークン有効期限チェック用の現在時刻を返す。
	 * </pre>
	 *
	 * @return 現在の日付に指定した時差を追加したもの
	 */
	public static Date getNowForWebCallTokenCheck() {
		return new Date();
	}

	/**
	 * <pre>
	 * もっとも過去の日付を返します.
	 * </pre>
	 *
	 * @param dates
	 *            日付達
	 * @return 最も過去の日付
	 */
	public static Date getBackwardDate(final Collection<Date> dates) {
		if (dates == null) {
			return null;
		}

		Date previous = null;
		for (final Date date : dates) {

			if (previous == null) {
				previous = date;
			} else if (date == null) {
				// nop;
			} else if (previous.after(date)) {
				previous = date;
			}

		}
		return previous;
	}

	/**
	 * <pre>
	 * もっとも過去の日付を返します.
	 * </pre>
	 *
	 * @param dates
	 *            日付達
	 * @return 最も過去の日付
	 */
	public static Date getBackwardDate(final Date... dates) {
		if (dates == null) {
			return null;
		}

		Date previous = null;
		for (final Date date : dates) {

			if (previous == null) {
				previous = date;
			} else if (date == null) {
				// nop;
			} else if (previous.after(date)) {
				previous = date;
			}

		}
		return previous;
	}

	/**
	 * システム日付を返します。<br>
	 * 取得した日時の時間を00:00:00にした日時を返します。<br>
	 *
	 * @param date
	 *            Date
	 * @return Date システム日付
	 */
	public static Date getDate(final Date date) {
		if (date == null) {
			return null;
		}

		final Calendar cal = Calendar.getInstance();
		// 指定日時取得
		cal.setTime(date);
		// 時分秒を０に設定
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		// ミリ秒も０に設定
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * システム日付を返します。<br>
	 * 取得した日時の時間を23:59:59にした日時を返します。<br>
	 *
	 * @param date
	 *            Date
	 * @return Date システム日付
	 */
	public static Date getDateMax(final Date date) {
		if (date == null) {
			return null;
		}

		final Calendar cal = Calendar.getInstance();
		// 指定日時取得
		cal.setTime(date);
		// 時を23に設定
		cal.set(Calendar.HOUR_OF_DAY, 23);
		// 分秒を59に設定
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);

		// ミリ秒も999に設定
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	/**
	 * <pre>
	 * もっとも未来の日付を返します.
	 * </pre>
	 *
	 * @param dates
	 *            日付達
	 * @return 最も未来の日付
	 */
	public static Date getFutureDate(final Collection<Date> dates) {
		if (dates == null) {
			return null;
		}

		Date latest = null;
		for (final Date date : dates) {

			if (latest == null) {
				latest = date;
			} else if (date == null) {
				// nop;
			} else if (latest.before(date)) {
				latest = date;
			}

		}
		return latest;
	}

	/**
	 * <pre>
	 * もっとも未来の日付を返します.
	 * </pre>
	 *
	 * @param dates
	 *            日付達
	 * @return 最も未来の日付
	 */
	public static Date getFutureDate(final Date... dates) {
		if (dates == null) {
			return null;
		}

		Date latest = null;
		for (final Date date : dates) {

			if (latest == null) {
				latest = date;
			} else if (date == null) {
				// nop;
			} else if (latest.before(date)) {
				latest = date;
			}

		}
		return latest;
	}

	/**
	 * 指定した日付の直前の金曜を返します。<br>
	 *
	 * @param date
	 *            日付
	 * @return Date 日付-減算する日数した日付
	 */
	public static Date getLastFriday(final Date date) {
		final int day_of_week_target = Calendar.FRIDAY;
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day_of_week_now = cal.get(Calendar.DAY_OF_WEEK);
		if (day_of_week_now < day_of_week_target) {
			day_of_week_now += 7;
		}
		return subDate(date, (day_of_week_now - day_of_week_target));
	}

	/**
	 * 指定した日付の直前の火曜を返します。<br>
	 *
	 * @param date
	 *            日付
	 * @return Date 日付-減算する日数した日付
	 */
	public static Date getLastTuesday(final Date date) {
		final int day_of_week_target = Calendar.TUESDAY;
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day_of_week_now = cal.get(Calendar.DAY_OF_WEEK);
		if (day_of_week_now < day_of_week_target) {
			day_of_week_now += 7;
		}
		return subDate(date, (day_of_week_now - day_of_week_target));
	}

	/**
	 * 指定した日付の直前の月曜を返します。<br>
	 *
	 * @param date
	 *            日付
	 * @return Date 日付-減算する日数した日付
	 */
	public static Date getLastMonday(final Date date) {
		final int day_of_week_target = Calendar.MONDAY;
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day_of_week_now = cal.get(Calendar.DAY_OF_WEEK);
		if (day_of_week_now < day_of_week_target) {
			day_of_week_now += 7;
		}
		return subDate(date, (day_of_week_now - day_of_week_target));
	}

	/**
	 * 指定した日付の直前の水曜を返します。<br>
	 *
	 * @param date
	 *            日付
	 * @return Date 日付-減算する日数した日付
	 */
	public static Date getLastWednesday(final Date date) {
		final int day_of_week_target = Calendar.WEDNESDAY;
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day_of_week_now = cal.get(Calendar.DAY_OF_WEEK);
		if (day_of_week_now < day_of_week_target) {
			day_of_week_now += 7;
		}
		return subDate(date, (day_of_week_now - day_of_week_target));
	}

	/**
	 * 指定した日付の直前の日曜を返します。<br>
	 *
	 * @param date
	 *            日付
	 * @return Date 日付-減算する日数した日付
	 */
	public static Date getLastSunday(final Date date) {
		final int day_of_week_target = Calendar.SUNDAY;
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day_of_week_now = cal.get(Calendar.DAY_OF_WEEK);
		if (day_of_week_now < day_of_week_target) {
			day_of_week_now += 7;
		}
		return subDate(date, (day_of_week_now - day_of_week_target));
	}

	/**
	 * 二つの日付を比較し、遅いほうを返します。<br>
	 *
	 * @param date1
	 *            日付
	 * @param date2
	 *            日付
	 * @return より遅い日付
	 */
	public static Date getMostLateDate(final Date date1, final Date date2) {
		long time1 = 0;
		long time2 = 0;
		if (date1 != null) {
			time1 = date1.getTime();
		}
		if (date2 != null) {
			time2 = date2.getTime();
		}
		if (time1 > time2) {
			return date1;
		} else {
			return date2;
		}
	}

	/**
	 * 指定した日付の1日後の日付を返します。<br>
	 *
	 * @param date
	 *            日付
	 * @return Date 日付+1日した日付
	 */
	public static Date getNextDate(final Date date) {
		if (date == null) {
			return null;
		}
		return addDate(date, 1);
	}

	/**
	 * 指定した日付の1日前の日付を返します。<br>
	 *
	 * @param date
	 *            日付
	 * @return Date 日付-1日した日付
	 */
	public static Date getPrevDate(final Date date) {
		return subDate(date, 1);
	}

	/**
	 * 指定したcurrDateから、指定したlimitDateまでの日数を返します。<br>
	 * （注意：時分秒は00:00:00に設定して日数を計算しています。）
	 *
	 * @param limitDate
	 *            期限日付
	 * @param currDate
	 *            指定日付
	 * @return int days 指定日付から期限日付までの日数
	 */
	public static int getRestDays(final Date limitDate, final Date currDate) {
		// パラメータがnullの場合
		if ((limitDate == null) || (currDate == null)) {
			throw new IllegalArgumentException();
		}
		// 指定日付が期限日付を過ぎている場合
		if (limitDate.before(currDate)) {
			return -1;
		}

		final Calendar cal = Calendar.getInstance();
		// limitDateの時間を00:00:00に設定
		cal.setTime(limitDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		final long limit = cal.getTimeInMillis();

		// currDateの時間を00:00:00に設定
		cal.setTime(currDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		final long curr = cal.getTimeInMillis();

		final long diff = limit - curr;
		// 日数に変換
		final int days = (int) (diff / (24 * 60 * 60 * 1000));

		return days;
	}

	/**
	 * システム日付を返します。<br>
	 * getSystemDateTime()から取得したシステム日時の時間を00:00:00にした日時を返します。<br>
	 *
	 * @return Date システム日付
	 */
	@Deprecated
	public static Date getSystemDate() {
		final Calendar cal = Calendar.getInstance();
		// システム日時取得
		cal.setTime(getSystemDateTime());
		// 時分秒を０に設定
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		// ミリ秒も０に設定
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * システム日時を返します。<br>
	 *
	 * @return Date システム日時
	 */
	public static Date getSystemDateTime() {
		return new Date();
	}

	/**
	 * <pre>
	 * スレッド日を取得します
	 * </pre>
	 *
	 * @return スレッド日
	 */
	public static Date getThreadDate() {
		return getDate(getThreadDateTime());
	}

	/**
	 * <pre>
	 * スレッド時刻を取得します.
	 * </pre>
	 *
	 * @return スレッド時刻
	 */
	public static Date getThreadDateTime() {
		return (Date) ThreadLocalUtils.getThreadLocalValue(THREAD_LOCAL_KEY);
	}

	/**
	 * <pre>
	 * 午前中かを判断します.
	 * </pre>
	 *
	 * @param date
	 *            日付
	 * @return 午前中のときtrue nullの時はfalse;
	 */
	public static boolean isAM(final Date date) {
		if (date == null) {
			return false;
		}
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		final int aorp = cal.get(Calendar.AM_PM);
		return Calendar.AM == aorp;
	}

	/**
	 * 比較日時が基準日時より新しい（もしくは後、遅い）場合trueを返します。<br>
	 *
	 * @param base
	 *            基準日
	 * @param compar
	 *            比較日
	 * @return 比較日時が基準日時より新しい（もしくは後、遅い）場合true
	 */
	public static boolean isFreshnessThanBaseDate(final Date base, final Date compar) {
		if ((base == null) || (compar == null)) {
			return false;
		}
		final long baseTime = base.getTime();
		final long comparTime = compar.getTime();
		if (baseTime < comparTime) {
			return true;
		}
		return false;
	}

	/**
	 * 比較日時が基準日時以降の場合trueを返します。<br>
	 *
	 * @param base
	 *            基準日時
	 * @param compare
	 *            比較日時
	 * @return 比較日時が基準日時以降の場合true
	 */
	public static boolean isFreshnessSinceBaseDate(final Date base, final Date compare) {
		if ((base == null) || (compare == null)) {
			return false;
		}
		return (isFreshnessThanBaseDate(base, compare)) || (compare.equals(base));
	}

	/**
	 * 二つの日付を比較し、同じ日かを判断します.<br>
	 *
	 * @param date1
	 *            日付
	 * @param date2
	 *            日付
	 * @return 同じ月の時true 違う月の時false;
	 */
	public static boolean isSameDate(final Date date1, final Date date2) {
		if ((date1 == null) || (date2 == null)) {
			return false;
		}
		final Calendar cal1 = Calendar.getInstance();
		final Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		return cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE);
	}

	/**
	 * 二つの日付を比較し、同じ月かを判断します.<br>
	 *
	 * @param date1
	 *            日付
	 * @param date2
	 *            日付
	 * @return 同じ月の時true 違う月の時false;
	 */
	public static boolean isSameMonth(final Date date1, final Date date2) {
		if ((date1 == null) || (date2 == null)) {
			return false;
		}
		final Calendar cal1 = Calendar.getInstance();
		final Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		return cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
	}

	/**
	 * 二つの日付を比較し、同じ年かを判断します.<br>
	 *
	 * @param date1
	 *            日付
	 * @param date2
	 *            日付
	 * @return 同じ年の時true 違う年の時false;
	 */
	public static boolean isSameYear(final Date date1, final Date date2) {
		if ((date1 == null) || (date2 == null)) {
			return false;
		}
		final Calendar cal1 = Calendar.getInstance();
		final Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
	}

	/**
	 * 二つの日付を比較し、同じ年月日かを判断します.<br>
	 * 時間は関係なし
	 *
	 * @param date1
	 *            日付
	 * @param date2
	 *            日付
	 * @return 同じ月の時true 違う月の時false;
	 */
	public static boolean isSameYearMonthDate(final Date date1, final Date date2) {
		return isSameYear(date1, date2) && isSameMonth(date1, date2) && isSameDate(date1, date2);
	}

	/**
	 * 指定された日付が今日（システム年月日）かどうか
	 *
	 * @param model
	 * @return
	 */
	public static boolean isToday(final Date date) {
		return DateUtils.isSameYearMonthDate(date, DateUtils.getSystemDate());
	}

	/**
	 * <pre>
	 * 当日からnカ月間の土曜、日曜の日付リストを取得します。
	 * </pre>
	 *
	 * @return 当日からnカ月間の土曜、日曜の日付リスト
	 */
	public static List<Date> getWeekEndDateListForMonth(int month) {

		List<Date> dateList = new ArrayList<Date>();
		Date today = getDate(getNow());
		final Calendar cal = Calendar.getInstance();

		// 当日から1カ月後までの日数を取得する
		int dateCount = DateUtils.getRestDays(addMonth(DateUtils.getNow(), month), today);
		for (int i = 0; i <= dateCount; i++) {
			// 当日から加算した日付を展開
			cal.setTime(addDate(today, i));
			// 曜日を取得
			int dotw = cal.get(Calendar.DAY_OF_WEEK);
			// 土日ならリストに追加
			if ((Calendar.SUNDAY == dotw) || (Calendar.SATURDAY == dotw)) {
				dateList.add(addDate(today, i));
			}
		}
		return dateList;
	}

	/**
	 * <pre>
	 * 当日からnカ月間の土曜、日曜、祝日の日付リストを取得します。
	 * </pre>
	 *
	 * @return 当日からnカ月間の土曜、日曜、祝日の日付リスト
	 */
	public static List<Date> getWeekEndAndHolidayListForMonth(int month) {

		List<Date> dateList = new ArrayList<Date>();
		Date today = getDate(getNow());
		final Calendar cal = Calendar.getInstance();

		// 当日から1カ月後までの日数を取得する
		int dateCount = DateUtils.getRestDays(addMonth(DateUtils.getNow(), month), today);
		// 休日Setを取得しておく
		Set<Date> holidaySet = HolidayUtil.getHolidaySet();
		for (int i = 0; i <= dateCount; i++) {
			// 当日から加算した日付を展開
			Date current = addDate(today, i);
			cal.setTime(current);
			// 曜日を取得
			int dotw = cal.get(Calendar.DAY_OF_WEEK);
			// 土日ならリストに追加
			if ((Calendar.SUNDAY == dotw) || (Calendar.SATURDAY == dotw)) {
				dateList.add(current);
				// 祝日ならリストに追加
			} else if (holidaySet.contains(current)) {
				dateList.add(current);
			}
		}
		return dateList;
	}

	/**
	 * <pre>
	 * 火曜か金曜かを判断します.
	 * </pre>
	 *
	 * @param date
	 *            日付
	 * @return 火曜か金曜のときtrue nullのときはfalse;
	 */
	public static boolean isTueOrFri(final Date date) {
		if (date == null) {
			return false;
		}
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		final int dotw = cal.get(Calendar.DAY_OF_WEEK);
		return (Calendar.TUESDAY == dotw) || (Calendar.FRIDAY == dotw);
	}

	/**
	 * <pre>
	 * 土曜か日曜かを判断します.
	 * </pre>
	 *
	 * @param date
	 *            日付
	 * @return 土曜か日曜のときtrue nullのときはfalse;
	 */
	public static boolean isSatOrSun(final Date date) {
		if (date == null) {
			return false;
		}
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		final int dotw = cal.get(Calendar.DAY_OF_WEEK);
		return (Calendar.SATURDAY == dotw) || (Calendar.SUNDAY == dotw);
	}

	/**
	 * <pre>
	 * その月の1日を返します。
	 * </pre>
	 *
	 * @param date
	 *            日付
	 * @return その月の1日
	 */
	public static Date monthFirst(final Date date) {
		if (date == null) {
			return null;
		}

		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * <pre>
	 * その月の末日を取得します.
	 * </pre>
	 *
	 * @param date
	 *            日付
	 * @return その月の末日
	 */
	public static Date monthLast(final Date date) {
		if (date == null) {
			return null;
		}

		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * <pre>
	 * スレッド時刻を設定します.
	 * </pre>
	 *
	 * @param datetime
	 *            スレッド時刻
	 */
	public static void setThreadDateTime(final Date datetime) {
		ThreadLocalUtils.setThreadLocalValue(THREAD_LOCAL_KEY, datetime);
	}

	/**
	 * 指定した日付に、指定した日数を減算した日付を返します。<br>
	 *
	 * @param date
	 *            日付
	 * @param subDays
	 *            減算する日数
	 * @return Date 日付-減算する日数した日付
	 */
	public static Date subDate(final Date date, final int subDays) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -subDays);
		return cal.getTime();
	}

	/**
	 * システム日時から指定の時間数を減算した日時を返します。
	 *
	 * @param hours
	 *            時間数
	 * @return システム日時から指定の時間数を減算した日時を返します
	 */
	public static Date subHourFromSystemDateTime(final int hours) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(getSystemDateTime());
		cal.add(Calendar.HOUR, -hours);
		return cal.getTime();
	}

	/**
	 * 指定日時から指定の時間数を減算した日時を返します。
	 *
	 * @param hours
	 *            時間数
	 * @return 指定日時から指定の時間数を減算した日時を返します
	 */
	public static Date subHourFromDateTime(final Date date, final int hours) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, -hours);
		return cal.getTime();
	}

	/**
	 * 年・月からDateに変換する。<br>
	 * ※日については、現在日を元に変換されます。
	 *
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return Dateに変換したもの。失敗した場合はnull。
	 */
	public static Date toDate(final String year, final String month) {
		return toDate(year, month, String.valueOf(Calendar.getInstance().get(Calendar.DATE)));
	}

	/**
	 * 年・月・日からDateに変換する。
	 *
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return Dateに変換したもの。失敗した場合はnull。
	 */
	public static Date toDate(final String year, final String month, final String day) {
		final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		try {
			sdf.setLenient(false);
			return sdf.parse(year + "/" + month + "/" + day);
		} catch (final ParseException e) {
			// parseに失敗したら黙ってnull
			return null;
		}
	}

	/**
	 * Dateから年/月/日に変換する。
	 *
	 * @param date
	 *            Date型の日付
	 * @return yyyy/MM/ddに変換したもの。失敗した場合は空。
	 */
	public static String toDateFormat(final Date date) {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
			return sdf.format(date);
		} catch (final Exception e) {
			// 失敗したら黙って空文字
			return "";
		}
	}

	/**
	 * Dateから年-月-日に変換する。
	 *
	 * @param date
	 *            Date型の日付
	 * @return yyyy-MM-ddに変換したもの。失敗した場合は空。
	 */
	public static String toDateFormatHyphen(final Date date) {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_HYPHEN);
			return sdf.format(date);
		} catch (final Exception e) {
			// 失敗したら黙って空文字
			return "";
		}
	}

	/**
	 * Dateから時間に変換する。
	 *
	 * @param date
	 *            Date型の日付
	 * @param format
	 *            フォーマット
	 * @return 文字列に変換したもの。失敗した場合は空。
	 */
	public static String toFormatString(final Date date, final String format) {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (final Exception e) {
			// 失敗したら黙って空文字
			return "";
		}
	}

	/**
	 * 日付文字列をスラッシュなし形式(yyyyMMdd)へ変換する
	 *
	 * @param str
	 *            変換する文字列
	 * @return 変換した日付。失敗した場合は null
	 */
	public static Date toDateSlash(final String str) {

		final SimpleDateFormat format = new SimpleDateFormat(FORMAT);

		try {

			// 日付を厳密処理する
			format.setLenient(false);

			// 変換した日付を返す
			return format.parse(str);

		} catch (final ParseException e) {

			// 失敗した場合は null
			return null;

		}

	}

	/**
	 * 日付文字列を指定したフォーマットで日付に変換する
	 *
	 * @param str
	 *            変換する文字列
	 * @return 変換した日付。失敗した場合は null
	 */
	public static Date toDateFormat(final String str, String formatStr) {

		final SimpleDateFormat format = new SimpleDateFormat(formatStr);

		try {

			// 日付を厳密処理する
			format.setLenient(false);

			// 変換した日付を返す
			return format.parse(str);

		} catch (final ParseException e) {

			// 失敗した場合は null
			return null;

		}

	}

	/**
	 * 日付文字列をスラッシュなし形式(yyyyMMdd)へ変換する
	 *
	 * @param str
	 *            変換する文字列
	 * @return 変換した日付。失敗した場合は null
	 */
	public static Date[] toDateSlashArray(final String[] strs) {

		if (strs == null) {
			return null;
		}

		Date[] dates = new Date[strs.length];
		for (int i = 0; i < strs.length; i++) {
			dates[i] = toDateSlash(strs[i]);
		}

		return dates;
	}

	/**
	 * 日付文字列をスラッシュなし形式(yyyyMMdd)へ変換する
	 *
	 * @param str
	 *            変換する文字列
	 * @return 変換した日付。失敗した場合は null
	 */
	public static Date toDateNoSlash(final String str) {

		final SimpleDateFormat format = new SimpleDateFormat(FORMAT_NO_SLASH);

		try {

			// 日付を厳密処理する
			format.setLenient(false);

			// 変換した日付を返す
			return format.parse(str);

		} catch (final ParseException e) {

			// 失敗した場合は null
			return null;

		}

	}

	/**
	 * 日付をスラッシュなし文字列形式(yyyyMMdd)へ変換する
	 *
	 * @param date
	 *            変換する日付
	 * @return 変換した日付文字列。失敗した場合は空文字
	 */
	public static String toDateFormatNoSlash(final Date date) {

		final SimpleDateFormat format = new SimpleDateFormat(FORMAT_NO_SLASH);

		try {

			// 日付を厳密処理する
			format.setLenient(false);

			// 変換した日付を返す
			return format.format(date);

		} catch (final Throwable e) {

			// 失敗した場合は空文字
			return "";

		}

	}

	/**
	 * 偽造日付
	 *
	 * @param today
	 * @param taretDate
	 * @return
	 */
	public static Date toFakeDate(final Date taretDate) {
		return toFakeDate(getThreadDate(), taretDate);
	}

	/**
	 * 偽造日付
	 *
	 * @param today
	 * @param taretDate
	 * @return
	 */
	public static Date toFakeDate(final Date today, final Date taretDate) {
		return subDate(today, (getRestDays(today, taretDate) % 10));
	}

	/**
	 * Dateから年月日 時：分に変換する。
	 *
	 * @param date
	 *            Date型の日付
	 * @return yyyy年M月d日 時：分に変換したもの。失敗した場合は空。
	 */
	public static String toFullDateFormatWithTime(final Date date) {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_FULLDATE_WITH_TIME);
			return sdf.format(date);
		} catch (final Exception e) {
			// 失敗したら黙って空文字
			return "";
		}
	}

	/**
	 * Dateから年月に変換する。
	 *
	 * @param date
	 *            Date型の日付
	 * @return yyyyMMに変換したもの。失敗した場合は空。
	 */
	public static String toFormatYearMonth(final Date date) {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YEAR_MONTH);
			return sdf.format(date);
		} catch (final Exception e) {
			// 失敗したら黙って空文字
			return "";
		}
	}

	/**
	 * Dateから年月日に変換する。
	 *
	 * @param date
	 *            Date型の日付
	 * @return yyyy年M月d日 に変換したもの。失敗した場合は空。
	 */
	public static String toFullDateFormat(final Date date) {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_FULLDATE);
			return sdf.format(date);
		} catch (final Exception e) {
			// 失敗したら黙って空文字
			return "";
		}
	}

	/**
	 * Dateから年月日 時：分に変換する。
	 *
	 * @param date
	 *            Date型の日付
	 * @return yy/MMに変換したもの。失敗した場合は空。
	 */
	public static String toFormatYearMonthShort(final Date date) {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YEAR_MONTH_SHORT);
			return sdf.format(date);
		} catch (final Exception e) {
			// 失敗したら黙って空文字
			return "";
		}
	}

	/**
	 * Dateから年/月に変換する。
	 *
	 * @param date
	 *            Date型の日付
	 * @return yyyy/MMに変換したもの。失敗した場合は空。
	 */
	public static String toFormatYearMonthShortSlash(final Date date) {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YEAR_MONTH_SHORT_SLASH);
			return sdf.format(date);
		} catch (final Exception e) {
			// 失敗したら黙って空文字
			return "";
		}
	}

	/**
	 * Dateから年月日(曜日)に変換する。
	 *
	 * @param date
	 *            Date型の日付
	 * @return yyyy年M月d日(E)に変換したもの。失敗した場合は空。
	 */
	public static String toFullDateFormatWithWeek(final Date date) {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_FULLDATE_WITH_WEEK, Locale.JAPANESE);
			return sdf.format(date);
		} catch (final Exception e) {
			// 失敗したら黙って空文字
			return "";
		}
	}

	/**
	 * Dateから月日に変換する。
	 *
	 * @param date
	 *            Date型の日付
	 * @return M/dに変換したもの。失敗した場合は空。
	 */
	public static String toMonthDayFormat(final Date date) {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_MONTHDAY);
			return sdf.format(date);
		} catch (final Exception e) {
			// 失敗したら黙って空文字
			return "";
		}
	}

	/**
	 * Dateから月日(曜日)に変換する。
	 *
	 * @param date
	 *            Date型の日付
	 * @return M月d日(E)に変換したもの。失敗した場合は空。
	 */
	public static String toMonthDayFormatWithWeek(final Date date) {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_MONTHDAY_WITH_WEEK, Locale.JAPANESE);
			return sdf.format(date);
		} catch (final Exception e) {
			// 失敗したら黙って空文字
			return "";
		}
	}

	/**
	 * Dateから日(曜日)に変換する。
	 *
	 * @param date
	 *            Date型の日付
	 * @return d日(E)に変換したもの。失敗した場合は空。
	 */
	public static String toOnlyDayFormatWithWeek(final Date date) {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_ONLYDATE_WITH_WEEK);
			return sdf.format(date);
		} catch (final Exception e) {
			// 失敗したら黙って空文字
			return "";
		}
	}

	/**
	 * 時・分からDateに変換する。
	 *
	 * @param hour
	 *            時
	 * @param minutes
	 *            分
	 * @return Dateに変換したもの。失敗した場合はnull。
	 */
	public static Date toTime(final String hour, final String minutes) {
		final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIME);
		try {
			sdf.setLenient(false);
			return sdf.parse(hour + ":" + minutes);
		} catch (final ParseException e) {
			// parseに失敗したら黙ってnull
			return null;
		}
	}

	/**
	 * 指定された日時情報から時分の表示形式へ変換する。
	 *
	 * @param date
	 *            日時情報
	 * @return 時分の表示形式
	 */
	public static String toTimeFormat(final Date date) {

		try {

			// 時分秒の表示形式へ変換する
			return new SimpleDateFormat(FORMAT_TIME).format(date);

		} catch (final Exception e) {

			// 失敗時は null を返す
			return null;

		}

	}

	/**
	 * 指定された日時情報から時分秒の表示形式へ変換する。
	 *
	 * @param date
	 *            日時情報
	 * @return 時分秒の表示形式
	 */
	public static String toSecondTimeString(final Date date) {

		try {

			// 時分秒の表示形式へ変換する
			return new SimpleDateFormat(FORMAT_SECOND_TIME).format(date);

		} catch (final Exception e) {

			// 失敗時は null を返す
			return null;

		}

	}

	/**
	 * 指定された日時情報から時分秒ミリ秒の表示形式へ変換する。
	 *
	 * @param date
	 *            日時情報
	 * @return 時分秒の表示形式
	 */
	public static String toMilliSecondTimeString(final Date date) {

		try {

			// 時分秒の表示形式へ変換する
			return new SimpleDateFormat(FORMAT_MILLISECOND_TIME).format(date);

		} catch (final Exception e) {

			// 失敗時は null を返す
			return null;

		}

	}

	/**
	 * 年・月・日からDateに変換する。
	 *
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return Dateに変換したもの。失敗した場合はnull。
	 */
	public static Date xoDate(final String year, final String month, final String day) {
		final Calendar cal = Calendar.getInstance();
		final Integer intYear = StringUtils.integerValue(year);
		final Integer intMonth = StringUtils.integerValue(month);
		final Integer intDay = StringUtils.integerValue(day);
		if ((intYear == null) || (intMonth == null) || (intDay == null)) {
			return null;
		}
		cal.clear();
		cal.setLenient(false);
		cal.set(Calendar.YEAR, intYear);
		cal.set(Calendar.MONTH, intMonth - 1);
		cal.set(Calendar.DAY_OF_MONTH, intDay);
		try {
			return cal.getTime();
		} catch (final Exception e) {
			return null;
		}
	}

	/**
	 * ISOMERS8601の形式へ変換する。
	 *
	 * @param date
	 *            日
	 * @param hour
	 *            時
	 * @param minuite
	 *            分
	 * @return ISOMERS8601形式の文字列
	 */
	public static String toISOString(final Date date, final Integer hour, final Integer minuite) {
		if (date != null && hour != null && minuite != null) {
			final Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, minuite);
			DateFormat df = new SimpleDateFormat(Format.FORMAT_ISO_8601);
			try {
				return df.format(cal.getTime());
			} catch (final Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * staticのみのユーティリティのため、コンストラクタを封殺
	 */
	private DateUtils() {
	}

	/**
	 * <pre>
	 * 排他チェックを行います.
	 *
	 * 【チェック事項】
	 * ・baseDateがdatesのどれよりも未来
	 * </pre>
	 *
	 * @param baseDate
	 *            編集開始時に取得した時刻
	 * @param dates
	 *            現在のDBの時刻
	 * @return baseDateががdatesのどれよりも未来のときtrue
	 */
	public static boolean exclusionDate(final Date baseDate, final Date... dates) {

		if (baseDate == null) {
			return false;
		}

		if ((dates == null) || (dates.length <= 0)) {
			return false;
		}

		final Date latest = latestDate(dates);
		if (latest == null) {
			return true;
		}

		if (latest.compareTo(baseDate) > 0) {
			return false;
		}

		return true;
	}

	/**
	 * <pre>
	 * 未来日を取得します.
	 * </pre>
	 *
	 * @param dates
	 *            比較する日付郡
	 * @return 最も未来日
	 */
	public static Date latestDate(final Date... dates) {
		Date latest = null;
		for (final Date date : dates) {
			if (date == null) {
				continue;
			}
			if (latest == null) {
				latest = date;
				continue;
			}
			if (!latest.after(date)) {
				latest = date;
			}
		}
		return latest;
	}

	public static Date max(final Date d1, final Date d2) {
		if ((d1 == null) && (d2 == null)) {
			return null;
		} else if ((d1 == null) || (d2 == null)) {
			return (Date) (d1 != null ? d1 : d2).clone();
		} else {
			return new Date(Math.max(d1.getTime(), d2.getTime()));
		}
	}

	public static Date min(final Date d1, final Date d2) {
		if ((d1 == null) && (d2 == null)) {
			return null;
		} else if ((d1 == null) || (d2 == null)) {
			return (Date) (d1 != null ? d1 : d2).clone();
		} else {
			return new Date(Math.min(d1.getTime(), d2.getTime()));
		}
	}

	/**
	 * @param date
	 *            日付
	 * @return MYSQLの最大値で丸めたもの
	 */
	public static Date mysqlMax(final Date date) {
		return date == null ? null : min(date, MYSQL_MAX_DATETIME);
	}

	/**
	 * dateがnullなら最大値を返す。
	 *
	 * @param date
	 * @return
	 */
	public static Date nullToMaxDate(final Date date) {
		return date == null ? MYSQL_MAX_DATETIME : date;
	}

	/**
	 * dateがnullなら最小値を返す。
	 *
	 * @param date
	 * @return
	 */
	public static Date nullToMinDate(final Date date) {
		return date == null ? MYSQL_MIN_DATETIME : date;
	}

	/**
	 * 2つの日付の差を求めます。
	 *
	 * @param date1
	 *            日付
	 * @param date2
	 *            日付
	 * @return 2つの日付の差（時間）
	 */
	public static int differenceDate(final Date date1, final Date date2) {
		final long datetime1 = date1.getTime();
		final long datetime2 = date2.getTime();
		final long one_date_time = 1000 * 60 * 60;
		final long diffDays = (datetime2 - datetime1) / one_date_time;
		return (int) diffDays;
	}

	/**
	 * 指定された日付操作種別に対する2つの日付の差分値を取得する。<br>
	 *
	 * @param type
	 *            差分対象日付操作種別 (Calendar定数)
	 * @param baseDate
	 *            比較元の日付
	 * @param targetDate
	 *            比較対象日付
	 * @return 指定された日付操作種別に対する2つの日付の差分値。(baseDate - targetDate)を返す。
	 * @deprecated intの最大値を超えるケースで正確な値が返却されない不具合あり compareDiffValueを使用する
	 */
	public static int compare(final int type, final Date baseDate, final Date targetDate) {

		// nullなら例外
		if ((baseDate == null) || (targetDate == null)) {

			throw new IllegalArgumentException();

		}

		final Calendar baseCal; // 比較元日付
		final Calendar targetCal; // 比較対象日付
		final long divideValue; // 除算する値

		// 種類別処理
		switch (type) {

		// 年
		case Calendar.YEAR:

			// 比較元日付と比較対象日付を作成する
			baseCal = Calendar.getInstance();
			targetCal = Calendar.getInstance();
			baseCal.setTime(baseDate);
			targetCal.setTime(targetDate);

			// 年の差分を返す
			return baseCal.get(Calendar.YEAR) - targetCal.get(Calendar.YEAR);

		// 月
		case Calendar.MONTH:

			// 比較元日付と比較対象日付を作成する
			baseCal = Calendar.getInstance();
			targetCal = Calendar.getInstance();
			baseCal.setTime(baseDate);
			targetCal.setTime(targetDate);

			// 月の差分を返す
			return (baseCal.get(Calendar.YEAR) * CONVERT_YEAR_TO_MONTH + (baseCal.get(Calendar.MONTH) + 1))
					- (targetCal.get(Calendar.YEAR) * CONVERT_YEAR_TO_MONTH + (targetCal.get(Calendar.MONTH) + 1));

		// 日
		case Calendar.DATE:

			// 日の算出用除算値を設定する
			divideValue = CONVERT_MILLISECOND_TO_DATE;
			break;

		// 時
		case Calendar.HOUR:
		case Calendar.HOUR_OF_DAY:

			// 時の算出用除算値を設定する
			divideValue = CONVERT_MILLISECOND_TO_HOUR;
			break;

		// 分
		case Calendar.MINUTE:

			// 分の算出用除算値を設定する
			divideValue = CONVERT_MILLISECOND_TO_MINUTE;
			break;

		// 秒
		case Calendar.SECOND:

			// 秒の算出用除算値を設定する
			divideValue = CONVERT_MILLISECOND_TO_SECOND;
			break;

		// ミリ秒
		case Calendar.MILLISECOND:

			// ミリ秒の算出用除算値を設定する
			divideValue = 1L;
			break;

		// その他 (エラー)
		default:

			throw new IllegalArgumentException("指定された日付操作種別には対応していません [type = " + type + "]");

		}

		// 差分結果を返す
		return (int) ((baseDate.getTime() - targetDate.getTime()) / divideValue);

	}

	/**
	 * 指定された日付操作種別に対する2つの日付の差分値を取得する。<br>
	 *
	 * @param type
	 *            差分対象日付操作種別 (Calendar定数)
	 * @param baseDate
	 *            比較元の日付
	 * @param targetDate
	 *            比較対象日付
	 * @return 指定された日付操作種別に対する2つの日付の差分値。(baseDate - targetDate)を返す。
	 */
	public static long compareDiffValue(final int type, final Date baseDate, final Date targetDate) {

		// nullなら例外
		if ((baseDate == null) || (targetDate == null)) {

			throw new IllegalArgumentException();

		}

		final Calendar baseCal; // 比較元日付
		final Calendar targetCal; // 比較対象日付
		final long divideValue; // 除算する値

		// 種類別処理
		switch (type) {

		// 年
		case Calendar.YEAR:

			// 比較元日付と比較対象日付を作成する
			baseCal = Calendar.getInstance();
			targetCal = Calendar.getInstance();
			baseCal.setTime(baseDate);
			targetCal.setTime(targetDate);

			// 年の差分を返す
			return baseCal.get(Calendar.YEAR) - targetCal.get(Calendar.YEAR);

		// 月
		case Calendar.MONTH:

			// 比較元日付と比較対象日付を作成する
			baseCal = Calendar.getInstance();
			targetCal = Calendar.getInstance();
			baseCal.setTime(baseDate);
			targetCal.setTime(targetDate);

			// 月の差分を返す
			return (baseCal.get(Calendar.YEAR) * CONVERT_YEAR_TO_MONTH + (baseCal.get(Calendar.MONTH) + 1))
					- (targetCal.get(Calendar.YEAR) * CONVERT_YEAR_TO_MONTH + (targetCal.get(Calendar.MONTH) + 1));

		// 日
		case Calendar.DATE:

			// 日の算出用除算値を設定する
			divideValue = CONVERT_MILLISECOND_TO_DATE;
			break;

		// 時
		case Calendar.HOUR:
		case Calendar.HOUR_OF_DAY:

			// 時の算出用除算値を設定する
			divideValue = CONVERT_MILLISECOND_TO_HOUR;
			break;

		// 分
		case Calendar.MINUTE:

			// 分の算出用除算値を設定する
			divideValue = CONVERT_MILLISECOND_TO_MINUTE;
			break;

		// 秒
		case Calendar.SECOND:

			// 秒の算出用除算値を設定する
			divideValue = CONVERT_MILLISECOND_TO_SECOND;
			break;

		// ミリ秒
		case Calendar.MILLISECOND:

			// ミリ秒の算出用除算値を設定する
			divideValue = 1L;
			break;

		// その他 (エラー)
		default:

			throw new IllegalArgumentException("指定された日付操作種別には対応していません [type = " + type + "]");

		}

		// 差分結果を返す
		return ((baseDate.getTime() - targetDate.getTime()) / divideValue);

	}

	/**
	 * 指定された日付操作種別に対する2つの日付の差分値を取得する。<br>
	 *
	 * @param type
	 *            差分対象日付操作種別 (Calendar定数)
	 * @param baseDate
	 *            比較元の日付
	 * @param targetDate
	 *            比較対象日付
	 * @return 指定された日付操作種別に対する2つの日付の差分値。(baseDate - targetDate)を返す。
	 */
	public static int compare(final int type, final long baseDate, final long targetDate) {

		return compare(type, new Date(baseDate), new Date(targetDate));

	}

	public static Date parse(final String date, final String format) {
		if ((date == null) || "".endsWith(date)) {
			return null;
		}
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (final ParseException e) {
			throw new SystemException(e);
		}
	}

	/**
	 * 和暦変換する。
	 *
	 * @param year
	 *            年
	 * @param dayOfMonth
	 *            日
	 * @return 和暦（年号+年）
	 */
	public static String convertJPCalendar(int year) {
		return convertJPCalendar(year, 1);
	}

	/**
	 * 和暦変換する。
	 *
	 * @param year
	 *            年
	 * @param dayOfMonth
	 *            日
	 * @return 和暦（年号+年）
	 */
	public static String convertJPCalendar(int year, int dayOfMonth) {
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		SimpleDateFormat format = new SimpleDateFormat(JP_CALENDER_ERAYEAR, new Locale("ja", "JP", "JP"));
		return format.format(cal.getTime());
	}

	/**
	 * フェア開催時間を表示形式で返却します。<br>
	 * (単一部制：xx:xx ～ xx:xx、複数部制：x部制 xx:xx～/xx:xx～)
	 *
	 * @param pluralFlg
	 *            複数部制フラグ
	 * @param times
	 *            日付
	 * @return
	 */
	public static String getOpenTime(boolean pluralFlg, Integer[][] times) {
		return getOpenTime(pluralFlg, true, true, times);
	}

	/**
	 * フェア開催時間を表示形式で返却します。(単一部制：xx:xx ～ xx:xx、複数部制：x部制(※) xx:xx～/xx:xx～)<br>
	 * ※dispPlural:trueの場合に表示
	 *
	 * @param pluralFlg
	 *            複数部制フラグ
	 * @param dispPlural
	 *            複数部制表示フラグ（true:表示、false:非表示）
	 * @param dispTime
	 *            開催時間表示フラグ（true:表示、false:非表示）
	 * @param times
	 *            日付
	 * @return
	 */
	public static String getOpenTime(boolean pluralFlg, boolean dispPlural, boolean dispTime, Integer[][] times) {

		// 複数部制
		if (pluralFlg) {
			// 部数
			int pluralCount = 0;
			if (times[0][0] != null || times[0][1] != null || times[0][2] != null || times[0][3] != null) {
				pluralCount = 1;
			}
			if (times[1][0] != null || times[1][1] != null || times[1][2] != null || times[1][3] != null) {
				pluralCount = 2;
			}
			if (times[2][0] != null || times[2][1] != null || times[2][2] != null || times[2][3] != null) {
				pluralCount = 3;
			}
			if (times[3][0] != null || times[3][1] != null || times[3][2] != null || times[3][3] != null) {
				pluralCount = 4;
			}
			if (times[4][0] != null || times[4][1] != null || times[4][2] != null || times[4][3] != null) {
				pluralCount = 5;
			}

			// 開催時間
			StringBuilder openTime = new StringBuilder();
			if (dispPlural) {
				openTime.append(pluralCount + "部制 ");
			}
			if (dispTime) {
				if (pluralCount >= 1) {
					openTime.append(StringUtils.insertHead(StringUtils.stringValue(times[0][0], "--"), "0", 2) + ":"
							+ StringUtils.insertHead(StringUtils.stringValue(times[0][1], "--"), "0", 2) + "～");
				}
				if (pluralCount >= 2) {
					openTime.append("/");
					openTime.append(StringUtils.insertHead(StringUtils.stringValue(times[1][0], "--"), "0", 2) + ":"
							+ StringUtils.insertHead(StringUtils.stringValue(times[1][1], "--"), "0", 2) + "～");
				}
				if (pluralCount >= 3) {
					openTime.append("/");
					openTime.append(StringUtils.insertHead(StringUtils.stringValue(times[2][0], "--"), "0", 2) + ":"
							+ StringUtils.insertHead(StringUtils.stringValue(times[2][1], "--"), "0", 2) + "～");
				}
				if (pluralCount >= 4) {
					openTime.append("/");
					openTime.append(StringUtils.insertHead(StringUtils.stringValue(times[3][0], "--"), "0", 2) + ":"
							+ StringUtils.insertHead(StringUtils.stringValue(times[3][1], "--"), "0", 2) + "～");
				}
				if (pluralCount == 5) {
					openTime.append("/");
					openTime.append(StringUtils.insertHead(StringUtils.stringValue(times[4][0], "--"), "0", 2) + ":"
							+ StringUtils.insertHead(StringUtils.stringValue(times[4][1], "--"), "0", 2) + "～");
				}
			}
			return openTime.toString();
		} else {
			StringBuilder openTime = new StringBuilder();
			if (dispTime) {
				openTime.append(StringUtils.insertHead(StringUtils.stringValue(times[0][0], "--"), "0", 2) + ":"
						+ StringUtils.insertHead(StringUtils.stringValue(times[0][1], "--"), "0", 2) + " ～ "
						+ StringUtils.insertHead(StringUtils.stringValue(times[0][2], "--"), "0", 2) + ":"
						+ StringUtils.insertHead(StringUtils.stringValue(times[0][3], "--"), "0", 2));
			}
			return openTime.toString();
		}

	}

	/**
	 * 開始日付と終了日付を指定し、期間の日付を取得します。
	 *
	 * @param startDate
	 * @param endDate
	 * @return 期間の日付
	 */
	public static List<Date> getBetweenDateList(Date startDate, Date endDate) {
		List<Date> resultList = new ArrayList<Date>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);

		while (calendar.getTime().before(endDate)) {
			Date date = calendar.getTime();
			resultList.add(date);
			calendar.add(Calendar.DATE, 1);
		}
		return resultList;
	}

	/**
	 * 指定した日付が、指定した日にちかどうかを判定します<br>
	 *
	 * @param date
	 *            日付(Date)
	 * @param day
	 *            日にち(Int)
	 * @return 指定した日付の場合true, nullの場合false
	 */
	public static boolean isSameDay(final Date date, final int day) {
		if (date == null || day == 0) {
			return false;
		}
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DATE) == day;
	}

	/**
	 * 指定した日付の直前の指定したシステム日付を返します。<br>
	 * 例：("2016/6/10","16")を入れると"2016/5/16"のシステム日付が返ります<br>
	 * 例：("2016/9/16","16")を入れると"2016/8/16"のシステム日付が返ります<br>
	 * 例：("2016/9/10","31")を入れると"2016/9/1"のシステム日付が返ります<br>
	 *
	 * @param date
	 *            日付(Date)
	 * @param day
	 *            日にち(Int)
	 * @return Date 日付-減算する日数した日付
	 */
	public static Date getDecideLastDay(final Date date, final int day) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.get(Calendar.DATE) > day) {
			return addDate(monthFirst(date), day - 1);
		} else {
			return addDate(monthFirst(addMonth(date, -1)), day - 1);
		}
	}

	/**
	 * 曜日を取得します.
	 *
	 * @param date
	 *            日付
	 * @return 曜日
	 */
	public static int getDayOfTheWeek(Date date) {
		Date day = DateUtils.getDate(date);
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * comparisonDateに対して targetDateが過去の日時かどうか
	 */
	public static boolean isBefore(Date comparisonDate, Date targetDate) {
		if (comparisonDate.compareTo(targetDate) > 0 || comparisonDate.compareTo(targetDate) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * comparisonDateに対して targetDateが未来の日時かどうか
	 */
	public static boolean isAfter(Date comparisonDate, Date targetDate) {
		if (comparisonDate.compareTo(targetDate) < 0 || comparisonDate.compareTo(targetDate) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 日付けから時間を取得
	 *
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 日付けから分を取得
	 *
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MINUTE);
	}
}
