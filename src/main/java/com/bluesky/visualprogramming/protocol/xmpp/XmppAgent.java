package com.bluesky.visualprogramming.protocol.xmpp;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.workgroup.agent.AgentSession;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageType;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.link.SoftLink;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class XmppAgent {

	static Logger logger = Logger.getLogger(XmppAgent.class);

	String server;
	String username;
	String password;

	XMPPConnection connection;

	MessageListener messageListener;

	// private SessionStatus sessionStatus=SessionStatus.Normal;
	MessageType nextMessageType = MessageType.Normal;

	public XmppAgent(String address, _Object obj, String connectionOptions) {

		Map<String, String> optMap = parseOptions(connectionOptions);

		if (optMap.containsKey("server"))
			server = optMap.get("server");
		else {
			int idx = address.indexOf('@');
			server = address.substring(idx + 1);
		}

		if (optMap.containsKey("username"))
			username = optMap.get("username");
		else {
			int idx = address.indexOf('@');
			username = address.substring(0, idx);
		}

		if (optMap.containsKey("password"))
			password = optMap.get("password");
		else {
			password = "";
		}

		messageListener = new MessageListener() {
			@Override
			public void processMessage(Chat chat,
					org.jivesoftware.smack.packet.Message msg) {

				if (msg.getBody() != null && !msg.getBody().trim().isEmpty()) {
					receivedMessage(msg);
					logger.debug("received message from a chat start by others.");
				}
			}

		};
	}

	public void connect() {
		try {

			connection = new XMPPConnection(server);

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

	public void send(String receiverAddress, Message msg) throws XMPPException {
		if (msg.sync)
			nextMessageType = MessageType.SyncReply;
		else if (!msg.sync && msg.needCallback())
			nextMessageType = MessageType.AsyncReply;
		else
			nextMessageType = MessageType.Normal;

		ChatManager chatManager = connection.getChatManager();
		Chat chat = chatManager.createChat(receiverAddress,
				getMessageListener());

		chat.sendMessage(String.format("%s", msg.toString()));
	}

	private String reviseAddress(String addr) {
		int idx = addr.lastIndexOf("/");
		if (idx >= 0)
			return addr.substring(0, idx);
		else
			return addr;
	}

	private void receivedMessage(org.jivesoftware.smack.packet.Message msg) {

		try {
			if (logger.isDebugEnabled())
				logger.debug(String.format("from:%s, to:%s,subject:%s,body:%s",
						msg.getFrom(), msg.getTo(), msg.getSubject(),
						msg.getBody()));

			VirtualMachine vm = VirtualMachine.getInstance();
			ObjectRepository repo = vm.getObjectRepository();
			StringValue returnValue = (StringValue) repo
					.createObject(ObjectType.STRING);
			returnValue.setValue(msg.getBody());
			// new Message(sync, sender, receiver, subject, body,
			// parameterStyle,
			// previousMessage, messageType);

			SoftLink receiver = (SoftLink) repo
					.createObject(ObjectType.SOFT_LINK);
			receiver.setValue("xmpp://" + reviseAddress(msg.getTo()));
			receiver.setName("receiver#" + receiver.getAddress());

			SoftLink sender = (SoftLink) repo
					.createObject(ObjectType.SOFT_LINK);
			sender.setValue("xmpp://"
					+ reviseAddress(reviseAddress(msg.getFrom())));
			sender.setName("sender#" + sender.getAddress());

			String subject;
			if (nextMessageType == MessageType.SyncReply
					|| nextMessageType == MessageType.AsyncReply)
				subject = "RE:" + msg.getSubject();
			else{
			
				if(msg.getSubject()==null || msg.getSubject().trim().isEmpty())
					subject = msg.getBody();
				else
					subject = msg.getSubject();
			}

			Message replyMsg = new Message(false, sender, receiver, subject,
					returnValue, ParameterStyle.ByName, null, nextMessageType);

			// reset
			nextMessageType = MessageType.Normal;

			replyMsg.urgent = true;

			vm.getPostService().sendMessage(replyMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<String, String> parseOptions(String options) {
		Map<String, String> map = new HashMap<String, String>();

		String[] kvs = options.split(";");
		for (String kv : kvs) {
			String[] ss = kv.split("=");

			map.put(ss[0], ss[1]);
		}

		return map;
	}

	protected MessageListener getMessageListener() {
		return messageListener;
	}
}
