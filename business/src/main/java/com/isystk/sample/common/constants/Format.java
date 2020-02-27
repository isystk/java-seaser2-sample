package com.isystk.sample.common.constants;

public class Format {

    public static final String DATE = "yyyy年M月d日(E)";
    public static final String DATE_FORINPUT = "yyyy/MM/dd";
    public static final String DATE_FORMANUALINPUT = "yyyyMMdd";

    public static final String DATE_SIMPLE = "M月d日(E)";
    public static final String DATE_SIMPLE_TIME = "M月d日(E) H時mm分";

    public static final String TIME = "HH:mm";
    public static final String TIME_DETAIL = TIME + ":ss";
    public static final String DATE_TIME = DATE + " " + TIME;
    public static final String DATE_TIME_DETAIL = DATE + " " + TIME_DETAIL;

    public static final String DATE_TIME_DETAIL_FORLABEL = "yyyyMMddHHmmss";


    /** 日付フォーマット yyyy/MM/dd */
    public static final String DATE_YYYYMMDD = "yyyy/MM/dd";
    /** 日付フォーマット yyyy/MM/dd */
    public static final String DATE_YYYYMMDDHHMMSS = "yyyy/MM/dd HH:mm:ss";
    /** 日付フォーマット yyyy/MM */
    public static final String DATE_YYYYMM = "yyyy/MM";
    /** 日付フォーマット MM/dd */
    public static final String DATE_MMDDE = "MM/dd(E)";
    /** 日付フォーマット MM/dd */
    public static final String DATE_MMDD = "MM/dd";
    /** 日付フォーマット yyyy/M/d(E) H時 */
    public static final String DATE_TIME_YYYYMDEH_JP = "yyyy年M月d日(E) H時";
    /** 日付フォーマット yyyy年M月d日 H時mm分 */
    public static final String DATE_TIME_YYYYMDHMM_JP = "yyyy年M月d日 H時mm分";
    /** 日付フォーマット yyyy年M月d日(E) H時mm分 */
    public static final String DATE_TIME_YYYYMDEHMM_JP = "yyyy年M月d日(E) H時mm分";
    /** 日付フォーマット yyyy年M月d日 */
    public static final String DATE_TIME_YYYYMD_JP = "yyyy年M月d日";
    /** 日付フォーマット yyyy */
    public static final String DATE_YYYY = "yyyy";
    /** 日付フォーマット MM */
    public static final String DATE_MM = "MM";
    /** 日付フォーマット MM */
    public static final String DATE_DD = "dd";
    /** 日付フォーマット HH */
    public static final String TIME_HH = "HH";
    /** 日付フォーマット mm */
    public static final String TIME_mm = "mm";
    /** 日付フォーマット yyyy-MM-dd */
    public static final String DATE_JS = "yyyy-MM-dd";
    /** 日付フォーマット yyyy.MM.dd */
    public static final String DATE_YYYYMMDD_DOT = "yyyy.MM.dd";

    /** 日時をISO8601形式に変換する際に使用するフォーマット */
    public static final String FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ssZ";

}
