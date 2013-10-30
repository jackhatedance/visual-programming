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
		String[] Names = { "object", "border", "name", "description" };
		for (String name : Names) {
			String oldId = name;
			Element e = doc.getElementById(oldId);
			if (e == null)
				System.out.println(id + " is null");
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
