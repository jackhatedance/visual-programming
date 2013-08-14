package com.bluesky.visualprogramming.vm;

import java.util.List;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.vm.exceptions.AlreadyHasOwnerException;
import com.bluesky.visualprogramming.vm.exceptions.LabelNotFoundException;
import com.bluesky.visualprogramming.vm.instruction.AccessField;
import com.bluesky.visualprogramming.vm.instruction.FieldAssignment;
import com.bluesky.visualprogramming.vm.instruction.Goto;
import com.bluesky.visualprogramming.vm.instruction.GotoIf;
import com.bluesky.visualprogramming.vm.instruction.Instruction;
import com.bluesky.visualprogramming.vm.instruction.PopBlock;
import com.bluesky.visualprogramming.vm.instruction.PushBlock;
import com.bluesky.visualprogramming.vm.instruction.SendMessage;
import com.bluesky.visualprogramming.vm.instruction.VariableAssignment;

public class ProcedureExecutor implements InstructionExecutor {
	CompiledProcedure procedure;
	ProcedureExecutionContext ctx;

	void executeNativeProcedure(String name, _Object[] parameters) {

	}

	public void execute(CompiledProcedure procedure,
			ProcedureExecutionContext ctx) {
		List<Instruction> instructions = procedure.getInstructions();

		while (true) {
			executeOneStep(instructions, ctx);
		}

	}

	void executeOneStep(List<Instruction> instructions,
			ProcedureExecutionContext ctx) {

		Instruction instruction = instructions.get(ctx.currentInstructionIndex);

		// execute it
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
		Message msg = new Message(instruction.sync,sender,receiver,instruction.messageSubject,instruction.messageBody);
		
		receiverObj.addToMessageQueue(msg);
	}

	@Override
	public void executeFieldAssignment(FieldAssignment instruction) {

		_Object rightObject = ctx.getObject(instruction.rightVar);

		_Object leftObject = ctx.getObject(instruction.ownerVar);

		_Object oldFieldObject = null;
		// ownership
		switch (instruction.type) {
		case OWN:
			if (!rightObject.isContext())
				throw new AlreadyHasOwnerException();

			oldFieldObject = leftObject.getChild(instruction.fieldName);

			// move to execution context
			if (oldFieldObject != null)
				ctx.putTempObject(oldFieldObject);

			leftObject.addChild(rightObject, instruction.fieldName);

			break;

		case REF:
			leftObject.addPointer(rightObject, instruction.fieldName);

			break;
		default:
			// auto
			if (rightObject.isContext()) {
				oldFieldObject = leftObject.getChild(instruction.fieldName);

				// move to execution context
				if (oldFieldObject != null)
					ctx.putTempObject(oldFieldObject);

				leftObject.addChild(rightObject, instruction.fieldName);
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
}
