package com.bluesky.visualprogramming.ui.avatar;

import java.io.IOException;
import java.net.URL;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGOMGElement;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGTransform;

import com.bluesky.visualprogramming.core.ObjectType;

public class SVGUtils {

	public static String SCENE = "svg/scene.svg";
	
	public static Document createScene(){
		return createDocument(SCENE);
	}
	
	private static Document createDocument(String resource) {
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

	public static Document createObjectDocument(String id, ObjectType objectType) {
		Document doc = createDocument(objectType.getSvgResource());
		updateIds(doc, id);

		return doc;
	}

	public static Element getObjectElement(Document doc, String id) {
		return doc.getElementById(id + "-" + "object");
	}

	public static Element getBorderElement(Document doc, String id) {
		return doc.getElementById(id + "-" + "border");
	}

	public static Element addElement(Document doc, Element element, float x,
			float y) {
		SVGOMGElement ele2 = (SVGOMGElement) doc.importNode(element, true);

		String transform = String.format("scale(0.5,0.5) translate(%f,%f)", x,
				y);
		ele2.setAttribute("transform", transform);

		Element svg = doc.getDocumentElement();
		svg.appendChild(ele2);

		return ele2;
	}

	public static void updateIds(Document doc, String prefix) {
		String[] IDs = { "object", "border", "name", "description" };
		for (String id : IDs) {
			Element e = doc.getElementById(id);
			if (e == null)
				System.out.println(id + " is null");
			else
				e.setAttribute("id", prefix + "-" + id);
		}
	}
}
