package com.bluesky.visualprogramming.vm.message;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.vm.debug.Debugger;

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
	private Debugger debugger;

	public WorkerService() {

		customers = new LinkedBlockingQueue<_Object>();
		executorServie = Executors.newFixedThreadPool(5);

		workers = new HashSet<Worker>();
	}

	public void setup(ObjectRepository objectRepository,
			PostService postService, Debugger debugger) {
		this.objectRepository = objectRepository;
		this.postService = postService;
		this.debugger = debugger;
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

		_Object cust = customers
				.poll(TASK_POLL_TIME_OUT, TimeUnit.MILLISECONDS);

		if (cust != null)
			assign(cust);

	}

	private void assign(_Object cust) {

		if (logger.isDebugEnabled())
			logger.debug("assign worker for " + cust.getName());

		Worker worker = new Worker(objectRepository, this, postService, cust, debugger);

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
		logger.debug("worker removed");
		workers.remove(worker);
	}

	/**
	 * wait until all workers get paused.
	 */
	public synchronized void pause() {


		// stop the worker threads
		CountDownLatch latch = new CountDownLatch(workers.size());
		for (Worker worker : workers) {
			worker.pause(latch);
		}

		try {
			logger.debug("wait for all workers to pause");
			latch.await();
			logger.debug("all workers are paused");
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		// stop the main thread of the service
		super.pause();
	}

	public synchronized void resume() {
		super.resume();

		for (Worker worker : workers) {
			worker.resume();
		}

		logger.debug("all workers are resumed");


	}
}
