package com.bluesky.visualprogramming.ui.avatar;

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
		//String[] Names = { "object", "border", "name", "description" };
		
		for (SvgElementType t: SvgElementType.values()) {
			String name = t.toString().toLowerCase();
			String oldId = name;
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
	}

	public Element getObjectNode() {
		return doc.getElementById(String.valueOf(id) + "-" + "object");
	}

	public Element getBorderNode() {
		return doc.getElementById(String.valueOf(id) + "-" + "border");
	}
}
