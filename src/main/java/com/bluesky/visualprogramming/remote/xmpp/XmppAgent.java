package com.bluesky.visualprogramming.remote.xmpp;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageType;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.Link;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.remote.AbstractProtocolService;
import com.bluesky.visualprogramming.remote.session.Session;
import com.bluesky.visualprogramming.utils.Config;
import com.bluesky.visualprogramming.vm.VirtualMachine;

/**
 * each agent has its unique UserID.
 * 
 * @author jack
 * 
 */
public class XmppAgent {

	static Logger logger = Logger.getLogger(XmppAgent.class);

	String server;
	String username;
	String password;

	XMPPConnection connection;

	MessageListener messageListener;

	// private SessionStatus sessionStatus=SessionStatus.Normal;

	/**
	 * a request sent from system object, wait for remote user response. that
	 * any incoming message from that userid(TODO or sessionID) is assumed to be
	 * it response.
	 */
	Map<String, Message> blockedOutgoingRequests = new HashMap<String, Message>();

	AbstractProtocolService service;

	public XmppAgent(AbstractProtocolService service, String address,
			_Object obj, Config config) {
		this.service = service;

		if (config.containsKey("server"))
			server = config.get("server");
		else {
			int idx = address.indexOf('@');
			server = address.substring(idx + 1);
		}

		if (config.containsKey("username"))
			username = config.get("username");
		else {
			int idx = address.indexOf('@');
			username = address.substring(0, idx);
		}
		password = config.getString("password", "");

		messageListener = new MessageListener() {
			@Override
			public void processMessage(Chat chat,
					org.jivesoftware.smack.packet.Message msg) {

				if (msg.getBody() != null && !msg.getBody().trim().isEmpty()) {
					receivedMessage(msg);
				}
			}

		};
	}

	public void connect() {
		try {

			ConnectionConfiguration config = new ConnectionConfiguration(server, 5222);
			config.setCompressionEnabled(true);
			config.setSASLAuthenticationEnabled(true);
			config.setReconnectionAllowed(true);
			
			
			connection = new XMPPConnection(config);

			connection.connect();

			connection.login(username, password);

			final ChatManager chatManager = connection.getChatManager();
			chatManager.addChatListener(new ChatManagerListener() {

				@Override
				public void chatCreated(Chat chat, boolean createdLocally) {
					chat.addMessageListener(getMessageListener());

				}
			});
		} catch (XMPPException e) {

			throw new RuntimeException(e);
		}
	}

	public void disconnect() {
		connection.disconnect();
	}

	protected void setBlockedOutgoingRequest(String receiverAddress,
			Message request) {
		if (blockedOutgoingRequests.containsKey(receiverAddress))
			throw new RuntimeException(
					"a blocked outgoing request already exsits for "
							+ receiverAddress);

		blockedOutgoingRequests.put(receiverAddress, request);
	}

	protected void removeBlockedOutgoingRequest(String receiverAddress) {
		blockedOutgoingRequests.remove(receiverAddress);
	}

	public void send(String receiverAddress, Message msg) throws XMPPException {

		if (msg.messageType == MessageType.SyncRequest)
			setBlockedOutgoingRequest(receiverAddress, msg);

		ChatManager chatManager = connection.getChatManager();
		Chat chat = chatManager.createChat(receiverAddress,
				getMessageListener());

		// TODO convert body to hierarchical text
		String msgBody = "";
		if (msg.body != null) {
			if (msg.body.getType() == ObjectType.NORMAL) {
				for (int i = 0; i < msg.body.getChildCount(); i++) {
					if (i != 0)
						msgBody += ";";

					_Object param = msg.body.getChild(i);
					msgBody += param.getName() + ":" + param.getValue();

				}
			} else if (msg.body.getType().isValueObject()) {
				msgBody = msg.body.getValue();

			}

		}

		// String response = String.format("[%s] %s", msg.getSubject(),
		// msgBody);

		chat.sendMessage(msgBody);
	}

	/**
	 * remove last slash?
	 * 
	 * @param addr
	 * @return
	 */
	private String reviseAddress(String addr) {
		int idx = addr.lastIndexOf("/");
		if (idx >= 0)
			return addr.substring(0, idx);
		else
			return addr;
	}

	/**
	 * IM client received message from remote peer.
	 * 
	 * @param msg
	 */
	private void receivedMessage(org.jivesoftware.smack.packet.Message msg) {

		try {
			if (logger.isDebugEnabled())
				logger.debug(String.format("from:%s, to:%s,subject:%s,body:%s",
						msg.getFrom(), msg.getTo(), msg.getSubject(),
						msg.getBody()));

			VirtualMachine vm = VirtualMachine.getInstance();
			ObjectRepository repo = vm.getObjectRepository();
			StringValue returnValue = (StringValue) repo.createObject(
					ObjectType.STRING, ObjectScope.ExecutionContext);

			// TODO convert msg.Body to _Object.
			returnValue.setValue(msg.getBody());

			String senderAddress = reviseAddress(msg.getFrom());
			Link senderLink = repo.getFactory().createLink(
					getFullAddress(reviseAddress(senderAddress)));
			
			Link receiverLink = repo.getFactory()
					.createLink(
							getFullAddress(reviseAddress(reviseAddress(msg
									.getTo()))));
			Session session = new Session(receiverLink, senderLink);
			
			Message newMsg = null;

			Message blockedOutgoingRequest = blockedOutgoingRequests
					.get(senderAddress);
			if (blockedOutgoingRequest != null) {
				// address match. it must be reply.
				if (logger.isDebugEnabled())
					logger.debug("it is a reply");

				
				Message replyMsg = new Message(blockedOutgoingRequest.receiver,
						blockedOutgoingRequest.sender, "RE:"
								+ blockedOutgoingRequest.getSubject(),
						returnValue, ParameterStyle.ByName,
						blockedOutgoingRequest, MessageType.SyncReply,
						session);

				replyMsg.urgent = true;

				// it can only be replied once.
				removeBlockedOutgoingRequest(senderAddress);

				newMsg = replyMsg;
			} else {// not a reply. it is a request.
				if (logger.isDebugEnabled())
					logger.debug("it is not a reply");


				// TODO convert msg.body to _Object				
				Message normalMsg = new Message(senderLink, receiverLink,
						msg.getBody(), null, ParameterStyle.ByName, null,
						MessageType.SyncRequest, session);

				normalMsg.urgent = false;

				newMsg = normalMsg;
			}

			// send it.
			vm.getPostService().sendMessage(newMsg);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected String getFullAddress(String shortAddress) {
		return "xmpp://" + shortAddress;
	}

	protected MessageListener getMessageListener() {
		return messageListener;
	}
}
