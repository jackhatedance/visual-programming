package com.bluesky.visualprogramming.remote.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {
	private String greeting = "Hello World";

	public HelloServlet() {
	}

	public HelloServlet(String greeting) {
		this.greeting = greeting;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter writer = response.getWriter();
		writer.println("<h1>" + greeting + "</h1>");
		writer.println("session=" + request.getSession(true).getId());
		writer.println("<br>");
		writer.println("pathInfo=" + request.getPathInfo());
	}
}
