package com.bluesky.visualprogramming.vm.debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Debugger {
	boolean enabled=false;

	List<BreakPoint> breakPoints= new ArrayList<BreakPoint>();
	
	
	Map<String, Boolean> procedurePathMap=new HashMap<String, Boolean>(); 
	
	public Debugger(boolean enabled) {
		this.enabled=enabled;
		//add break points here. hard code
		//addBreakPoint(new BreakPoint("world.examples.webapp.page._system.html.component.toHtml",16));
		
		
		for(BreakPoint bp : breakPoints){
			procedurePathMap.put(bp.path,true);
		}
		
	}
	
	public void addBreakPoint(BreakPoint bp){
		breakPoints.add(bp);
	}
	
	public boolean isEnabled(){
		return enabled;
	}
	public boolean needBreak(String procedurePath, int line){
		
		
		if(!procedurePathMap.containsKey(procedurePath))
			return false;
		
		
		for(BreakPoint bp : breakPoints){
			if(bp.match(procedurePath, line))
			{
				return true;
			}
		}
		
		return false;
	}
}
