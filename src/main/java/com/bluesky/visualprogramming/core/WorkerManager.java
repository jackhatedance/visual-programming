package com.bluesky.visualprogramming.core;

import java.util.List;

public class WorkerManager {

	private List<Worker> workers;
	private List<_Object> customers;
	
	/**
	 * it must be awake, message queue is not empty, and no worker assigned.
	 * @param obj
	 */
	public synchronized void addCustomer(_Object customer){
	
		if(customers.contains(customer))
			throw new RuntimeException("customer is already added.");
		
		if(!customer.isAwake())
			throw new RuntimeException("customer is sleep, cannot be added.");
		
		if(customer.hasWorker())
			throw new RuntimeException("customer alreadt has worker, cannot be added.");
		
		customers.add(customer);
	}
}
