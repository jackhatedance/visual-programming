package com.bluesky.visualprogramming.ui.avatar;

import org.apache.batik.dom.svg.SVGOMGElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.bluesky.visualprogramming.core.ObjectType;

public class SvgScene {

	Document doc;
	Element svg;

	public SvgScene() {
		doc = SVGUtils.createScene();
		svg = doc.getDocumentElement();
		// Make the text look nice.
		svg.setAttributeNS(null, "text-rendering", "geometricPrecision");
	}

	public SVGOMGElement addObject(ObjectType type, long id, float x, float y) {
		Document objDoc = SVGUtils.createObjectDocument(String.valueOf(id),
				type);

		SvgObject svgObject = new SvgObject(objDoc, id);

		SVGOMGElement ele2 = (SVGOMGElement) doc.importNode(
				svgObject.getObjectNode(), true);

		String transform = String.format("scale(0.5,0.5) translate(%f,%f)", x,
				y);
		ele2.setAttribute("transform", transform);

		svg.appendChild(ele2);

		return ele2;

	}

	public Element getElement(long id, SvgElementType type) {
		return doc.getElementById(id + "-" + type.toString().toLowerCase());
	}

	public Element getSVGElement() {
		return svg;
	}

	public Document getDocument() {
		return this.doc;
	}
}
