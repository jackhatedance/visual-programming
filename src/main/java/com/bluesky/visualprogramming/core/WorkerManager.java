package com.bluesky.visualprogramming.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bluesky.my4gl.core.flow.node.OneInPortNode;

public class WorkerManager implements Runnable {

	private ExecutorService executorServie;

	/**
	 * awake customers with messages in queue.
	 */
	private List<_Object> customers;

	private boolean running;

	private ObjectRepository objectRepository;
	private PostService postService;

	public WorkerManager() {
		customers = Collections.synchronizedList(new ArrayList<_Object>());

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

		if (!customer.isAwake())
			throw new RuntimeException("customer is sleep, cannot be added.");

		if (customer.hasWorker())
			throw new RuntimeException(
					"customer alreadt has worker, cannot be added.");

		customers.add(customer);
	}

	/**
	 * assign all worker to customers
	 */
	@Override
	public void run() {

		while (true) {
			if (!customers.isEmpty()) {
				_Object cust = customers.remove(0);

				// check if available worker there, or just wait for that

				Runnable worker = new Worker(objectRepository, this,
						postService, cust);

				executorServie.execute(worker);

			}

		}

	}

}
