package com.bluesky.visualprogramming.vm;

import com.bluesky.visualprogramming.vm.instruction.AccessField;
import com.bluesky.visualprogramming.vm.instruction.CreateObject;
import com.bluesky.visualprogramming.vm.instruction.FieldAssignment;
import com.bluesky.visualprogramming.vm.instruction.Goto;
import com.bluesky.visualprogramming.vm.instruction.GotoIf;
import com.bluesky.visualprogramming.vm.instruction.NoOperation;
import com.bluesky.visualprogramming.vm.instruction.PopBlock;
import com.bluesky.visualprogramming.vm.instruction.ProcedureEnd;
import com.bluesky.visualprogramming.vm.instruction.PushBlock;
import com.bluesky.visualprogramming.vm.instruction.SendMessage;
import com.bluesky.visualprogramming.vm.instruction.VariableAssignment;

public interface InstructionExecutor {
	ExecutionStatus executeAccessField(AccessField instruction);
	
	ExecutionStatus executeCreateObject(CreateObject instruction);

	ExecutionStatus executeGoto(Goto instruction);

	ExecutionStatus executeGotoIf(GotoIf instruction);

	ExecutionStatus executePushBlock(PushBlock instruction);

	ExecutionStatus executePopBlock(PopBlock instruction);

	ExecutionStatus executeSendMessage(SendMessage instruction);

	ExecutionStatus executeFieldAssignment(FieldAssignment instruction);

	ExecutionStatus executeVariableAssignment(VariableAssignment instruction);
	
	ExecutionStatus executeNoOperation(NoOperation instruction);

	ExecutionStatus executeProcedureEnd(ProcedureEnd instruction);
}
