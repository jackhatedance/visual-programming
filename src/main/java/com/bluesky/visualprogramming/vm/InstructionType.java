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
		public ExecutionStatus execute(InstructionExecutor executor,
				Instruction instruction) {
			return executor.executeCreateObject((CreateObject) instruction);

		}
	},
	ACCESS_FIELD {
		@Override
		public ExecutionStatus execute(InstructionExecutor executor,
				Instruction instruction) {
			return executor.executeAccessField((AccessField) instruction);

		}
	},
	GOTO {
		@Override
		public ExecutionStatus execute(InstructionExecutor executor,
				Instruction instruction) {
			return executor.executeGoto((Goto) instruction);

		}

		@Override
		public boolean updatedInstructionIndex() {
			return true;
		}
	},
	GOTO_IF {
		@Override
		public ExecutionStatus execute(InstructionExecutor executor,
				Instruction instruction) {
			return executor.executeGotoIf((GotoIf) instruction);

		}

		@Override
		public boolean updatedInstructionIndex() {
			return true;
		}
	},
	PUSH_BLOCK {
		@Override
		public ExecutionStatus execute(InstructionExecutor executor,
				Instruction instruction) {
			return executor.executePushBlock((PushBlock) instruction);

		}
	},
	POP_BLOCK {
		@Override
		public ExecutionStatus execute(InstructionExecutor executor,
				Instruction instruction) {
			return executor.executePopBlock((PopBlock) instruction);

		}
	},
	SEND_MESSAGE {
		@Override
		public ExecutionStatus execute(InstructionExecutor executor,
				Instruction instruction) {
			return executor.executeSendMessage((SendMessage) instruction);

		}
	},
	NO_OPERATION {
		@Override
		public ExecutionStatus execute(InstructionExecutor executor,
				Instruction instruction) {
			return executor.executeNoOperation((NoOperation) instruction);
		}
	},
	FIELD_ASSIGNMENT {
		@Override
		public ExecutionStatus execute(InstructionExecutor executor,
				Instruction instruction) {
			return executor.executeFieldAssignment((FieldAssignment) instruction);
		}
	},
	VARIABLE_ASSIGNMENT {
		@Override
		public ExecutionStatus execute(InstructionExecutor executor,
				Instruction instruction) {
			return executor.executeVariableAssignment((VariableAssignment) instruction);
		}
	}, PROCEDURE_END{
		@Override
		public ExecutionStatus execute(InstructionExecutor executor,
				Instruction instruction) {
			return executor.executeProcedureEnd((ProcedureEnd) instruction);
		}
	};

	abstract public ExecutionStatus execute(InstructionExecutor executor,
			Instruction instruction);

	public boolean updatedInstructionIndex() {
		return false;
	}
}
