package com.bluesky.visualprogramming.messageEngine;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core._Object;

public class PostService {

	private ObjectRepository objectRepository;
	private WorkerManager workerManager;

	public void init(ObjectRepository objectRepository,
			WorkerManager workerManager) {
		this.objectRepository = objectRepository;
		this.workerManager = workerManager;
	}

	public void sendMessage(Message msg) {
		boolean needWorker = msg.receiver.addToMessageQueue(msg);
		if (needWorker)
		{			
			workerManager.addCustomer(msg.receiver);
		}
	}

	public void sendMessageFromNobody(_Object receiver, String subject) {

		Message msg = new Message(false, null, receiver, subject, null,ParameterStyle.ByName,null);
		sendMessage(msg);
	}
}
