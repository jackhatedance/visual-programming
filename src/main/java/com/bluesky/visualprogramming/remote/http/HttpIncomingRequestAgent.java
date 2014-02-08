package com.bluesky.visualprogramming.remote.http;

import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.Link;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.remote.callback.Callback;
import com.bluesky.visualprogramming.vm.VirtualMachine;

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

		// TODO convert to _Object to HTML
		VirtualMachine vm = VirtualMachine.getInstance();
		ObjectRepository repo = vm.getObjectRepository();

		String address = "path://_root.lib.web.render@local";
		Link receiverLink = (Link) repo.createObject(ObjectType.LINK,
				ObjectScope.ExecutionContext);
		receiverLink.setValue(address);

		_Object params = repo.createObject(ObjectType.NORMAL,
				ObjectScope.ExecutionContext);
		params.setField("target", msg.body, false);

		vm.getPostService().sendMessageFromNobody(receiverLink, "do", params,
				new Callback() {

					@Override
					public void onComplete(_Object result) {
						responseBody = result;
						logger.debug("response is: " + responseBody);

						responseReady.release();

					}
				});

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
