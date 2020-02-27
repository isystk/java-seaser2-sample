package com.isystk.sample.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Message {

    private static final Logger LOGGER = LoggerFactory.getLogger(Message.class);

    private static Properties properties;

    static {
	properties = loadProperties();
    }

    private Message() {
    }

    private static Properties loadProperties() {

	Properties props = new Properties();
	InputStream conf = Thread.currentThread().getContextClassLoader().getResourceAsStream("application-message.properties");

	if (conf == null) {
	    LOGGER.warn("not exists `classpath:application-message.properties`");
	} else {
	    try {
		props.load(conf);
	    } catch (IOException e) {
		LOGGER.error("read failed `classpath:application-message.properties`", e);
	    }
	}
	return props;
    }

    public static String getString(AppMessageNames config) {
	return getString(config.key);
    }

    public static String getString(String key) {
//	// ローカルの場合は毎回ファイルをロードする。
//	if (Env.UT.equals(Env.getValue())) {
//	    properties = loadProperties();
//	}
	String value = properties.getProperty(key);
	if (value == null) {
	    return key;
	}
	return value;
    }

    public static String getString(String key, String... values) {
	return MessageFormat.format(Message.getString(key), values);
    }

    public static String getString(String key, Object[] values) {
	return MessageFormat.format(Message.getString(key), values);
    }

    /**
     * tld用
     * 
     * @param key
     * @param value
     * @return
     */
    public static String getString1(String key, String value) {
	return getString(key, value);
    }

    /**
     * tld用
     * 
     * @param key
     * @param value
     * @return
     */
    public static String getString2(String key, String value1, String value2) {
	return getString(key, value1, value2);
    }

    /**
     * tld用
     * 
     * @param key
     * @param value
     * @return
     */
    public static String getString3(String key, String value1, String value2, String value3) {
	return getString(key, value1, value2, value3);
    }

    /**
     * tld用
     * 
     * @param key
     * @param value
     * @return
     */
    public static String getString4(String key, String value1, String value2, String value3, String value4) {
	return getString(key, value1, value2, value3, value4);
    }
}
