package com.bluesky.visualprogramming.messageEngine;

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
import com.bluesky.visualprogramming.remote.email.EmailService;
import com.bluesky.visualprogramming.remote.http.HttpService;
import com.bluesky.visualprogramming.remote.path.PathService;
import com.bluesky.visualprogramming.remote.ssh.SshService;
import com.bluesky.visualprogramming.remote.xmpp.XmppService;
import com.bluesky.visualprogramming.vm.Service;

public class PostService implements Runnable, Service {
	static Logger logger = Logger.getLogger(PostService.class);

	private ObjectRepository objectRepository;
	private WorkerManager workerManager;
	private RemoteCommunicationService remoteCommunicationService;

	private BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<Message>();

	
	//rpc sender threads
	ExecutorService executorService = Executors.newFixedThreadPool(5);
	
	private volatile boolean running = true;

	public void init(ObjectRepository objectRepository,
			WorkerManager workerManager) {
		this.objectRepository = objectRepository;
		this.workerManager = workerManager;

		remoteCommunicationService = new RemoteCommunicationService(
				objectRepository);

		remoteCommunicationService.addProtocolService(new CallbackService());
		remoteCommunicationService.addProtocolService(new PathService());

		remoteCommunicationService.addProtocolService(new XmppService());
		remoteCommunicationService.addProtocolService(new SshService());
		remoteCommunicationService.addProtocolService(new HttpService());
		remoteCommunicationService.addProtocolService(new EmailService());
	}

	public void  sendMessage(Message msg) {
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

		ProtocolType protocol = ProtocolType.valueOf(link
				.getProtocol().toUpperCase());
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
		//RPC is time consuming, additional thread is required
		
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
			workerManager.addCustomer(receiver);
		}

	}

	/**
	 * 
	 * @param receiver
	 * @param subject
	 * @param body optional
	 * @param callback
	 *            optional
	 */
	public void sendMessageFromNobody(_Object receiver, String subject, _Object body,
			Callback callback) {

		// make a random
		String id = UUID.randomUUID().toString();
		String fullAddress = String.format("callback://%s@local", id);
		Link senderLink = (Link) objectRepository.createObject(ObjectType.LINK,
				ObjectScope.ExecutionContext);
		senderLink.setValue(fullAddress);

		CallbackService cbSvc = (CallbackService) remoteCommunicationService
				.getService(ProtocolType.CALLBACK);

		cbSvc.setCallback(senderLink.getAddress(), callback);

		Message msg = new Message(true, senderLink, receiver, subject, body,
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

		if (logger.isDebugEnabled())
			logger.debug("thread terminated.");
	}

	public void stop() {
		this.running = false;

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
