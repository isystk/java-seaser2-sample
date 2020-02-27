/*
 * ValidateUtil.java 2011/03/28 mnakamura
 */
package com.isystk.sample.web.common.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.UrlValidator;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.seasar.framework.util.StringUtil;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.isystk.sample.common.config.AppMessageNames;
import com.isystk.sample.common.config.Message;
import com.isystk.sample.common.util.BeanCopyUtil;
import com.isystk.sample.common.util.DateUtils;
import com.isystk.sample.common.util.FileUtil;

/**
 * @author mnakamura
 *
 */
public class ValidateUtil {

	/**
	 * メッセージリソースにメッセージを追加
	 *
	 * @param messages
	 *            メッセージ
	 * @param key
	 *            キー
	 * @param params
	 *            置換文字列
	 */
	public static void add(ActionMessages messages, String key, Object... params) {
		add(ActionMessages.GLOBAL_MESSAGE, messages, key, params);
	}

	/**
	 * メッセージリソースにnullを追加（propertyNameに指定された名前の）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param messages
	 *            メッセージ
	 */
	public static void add(String propertyName, ActionMessages messages) {
		add(propertyName, messages, null, new Object[] {});
	}

	/**
	 * メッセージリソースにメッセージを追加
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param messages
	 *            メッセージ
	 * @param key
	 *            キー
	 * @param params
	 *            置換文字列
	 */
	public static void add(String propertyName, ActionMessages messages, String key, Object... params) {
		if (params == null || params.length == 0) {
			messages.add(propertyName, new ActionMessage(key));
		} else if (params.length > 0) {
			messages.add(propertyName, new ActionMessage(key, params));
		}
	}

	/**
	 * メッセージリソースにメッセージを追加
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param messages
	 *            メッセージ
	 * @param messageStr
	 *            メッセージ文字列
	 * @param isResourece
	 *            リソースに直接メッセージ文字列を追加するかどうか
	 */
	public static void add(String propertyName, ActionMessages messages, String messageStr, boolean isResourece) {
		messages.add(propertyName, new ActionMessage(messageStr, isResourece));
	}

	/**
	 * 必須か（値が含まれているか）どうかをチェック
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean required(String value) {
		if (value == null) {
			return false;
		}

		if (value.length() == 0) {
			return false;
		}

		value = value.replaceAll("[\t\r\n 　]", "");
		if (value.length() == 0) {
			return false;
		}

		return true;
	}

	/**
	 * 必須か（値が含まれているか）どうかをチェック。（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void requiredChk(String propertyName, String value, ActionMessages messages, String itemName) {
		if (required(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_REQUIRED.key, itemName);
		}
	}

	/**
	 * フラグ（0 or 1）かどうかをチェック。
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean flg(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		try {
			int i = Integer.parseInt(value);
			return (i == 0 || i == 1) ? true : false;

		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * フラグ（0 or 1）かどうかをチェック（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param key
	 *            キー
	 * @param params
	 *            置換文字列
	 */
	public static void flgChk(String propertyName, String value, ActionMessages messages, String key,
			Object... params) {
		if (flg(value) == false) {
			add(propertyName, messages, key, params);
		}
	}

	/**
	 * 最短文字数チェック
	 *
	 * @param value
	 *            値
	 * @param min
	 *            最小文字数
	 * @return true / false
	 */
	public static boolean minLength(String value, int min) {
		return length(value, min, Integer.MAX_VALUE);
	}

	/**
	 * 最短文字数チェック（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param min
	 *            最小文字数
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void minLengthChk(String propertyName, String value, int min, ActionMessages messages,
			String itemName) {
		if (minLength(value, min) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_MINLENGTH.key, itemName, min);
		}
	}

	/**
	 * 最長文字数チェック
	 *
	 * @param value
	 *            値
	 * @param max
	 *            最大文字数
	 * @return true / false
	 */
	public static boolean maxLength(String value, int max) {
		return length(value, 0, max);
	}

	/**
	 * 最長文字数チェック（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param max
	 *            最大文字数
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void maxLengthChk(String propertyName, String value, int max, ActionMessages messages,
			String itemName) {
		if (maxLength(value, max) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_MAXLENGTH.key, itemName, max);
		}
	}

	/**
	 * 文字数チェック
	 *
	 * @param value
	 *            値
	 * @param min
	 *            最小文字数
	 * @param max
	 *            最大文字数
	 * @return true / false
	 */
	public static boolean length(String value, int min, int max) {
		if (StringUtils.isEmpty(value))
			return true;
		// js側で改行は2文字でカウントさせるようにした
		// value = value.replaceAll("\r\n", "\n");
		final int count = value.codePointCount(0, value.length());

		return count >= min && count <= max;
	}

	/**
	 * 文字数チェック（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param min
	 *            最小文字数
	 * @param max
	 *            最大文字数
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void lengthChk(String propertyName, String value, int min, int max, ActionMessages messages,
			String itemName) {
		if (length(value, min, max) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_LENGTH.key, itemName, min, max);
		}
	}

	/**
	 * 最小数値チェック
	 *
	 * @param value
	 *            数値
	 * @param min
	 *            最小数値
	 * @return true / false
	 */
	public static boolean minValue(Integer value, int min) {
		if (value == null)
			return true;

		if (min <= value) {
			return true;
		}
		return false;
	}

