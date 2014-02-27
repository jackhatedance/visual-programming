package com.bluesky.visualprogramming.vm;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.timer.TimerService;
import com.bluesky.visualprogramming.vm.message.PostService;
import com.bluesky.visualprogramming.vm.message.Worker;
import com.bluesky.visualprogramming.vm.message.WorkerService;

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

	private WorkerService workerService;
	private PostService postService;
	private Thread postServiceThread;

	private boolean timerServiceEnabled = false;
	private TimerService timerService;

	private ServiceStatus status;

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
		workerService = new WorkerService();

		timerServiceEnabled = true;
		String timerServiceEnabledStr = appProperties
				.getProperty("service.timer.enabled");
		if (timerServiceEnabledStr != null)
			timerServiceEnabled = Boolean.valueOf(timerServiceEnabledStr);
		timerService = new TimerService(objectRepository);

		/*
		 * workerManager and postService only need each other at runtime.
		 */
		workerService.beforeInit(objectRepository, postService);
		workerService.init();
		
		postService.setup(objectRepository, workerService);

		postServiceThread = new Thread(postService, "PostService");

		timerService.init();

		status = ServiceStatus.Stopped;

		logger.info("VM initialized");
	}

	public void loadFromImage(String file) {
		if (status == ServiceStatus.Running || status == ServiceStatus.Paused)
			throw new RuntimeException("cannot load while running or suspended");
		// pause();

		objectRepository.loadXml(file);

		// resume();

		logger.info("VM loaded image file:" + file);
	}

	/**
	 * start internal service threads
	 */
	public void start() {

		workerService.start();
		postServiceThread.start();

		if (timerServiceEnabled)
			timerService.start();

		status = ServiceStatus.Running;
		logger.info("VM started");
	}

	public void pause() {

		try {
			// logger.info("VM pause begin...");
			workerService.pause();

			postService.stop();
			postServiceThread.interrupt();

			postServiceThread.join();

			if (timerServiceEnabled)
				timerService.pause();

			status = ServiceStatus.Paused;

			logger.info("VM paused");
		} catch (InterruptedException e) {

			throw new RuntimeException(e);
		}

	}

	public void resume() {

		postServiceThread = new Thread(postService, "PostService");

		workerService.resume();
		postServiceThread.start();

		if (timerServiceEnabled)
			timerService.resume();

		status = ServiceStatus.Running;

		logger.info("VM resumed");
	}

	public ObjectRepository getObjectRepository() {
		return objectRepository;
	}

	public void setObjectRepository(ObjectRepository objectRepository) {
		this.objectRepository = objectRepository;
	}

	public void setWorkerService(WorkerService workerService) {
		this.workerService = workerService;
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
