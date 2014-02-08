package com.bluesky.visualprogramming.vm.instruction;

import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.vm.InstructionType;

public class SendMessage extends Instruction {

	public boolean sync = true;
	public String callback;
	public String replyVar;
	public String receiverVar;
	
	
	public String messageSubjectVar;
	
	public String messageBodyVar;
	public ParameterStyle paramStyle;


	public SendMessage(int line) {
		super(line);
		
		this.type = InstructionType.SEND_MESSAGE;
	}

	@Override
	public String toString() {

		String syncDesc = sync ? "sync" : "async";
		return String.format("[send_message] %s %s = %s.$%s(%s)", syncDesc,
				replyVar, receiverVar, messageSubjectVar, messageBodyVar);
	}
}
