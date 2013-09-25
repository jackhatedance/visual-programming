package com.bluesky.visualprogramming.vm;

import java.io.FileInputStream;
import java.util.Properties;

public class AppProperties {
	static private AppProperties instance;

	public static AppProperties getInstance() {
		if (instance == null) {
			instance = new AppProperties();
		}

		return instance;
	}

	Properties remoteSecurityConfig;

	public AppProperties() {
		Properties appProperties = new Properties();
		try {
			appProperties.load(AppProperties.class
					.getResourceAsStream("/app.properties"));

			String remoteSecurityFile = appProperties
					.getProperty("remote.security.file");
			remoteSecurityConfig = new Properties();
			remoteSecurityConfig.load(new FileInputStream(remoteSecurityFile));

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public Properties getRemoteSecurityConfig() {
		return remoteSecurityConfig;
	}

}
