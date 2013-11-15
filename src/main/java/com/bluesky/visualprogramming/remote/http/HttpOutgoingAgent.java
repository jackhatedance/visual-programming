package com.bluesky.visualprogramming.remote.http;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageType;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.remote.ConnectionOptions;
import com.bluesky.visualprogramming.remote.RemoteAddress;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class HttpOutgoingAgent {
	static Logger logger = Logger.getLogger(HttpOutgoingAgent.class);

	public static final String ROOT_USER = "ROOT";

	String username;
	String password;

	/*
	 * a.k.a. server name
	 */
	String server;
	int port;
	/**
	 * Basic, NTLM,...etc.
	 */
	String authType;

	CredentialsProvider credsProvider;

	/**
	 * 
	 * @param address
	 *            local proxy object
	 * @param connectionOptions
	 */

	public HttpOutgoingAgent(String address, String connectionOptions) {

		RemoteAddress addr = RemoteAddress.valueOf(address);

		this.username = addr.username;
		this.server = addr.server;
		this.port = addr.port >= 0 ? addr.port : 80;

		Map<String, String> optMap = new ConnectionOptions(connectionOptions).map;
		if (optMap.containsKey("password"))
			password = optMap.get("password");
		else {
			password = "";
		}

		if (optMap.containsKey("authType"))
			authType = optMap.get("authType");
		else {
			if (password != null && !password.trim().isEmpty())
				authType = AuthType.Basic.toString();
			else
				authType = null;
		}

	}

	public void send(String receiverAddress, Message message)
			throws ClientProtocolException, IOException {

		RemoteAddress ra = RemoteAddress.valueOf(receiverAddress);
		if (ra == null) {
			String errorMsg = "invalid receiver address:" + receiverAddress;
			if (logger.isDebugEnabled())
				logger.debug(errorMsg);

			throw new RuntimeException(errorMsg);
		}

		String url = populateUrl(message, ra);

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(server, port),
				new UsernamePasswordCredentials(username, password));
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCredentialsProvider(credsProvider).build();

		try {
			HttpGet httpget = new HttpGet(url);

			if (logger.isDebugEnabled())
				logger.debug("executing request" + httpget.getRequestLine());
			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpclient.execute(httpget, responseHandler);

			reply(message, responseBody);

		} finally {
			httpclient.close();
		}

	}

	private void reply(Message message, String responseBody) {
		VirtualMachine vm = VirtualMachine.getInstance();
		ObjectRepository repo = vm.getObjectRepository();
		StringValue returnValue = (StringValue) repo.createObject(
				ObjectType.STRING, ObjectScope.ExecutionContext);

		// TODO convert msg.Body to _Object.
		returnValue.setValue(responseBody);

		Message replyMsg = new Message(false, message.receiver, message.sender,
				"RE:" + message.getSubject(), returnValue,
				ParameterStyle.ByName, null, MessageType.SyncReply);

		replyMsg.urgent = true;

		vm.getPostService().sendMessage(replyMsg);
	}

	private String populateUrl(Message message, RemoteAddress ra) {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append("http");
		sbUrl.append("://");
		sbUrl.append(ra.server);

		if (ra.port >= 0)
			sbUrl.append(":" + String.valueOf(ra.port));

		if (!ra.username.equals(ROOT_USER))
			sbUrl.append("/" + ra.username);

		sbUrl.append("/");
		sbUrl.append(message.getSubject());

		if (message.body != null) {
			_Object body = message.body;
			sbUrl.append("?");
			for (String cname : body.getChildrenNames()) {
				_Object field = body.getChild(cname);
				sbUrl.append(cname);
				sbUrl.append("=");
				sbUrl.append(field.getValue());
			}
		}
		return sbUrl.toString();
	}

}
