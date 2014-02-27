package com.bluesky.visualprogramming.vm;

/**
 * a enhanced interface of service, support start/stop/pause/resume operations.
 * 
 * this enabled the virtual machine to be paused.
 * 
 * @author jack
 * 
 */
public interface Service {

	/**
	 * set resource, create internal service
	 */
	void init();

	/**
	 * start sub-service(thread)
	 */
	void start();

	/**
	 * pause thread and internal service
	 */
	void pause();

	/**
	 * resume thread and internal service
	 */
	void resume();

	/**
	 * stop internal service
	 */
	void stop();

	/**
	 * free resource; destroy internal service
	 */
	void destroy();

}
