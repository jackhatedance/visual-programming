package com.bluesky.visualprogramming.ui.svg;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.bluesky.visualprogramming.ui.SVGMainWindow;

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

	public SvgObject(Document doc, long id) {
		this.doc = doc;
		this.id = id;

		Element svg = doc.getDocumentElement();
		Element defs = doc.getElementById("defs");
		Element object = doc.getElementById("object");

		if (defs != null)
			updateTagIds(defs, idMap);

		updateTagIds(object, null);

		updateReferenceIds(object);

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
					if (logger.isDebugEnabled())
						logger.debug(String.format("update ref ID:%s=%s",
								attributeName, attrValue));
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

	public Element getObjectNode() {
		return doc.getElementById(String.valueOf(id) + "-" + "object");
	}

	public Element getBorderNode() {
		return doc.getElementById(String.valueOf(id) + "-" + "border");
	}

	public Element getDefsNode() {
		return doc.getElementById(String.valueOf(id) + "-" + "defs");
	}
}
