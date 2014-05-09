package com.bluesky.visualprogramming.vm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.CodePosition;
import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageType;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core.VException;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.NativeMethodHelper;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.core.value.ValueObject;
import com.bluesky.visualprogramming.dialect.goo.GooCompiler;
import com.bluesky.visualprogramming.vm.exceptions.CannotObtainOwnershipException;
import com.bluesky.visualprogramming.vm.exceptions.LabelNotFoundException;
import com.bluesky.visualprogramming.vm.instruction.AccessField;
import com.bluesky.visualprogramming.vm.instruction.CreateObject;
import com.bluesky.visualprogramming.vm.instruction.FieldAssignment;
import com.bluesky.visualprogramming.vm.instruction.Goto;
import com.bluesky.visualprogramming.vm.instruction.GotoIf;
import com.bluesky.visualprogramming.vm.instruction.Instruction;
import com.bluesky.visualprogramming.vm.instruction.NoOperation;
import com.bluesky.visualprogramming.vm.instruction.PopBlock;
import com.bluesky.visualprogramming.vm.instruction.ProcedureEnd;
import com.bluesky.visualprogramming.vm.instruction.PushBlock;
import com.bluesky.visualprogramming.vm.instruction.SendMessage;
import com.bluesky.visualprogramming.vm.instruction.ValueObjectAssignmentPolicy;
import com.bluesky.visualprogramming.vm.instruction.VariableAssignment;
import com.bluesky.visualprogramming.vm.message.PostService;
import com.bluesky.visualprogramming.vm.message.Worker;
import com.bluesky.visualprogramming.vm.message.WorkerStatus;

public class InstructionExecutorImpl implements InstructionExecutor {
	static Logger logger = Logger.getLogger(InstructionExecutorImpl.class);

	// some special fields:

	public static final String IMPLICT_FIELD_OWNER = "_owner";
	// not field name
	public static final String IMPLICT_FIELD_NAME = "_objectName";

	ObjectRepository objectRepository;
	CompiledProcedure procedure;
	ProcedureExecutionContext ctx;
	Message message;
	PostService postService;

	// private volatile boolean paused;

	private Worker worker;

	public InstructionExecutorImpl(ObjectRepository objectRepository,
			PostService postService, CompiledProcedure procedure,
			ProcedureExecutionContext ctx, Message msg, Worker worker) {

		this.objectRepository = objectRepository;
		this.postService = postService;
		this.procedure = procedure;
		this.ctx = ctx;
		this.message = msg;

		// for pause operation
		this.worker = worker;

	}


	public synchronized void resume() {
		notify();
	}

	private boolean isPaused() {
		return worker.getPauseFlag();
	}

	/**
	 * move forward one step
	 * 
	 * @param procedure
	 * @param ctx
	 * @return
	 */
	public void execute() {
		List<Instruction> instructions = procedure.getInstructions();

		loop1: while (true) {

			while (isPaused()) {
				worker.updateRunningStatus(WorkerStatus.Paused);

				synchronized (this) {
					try {
						logger.debug("paused");

						// wait until someone resume me
						wait();
						logger.debug("resumed");
					} catch (InterruptedException ex) {
						logger.debug("interrupted, terminated.");
						break loop1;
					}
				}
			}

			InstructionExecutionResult instructionExecutionResult = executeOneStep(instructions);
			ExecutionStatus instructionExecutionStatus = instructionExecutionResult
					.getStatus();

			if (instructionExecutionStatus == ExecutionStatus.WAITING) {
				ctx.executionStatus = ExecutionStatus.WAITING;
				break;
			} else if (instructionExecutionStatus == ExecutionStatus.ERROR) {
				ctx.executionStatus = ExecutionStatus.ERROR;
				ctx.executionErrorMessage = instructionExecutionResult
						.getDesc();
				ctx.executionErrorLine = instructionExecutionResult.getLine();
				break;
			}

			if (ctx.isProcedureEnd()) {
				ctx.executionStatus = ExecutionStatus.COMPLETE;
				break;
			}

		}

	}