	/**
	 * 最小数値チェック（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            数値
	 * @param min
	 *            最小数値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void minValueChk(String propertyName, Integer value, int min, ActionMessages messages,
			String itemName) {
		if (minValue(value, min) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_MINVALUE.key, itemName, min);
		}
	}

	/**
	 * 最小数値チェック
	 *
	 * @param value
	 *            数値
	 * @param min
	 *            最小数値
	 * @return true / false
	 */
	public static boolean minValue(BigDecimal value, double min) {
		if (value == null)
			return true;

		if (min <= value.doubleValue()) {
			return true;
		}
		return false;
	}

	/**
	 * 最小数値チェック（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            数値
	 * @param min
	 *            最小数値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void minValueChk(String propertyName, BigDecimal value, double min, ActionMessages messages,
			String itemName) {
		if (minValue(value, min) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_MINVALUE.key, itemName, min);
		}
	}

	/**
	 * 最大数値チェック
	 *
	 * @param value
	 *            数値
	 * @param min
	 *            最大数値
	 * @return true / false
	 */
	public static boolean maxValue(Integer value, int max) {
		if (value == null)
			return true;

		if (value <= max) {
			return true;
		}
		return false;
	}

	/**
	 * 最大数値チェック（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            数値
	 * @param min
	 *            最大数値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void maxValueChk(String propertyName, Integer value, int max, ActionMessages messages,
			String itemName) {
		if (maxValue(value, max) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_MAXVALUE.key, itemName, max);
		}
	}

	/**
	 * 最大数値チェック
	 *
	 * @param value
	 *            数値
	 * @param min
	 *            最大数値
	 * @return true / false
	 */
	public static boolean maxValue(BigDecimal value, double max) {
		if (value == null)
			return true;

		if (value.doubleValue() <= max) {
			return true;
		}
		return false;
	}

	/**
	 * 最大数値チェック（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            数値
	 * @param max
	 *            最大数値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void maxValueChk(String propertyName, BigDecimal value, double max, ActionMessages messages,
			String itemName) {
		if (maxValue(value, max) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_MAXVALUE.key, itemName, max);
		}
	}

	/**
	 * 少数値フォーマットチェック
	 *
	 * @param val
	 *            数値
	 * @param num1
	 *            整数部分の桁数
	 * @param num2
	 *            少数部分の桁数
	 * @return
	 */
	public static boolean decimalValue(String val, int num1, int num2) {
		String regex = "^(\\d{" + num1 + "})(\\.\\d{" + num2 + "})?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(val);
		return matcher.find();
	}

	/**
	 * 少数値フォーマットチェック（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            数値
	 * @param num1
	 *            整数部分の桁数
	 * @param num2
	 *            少数部分の桁数
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void decimalValueChk(String propertyName, String value, int num1, int num2, ActionMessages messages,
			String itemName) {
		if (decimalValue(value, num1, num2) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_DECIMALVALUE.key, itemName, num1, num2);
		}
	}

	/**
	 * 使用可能な文字かどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesAbleChar(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		return true; // 現状は使用不能な文字はなしの想定
	}

	/**
	 * 使用可能な文字かどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void matchesAbleCharChk(String propertyName, String value, ActionMessages messages, String itemName) {
		if (matchesAbleChar(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_ENABLECHAR.key, itemName);
		}
	}

	/**
	 * 半角文字（1バイト文字）にマッチするかどうか（半角カナはマルチバイトなのでNG）
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesHalfChar(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		return value.length() == value.getBytes().length;
	}

	/**
	 * 半角文字（1バイト文字）にマッチするかどうか（半角カナはマルチバイトなのでNG）（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void matchesHalfCharChk(String propertyName, String value, ActionMessages messages, String itemName) {
		if (matchesHalfChar(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_HALFCHAR.key, itemName);
		}
	}

	/**
	 * 半角英数にマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesHalf(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		return value.matches("[0-9a-zA-Z]+");
	}

	/**
	 * 半角英数にマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void matchesHalfChk(String propertyName, String value, ActionMessages messages, String itemName) {
		if (matchesHalf(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_ISHANKAKUEISU.key, itemName);
		}
	}

	/**
	 * 半角英数、アンダースコア、ハイフン、ドットにマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesResourceKey(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		return value.matches("[\\w\\.\\-]+");
	}

	/**
	 * 半角英数、アンダースコア、ハイフン、ドットにマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param key
	 *            キー
	 * @param params
	 *            置換文字列
	 */
	public static void matchesResourceKeyChk(String propertyName, String value, ActionMessages messages, String key,
			Object... params) {
		if (matchesResourceKey(value) == false) {
			add(propertyName, messages, key, params);
		}
	}

