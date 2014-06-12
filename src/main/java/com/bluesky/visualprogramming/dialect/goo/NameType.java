package com.bluesky.visualprogramming.dialect.goo;

public enum NameType {
	ID {
		@Override
		public boolean isVariable() {			
			return false;
		}
		@Override
		public String getLiteral(Name name) {

			return name.getValue();
		}
	},
	String_ {
		@Override
		public String getLiteral(Name name) {

			return "\"" + name.getValue() + "\"";
		}
		@Override
		public boolean isVariable() {			
			return false;
		}
	},
	Variable {
		@Override
		public String getLiteral(Name name) {

			return "$" + name.getValue();
		}
		@Override
		public boolean isVariable() {			
			return true;
		}
	};

	abstract public String getLiteral(Name name);
	abstract public boolean isVariable();
}