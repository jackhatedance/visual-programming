package com.bluesky.visualprogramming.vm;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.utils.Config;

public class AppProperties {
	static Logger logger = Logger.getLogger(AppProperties.class);

	public static final String AUTO_SAVE_INTERVAL = "autoSave.interval";
	
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

	public Config getAsConfig() {
		Map<String, String> map = new HashMap<String, String>();

		for (Object key : appProperties.keySet()) {
			String value = appProperties.getProperty((String) key);

			map.put((String) key, value);
		}

		return new Config(map);
	}
}
