package com.bluesky.visualprogramming.ui.avatar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.batik.dom.events.DOMMouseEvent;
import org.apache.batik.dom.svg.SVGOMGElement;
import org.apache.batik.dom.svg.SVGOMPoint;
import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.svg.SVGLocatable;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGStylable;
import org.w3c.dom.svg.SVGTransform;

import com.bluesky.visualprogramming.core.ObjectType;

public class BatikFrame extends JFrame {
	protected JSVGCanvas canvas;
	protected SvgScene scene;

	/**
	 * the dragged target
	 */
	protected EventTarget currentElement;

	// offset of mouse and shape left-top point.
	protected float dragOffsetX;
	protected float dragOffsetY;

	public BatikFrame() {
		init();
	}

	public void init() {
		// Create a new JSVGCanvas.
		canvas = new JSVGCanvas();
		// canvas.setBounds(0, 0, 1000, 1000);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add("Center", canvas);

		JButton btn = new JButton("btn");
		panel.add("North", btn);
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				scene.clear();

			}
		});

		getContentPane().add(panel);

		try {
			scene = new SvgScene();

			scene.addObject(ObjectType.NORMAL, 0, 0, 580, 0.1f);
			scene.addObject(ObjectType.BOOLEAN, 1, 500, 0, 0.2f);
			scene.addObject(ObjectType.BOOLEAN, 2, 200, 580, 0.3f);
			// Change the document viewBox.
			// svg.setAttributeNS(null, "viewBox", "40 95 370 265");

			scene.setName(0, "foo");
			scene.setDescription(0, "some words");
			scene.setBorderColor(0, Color.yellow);

			scene.setName(1, "isOK");
			scene.setDescription(1, "true");

			for (long objId = 0; objId < 3; objId++) {
				org.w3c.dom.events.EventTarget t = (EventTarget) scene
						.getElement(objId, SvgElementType.Object);
				if (t != null) {
					addMouseListener(t);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void addMouseListener(org.w3c.dom.events.EventTarget target){
		target.addEventListener("mousedown", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				currentElement = evt.getCurrentTarget();
				DOMMouseEvent elEvt = (DOMMouseEvent) evt;
				int nowToX = elEvt.getClientX();
				int nowToY = elEvt.getClientY();
				System.out.println(String.format(
						"client xy: %d,%d", nowToX, nowToY));

				Element ele = (Element) currentElement;

				SVGOMPoint screenPt = new SVGOMPoint(nowToX, nowToY);

				long objectId = SVGUtils.getObjectId(ele);

				Element objBorder = scene.getElement(objectId,
						SvgElementType.Border);

				// elem -> screen
				SVGMatrix mat = ((SVGLocatable) objBorder)
						.getScreenCTM();

				mat = mat.inverse(); // screen -> elem
				SVGOMPoint svgPt = (SVGOMPoint) screenPt
						.matrixTransform(mat);

				SVGOMPoint borderPosition = SVGUtils
						.getXY(objBorder);

				dragOffsetX = svgPt.getX() - borderPosition.getX();
				dragOffsetY = svgPt.getY() - borderPosition.getY();

			}
		}, false);

		target.addEventListener("mouseup", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				currentElement = null;

			}
		}, false);

		target.addEventListener("mousemove", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				if (currentElement == null)
					return;

				Element ele = (Element) currentElement;
				long objId = SVGUtils.getObjectId(ele);
				Element border = scene.getElement(objId,
						SvgElementType.Border);

				((SVGStylable) border).getStyle().setProperty(
						"stroke", "red", "");

				SVGOMGElement object = (SVGOMGElement) scene
						.getElement(objId, SvgElementType.Object);
				SVGTransform transform = scene.getTransform(objId,
						TransformIndex.Offset);

				DOMMouseEvent elEvt = (DOMMouseEvent) evt;
				int nowToX = elEvt.getClientX();
				int nowToY = elEvt.getClientY();
				System.out.println(String.format(
						"client xy: %d,%d", nowToX, nowToY));

				// convert it to a point for use with the Matrix
				SVGOMPoint pt = new SVGOMPoint(nowToX, nowToY);
				// Get the items screen coordinates, and apply the
				// transformation
				// elem -> screen
				SVGMatrix mat = ((SVGLocatable) border)
						.getScreenCTM();

				mat = mat.inverse(); // screen -> elem
				SVGOMPoint droppt = (SVGOMPoint) pt
						.matrixTransform(mat);
				if (transform.getType() == SVGTransform.SVG_TRANSFORM_TRANSLATE) {

					System.out.println(String.format(
							"drop xy: %f,%f", droppt.getX(),
							droppt.getY()));

					float oldX = transform.getMatrix().getE();
					float oldY = transform.getMatrix().getF();

					System.out.println(String.format(
							"old xy: %f,%f", oldX, oldY));

					float tranlsateX = droppt.getX() + oldX
							- dragOffsetX;
					float tranlsateY = droppt.getY() + oldY
							- dragOffsetY;

					System.out.println(String.format(
							"translate xy: %f,%f", tranlsateX,
							tranlsateY));

					transform.setTranslate(tranlsateX, tranlsateY);
					SVGTransform t = transform;
					// t.setMatrix(t.getMatrix().translate(dragpt.getX(),
					// dragpt.getY()));
					// transform.setTranslate(dragpt.getX(),
					// dragpt.getY());
					// transform.setTranslate(nowToX,nowToY);
					// transform.setTranslate(50,10);
				}

			}
		}, false);
	}
	
	public void start() {
		// Display the document.
		canvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
		canvas.setDocument(scene.getDocument());
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

		f.setSize(800, 800);

		f.start();

	}

}
