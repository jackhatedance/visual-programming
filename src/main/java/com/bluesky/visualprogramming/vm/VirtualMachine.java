package com.bluesky.visualprogramming.vm;

import org.antlr.v4.runtime.tree.gui.PostScriptDocument;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.PostService;
import com.bluesky.visualprogramming.core.WorkerManager;

public class VirtualMachine {
	private ObjectRepository objectRepository;
	private WorkerManager workerManager;
	private PostService postService;

	private static VirtualMachine instance;

	public static VirtualMachine getInstance() {
		return instance;
	}

	static {
		instance = new VirtualMachine();

	}

	public VirtualMachine() {
		objectRepository = new ObjectRepository();

		workerManager = new WorkerManager();
		postService = new PostService();

		workerManager.init(objectRepository, postService);
		postService.init(objectRepository, workerManager);

		Thread t = new Thread(workerManager);
		t.start();
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
