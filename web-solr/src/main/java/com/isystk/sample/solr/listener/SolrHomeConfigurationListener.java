/**
 * Copyright(c) isystk.com</br>
 */
package com.isystk.sample.solr.listener;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author iseyoshitaka
 */
public class SolrHomeConfigurationListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(SolrHomeConfigurationListener.class);

	public void contextInitialized(ServletContextEvent sce) {

		Properties config = load();

		settingSolrHome(config);

		settingSolrDataDir(config);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// nop
	}

	private Properties load() {
		Properties prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("solr.properties"));
		} catch (IOException e) {
			logger.error("I/O Error : " + e.getMessage(), e);
		}
		return prop;
	}

	private void settingSolrHome(Properties prop) {
		String sh = prop.getProperty("solr.home");
		if (sh == null || sh.length() <= 0)
			return;

		System.setProperty("solr.solr.home", sh);
	}

	private void settingSolrDataDir(Properties prop) {
		String sh = prop.getProperty("solr.data.dir");
		if (sh == null || sh.length() <= 0)
			return;

		System.setProperty("solr.data.dir", sh);
	}
}
