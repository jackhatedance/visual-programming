package com.bluesky.visualprogramming.remote.http;

import java.io.StringWriter;
import java.util.concurrent.Semaphore;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.serialization.rpc.ConfigurableObjectSerializer;
import com.bluesky.visualprogramming.core.serialization.rpc.MessageFormat;
import com.bluesky.visualprogramming.core.value.StringValue;

/**
 * an agent for each request.
 * 
 * It is created by Servlet in case of incoming session;
 * 
 * @author jack
 * 
 */
public class HttpIncomingRequestAgent {
	static Logger logger = Logger.getLogger(HttpIncomingRequestAgent.class);

	private MessageFormat responseContentFormat;

	private _Object responseBody;
	/**
	 * used to indicate response is ready.
	 */
	private Semaphore responseReady = new Semaphore(0);

	private HttpServletResponse response;

	public HttpIncomingRequestAgent(HttpServletResponse response,
			MessageFormat responseContentFormat) {
		this.response = response;
		this.responseContentFormat = responseContentFormat;
	}

	public void send(String receiverAddress, Message msg) {

		if (logger.isDebugEnabled())
			logger.debug("sending message to " + receiverAddress);

		String username = getUsername(receiverAddress);
		if (!username.startsWith(MessageServlet.VISITOR_PREFIX)) {
			throw new RuntimeException("receiver is not visitor:" + username);
		}

		if (msg.body instanceof StringValue) {
			responseBody = msg.body;
			logger.debug("response is: " + responseBody);

		} else {

			ConfigurableObjectSerializer serializer = responseContentFormat
					.getSerializer();

			StringWriter sw = new StringWriter();
			serializer.serialize(msg.body, sw, null);

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
		if (responseBody != null) {
			StringValue sv = (StringValue) responseBody;
			return sv.getValue();
		} else
			return "";
	}

}
