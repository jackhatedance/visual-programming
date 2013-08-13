package com.bluesky.visualprogramming.samplewebapp.blog;

import java.util.Map;

import com.bluesky.visualprogramming.samplewebapp.Processor;
import com.bluesky.visualprogramming.samplewebapp.Storage;

public class FillMaterPage implements Processor {

	Storage storage;
	public FillMaterPage( ) {
	
	}

	@Override
	public void process(Map context) {
		String post = (String) context.get("post");

		String masterPage = storage.getMasterPage();
		String resp = masterPage.replaceAll("#post#", post);

		context.put("resp", resp);
	}

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

}
