package com.bluesky.visualprogramming.messageEngine;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageType;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.link.SoftLink;
import com.bluesky.visualprogramming.remote.ProtocolType;
import com.bluesky.visualprogramming.remote.RemoteCommunicationService;
import com.bluesky.visualprogramming.remote.protocol.xmpp.XmppService;

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

		remoteCommunicationService = new RemoteCommunicationService(
				objectRepository);

		XmppService xmppService = new XmppService();
		remoteCommunicationService.addProtocolService(xmppService);
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
		if (msg.receiver instanceof SoftLink) {
			SoftLink receiverLink = (SoftLink) msg.receiver;

			ProtocolType protocol = ProtocolType.valueOf(receiverLink
					.getProtocol().toUpperCase());
			_Object localObject = remoteCommunicationService.getLocalObject(
					protocol, receiverLink.getAddress());

			logger.debug("receiver is link:" + receiverLink.getValue());

			if (localObject != null) {
				logger.debug("localObject is not null");
				
				//update link with pointer
				msg.receiver = localObject;
				
				sendLocalMessage(msg, localObject);
			} else {
				logger.debug("localObject is null");
				remoteCommunicationService.send(protocol,
						receiverLink.getAddress(), msg);

			}
		} else
			sendLocalMessage(msg, msg.receiver);
	}

	private void sendLocalMessage(Message msg, _Object receiver) {
		boolean applyWorkerForMe = receiver.addToMessageQueue(msg);

		if (logger.isDebugEnabled())
			logger.debug(String.format(
					"post office send message from: '%s' to: '%s' content: %s",
					msg.sender, msg.receiver, msg.toString()));

		if (applyWorkerForMe) {
			workerManager.addCustomer(receiver);
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

			}

		}

		logger.debug("thread terminated.");
	}

	public void stop() {
		this.running = false;

	}

}
