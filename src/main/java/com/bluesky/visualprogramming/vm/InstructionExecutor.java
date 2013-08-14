package com.bluesky.visualprogramming.vm;

import com.bluesky.visualprogramming.vm.instruction.AccessField;
import com.bluesky.visualprogramming.vm.instruction.FieldAssignment;
import com.bluesky.visualprogramming.vm.instruction.Goto;
import com.bluesky.visualprogramming.vm.instruction.GotoIf;
import com.bluesky.visualprogramming.vm.instruction.PopBlock;
import com.bluesky.visualprogramming.vm.instruction.PushBlock;
import com.bluesky.visualprogramming.vm.instruction.SendMessage;
import com.bluesky.visualprogramming.vm.instruction.VariableAssignment;

public interface InstructionExecutor {
	void executeAccessField(AccessField instruction);

	void executeGoto(Goto instruction);

	void executeGotoIf(GotoIf instruction);

	void executePushBlock(PushBlock instruction);

	void executePopBlock(PopBlock instruction);

	void executeSendMessage(SendMessage instruction);

	void executeFieldAssignment(FieldAssignment instruction);

	void executeVariableAssignment(VariableAssignment instruction);
}
