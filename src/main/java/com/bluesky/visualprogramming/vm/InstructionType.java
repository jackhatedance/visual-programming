package com.bluesky.visualprogramming.vm;

import com.bluesky.visualprogramming.vm.instruction.AccessField;
import com.bluesky.visualprogramming.vm.instruction.Goto;
import com.bluesky.visualprogramming.vm.instruction.GotoIf;
import com.bluesky.visualprogramming.vm.instruction.Instruction;
import com.bluesky.visualprogramming.vm.instruction.PopBlock;
import com.bluesky.visualprogramming.vm.instruction.PushBlock;
import com.bluesky.visualprogramming.vm.instruction.SendMessage;

public enum InstructionType {
	CREATE_VALUE_OBJECT{
		@Override
		public void execute(InstructionExecutor executor,
				Instruction instruction) {
			executor.executeAccessField((AccessField) instruction);
			
		}
	},
	ACCESS_FIELD {
		@Override
		public void execute(InstructionExecutor executor,
				Instruction instruction) {
			executor.executeAccessField((AccessField) instruction);

		}
	},
	GOTO {
		@Override
		public void execute(InstructionExecutor executor,
				Instruction instruction) {
			executor.executeGoto((Goto) instruction);

		}

		@Override
		public boolean updateInstructionIndex() {
			return true;
		}
	},
	GOTO_IF {
		@Override
		public void execute(InstructionExecutor executor,
				Instruction instruction) {
			executor.executeGotoIf((GotoIf) instruction);

		}

		@Override
		public boolean updateInstructionIndex() {
			return true;
		}
	},
	PUSH_BLOCK {
		@Override
		public void execute(InstructionExecutor executor,
				Instruction instruction) {
			executor.executePushBlock((PushBlock) instruction);

		}
	},
	POP_BLOCK {
		@Override
		public void execute(InstructionExecutor executor,
				Instruction instruction) {
			executor.executePopBlock((PopBlock) instruction);

		}
	},
	SEND_MESSAGE {
		@Override
		public void execute(InstructionExecutor executor,
				Instruction instruction) {
			executor.executeSendMessage((SendMessage) instruction);

		}
	};

	abstract public void execute(InstructionExecutor executor,
			Instruction instruction);

	public boolean updateInstructionIndex() {
		return false;
	}
}
