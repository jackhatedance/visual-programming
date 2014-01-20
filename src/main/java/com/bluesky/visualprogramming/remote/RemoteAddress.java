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

	public ProtocolType protocol;
	public String userId;
	public String server;
	public int port = -1;

	public RemoteAddress() {
		protocol = ProtocolType.UNKNOWN;
		userId = "";
		server = "";
	}

	public RemoteAddress(String protocol, String userId, String server, int port) {
		this.protocol = ProtocolType.getType(protocol);
		this.userId = userId;
		this.server = server;
		this.port = port;

	}

	public static RemoteAddress valueOf(String fullAddress) {
		String patternStr = "((?<protocol>\\w+)://)?(?<username>[a-zA-Z0-9_.]+)?@(?<server>\\w+)(:(?<port>\\d+))?";
		Pattern pattern = Pattern.compile(patternStr,
				Pattern.UNICODE_CHARACTER_CLASS);
		Matcher matcher = pattern.matcher(fullAddress);

		if (matcher.matches()) {

			String protocol = matcher.group("protocol");
			String username = nullToEmpty(matcher.group("username"));
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
			sb.append(protocol.toString().toLowerCase() + "://");

		sb.append(userId + "@" + server);

		if (port >= 0)
			sb.append(":" + port);

		return sb.toString();
	}

	public String getAddress() {
		StringBuilder sb = new StringBuilder();

		sb.append(userId + "@" + server);

		if (port >= 0)
			sb.append(":" + port);

		return sb.toString();
	}

	private static String nullToEmpty(String s) {
		return s == null ? "" : s;
	}
}
