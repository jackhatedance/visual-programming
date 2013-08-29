package com.bluesky.visualprogramming.vm;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.messageEngine.PostService;
import com.bluesky.visualprogramming.messageEngine.WorkerManager;

/**
 * one vm start with one image.
 * 
 * @author jack
 * 
 */
public class VirtualMachine {
	private ObjectRepository objectRepository;

	private WorkerManager workerManager;
	private Thread workerManagerThread;

	private PostService postService;
	private Thread postServiceThread;

	private boolean running = false;

	private static VirtualMachine instance = null;

	public static VirtualMachine getInstance() {
		if(instance==null)
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
	}
	
	public void loadFromImage(String file){
		objectRepository.load(file);
		
		postService.recreateAgents(objectRepository);
	}

	public void start() {

		workerManagerThread.start();
		postServiceThread.start();

		running = true;
	}

	public void pause() {

		try {
			workerManager.stop();

			workerManagerThread.join();

			postService.stop();

			postServiceThread.join();

			running = false;
		} catch (InterruptedException e) {

			throw new RuntimeException(e);
		}

	}
	
	public void resume() {

		workerManagerThread.start();
		postServiceThread.start();

		running = true;
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
