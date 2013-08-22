package com.bluesky.visualprogramming.messageEngine;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageStatus;
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

	}

	/**
	 * help customer processing messages in queue, according to procedures. it
	 * will work until message queue empty or customer need sleep(wait for
	 * reply).
	 * 
	 * once a reply is coming, a worker will be dispatch again, continue work.
	 * 
	 * @param obj
	 */
	public void processMessages(_Object obj) {

		while (true) {
			Message msg = obj.getMessageQueue().peekFirst();

			if (msg == null)
				break;

			Procedure proc = obj.getProcedure(msg.subject);
			if (proc == null)
				throw new RuntimeException("message not understand:"
						+ msg.subject);

			ExecutionStatus procedureExecutionStatus = executeProcedure(msg,
					obj, proc);

			if (procedureExecutionStatus == ExecutionStatus.COMPLETE) {

				// remove from queue
				obj.getMessageQueue().removeFirst();

				if (msg.sync) {// wake up the sender, let it continue.
					if (msg.sender.isAwake())
						throw new RuntimeException(
								String.format(
										"assert error: sender [%s] should be sleeping.",
										msg.sender));

					if (msg.sender.hasWorker())
						throw new RuntimeException(
								String.format(
										"assert error: sender [%s] should has no worker assigned.",
										msg.sender));

					logger.debug(String.format("sender [%s] wakeup, added to workManager queue.",msg.sender));
					
					msg.sender.wake();
					workerManager.addCustomer(msg.sender);
				} else {
					//notify the sender
					
					if (msg.needCallback()) {
						_Object newBody = objectRepository.createObject(null);
						newBody.addChild(msg.body, "body", true);
						newBody.addChild(msg.reply, "reply", true);
						Message replyMsg = new Message(false, msg.receiver,
								msg.sender, msg.callback, newBody,
								ParameterStyle.ByName);

						postService.sendMessage(replyMsg);
					}
				}

			} else if (procedureExecutionStatus == ExecutionStatus.WAITING) {
				System.out.println(String.format("[%s] is waiting for reply",obj));
				break;
			}
		}
		
		//job done, worker leaves
		obj.setWorker(null);		
		obj.sleep();
		logger.debug(String.format("job for [%s] is done, worker leaves",obj));
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

			_Object result = nativeP.execute(obj, msg);

			msg.executionContext.setResult(result);

			msg.status = MessageStatus.FINISHED;

			ExecutionStatus es = ExecutionStatus.COMPLETE;

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
