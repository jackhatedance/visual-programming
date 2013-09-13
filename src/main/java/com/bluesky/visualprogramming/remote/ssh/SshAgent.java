package com.bluesky.visualprogramming.remote.ssh;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageType;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.VirtualMachine;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public class SshAgent {

	static Logger logger = Logger.getLogger(SshAgent.class);

	String server;
	int port;
	String username;
	String password;

	JSch jsch;
	Session session;
	Channel channel;

	public SshAgent(String address, _Object obj, String connectionOptions) {

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

		if (optMap.containsKey("port"))
			port = Integer.valueOf(optMap.get("port"));
		else {
			port = 22;
		}

		jsch = new JSch();

	}

	public void connect() {
		try {

			session = jsch.getSession(username, server, port);
			logger.debug(String.format(
					"login ssh to server %s:%d as user %s password %s", server,
					port, username, password));
			UserInfo ui = new MyUserInfo(password);
			
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setPassword(password);
			//session.setUserInfo(ui);

			session.connect();

		} catch (JSchException e) {

			throw new RuntimeException(e);
		}
	}

	public void sendMessage(Message msg) {
		Message lastRequestMessage = msg;

		try {
			channel = session.openChannel("exec");

			if (logger.isDebugEnabled())
				logger.debug("create channel");

			// channel.setInputStream(System.in);
			channel.setInputStream(null);
			((ChannelExec) channel).setCommand(msg.getSubject());
			int exitStatus = 0;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			InputStream in = channel.getInputStream();
			channel.connect();

			if (logger.isDebugEnabled())
				logger.debug("connect channel");

			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;

					output.write(tmp);

					if (logger.isDebugEnabled())
						logger.debug("read output");

				}
				if (channel.isClosed()) {
					if (logger.isDebugEnabled())
						logger.debug("channel closed");

					exitStatus = channel.getExitStatus();
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
					if (logger.isDebugEnabled())
						logger.debug("error");

					throw new RuntimeException(ee);
				}
			}

			VirtualMachine vm = VirtualMachine.getInstance();
			ObjectRepository repo = vm.getObjectRepository();
			_Object returnValue = (_Object) repo.createObject(
					ObjectType.NORMAL, ObjectScope.ExecutionContext);

			IntegerValue status = (IntegerValue) repo.createObject(
					ObjectType.INTEGER, ObjectScope.ExecutionContext);
			status.setIntValue(exitStatus);

			StringValue outputValue = (StringValue) repo.createObject(
					ObjectType.STRING, ObjectScope.ExecutionContext);
			outputValue.setValue(output.toString());

			returnValue.addChild(status, "status", true);
			returnValue.addChild(outputValue, "output", true);

			Message replyMsg = new Message(false, lastRequestMessage.receiver,
					lastRequestMessage.sender, "RE:"
							+ lastRequestMessage.getSubject(), returnValue,
					ParameterStyle.ByName, null, MessageType.SyncReply);

			replyMsg.urgent = true;

			// it can only be replied once.
			lastRequestMessage = null;

			vm.getPostService().sendMessage(replyMsg);

			if (logger.isDebugEnabled())
				logger.debug("reply sent");

		} catch (Exception e) {
			throw new RuntimeException(e);

		}
	}

	public void disconnect() {
		channel.disconnect();
		session.disconnect();
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
	
	

}
