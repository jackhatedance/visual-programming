package com.bluesky.visualprogramming.vm.debug;

import com.bluesky.visualprogramming.core.Procedure;
import com.bluesky.visualprogramming.core._Object;

public class BreakPoint {
	//_Object object;
	//Procedure procedure;
	String path;
	int line;
	boolean enabled=true;
	public BreakPoint(String path, int line) {
	this.path=path;
	this.line=line;
	}
	public boolean match(String path, int line){
		return this.enabled && this.path.equals(path) && (this.line==line);
	}
}