	/**
	 * 半角正の整数（0始まりNG）にマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesHalfPositiveInteger(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		return value.matches("0|[1-9][0-9]*");
	}

	/**
	 * 半角正の整数（0始まりNG）にマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void matchesHalfPositiveIntegerChk(String propertyName, String value, ActionMessages messages,
			String itemName) {
		if (matchesHalfPositiveInteger(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_HALFPOSITIVEINTEGER.key, itemName);
		}
	}

	/**
	 * 半角整数（0始まりNG・正負ともOK）にマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesHalfInteger(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		return value.matches("\\-?(0|[1-9][0-9]*)");
	}

	/**
	 * 半角整数（0始まりNG・正負ともOK）にマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void matchesHalfIntegerChk(String propertyName, String value, ActionMessages messages,
			String itemName) {
		if (matchesHalfInteger(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_ISHANKAKUSU.key, itemName);
		}
	}

	/**
	 * 半角正の小数（整数部分の0埋めNG）にマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesHalfPositiveDecimal(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		return value.matches("(0|[1-9][0-9]*)(|\\.[0-9]+)");
	}

	/**
	 * 半角正の小数（整数部分の0埋めNG）にマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void matchesHalfPositiveDecimalChk(String propertyName, String value, ActionMessages messages,
			String itemName) {
		if (matchesHalfPositiveDecimal(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_HALFPOSITIVEDECIMAL.key, itemName);
		}
	}

	/**
	 * 半角小数（整数部分の0埋めNG・正負ともOK）にマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesHalfDecimal(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		return value.matches("\\-?(0|[1-9][0-9]*)(|\\.[0-9]+)");
	}

	/**
	 * 半角小数（整数部分の0埋めNG・正負ともOK）にマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void matchesHalfDecimalChk(String propertyName, String value, ActionMessages messages,
			String itemName) {
		if (matchesHalfDecimal(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_HALFDECIMAL.key, itemName);
		}
	}

	/**
	 * 半角数字（0始まりOK、0～9だけで構成された値）にマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesHalfNumber(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		return value.matches("[0-9]+");
	}

	/**
	 * 半角数字（0始まりOK、0～9だけで構成された値）にマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void matchesHalfNumberChk(String propertyName, String value, ActionMessages messages,
			String itemName) {
		if (matchesHalfNumber(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_ISHANKAKUSU.key, itemName);
		}
	}

	private static final Pattern PTN_DIGIT_OR_ALPHA_OR_PUNCT = Pattern.compile("[\\p{Digit}\\p{Alnum}\\p{Punct}\\s]*");

	/**
	 * 半角英数記号および空白にマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesHalfNumberOrAlphaOrPunct(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		return PTN_DIGIT_OR_ALPHA_OR_PUNCT.matcher(value).matches();
	}

	/**
	 * 半角英数記号および空白にマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void matchesHalfNumberOrAlphaOrPunctChk(String propertyName, String value, ActionMessages messages,
			String itemName) {
		if (matchesHalfNumberOrAlphaOrPunct(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_ISHANKAKUEISUKIGOU.key, itemName);
		}
	}

	// 半角文字(半角英数/記号/ｶﾅ)
	private static final Pattern PTN_HALFWIDTH = Pattern.compile("^[ -~｡-ﾟ]+$");

	// 全角文字(半角英数/記号/ｶﾅ 以外の文字すべて)
	private static final Pattern PTN_FULLWIDTH = Pattern.compile("^[^ -~｡-ﾟ]+$");

	/**
	 * 半角文字(半角英数/記号/ｶﾅ)にマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesHalfWidth(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		return PTN_HALFWIDTH.matcher(value).matches();
	}

	/**
	 * 全角文字にマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesFullWidth(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		return PTN_FULLWIDTH.matcher(value).matches();
	}

	/**
	 * 全角文字にマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void matchesFullWidthChk(String propertyName, String value, ActionMessages messages,
			String itemName) {
		if (matchesFullWidth(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_ZENKAKUTYPE.key, itemName);
		}
	}

	/**
	 * 全角カタカナにマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesFullWidthKatakana(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		int len = value.length();
		for (int i = 0; i < len; i++) {
			char ch = value.charAt(i);
			if (ch >= 'ァ' && ch <= 'ヾ' || ch == '　') {
			} else {
				return false;
			}
		}

		return true;
	}

	/**
	 * 全角カタカナにマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void matchesFullWidthKatakanaChk(String propertyName, String value, ActionMessages messages,
			String itemName) {
		if (matchesFullWidthKatakana(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_ISFULLWIDTHKATAKANA.key, itemName);
		}
	}

	protected static boolean isYearLimitOver(Date parse) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtils.getNow());
		cal.add(Calendar.YEAR, 1000);

		return cal.getTime().compareTo(parse) > 0;
	}

	/**
	 * 年月の形式（YYYY/MM/）にマッチするかどうか また、本日より千年以上先ならバリデートを失敗させる（ＤＢに入らないことなどがあるため）。
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesMonth(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		try {
			Date parse = BeanCopyUtil.MONTH_FORMAT.parse(value);

			return isYearLimitOver(parse);

		} catch (ParseException ex) {
			return false;
		}
	}

	/**
	 * 日付の形式（YYYY/MM/DD）にマッチするかどうか また、本日より千年以上先ならバリデートを失敗させる（ＤＢに入らないことなどがあるため）。
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesDate(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		try {
			Date parse = BeanCopyUtil.DATE_FORMAT.parse(value);

			return isYearLimitOver(parse);

		} catch (ParseException ex) {
			return false;
		}
	}

	/**
	 * 日付の形式（YYYY/MM/DD）にマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void matchesDateChk(String propertyName, String value, ActionMessages messages, String itemName) {
		if ((matchesDate(value) == false)
				|| ((StringUtils.isNotEmpty(value)) && (!value.matches("\\d{4}/\\d{1,2}/\\d{1,2}")))) {
			add(propertyName, messages, AppMessageNames.ERRORS_DATE.key, itemName);
		}
	}

	public static void matchesMonthChk(String propertyName, String value, ActionMessages messages, String itemName) {
		if ((matchesMonth(value) == false)
				|| ((StringUtils.isNotEmpty(value)) && (!value.matches("\\d{4}/\\d{1,2}")))) {
			add(propertyName, messages, AppMessageNames.ERRORS_DATE.key, itemName);
		}
	}

	private static final SimpleDateFormat SIMPLE_MANUAL_ENTRY_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	static {
		SIMPLE_MANUAL_ENTRY_DATE_FORMAT.setLenient(false);
	}

	/**
	 * 手入力の日付の形式（yyyyMMdd）にマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesManualEntryDate(String value) {
		if (StringUtils.isEmpty(value)) {
			return true;
		}

		if (value.length() != 8) {
			return false;
		}

		try {
			SIMPLE_MANUAL_ENTRY_DATE_FORMAT.parse(value);
			return true;
		} catch (ParseException ex) {
			return false;
		}
	}

	/**
	 * 手入力の日付の形式（yyyyMMdd）にマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void matchesManualEntryDateChk(String propertyName, String value, ActionMessages messages,
			String itemName) {
		if (matchesManualEntryDate(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_MANUALENTRYDATE.key, itemName);
		}
	}

	/**
	 * 日付の大小比較を行い正しいかどうかをチェック。 日付の形式として正しいかどうかはチェックしないので、別途チェックを実施すること
	 *
	 * @param fromDate
	 *            From日
	 * @param toDate
	 *            To日
	 * @return true / false
	 */
	public static boolean dateRange(String fromDate, String toDate) {
		if (StringUtils.isEmpty(fromDate) || StringUtils.isEmpty(toDate))
			return true;

		try {
			Date to = BeanCopyUtil.DATE_FORMAT.parse(fromDate);
			Date from = BeanCopyUtil.DATE_FORMAT.parse(toDate);

			return from.compareTo(to) >= 0;
		} catch (ParseException ex) { // パース失敗はtrueとなることに注意
			return true;
		}
	}

