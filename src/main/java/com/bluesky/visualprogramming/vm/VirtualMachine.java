package com.bluesky.visualprogramming.vm;

import org.antlr.v4.runtime.tree.gui.PostScriptDocument;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.messageEngine.PostService;
import com.bluesky.visualprogramming.messageEngine.WorkerManager;

public class VirtualMachine {
	private ObjectRepository objectRepository;
	private WorkerManager workerManager;
	private Thread workerManagerThread;

	private PostService postService;
	private Thread postServiceThread;

	private static VirtualMachine instance;

	public static VirtualMachine getInstance() {
		return instance;
	}

	static {
		instance = new VirtualMachine();

	}

	public VirtualMachine() {

	}

	public void loadImage(String file){
		objectRepository.load(file);
	}
	
	public void start() {
		objectRepository = new ObjectRepository();

		workerManager = new WorkerManager();
		postService = new PostService();

		workerManager.init(objectRepository, postService);
		postService.init(objectRepository, workerManager);

		workerManagerThread = new Thread(workerManager, "WorkerManager");
		workerManagerThread.start();

		postServiceThread = new Thread(postService, "PostService");
		postServiceThread.start();
	}

	public void stop() {

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
