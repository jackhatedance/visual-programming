package com.bluesky.visualprogramming.core;

public interface ObjectRepositoryListener {
	/**
	 * after create an object by the repository
	 * 
	 * @param obj
	 */
	void afterCreate(_Object obj);

	void beforeDestroy(_Object obj);

	/**
	 * after load from XML file. It is good time to create remote agent, attach
	 * timer.
	 * 
	 * @param obj
	 */
	void afterLoadFromFile(_Object obj);

	/**
	 * after "afterLoadFromFile" notified.
	 * 
	 * not effected.
	 */
	void afterAllLoaded();
}
