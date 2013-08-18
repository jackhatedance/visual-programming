package com.bluesky.visualprogramming.core;

import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Message {

	public boolean sync;
	public _Object sender;
	public _Object receiver;
	public String subject;
	public _Object body;

	public ProcedureExecutionContext executionContext;

	public _Object reply;

	/**
	 * if async, the callback procedure name, pass the reply or message
	 */
	public String callback;

	public MessageStatus status=MessageStatus.NOT_STARTED;

	/**
	 * sync or aync
	 * 
	 * @param sync
	 * @param sender
	 * @param receiver
	 * @param subject
	 * @param body
	 */
	public Message(boolean sync, _Object sender, _Object receiver,
			String subject, _Object body) {
		this.sync = sync;
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.body = body;

	}

	/**
	 * async call with callback
	 * 
	 * @param callback
	 * @param sender
	 * @param receiver
	 * @param subject
	 * @param body
	 */
	public Message(String callback, _Object sender, _Object receiver,
			String subject, _Object body) {
		this.callback = callback;
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.body = body;
		// TODO Auto-generated method stub
	}
	
	public void initExecutionContext(){
		executionContext = new ProcedureExecutionContext();
	}

	public boolean needCallback() {
		return callback != null && (!callback.isEmpty());
	}

	
}
