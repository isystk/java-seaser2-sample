/**
 * Copyright(c) team-lab</br>
 */
package com.isystk.sample.common.constants.authority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 管理者の権限拡張情報
 * 
 * @author iseyoshitaka
 */
public enum ClientAuthorityExt {

    /** 同じ会社のみ */
    ONLY_SAME_COMPANY(),

    /** 同じ部門のみ */
    ONLY_SAME_DEPT(),

    /** 企業責任者を選択できない */
    CANT_SELECT_CORP_CLIENT(),

    /** 同じ店舗のみ */
    ONLY_SAME_SHOP(),

    /** オペレーター1, 2のみ選択可能 */
    ONLY_OPERATORS(),

    /** 権限は編集のみ選択可能 */
    ONLY_EDIT(),

    /** 担当部門の情報のみ */
    ONLY_DEPT_IN_CHARGE(),

    /** 担当部門を選択できない */
    CANT_SELECT_DEPT_IN_CHARGE(),

    /** 公開フラグを選択、編集することができない */
    CANT_SELECT_PUB_FLG(),

    /** 所属クライアントの情報のみ　 */
    ONLY_SAME_CLIENT(),

    /** 所属ブランドの情報のみ */
    ONLY_SAME_BRAND(),

    /** 掲載確定の情報のみ選択可能 */
    ONLY_SELECT_PUBLISH_COMPLETE(),

    /** 掲載確定の情報を選択できない */
    CANT_SELECT_PUBLISH_COMPLETE()

    ;

    /** すべての権限を持つアカウントの表現文字列 */
    public static final String SUPER_ADMIN = "*";

    /**
     * nameで権限を見つけます。
     * 
     * @param name 権限名
     * @return 権限
     */
    public static ClientAuthorityExt find(String name) {
	if (name == null) {
	    return null;
	}
	for (ClientAuthorityExt a : values()) {
	    if (a.name().equals(name)) {
		return a;
	    }
	}
	return null;
    }

    /**
     * 権限を持っているかどうかを判定します
     * 
     * @param authes 権限の集合
     * @param auth 権限
     * @return 持っているときtrue
     */
    public static boolean hasAuthorityExt(ClientAuthorityExt[] authes, ClientAuthorityExt auth) {

	if (auth == null) {
	    throw new IllegalArgumentException();
	}

	if (authes == null || authes.length <= 0) {
	    return false;
	}

	for (ClientAuthorityExt a : authes) {
	    if (a == null) {
		continue;
	    }
	    if (a.equals(auth)) {
		return true;
	    }
	}
	return false;
    }

    /**
     * @param authes　権限
     * @return 権限文字列
     */
    public static String toString(ClientAuthorityExt[] authes) {
	if (authes == null || authes.length <= 0) {
	    return "";
	}

	boolean first = true;
	StringBuilder sb = new StringBuilder();
	for (ClientAuthorityExt a : authes) {
	    if (a == null) {
		continue;
	    }
	    if (first) {
		first = false;
	    } else {
		sb.append(",");
	    }
	    sb.append(a.name());
	}

	return sb.toString();
    }

    /**
     * 文字列から権限を起こします
     * 
     * @param strs 権限文字列
     * @return 権限
     */
    public static Map<ClientAuthority, List<ClientAuthorityExt>> valuesOf(String strs) {
	if (strs == null || strs.length() <= 0) {
	    return Collections.emptyMap();
	}

	Map<ClientAuthority, List<ClientAuthorityExt>> result = new HashMap<ClientAuthority, List<ClientAuthorityExt>>();
	for (String kv : strs.split("\\s*,\\s*")) {

	    String[] keyAndValue = kv.split("\\s*=\\s*");

	    ClientAuthority key = ClientAuthority.find(keyAndValue[0]);
	    if (key == null) {
		continue;
	    }

	    List<ClientAuthorityExt> values = new ArrayList<ClientAuthorityExt>();
	    String[] valuez = keyAndValue[1].split("\\s*:\\s*");
	    for (String val : valuez) {
		ClientAuthorityExt value = ClientAuthorityExt.find(val);
		if (value == null) {
		    continue;
		}
		values.add(value);
	    }

	    result.put(key, values);
	}

	return result;
    }

    /** AccountAuthorityの対応する権限 */
    private final AccountAuthority[] account;

    /** 空 */
    private static final ClientAuthorityExt[] EMPTY = new ClientAuthorityExt[0];

    /**
     * @param account AccountAuthorityの対応する権限
     */
    private ClientAuthorityExt(AccountAuthority... account) {
	this.account = account;
    }
}
