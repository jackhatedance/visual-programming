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
	 * store address of local object. one object can have many addresses; but
	 * must be unique.
	 * 
	 * client-only aliases does not need to be here.
	 * 
	 * key is address, value is object
	 */
	Map<String, _Object> addressObjectMap = new HashMap<String, _Object>();

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
			HttpClientAgent clientAgent = new HttpClientAgent(protocol,
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

	private boolean isVisitor(String address) {
		return address.startsWith(MessageServlet.VISITOR_PREFIX);

	}

	@Override
	public void send(String receiverAddress, Message message) {

		if (isVisitor(receiverAddress)) {
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
		} else {
			// it is a active HTTP request to remote web server.
			try {

				if (logger.isDebugEnabled())
					logger.debug("cannot find agent for " + receiverAddress
							+ ", it is an active request");

				// for http request need authentication, we use auth info from
				// the sender.
				RemoteAddress ra = RemoteAddress.valueOf(receiverAddress);
				HttpClientAgent clientAgent = getClientAgent(message.sender, ra.server);
				
				if (clientAgent != null)
					clientAgent.send(receiverAddress, message);
				else
					throw new RuntimeException(
							"no http client agent for remote address:"
									+ receiverAddress);

			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
				throw new RuntimeException(e);
			}

		}
	}

	public void setAgent(String sessionId, HttpIncomingRequestAgent agent) {
		incomingRequestAgents.put(sessionId, agent);
	}

	public HttpIncomingRequestAgent getAgent(String sessionId) {
		return incomingRequestAgents.get(sessionId);
	}

}
