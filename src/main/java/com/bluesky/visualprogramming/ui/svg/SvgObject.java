package com.bluesky.visualprogramming.ui.svg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SvgObject {

	Document doc;
	long id;

	public SvgObject(Document doc, long id) {
		this.doc = doc;
		this.id = id;
		
		updateIds();
	}
	
	private  void updateIds() {
				
		for (SvgElementType t: SvgElementType.values()) {
			String name = t.toString().toLowerCase();
			String oldId = "0-"+name;
			Element e = doc.getElementById(oldId);
			if (e == null)
			{				
				System.out.println(String.format("'%s' is not exsit",name));
			}
			else
			{
				String newId = String.valueOf(id) + "-" + name;
				e.setAttribute("id", newId);
			}			
		}
		
		//custom nodes
		for(int i=0;i<5;i++){
			String name = String.format("custom%d",i);
			String oldId = "0-"+name;			
			Element e = doc.getElementById(oldId);
			if (e != null)			
			{
				String newId = String.valueOf(id) + "-" + name;
				e.setAttribute("id", newId);
			}
		}
	}

	public Element getObjectNode() {
		return doc.getElementById(String.valueOf(id) + "-" + "object");
	}

	public Element getBorderNode() {
		return doc.getElementById(String.valueOf(id) + "-" + "border");
	}
}
