package com.bluesky.visualprogramming.vm.instruction;

import com.bluesky.visualprogramming.core._Object;

public class SendMessage extends Instruction {

	public boolean sync;
	public String callback;
	public String receiverVar;
	public String messageSubject;
	public _Object messageBody;
}