	/**
	 * 日付の大小比較を行い正しいかどうかをチェック（エラーメッセージも同時に追加）
	 * 日付の形式として正しいかどうかはチェックしないので、別途チェックを実施すること
	 *
	 * @param fromDatePropertyName
	 *            From日プロパティ名
	 * @param toDatePropertyName
	 *            To日プロパティ名
	 * @param fromDate
	 *            From日
	 * @param toDate
	 *            To日
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void dateRangeChk(String fromDatePropertyName, String toDatePropertyName, String fromDate,
			String toDate, ActionMessages messages, String itemName) {
		if (dateRange(fromDate, toDate) == false) {
			add(fromDatePropertyName, messages, AppMessageNames.ERRORS_DATERANGE.key, itemName);
		}
	}

	public static void monthRangeChk(String fromDatePropertyName, String toDatePropertyName, String fromMonth,
			String toMonth, ActionMessages messages, String itemName) {
		dateRangeChk(fromDatePropertyName, toDatePropertyName, fromMonth + "/01", toMonth + "/01", messages, itemName);
	}

	/**
	 * 手入力の日付の大小比較を行い正しいかどうかをチェック。 手入力の日付の形式として正しいかどうかはチェックしないので、別途チェックを実施すること
	 *
	 * @param fromDate
	 *            From日
	 * @param toDate
	 *            To日
	 * @return true / false
	 */
	public static boolean manualEntryDateRange(String fromDate, String toDate) {
		if (StringUtils.isEmpty(fromDate) || StringUtils.isEmpty(toDate))
			return true;

		try {
			Date to = BeanCopyUtil.MANUAL_INPUT_DATE_FORMAT.parse(fromDate);
			Date from = BeanCopyUtil.MANUAL_INPUT_DATE_FORMAT.parse(toDate);

			return from.compareTo(to) >= 0;
		} catch (ParseException ex) { // パース失敗はtrueとなることに注意
			return true;
		}
	}

	/**
	 * 手入力の日付の大小比較を行い正しいかどうかをチェック（エラーメッセージも同時に追加）
	 * 手入力の日付の形式として正しいかどうかはチェックしないので、別途チェックを実施すること
	 *
	 * @param fromDatePropertyName
	 *            From日プロパティ名
	 * @param toDatePropertyName
	 *            To日プロパティ名
	 * @param fromDate
	 *            From日
	 * @param toDate
	 *            To日
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void manualEntryDateChk(String fromDatePropertyName, String toDatePropertyName, String fromDate,
			String toDate, ActionMessages messages, String itemName) {
		if (manualEntryDateRange(fromDate, toDate) == false) {
			add(fromDatePropertyName, messages, AppMessageNames.ERRORS_DATERANGE.key, itemName);
		}
	}

	/**
	 * 指定の日付が現在の日付よりも、より前の日付「<」かどうか。 日付の形式として正しいかどうかはチェックしないので、別途チェックを実施すること
	 *
	 * @param targetDate
	 *            対象日
	 * @return true / false
	 */
	public static boolean dateLtNow(String targetDate) {
		if (StringUtils.isEmpty(targetDate))
			return true;

		try {
			Date target = BeanCopyUtil.DATE_FORMAT.parse(targetDate);
			Date now = DateUtils.getDate(DateUtils.getNow());
			return target.compareTo(now) < 0;
		} catch (ParseException ex) { // パース失敗はtrueとなることに注意
			return true;
		}
	}

