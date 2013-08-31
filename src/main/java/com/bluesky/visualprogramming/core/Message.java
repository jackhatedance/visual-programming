package com.bluesky.visualprogramming.core;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.messageEngine.Worker;
import com.bluesky.visualprogramming.vm.ExecutionStatus;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Message {

	static Logger logger = Logger.getLogger(Message.class);

	public boolean sync;
	public _Object sender;
	/**
	 * receiver could be real object or link(UOI)
	 */
	public _Object receiver;

	public String subject;
	public _Object body;
	public ParameterStyle parameterStyle;

	public ProcedureExecutionContext executionContext;

	public _Object reply;

	// used only when it is a reply.
	public Message previous;

	public MessageType messageType;

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
			Message previousMessage, MessageType messageType) {
		this.sync = sync;
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.body = body;
		this.parameterStyle = parameterStyle;
		this.previous = previousMessage;
		this.messageType = messageType;
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

		executionContext.setExecutionStatus(ExecutionStatus.ON_GOING);

		executionContext.setObject("root", root);
		executionContext.setObject("self", receiver);
		executionContext.setObject("_parameters", body);

		if (parameterStyle == ParameterStyle.ByName) {
			for (String name : paramNames) {

				if (logger.isDebugEnabled())
					logger.debug("push parameter to context:" + name);

				_Object p = body.getChild(name);
				executionContext.setObject(name, p);
			}
		} else {
			// by order
			int minCount = body.getChildCount() < paramNames.length ? body
					.getChildCount() : paramNames.length;
			for (int i = 0; i < minCount; i++) {

				String name = paramNames[i];
				_Object p = body.getChild(i);

				if (logger.isDebugEnabled())
					logger.debug("push parameter to context:" + name);

				executionContext.setObject(name, p);
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
		return this.messageType == MessageType.SyncReply;
	}
	public String toString() {

		if (messageType == MessageType.SyncReply) {
			// remote communication may lost previous message.
			String previousMsg = "n/a";
			if (previous != null)
				previousMsg = previous.toString();

			return String.format("RE:%s", previousMsg);
		} else {
			StringBuilder sb = new StringBuilder();

			if (body != null) {
				for (int i = 0; i < body.getChildCount(); i++) {
					if (i > 0)
						sb.append(",");

					sb.append(body.getChild(i).getValue());

				}
			}
			String parameters = sb.toString();

			String executionStatus = "";
			if (executionContext != null
					&& executionContext.executionStatus != null)
				executionStatus = executionContext.executionStatus.toString();

			return String.format("%s(%s) -- %s", subject, parameters,
					executionStatus);
		}
	}
	
	 
	
	
}
