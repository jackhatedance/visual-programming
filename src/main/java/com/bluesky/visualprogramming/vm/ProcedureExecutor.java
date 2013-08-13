package com.bluesky.visualprogramming.vm;

import java.util.List;

import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.vm.exceptions.AlreadyHasOwnerException;
import com.bluesky.visualprogramming.vm.exceptions.LabelNotFoundException;
import com.bluesky.visualprogramming.vm.instruction.AccessField;
import com.bluesky.visualprogramming.vm.instruction.Assignment;
import com.bluesky.visualprogramming.vm.instruction.Goto;
import com.bluesky.visualprogramming.vm.instruction.GotoIf;
import com.bluesky.visualprogramming.vm.instruction.Instruction;
import com.bluesky.visualprogramming.vm.instruction.PopBlock;
import com.bluesky.visualprogramming.vm.instruction.PushBlock;
import com.bluesky.visualprogramming.vm.instruction.SendMessage;

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

	}

	@Override
	public void executeAssignment(Assignment instruction) {
		// if is var
		_Object right = ctx.getObject(instruction.right);
		_Object left = ctx.getObject(instruction.left);

		_Object leftOwner = left.getOwner();

		// ownership
		switch (instruction.type) {
		case OWN:
			if (!right.isContext())
				throw new AlreadyHasOwnerException();

			ctx.setObject(instruction.left, right);

			break;

		case REF:
			ctx.setObject(instruction.left, right);

			break;
		default:
			// auto

			ctx.setObject(instruction.left, right);
			if (right.isContext() && !left.isContext())
				leftOwner.addChild(right, left.getName());
			else
				leftOwner.addPointer(right, left.getName());

		}

	}

}
