package com.bluesky.visualprogramming.ui.avatar;

import java.io.IOException;
import java.net.URL;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SVGUtils {

	public static Document createDocument(String resource) {
		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
		// eg. "svg/scene.svg"
		URL urlScene = SVGUtils.class.getClassLoader().getResource(resource);
		try {
			return f.createDocument(urlScene.toString());
		} catch (IOException e) {

			throw new RuntimeException(e);
		}
		
		

	}
	
	public static  void updateIds(Document doc, String prefix) {
		String[] IDs = { "object", "border", "name", "description" };
		for (String id : IDs) {
			Element e = doc.getElementById(id);
			if (e == null)
				System.out.println(id + " is null");
			else
				e.setAttribute("id", prefix + id);
		}
	}
}
