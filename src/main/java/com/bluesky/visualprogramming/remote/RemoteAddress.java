package com.bluesky.visualprogramming.remote;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * format: {protocol}://{username}@{server}:[port];
 * 
 * port is optional.
 * 
 * example: http://jack@webserver:8080
 * 
 * @author jack
 * 
 */
public class RemoteAddress {

	public String protocol;
	public String username;
	public String server;
	public int port;

	public RemoteAddress(String protocol, String username, String server,
			int port) {
		this.protocol = protocol;
		this.username = username;
		this.server = server;
		this.port = port;

	}

	public static RemoteAddress valueOf(String fullAddress) {
		String patternStr = "((?<protocol>\\w+)://)?(?<username>\\w+)@(?<server>\\w+)(:(?<port>\\d+))?";
		Pattern pattern = Pattern.compile(patternStr,
				Pattern.UNICODE_CHARACTER_CLASS);
		Matcher matcher = pattern.matcher(fullAddress);

		if (matcher.matches()) {

			String protocol = matcher.group("protocol");
			String username = matcher.group("username");
			String server = matcher.group("server");
			String portStr = matcher.group("port");

			int port = -1;
			if (portStr != null && !portStr.trim().isEmpty()) {
				try {
					port = Integer.valueOf(portStr);
				} catch (NumberFormatException e) {

				}
			}
			RemoteAddress obj = new RemoteAddress(protocol, username, server,
					port);
			return obj;

		} else

			return null;

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (protocol != null)
			sb.append(protocol + "://");

		sb.append(username + "@" + server);

		if (port >= 0)
			sb.append(":" + port);

		return sb.toString();
	}

}
