package com.bluesky.my4gl.internalClass.lang;

public enum PrimitiveType {
	Boolean {
		@Override
		public java.lang.String getFullName() {

			return "com.bluesky.my4gl.lang.Boolean";
		}
	},
	Integer {
		@Override
		public java.lang.String getFullName() {
			return "com.bluesky.my4gl.lang.Integer";
		}
	},
	Float {
		@Override
		public java.lang.String getFullName() {
			return "com.bluesky.my4gl.lang.Float";
		}
	},
	Character{
		@Override
		public java.lang.String getFullName() {
			return "com.bluesky.my4gl.lang.Character";
		}
	},
	String {
		@Override
		public java.lang.String getFullName() {
			return "com.bluesky.my4gl.lang.String";
		}
	},
	Array {
		@Override
		public java.lang.String getFullName() {
			return "com.bluesky.my4gl.lang.Array";
		}
	};

	public abstract String getFullName();
}
