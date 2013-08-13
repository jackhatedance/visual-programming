package com.bluesky.visualprogramming.samplewebapp;

import java.util.HashMap;
import java.util.Map;


public class Dispatcher extends Pipeline{
	
	private Map<String,Processor> actionMap;
	public Dispatcher() {
	
	}
	@Override
	public void process(Map context) {

		super.process(context);
		
		String action = (String)context.get("action");
		Processor p = actionMap.get(action);
		
		p.process(context);

	}
	
	void assemble() {

		ParseUrl pu = new ParseUrl();
		processors.add(pu);
	}

	public Map<String, Processor> getActionMap() {
		return actionMap;
	}
	public void setActionMap(Map<String, Processor> actionMap) {
		this.actionMap = actionMap;
	}
	public static void main(String[] args) {
		Dispatcher p = new Dispatcher();
		p.assemble();

		 
		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("url", "blog?id=10");
		p.process(ctx);

		String resp = (String) ctx.get("resp");
		System.out.print(resp);
	}
}