	private InstructionExecutionResult executeOneStep(
			List<Instruction> instructions) {

		Instruction instruction = instructions.get(ctx.currentInstructionIndex);

		// execute it
		if (logger.isDebugEnabled())
			logger.debug(instruction.toString());

		InstructionExecutionResult result = new InstructionExecutionResult();

		try {
			ExecutionStatus es = instruction.type.execute(this, instruction);
			// goto instruction update index by them self
			if (!instruction.type.updatedInstructionIndex()) {
				//
				if (es == ExecutionStatus.COMPLETE)
					ctx.currentInstructionIndex++;
			}

			result.setStatus(es);
		} catch (Exception e) {
			// e.printStackTrace();
			result.setStatus(ExecutionStatus.ERROR);
			result.setDesc(e.getMessage());
			result.setLine(instruction.line);
		}

		return result;
	}

	@Override
	public ExecutionStatus executeAccessField(AccessField instruction) {

		_Object obj = ctx.getObject(instruction.objName);
		if (obj == null)
			throw new RuntimeException("object not exist:"
					+ instruction.toString());

		_Object result;
		if (instruction.fieldName.equals(IMPLICT_FIELD_OWNER))
			result = obj.getOwner();
		else if (instruction.fieldName.equals(IMPLICT_FIELD_NAME)) {
			result = new StringValue(-1);
			result.setValue(obj.getName());
		} else
			result = obj.getChild(instruction.fieldName);

		ctx.setObject(instruction.varName, result);

		return ExecutionStatus.COMPLETE;
	}

	@Override
	public ExecutionStatus executeCreateObject(CreateObject instruction) {
		// owner is execution context

		String strValue = instruction.value;
		_Object obj = objectRepository.createObject(instruction.objType,
				ObjectScope.ExecutionContext);
		obj.setName(instruction.varName);
		obj.setValue(strValue);

		ctx.setVariable(instruction.varName, obj);

		return ExecutionStatus.COMPLETE;
	}

	@Override
	public ExecutionStatus executeGoto(Goto instruction) {
		Integer index = procedure.getLabelIndex(instruction.destinationLabel);

		if (index == null)
			throw new LabelNotFoundException(instruction.destinationLabel);

		ctx.currentInstructionIndex = index;

		return ExecutionStatus.COMPLETE;
	}

	@Override
	public ExecutionStatus executeGotoIf(GotoIf instruction) {
		_Object actualObject = ctx.getObject(instruction.actualVarName);

		BooleanValue b = (BooleanValue) actualObject;
		if (b.getBooleanValue() == instruction.expected) {

			if (logger.isDebugEnabled())
				logger.debug("condition meets, goto");
			Integer index = procedure
					.getLabelIndex(instruction.destinationLabel);
			if (index == null)
				throw new LabelNotFoundException(instruction.destinationLabel);

			ctx.currentInstructionIndex = index;

		} else
			ctx.currentInstructionIndex++;

		return ExecutionStatus.COMPLETE;
	}

	@Override
	public ExecutionStatus executePushBlock(PushBlock instruction) {
		BlockStackItem item = new BlockStackItem();
		item.type = item.type;
		ctx.blockStacks.push(item);

		return ExecutionStatus.COMPLETE;
	}

	@Override
	public ExecutionStatus executePopBlock(PopBlock instruction) {
		ctx.blockStacks.pop();

		return ExecutionStatus.COMPLETE;
	}

	private Map<String, _Object> parametersToMap(_Object parameters,
			ParameterStyle parameterStyle, String[] paramNames) {
		Map<String, _Object> parametersMap = new HashMap<String, _Object>();

		if (parameters != null) {
			if (parameterStyle == ParameterStyle.ByName) {
				for (String name : paramNames) {

					if (logger.isDebugEnabled())
						logger.debug("push parameter to context:" + name);

					_Object p = parameters.getChild(name);
					parametersMap.put(name, p);
				}
			} else {
				// by order
				int prefixLen = GooCompiler.PARAMETER_BY_ORDER_NAME_PREFIX
						.length();
				int paramLen = paramNames.length;
				for (String fieldName : parameters.getFieldNames()) {
					int idx = Integer.valueOf(fieldName.substring(prefixLen));

					if (idx >= paramLen) {
						logger.warn("too many parameters than required"
								+ toString());
						continue;
					}

					String name = paramNames[idx];
					_Object p = parameters.getChild(fieldName);

					if (logger.isDebugEnabled())
						logger.debug("push parameter to context:" + name);

					parametersMap.put(name, p);
				}

			}
		}

		return parametersMap;
	}

