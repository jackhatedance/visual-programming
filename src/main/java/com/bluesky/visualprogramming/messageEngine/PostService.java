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

public class PostService implements Runnable {
	static Logger logger = Logger.getLogger(PostService.class);

	private ObjectRepository objectRepository;
	private WorkerManager workerManager;
	private Communicator communicator;

	private BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<Message>();

	public void init(ObjectRepository objectRepository,
			WorkerManager workerManager) {
		this.objectRepository = objectRepository;
		this.workerManager = workerManager;
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

	private _Object seekRealObject(_Object obj){
		if(obj instanceof Link)
		{
			//TODO
			
			return null;
		}else
			return obj;
	}
	
	private void _sendMessage(Message msg) {
		//add support of link object
		if(msg.receiver instanceof Link	)
		{
			SoftLink receiverLink =  (SoftLink)msg.receiver;
			
			_Object target = objectRepository.getObjectByUri(receiverLink.getValue());
			//if(target!=null)
			
			//TODO
				
			//else let communicator send it to remote system.
			
		}
		
		
		boolean applyWorkerForMe = msg.receiver.addToMessageQueue(msg);

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

		while (true) {
			Message msg;
			try {
				msg = messageQueue.take();
				_sendMessage(msg);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);

			}

		}
	}
}
