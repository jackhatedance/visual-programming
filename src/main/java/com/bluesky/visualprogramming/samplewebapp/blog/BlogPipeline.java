package com.bluesky.visualprogramming.samplewebapp.blog;

import java.util.HashMap;
import java.util.Map;

import com.bluesky.visualprogramming.samplewebapp.ParseUrl;
import com.bluesky.visualprogramming.samplewebapp.Pipeline;
import com.bluesky.visualprogramming.samplewebapp.Storage;

public class BlogPipeline extends Pipeline {

	Storage storage;

	public BlogPipeline() {

	}

	public void assemble() {
		storage = new Storage();

		ParseUrl pu = new ParseUrl();
		processors.add(pu);

		PullPost pp = new PullPost();
		pp.setStorage(storage);

		processors.add(pp);

		FillMaterPage fmp = new FillMaterPage();
		fmp.setStorage(storage);

		processors.add(fmp);
	}

	public static void main(String[] args) {
		BlogPipeline p = new BlogPipeline();
		p.assemble();

		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("url", "blog?id=10");
		p.process(ctx);

		String resp = (String) ctx.get("resp");
		System.out.print(resp);
	}
}
