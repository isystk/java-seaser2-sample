package com.isystk.sample.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.isystk.sample.common.exception.SystemException;

/**
 * 六曜を取得
 * 
 * @author mnakamura
 * 
 */
public class RokuyouUtil {

    private static Map<String, String> rokuyouMap = null;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    /**
     * 六曜を取得する
     * 
     * @param date Date
     * @return 六曜
     */
    public static String getRokuyou(Date date) {
	try {
	    String s = sdf.format(date);
	    if (rokuyouMap == null) {
		rokuyouMap = createRokuyouMap();
	    }
	    return rokuyouMap.get(s);
	} catch (Exception e) {
	    return null;
	}
    }

    /**
     * 六曜のMapを取得する
     * 
     * @return 六曜のSet
     */
    public static Map<String, String> getRokuyouMap() {
	if (rokuyouMap == null) {
	    rokuyouMap = createRokuyouMap();
	}
	return rokuyouMap;
    }

    /**
     * 六曜のMapを作成する
     * 
     * @return 六曜のMap
     */
    private static Map<String, String> createRokuyouMap() {
	Map<String, String> result = new HashMap<String, String>();
	BufferedReader reader = new BufferedReader(new InputStreamReader(RokuyouUtil.class.getClassLoader().getResourceAsStream("rokuyou.dat")));

	try {
	    while (reader.ready()) {
		String rokuyou = reader.readLine().trim();
		if (StringUtils.isNullOrEmpty(rokuyou) == false) {
		    String[] array = rokuyou.split(" ");
		    if (array.length == 2) {
			String d = array[0];
			String r = array[1];
			result.put(d, r);
		    }
		}
	    }
	} catch (Exception e) {
	    throw new SystemException("六曜ファイル(rokuyou.dat)の読み込みに失敗しました", e);
	}

	return result;
    }

}
