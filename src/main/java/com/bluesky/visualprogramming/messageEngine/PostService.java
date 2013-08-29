package com.bluesky.visualprogramming.messageEngine;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Link;
import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageType;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.link.SoftLink;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.remote.ProtocolType;
import com.bluesky.visualprogramming.remote.RemoteCommunicationService;

public class PostService implements Runnable {
	static Logger logger = Logger.getLogger(PostService.class);

	private ObjectRepository objectRepository;
	private WorkerManager workerManager;
	private RemoteCommunicationService remoteCommunicationService;

	private BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<Message>();

	private volatile boolean running = true;

	public void init(ObjectRepository objectRepository,
			WorkerManager workerManager) {
		this.objectRepository = objectRepository;
		this.workerManager = workerManager;

		createCommunicationAgents();
	}

	private void createCommunicationAgents() {
		// create agent for alias
		// linking
		for (_Object o : objectRepository.getAllObjects()) {

			System.out.println(o.getName());
			_Object aliases = o.getChild("_aliases");
			if (aliases != null) {
				for (int i = 0; i < aliases.getChildCount(); i++) {
					_Object alias = aliases.getChild(i);

					ProtocolType pt = ProtocolType.valueOf(((StringValue) alias
							.getChild("protocol")).getValue().toUpperCase());
					StringValue address = (StringValue) alias
							.getChild("address");
					StringValue connectionOptions = (StringValue) alias
							.getChild("connectionOptions");

					remoteCommunicationService.register(pt, address.getValue(),
							o, connectionOptions.getValue());
				}
			}

		}
	}

	public void sendMessage(Message msg) {
		try {
			messageQueue.put(msg);

			if (logger.isDebugEnabled())
				logger.debug(String
						.format("post office receieved request message from '%s' to '%s' content '%s'",
								msg.sender, msg.receiver, msg.toString()));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private void _sendMessage(Message msg) {
		// add support of link object
		if (msg.receiver instanceof Link) {
			SoftLink receiverLink = (SoftLink) msg.receiver;

			ProtocolType protocol = ProtocolType.valueOf(receiverLink
					.getProtocol().toUpperCase());
			_Object localObject = remoteCommunicationService.query(protocol,
					receiverLink.getAddress());

			if (localObject != null)
				sendLocalMessage(msg, localObject);
			else {
				remoteCommunicationService.send(protocol,
						receiverLink.getAddress(), msg);

			}
		}

		sendLocalMessage(msg, msg.receiver);
	}

	private void sendLocalMessage(Message msg, _Object receiver) {
		boolean applyWorkerForMe = receiver.addToMessageQueue(msg);

		if (logger.isDebugEnabled())
			logger.debug(String.format(
					"post office send message from: '%s' to: '%s' content: %s",
					msg.sender, msg.receiver, msg.toString()));

		if (applyWorkerForMe) {
			workerManager.addCustomer(msg.receiver);
		}

	}

	public void sendMessageFromNobody(_Object receiver, String subject) {

		Message msg = new Message(false, null, receiver, subject, null,
				ParameterStyle.ByName, null, MessageType.Normal);
		sendMessage(msg);
	}

	@Override
	public void run() {

		while (running) {
			Message msg;
			try {
				msg = messageQueue.take();
				_sendMessage(msg);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

		}
	}

	public void stop() {
		this.running = false;

	}

	public void recreateAgents(ObjectRepository objectRepository2) {
		// TODO Auto-generated method stub
		
	}
}
