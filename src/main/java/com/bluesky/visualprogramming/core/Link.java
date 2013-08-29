package com.bluesky.visualprogramming.core;

/**
 * target must not be a link.
 * 
 * @author jackding
 * 
 */
public abstract class Link extends _Object {

	public Link(long id) {
		super(id);

	}

	public abstract _Object getTarget();
}
