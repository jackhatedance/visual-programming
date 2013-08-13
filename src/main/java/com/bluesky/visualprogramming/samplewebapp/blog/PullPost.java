package com.bluesky.visualprogramming.samplewebapp.blog;

import java.util.Map;

import com.bluesky.visualprogramming.samplewebapp.Processor;
import com.bluesky.visualprogramming.samplewebapp.Storage;

public class PullPost implements Processor {

	Storage storage;
	
	public PullPost() {
	
	}

	@Override
	public void process(Map context) {
		Map paramMap = (Map)context.get("paramMap");
		String id = (String)paramMap.get("id");
		String post = storage.getPost(Integer.valueOf(id));

		context.put("post", post);
	}

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	
}
