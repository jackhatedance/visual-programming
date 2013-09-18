package com.bluesky.visualprogramming.remote.http;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageType;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.StringValue;

/**
 * receive message(HTTPrequest) and forward to local object. then return
 * response.
 * 
 * @author jackding
 * 
 */
public class HttpContextHandler extends AbstractHandler {

	public static final String VISITOR = "visitor";

	final String context;

	StringValue responseBody;
	/**
	 * used to indicate response is ready.
	 */
	private Object responseReady = new Object();

	public HttpContextHandler(String context) {
		this.context = context;

	}

	/**
	 * e.g. a http://x.com/a request send to http://a@x.com/. then the it reply
	 * to http://guestOfA@x.com
	 */
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// parse parameters

		// wait for response
		responseReady.wait();

		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		response.getWriter().write(responseBody.getValue());

		Message replyMsg = new Message(false, lastRequestMessage.receiver,
				lastRequestMessage.sender, "RE:"
						+ lastRequestMessage.getSubject(), returnValue,
				ParameterStyle.ByName, null, MessageType.SyncReply);

		replyMsg.urgent = true;

		// it can only be replied once.
		lastRequestMessage = null;

		vm.getPostService().sendMessage(replyMsg);

	}

	public void setResponse(StringValue responseBody) {
		this.responseBody = responseBody;

		responseReady.notify();

	}
}
