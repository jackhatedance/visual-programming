package com.bluesky.visualprogramming.remote.http;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageType;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.Link;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class MessageServlet extends HttpServlet {
	static Logger logger = Logger.getLogger(MessageServlet.class);

	public static final String VISITOR_PREFIX = "visitor_";

	HttpService service;

	public MessageServlet(HttpService service) {
		this.service = service;
	}


	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		VirtualMachine vm = VirtualMachine.getInstance();
		ObjectRepository repo = vm.getObjectRepository();

		// parser receiver address, e.g. http://x.com/username/subject
		String target = request.getPathInfo();

		if (target.indexOf('/') == 0)
			target = target.substring(1);

		int index = target.indexOf('/');
		if (index < 0) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		String username = target.substring(0, index);
		String subject = target.substring(index + 1);
		String server = request.getServerName();

		String sessionId = request.getSession(true).getId();

		Link receiverLink = (Link) repo.createObject(ObjectType.LINK,
				ObjectScope.ExecutionContext);
		String receiverAddress = username + "@" + server;
		String fullReceiverAddress = "http://" + receiverAddress;
		receiverLink.setValue(fullReceiverAddress);

		if (!vm.getPostService().isLocal(receiverLink)) {
			// receiver NOT FOUND
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		Link senderLink = (Link) repo.createObject(ObjectType.LINK,
				ObjectScope.ExecutionContext);

		// the session ID has no use here. remove it any time.
		String senderAddress = String.format("%s_%s_%s@%s", VISITOR_PREFIX,
				sessionId, getNextRequestId(), server);
		String fullSenderAddress = "http://" + senderAddress;
		senderLink.setValue(fullSenderAddress);

		if (logger.isDebugEnabled())
			logger.debug(String.format("a request from %s to %s, subject '%s'",
					senderAddress, receiverAddress, subject));

		// create agent if has not
		HttpIncomingRequestAgent agent = service.getAgent(senderAddress);
		if (agent == null) {
			agent = new HttpIncomingRequestAgent();
			service.setAgent(senderAddress, agent);

			if (logger.isDebugEnabled())
				logger.debug("create new agent for " + senderAddress);

		}

		//parse HTTP parameters
		_Object parameters = buildParamterObject(request);

		/*
		 * Assume it is a HTTP session with a browser, the return value should
		 * be HTML String other than _Object. so we send this request to a
		 * middle agent which will call the real receiver and convert the result
		 * to html.
		 */
		Message incomingMsg = new Message(true, senderLink, receiverLink,
				subject, parameters, ParameterStyle.ByName, null, MessageType.Normal);


		vm.getPostService().sendMessage(incomingMsg);

		// wait for response

		if (logger.isDebugEnabled())
			logger.debug("servlet waiting for response...");

		try {

			agent.waitForResponse();
			if (logger.isDebugEnabled())
				logger.debug("response is ready");

		} catch (InterruptedException e) {

			logger.error("HTTP wait for response error.", e);
		}

		if (logger.isDebugEnabled())
			logger.debug("servlet wakeup");

		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);

		response.getWriter().println(agent.getResponse());
		
	}

	private String getNextRequestId() {
		return UUID.randomUUID().toString();
	}
	
	private _Object buildParamterObject(HttpServletRequest request){
		VirtualMachine vm = VirtualMachine.getInstance();
		ObjectRepository repo = vm.getObjectRepository();

		_Object body = repo.createObject(ObjectType.NORMAL, ObjectScope.ExecutionContext);

		for(String key : request.getParameterMap().keySet()){
			StringValue svValue = (StringValue)repo.createObject(ObjectType.STRING, ObjectScope.ExecutionContext);
			String value = request.getParameter(key);
			
			svValue.setValue(value);
			body.setField(key, svValue, true);			
		}
		
		return body;
	}
}
