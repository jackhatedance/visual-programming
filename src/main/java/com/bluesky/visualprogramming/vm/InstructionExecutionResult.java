package com.bluesky.visualprogramming.vm;

public class InstructionExecutionResult {
	private ExecutionStatus status;
	private String desc;
	private int line;
	
	
	public ExecutionStatus getStatus() {
		return status;
	}
	public void setStatus(ExecutionStatus status) {
		this.status = status;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	

}
