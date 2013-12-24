package com.bluesky.visualprogramming.vm;

import java.io.FileInputStream;
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
	Properties remoteSecurityConfig;

	public AppProperties() {
		appProperties = new Properties();
		try {
			appProperties.load(AppProperties.class
					.getResourceAsStream("/app.properties"));

			String remoteSecurityFile = appProperties
					.getProperty("remote.security.file");
			remoteSecurityConfig = new Properties();
			remoteSecurityConfig.load(new FileInputStream(remoteSecurityFile));

		} catch (Exception e) {
			logger.warn("app.properties not found.");			
		}

	}

	public Properties getRemoteSecurityConfig() {
		return remoteSecurityConfig;
	}

	public String getProperty(String key) {
		return appProperties.getProperty(key);
	}
}