	/**
	 * 指定の日付が現在の日付よりも、より前の日付「<」かどうか。（エラーメッセージも同時に追加）
	 * 日付の形式として正しいかどうかはチェックしないので、別途チェックを実施すること
	 *
	 * @param targetDatePropertyName
	 *            対象日プロパティ名
	 * @param target
	 *            対象日
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void dateLtNowChk(String targetDatePropertyName, String targetDate, ActionMessages messages,
			String itemName) {
		if (dateLtNow(targetDate) == false) {
			add(targetDatePropertyName, messages, AppMessageNames.ERRORS_DATELTNOW.key, itemName);
		}
	}

	/**
	 * 指定の日付が現在の日付よりも、以前の日付「<=」かどうか。 日付の形式として正しいかどうかはチェックしないので、別途チェックを実施すること
	 *
	 * @param targetDate
	 *            対象日
	 * @return true / false
	 */
	public static boolean dateLeNow(String targetDate) {
		if (StringUtils.isEmpty(targetDate))
			return true;

		try {
			Date target = BeanCopyUtil.DATE_FORMAT.parse(targetDate);
			Date now = DateUtils.getDate(DateUtils.getNow());
			return target.compareTo(now) <= 0;
		} catch (ParseException ex) { // パース失敗はtrueとなることに注意
			return true;
		}
	}

	/**
	 * 指定の日付が現在の日付よりも、以前の日付「<=」かどうか。 （エラーメッセージも同時に追加）
	 * 日付の形式として正しいかどうかはチェックしないので、別途チェックを実施すること
	 *
	 * @param targetDatePropertyName
	 *            対象日プロパティ名
	 * @param target
	 *            対象日
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void dateLeNowChk(String targetDatePropertyName, String targetDate, ActionMessages messages,
			String itemName) {
		if (dateLeNow(targetDate) == false) {
			add(targetDatePropertyName, messages, AppMessageNames.ERRORS_DATELENOW.key, itemName);
		}
	}

	/**
	 * 指定の日付が現在の日付よりも、より後の日付「>」かどうか。 日付の形式として正しいかどうかはチェックしないので、別途チェックを実施すること
	 *
	 * @param targetDate
	 *            対象日
	 * @return true / false
	 */
	public static boolean dateGtNow(String targetDate) {
		if (StringUtils.isEmpty(targetDate))
			return true;

		try {
			Date target = BeanCopyUtil.DATE_FORMAT.parse(targetDate);
			Date now = DateUtils.getDate(DateUtils.getNow());
			return target.compareTo(now) > 0;
		} catch (ParseException ex) { // パース失敗はtrueとなることに注意
			return true;
		}
	}

	/**
	 * 指定の日付が現在の日付よりも、より後の日付「>」かどうか。 （エラーメッセージも同時に追加）
	 * 日付の形式として正しいかどうかはチェックしないので、別途チェックを実施すること
	 *
	 * @param targetDatePropertyName
	 *            対象日プロパティ名
	 * @param target
	 *            対象日
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void dateGtNowChk(String targetDatePropertyName, String targetDate, ActionMessages messages,
			String itemName) {
		if (dateGtNow(targetDate) == false) {
			add(targetDatePropertyName, messages, AppMessageNames.ERRORS_DATEGTNOW.key, itemName);
		}
	}

	/**
	 * 指定の日付が現在の日付よりも、以降の日付「>=」かどうか。 日付の形式として正しいかどうかはチェックしないので、別途チェックを実施すること
	 *
	 * @param targetDate
	 *            対象日
	 * @return true / false
	 */
	public static boolean dateGeNow(String targetDate) {
		if (StringUtils.isEmpty(targetDate))
			return true;

		try {
			Date target = BeanCopyUtil.DATE_FORMAT.parse(targetDate);
			Date now = DateUtils.getDate(DateUtils.getNow());
			return target.compareTo(now) >= 0;
		} catch (ParseException ex) { // パース失敗はtrueとなることに注意
			return true;
		}
	}

	/**
	 * 指定の日付が現在の日付よりも、以降の日付「>=」かどうか。 （エラーメッセージも同時に追加）
	 * 日付の形式として正しいかどうかはチェックしないので、別途チェックを実施すること
	 *
	 * @param targetDatePropertyName
	 *            対象日プロパティ名
	 * @param target
	 *            対象日
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void dateGeNowChk(String targetDatePropertyName, String targetDate, ActionMessages messages,
			String itemName) {
		if (dateGeNow(targetDate) == false) {
			add(targetDatePropertyName, messages, AppMessageNames.ERRORS_DATEGENOW.key, itemName);
		}
	}

	/**
	 * マルチボックスなどでリストにひとつ以上選択されているか
	 *
	 * @param value
	 *            値のリスト
	 * @return true / false
	 */
	public static boolean choseOneOrMore(String[] values) {
		if (values == null)
			return true;

		return values.length != 0;
	}

