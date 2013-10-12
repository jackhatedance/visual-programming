package com.bluesky.visualprogramming.ui.avatar;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

public class BatikFrame extends JFrame {
	protected JSVGCanvas canvas;

	protected Document doc;

	protected Element svg;

	public BatikFrame() {
		init();
	}

	public void init() {
		// Create a new JSVGCanvas.
		canvas = new JSVGCanvas();
		getContentPane().add(canvas);

		try {
			// Parse the barChart.svg file into a Document.
			String parser = XMLResourceDescriptor.getXMLParserClassName();
			SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
			URL url = new URL(
					"http://svn.apache.org/repos/asf/xmlgraphics/batik/trunk/samples/barChart.svg");
			url = new URL("file:///D:/Users/jackding/svg/object.svg");
			doc = f.createDocument(url.toString());

			svg = doc.getDocumentElement();

			// Change the document viewBox.
			svg.setAttributeNS(null, "viewBox", "40 95 370 265");

			// Make the text look nice.
			svg.setAttributeNS(null, "text-rendering", "geometricPrecision");

			// Remove the xml-stylesheet PI.
			for (Node n = svg.getPreviousSibling(); n != null; n = n
					.getPreviousSibling()) {
				if (n.getNodeType() == Node.PROCESSING_INSTRUCTION_NODE) {
					doc.removeChild(n);
					break;
				}
			}

			// Remove the Batik sample mark 'use' element.
			for (Node n = svg.getLastChild(); n != null; n = n
					.getPreviousSibling()) {
				if (n.getNodeType() == Node.ELEMENT_NODE
						&& n.getLocalName().equals("use")) {
					svg.removeChild(n);
					// break;
				}
			}svg.q
			Element shoeBar = doc.getElementById("ShoeBar");
			org.w3c.dom.events.EventTarget t = (EventTarget) shoeBar;
			if (t != null) {
				t.addEventListener("click", new EventListener() {

					@Override
					public void handleEvent(Event evt) {
						Element target = (Element) evt.getCurrentTarget();
						System.out.println(target.getAttribute("id"));
						target.setAttribute("title", "aaa");
					}
				}, false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void start() {
		// Display the document.
		canvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
		canvas.setDocument(doc);
	}

	public void stop() {
		// Remove the document.
		canvas.setDocument(null);
	}

	public void destroy() {
		canvas.dispose();
	}

	public void updateBar(final String name, final float value) {
		canvas.getUpdateManager().getUpdateRunnableQueue()
				.invokeLater(new Runnable() {
					public void run() {
						Element bar = doc.getElementById(name);
						if (bar == null) {
							return;
						}

						Node n;
						Element path1, path2, path3;
						for (n = bar.getFirstChild(); n.getNodeType() != Node.ELEMENT_NODE; n = n
								.getNextSibling()) {
						}
						path1 = (Element) n;
						for (n = n.getNextSibling(); n.getNodeType() != Node.ELEMENT_NODE; n = n
								.getNextSibling()) {
						}
						path2 = (Element) n;
						for (n = n.getNextSibling(); n.getNodeType() != Node.ELEMENT_NODE; n = n
								.getNextSibling()) {
						}
						path3 = (Element) n;

						int offset;
						if (name.equals("ShoeBar")) {
							offset = 0;
						} else if (name.equals("CarBar")) {
							offset = 79;
						} else if (name.equals("TravelBar")) {
							offset = 158;
						} else {
							offset = 237;
						}

						String d = "M " + (offset + 86) + ",240 v -"
								+ (3.7 * value) + " l 15,-15 v "
								+ (3.7 * value) + " l -15,15 z";
						path1.setAttributeNS(null, "d", d);
						d = "M " + (offset + 86) + "," + (240 - 3.7 * value)
								+ " h -39 l 15,-15 h 39 l -15,15 z";
						path2.setAttributeNS(null, "d", d);
						d = "M " + (offset + 47) + "," + (240 - 3.7 * value)
								+ " v " + (3.7 * value) + " h 39 v -"
								+ (3.7 * value) + " h -39 z";
						path3.setAttributeNS(null, "d", d);
					}
				});
	}

	public static void main(String[] args) {

		BatikFrame f = new BatikFrame();
		f.setSize(800, 800);
		Container content = f.getContentPane();
		content.setBackground(Color.white);
		content.setLayout(new FlowLayout());

		f.start();

		// f.addWindowListener(new ExitListener());
		f.setVisible(true);

	}
}