package com.bluesky.visualprogramming.remote.email;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
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
	
	public static final String MESSAGE_ID = "Message-ID";
	public static final String IN_REPLY_TO = "In-Reply-To";
	public static final String SUPERSEDES = "Supersedes";
	

	String user;
	String password;

	String checkFolder;
	String processedFolder;

	Properties props;
	
	/**
	 * key is email message ID, value is outgoing message that are waiting for reply.
	 */
	Map<String, Message> blockingMessages=new HashMap<String, Message>();

	public EmailAgent(String address, _Object obj, Config config) {
		user = config.getString(EmailService.USER, "");
		password = config.getString(EmailService.PASS, "");

		checkFolder = config.getString(EmailService.CHECK_FOLDER, "inbox");
		processedFolder = config.getString(EmailService.PROCESSED_FOLDER,
				"processed");
		/*
		 * TLS example: props.put("mail.smtp.auth", "true");
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
			if (key.startsWith("mail.")) {
				String value = config.get(key);
				props.put(key, value);
				// if (logger.isDebugEnabled())
				// logger.debug(String.format("%s:%s", key, value));
			}
		}

	}

	public void send(String receiverAddress, Message msg) {

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(user, password);
					}
				});

		try {

			javax.mail.Message message = new MimeMessage(session);
			// set by "mail.from" in props
			// message.setFrom(new InternetAddress(address));

			message.setRecipients(javax.mail.Message.RecipientType.TO,
					InternetAddress.parse(receiverAddress));

			message.setSubject(msg.getSubject());
			String body = createPlainMessageBody(msg);
			message.setText(body);

			Transport.send(message);
			
			//if(msg.messageType.isReply() && msg.messageType.isSync())

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
					msgBody.append(param.getName() + ":" + param.getValue());

				}
			} else if (msg.body.getType().isValueObject()) {
				msgBody.append(msg.body.getValue());

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

	protected void processMail(javax.mail.Message msg) {
		try {
			Address[] in = msg.getFrom();
			for (Address address : in) {
				System.out.println("FROM:" + address.toString());
			}

			String textContent = null;
			if (msg.getContent() instanceof String) {
				textContent = (String) msg.getContent();
			} else if (msg.getContent() instanceof Multipart) {
				Multipart mp = (Multipart) msg.getContent();
				BodyPart bp = mp.getBodyPart(0);

				textContent = bp.getContent().toString();
			}

			String msgId = arrayToString(msg.getHeader(MESSAGE_ID));
			String inReplyTo = arrayToString(msg.getHeader(IN_REPLY_TO));
			String supersedes = arrayToString(msg.getHeader(SUPERSEDES));
			
			System.out.println("MSG ID:" + msgId);
			System.out.println("In reply to:" + inReplyTo);
			System.out.println("supersedes:" + supersedes);
			
			System.out.println("SENT DATE:" + msg.getSentDate());
			System.out.println("SUBJECT:" + msg.getSubject());
			System.out.println("CONTENT TYPE:" + msg.getContentType());
			System.out.println("CONTENT:" + textContent);
			
			
			if(inReplyTo==null || inReplyTo.isEmpty())
			{
				//a reply
			}
			else
			{
				//a request
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void checkNewMessage() {
		logger.debug("check new message...");

		try {
			Session session = Session.getInstance(props, null);
			Store store = session.getStore();
			store.connect(user, password);
			Folder inbox = store.getFolder(checkFolder);
			inbox.open(Folder.READ_WRITE);
			
			for (javax.mail.Message msg : inbox.getMessages()) {
				processMail(msg);
			}
			// inbox.

		} catch (Exception mex) {
			mex.printStackTrace();
		}
	}
	
	protected String arrayToString(String[] array){
		StringBuilder sb = new StringBuilder();
		if(array!=null)
		{
			int i=0;
			for(String item : array){
				if(i>0)
					sb.append(";");
				
				sb.append(item);
				i++;
				
			}
		}
		
		return sb.toString();
	}

}
