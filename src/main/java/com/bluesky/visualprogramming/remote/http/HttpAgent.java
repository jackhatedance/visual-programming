package com.bluesky.visualprogramming.remote.http;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.value.StringValue;

public class HttpAgent {
	static Logger logger = Logger.getLogger(HttpAgent.class);

	HttpContextHandler handler;

	public HttpAgent(String address) {
		String username = "";
		handler = new HttpContextHandler("/" + username);

	}

	public void send(String receiverAddress, Message msg) {

		String username = getUsername(receiverAddress);
		if (username == handler.VISITOR) {
			handler.setResponse((StringValue) msg.body);
		} else {
			// use HTTP client
		}
	}

	protected String getUsername(String address) {
		String[] ss = address.split("@");
		return ss[0];
	}
}
