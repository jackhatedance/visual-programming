package com.bluesky.visualprogramming.vm.message;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core._Object;

/**
 * manage workers(aka thread to execute code). accept request from any object
 * that need worker.
 * 
 * @author jack
 * 
 */
public class WorkerService extends ThreadService implements Runnable  {

	static Logger logger = Logger.getLogger(WorkerService.class);
	/**
	 * thread pool that drive every worker.
	 */
	private ExecutorService executorServie;
	/**
	 * awake customers with messages in queue.
	 */
	private BlockingQueue<_Object> customers;

	/**
	 * working workers, will be removed after the work is done
	 */
	private Set<Worker> workers;

	private ObjectRepository objectRepository;
	private PostService postService;

	public WorkerService() {

		customers = new LinkedBlockingQueue<_Object>();
		executorServie = Executors.newFixedThreadPool(5);

		workers = new TreeSet<Worker>();
	}

	public void setup(ObjectRepository objectRepository,
			PostService postService) {
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
	public void doTask() throws InterruptedException {

		_Object cust = customers.take();
		assign(cust);


	}

	private void assign(_Object cust) {

		if (logger.isDebugEnabled())
			logger.debug("assign worker for " + cust.getName());

		Worker worker = new Worker(objectRepository, this, postService, cust);

		executorServie.execute(worker);

	}

	@Override
	public void init() {
		name = "WorkerService";
		super.init();		
	}

	/**
	 * when work is done, get retired.
	 * 
	 * @param worker
	 */
	public synchronized void removeWorker(Worker worker) {
		workers.remove(worker);
	}

	public synchronized void pause() {
		for (Worker worker : workers) {
			worker.pause();
		}
	}

	public synchronized void resume() {
		for (Worker worker : workers) {

			// TODO
			// worker.resume();
		}
	}
}
