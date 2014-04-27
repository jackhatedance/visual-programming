package com.bluesky.visualprogramming.vm.message;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.BasicObjectFactory;
import com.bluesky.visualprogramming.core.CodePosition;
import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageStatus;
import com.bluesky.visualprogramming.core.MessageType;
import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core.Procedure;
import com.bluesky.visualprogramming.core.ReplyStatus;
import com.bluesky.visualprogramming.core.VException;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.Link;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.CompiledProcedure;
import com.bluesky.visualprogramming.vm.ExecutionStatus;
import com.bluesky.visualprogramming.vm.InstructionExecutorImpl;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class Worker implements Runnable {
	static Logger logger = Logger.getLogger(Worker.class);

	private ObjectRepository objectRepository;

	private WorkerService workerManager;
	private PostService postService;
	private _Object customer;

	public Worker(ObjectRepository objectRepository,
			WorkerService workerManager, PostService postService,
			_Object customer) {
		this.objectRepository = objectRepository;
		this.workerManager = workerManager;
		this.postService = postService;
		this.customer = customer;

		if (customer.hasWorker())
			throw new RuntimeException("customer already has worker.");

		customer.assignWorkerIfNot(this);
	}

	/**
	 * help customer processing messages in queue, according to procedures. it
	 * will work until message queue empty or customer need sleep(wait for
	 * reply).
	 * 
	 * once a reply is coming, a worker will be dispatched again, continue work.
	 * 
	 * @param obj
	 */
	public void processMessages(_Object obj) {

		if (logger.isDebugEnabled())
			logger.debug("start work for customer:" + obj.getName());

		while (true) {
			Message msg = null;

			// a message could arrive at any time
			synchronized (obj) {

				msg = obj.peekFirstMessage();
				if (msg == null) {
					// work is leaving the customer
					obj.removeWorker();

					break;
				}

				if (logger.isDebugEnabled())
					logger.debug(String.format(
							"start processing msg '%s', msg.count %d ",
							msg.toString(), obj.getMessageQueueSize()));

				// could be void
				String replyValue = "";
				if (msg.body != null)
					replyValue = msg.body.getValue();

				if (msg.messageType.isReply() && msg.body instanceof VException) {
					// just print the exception
					// TODO how to handle error and propagation solution???
					VException vex = (VException) msg.body;
					logger.debug("reply exception captured " + vex.getMessage());
				}

				if (msg.isSyncReply())
					processSyncReply(obj, msg, replyValue);
				else {
					// it is new request or a async reply.
					// note that a async reply is just like an async request. it
					// needs to be processed.

					Procedure proc = obj.lookupProcedure(msg);
					if (proc == null) {
						logger.warn("message not understand:"
								+ msg.getSubject());

						executeUnknowMessage(msg, obj, proc);

					} else
						executeProcedure(msg, obj, proc);

					ExecutionStatus procedureExecutionStatus = msg.executionContext
							.getExecutionStatus();

					if (logger.isDebugEnabled())
						logger.debug("procedureExecutionStatus "
								+ procedureExecutionStatus);

					if (procedureExecutionStatus.isTerminated()) {

						afterProcedureTerminated(obj, msg,
								procedureExecutionStatus);

					} else if (procedureExecutionStatus == ExecutionStatus.WAITING) {

						if (logger.isDebugEnabled())
							logger.debug(String.format(
									"[%s] is waiting for reply", obj));

						obj.moveMessageToBlockingQueue(msg);

						/*
						 * TODO let the worker thread wait for a while, maybe
						 * the reply come very soon. so that we can reuse
						 * current worker for the same customer.
						 */

						if (obj.hasWorkableMessage()) {

							if (logger.isDebugEnabled())
								logger.debug("continue work on next message");

							// check
							// if(obj.peekFirstMessage().previous!=msg)
							// throw new
							// RuntimeException("the newer message is not the SYNC-REPLY.");

						} else {
							// worker leaves
							obj.removeWorker();
							break;

						}

					}
				}

			}// end of process one message

			if (logger.isDebugEnabled())
				logger.debug(String.format(
						"after processing msg '%s', msg.count %d",
						msg.toString(), obj.getMessageQueueSize()));
		}

		if (logger.isDebugEnabled())
			logger.debug("finish work for customer:" + obj.getName());
	}

	private void afterProcedureTerminated(_Object obj, Message msg,
			ExecutionStatus procedureExecutionStatus) {

		if (logger.isDebugEnabled())
			obj.printMessageQueue();

		if (logger.isDebugEnabled())
			System.out.println("remove the sync reply message");

		// remove from queue
		obj.removeMessage(msg);

		if (logger.isDebugEnabled())
			obj.printMessageQueue();

		if (msg.messageType.isSync()) {

			// wake up the sender,
			// let it continue.

			if (msg.sender != null) {
				Message replyMsg = new Message(obj, msg.sender, "RE:"
						+ msg.getSubject(), msg.reply, ParameterStyle.ByName,
						msg, MessageType.SyncReply);

				if (procedureExecutionStatus == ExecutionStatus.ERROR) {
					replyMsg.setSubject("Error " + replyMsg.getSubject());
					replyMsg.replyStatus = ReplyStatus.Error;
				} else
					replyMsg.replyStatus = ReplyStatus.Normal;

				replyMsg.urgent = true;

				postService.sendMessage(replyMsg);
			}

			// print exception when the sender is NULL or
			// remote(callback link is also remote).
			if (msg.reply instanceof VException) {
				boolean isRemote = false;
				if (msg.sender != null && (msg.sender instanceof Link)) {
					Link senderLink = (Link) msg.sender;
					isRemote = !postService.isLocal(senderLink);

				}
				if (msg.sender == null || isRemote) {
					// print VException if any
					VException ex = (VException) msg.reply;

					System.err.println(ex.getTrace());

				}
			}
		} else {
			// notify the sender

			if (msg.needCallback()) {

				ReplyStatus replayStatus = (procedureExecutionStatus == ExecutionStatus.ERROR) ? ReplyStatus.Error
						: ReplyStatus.Normal;

				Message replyMsg = Message.newAsyncReplyMessage(msg.receiver,
						msg.sender, msg.replySubject, msg.reply,
						ParameterStyle.ByName, replayStatus);

				postService.sendMessage(replyMsg);

			}
		}
	}

	/**
	 * get return value. set the blocking procedure to be runnable.
	 * 
	 * @param obj
	 * @param msg
	 * @param replyValue
	 */
	private void processSyncReply(_Object obj, Message msg, String replyValue) {
		if (logger.isDebugEnabled()) {
			String previousMessage = "n/a";
			if (msg.previous != null)
				previousMessage = msg.previous.toString();
			logger.debug(String.format("comes reply of '%s', value: %s",
					previousMessage, replyValue));
		}
		/**
		 * pick the reply body from current message, put into the pending
		 * procedure.
		 */
		_Object reply = msg.body;

		if (logger.isDebugEnabled())
			obj.printMessageQueue();

		if (logger.isDebugEnabled())
			System.out.println("remove the sync reply message");

		if (!obj.removeMessage(msg))
			throw new RuntimeException("remove message failed:"
					+ msg.toString());

		if (logger.isDebugEnabled())
			obj.printMessageQueue();

		// push reply to the blocking message
		Message replyToMessage = msg.previous;
		Message lastMessage = replyToMessage.previous;

		if (logger.isDebugEnabled())
			logger.debug("last message:" + lastMessage.toString());
		if (lastMessage.executionContext == null)
			throw new RuntimeException(
					"error: last message's execution context is null.");

		// put return value
		lastMessage.executionContext.reply = reply;

		// set flag
		if (msg.replyStatus == ReplyStatus.Normal)
			lastMessage.executionContext
					.setExecutionStatus(ExecutionStatus.RUNNING);
		else
			lastMessage.executionContext
					.setExecutionStatus(ExecutionStatus.ERROR);

		obj.moveMessageToRunnableQueue(lastMessage);
	}

	private void executeUnknowMessage(Message msg, _Object obj, Procedure proc) {

		msg.initExecutionContext(objectRepository.getRootObject(),
				new String[0]);

		StringValue result = (StringValue) objectRepository.createObject(
				ObjectType.STRING, ObjectScope.ExecutionContext);
		result.setValue("message not understand:" + msg.getSubject());

		msg.reply = result;

		msg.executionContext.executionStatus = ExecutionStatus.COMPLETE;

	}

	// private void executeErrorReplyMessage(Message msg, _Object obj,
	// Procedure proc) {
	//
	// msg.initExecutionContext(objectRepository.getRootObject(),
	// new String[0]);
	//
	// VException ex = getObjectFactory().createException();
	//
	// ex.setMessage("NativeProcedure execution error:" + e.getMessage());
	// CodePosition pos = new CodePosition(obj.getPath(), proc.getName(),
	// null, msg.executionContext.executionErrorLine);
	//
	// msg.reply = ex;
	//
	// msg.executionContext.executionStatus = ExecutionStatus.COMPLETE;
	//
	// }

	private void executeProcedure(Message msg, _Object obj, Procedure proc) {

		if (proc.isNative()) {

			// native procedure always complete
			msg.reply = executeNativeProcedure(msg, obj, proc);
		} else {

			msg.reply = executeNormalProcedure(msg, obj, proc);
		}

		// msg.status = MessageStatus.FINISHED;

	}

	/**
	 * 
	 * @param msg
	 * @param obj
	 * @param proc
	 * @return the return value of the procedure
	 */
	private _Object executeNormalProcedure(Message msg, _Object obj,
			Procedure proc) {
		CompiledProcedure cp = null;

		try {
			cp = obj.getCompiledProcedure(proc);
		} catch (Exception e) {
			/*
			 * TODO for some legacy reason, later code will check execution
			 * context anyhow. We can think parse error is one kind of execution
			 * error. so, I initialize it.
			 */
			msg.initExecutionContext(objectRepository.getRootObject(),
					new String[0]);
			msg.executionContext.setExecutionStatus(ExecutionStatus.ERROR);

			// compile error
			CodePosition position = new CodePosition(obj.getPath(),
					proc.getName(), null, 0);
			VException ex = getObjectFactory().createException(
					"parse error:" + e.getMessage());


			ex.addTrace(position);

			return ex;
		}

		if (msg.status == MessageStatus.NOT_STARTED) {
			msg.initExecutionContext(objectRepository.getRootObject(), cp
					.getParameters().toArray(new String[0]));

			msg.status = MessageStatus.IN_PROGRESS;
		}

		InstructionExecutorImpl executor = new InstructionExecutorImpl(
				objectRepository, postService, cp, msg.executionContext, msg);
		// executor.setPolicy(step);

		executor.execute();

		_Object result = msg.executionContext.getResult();
		// error handling, convert error to VException and set it as return
		// value
		if (msg.executionContext.executionStatus == ExecutionStatus.ERROR) {

			CodePosition position = new CodePosition(obj.getPath(),
					proc.getName(), null,
					msg.executionContext.executionErrorLine);

			if (result != null && result instanceof VException) {
				// if already has exception, then just append this stack item
				VException ex = (VException) result;
				ex.addTrace(position);
			} else {
				// if there is no exception yet, create one.
				VException ex = (VException) objectRepository.createObject(
						ObjectType.EXCEPTION, ObjectScope.ExecutionContext);

				ex.setMessage("execution error:"
						+ msg.executionContext.executionErrorMessage);
				ex.addTrace(position);
				result = ex;
			}

		}

		return result;

	}

	private _Object executeNativeProcedure(Message msg, _Object obj,
			Procedure proc) {
		_Object result = null;

		msg.initExecutionContext(objectRepository.getRootObject(),
				proc.getNativeProcedureParameterNames());

		try {
			Class cls = Class.forName(proc.nativeProcedureClassName);
			NativeProcedure nativeP = (NativeProcedure) cls.newInstance();

			VirtualMachine vm = VirtualMachine.getInstance();

			nativeP.execute(vm, obj, msg.executionContext);

			msg.executionContext.setExecutionStatus(ExecutionStatus.COMPLETE);

			result = msg.executionContext.getResult();
		} catch (Exception e) {
			msg.executionContext.setExecutionStatus(ExecutionStatus.ERROR);
			e.printStackTrace();

			// error handling, convert error to VException and set it as return
			// value
			VException ex = getObjectFactory().createException(
					"NativeProcedure execution error:" + e.getMessage());


			CodePosition pos = new CodePosition(obj.getPath(), proc.getName(),
					null, msg.executionContext.executionErrorLine);

			ex.addTrace(e);

			ex.addTrace(pos);
			result = ex;
		}

		return result;
	}

	protected BasicObjectFactory getObjectFactory() {
		return objectRepository.getFactory();
	}

	@Override
	public void run() {
		processMessages(customer);

		workerManager.removeWorker(this);
	}

	public void pause() {

	}
}
