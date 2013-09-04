package com.bluesky.visualprogramming.vm;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageType;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.dialect.goo.GooCompiler;
import com.bluesky.visualprogramming.messageEngine.PostService;
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
import com.bluesky.visualprogramming.vm.instruction.VariableAssignment;

public class ProcedureExecutor implements InstructionExecutor {
	static Logger logger = Logger.getLogger(ProcedureExecutor.class);

	ObjectRepository objectRepository;
	CompiledProcedure procedure;
	ProcedureExecutionContext ctx;
	PostService postService;

	public ProcedureExecutor(ObjectRepository objectRepository,
			PostService postService, CompiledProcedure procedure,
			ProcedureExecutionContext ctx) {

		this.objectRepository = objectRepository;
		this.postService = postService;
		this.procedure = procedure;
		this.ctx = ctx;
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

		while (true) {
			ExecutionStatus instructionExecutionStatus = executeOneStep(instructions);

			if (instructionExecutionStatus == ExecutionStatus.WAITING) {
				ctx.executionStatus = ExecutionStatus.WAITING;
				break;
			} else if (instructionExecutionStatus == ExecutionStatus.ERROR) {
				ctx.executionStatus = ExecutionStatus.ERROR;
				break;
			}

			if (ctx.isProcedureEnd()) {
				ctx.executionStatus = ExecutionStatus.COMPLETE;
				break;
			}

		}

	}

	public ExecutionStatus executeOneStep(List<Instruction> instructions) {

		Instruction instruction = instructions.get(ctx.currentInstructionIndex);

		// execute it
		if (logger.isDebugEnabled())
			logger.debug(instruction.toString());

		ExecutionStatus es;
		try {
			es = instruction.type.execute(this, instruction);
			// goto instruction update index by them self
			if (!instruction.type.updatedInstructionIndex()) {
				//
				if (es == ExecutionStatus.COMPLETE)
					ctx.currentInstructionIndex++;
			}
		} catch (Exception e) {
			es = ExecutionStatus.ERROR;
			e.printStackTrace();
		}

		return es;
	}

	@Override
	public ExecutionStatus executeAccessField(AccessField instruction) {

		_Object obj = ctx.getObject(instruction.objName);
		if (obj == null)
			throw new RuntimeException("object not exist:"
					+ instruction.toString());

		_Object result = obj.getChild(instruction.fieldName);

		ctx.setObject(instruction.varName, result);

		return ExecutionStatus.COMPLETE;
	}

	@Override
	public ExecutionStatus executeCreateObject(CreateObject instruction) {
		// owner is execution context

		// remove the quotation marks
		String strValue = instruction.objType.extractValue(instruction.literal);
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
			throw new LabelNotFoundException();

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
				throw new LabelNotFoundException();

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

	@Override
	public ExecutionStatus executeSendMessage(SendMessage instruction) {
		if (ctx.step == 0) {
			_Object receiverObj = ctx.getObject(instruction.receiverVar);

			_Object sender = ctx.getObject("self");
			_Object receiver = ctx.getObject(instruction.receiverVar);
			if (receiver == null)
				throw new RuntimeException("receiver object not exist:"
						+ instruction.receiverVar);

			_Object messageBody = ctx.getObject(instruction.messageBodyVar);

			MessageType msgType = MessageType.Normal;
			if (sender == receiver)
				msgType = MessageType.Recursive;

			Message msg = new Message(instruction.sync, sender, receiver,
					instruction.messageSubject, messageBody,
					instruction.paramStyle, null, msgType);

			ctx.step = 1;

			// sender.sleep();
			postService.sendMessage(msg);

			if (logger.isDebugEnabled())
				logger.debug("executeSendMessage, step 1 end; waiting...");

			return ExecutionStatus.WAITING;
		} else if (ctx.step == 1) {
			// it is the reply(return value) from the call.
			_Object reply = ctx.reply;

			String replyValue = "";
			if (reply != null)
				replyValue = reply.getValue();

			if (logger.isDebugEnabled())
				logger.debug(String.format("executeSendMessage, step 2; %s=%s",
						instruction.replyVar, replyValue));

			ctx.setObject(instruction.replyVar, reply);

			// reset to 0
			ctx.step = 0;

			return ExecutionStatus.COMPLETE;
		} else
			throw new RuntimeException("invalid step:" + ctx.step);

	}

	@Override
	public ExecutionStatus executeFieldAssignment(FieldAssignment instruction) {

		_Object rightObject = ctx.getObject(instruction.rightVar);

		_Object leftObject = ctx.getObject(instruction.ownerVar);

		_Object oldFieldObject = null;
		// ownership
		switch (instruction.assignmenType) {
		case OWN:
			if (rightObject == null) {
				oldFieldObject = leftObject.getChild(instruction.fieldName);

				// move to execution context
				if (oldFieldObject != null)
					leftObject.removeChild(oldFieldObject);

			} else {
				if (rightObject.getScope() != ObjectScope.ExecutionContext)
					throw new CannotObtainOwnershipException();

				oldFieldObject = leftObject.getChild(instruction.fieldName);

				// move to execution context
				if (oldFieldObject != null)
					leftObject.removeChild(oldFieldObject);

				leftObject.addChild(rightObject, instruction.fieldName, true);
			}
			break;

		case REF:
			leftObject.addChild(rightObject, instruction.fieldName, false);

			break;
		default:
			// auto
			if (rightObject == null) {
				oldFieldObject = leftObject.getChild(instruction.fieldName);

				// move to execution context
				if (oldFieldObject != null)
					leftObject.removeChild(oldFieldObject);
			} else if (rightObject.getScope() == ObjectScope.ExecutionContext) {

				leftObject.setChild(instruction.fieldName, rightObject, true);
			} else {
				leftObject.addPointer(rightObject, instruction.fieldName);
			}

		}

		return ExecutionStatus.COMPLETE;
	}

	@Override
	public ExecutionStatus executeVariableAssignment(
			VariableAssignment instruction) {
		_Object right = ctx.getObject(instruction.right);
		ctx.setVariable(instruction.left, right);

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
