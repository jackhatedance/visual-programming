package com.bluesky.visualprogramming.remote.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.parse.ANTLRParser.exceptionGroup_return;
import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.remote.AbstractProtocolService;
import com.bluesky.visualprogramming.remote.ConnectionOptions;
import com.bluesky.visualprogramming.remote.ProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolType;
import com.bluesky.visualprogramming.remote.ssh.SshAgent;
import com.bluesky.visualprogramming.remote.xmpp.XmppAgent;

public class HttpService extends AbstractProtocolService implements
		ProtocolService {
	static Logger logger = Logger.getLogger(HttpService.class);

	private ProtocolType type = ProtocolType.HTTP;

	// key is address, value is object
	BidiMap addressObjectMap = new DualHashBidiMap();

	/**
	 * key is address of visitor, <PREFIX>_sessionId_requestId@host.
	 * 
	 * requestId is a random number.
	 */
	Map<String, HttpIncomingRequestAgent> incomingRequestAgents = new HashMap<String, HttpIncomingRequestAgent>();
	Map<String, HttpOutgoingAgent> outgoingRequestAgents = new HashMap<String, HttpOutgoingAgent>();

	public HttpService() {
		Server server = new Server(8080);

		ServletContextHandler context = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);

		context.addServlet(new ServletHolder(new MessageServlet(this)), "/*");

		try {
			logger.info("HTTP server starting");
			server.start();

		} catch (Exception e) {

			logger.error("failed to start web server", e);
		}

	}

	@Override
	public void register(String address, _Object obj, String connectionOptions) {
		if (addressObjectMap.containsKey(address))
			throw new RuntimeException("already registered:" + address);

		addressObjectMap.put(address, obj);

		ConnectionOptions opts = new ConnectionOptions(connectionOptions);

		boolean authOnly = false;

		String authOnlyStr;
		if (opts.map.containsKey("authOnly")) {
			authOnlyStr = opts.map.get("authOnly");
			try {
				authOnly = Boolean.valueOf(authOnlyStr);
			} catch (Exception e) {
				logger.error(e);
			}
		}

		if (authOnly) {
			HttpOutgoingAgent outAgent = new HttpOutgoingAgent(address,
					connectionOptions);
			outgoingRequestAgents.put(address, outAgent);

			logger.info(address + " is authOnly.");
		}
		else
			logger.info(address + " is not authOnly.");

	}

	@Override
	public _Object getLocalObject(String address) {

		return (_Object) addressObjectMap.get(address);
	}

	public String getAddress(_Object obj) {

		return (String) addressObjectMap.getKey(obj);
	}

	@Override
	public void send(String receiverAddress, Message message) {

		HttpIncomingRequestAgent requestAgent = incomingRequestAgents
				.get(receiverAddress);

		try {
			if (requestAgent != null) {
				if (logger.isDebugEnabled())
					logger.debug("find agent for " + receiverAddress);
				// it is a response for incoming request
				requestAgent.send(receiverAddress, message);

				// remove it since request is completed
				incomingRequestAgents.remove(requestAgent);
			} else {
				if (logger.isDebugEnabled())
					logger.debug("cannot find agent for " + receiverAddress
							+ ", it is an active request");

				// for http request need authentication, we use auth info from
				// the sender.
				String senderAddress = getAddress(message.sender);
				HttpOutgoingAgent outgoingAgent = outgoingRequestAgents
						.get(senderAddress);

				try {
					outgoingAgent.send(receiverAddress, message);

				} catch (Exception e) {
					logger.error(e);
					throw new RuntimeException(e);
				}

			}
		} catch (Exception e) {

			throw new RuntimeException(e);
		}
	}

	@Override
	public ProtocolType getType() {

		return type;
	}

	public void setAgent(String sessionId, HttpIncomingRequestAgent agent) {
		incomingRequestAgents.put(sessionId, agent);
	}

	public HttpIncomingRequestAgent getAgent(String sessionId) {
		return incomingRequestAgents.get(sessionId);
	}

}
