package com.bluesky.visualprogramming.vm;

import java.util.Properties;

import org.apache.log4j.Logger;

public class AppProperties {
	static Logger logger = Logger.getLogger(AppProperties.class);
	
	static private AppProperties instance;

	public static AppProperties getInstance() {
		if (instance == null) {
			instance = new AppProperties();
		}

		return instance;
	}

	Properties appProperties;


	public AppProperties() {
		appProperties = new Properties();
		try {
			appProperties.load(AppProperties.class
					.getResourceAsStream("/app.properties"));


		} catch (Exception e) {
			logger.warn("app.properties not found.");			
		}

	}

	public String getProperty(String key) {
		return appProperties.getProperty(key);
	}
}
