package com.bluesky.visualprogramming.vm.message;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.vm.Service;
import com.bluesky.visualprogramming.vm.ServiceStatus;

/**
 * 
 * a base class that for usual scenary that consist of one thread and support
 * pause/resume.
 * 
 * 
 * @author jack
 * 
 */
public abstract class ThreadService implements Runnable, Service {

	static Logger logger = Logger.getLogger(ThreadService.class);

	protected String name = "abstractService";
	protected Thread thread;
	/**
	 * set by other, if it is true, then thread try to wait.
	 */
	protected volatile boolean pauseFlag = false;
	protected volatile boolean stopFlag = false;
	protected ServiceStatus _status;

	protected void testTransitTo(ServiceStatus newStatus) {
		_status.assertTransit(newStatus);

	}

	protected ServiceStatus getStatus() {
		return _status;
	}

	protected void setStatus(ServiceStatus status) {

		if (logger.isDebugEnabled())
			logger.debug(String.format("%s status change from %s to %s", name,
					this._status, status));

		this._status = status;
	}

	@Override
	public void init() {
		thread = new Thread(this, name);

		setStatus(ServiceStatus.Initialized);
	}

	protected abstract void doTask() throws InterruptedException;

	@Override
	public void run() {
		while (!stopFlag) {

			try {
				synchronized (this) {
					while (pauseFlag) {
						setStatus(ServiceStatus.Paused);

						// reset
						pauseFlag = false;

						wait();
						//already updated the status in resume function
						//setStatus(ServiceStatus.Running);
					}

				}

				doTask();

			} catch (InterruptedException e) {
				if (logger.isDebugEnabled())
					logger.debug(name + "interrupted.");

			}
		}

		setStatus(ServiceStatus.Stopped);
		// reset
		stopFlag = false;

		if (logger.isDebugEnabled())
			logger.debug("thread stopped.");

	}

	@Override
	public void start() {
		testTransitTo(ServiceStatus.Running);

		thread.start();
		setStatus(ServiceStatus.Running);
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

		// status updated in run()
		setStatus( ServiceStatus.Running);
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

		setStatus(ServiceStatus.Destroyed);

	}

}
