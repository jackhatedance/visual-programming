package com.bluesky.visualprogramming.ui.svg;

import java.awt.Color;

import org.apache.batik.dom.svg.SVGOMGElement;
import org.apache.batik.dom.svg.SVGOMScriptElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGStylable;
import org.w3c.dom.svg.SVGTransform;

import com.bluesky.visualprogramming.core.ObjectType;

public class SvgScene {

	Document doc;
	Element svg;

	public SvgScene() {
		doc = SVGUtils.createScene();
		svg = doc.getDocumentElement();

		if (!svg.getNodeName().equalsIgnoreCase("svg"))
			throw new RuntimeException("tag name error.");

		// Make the text look nice.
		svg.setAttributeNS(null, "text-rendering", "geometricPrecision");
	}

	public SVGOMGElement addObject(ObjectType type, long id, float x, float y,
			float scale) {
		
		Document objDoc = SVGUtils.createObjectDocument(type);

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
	
	public SVGOMGElement addObject(String svgContent, long id, float x, float y,
			float scale) {
		
		Document objDoc = SVGUtils.createObjectDocument(svgContent);

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
		Node script = null;
		Element background = null;
		while (svg.hasChildNodes()) {
			Node c = svg.getFirstChild();
			Element e = (Element) c;

			// save it
			if (c instanceof SVGOMScriptElement)
				script = c;
			if (e.getAttribute("id").equals("background"))
				background = e;

			svg.removeChild(c);
		}

		// add back
		svg.appendChild(script);
		svg.appendChild(background);
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

	public void setBorderColor(long id, Color color) {
		SVGStylable e = (SVGStylable) getElement(id, SvgElementType.Border);
		String hex = String.format("#%02x%02x%02x", color.getRed(),
				color.getGreen(), color.getBlue());
		try {
			e.getStyle().setProperty("stroke", hex, "");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("setborder color, id:" + id);
		}
	}

}
