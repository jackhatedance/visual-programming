package com.bluesky.visualprogramming.vm;

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

public enum InstructionType {
	CREATE_OBJECT {
		@Override
		public void execute(InstructionExecutor executor,
				Instruction instruction) {
			executor.executeCreateObject((CreateObject) instruction);

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
	},
	NO_OPERATION {
		@Override
		public void execute(InstructionExecutor executor,
				Instruction instruction) {
			executor.executeNoOperation((NoOperation) instruction);
		}
	},
	FIELD_ASSIGNMENT {
		@Override
		public void execute(InstructionExecutor executor,
				Instruction instruction) {
			executor.executeFieldAssignment((FieldAssignment) instruction);
		}
	},
	VARIABLE_ASSIGNMENT {
		@Override
		public void execute(InstructionExecutor executor,
				Instruction instruction) {
			executor.executeVariableAssignment((VariableAssignment) instruction);
		}
	}, PROCEDURE_END{
		@Override
		public void execute(InstructionExecutor executor,
				Instruction instruction) {
			executor.executeProcedureEnd((ProcedureEnd) instruction);
		}
	};

	abstract public void execute(InstructionExecutor executor,
			Instruction instruction);

	public boolean updateInstructionIndex() {
		return false;
	}
}