	/**
	 * マルチボックスなどでリストにひとつ以上選択されているか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値のリスト
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void choseOneOrMoreChk(String propertyName, String[] values, ActionMessages messages,
			String itemName) {
		if (choseOneOrMore(values) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_CHOSEONEORMORE.key, itemName);
		}
	}

	/**
	 * メールアドレスにマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesEmail(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		int indexOfAtmark = value.indexOf("@", 0);

		// メールアドレスに "@" が含まれているかの確認
		if (indexOfAtmark == -1) {
			return false;
		} else if (value.indexOf("@", indexOfAtmark + 1) != -1) {
			// @が複数存在する場合
			return false;
		} else if (indexOfAtmark == 0 || indexOfAtmark + 1 == value.length()) {
			// @が最初や最後に現れた場合
			return false;
		} else if (isWrongSymbolContain(value)) {
			// メールアドレス中に不正な記号（送信エラーになるもの） が含まれているかのチェック
			return false;
		} else if (value.indexOf(" ") != -1) {
			// メールアドレス中に半角スペースが含まれているかのチェック
			// ※バリデーションで半角スペースは空文字にしているため、通常ありえない
			return false;
		} else if (!matchesHalfWidth(value)) {
			// メールアドレスが半角文字か
			// ※全角文字は許容しない
			return false;
			// RFC に準拠しているかのチェック
			// 注意：API に直接 "mailAddress" を渡さないこと
		}
		return true;
	}

	/**
	 * メールアドレスにマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void matchesEmailChk(String propertyName, String value, ActionMessages messages, String itemName) {
		if ((matchesEmail(value) == false) || (DomainUtil.domainChk(value) == false)) {
			add(propertyName, messages, AppMessageNames.ERRORS_EMAIL.key, itemName);
		}
	}

	/**
	 * メールアドレス（入力）とメールアドレス確認がマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 * @param mail
	 * @param mailConfirm
	 * @param messages
	 * @param itemName
	 */
	public static void matchesEmailInputAndConfirm(String propertyName, String mail, String mailConfirm,
			ActionMessages messages, String itemName) {
		// 確認用と一致するか
		if (StringUtil.isNotEmpty(mail) && StringUtil.isNotEmpty(mailConfirm)) {
			if (!mail.equals(mailConfirm)) {
				add(propertyName, messages, AppMessageNames.ERRORS_MAIL.key, itemName);
			}
		}
	}

	/**
	 * 電話番号（数字始まり、-ありOK）にマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesPhone(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		return value.matches("(?!-)(?!.*-$)(?!.*--)([-0-9]+)$");
	}

	/**
	 * 電話番号（0始まり、-を除いた桁数が10桁か11桁）にマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesPhoneSevere(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		return value.replaceAll("-", "").matches("0[0-9]{9,10}");
	}

	/**
	 * 電話番号（数字始まり、-ありOK）にマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param key
	 *            キー
	 * @param params
	 *            置換文字列
	 */
	public static void matchesPhoneChk(String propertyName, String value, ActionMessages messages, Object... params) {
		if (matchesPhone(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_PHONE.key, params);
		}
	}

	/**
	 * 電話番号（0始まり、-を除いた桁数が10桁か11桁）にマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param key
	 *            キー
	 * @param params
	 *            置換文字列
	 */
	public static void matchesPhoneSevereChk(String propertyName, String value, ActionMessages messages,
			Object... params) {
		if (matchesPhoneSevere(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_PHONE.key, params);
		}
	}

	/**
	 * WEBコールで使用出来る番号にマッチするかどうか</br>
	 * 前提条件：WEBコール使用「有」 であることがチェック済みであること
	 *
	 * @param value
	 *            値
	 * @return 使用出来る番号ならtrue
	 */
	public static boolean matchesWebCallTargetTel(String value) {
		if (StringUtils.isEmpty(value)) {
			return true;
		}
		String regex = "^050";
		Pattern ptn = Pattern.compile(regex);
		Matcher mtc = ptn.matcher(value.replaceAll("-", ""));
		return !mtc.find();
	}

	/**
	 * 郵便番号（7文字の数値もしくはハイフン付き）にマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesPostCode(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		try {
			if (value.charAt(3) == '-') {
				value = value.substring(0, 3) + value.substring(4);
			}

			if (matchesHalfNumber(value) == false) { // Integer.valueOf(value)は全角でも数値を変換できてしまうので半角数値であることをチェックする
				return false;
			}

			Integer.valueOf(value); // 数値チェック （念のためIntegerへの変換に失敗しないかどうかをチェック）
			if (value.length() == 7) {
				return true;
			}
		} catch (Exception e) {
			// void
		}

		return false;
	}

	/**
	 * 郵便番号（7文字の数値）にマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param key
	 *            キー
	 * @param params
	 *            置換文字列
	 */
	public static void matchesPostCodeChk(String propertyName, String value, ActionMessages messages,
			Object... params) {
		if (matchesPostCode(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_POSTCODETYPE.key, params);
		}
	}

