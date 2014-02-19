package com.bluesky.visualprogramming.messageEngine;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core._Object;

public class WorkerManager implements Runnable {

	static Logger logger = Logger.getLogger(WorkerManager.class);

	private ExecutorService executorServie;

	/**
	 * awake customers with messages in queue.
	 */
	private BlockingQueue<_Object> customers;

	private volatile boolean running = true;

	private ObjectRepository objectRepository;
	private PostService postService;

	public WorkerManager() {
		customers = new LinkedBlockingQueue<_Object>();

		executorServie = Executors.newFixedThreadPool(5);
	}

	public void init(ObjectRepository objectRepository, PostService postService) {
		this.objectRepository = objectRepository;
		this.postService = postService;
	}

	/**
	 * it must be awake, message queue is not empty, and no worker assigned.
	 * 
	 * @param obj
	 */
	public synchronized void addCustomer(_Object customer) {

		if (customers.contains(customer))
			throw new RuntimeException("customer is already added.");

		if (customer.hasWorker())
			throw new RuntimeException(
					"customer already has worker, cannot be added.");

		if (logger.isDebugEnabled())
			logger.debug(String.format(
					"customer '%s' is requesting for a worker, wait in queue.",
					customer.getName()));

		try {
			customers.put(customer);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void run() {
		while (running) {
			_Object cust;
			try {
				cust = customers.take();
				assign(cust);
			} catch (InterruptedException e) {
				if (logger.isDebugEnabled())
					logger.debug("interrupted.");
				
			}
		}
		if (logger.isDebugEnabled())
			logger.debug("thread terminated.");

	}

	private void assign(_Object cust) {

		if (logger.isDebugEnabled())
			logger.debug("assign worker for " + cust.getName());

		Worker worker = new Worker(objectRepository, this, postService, cust);

		executorServie.execute(worker);
	}

	public synchronized void stop() {
		running = false;		
	}

}
