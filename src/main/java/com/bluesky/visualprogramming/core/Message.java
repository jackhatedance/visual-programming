package com.bluesky.visualprogramming.core;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.dialect.goo.GooCompiler;
import com.bluesky.visualprogramming.remote.session.Session;
import com.bluesky.visualprogramming.vm.ExecutionStatus;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Message {

	static Logger logger = Logger.getLogger(Message.class);
	/**
	 * a UUID. used for async reply.
	 */
	public String id;

	public _Object sender;
	/**
	 * receiver could be real object or link(UOI)
	 */
	public _Object receiver;

	private StringValue subject;
	public _Object body;
	public ParameterStyle parameterStyle;

	public ProcedureExecutionContext executionContext;
	/**
	 * after a incoming message being processed, the result is stored here.
	 */
	public _Object reply;

	// used only when it is a reply.
	// public Message replyTo;
	/**
	 * 2 usages,
	 * 
	 * 1 is as replyTo,
	 * 
	 * 2 is as message of execution context, similar to call stack
	 */
	public Message previous;

	/**
	 * the remote user ID, such as xmpp://foo@bar.com. used in session enabled
	 * mode of remote communication.
	 * 
	 */
	public Session session;

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
	public String replySubject;

	public MessageStatus status = MessageStatus.NOT_STARTED;

	/**
	 * an async request, may need a callback.
	 * 
	 * @param sender
	 * @param receiver
	 * @param subject
	 * @param body
	 * @param parameterStyle
	 * @param replySubject
	 *            could be null
	 * @return
	 */
	public static Message newAsyncRequestMessage(_Object sender,
			_Object receiver, String subject, _Object body,
			ParameterStyle parameterStyle, String replySubject) {

		Message msg = new Message(sender, receiver, subject, body,
				parameterStyle, null, MessageType.AsyncRequest, null);
		msg.replySubject = replySubject;

		return msg;
	}

	/**
	 * a async reply , which obviously don't need a callback again.
	 * 
	 * @param sender
	 * @param receiver
	 * @param subject
	 * @param body
	 * @param parameterStyle
	 * @return
	 */
	public static Message newAsyncReplyMessage(_Object sender,
			_Object receiver, String subject, _Object body,
			ParameterStyle parameterStyle, ReplyStatus replyStatus) {

		Message msg = new Message(sender, receiver, subject, body,
				parameterStyle, null, MessageType.AsyncReply, null);

		msg.replyStatus = replyStatus;

		return msg;
	}

	/**
	 * sync or aync
	 * 
	 * @param sync
	 * @param sender
	 * @param receiver
	 * @param subject
	 * @param body
	 */
	public Message(_Object sender, _Object receiver, String subject,
			_Object body, ParameterStyle parameterStyle,
			Message previousMessage, MessageType messageType, Session session) {

		this.sender = sender;
		this.receiver = receiver;

		this.subject = new StringValue(-1);
		this.subject.setValue(subject);

		this.body = body;
		this.parameterStyle = parameterStyle;
		this.previous = previousMessage;
		this.messageType = messageType;

		this.session = session;
		/**
		 * message body is always stand alone, temporarily
		 */
		// if (body != null)
		// body.setScope(ObjectScope.ExecutionContext);
	}

	/**
	 * init the global variable, parameter, to the execution context
	 * 
	 * @param root
	 * @param paramNames
	 */
	public void initExecutionContext(_Object root, _Object globalLinks, String[] paramNames) {
		executionContext = new ProcedureExecutionContext();

		executionContext.setExecutionStatus(ExecutionStatus.RUNNING);

		executionContext.setObject(ProcedureExecutionContext.VAR_ROOT, root);		
		executionContext.setObject(ProcedureExecutionContext.VAR_GLOBAL_LINKS, globalLinks);
		executionContext		
				.setObject(ProcedureExecutionContext.VAR_SELF, receiver);
		executionContext.setObject(ProcedureExecutionContext.VAR_SUBJECT,
				subject);
		executionContext.setObject(ProcedureExecutionContext.VAR_PARAMETERS,
				body);
		executionContext.setObject(
				ProcedureExecutionContext.VAR_PARAMETER_STYLE,
				getParamStyleSV());

		if (session != null)
			executionContext.setObject(
					ProcedureExecutionContext.VAR_SESSION_USER, session.remote);

		// executionContext.setObject("sender", receiver);

		if (messageType == MessageType.AsyncReply) {
			// special for async reply. it is not a normal request.
			if (paramNames.length > 0)
				executionContext.setObject(paramNames[0], body);

		} else {

			_Object parameters = body;
			if (parameters != null) {
				if (parameterStyle == ParameterStyle.ByName) {
					for (String name : paramNames) {

						if (logger.isDebugEnabled())
							logger.debug("push parameter to context:" + name);

						_Object p = parameters.getChild(name);
						executionContext.setObject(name, p);
					}
				} else {
					// by order
					int prefixLen = GooCompiler.PARAMETER_BY_ORDER_NAME_PREFIX
							.length();
					int paramLen = paramNames.length;
					for (String fieldName : parameters.getFieldNames()) {
						if(fieldName.length() < prefixLen)
							System.out.println("error");
						
						int idx = Integer.valueOf(fieldName
								.substring(prefixLen));

						if (idx >= paramLen) {
							logger.warn("too many parameters than required"
									+ toString());
							continue;
						}

						String name = paramNames[idx];
						_Object p = parameters.getChild(fieldName);

						if (logger.isDebugEnabled())
							logger.debug("push parameter to context:" + name);

						executionContext.setObject(name, p);
					}

				}
			}
		}
	}

	public boolean needCallback() {
		return replySubject != null && (!replySubject.isEmpty());
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
			String previousMsg = "null";
			if (previous != null)
				previousMsg = previous.toString();

			return String.format("RE:%s", previousMsg);
		} else {
			StringBuilder sb = new StringBuilder();

			if (body != null) {
				for (int i = 0; i < body.getFieldCount(); i++) {
					if (i > 0)
						sb.append(",");

					String childValue = "";
					if (body.getField(i).getTarget() != null)
						childValue = body.getField(i).getTarget().getValue();

					sb.append(childValue);

				}
			}
			String parameters = sb.toString();

			String executionStatus = "";
			if (executionContext != null
					&& executionContext.executionStatus != null)
				executionStatus = executionContext.executionStatus.toString();

			String from = "[nobody]";
			if (sender != null)
				from = sender.getPath();

			String to = receiver.getPath();
			return String.format(
					"from: %s, to:%s, subject:%s, body:%s, status: %s", from,
					to, subject, parameters, executionStatus);
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
	
	/**
	 * 
	 * @return
	 */
	public _Object getSenderForRemoteCommunication(){
		if(session!=null)
			return session.local;
		else
			return sender;
	}
}
