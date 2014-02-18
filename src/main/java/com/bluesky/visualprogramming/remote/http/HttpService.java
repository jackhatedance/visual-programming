package com.bluesky.visualprogramming.remote.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
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
	 * store address of local object
	 * 
	 * key is address, value is object
	 */
	BidiMap addressObjectMap = new DualHashBidiMap();

	/**
	 * not a http server
	 */
	boolean clientOnly = false;

	/**
	 * key is address of visitor, <PREFIX>_sessionId_requestId@host.
	 * 
	 * requestId is a random number.
	 */
	Map<String, HttpIncomingRequestAgent> incomingRequestAgents = new HashMap<String, HttpIncomingRequestAgent>();
	/**
	 * key is userId without host name, because we don't want to assume the
	 * domain name. So foo@a.com is same with foo2b.com. because they has same
	 * user ID.
	 */
	Map<String, HttpOutgoingAgent> outgoingRequestAgents = new HashMap<String, HttpOutgoingAgent>();

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

	@Override
	public void register(ProtocolType protocol, String address, _Object obj,
			Config config) {


		//true if act as HTTP client, not server. 
		clientOnly = config.getBoolean("clientOnly", false);

		HttpOutgoingAgent outAgent = new HttpOutgoingAgent(protocol, address,
				config);
		outgoingRequestAgents.put(address, outAgent);

		logger.info(address + " is client only.");

		if (!clientOnly) {
			// add address local-object mapping table

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

	public String getAddress(_Object obj) {

		return (String) addressObjectMap.getKey(obj);
	}

	private String getId(String address) {
		RemoteAddress ra = RemoteAddress.valueOf(address);
		return ra.userId;
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
				String senderAddress = getAddress(message.sender);
				HttpOutgoingAgent outgoingAgent = outgoingRequestAgents
						.get(senderAddress);

				outgoingAgent.send(receiverAddress, message);

			} catch (Exception e) {
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
