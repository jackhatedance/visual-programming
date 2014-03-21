package com.bluesky.visualprogramming.core;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.dialect.goo.GooCompiler;
import com.bluesky.visualprogramming.vm.ExecutionStatus;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Message {

	static Logger logger = Logger.getLogger(Message.class);
	/**
	 * a UUID. used for async reply.
	 */
	public String id;

	public boolean sync;
	public _Object sender;
	/**
	 * receiver could be real object or link(UOI)
	 */
	public _Object receiver;

	private StringValue subject;
	public _Object body;
	public ParameterStyle parameterStyle;


	public ProcedureExecutionContext executionContext;

	public _Object reply;

	// used only when it is a reply.
	public Message previous;

	public MessageType messageType;

	/**
	 * only used when message type is reply.
	 */
	public ReplyStatus replyStatus;

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

		this.subject = new StringValue(-1);
		this.subject.setValue(subject);

		this.body = body;
		this.parameterStyle = parameterStyle;
		this.previous = previousMessage;
		this.messageType = messageType;

		/**
		 * message body is always stand alone, temporarily
		 */
		if (body != null)
			body.setScope(ObjectScope.ExecutionContext);
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

		this.subject = new StringValue(-1);
		this.subject.setValue(subject);

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

		executionContext.setObject(ObjectRepository.ROOT, root);
		executionContext
				.setObject(ProcedureExecutionContext.VAR_SELF, receiver);
		executionContext.setObject(ProcedureExecutionContext.VAR_SUBJECT,
				subject);
		executionContext.setObject(ProcedureExecutionContext.VAR_PARAMETERS,
				body);
		executionContext.setObject(
				ProcedureExecutionContext.VAR_PARAMETER_STYLE,
				getParamStyleSV());
		// executionContext.setObject("sender", receiver);

		if (body != null) {
			if (parameterStyle == ParameterStyle.ByName) {
				for (String name : paramNames) {

					if (logger.isDebugEnabled())
						logger.debug("push parameter to context:" + name);

					_Object p = body.getChild(name);
					executionContext.setObject(name, p);
				}
			} else {
				// by order
				int prefixLen = GooCompiler.PARAMETER_BY_ORDER_NAME_PREFIX.length();
				int paramLen = paramNames.length;
				for(String fieldName :body.getFieldNames()){
					int idx = Integer.valueOf(fieldName.substring(prefixLen));
					
					if(idx>=paramLen)
					{
						logger.warn("too many parameters than required"+toString());
						continue;
					}
					
					String name = paramNames[idx];
					_Object p = body.getChild(fieldName);

					if (logger.isDebugEnabled())
						logger.debug("push parameter to context:" + name);

					executionContext.setObject(name, p);
				}
				
				
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

	@Override
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

					String childValue = "";
					if (body.getChild(i) != null)
						childValue = body.getChild(i).getValue();

					sb.append(childValue);

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

	public String getSubject() {
		return subject.getValue();
	}

	public void setSubject(String subject) {
		this.subject.setValue(subject);
	}

	public StringValue getParamStyleSV() {
		StringValue sv = new StringValue(-1);

		if (parameterStyle != null)
			sv.setValue(parameterStyle.name());

		return sv;
	}
}