	private _Object executeNativeMethod(String fullClassName,
			String methodName, _Object parameters, ParameterStyle parameterStyle) {
		_Object result = null;

		try {

			result = NativeMethodHelper.executeNativeMethod(fullClassName,
					methodName, parameters, parameterStyle);

		} catch (Exception e) {
			e.printStackTrace();

			// error handling, convert error to VException and set it as return
			// value
			VException ex = objectRepository.getFactory().createException(
					"NativeProcedure execution error:" + e.getMessage());

			CodePosition pos = new CodePosition(fullClassName, methodName,
					null, 0);

			ex.addTrace(e);

			ex.addTrace(pos);
			result = ex;
		}

		return result;
	}

	@Override
	public ExecutionStatus executeSendMessage(SendMessage instruction) {

		// native method
		String nativeMethodVarPrefix = "_java_";
		if (instruction.receiverVar.startsWith(nativeMethodVarPrefix)) {
			String fullClassName = instruction.receiverVar.substring(
					nativeMethodVarPrefix.length()).replace("_", ".");

			StringValue messageSubject = (StringValue) ctx
					.getObject(instruction.messageSubjectVar);
			String methodName = messageSubject.getValue();

			_Object result = executeNativeMethod(fullClassName, methodName,
					ctx.getObject(instruction.messageBodyVar),
					instruction.paramStyle);

			ctx.setObject(instruction.replyVar, result);

			return ExecutionStatus.COMPLETE;
		} else {

			if (ctx.step == 0) {

				_Object sender = ctx
						.getObject(ProcedureExecutionContext.VAR_SELF);
				_Object receiver = ctx.getObject(instruction.receiverVar);

				if (receiver == null)
					throw new RuntimeException("receiver object not exist:"
							+ instruction.receiverVar);

				StringValue messageSubject = (StringValue) ctx
						.getObject(instruction.messageSubjectVar);
				StringValue replySubjectSV = (StringValue) ctx
						.getObject(instruction.replySubjectVar);
				String replySubject = replySubjectSV != null ? replySubjectSV
						.getValue() : null;

				if (messageSubject == null)
					throw new RuntimeException("subject object not exist:"
							+ instruction.messageSubjectVar);

				_Object messageBody = ctx.getObject(instruction.messageBodyVar);

				/*
				 * if (sender == receiver) msgType = MessageType.Recursive;
				 */

				ExecutionStatus executionStatus = null;
				Message msg = null;
				if (instruction.sync) {
					if (logger.isDebugEnabled())
						logger.debug("executeSendMessage (SYNC), step 1 end; waiting...");

					msg = new Message(sender, receiver,
							messageSubject.getValue(), messageBody,
							instruction.paramStyle, message,
							MessageType.SyncRequest);

					ctx.step = 1;

					executionStatus = ExecutionStatus.WAITING;
				} else {
					// async
					if (logger.isDebugEnabled())
						logger.debug("executeSendMessage (ASYNC). finished, no reponse.");

					msg = Message.newAsyncRequestMessage(sender, receiver,
							messageSubject.getValue(), messageBody,
							instruction.paramStyle, replySubject);

					ctx.step = 0;

					// reply is null.
					ctx.setObject(instruction.replyVar, null);

					executionStatus = ExecutionStatus.COMPLETE;

				}

				postService.sendMessage(msg);

				return executionStatus;
			} else if (ctx.step == 1) {
				// it is the reply(return value) from the call.
				_Object reply = ctx.reply;

				// check if reply is exception, append trace source
				if (reply instanceof VException) {
					VException ex = (VException) reply;
					// set the exception as result
					ctx.setResult(ex);

					throw new RuntimeException("received exception:"
							+ ex.getMessage());
				}

				if (logger.isDebugEnabled()) {
					String replyValue = "";
					if (reply != null)
						replyValue = reply.getValue();

					logger.debug(String.format(
							"executeSendMessage, step 2; %s=%s",
							instruction.replyVar, replyValue));
				}

				ctx.setObject(instruction.replyVar, reply);

				// reset to 0
				ctx.step = 0;

				return ExecutionStatus.COMPLETE;
			} else
				throw new RuntimeException("invalid step:" + ctx.step);
		}
	}

