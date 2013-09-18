package com.bluesky.visualprogramming.remote.http;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;

public class Test {
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);

		ContextHandler context = new ContextHandler("/");
		context.setHandler(new HelloHandler("Hello"));

		ContextHandler contextFR = new ContextHandler("/fr");
		contextFR.setHandler(new HelloHandler("Bonjoir"));

		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { contextFR, context });
		server.setHandler(handlers);

		server.start();
		server.join();
	}
}
