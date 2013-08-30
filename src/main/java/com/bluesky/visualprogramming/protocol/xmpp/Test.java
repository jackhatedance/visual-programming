package com.bluesky.visualprogramming.protocol.xmpp;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class Test {
	public static void main(String[] args) {

		XMPPConnection connection = new XMPPConnection("getonsip.com");

		try {
			// Connect
			connection.connect();

			// Login with appropriate credentials
			connection.login("jackhatedance", "pw12#$");

			ChatManager chatManager = connection.getChatManager();
			chatManager.addChatListener(new ChatManagerListener() {
				
				@Override
				public void chatCreated(Chat chat, boolean createdLocally) {
			
					
				}
			});
			
			Chat chat = chatManager.createChat("jackding@cisco.com",
					new MessageListener() {

						@Override
						public void processMessage(Chat chat, Message msg) {

							try {

								chat.sendMessage(String.format(
										"why did you say '%s'", msg.getBody()));
							} catch (XMPPException e) {

								e.printStackTrace();
							}
						}
					});
			chat.sendMessage("hi");

		} catch (XMPPException e) {
			// Do something better than this!
			e.printStackTrace();
		} finally {
			//connection.disconnect();
		}

		boolean isRunning = true;
		while (isRunning) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