	@Override
	public ExecutionStatus executeFieldAssignment(FieldAssignment instruction) {

		_Object rightObject = ctx.getObject(instruction.rightVar);

		_Object leftObject = ctx.getObject(instruction.ownerVar);

		if (leftObject == null)
			throw new RuntimeException("left object is null:"
					+ instruction.ownerVar);

		// only one field for each child object
		//
		// if (leftObject != null && leftObject.getChildIndex(rightObject) >= 0)
		// throw new RuntimeException(
		// String.format(
		// "left object '%s' already has field pointing to right object '%s'",
		// instruction.ownerVar, instruction.rightVar));

		_Object oldFieldObject = null;

		String fieldName = ctx.get(instruction.fieldNameVar).getValue();
		// ownership
		switch (instruction.assignmentType) {
		case OWN:
			if (rightObject == null) {

				oldFieldObject = leftObject.getChild(fieldName);

				// move to execution context
				if (oldFieldObject != null)
					leftObject.removeChild(oldFieldObject);

			} else {
				if (rightObject.getScope() != ObjectScope.ExecutionContext)
					throw new CannotObtainOwnershipException();

				oldFieldObject = leftObject.getChild(fieldName);

				// move to execution context
				if (oldFieldObject != null)
					leftObject.removeChild(oldFieldObject);

				leftObject.setField(fieldName, rightObject, true);
			}
			break;

		case REF:
			if (rightObject == null) {

				oldFieldObject = leftObject.getChild(fieldName);

				// move to execution context
				if (oldFieldObject != null)
					leftObject.removeChild(oldFieldObject);

			} else {
				leftObject.setField(fieldName, rightObject, false);
			}
			break;
		default:
			// auto
			if (rightObject == null) {
				oldFieldObject = leftObject.getChild(fieldName);

				// move to execution context
				if (oldFieldObject != null)
					leftObject.removeChild(oldFieldObject);
			} else if (leftObject.scope.stableThan(rightObject.getScope())) {

				if (rightObject.hasOwner())
					rightObject.downgradeLinkFromOwner();

				leftObject.setField(fieldName, rightObject, true);
			} else {
				if (instruction.valueObjectAssignmentPolicy == ValueObjectAssignmentPolicy.Clone
						&& (rightObject instanceof ValueObject)) {
					ValueObject newObject = (ValueObject) objectRepository
							.createObject(rightObject.getType(),
									ObjectScope.ExecutionContext);
					newObject.copyValue(rightObject);
					leftObject.setField(fieldName, newObject, true);
				} else {
					boolean owns = leftObject.getScope().stableThan(
							rightObject.getScope());
					leftObject.setField(fieldName, rightObject, owns);
				}
			}

		}

		return ExecutionStatus.COMPLETE;
	}

	@Override
	public ExecutionStatus executeVariableAssignment(
			VariableAssignment instruction) {
		_Object right = ctx.getObject(instruction.right);

		if (logger.isDebugEnabled()) {
			if (right != null && !right.hasRealName())
				right.setName(instruction.left);
		}

		if (instruction.assignmenType == null) {
			System.out.println("no type");
		}

		switch (instruction.assignmenType) {
		case OWN:
			throw new RuntimeException("not support assignment operator.");

		case REF:
			ctx.setVariable(instruction.left, right);
			break;
		default:
			if (right instanceof ValueObject) {
				ValueObject cloneObject = (ValueObject) objectRepository
						.createObject(right.getType(),
								ObjectScope.ExecutionContext);

				cloneObject.copyValue(right);
				ctx.setVariable(instruction.left, cloneObject);
			} else
				ctx.setVariable(instruction.left, right);
		}

		return ExecutionStatus.COMPLETE;
	}

	@Override
	public ExecutionStatus executeNoOperation(NoOperation instruction) {
		// intend to do nothing
		return ExecutionStatus.COMPLETE;
	}

	@Override
	public ExecutionStatus executeProcedureEnd(ProcedureEnd instruction) {
		// set flag
		ctx.setProcedureEnd(true);
		return ExecutionStatus.COMPLETE;
	}

}
