package com.bluesky.visualprogramming.vm;

import java.util.List;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.PostService;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.vm.exceptions.AlreadyHasOwnerException;
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
	ObjectRepository objectRepository;
	CompiledProcedure procedure;
	ProcedureExecutionContext ctx;
	PostService postService;

	private ExecutionStatus instructionExecutionStatus = ExecutionStatus.COMPLETE;

	public ProcedureExecutor(ObjectRepository objectRepository,PostService postService,
			CompiledProcedure procedure, ProcedureExecutionContext ctx) {

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
	public ExecutionStatus execute() {
		List<Instruction> instructions = procedure.getInstructions();

		while (true) {
			executeOneStep(instructions);

			if (instructionExecutionStatus == ExecutionStatus.WAITING)
				break;
			
			if (ctx.isProcedureEnd()){
				instructionExecutionStatus = ExecutionStatus.COMPLETE;
				break;
			}

		}
		return instructionExecutionStatus;
	}

	void executeOneStep(List<Instruction> instructions) {

		Instruction instruction = instructions.get(ctx.currentInstructionIndex);

		// execute it
		System.out.println(instruction.toString());
		instruction.type.execute(this, instruction);

		if (!instruction.type.updateInstructionIndex())
			ctx.currentInstructionIndex++;

	}

	@Override
	public void executeAccessField(AccessField instruction) {

		_Object obj = ctx.getObject(instruction.objName);
		_Object result = obj.getChild(instruction.fieldName);

		ctx.setObject(instruction.varName, result);
	}

	@Override
	public void executeCreateObject(CreateObject instruction) {
		// owner is execution context
		_Object obj = objectRepository.createObject(null, instruction.varName,
				instruction.objType, instruction.literal);

		ctx.setVariable(instruction.varName, obj);
	}

	@Override
	public void executeGoto(Goto instruction) {
		Integer index = procedure.getLabelIndex(instruction.label);

		if (index == null)
			throw new LabelNotFoundException();

		ctx.currentInstructionIndex = index;
	}

	@Override
	public void executeGotoIf(GotoIf instruction) {
		_Object actualObject = ctx.getObject(instruction.actualVarName);

		BooleanValue b = (BooleanValue) actualObject;
		if (b.getBooleanValue() == instruction.expected) {
			Integer index = procedure.getLabelIndex(instruction.label);
			if (index == null)
				throw new LabelNotFoundException();

			ctx.currentInstructionIndex = index;
		}

	}

	@Override
	public void executePushBlock(PushBlock instruction) {
		BlockStackItem item = new BlockStackItem();
		item.type = item.type;
		ctx.blockStacks.push(item);
	}

	@Override
	public void executePopBlock(PopBlock instruction) {
		ctx.blockStacks.pop();
	}

	@Override
	public void executeSendMessage(SendMessage instruction) {
		_Object receiverObj = ctx.getObject(instruction.receiverVar);

		_Object sender = ctx.getObject("self");
		_Object receiver = ctx.getObject(instruction.receiverVar);
		if (receiver == null)
			throw new RuntimeException("receiver object not exist:"
					+ instruction.receiverVar);

		_Object messageBody = ctx.getObject(instruction.messageBodyVar);
		Message msg = new Message(instruction.sync, sender, receiver,
				instruction.messageSubject, messageBody,instruction.paramStyle);

		sender.sleep();
		postService.sendMessage(msg);		
	}

	@Override
	public void executeFieldAssignment(FieldAssignment instruction) {

		_Object rightObject = ctx.getObject(instruction.rightVar);

		_Object leftObject = ctx.getObject(instruction.ownerVar);

		_Object oldFieldObject = null;
		// ownership
		switch (instruction.assignmenType) {
		case OWN:
			if (!rightObject.isContext())
				throw new AlreadyHasOwnerException();

			oldFieldObject = leftObject.getChild(instruction.fieldName);

			// move to execution context
			if (oldFieldObject != null)
				ctx.putTempObject(oldFieldObject);

			leftObject.addChild(rightObject, instruction.fieldName,true);

			break;

		case REF:
			leftObject.addChild(rightObject, instruction.fieldName,false);

			break;
		default:
			// auto
			if (rightObject.isContext()) {
				oldFieldObject = leftObject.getChild(instruction.fieldName);

				// move to execution context
				if (oldFieldObject != null)
					ctx.putTempObject(oldFieldObject);

				leftObject.addChild(rightObject, instruction.fieldName,true);
			} else {
				leftObject.addPointer(rightObject, instruction.fieldName);
			}

		}

	}

	@Override
	public void executeVariableAssignment(VariableAssignment instruction) {
		_Object right = ctx.getObject(instruction.right);
		ctx.setVariable(instruction.left, right);

	}

	@Override
	public void executeNoOperation(NoOperation instruction) {
		// intend to do nothing

	}

	@Override
	public void executeProcedureEnd(ProcedureEnd instruction) {
		// set flag
		ctx.setProcedureEnd(true);

	}

}
