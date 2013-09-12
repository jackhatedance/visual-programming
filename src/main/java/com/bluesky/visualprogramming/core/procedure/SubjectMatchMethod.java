package com.bluesky.visualprogramming.core.procedure;


public enum SubjectMatchMethod {
	RegularExpression {
		@Override
		public SubjectMatcher getMatcher() {

			return new RegExpMatcher();
		}
	},
	Groovy {
		@Override
		public SubjectMatcher getMatcher() {

			return new GroovyMatcher();
		}
	};

	public abstract SubjectMatcher getMatcher();
}
