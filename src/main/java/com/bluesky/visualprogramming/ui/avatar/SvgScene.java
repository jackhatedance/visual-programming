package com.bluesky.visualprogramming.ui.avatar;

import org.apache.batik.dom.svg.SVGOMGElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGTransform;

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

	public SVGOMGElement addObject(ObjectType type, long id,float x, float y, float scale) {
		Document objDoc = SVGUtils.createObjectDocument(String.valueOf(id),
				type);

		SvgObject svgObject = new SvgObject(objDoc, id);

		SVGOMGElement ele2 = (SVGOMGElement) doc.importNode(
				svgObject.getObjectNode(), true);

		
		String scaleStr = String.format("scale(%f,%f)", scale, scale);
		String translate = String.format("translate(%f,%f)", x, y);
		

		// String transform = translate + " " + scaleStr;
		String transform = scaleStr + " " + translate;
		ele2.setAttribute("transform", transform);

		svg.appendChild(ele2);

		return ele2;

	}

	public void clear() {
		NodeList list = svg.getChildNodes();
		while (svg.hasChildNodes()) {
			Node c = svg.getFirstChild();
			svg.removeChild(c);
		}
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

	public void setName(long id, String name) {
		Element e = getElement(id, SvgElementType.Name);
		e.setTextContent(name);
	}

	public void setDescription(long id, String desc) {
		Element e = getElement(id, SvgElementType.Description);
		e.setTextContent(desc);
	}

	public SVGTransform getTransform(long id, TransformIndex index) {
		SVGOMGElement object = (SVGOMGElement) getElement(id,
				SvgElementType.Object);
		SVGTransform transform = object.getTransform().getBaseVal()
				.getItem(index.getIndex());
		return transform;
	}
}
