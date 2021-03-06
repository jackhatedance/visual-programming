package com.bluesky.visualprogramming.ui.svg;

import java.awt.Color;

import org.apache.batik.dom.svg.SVGOMGElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGStylable;

import com.bluesky.visualprogramming.core.ObjectType;
/**
 * represent the SVG file of the whole diagram canvas.
 * 
 * @author jack
 *
 */
public class SvgScene {

	Document doc;
	Element svg;
	Element wrapper;
	
	Element defs;
	Element script;
	Element background;
	SVGOMGElement transformObjectElement;
	SvgTransformBox transformBox;

	public SvgScene() {
		doc = SVGUtils.createScene();
		svg = doc.getDocumentElement();

		if (!svg.getNodeName().equalsIgnoreCase("svg"))
			throw new RuntimeException("tag name error.");

		// Make the text look nice.
		svg.setAttributeNS(null, "text-rendering", "geometricPrecision");
		
		defs = doc.getElementById("defs");
		script = doc.getElementById("script");
		background = doc.getElementById("background");
		transformObjectElement = (SVGOMGElement) doc
				.getElementById("transform-object");

		wrapper = doc.getElementById("wrapper");		
		
		transformBox = new SvgTransformBox(doc, transformObjectElement);
		transformBox.setVisible(false);
	}

	public SVGOMGElement addObject(ObjectType type, long id, float x, float y,
			float scale) {

		Document objDoc = SVGUtils.createObjectDocument(type);

		SvgObject svgObject = new SvgObject(objDoc, id, true);

		SVGOMGElement newObjectElement = (SVGOMGElement) doc.importNode(
				svgObject.getObjectNode(), true);

		if (svgObject.getDefsNode() != null) {
			Node newDefstElement = doc
					.importNode(svgObject.getDefsNode(), true);

			wrapper.appendChild(newDefstElement);

		}

		String scaleStr = String.format("scale(%f,%f)", scale, scale);
		String translate = String.format("translate(%f,%f)", x, y);

		// String transform = translate + " " + scaleStr;
		String transform = scaleStr + " " + translate;
		newObjectElement.setAttribute("transform", transform);

		wrapper.appendChild(newObjectElement);

		return newObjectElement;

	}

	public SVGOMGElement addObject(String svgContent, long id, float x,
			float y, float scale) {

		Document objDoc = SVGUtils.createObjectDocument(svgContent);

		SvgObject svgObject = new SvgObject(objDoc, id, true);

		SVGOMGElement ele2 = (SVGOMGElement) doc.importNode(
				svgObject.getObjectNode(), true);

		String scaleStr = String.format("scale(%f,%f)", scale, scale);
		String translate = String.format("translate(%f,%f)", x, y);

		// String transform = translate + " " + scaleStr;
		String transform = scaleStr + " " + translate;
		ele2.setAttribute("transform", transform);

		wrapper.appendChild(ele2);

		return ele2;

	}

	public void clear() {
		Node script = null;
		Element background = null;
		while (svg.hasChildNodes()) {
			Node c = svg.getFirstChild();
			svg.removeChild(c);
		}

		//clear objects
		while (wrapper.hasChildNodes()) {
			Node c = wrapper.getFirstChild();
			wrapper.removeChild(c);
		}

		// add back
		if (defs != null)
			svg.appendChild(defs);

		if (script != null)
			svg.appendChild(script);
		
		if (wrapper != null)
			svg.appendChild(wrapper);

		if (transformObjectElement != null)
			svg.appendChild(transformObjectElement);
		
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

	

	public SvgObject getSvgObject(long id) {
		return new SvgObject(doc, id, false);
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
	
	public void setBorderWidth(long id, int width) {
		SVGStylable e = (SVGStylable) getElement(id, SvgElementType.Border);
		try {
			e.getStyle().setProperty("stroke-width", String.valueOf(width), "");			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("setborder width, id:" + id);
		}
	}
		
	public SvgTransformBox getTransformBox() {
		return transformBox;
	}
	
	public void setZoom(float rate){
		String scaleStr = String.format("scale(%f)", rate);
		wrapper.setAttribute("transform", scaleStr);
	}

}
