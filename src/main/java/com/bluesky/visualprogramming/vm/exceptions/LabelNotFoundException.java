package com.bluesky.visualprogramming.vm.exceptions;

public class LabelNotFoundException extends RuntimeException {
	public LabelNotFoundException(String label) {
		super(label);
	}
}
