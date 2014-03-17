package com.bluesky.visualprogramming.remote.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.remote.AbstractProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolType;
import com.bluesky.visualprogramming.remote.RemoteAddress;
import com.bluesky.visualprogramming.utils.Config;

public class HttpService extends AbstractProtocolService implements
		ProtocolService {
	static Logger logger = Logger.getLogger(HttpService.class);


	/**
	 * key is address of visitor, <PREFIX>_sessionId_requestId@host.
	 * 
	 * requestId is a random number.
	 */
	Map<String, HttpIncomingRequestAgent> incomingRequestAgents = new HashMap<String, HttpIncomingRequestAgent>();
	/**
	 * key is local object, domain/host, value of http client agent.
	 */
	Map<_Object, Map<String, HttpClientAgent>> clientAgents = new HashMap<_Object, Map<String, HttpClientAgent>>();

	public HttpService() {
		supportedTypes = new ProtocolType[] { ProtocolType.HTTP,
				ProtocolType.HTTPS };

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

	private void addClientAgent(_Object obj, String remoteHost,
			HttpClientAgent agent) {
		if (!clientAgents.containsKey(obj))
			clientAgents.put(obj, new HashMap<String, HttpClientAgent>());

		Map<String, HttpClientAgent> protocolAgents = clientAgents.get(obj);
		protocolAgents.put(remoteHost, agent);
	}

	private HttpClientAgent getClientAgent(_Object sender, String receiverHost) {
		Map<String, HttpClientAgent> protocolAgents = clientAgents.get(sender);
		if (protocolAgents != null) {
			HttpClientAgent agent = protocolAgents.get(receiverHost);
			return agent;
		}

		return null;

	}

	@Override
	public void register(ProtocolType protocol, String address, _Object obj,
			Config config) {

		// true if act as HTTP client, not server.
		boolean clientOnly = config.getBoolean("clientOnly", false);

		if (clientOnly) {
			logger.info(address + " is client only.");
			HttpClientAgent clientAgent = new HttpClientAgent(this, protocol,
					address, config);
			RemoteAddress ra = RemoteAddress.valueOf(address);
			addClientAgent(obj, ra.server, clientAgent);
		} else {
			// add address local-object mapping table
			logger.info(address + " is server mode.");
			// cannot register same address on local machine.
			if (addressObjectMap.containsKey(address))
				throw new RuntimeException("already registered:" + address);

			addressObjectMap.put(address, obj);
			addressConfigMap.put(address, config);
		}
	}

	@Override
	public _Object getLocalObject(String address) {

		return (_Object) addressObjectMap.get(address);
	}



	protected void sendReply(String receiverAddress, Message message) {
		// it is a reply
		HttpIncomingRequestAgent requestAgent = incomingRequestAgents
				.get(receiverAddress);

		if (requestAgent != null) {
			// find a correspondent agent for the receiver address, means
			// that it is a reply of a HTTP (browser) request.

			if (logger.isDebugEnabled())
				logger.debug("find agent for " + receiverAddress);
			// it is a response for incoming request
			requestAgent.send(receiverAddress, message);

			// remove it since request is completed
			incomingRequestAgents.remove(requestAgent);
		} else {
			// missing agent, timeout?
			throw new RuntimeException("missing agent");
		}
	}

	protected void sendRequest(String receiverAddress, Message message) {
		try {

			// for http request need authentication, we use auth info from
			// the sender.protocol
			RemoteAddress ra = RemoteAddress.valueOf(receiverAddress);
			HttpClientAgent clientAgent = getClientAgent(message.sender,
					ra.server);

			if (clientAgent == null) {
				//create an agent for it. but no auth supported.
				Map<String,String> map = new HashMap<String,String>();
				map.put("clientOnly", "true");
				Config config = new Config(map);
				String fakeAddr = "anonymous@"+ra.server;
				register(ProtocolType.HTTP, fakeAddr, message.sender, config);

				if (logger.isDebugEnabled())
					logger.debug("create http client agent for remote address:"
							+ receiverAddress);

				clientAgent = getClientAgent(message.sender, ra.server);
			}
		
			clientAgent.send(receiverAddress, message);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void send(String receiverAddress, Message message) {

		if (message.isReply()) {
			sendReply(receiverAddress, message);
		} else {
			// it is a active HTTP request to remote web server.
			sendRequest(receiverAddress, message);
		}
	}

	public void setAgent(String sessionId, HttpIncomingRequestAgent agent) {
		incomingRequestAgents.put(sessionId, agent);
	}

	public HttpIncomingRequestAgent getAgent(String sessionId) {
		return incomingRequestAgents.get(sessionId);
	}

}
