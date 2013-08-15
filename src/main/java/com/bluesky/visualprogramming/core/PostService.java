package com.bluesky.visualprogramming.core;

public class PostService {
	WorkerManager workerManager;
	
	public void sendMessage(Message msg){
		int size = msg.receiver.addToMessageQueue(msg);
		if(size==1)
			workerManager.addCustomer(msg.receiver);
	}
	
	public void sendMessageFromNobody(_Object receiver,String subject){
		Message msg = new Message(false, null, receiver, subject, null);
	}
}
