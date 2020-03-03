package com.isystk.sample.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Config {

	private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

	private static Properties properties;

	static {

		Properties props = new Properties();
		Properties serverProps = new Properties();
		InputStream conf = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("application-config.properties");
		InputStream serverConf = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("application-server-config.properties");

		if (conf == null) {
			LOGGER.warn("not exists `classpath:application-config.properties`");
		} else {
			try {
				props.load(conf);
			} catch (IOException e) {
				LOGGER.error("read failed `classpath:application-config.properties`", e);
			}
		}

		if (serverConf == null) {
			LOGGER.warn("not exists `classpath:application-server-config.properties`");
		} else {
			try {
				serverProps.load(serverConf);
			} catch (IOException e) {
				LOGGER.error("read failed `classpath:application-server-config.properties`", e);
			}
		}

		for (Entry<Object, Object> serverEntry : serverProps.entrySet()) {
			props.put(serverEntry.getKey(), serverEntry.getValue());
		}
		properties = props;
	}

	private Config() {
	}

	public static String getString(AppConfigNames config) {
		return getString(config.key);
	}

	public static String getString(String key) {
		return properties.getProperty(key);
	}

	// FIXME iseyoshitaka 例外処理する
	public static Integer getInteger(AppConfigNames config) {
		return getInteger(config.key);
	}

	public static Integer getInteger(String key) {
		return Integer.parseInt(properties.getProperty(key));
	}

	public static String[] getStringArray(AppConfigNames config) {
		return getStringArray(config.key);
	}

	public static String[] getStringArray(String key) {
		return getString(key).split(",");
	}

	// FIXME iseyoshitaka 例外処理する
	public static Boolean getBoolean(AppConfigNames config) {
		return getBoolean(config.key);
	}

	public static Boolean getBoolean(String key) {
		return Boolean.parseBoolean(properties.getProperty(key));
	}

	public static InputStream getInputStream(String resource) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
	}
}
