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

		while (true) {
			
			//lock the customer when processing messages
			synchronized (obj) {
				
			}
			
			Message msg = obj.getMessageQueue().peekFirst();

			if (msg == null)
				break;

			Procedure proc = obj.lookupProcedure(msg.subject);
			if (proc == null)
				throw new RuntimeException("message not understand:"
						+ msg.subject);

			ExecutionStatus procedureExecutionStatus = executeProcedure(msg,
					obj, proc);

			if (procedureExecutionStatus == ExecutionStatus.COMPLETE) {

				// remove from queue
				obj.getMessageQueue().removeFirst();
	
				if (msg.sync) {// wake up the sender, let it continue.
					
					Message replyMsg = new Message(false, msg.receiver,
							msg.sender, "_REPLY", msg.reply,
							ParameterStyle.ByName,msg);

					replyMsg.urgent = true;

					postService.sendMessage(replyMsg);
					
				} else {
					// notify the sender

					if (msg.needCallback()) {
						
						Message replyMsg = new Message(false, msg.receiver,
								msg.sender, msg.callback, msg.reply,
								ParameterStyle.ByName,msg);

						postService.sendMessage(replyMsg);
					}
				}

			} else if (procedureExecutionStatus == ExecutionStatus.WAITING) {
				logger.debug(String.format("[%s] is waiting for reply", obj));
				// TODO let the worker thread wait for a while, maybe the reply
				// come very soon. so that we can reuse current worker for the
				// same customer.

				break;
			}
		}

		// job done, worker leaves
		obj.setWorker(null);
		
		logger.debug(String.format("job for [%s] is done, worker leaves", obj));
	}

	private ExecutionStatus executeProcedure(Message msg, _Object obj,
			Procedure proc) {
		ExecutionStatus procedureExecutionStatus;

		if (proc.isNative()) {
			msg.initExecutionContext(objectRepository.getRootObject());

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
		if (msg.status == MessageStatus.NOT_STARTED) {
			msg.initExecutionContext(objectRepository.getRootObject());

			msg.status = MessageStatus.IN_PROGRESS;
		}

		CompiledProcedure cp = obj.getCompiledProcedure(msg.subject);

		ProcedureExecutor executor = new ProcedureExecutor(objectRepository,
				postService, cp, msg.executionContext);
		// e.setPolicy(step);

		return executor.execute();

	}

	private ExecutionStatus executeNativeProcedure(Message msg, _Object obj,
			Procedure proc) {
		try {
			Class cls = Class.forName(proc.nativeProcedureClassName);
			NativeProcedure nativeP = (NativeProcedure) cls.newInstance();

			ExecutionStatus es = nativeP.execute(obj, msg);

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
