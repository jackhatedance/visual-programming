package com.bluesky.visualprogramming.vm.instruction;

public enum AssignmentType {
	OWN {
		@Override
		public String getOperator() {
			 
			return "=>";
		}
	}, REF {
		@Override
		public String getOperator() {
			
			return "->";
		}
	}, AUTO {
		@Override
		public String getOperator() {
			
			return "~=";
		}
	};
	
	abstract public String getOperator();
}
