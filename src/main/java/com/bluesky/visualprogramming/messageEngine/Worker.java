package com.bluesky.visualprogramming.messageEngine;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageStatus;
import com.bluesky.visualprogramming.core.MessageType;
import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core.Procedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.vm.CompiledProcedure;
import com.bluesky.visualprogramming.vm.ExecutionStatus;
import com.bluesky.visualprogramming.vm.ProcedureExecutor;

public class Worker implements Runnable {
	static Logger logger = Logger.getLogger(Worker.class);

	private ObjectRepository objectRepository;

	private WorkerManager workerManager;
	private PostService postService;
	private _Object customer;

	public Worker(ObjectRepository objectRepository,
			WorkerManager workerManager, PostService postService,
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

				// obj.checkMessageType(msg.messageType);

				if (msg.executionContext != null
						&& msg.executionContext.getExecutionStatus() == ExecutionStatus.WAITING) {
					obj.removeWorker();
					break;
				}

				logger.debug(String.format(
						"start processing msg '%s', msg.count %d ",
						msg.toString(), obj.getMessageQueueSize()));

				// could be void
				String replyValue = "";
				if (msg.body != null)
					replyValue = msg.body.getValue();

				if (msg.isSyncReply()) {
					logger.debug(String.format(
							"comes reply of '%s', value: %s",
							msg.previous.toString(), replyValue));
					/**
					 * pick the reply body from current message, put into the
					 * pending procedure.
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

					// push reply to next message
					Message lastMessage = obj.peekFirstMessage();
					logger.debug("last message:" + lastMessage.toString());
					if (lastMessage.executionContext == null)
						throw new RuntimeException(
								"error: last message's execution context is null.");

					obj.peekFirstMessage().executionContext.reply = reply;
					obj.peekFirstMessage().executionContext
							.setExecutionStatus(ExecutionStatus.ON_GOING);

				} else {

					Procedure proc = obj.lookupProcedure(msg.subject);
					if (proc == null)
						throw new RuntimeException("message not understand:"
								+ msg.subject);

					executeProcedure(msg, obj, proc);

					ExecutionStatus procedureExecutionStatus = msg.executionContext
							.getExecutionStatus();

					logger.debug("procedureExecutionStatus "
							+ procedureExecutionStatus);

					if (procedureExecutionStatus == ExecutionStatus.COMPLETE) {

						if (logger.isDebugEnabled())
							obj.printMessageQueue();

						if (logger.isDebugEnabled())
							System.out.println("remove the sync reply message");

						// remove from queue
						obj.removeMessage(msg);

						if (logger.isDebugEnabled())
							obj.printMessageQueue();

						if (msg.sync) {// wake up the sender, let it continue.

							Message replyMsg = new Message(false, msg.receiver,
									msg.sender, "RE:" + msg.subject, msg.reply,
									ParameterStyle.ByName, msg,
									MessageType.SyncReply);

							replyMsg.urgent = true;

							postService.sendMessage(replyMsg);

						} else {
							// notify the sender

							if (msg.needCallback()) {

								Message replyMsg = new Message(false,
										msg.receiver, msg.sender, msg.callback,
										msg.reply, ParameterStyle.ByName, msg,
										MessageType.AsyncReply);

								postService.sendMessage(replyMsg);

							}
						}

					} else if (procedureExecutionStatus == ExecutionStatus.WAITING) {
						logger.debug(String.format("[%s] is waiting for reply",
								obj));
						obj.setExpectMessageType(MessageType.SyncReply);
						/*
						 * TODO let the worker thread wait for a while, maybe
						 * the reply come very soon. so that we can reuse
						 * current worker for the same customer.
						 */
						synchronized (obj) {
							if (obj.hasNewerMessageArrived(msg)) {
								// continue work on reply
								logger.debug("newer messages arrived before worker leaves");

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
				}
			}// end of process one message

			logger.debug(String.format(
					"after processing msg '%s', msg.count %d", msg.toString(),
					obj.getMessageQueueSize()));
		}

		logger.debug("finish work for customer:" + obj.getName());
	}

	private void executeProcedure(Message msg, _Object obj, Procedure proc) {

		if (proc.isNative()) {

			// native procedure always complete
			executeNativeProcedure(msg, obj, proc);
		} else
			executeNormalProcedure(msg, obj, proc);

		msg.reply = msg.executionContext.getResult();

		// msg.status = MessageStatus.FINISHED;

	}

	private void executeNormalProcedure(Message msg, _Object obj, Procedure proc) {

		CompiledProcedure cp = obj.getCompiledProcedure(msg.subject);

		if (msg.status == MessageStatus.NOT_STARTED) {
			msg.initExecutionContext(objectRepository.getRootObject(), cp
					.getParameters().toArray(new String[0]));

			msg.status = MessageStatus.IN_PROGRESS;
		}

		ProcedureExecutor executor = new ProcedureExecutor(objectRepository,
				postService, cp, msg.executionContext);
		// e.setPolicy(step);

		executor.execute();

	}

	private void executeNativeProcedure(Message msg, _Object obj, Procedure proc) {

		msg.initExecutionContext(objectRepository.getRootObject(),
				proc.getNativeProcedureParameterNames());

		try {
			Class cls = Class.forName(proc.nativeProcedureClassName);
			NativeProcedure nativeP = (NativeProcedure) cls.newInstance();

			nativeP.execute(obj, msg.executionContext, msg);

		} catch (Exception e) {
			throw new RuntimeException(
					"error during executing native procedure.", e);
		}
	}

	@Override
	public void run() {
		processMessages(customer);
	}
}
