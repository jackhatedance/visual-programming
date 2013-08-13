package com.bluesky.visualprogramming.samplewebapp;

import java.util.HashMap;
import java.util.Map;

import com.bluesky.visualprogramming.samplewebapp.blog.BlogPipeline;
/**
 * this is a demo app that I would want to implement with the new programming platform.
 * @author jack
 *
 */
public class WebApp extends Pipeline {

	public WebApp() {

	}

	void assemble() {

		Dispatcher dispatcher = new Dispatcher();

		Map<String, Processor> actionMap = new HashMap<String, Processor>();
		BlogPipeline bp = new BlogPipeline();
		bp.assemble();
		actionMap.put("blog", bp);

		dispatcher.setActionMap(actionMap);

		dispatcher.assemble();

		processors.add(dispatcher);

	}

	public static void main(String[] args) {

		WebApp p = new WebApp();
		p.assemble();

		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("url", "blog?id=110");
		p.process(ctx);

		String resp = (String) ctx.get("resp");
		System.out.print(resp);
	}
}
