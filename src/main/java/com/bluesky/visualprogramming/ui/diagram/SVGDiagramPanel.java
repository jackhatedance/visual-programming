package com.bluesky.visualprogramming.ui.diagram;

import java.awt.BorderLayout;

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

import com.bluesky.visualprogramming.ui.avatar.SVGUtils;
import com.bluesky.visualprogramming.ui.avatar.SvgElementType;
import com.bluesky.visualprogramming.ui.avatar.SvgScene;
import com.bluesky.visualprogramming.ui.avatar.TransformIndex;

public class SVGDiagramPanel extends JPanel {

	/**
	 * the dragged target
	 */
	public EventTarget currentElement;

	// private SVGOMPoint cursorOffset;
	// offset of mouse and shape left-top point.
	public float dragOffsetX;
	public float dragOffsetY;

	private JSVGCanvas canvas;
	private SvgScene scene;

	public SVGDiagramPanel() {
		canvas = new JSVGCanvas();
		canvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);

		scene = new SvgScene();
		canvas.setDocument(scene.getDocument());
		setVisible(true);

		setLayout(new BorderLayout());
		
		add("Center", canvas);
	}

	public void addMouseListener(org.w3c.dom.events.EventTarget target) {
		target.addEventListener("mousedown", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				currentElement = evt.getCurrentTarget();
				DOMMouseEvent elEvt = (DOMMouseEvent) evt;
				int nowToX = elEvt.getClientX();
				int nowToY = elEvt.getClientY();
				System.out.println(String.format("client xy: %d,%d", nowToX,
						nowToY));

				Element ele = (Element) currentElement;

				SVGOMPoint screenPt = new SVGOMPoint(nowToX, nowToY);

				long objectId = SVGUtils.getObjectId(ele);

				Element objBorder = scene.getElement(objectId,
						SvgElementType.Border);

				// elem -> screen
				SVGMatrix mat = ((SVGLocatable) objBorder).getScreenCTM();

				mat = mat.inverse(); // screen -> elem
				SVGOMPoint svgPt = (SVGOMPoint) screenPt.matrixTransform(mat);

				SVGOMPoint borderPosition = SVGUtils.getXY(objBorder);

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
				Element border = scene.getElement(objId, SvgElementType.Border);

				((SVGStylable) border).getStyle().setProperty("stroke", "red",
						"");

				SVGOMGElement object = (SVGOMGElement) scene.getElement(objId,
						SvgElementType.Object);
				SVGTransform transform = scene.getTransform(objId,
						TransformIndex.Offset);

				DOMMouseEvent elEvt = (DOMMouseEvent) evt;
				int nowToX = elEvt.getClientX();
				int nowToY = elEvt.getClientY();
				System.out.println(String.format("client xy: %d,%d", nowToX,
						nowToY));

				// convert it to a point for use with the Matrix
				SVGOMPoint pt = new SVGOMPoint(nowToX, nowToY);
				// Get the items screen coordinates, and apply the
				// transformation
				// elem -> screen
				SVGMatrix mat = ((SVGLocatable) border).getScreenCTM();

				mat = mat.inverse(); // screen -> elem
				SVGOMPoint droppt = (SVGOMPoint) pt.matrixTransform(mat);
				if (transform.getType() == SVGTransform.SVG_TRANSFORM_TRANSLATE) {

					System.out.println(String.format("drop xy: %f,%f",
							droppt.getX(), droppt.getY()));

					float oldX = transform.getMatrix().getE();
					float oldY = transform.getMatrix().getF();

					System.out.println(String.format("old xy: %f,%f", oldX,
							oldY));

					float tranlsateX = droppt.getX() + oldX - dragOffsetX;
					float tranlsateY = droppt.getY() + oldY - dragOffsetY;

					System.out.println(String.format("translate xy: %f,%f",
							tranlsateX, tranlsateY));

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

	public JSVGCanvas getCanvas() {
		return canvas;
	}

	public void setCanvas(JSVGCanvas canvas) {
		this.canvas = canvas;
	}

	public SvgScene getScene() {
		return scene;
	}

	public void setScene(SvgScene scene) {
		this.scene = scene;
	}
	
	
}
