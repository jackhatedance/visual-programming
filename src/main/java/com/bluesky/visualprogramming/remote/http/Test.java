package com.bluesky.visualprogramming.remote.http;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Test {
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);

		ServletContextHandler context = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);

		context.addServlet(new ServletHolder(new HelloServlet()), "/*");
		context.addServlet(new ServletHolder(new HelloServlet(
				"Buongiorno Mondo")), "/it/*");
		context.addServlet(new ServletHolder(new HelloServlet(
				"Bonjour le Monde")), "/fr/*");

		server.start();
		server.join();
	}
}
