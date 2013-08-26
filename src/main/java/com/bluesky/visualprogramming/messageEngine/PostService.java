package com.bluesky.visualprogramming.messageEngine;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core._Object;

public class PostService {
	static Logger logger = Logger.getLogger(PostService.class);

	private ObjectRepository objectRepository;
	private WorkerManager workerManager;

	public void init(ObjectRepository objectRepository,
			WorkerManager workerManager) {
		this.objectRepository = objectRepository;
		this.workerManager = workerManager;
	}

	public void sendMessage(Message msg) {
		boolean applyWorkerForMe = msg.receiver.addToMessageQueue(msg);
		logger.debug(String.format(
				"post office send message from '%s' to '%s' subject '%s'",
				msg.sender, msg.receiver, msg.subject));
		if (applyWorkerForMe) {
			workerManager.addCustomer(msg.receiver);
		}
	}

	public void sendMessageFromNobody(_Object receiver, String subject) {

		Message msg = new Message(false, null, receiver, subject, null,
				ParameterStyle.ByName, null);
		sendMessage(msg);
	}
}
