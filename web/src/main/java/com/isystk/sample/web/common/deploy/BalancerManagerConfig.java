package com.isystk.sample.web.common.deploy;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * バランサマネージャ設定を取り込み
 *
 * @author iseyoshitaka
 *
 */
public class BalancerManagerConfig {
    public final String name;
    public final String managerUrl;
    public final String balancerName;
    public final int retryCount;
    public final int retryWait;

    BalancerManagerConfig(String balancerHost, Properties prop) {
	this.name = balancerHost;
	this.managerUrl = loadConfig("balancer.manager", balancerHost, false, prop);
	this.balancerName = loadConfig("balancer.name", balancerHost, false, prop);
	this.retryCount = Integer.parseInt(loadConfig("balancer.retry.count", balancerHost, false, prop));
	this.retryWait = Integer.parseInt(loadConfig("balancer.retry.wait", balancerHost, false, prop));
    }

    private static String loadConfig(String key, String prefix, boolean targetConfigRequired, Properties prop) {
	String targetKey = prefix + "." + key;
	String ret = prop.getProperty(targetKey, targetConfigRequired ? null : prop.getProperty(key));
	if (StringUtils.isEmpty(ret)) {
	    throw new IllegalArgumentException("必須のプロパティー" + targetKey + (targetConfigRequired ? "" : "または" + key) + "が見つかりません。");
	}
	return ret;
    }
}
