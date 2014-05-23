package com.bluesky.visualprogramming.core;

public enum SystemField {

	Prototype, Type, SubjectMatch, Aliases, Graphic, Layout, Collection;
	
	
	public String getFieldName(){
		String first = this.name().substring(0,1).toLowerCase();
		String rest = this.name().substring(1, name().length());
		
		return first + rest;	
		
	}
}
