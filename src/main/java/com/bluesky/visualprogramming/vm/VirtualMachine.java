package com.bluesky.visualprogramming.vm;

import java.util.Timer;
import java.util.TimerTask;

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

	Timer timer = new Timer("Image Saver");

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

		timerServiceEnabled = true;
		boolean timerServiceEnabled = appConfig.getBoolean(
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

		if (appConfig.containsKey(AppProperties.AUTO_SAVE_INTERVAL)) {
			int autoSaveInterval = appConfig.getInteger(
					AppProperties.AUTO_SAVE_INTERVAL, 1000 * 60 * 10);
			int minDelay = 1000 * 10;
			int delay = autoSaveInterval < minDelay ? minDelay
					: autoSaveInterval;

			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					VirtualMachine vm = VirtualMachine.getInstance();

					vm.pause();

					logger.debug("auto saving");
					vm.save();
					vm.resume();

				}
			}, delay, autoSaveInterval);
		}
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
		logger.info("VM loaded user image file:" + runtimeFile);
	}

	public void save() {
		getObjectRepository().save(runtimeFile, userFile);
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

		// logger.info("VM pause begin...");
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

	}

	@Override
	public void destroy() {

	}

}
