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
import com.bluesky.visualprogramming.remote.ProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolType;

public class HttpService implements ProtocolService {
	static Logger logger = Logger.getLogger(HttpService.class);

	private ProtocolType type = ProtocolType.HTTP;

	// key is address, value is object
	BidiMap addressObjectMap = new DualHashBidiMap();

	// key is address of visitor, <PREFIX>_sessionId@host.
	Map<String, HttpAgent> agents = new HashMap<String, HttpAgent>();

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

		HttpAgent agent = agents.get(receiverAddress);

		try {
			if (agent != null) {
				if (logger.isDebugEnabled())
					logger.debug("find agent for " + receiverAddress);
				// it is a response for incoming request
				agent.send(receiverAddress, message);
			} else {
				if (logger.isDebugEnabled())
					logger.debug("cannot find agent for " + receiverAddress
							+ ", it is a active request");

				// it is an active request outgoing
				// TODO use HttpClient lib
			}
		} catch (Exception e) {

			throw new RuntimeException(e);
		}
	}

	@Override
	public ProtocolType getType() {

		return type;
	}

	public void setAgent(String sessionId, HttpAgent agent) {
		agents.put(sessionId, agent);
	}

	public HttpAgent getAgent(String sessionId) {
		return agents.get(sessionId);
	}

}
