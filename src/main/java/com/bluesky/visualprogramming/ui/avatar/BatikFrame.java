package com.bluesky.visualprogramming.ui.avatar;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.batik.dom.events.DOMMouseEvent;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGOMGElement;
import org.apache.batik.dom.svg.SVGOMPoint;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.xalan.transformer.MsgMgr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.svg.SVGLocatable;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGPoint;
import org.w3c.dom.svg.SVGStylable;
import org.w3c.dom.svg.SVGTransform;

public class BatikFrame extends JFrame {
	protected JSVGCanvas canvas;

	protected Document doc;

	protected Element svg;

	
	protected volatile EventTarget currentElement;
	protected int dragX;
	protected int dragY;
	
	public BatikFrame() {
		init();
	}

	public void init() {
		// Create a new JSVGCanvas.
		canvas = new JSVGCanvas();
		// canvas.setBounds(0, 0, 1000, 1000);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add("Center", canvas);

		getContentPane().add(panel);

		try {
			// Parse the barChart.svg file into a Document.

			doc = SVGUtils.createDocument("svg/scene.svg");	
			Document objectDoc = SVGUtils.createDocument("svg/object.svg");
			Document booleanDoc = SVGUtils.createDocument("svg/boolean.svg");

			SVGUtils.updateIds(objectDoc, "1");
			SVGUtils.updateIds(booleanDoc, "2");

			svg = doc.getDocumentElement();

			// Change the document viewBox.
			// svg.setAttributeNS(null, "viewBox", "40 95 370 265");

			// Make the text look nice.
			 svg.setAttributeNS(null, "text-rendering", "geometricPrecision");

			// move object to scene
			Element object = objectDoc.getElementById("1object");
			Element border = objectDoc.getElementById("1border");

			String width = border.getAttribute("width");
			System.out.println("width:"+width);
			/*
			 * CSSStyleDeclaration css = ((SVGStylable) border).getStyle();
			 * System.out.println(css); ((SVGStylable)
			 * border).getStyle().setProperty("stroke", "red", "");
			 */
			Element box2 = (Element) doc.importNode(object, true);

			box2.setAttribute("transform", "scale(1.2,1.2) translate(0,580)");

			svg.appendChild(box2);

			object = booleanDoc.getElementById("2object");
			box2 = (Element) doc.importNode(object, true);

			box2.setAttribute("transform", " scale(0.5,0.5) translate(500,0)");
			svg.appendChild(box2);

			 
			
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
			}

			String prefix = "1";
			final String OBJECT=prefix+"object";
			final String BORDER = prefix + "border";
			final Element objBorder = doc.getElementById(OBJECT);
			//org.w3c.dom.events.EventTarget scene = (EventTarget) doc.getDocumentElement();
			
			org.w3c.dom.events.EventTarget t = (EventTarget) doc.getElementById(OBJECT);
			if (t != null) {
				t.addEventListener("mousedown", new EventListener() {
					
					@Override
					public void handleEvent(Event evt) {
						currentElement= evt.getCurrentTarget();
						DOMMouseEvent elEvt = (DOMMouseEvent) evt;
						int nowToX = elEvt.getClientX();
						int nowToY = elEvt.getClientY();
						System.out.println(String.format("client xy: %d,%d",
								nowToX, nowToY));
						
						dragX=nowToX;
						dragY=nowToY;	
						
					}
				}, false);
				
				t.addEventListener("mouseup", new EventListener() {
					
					@Override
					public void handleEvent(Event evt) {
						currentElement= null;				
						
					}
				}, false);
				
				t.addEventListener("mousemove", new EventListener() {

					@Override
					public void handleEvent(Event evt) {
						if(currentElement==null)
							return;
						
						//Element target = (Element) evt.getCurrentTarget();
						Element target = objBorder;
								
						Element border = target.getOwnerDocument()
								.getElementById(BORDER);

						((SVGStylable) border).getStyle().setProperty("stroke",
								"red", "");

						SVGOMGElement object = (SVGOMGElement) target
								.getOwnerDocument().getElementById(OBJECT);
						SVGTransform transform = object.getTransform()
								.getBaseVal().getItem(1);

						DOMMouseEvent elEvt = (DOMMouseEvent) evt;
						int nowToX = elEvt.getClientX();
						int nowToY = elEvt.getClientY();
						System.out.println(String.format("client xy: %d,%d",
								nowToX, nowToY));

						
						// convert it to a point for use with the Matrix
						SVGOMPoint pt = new SVGOMPoint(nowToX, nowToY);
						// Get the items screen coordinates, and apply the
						// transformation
						// elem -> screen
						SVGMatrix mat = ((SVGLocatable) objBorder)
								.getScreenCTM();

						mat = mat.inverse(); // screen -> elem
						SVGOMPoint droppt = (SVGOMPoint) pt
								.matrixTransform(mat);
						if (transform.getType() == SVGTransform.SVG_TRANSFORM_TRANSLATE) {

							
							System.out.println(String.format("drop xy: %f,%f",
									droppt.getX(), droppt.getY()));
							
							float oldX = transform.getMatrix().getE();
							float oldY = transform.getMatrix().getF();
							
							System.out.println(String.format("old xy: %f,%f",
									oldX,oldY));
							
							float tranlsateX = droppt.getX() + oldX-50;
							float tranlsateY = droppt.getY() + oldY-50;
							
							System.out.println(String.format("translate xy: %f,%f",
									tranlsateX, tranlsateY));
							
							transform.setTranslate(tranlsateX, tranlsateY);
							SVGTransform t = transform;
							 //t.setMatrix(t.getMatrix().translate(dragpt.getX(), dragpt.getY()));
							//transform.setTranslate(dragpt.getX(), dragpt.getY());
							//transform.setTranslate(nowToX,nowToY);
							//transform.setTranslate(50,10);
						}

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
		setVisible(true);
	}

	public void stop() {
		// Remove the document.
		canvas.setDocument(null);
	}

	public void destroy() {
		canvas.dispose();
	}

	public static void main(String[] args) {

		final BatikFrame f = new BatikFrame();

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				f.destroy();
				System.exit(0);
			}
		});

		f.setSize(800,800);

		f.start();

	}

	
}
