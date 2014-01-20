package com.bluesky.visualprogramming.remote.http;

import java.io.IOException;
import java.io.StringReader;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
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
import com.bluesky.visualprogramming.core.serialization.dump.ObjectSerializer;
import com.bluesky.visualprogramming.core.serialization.rpc.MessageFormatType;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.remote.ConnectionOptions;
import com.bluesky.visualprogramming.remote.ProtocolType;
import com.bluesky.visualprogramming.remote.RemoteAddress;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class HttpOutgoingAgent {
	static Logger logger = Logger.getLogger(HttpOutgoingAgent.class);

	public static final String ROOT_USER = "ROOT";

	ProtocolType protocol;

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

	String messageFormat;
	ObjectSerializer serializer;

	/**
	 * 
	 * @param address
	 *            local proxy object
	 * @param connectionOptions
	 */

	public HttpOutgoingAgent(ProtocolType protocol, String address,
			String connectionOptions) {
		this.protocol = protocol;

		RemoteAddress addr = RemoteAddress.valueOf(address);

		this.username = addr.userId;
		this.server = addr.server;

		int defaultPort = protocol == ProtocolType.HTTP ? 80 : 443;
		this.port = addr.port >= 0 ? addr.port : defaultPort;

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

		if (optMap.containsKey("messageFormat"))
			messageFormat = optMap.get("messageFormat");
		else {
			messageFormat = "";
		}

		MessageFormatType type = MessageFormatType.getType(messageFormat);
		if (type != null)
			serializer = type.getSerializer();
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
		_Object returnValue;

		VirtualMachine vm = VirtualMachine.getInstance();
		if (serializer == null) {

			ObjectRepository repo = vm.getObjectRepository();
			StringValue returnStringValue = (StringValue) repo.createObject(
					ObjectType.STRING, ObjectScope.ExecutionContext);

			returnStringValue.setValue(responseBody);

			returnValue = returnStringValue;
		} else {
			StringReader sr = new StringReader(responseBody);
			returnValue = serializer.deserialize(sr);
		}

		Message replyMsg = new Message(false, message.receiver, message.sender,
				"RE:" + message.getSubject(), returnValue,
				ParameterStyle.ByName, null, MessageType.SyncReply);

		replyMsg.urgent = true;

		vm.getPostService().sendMessage(replyMsg);
	}

	private String populateUrl(Message message, RemoteAddress ra) {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(protocol.name().toLowerCase());
		sbUrl.append("://");
		sbUrl.append(ra.server);

		if (ra.port >= 0)
			sbUrl.append(":" + String.valueOf(ra.port));

		if (!ra.userId.equals(ROOT_USER))
			sbUrl.append("/" + ra.userId);

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
