package com.bluesky.visualprogramming.vm.instruction;

import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.dialect.goo.Name;
import com.bluesky.visualprogramming.dialect.goo.NameType;
import com.bluesky.visualprogramming.vm.InstructionType;

public class SendMessage extends Instruction {

	public boolean sync = true;
	public String replyVar;
	public String receiverVar;
	
	
	public Name messageSubjectName;
	public String replySubjectVar;
	
	public String messageBodyVar;
	public ParameterStyle paramStyle;


	public SendMessage(int line) {
		super(line);
		
		this.type = InstructionType.SEND_MESSAGE;
	}

	@Override
	public String toString() {

		String syncDesc = sync ? "sync" : "async";
		String replySubjectStr = "";
		if (replySubjectVar != null && replySubjectVar != "") {
			replySubjectStr = "#" + replySubjectVar;
		}

		return String
				.format("[send_message] %s %s = %s.%s%s(%s)", syncDesc,
						replyVar, receiverVar, messageSubjectName.getLiteral(), replySubjectStr,
				messageBodyVar);
	}
}
