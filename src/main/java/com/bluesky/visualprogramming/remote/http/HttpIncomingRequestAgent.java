package com.bluesky.visualprogramming.remote.http;

import java.io.StringWriter;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.serialization.rpc.ConfigurableObjectSerializer;
import com.bluesky.visualprogramming.core.serialization.rpc.MessageFormat;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.utils.Config;

/**
 * an agent for each incoming HTTP request.
 * 
 * It is created by Servlet in case of incoming session;
 * 
 * @author jack
 * 
 */
public class HttpIncomingRequestAgent {
	static Logger logger = Logger.getLogger(HttpIncomingRequestAgent.class);

	private MessageFormat responseContentFormat;

	private String textResponse;
	/**
	 * used to indicate response is ready.
	 */
	private Semaphore responseReady = new Semaphore(0);

	public HttpIncomingRequestAgent(MessageFormat responseContentFormat) {

		this.responseContentFormat = responseContentFormat;
	}

	public void send(String receiverAddress, Message msg) {

		if (logger.isDebugEnabled()) {
			logger.debug("sending message to " + receiverAddress);
		}

		String username = getUsername(receiverAddress);
		if (!username.startsWith(MessageServlet.VISITOR_PREFIX)) {
			throw new RuntimeException("receiver is not visitor:" + username);
		}

		if (msg.body instanceof StringValue) {
			textResponse = msg.body.getValue();

			if (logger.isDebugEnabled()) {
				logger.debug("response is: " + textResponse);
			}
		} else {

			if (logger.isDebugEnabled())
				logger.debug("responseContentFormat is: "
						+ responseContentFormat.name());

			ConfigurableObjectSerializer serializer = responseContentFormat
					.getSerializer();

			StringWriter sw = new StringWriter();
			Config config = new Config();
			try {
				serializer.serialize(msg.body, sw, config);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (logger.isDebugEnabled())
				logger.debug("serialization complete:" + sw.toString());

			textResponse = sw.toString();
		}

		responseReady.release();
	}

	protected String getUsername(String address) {
		String[] ss = address.split("@");
		return ss[0];
	}

	public void waitForResponse() throws InterruptedException {

		responseReady.acquire();

	}

	public String getResponse() {
		return textResponse;
	}

}
