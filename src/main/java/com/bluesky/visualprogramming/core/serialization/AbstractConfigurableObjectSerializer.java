package com.bluesky.visualprogramming.core.serialization;

import com.bluesky.visualprogramming.core.ObjectRepository;

public abstract class AbstractConfigurableObjectSerializer implements ConfigurableObjectSerializer{

	private ObjectRepository repo;

	public ObjectRepository getRepo() {
		return repo;
	}

	public void setRepo(ObjectRepository repo) {
		this.repo = repo;
	}
	 
	
	
	
}
