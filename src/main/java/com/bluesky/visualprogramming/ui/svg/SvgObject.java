package com.bluesky.visualprogramming.ui.svg;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import org.apache.batik.dom.svg.SVGOMGElement;
import org.apache.batik.dom.svg.SVGOMRectElement;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.DocumentEvent;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.MutationEvent;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGTransform;

public class SvgObject {
	static Logger logger = Logger.getLogger(SvgObject.class);

	Document doc;
	/**
	 * object id
	 */
	long id;

	private final String[] referenceAttributeNames = { "style", "xlink:ref" };
	/**
	 * old ID <-> new ID
	 */
	private Map<String, String> idMap = new HashMap<>();

	public SvgObject(Document doc, long id, boolean needInit) {
		this.doc = doc;
		this.id = id;

		if (needInit) {
			Element svg = doc.getDocumentElement();
			Element defs = doc.getElementById("defs");
			Element object = doc.getElementById("object");

			if (defs != null)
				updateTagIds(defs, idMap);

			updateTagIds(object, null);

			updateReferenceIds(object);
		}
	}

	private void updateTagIds(Node node, Map<String, String> idMap) {

		// update all tag that has ID
		Element e = (Element) node;
		String oldElementId = e.getAttribute("id");
		if (oldElementId != null && !oldElementId.isEmpty()) {
			String newId = String.format("%d-%s", id, oldElementId);
			e.setAttribute("id", newId);

			if (idMap != null)
				idMap.put(oldElementId, newId);
		}

		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentNode = nodeList.item(i);
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				// calls this method for all the children which is Element
				updateTagIds(currentNode, idMap);
			}
		}
	}

	private void updateReferenceIds(Node node) {
		// update all tag that has ID
		Element e = (Element) node;

		for (String attributeName : referenceAttributeNames) {

			if (e.hasAttribute(attributeName)) {
				String attrValue = e.getAttribute(attributeName);
				boolean changed = false;
				for (String oldId : idMap.keySet()) {
					String oldRef = "#" + oldId;
					if (attrValue.contains(oldRef)) {
						changed = true;

						String newId = idMap.get(oldId);
						attrValue = attrValue.replaceAll(oldRef, "#" + newId);
					}

				}

				if (changed) {
					e.setAttribute(attributeName, attrValue);
					// if (logger.isDebugEnabled())
					// logger.debug(String.format("update ref ID:%s=%s",
					// attributeName, attrValue));
				}
			}
		}

		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentNode = nodeList.item(i);
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				// calls this method for all the children which is Element
				updateReferenceIds(currentNode);

			}
		}
	}

	public SVGOMGElement getObjectNode() {
		return (SVGOMGElement) getElement(SvgElementType.Object);
	}

	public SVGOMRectElement getBorderNode() {
		return (SVGOMRectElement) getElement(SvgElementType.Border);
	}

	public SVGElement getElement(SvgElementType type) {
		return (SVGElement) doc.getElementById(String.valueOf(id) + "-"
				+ type.toString().toLowerCase());
	}

	public Element getDefsNode() {
		return doc.getElementById(String.valueOf(id) + "-" + "defs");
	}

	public Rectangle2D.Float getBorder() {

		SVGOMRectElement border = getBorderNode();

		float x = Float.valueOf(border.getAttribute("x"));
		float y = Float.valueOf(border.getAttribute("y"));

		float width = Float.valueOf(border.getAttribute("width"));
		float height = Float.valueOf(border.getAttribute("height"));

		// System.out.println(String.format("%f,%f,%f,%f", x,y,width,height));

		Rectangle2D.Float rect = new Rectangle2D.Float(x - 1, y - 1, width + 1,
				height + 1);

		return rect;
	}

	public void invokeScriptEvent(String eventName) {
		SVGElement textElement = getElement(SvgElementType.Event);
		textElement.setTextContent(eventName);

		DocumentEvent de = (DocumentEvent) doc;
		MutationEvent ev = (MutationEvent) de.createEvent("MutationEvents");
		ev.initMutationEvent("DOMCharacterDataModified", true, // canBubbleArg
				false, // cancelableArg
				null, // relatedNodeArg
				null, // prevValueArg
				null, // newValueArg
				null, // attrNameArg
				ev.ADDITION);
		EventTarget t = (EventTarget) textElement;
		t.dispatchEvent(ev);
	}

	public SVGTransform getTransform(TransformIndex index) {
		SVGOMGElement object = (SVGOMGElement) getElement(SvgElementType.Object);
		SVGTransform transform = object.getTransform().getBaseVal()
				.getItem(index.getIndex());
		return transform;
	}
}
