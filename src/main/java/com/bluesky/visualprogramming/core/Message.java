package com.bluesky.visualprogramming.core;

import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Message {

	public boolean sync;
	public _Object sender;
	public _Object receiver;
	public String subject;
	public _Object body;
	public ParameterStyle parameterStyle;

	public ProcedureExecutionContext executionContext;

	public _Object reply;

	// used only when it is a reply.
	public Message previous;

	// usually will goes to the head of the message queue.
	public boolean urgent = false;

	/**
	 * if async, the callback procedure name, pass the reply or message
	 */
	public String callback;

	public MessageStatus status = MessageStatus.NOT_STARTED;

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
			String subject, _Object body, ParameterStyle parameterStyle,
			Message previousMessage) {
		this.sync = sync;
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.body = body;
		this.parameterStyle = parameterStyle;
		this.previous = previousMessage;
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
			String subject, _Object body, ParameterStyle paramStyle) {
		this.callback = callback;
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.body = body;
		this.parameterStyle = paramStyle;

	}

	/**
	 * init the global variable, parameter, to the execution context
	 * 
	 * @param root
	 * @param paramNames
	 */
	public void initExecutionContext(_Object root, String[] paramNames) {
		executionContext = new ProcedureExecutionContext();

		executionContext.setObject("root", root);
		executionContext.setObject("self", receiver);
		executionContext.setObject("_parameters", body);

		if (parameterStyle == ParameterStyle.ByName) {
			for (String name : paramNames) {
				_Object p = body.getChild(name);
				executionContext.setObject(name, p);
			}
		} else {
			// by order
			for (int i = 0; i < body.getChildCount(); i++) {
				_Object p = body.getChild(i);
				executionContext.setObject(paramNames[i], p);
			}
		}
	}

	public boolean needCallback() {
		return callback != null && (!callback.isEmpty());
	}

	public boolean isReply() {
		return previous != null;
	}

	/**
	 * a reply of a sync invoke
	 * 
	 * @return
	 */
	public boolean isSyncReply() {
		return previous != null && previous.sync;
	}
}
