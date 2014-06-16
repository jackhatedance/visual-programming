package com.bluesky.visualprogramming.core;

public interface ObjectRepositoryListener {
	/**
	 * remove it to reduce suspending time of auto-saving. and no one is really
	 * using it so far.
	 */
	// void beforeSave(_Object obj);
	
	/**
	 * after create an object by the repository
	 * 
	 * @param obj
	 */
	void afterCreate(_Object obj);

	void beforeDestroy(_Object obj);

	/**
	 * after load from XML file. the objects are not linked. 
	 * 
	 * system should process, linking.
	 * 
	 *  after this event, the objects are ready to navigate.
	 *  
	 * 
	 * @param obj
	 */
	void afterLoadFromFile(_Object obj);
	
	/**
	 * after load completed.
	 * 
	 * it is time to start service.
	 * 
	 * @param obj
	 */
	void onStartService(_Object obj);

	/**
	 * after "afterLoadFromFile" notified.
	 * 
	 * not effected.
	 */
	void afterAllLoaded();
}
