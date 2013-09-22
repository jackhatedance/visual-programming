package com.bluesky.visualprogramming.remote.http;

import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core._Object;

/**
 * an agent for each session.
 * 
 * @author jack
 * 
 */
public class HttpAgent {
	static Logger logger = Logger.getLogger(HttpAgent.class);

	private _Object responseBody;
	/**
	 * used to indicate response is ready.
	 */
	private Semaphore responseReady = new Semaphore(0);

	public void send(String receiverAddress, Message msg) {

		if (logger.isDebugEnabled())
			logger.debug("sending message to " + receiverAddress);

		String username = getUsername(receiverAddress);
		if (!username.startsWith(MessageServlet.VISITOR_PREFIX)) {
			throw new RuntimeException("receiver is not visitor:" + username);
		}

		responseBody = msg.body;
		responseReady.release();

	}

	protected String getUsername(String address) {
		String[] ss = address.split("@");
		return ss[0];
	}

	public void waitForResponse() throws InterruptedException {

		responseReady.acquire();

	}
	
	public String getResponse(){
		if(responseBody!=null)
			return responseBody.getValue();
		else
			return "";
	}
}
