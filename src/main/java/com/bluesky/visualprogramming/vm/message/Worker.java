package com.bluesky.visualprogramming.vm.message;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.BasicObjectFactory;
import com.bluesky.visualprogramming.core.CodePosition;
import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageStatus;
import com.bluesky.visualprogramming.core.MessageType;
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

public class Worker implements Runnable {
	static Logger logger = Logger.getLogger(Worker.class);

	private ObjectRepository objectRepository;

	private WorkerService workerManager;
	private PostService postService;
	private _Object customer;

	private InstructionExecutorImpl executor;

	/**
	 * a flag to ask the work or executor to pause. cleared when paused
	 * achieved.
	 */
	private volatile boolean pauseFlag = false;

	public WorkerStatus status;

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
					if (logger.isDebugEnabled())
						logger.debug("no more workable message to be processed.");

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
					if (logger.isDebugEnabled())
						logger.debug("reply exception captured "
								+ vex.getMessage());
				}

				if (msg.isSyncReply())
					processSyncReply(obj, msg, replyValue);
				else {
					// it is new request or a async reply.
					// note that a async reply is just like an async request. it
					// needs to be processed.

					Procedure proc = obj.lookupProcedure(msg, objectRepository);
					if (proc == null) {
						logger.warn("message not understand:" + obj.getName()
								+ "." + msg.getSubject());

						executeUnknowMessage(msg, obj, proc);

					} else {
						_Object result = executeProcedure(msg, obj, proc);
						msg.reply = result;
					}

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

						// if (!obj.hasWorkableMessage()) {
						// Thread.sleep(1000);
						// }
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

		// check if it has replyTo message, otherwise we don't know which
		// blocking message is it for.
		Message replyToMessage = msg.previous;

		if (replyToMessage == null) {
			logger.error("received a invalid SyncReply message, it has no previous(replyTo) message. the message is: "
					+ msg.toString());

			return;
		}

		Message lastMessage = replyToMessage.previous;
		if (lastMessage == null) {
			logger.error("the replyTo message has no parent, that means it may not be a sync message."
					+ msg.toString());

			return;
		}

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

	/**
	 * 
	 * @param msg
	 * @param obj
	 * @param proc
	 * @return the return value of the procedure
	 */
	private _Object executeProcedure(Message msg, _Object obj, Procedure proc) {
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

		executor = new InstructionExecutorImpl(objectRepository, postService,
				cp, msg.executionContext, msg, this);
		// executor.setPolicy(step);

		executor.execute();

		executor = null;

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
				VException ex = getObjectFactory().createException(
						"execution error:"
								+ msg.executionContext.executionErrorMessage);

				ex.addTrace(position);
				result = ex;
			}

		}

		return result;

	}

	protected BasicObjectFactory getObjectFactory() {
		return objectRepository.getFactory();
	}

	@Override
	public void run() {
		
		try{
			processMessages(customer);
	
			workerManager.removeWorker(this);
	
			updateRunningStatus(WorkerStatus.Finished);
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public synchronized void updateRunningStatus(WorkerStatus newStatus) {
		status = newStatus;

		if (pauseFlag) {
			pausedOrFinishedLatch.countDown();

			// reset the flag
			pauseFlag = false;
		}

	}

	public boolean getPauseFlag() {
		return pauseFlag;
	}

	public CountDownLatch pausedOrFinishedLatch;

	public synchronized void pause(CountDownLatch latch) {
		/**
		 * wait for pausedOrTerminated.await();
		 */

		switch (status) {
		case Running:

			/*
			 * switch the flag on, so that the worker thread(not this one) will
			 * check it at 2 places. at each place, it will countDown the latch.
			 */
			pauseFlag = true;
			pausedOrFinishedLatch = new CountDownLatch(1);
			try {
				pausedOrFinishedLatch.await();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			break;
		case Paused:
			throw new RuntimeException("already paused");

		case Finished:
			// throw new RuntimeException("already finished");

		}

		latch.countDown();
	}

	public synchronized void resume() {
		switch (status) {
		case Paused:
			// awake the sleep thread
			executor.resume();

		}
	}
}
