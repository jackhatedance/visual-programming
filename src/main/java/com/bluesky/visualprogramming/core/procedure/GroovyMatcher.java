package com.bluesky.visualprogramming.core.procedure;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class GroovyMatcher extends SubjectMatcher {

	public GroovyMatcher(String rule) {
		super(rule);

	}

	@Override
	public boolean matches(String subject) {
		Binding binding = new Binding();
		binding.setVariable("subject", subject);
		GroovyShell shell = new GroovyShell(binding);

		Boolean value = (Boolean) shell.evaluate("return " + rule);

		return value;
	}

}
