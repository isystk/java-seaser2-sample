/*
 * BooleanUtil.java
 * 2011/04/26 mmatsumoto
 */
package com.isystk.sample.common.util;


/**
 * @author mmatsumoto
 *
 */
public class BooleanUtil {

    /**
     * フラグ（0 or 1 or true or false）からbooleanへ変換
     * 
     * @param value 値
     * @return true / false
     */
    public static boolean toBool(String value) {
	return "1".equals(value) || "true".equals(value);
    }

    /**
     * フラグ（0 or 1 or true or false）からbooleanへ変換
     * 
     * @param value 値
     * @return true / false
     */
    public static boolean toBool(String value, Boolean defaultValue) {
	if (value == null) {
	    return defaultValue;
	}
	return toBool(value);
    }

    /**
     * フラグ（0 or 1 or true or false）からbooleanへ変換
     * 
     * @param value 値
     * @return true / false
     */
    public static Boolean[] toBoolArray(String[] strArray) {
	if (strArray == null) {
	    return null;
	}
	Boolean[] booleanArray = new Boolean[strArray.length];
	for (int i = 0; i < strArray.length; i++) {
	    booleanArray[i] = ("1".equals(strArray[i]) || "true".equals(strArray[i]));
	}
	return booleanArray;
    }

    /**
     * booleanへ変換可能かどうか
     * 
     * @param value 値
     * @return true / false
     */
    public static boolean isBool(String value) {
	return "1".equals(value) || "0".equals(value) || "true".equals(value) || "false".equals(value);
    }


    /**
     * フラグ（0 or 1 or true or false）からbooleanへ変換
     * 
     * @param value 値
     * @return true / false
     */
    public static boolean toBool(Boolean value) {
    	if (value == null) {
    		return false;
    	}
    	return value;
    }
}
