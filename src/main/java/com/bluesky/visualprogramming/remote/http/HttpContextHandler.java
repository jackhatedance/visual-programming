package com.bluesky.visualprogramming.remote.http;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.bluesky.visualprogramming.core.Link;
import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageType;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.VirtualMachine;

/**
 * receive message(HTTPrequest) and forward to local object. then return
 * response.
 * 
 * @author jackding
 * 
 */
public class HttpContextHandler extends AbstractHandler {
	static Logger logger = Logger.getLogger(HttpContextHandler.class);

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
	 * to http://visitor@x.com
	 */
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		VirtualMachine vm = VirtualMachine.getInstance();
		ObjectRepository repo = vm.getObjectRepository();

		// parser receiver address, e.g. http://x.com/username/subject

		if (target.indexOf('/') == 0)
			target = target.substring(1);

		int index = target.indexOf('/');
		String username = target.substring(0, index);
		String subject = target.substring(index + 1);
		String server = request.getServerName();

		Link receiverLink = (Link) repo.createObject(ObjectType.LINK,
				ObjectScope.ExecutionContext);
		receiverLink.setValue("http://" + username + "@" + server);

		Link senderLink = (Link) repo.createObject(ObjectType.LINK,
				ObjectScope.ExecutionContext);
		senderLink.setValue("http://" + VISITOR + "@" + server);

		// TODO: parse HTTP parameters

		Message incomingMsg = new Message(true, receiverLink, senderLink,
				subject, null, ParameterStyle.ByName, null, MessageType.Normal);

		vm.getPostService().sendMessage(incomingMsg);

		// wait for response
		try {
			responseReady.wait();
		} catch (InterruptedException e) {

			logger.error("HTTP wait for response error.", e);
		}

		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		response.getWriter().write(responseBody.getValue());

	}

	public void setResponse(StringValue responseBody) {
		this.responseBody = responseBody;

		responseReady.notify();

	}
}
