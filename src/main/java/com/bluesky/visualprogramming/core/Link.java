package com.bluesky.visualprogramming.core;

public abstract class Link extends _Object {

	public Link(long id) {
		super(id);
		
	}

	public abstract _Object getTarget();
}