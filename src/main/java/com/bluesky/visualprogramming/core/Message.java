package com.bluesky.visualprogramming.core;

import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;


public class Message {

	boolean sync;
	_Object sender;
	_Object receiver;
	String subject;
	_Object body;
	
	ProcedureExecutionContext executionContext;
	
	_Object reply;

	/**
	 * if async, the callback procedure name, pass the reply or message
	 */
	String callback;
}
