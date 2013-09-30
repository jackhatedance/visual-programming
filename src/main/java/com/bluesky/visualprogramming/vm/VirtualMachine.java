package com.bluesky.visualprogramming.vm;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.messageEngine.PostService;
import com.bluesky.visualprogramming.messageEngine.Worker;
import com.bluesky.visualprogramming.messageEngine.WorkerManager;
import com.bluesky.visualprogramming.timer.TimerService;

/**
 * one vm start with one image.
 * 
 * @author jack
 * 
 */
public class VirtualMachine implements Service {
	static Logger logger = Logger.getLogger(VirtualMachine.class);

	private AppProperties appProperties;

	private ObjectRepository objectRepository;

	private WorkerManager workerManager;
	private Thread workerManagerThread;

	private PostService postService;
	private Thread postServiceThread;

	private boolean timerServiceEnabled = false;
	private TimerService timerService;

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
		appProperties = AppProperties.getInstance();

		objectRepository = new ObjectRepository();

		postService = new PostService();
		workerManager = new WorkerManager();

		timerServiceEnabled = true;
		String timerServiceEnabledStr = appProperties
				.getProperty("service.timer.enabled");
		if (timerServiceEnabledStr != null)
			timerServiceEnabled = Boolean.valueOf(timerServiceEnabledStr);
		timerService = new TimerService(objectRepository);

		/*
		 * workerManager and postService only need each other at runtime.
		 */
		workerManager.init(objectRepository, postService);
		postService.init(objectRepository, workerManager);

		workerManagerThread = new Thread(workerManager, "WorkerManager");

		postServiceThread = new Thread(postService, "PostService");

		timerService.init();

		logger.info("VM initialized");
	}

	public void loadFromImage(String file) {
		if (running)
			throw new RuntimeException("cannot load while running");
		// pause();

		objectRepository.loadXml(file);

		// resume();

		logger.info("VM loaded image file:" + file);
	}

	/**
	 * start internal service threads
	 */
	public void start() {

		workerManagerThread.start();
		postServiceThread.start();

		if (timerServiceEnabled)
			timerService.start();

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

			if (timerServiceEnabled)
				timerService.pause();

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

		if (timerServiceEnabled)
			timerService.resume();

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

	public TimerService getTimerService() {
		return timerService;
	}

	public void setTimerService(TimerService timerService) {
		this.timerService = timerService;
	}

	@Override
	public void init() {

	}

	@Override
	public void stop() {

	}

	@Override
	public void destroy() {

	}

}
