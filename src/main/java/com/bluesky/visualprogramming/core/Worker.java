package com.bluesky.visualprogramming.core;

import com.bluesky.visualprogramming.vm.CompiledProcedure;
import com.bluesky.visualprogramming.vm.ExecutionStatus;
import com.bluesky.visualprogramming.vm.ProcedureExecutor;

public class Worker implements Runnable {
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
			if(proc==null)
				throw new RuntimeException("message not understand:"+ msg.subject);
			
			ExecutionStatus es = executeProcedure(msg, obj, proc);

			if (es == ExecutionStatus.WAIT) {
				obj.setAwake(false);

				break;
			}
		}
	}

	private ExecutionStatus executeProcedure(Message msg, _Object obj,
			Procedure proc) {
		ExecutionStatus es;
		if (proc.isNative()) {
			msg.initExecutionContext(objectRepository.getRootObject());
			es = executeNativeProcedure(msg, obj, proc);
		} else {

			if (msg.status == MessageStatus.NOT_STARTED) {
				msg.initExecutionContext(objectRepository.getRootObject());
				
				
				msg.status = MessageStatus.IN_PROGRESS;
			}

			CompiledProcedure cp = obj.getCompiledProcedure(msg.subject);

			ProcedureExecutor executor = new ProcedureExecutor();
			// e.setPolicy(step);

			es = executor.execute(cp, msg.executionContext);

		}

		if (es == ExecutionStatus.COMPLETED) {
			// remove from queue
			obj.getMessageQueue().removeFirst();

			if (msg.sync) {
				if (msg.sender.isAwake())
					throw new RuntimeException(
							"assert error: sender should be sleeping.");

				msg.sender.setAwake(true);
				workerManager.addCustomer(msg.sender);
			} else {
				if (msg.needCallback()) {
					_Object newBody = objectRepository.createObject(null);
					newBody.addChild(msg.body, "body");
					newBody.addChild(msg.reply, "reply");
					Message replyMsg = new Message(false, msg.receiver,
							msg.sender, msg.callback, newBody);
					postService.sendMessage(replyMsg);
				}
			}

		}

		return es;

	}

	private ExecutionStatus executeNativeProcedure(Message msg, _Object obj,
			Procedure proc) {
		try {
			Class cls = Class.forName(proc.nativeProcedureClassName);
			NativeProcedure nativeP = (NativeProcedure) cls.newInstance();

			_Object result = nativeP.execute(obj, msg);

			msg.executionContext.setResult(result);

			msg.status = MessageStatus.FINISHED;

			ExecutionStatus es = ExecutionStatus.COMPLETED;

			return es;
		} catch (Exception e) {
			throw new RuntimeException(
					"error during executing native procedure." + e);
		}
	}

	@Override
	public void run() {
		processMessages(customer);
	}
}
