package com.bluesky.visualprogramming.vm.message;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.vm.Service;
import com.bluesky.visualprogramming.vm.ServiceStatus;

/**
 * 
 * base class to support service interface
 * 
 * @author jack
 * 
 */
public abstract class AbstractService implements Runnable, Service {

	static Logger logger = Logger.getLogger(AbstractService.class);

	protected String name = "abstractService";
	protected Thread thread;
	/**
	 * set by other, if it is true, then thread try to wait.
	 */
	protected volatile boolean pauseFlag = false;
	protected volatile boolean stopFlag = false;
	protected ServiceStatus _status;


	protected void testTransitTo(ServiceStatus newStatus){
		_status.assertTransit(newStatus);
	
	}

	protected ServiceStatus getStatus(){
		return _status;
	}
	
	protected void setStatus(ServiceStatus status) {

		if (logger.isDebugEnabled())
			logger.debug(String.format("status change from %s to %s",
					this._status, status));

		this._status = status;
	}
	
	@Override
	public abstract void run() ;

	@Override
	public void init() {
		thread = new Thread(this, name);

		setStatus( ServiceStatus.Initialized);
	}

	@Override
	public void start() {
		testTransitTo(ServiceStatus.Running);
		 

		thread.start();
		setStatus( ServiceStatus.Running);
	}

	@Override
	public synchronized void pause() {
		testTransitTo(ServiceStatus.Paused);
		
		pauseFlag = true;

		// in case it waits on the queue.take()
		thread.interrupt();

		// wait until it really paused
		while (getStatus() != ServiceStatus.Paused) {
			try {
				wait(10);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		// below status is set in run();
		// serviceStatus = ServiceStatus.Paused;
	}

	@Override
	public synchronized void resume() {
		testTransitTo(ServiceStatus.Running);
		
		notify();
		
		//status updated in run()
		//setStatus( ServiceStatus.Running);
	}

	@Override
	public void stop() {
		testTransitTo(ServiceStatus.Stopped);
		
		stopFlag = true;

		// in case it waits on the queue.take()
		thread.interrupt();

	}

	@Override
	public void destroy() {
		testTransitTo(ServiceStatus.Destroyed);
		

		setStatus( ServiceStatus.Destroyed);

	}

}