	private static final UrlValidator httpUrlValidator = new UrlValidator(new String[] { "http" });

	/**
	 * URL(HTTP)にマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	@Deprecated
	public static boolean matchesHttpUrl(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		try {
			new URL(value);
			return httpUrlValidator.isValid(value);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * URL(HTTP)にマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 * @deprecated matchesHttpsUrlのみ使用するようになる予定です
	 */
	@Deprecated
	public static void matchesHttpUrlChk(String propertyName, String value, ActionMessages messages, String itemName) {
		if (matchesHttpUrl(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_HTTPURLTYPE.key, itemName);
		}
	}

	/**
	 * URL(HTTPS)にマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesHttpsUrl(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		try {
			new URL(value);
			return (value.startsWith("https://"));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * URL(HTTPS)にマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void matchesHttpsUrlChk(String propertyName, String value, ActionMessages messages, String itemName) {
		if (matchesHttpsUrl(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_HTTPSURLTYPE.key, itemName);
		}
	}

	/**
	 * URL(HTTPかHTTPS)にマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesHttpOrHttpsUrl(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		try {
			new URL(value);
			return (value.startsWith("http://") || value.startsWith("https://"));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * URL(HTTPかHTTPS)にマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void matchesHttpOrHttpsUrlChk(String propertyName, String value, ActionMessages messages,
			String itemName) {
		if (matchesHttpOrHttpsUrl(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_HTTPORHTTPSURLTYPE.key, itemName);
		}
	}

	/**
	 * 当該URLが指定のドメインにマッチするかどうか（ただし、URLの形式でない場合にはチェックをしないので注意。
	 * matchesHttpUrlを合わせて使用すること）
	 *
	 * @param value
	 *            値
	 * @param domainName
	 *            ドメイン名
	 * @return true / false
	 */
	public static boolean matchesUrlDomain(String value, String domainName) {
		if (StringUtils.isEmpty(value))
			return true;

		try {
			URL url = new URL(value);
			return domainName.equals(url.getHost());
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * 空文字または印刷可能文字のみの場合、true
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchPrintableCharacters(String value) {
		if (StringUtils.isEmpty(value))
			return true;
		return value.matches("\\p{Print}+");
	}

	/**
	 * 空文字または印刷可能文字のみの場合、true（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param key
	 *            キー
	 * @param params
	 *            置換文字列
	 */
	public static void matchPrintableCharactersChk(String propertyName, String value, ActionMessages messages,
			String key, Object... params) {
		if (matchPrintableCharacters(value) == false) {
			add(propertyName, messages, key, params);
		}
	}

	/**
	 * 禁則文字列を含むか いまのところ何も無いらしい。
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean includeInvalidCharacter(String value) {
		if (StringUtils.isEmpty(value))
			return true;
		boolean result = true;
		char[] invalidChar = {};
		for (char aChar : invalidChar) {
			if (value.matches(".*" + aChar + ".*")) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * 禁則文字列を含むか いまのところ何も無いらしい。（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param key
	 *            キー
	 * @param params
	 *            置換文字列
	 */
	public static void includeInvalidCharacterChk(String propertyName, String value, ActionMessages messages,
			String key, Object... params) {
		if (includeInvalidCharacter(value) == false) {
			add(propertyName, messages, key, params);
		}
	}

	/**
	 * ################################### private
	 * ###################################
	 */

	/**
	 * メールアドレスチェック用 メール送信不可の記号が含まれているか<br>
	 * 対象記号→ ( ) < > [ ] ; : , \ "
	 *
	 * @param mailAddress
	 *            メールアドレス
	 * @return true / false
	 */
	private static boolean isWrongSymbolContain(String mailAddress) {
		char[] parenthesis = { '(', ')', '<', '>', '[', ']', ';', ':', ',', '\\', '"', '\u00a5' };
		for (int i = 0; i < mailAddress.length(); i++) {
			for (int j = 0; j < parenthesis.length; j++) {
				if (mailAddress.charAt(i) == parenthesis[j]) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 緯度（-90～90 半角正の小数）のマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesLatitude(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		if (value.length() <= 20 && matchesHalfDecimal(value)) {
			Double latitude = Double.parseDouble(value);
			if (latitude >= -90.0 && latitude <= 90.0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 緯度（-90～90）にマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void matchesLatitudeChk(String propertyName, String value, ActionMessages messages, String itemName) {
		if (matchesLatitude(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_LATITUDE.key, itemName);
		}
	}

	/**
	 * 経度（-180～180 半角正の小数）にマッチするかどうか
	 *
	 * @param value
	 *            値
	 * @return true / false
	 */
	public static boolean matchesLongitude(String value) {
		if (StringUtils.isEmpty(value))
			return true;

		if (value.length() <= 20 && matchesHalfDecimal(value)) {
			Double longitude = Double.parseDouble(value);
			if (longitude >= -180 && longitude <= 180.0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 経度（-180～180）にマッチするかどうか（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void matchesLongitudeChk(String propertyName, String value, ActionMessages messages,
			String itemName) {
		if (matchesLatitude(value) == false) {
			add(propertyName, messages, AppMessageNames.ERRORS_LONGITUDE.key, itemName);
		}
	}

	/**
	 * ファイルの拡張子をチェックします。
	 *
	 * @param file
	 *            ファイル
	 * @param extensions
	 *            対応する拡張子
	 * @return true / false
	 */
	public static boolean isExtension(FormFile file, String[] extensions) {
		if (file == null) {
			return false;
		}
		String fileName = file.getFileName();
		if (extensions == null || extensions.length == 0) {
			return (FilenameUtils.indexOfExtension(fileName) == -1);
		}
		String fileExt = FilenameUtils.getExtension(fileName);
		for (int i = 0; i < extensions.length; i++) {
			if (fileExt.equalsIgnoreCase(extensions[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ファイルの拡張子をチェックします。（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param file
	 *            ファイル
	 * @param extensions
	 *            対応する拡張子
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void isExtensionChk(String propertyName, FormFile file, String[] extensions, ActionMessages messages,
			String itemName) {
		if (!isExtension(file, extensions)) {
			add(propertyName, messages, AppMessageNames.ERRORS_ISEXTENSION.key, itemName);
		}
	}

	/**
	 * ファイルサイズをチェックします。
	 *
	 * @param file
	 *            ファイル
	 * @param maxSize
	 *            最大のファイルサイズ（バイト）
	 * @return true / false
	 */
	public static boolean maxFileSize(FormFile file, long maxSize) {
		if (file == null) {
			return false;
		}
		if (maxSize < file.getFileSize()) {
			return false;
		}
		return true;
	}

	/**
	 * ファイルサイズをチェックします。（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param file
	 *            ファイル
	 * @param maxSize
	 *            最大のファイルサイズ（バイト）
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void maxFileSizeChk(String propertyName, FormFile file, long maxSize, ActionMessages messages,
			String itemName) {
		if (!maxFileSize(file, maxSize)) {
			add(propertyName, messages, AppMessageNames.ERRORS_MAXFILESIZE.key, itemName, maxSize);
		}
	}

	/**
	 * 画像ファイルかどうかをチェックします。
	 *
	 * @param imageFile
	 *            画像ファイル
	 * @return true / false
	 */
	public static boolean isImageFile(FormFile imageFile) {
		if (imageFile == null) {
			return false;
		}
		ImageInputStream is = null;
		try {
			is = ImageIO.createImageInputStream(imageFile.getInputStream());
			Iterator<ImageReader> i = ImageIO.getImageReaders(is);
			while (i.hasNext()) {
				return true;
			}
		} catch (IOException e) {
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e1) {
				}
			}
		}
		return false;
	}

	/**
	 * 画像ファイルかどうかをチェックします。（エラーメッセージも同時に追加）
	 *
	 * @param propertyName
	 *            プロパティ名
	 * @param file
	 *            ファイル
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 */
	public static void isImageFileChk(String propertyName, FormFile imageFile, ActionMessages messages,
			String itemName) {
		if (!isImageFile(imageFile)) {
			add(propertyName, messages, AppMessageNames.ERRORS_ISIMAGEFILE.key, itemName);
		}
	}

	/**
	 * 画像の長さチェック
	 *
	 * @param is
	 * @param imageFileMaxImageLength
	 * @return true:許容内、false:許容外
	 * @throws IOException
	 */
	public static boolean imageFileMaxImageLengthChk(InputStream is, final int imageFileMaxImageLength)
			throws IOException {

		BufferedImage image = FileUtil.readJAI(is);

		if (image != null) {
			int width = image.getWidth();
			int height = image.getHeight();
			if (imageFileMaxImageLength < width || imageFileMaxImageLength < height) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 画像がCMYKであるかどうか
	 *
	 * @param imageFile
	 * @return true:CMYK、false:CMYKでない
	 */
	public static boolean isCmykImageChk(FormFile imageFile) {

		try {
			ImageIO.read(imageFile.getInputStream());

		} catch (IIOException e) {
			if (!StringUtils.isEmpty(e.getMessage()) && e.getMessage().equals("Unsupported Image Type")) {
				// CMYKの場合当該例外が発生する
				return true;
			}
			return false;

		} catch (Exception e) {
			return false;

		}
		return false;
	}

	/**
	 * ActionMessagesからメッセージ文字列のリストを取得する
	 *
	 * @param actionMessages
	 *            ActionMessages
	 * @return メッセージ文字列のリスト
	 */
	public static List<String> getMessageStrings(ActionMessages actionMessages) {
		List<ActionMessage> newArrayList = Lists.newArrayList((Iterator<ActionMessage>) actionMessages.get());

		return Lists.transform(newArrayList, new Function<ActionMessage, String>() {
			@Override
			public String apply(ActionMessage a) {
				return getMessageString(a);
			}
		});
	}

	/**
	 * ActionMessageからメッセージ文字列を取得する
	 *
	 * @param actionMessage
	 *            ActionMessage
	 * @return メッセージ文字列
	 */
	public static String getMessageString(ActionMessage actionMessage) {
		String result = actionMessage.getKey();
		if (actionMessage.isResource()) {
			result = Message.getString(actionMessage.getKey(), actionMessage.getValues());
		}

		return result;
	}

}
