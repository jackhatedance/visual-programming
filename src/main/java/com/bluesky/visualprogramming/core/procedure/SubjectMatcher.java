package com.bluesky.visualprogramming.core.procedure;

import com.bluesky.visualprogramming.core.Message;

public abstract class SubjectMatcher {
	protected String rule;

	public SubjectMatcher(String rule) {
		this.rule = rule;
	}

	public abstract boolean matches(String subject);

	public void postProcess(Message msg) {
	};
}
