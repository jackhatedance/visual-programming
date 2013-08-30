package com.bluesky.visualprogramming.vm;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.messageEngine.PostService;
import com.bluesky.visualprogramming.messageEngine.Worker;
import com.bluesky.visualprogramming.messageEngine.WorkerManager;

/**
 * one vm start with one image.
 * 
 * @author jack
 * 
 */
public class VirtualMachine {
	static Logger logger = Logger.getLogger(VirtualMachine.class);

	private ObjectRepository objectRepository;

	private WorkerManager workerManager;
	private Thread workerManagerThread;

	private PostService postService;
	private Thread postServiceThread;

	private boolean running = false;

	private static VirtualMachine instance = null;

	public static void setInstance(VirtualMachine inst) {
		instance = inst;
	}

	public static VirtualMachine getInstance() {
		if (instance == null)
			instance = new VirtualMachine();

		return instance;
	}

	public VirtualMachine() {
		objectRepository = new ObjectRepository();

		postService = new PostService();
		workerManager = new WorkerManager();

		/*
		 * workerManager and postService only need each other at runtime.
		 */
		workerManager.init(objectRepository, postService);
		postService.init(objectRepository, workerManager);

		workerManagerThread = new Thread(workerManager, "WorkerManager");

		postServiceThread = new Thread(postService, "PostService");

		logger.info("VM initialized");
	}

	public void loadFromImage(String file) {
		if(running)
			throw new RuntimeException("cannot load while running");
		//pause();
		
		objectRepository.load(file);

		//resume();
		
		logger.info("VM loaded image file:" + file);
	}

	/**
	 * start internal service threads
	 */
	public void start() {

		workerManagerThread.start();
		postServiceThread.start();

		running = true;

		logger.info("VM started");
	}

	public void pause() {

		try {
			// logger.info("VM pause begin...");
			workerManager.stop();
			workerManagerThread.interrupt();

			workerManagerThread.join();

			postService.stop();
			postServiceThread.interrupt();

			postServiceThread.join();

			running = false;

			logger.info("VM paused");
		} catch (InterruptedException e) {

			throw new RuntimeException(e);
		}

	}

	public void resume() {

		workerManagerThread = new Thread(workerManager, "WorkerManager");

		postServiceThread = new Thread(postService, "PostService");

		workerManagerThread.start();
		postServiceThread.start();

		running = true;

		logger.info("VM resumed");
	}

	public ObjectRepository getObjectRepository() {
		return objectRepository;
	}

	public void setObjectRepository(ObjectRepository objectRepository) {
		this.objectRepository = objectRepository;
	}

	public WorkerManager getWorkerManager() {
		return workerManager;
	}

	public void setWorkerManager(WorkerManager workerManager) {
		this.workerManager = workerManager;
	}

	public PostService getPostService() {
		return postService;
	}

	public void setPostService(PostService postService) {
		this.postService = postService;
	}

}
