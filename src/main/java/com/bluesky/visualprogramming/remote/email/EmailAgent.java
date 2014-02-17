package com.bluesky.visualprogramming.remote.email;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.utils.Config;

public class EmailAgent {

	static Logger logger = Logger.getLogger(EmailAgent.class);

	//String server;
	String username;
	String password;

	Properties props;

	public EmailAgent(String address, _Object obj, Config config) {

		

		if (config.containsKey("username"))
			username = config.get("username");
		else {
			int idx = address.indexOf('@');
			username = address.substring(0, idx);
		}
		password = config.getString("password", "");

		/*
		 * TLS example: 
		 * props.put("mail.smtp.auth", "true");
		 * props.put("mail.smtp.starttls.enable", "true");
		 * props.put("mail.smtp.host", "smtp.gmail.com");
		 * props.put("mail.smtp.port", "587");
		 * 
		 * SSL: props.put("mail.smtp.host", "smtp.gmail.com");
		 * props.put("mail.smtp.socketFactory.port", "465");
		 * props.put("mail.smtp.socketFactory.class",
		 * "javax.net.ssl.SSLSocketFactory"); props.put("mail.smtp.auth",
		 * "true"); props.put("mail.smtp.port", "465");
		 */

		// move smtp options to prop object.
		props = new Properties();
		for (String key : config.keySet()) {
			if (key.startsWith("mail.smtp.")) {
				String value = config.get(key);
				props.put(key, value);
				if (logger.isDebugEnabled())
					logger.debug(String.format("%s:%s", key, value));
			}
		}

	}

	public void send(String receiverAddress, Message msg) {

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			javax.mail.Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(javax.mail.Message.RecipientType.TO,
					InternetAddress.parse(receiverAddress));
			
			
			message.setSubject(msg.getSubject());
			String body = createPlainMessageBody(msg);
			message.setText(body);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	private String createPlainMessageBody(Message msg) {
		// TODO convert body to hierarchical text
		StringBuilder msgBody = new StringBuilder();
		if (msg.body != null) {
			if (msg.body.getType() == ObjectType.NORMAL) {
				for (int i = 0; i < msg.body.getChildCount(); i++) {
					if (i != 0)
						msgBody.append(";");

					_Object param = msg.body.getChild(i);
					msgBody.append( param.getName() + ":" + param.getValue());

				}
			} else if (msg.body.getType().isValueObject()) {
				msgBody.append( msg.body.getValue());

			}

		}

		// chat.sendMessage(String.format("[%s] %s", msg.getSubject(),
		// msgBody));
		return msgBody.toString();
	}

	private String reviseAddress(String addr) {
		int idx = addr.lastIndexOf("/");
		if (idx >= 0)
			return addr.substring(0, idx);
		else
			return addr;
	}

}
