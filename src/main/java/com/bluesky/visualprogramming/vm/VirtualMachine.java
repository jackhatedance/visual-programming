package com.bluesky.visualprogramming.vm;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.timer.TimerService;
import com.bluesky.visualprogramming.utils.Config;
import com.bluesky.visualprogramming.vm.message.PostService;
import com.bluesky.visualprogramming.vm.message.WorkerService;

/**
 * one vm start with one image.
 * 
 * @author jack
 * 
 */
public class VirtualMachine implements Service {
	static Logger logger = Logger.getLogger(VirtualMachine.class);

	private Config appConfig;

	private ObjectRepository objectRepository;

	private WorkerService workerService;
	private PostService postService;

	private boolean timerServiceEnabled = false;
	private TimerService timerService;

	private ServiceStatus status;

	 
	private static VirtualMachine instance = null;

	// image files
	private String runtimeFile;
	private String userFile;

	public static void setInstance(VirtualMachine inst) {
		instance = inst;
	}

	public static VirtualMachine getInstance() {
		if (instance == null) {
			// only for unit test. for normal use, it should be created
			// intentionally.
			instance = new VirtualMachine();
		}

		return instance;
	}

	public VirtualMachine() {
		appConfig = AppProperties.getInstance().getAsConfig();

		objectRepository = new ObjectRepository();

		postService = new PostService();
		workerService = new WorkerService();

		timerServiceEnabled = appConfig.getBoolean(
				"service.timer.enabled", true);
		timerService = new TimerService(objectRepository);

		/*
		 * workerManager and postService only need each other at runtime.
		 */
		workerService.setup(objectRepository, postService);
		workerService.init();

		postService.setup(objectRepository, workerService);
		postService.init();

		timerService.init();

		status = ServiceStatus.Initialized;

		logger.info("VM initialized");

	}

	public void loadFromImage(String runtimeFile, String userFile) {

		// keep it. for later saving.
		this.runtimeFile = runtimeFile;
		this.userFile = userFile;

		if (status == ServiceStatus.Running || status == ServiceStatus.Paused)
			throw new RuntimeException("cannot load while running or suspended");
		// pause();

		objectRepository.load(runtimeFile, userFile);

		// resume();

		logger.info("VM loaded runtime image file:" + runtimeFile);
		logger.info("VM loaded user image file:" + userFile);
	}

	public void save() {
		boolean saveRuntimeXml = appConfig.getBoolean("autoSave.runtime.xml",
				false);

		String runtimeFile2 = null;
		if (saveRuntimeXml)
			runtimeFile2 = runtimeFile;

		getObjectRepository().save(runtimeFile2, userFile);
	}

	/**
	 * start internal service threads
	 */
	public void start() {

		workerService.start();
		postService.start();

		if (timerServiceEnabled)
			timerService.start();

		status = ServiceStatus.Running;
		logger.info("VM started");
	}

	public void pause() {


		logger.info("VM pause begin...");
		workerService.pause();

		postService.pause();

		if (timerServiceEnabled)
			timerService.pause();

		status = ServiceStatus.Paused;

		logger.info("VM paused");

	}

	public void resume() {

		workerService.resume();
		postService.resume();

		if (timerServiceEnabled)
			timerService.resume();

		status = ServiceStatus.Running;

		logger.info("VM resumed");
	}

	public ObjectRepository getObjectRepository() {
		return objectRepository;
	}

	public PostService getPostService() {
		return postService;
	}

	public TimerService getTimerService() {
		return timerService;
	}

	public void setTimerService(TimerService timerService) {
		this.timerService = timerService;
	}

	@Override
	public void init() {

		workerService.init();

		postService.init();

		timerService.init();

		status = ServiceStatus.Initialized;

		logger.info("VM initialized");
	}

	@Override
	public void stop() {
		if (timerServiceEnabled)
			timerService.stop();

		postService.stop();
		workerService.stop();

		status = ServiceStatus.Stopped;

		logger.info("VM stopped");
	}

	@Override
	public void destroy() {

	}

}
