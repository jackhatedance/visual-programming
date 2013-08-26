package com.bluesky.visualprogramming.messageEngine;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageStatus;
import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectType;
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

		customer.setWorker(this);
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
		Message msg = null;
		while ((msg = obj.getMessageQueue().peekFirst()) != null) {
			logger.debug(String.format(
					"start processing msg '%s', msg.count %d ", msg.subject,
					obj.getMessageQueue().size()));

			if (msg.isSyncReply()) {
				logger.debug("comes reply of " + msg.previous.subject);
				/**
				 * pick the reply body from current message, put into the
				 * pending procedure.
				 */
				_Object reply = msg.body;

				obj.getMessageQueue().removeFirst();
				obj.clearNewSyncReplyArrivedFlag();

				// push reply to next message
				obj.getMessageQueue().peekFirst().executionContext.reply = reply;

			} else {

				Procedure proc = obj.lookupProcedure(msg.subject);
				if (proc == null)
					throw new RuntimeException("message not understand:"
							+ msg.subject);

				ExecutionStatus procedureExecutionStatus = executeProcedure(
						msg, obj, proc);

				if (procedureExecutionStatus == ExecutionStatus.COMPLETE) {

					// remove from queue
					obj.getMessageQueue().removeFirst();

					if (msg.sync) {// wake up the sender, let it continue.

						Message replyMsg = new Message(false, msg.receiver,
								msg.sender, "_REPLY", msg.reply,
								ParameterStyle.ByName, msg);

						replyMsg.urgent = true;

						postService.sendMessage(replyMsg);

					} else {
						// notify the sender

						if (msg.needCallback()) {

							Message replyMsg = new Message(false, msg.receiver,
									msg.sender, msg.callback, msg.reply,
									ParameterStyle.ByName, msg);

							postService.sendMessage(replyMsg);
						}
					}

					synchronized (obj) {
						// job done, worker leaves
						obj.setWorker(null);
					}

				} else if (procedureExecutionStatus == ExecutionStatus.WAITING) {
					logger.debug(String
							.format("[%s] is waiting for reply", obj));
					/*
					 * TODO let the worker thread wait for a while, maybe the
					 * reply come very soon. so that we can reuse current worker
					 * for the same customer.
					 */
					synchronized (obj) {
						// check if the reply has come
						if (obj.hasNewSyncReplyArrived()) {
							// continue work on reply
							logger.debug("reply arrived before worker leaves");
						} else {
							// worker leaves
							obj.setWorker(null);
							break;

						}
					}
				}
			}

			logger.debug(String.format("end processing msg '%s', msg.count %d",
					msg.subject, obj.getMessageQueue().size()));
		}

		logger.debug(String.format("job for [%s] is done, worker leaves", obj));
	}

	private ExecutionStatus executeProcedure(Message msg, _Object obj,
			Procedure proc) {
		ExecutionStatus procedureExecutionStatus;

		if (proc.isNative()) {

			// native procedure always complete
			procedureExecutionStatus = executeNativeProcedure(msg, obj, proc);
		} else
			procedureExecutionStatus = executeNormalProcedure(msg, obj, proc);

		msg.reply = msg.executionContext.getResult();

		// msg.status = MessageStatus.FINISHED;

		return procedureExecutionStatus;

	}

	private ExecutionStatus executeNormalProcedure(Message msg, _Object obj,
			Procedure proc) {

		CompiledProcedure cp = obj.getCompiledProcedure(msg.subject);

		if (msg.status == MessageStatus.NOT_STARTED) {
			msg.initExecutionContext(objectRepository.getRootObject(), cp
					.getParameters().toArray(new String[0]));

			msg.status = MessageStatus.IN_PROGRESS;
		}

		ProcedureExecutor executor = new ProcedureExecutor(objectRepository,
				postService, cp, msg.executionContext);
		// e.setPolicy(step);

		return executor.execute();

	}

	private ExecutionStatus executeNativeProcedure(Message msg, _Object obj,
			Procedure proc) {

		msg.initExecutionContext(objectRepository.getRootObject(),
				proc.getNativeProcedureParameterNames());

		try {
			Class cls = Class.forName(proc.nativeProcedureClassName);
			NativeProcedure nativeP = (NativeProcedure) cls.newInstance();

			ExecutionStatus es = nativeP
					.execute(obj, msg.executionContext, msg);

			msg.status = MessageStatus.FINISHED;

			return es;
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
