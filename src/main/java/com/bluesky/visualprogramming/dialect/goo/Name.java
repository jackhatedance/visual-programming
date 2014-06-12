package com.bluesky.visualprogramming.dialect.goo;
/**
 * name of field or subject.
 * affect 3 instructions: accessField, fieldAssignment, sendMessage
 * 
 * @author jackding
 *
 */
public class Name {

	String value;
	NameType type;

	public Name(String value, NameType type) {
		this.value = value;
		this.type = type;
	}

	public String getValue() {
		return this.value;
	}

	public NameType getType() {
		return this.type;
	}
	
	public boolean isVariable(){
		return type.isVariable();
	}
	
	public String getLiteral() {
		return type.getLiteral(this);		
	}
}
