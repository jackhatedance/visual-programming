package com.bluesky.visualprogramming.core;

public class InvalidELException extends RuntimeException {

	public InvalidELException(String el, String field) {
		super(String.format(" field '%s' not found of '%s'", field, el));

	}
}
