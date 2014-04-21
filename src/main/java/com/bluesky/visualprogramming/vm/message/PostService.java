package com.bluesky.visualprogramming.vm.message;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageType;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.Link;
import com.bluesky.visualprogramming.remote.ProtocolType;
import com.bluesky.visualprogramming.remote.RemoteCommunicationService;
import com.bluesky.visualprogramming.remote.callback.Callback;
import com.bluesky.visualprogramming.remote.callback.CallbackService;

public class PostService extends ThreadService implements Runnable {
	static Logger logger = Logger.getLogger(PostService.class);

	private ObjectRepository objectRepository;
	private WorkerService workerService;
	private RemoteCommunicationService remoteCommunicationService;

	private BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<Message>();

	// threads for RPC
	ExecutorService executorService = Executors.newFixedThreadPool(5);

	private volatile boolean running = true;

	public void setup(ObjectRepository objectRepository,
			WorkerService workerService) {
		this.objectRepository = objectRepository;
		this.workerService = workerService;

	}

	public void sendMessage(Message msg) {
		if (msg.receiver == null) {
			logger.error("msg has no receiver:" + msg.toString());
			return;
		}

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

	/**
	 * is there a local object claims this link
	 * 
	 * @param link
	 * @return
	 */
	public _Object getLocalObject(Link link) {

		ProtocolType protocol = ProtocolType.valueOf(link.getProtocol()
				.toUpperCase());
		_Object localObject = remoteCommunicationService.getLocalObject(
				protocol, link.getAddress());

		return localObject;
	}

	public boolean isLocal(Link link) {
		_Object obj = getLocalObject(link);
		return obj != null;
	}

	private void _sendMessage(Message msg) {

		// add support of link object
		if (msg.receiver instanceof Link) {
			Link receiverLink = (Link) msg.receiver;

			ProtocolType protocol = ProtocolType.valueOf(receiverLink
					.getProtocol().toUpperCase());
			_Object localObject = remoteCommunicationService.getLocalObject(
					protocol, receiverLink.getAddress());

			if (logger.isDebugEnabled())
				logger.debug("receiver is link:" + receiverLink.getValue());

			if (localObject != null) {
				if (logger.isDebugEnabled())
					logger.debug("localObject is not null");

				// update link with pointer
				msg.receiver = localObject;

				sendLocalMessage(msg, localObject);
			} else {
				if (logger.isDebugEnabled())
					logger.debug("localObject is null");

				sendRemoteMessage(msg, receiverLink, protocol);

			}
		} else
			sendLocalMessage(msg, msg.receiver);
	}

	private void sendRemoteMessage(final Message msg, final Link receiverLink,
			final ProtocolType protocol) {
		// RPC is time consuming, additional thread is required

		executorService.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				remoteCommunicationService.send(protocol,
						receiverLink.getAddress(), msg);
				return null;
			}
		});

	}

	private void sendLocalMessage(Message msg, _Object receiver) {
		boolean applyWorkerForMe = receiver.addToMessageQueue(msg);

		if (logger.isDebugEnabled())
			logger.debug(String.format(
					"post office send message from: '%s' to: '%s' content: %s",
					msg.sender, msg.receiver, msg.toString()));

		if (applyWorkerForMe) {
			workerService.addCustomer(receiver);
		}

	}

	/**
	 * 
	 * @param receiver
	 * @param subject
	 * @param body
	 *            optional
	 * @param callback
	 *            optional
	 */
	public void sendMessageFromNobody(_Object receiver, String subject,
			_Object body, Callback callback) {

		// make a random
		String id = UUID.randomUUID().toString();
		String fullAddress = String.format("callback://%s@local", id);
		Link senderLink = (Link) objectRepository.createObject(ObjectType.LINK,
				ObjectScope.ExecutionContext);
		senderLink.setValue(fullAddress);

		CallbackService cbSvc = (CallbackService) remoteCommunicationService
				.getService(ProtocolType.CALLBACK);

		cbSvc.setCallback(senderLink.getAddress(), callback);

		Message msg = new Message(senderLink, receiver, subject, body,
				ParameterStyle.ByName, null, MessageType.SyncRequest);
		sendMessage(msg);
	}

	@Override
	protected void doTask() throws InterruptedException {
		Message msg = messageQueue.take();
		_sendMessage(msg);

	}

	@Override
	public void init() {
		name = "PostService";

		remoteCommunicationService = new RemoteCommunicationService(
				objectRepository);

		super.init();
	}

}
