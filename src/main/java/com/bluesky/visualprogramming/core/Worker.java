package com.bluesky.visualprogramming.core;

import com.bluesky.visualprogramming.vm.CompiledProcedure;
import com.bluesky.visualprogramming.vm.ExecutionStatus;
import com.bluesky.visualprogramming.vm.ProcedureExecutor;

public class Worker {
	WorkerManager workerManager;
	PostService postService;
	
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
			Message msg = obj.getMessageQueue().poll();
			if (msg == null)
				break;

			Procedure proc = obj.getProcedure(msg.subject);
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
			try {
				Class cls = Class.forName(proc.nativeProcedureClassName);
				NativeProcedure nativeP = (NativeProcedure) cls.newInstance();

				_Object result = nativeP.execute(obj, msg);

				msg.executionContext.setResult(result);
				
				es = ExecutionStatus.COMPLETED;
			} catch (Exception e) {
				throw new RuntimeException(
						"error during executing native procedure." + e);
			}
		} else {
			CompiledProcedure cp = obj.getCompiledProcedure(msg.subject);

			ProcedureExecutor executor = new ProcedureExecutor();
			// e.setPolicy(step);

			es = executor.execute(cp, msg.executionContext);

		}
		
		if(es==ExecutionStatus.COMPLETED){
			if(msg.sync){
				if(msg.sender.isAwake())
					throw new RuntimeException("assert error: sender should be sleeping.");
				
				msg.sender.setAwake(true);
				workerManager.addCustomer(msg.sender);
			}else{
				if(msg.needCallback())
				{
					_Object newBody = ObjectRepository.getInstance().createObject(null);
					newBody.addChild(msg.body, "body");
					newBody.addChild(msg.reply, "reply");
					Message replyMsg = new Message(false, msg.receiver, msg.sender, msg.callback, newBody);
					postService.sendMessage(replyMsg);
				}
			}
			
		}

		return es;

	}
}
