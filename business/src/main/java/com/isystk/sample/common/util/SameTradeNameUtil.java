package com.isystk.sample.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.isystk.sample.common.exception.SystemException;

/**
 * 別屋号IDを同一の屋号として取扱うためのMap取得
 * 
 * @author iseyoshitaka
 */
public class SameTradeNameUtil {

    private static Map<Integer, List<Integer>> sameTradeNameIdMap = null;

    /**
     * 同屋号取扱いMapを取得する
     * 
     * @param date Date
     * @return 同屋号取扱いMap
     */
    public static List<Integer> getSameTradeNameIdList(Integer tradeNameId) {
	try {
	    if (sameTradeNameIdMap == null) {
		sameTradeNameIdMap = createSameTradeNameIdMap();
	    }
	    return sameTradeNameIdMap.get(tradeNameId);
	} catch (Exception e) {
	    return null;
	}
    }

    /**
     * 同屋号取扱いMapを取得する
     * 
     * @return 同屋号取扱いMapのSet
     */
    public static Map<Integer, List<Integer>> getSameTradeNameIdMap() {
	if (sameTradeNameIdMap == null) {
	    sameTradeNameIdMap = createSameTradeNameIdMap();
	}
	return sameTradeNameIdMap;
    }

    /**
     * 同屋号取扱いMapを作成する
     * 
     * @return 同屋号取り扱いのMap
     */
    private static Map<Integer, List<Integer>> createSameTradeNameIdMap() {
	BufferedReader reader = new BufferedReader(new InputStreamReader(SameTradeNameUtil.class.getClassLoader().getResourceAsStream(
	    "sameTradeName.dat")));

	Map<Integer, List<Integer>> readList = new HashMap<Integer, List<Integer>>();
	try {
	    while (reader.ready()) {
		String line = reader.readLine().trim();
		if (StringUtils.isNullOrEmpty(line) == false) {
		    String[] array = line.split("=");
		    if (array.length == 2) {
			Integer d = NumberUtil.toInteger(array[0]);
			Integer r = NumberUtil.toInteger(array[1]);

			// dをキーにrを追加
			List<Integer> dTradeNameIdList = readList.get(d);
			if (dTradeNameIdList == null) {
			    dTradeNameIdList = new ArrayList<Integer>();
			}
			if (!dTradeNameIdList.contains(r) && r != d) {
			    dTradeNameIdList.add(r);
			}
			readList.put(d, dTradeNameIdList);

			// dをキーにrを追加
			List<Integer> rTradeNameIdList = readList.get(r);
			if (rTradeNameIdList == null) {
			    rTradeNameIdList = new ArrayList<Integer>();
			}
			if (!rTradeNameIdList.contains(d) && r != d) {
			    rTradeNameIdList.add(d);
			}
			readList.put(r, rTradeNameIdList);
		    }
		}
	    }
	} catch (Exception e) {
	    throw new SystemException("同屋号取扱いファイル(sameTradeName.dat)の読み込みに失敗しました", e);
	}

	// マッチング
	Map<Integer, List<Integer>> result = new HashMap<Integer, List<Integer>>();
	for (Integer key : readList.keySet()) {
	    result.put(key, new ArrayList<Integer>());
	    List<Integer> work = new ArrayList<Integer>(readList.get(key));
	    setList(readList, key, work);
	    Collections.sort(work);
	    for (Integer val : work) {
		if (val.intValue() != key.intValue()) {
		    result.get(key).add(val);
		}
	    }
	}

	return result;
    }

    /**
     * データのマッチング
     * 
     * @param readList
     * @param key
     * @param list
     */
    private static void setList(Map<Integer, List<Integer>> readList, Integer key, List<Integer> list) {
	for (Integer val : readList.get(key)) {
	    List<Integer> matchList = readList.get(val);
	    for (Integer matchVal : matchList) {
		if (!list.contains(matchVal) && key != matchVal) {
		    list.add(matchVal);
		    setList(readList, matchVal, list);
		}
	    }

	    List<Integer> matchList2 = readList.get(key);
	    for (Integer matchVal2 : matchList2) {
		if (!list.contains(matchVal2) && val != matchVal2) {
		    list.add(matchVal2);
		    setList(readList, matchVal2, list);
		}
	    }
	}
    }

    public static void main(String[] args) {
	Map<Integer, List<Integer>> sameTradeNameIdMap = createSameTradeNameIdMap();
	System.out.println(sameTradeNameIdMap);
    }

}
