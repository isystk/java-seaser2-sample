package com.isystk.sample.web.common.util;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

import com.isystk.sample.common.constants.Constants.Tracking;
import com.isystk.sample.common.util.DateUtils;
import com.isystk.sample.common.util.StringUtils;

/**
 * @author ikedanatsuko
 * 
 */
public class TrackingIdUtil {

    private static final Integer randomStringLength = 10;

    /** トラッキングID IPアドレス + "_" + 年月日時分秒(YYYYMMDDhhmmssSSS) + "_" + ランダム文字列(10文字) */
    private static final int T_TRACKING_LOG_TRACKINGID = 44;

    /**
     * トラッキングID発行
     * 
     */
    public static String getTrackingId() {

	// クッキーからトラッキングID取得
	String trackingId = CookieUtil.getValue(Tracking.COOKIE_NAME);

	// トラッキングIDが44桁以上の場合、初期化する
	if (!StringUtils.checkMaxLength(trackingId, T_TRACKING_LOG_TRACKINGID)) {
	    trackingId = null;
	}

	// 取得できなかった場合、トラッキングID生成。クッキーに保存
	if (StringUtils.isNullOrEmpty(trackingId)) {
	    Date date = DateUtils.getNow();
	    String dateString = "_" + DateUtils.toDateFormatNoSlash(date) + DateUtils.toMilliSecondTimeString(date).replace(":", "");
	    String randomString = "_" + RandomStringUtils.randomAlphanumeric(randomStringLength);

	    trackingId = IPAddressUtil.getRealIP() + dateString + randomString;
	    CookieUtil.setCookie(Tracking.COOKIE_NAME, trackingId, Tracking.COOKIE_PATH, Tracking.COOKIE_AGE, null);
	}

	return trackingId;

    }

}
