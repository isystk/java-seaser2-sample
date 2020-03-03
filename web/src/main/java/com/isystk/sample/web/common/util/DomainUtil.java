package com.isystk.sample.web.common.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.lang.StringUtils;

/**
 * @author iseyoshitaka
 * 
 */
public class DomainUtil {

	public static final String RECORD_SOA = "SOA";
	public static final String RECORD_A = "A";
	public static final String RECORD_NS = "NS";
	public static final String RECORD_MX = "MX";
	public static final String RECORD_CNAME = "CNAME";

	/**
	 * ドメイン有効チェック
	 * 
	 * @param propertyName
	 *            プロパティ名
	 * @param value
	 *            値
	 * @param messages
	 *            メッセージ
	 * @param itemName
	 *            項目名（表示文字列）
	 * @return 判定結果（true：正常、false：異常）
	 */
	public static boolean domainChk(String value) {
		if (StringUtils.isNotEmpty(value)) {
			return (isEffectiveDomain(getHostName(value)));
		}
		return true;
	}

	/**
	 * メールアドレスから「＠」マーク以降のホスト名を取得します。
	 * 
	 * @param mailAddressValue
	 *            メールアドレス
	 * @return 「＠」マーク以降のホスト名
	 */
	private static String getHostName(String mailAddressValue) {
		if (mailAddressValue.indexOf("@") == -1) {
			return mailAddressValue;
		} else {
			int startPos = mailAddressValue.indexOf("@") + 1;
			return mailAddressValue.substring(startPos, mailAddressValue.length());
		}
	}

	/**
	 * 有効なドメインかを判定します。
	 * 
	 * @param mailAddressValue
	 *            メールアドレス
	 */
	private static boolean isEffectiveDomain(String host) {
		List entries = lookup(host, RECORD_MX);
		return (entries.size() > 0);
	}

	/**
	 * 有効なドメインかを判定します。
	 * 
	 * @param mailAddressValue
	 *            メールアドレス
	 * @param record
	 *            レコード
	 */
	private static List lookup(String hostName, String record) {

		List result = new ArrayList();
		try {
			Hashtable env = new Hashtable();
			env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
			DirContext ictx = new InitialDirContext(env);
			Attributes attrs = ictx.getAttributes(hostName, new String[] { record });
			Attribute attr = attrs.get(record);

			NamingEnumeration attrEnum = attr.getAll();
			while (attrEnum.hasMoreElements())
				result.add(attrEnum.next());
		} catch (NamingException e) {
		} catch (NullPointerException e) {
		}
		return result;
	}

}
