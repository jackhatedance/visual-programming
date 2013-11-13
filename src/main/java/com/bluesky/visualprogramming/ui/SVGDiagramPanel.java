package com.bluesky.visualprogramming.ui;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.apache.batik.dom.events.DOMMouseEvent;
import org.apache.batik.dom.svg.SVGOMGElement;
import org.apache.batik.dom.svg.SVGOMPoint;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.events.DocumentEvent;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.MutationEvent;
import org.w3c.dom.svg.SVGLocatable;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGPoint;
import org.w3c.dom.svg.SVGStylable;
import org.w3c.dom.svg.SVGTransform;

import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.ui.svg.SVGUtils;
import com.bluesky.visualprogramming.ui.svg.SvgElementType;
import com.bluesky.visualprogramming.ui.svg.SvgObject;
import com.bluesky.visualprogramming.ui.svg.SvgScene;
import com.bluesky.visualprogramming.ui.svg.TransformIndex;

public class SVGDiagramPanel extends JPanel {
	static Logger logger = Logger.getLogger(SVGDiagramPanel.class);
	/**
	 * the object node that has been clicked, a transform box is arrounded.
	 */
	public Element selectedTarget;
	/**
	 * offset of mouse and shape left-top point.
	 */
	public Point2D.Float dragOffset;
	/**
	 * is in the dragging mode.
	 */
	private boolean dragging = false;

	/**
	 * element for popup menu, right clicked will select it.
	 */
	private Element popupTarget;

	private JSVGCanvas canvas;
	private SvgScene scene;

	private SVGMainWindow mainWindow;
	private JPopupMenu objectPopupMenu;
	private JPopupMenu backgroundPopupMenu;
	private Point popupMenuPosition;

	public SVGDiagramPanel(SVGMainWindow mainWindow,
			JPopupMenu objectPopupMenu, JPopupMenu backgroundPopupMenu) {
		this.mainWindow = mainWindow;
		this.objectPopupMenu = objectPopupMenu;
		this.backgroundPopupMenu = backgroundPopupMenu;

		canvas = new JSVGCanvas();
		canvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);

		setLayout(new BorderLayout());

		add("Center", canvas);
	}

	/**
	 * set scene(document) so that onload event will happen. resue a document
	 * won't trigger onload event.
	 * 
	 * @param scene
	 */
	public void setScene(SvgScene scene) {
		this.scene = scene;
		canvas.setDocument(scene.getDocument());
		setVisible(true);

	}

	public void addMouseMoveListener(org.w3c.dom.events.EventTarget target) {
		target.addEventListener("mousemove", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				if (!dragging)
					return;

				Element ele = (Element) selectedTarget;
				long objId = SVGUtils.getObjectId(ele);
				Element border = scene.getElement(objId, SvgElementType.Border);
				// System.out.println(objId);
				CSSStyleDeclaration style = ((SVGStylable) border).getStyle();
				style.setProperty("stroke-dasharray",
						"27.94599915,27.94599915", "");

				SVGOMGElement object = (SVGOMGElement) scene.getElement(objId,
						SvgElementType.Object);
				SVGTransform transform = scene.getTransform(objId,
						TransformIndex.Offset);

				DOMMouseEvent elEvt = (DOMMouseEvent) evt;
				int nowToX = elEvt.getClientX();
				int nowToY = elEvt.getClientY();

				// if (logger.isDebugEnabled())
				// logger.debug(String.format("client xy: %d,%d", nowToX,
				// nowToY));

				// convert it to a point for use with the Matrix
				SVGOMPoint pt = new SVGOMPoint(nowToX, nowToY);
				// Get the items screen coordinates, and apply the
				// transformation
				// elem -> screen
				SVGMatrix mat = ((SVGLocatable) border).getScreenCTM();

				mat = mat.inverse(); // screen -> elem
				SVGOMPoint droppt = (SVGOMPoint) pt.matrixTransform(mat);
				if (transform.getType() == SVGTransform.SVG_TRANSFORM_TRANSLATE) {
					// if (logger.isDebugEnabled())
					// logger.debug(String.format("drop xy: %f,%f",
					// droppt.getX(), droppt.getY()));

					float oldX = transform.getMatrix().getE();
					float oldY = transform.getMatrix().getF();

					// if (logger.isDebugEnabled())
					// logger.debug(String.format("old xy: %f,%f", oldX, oldY));

					float tranlsateX = (float) (droppt.getX() + oldX - dragOffset
							.getX());
					float tranlsateY = (float) (droppt.getY() + oldY - dragOffset
							.getY());

					// if (logger.isDebugEnabled())
					// logger.debug(String.format("translate xy: %f,%f",
					// tranlsateX, tranlsateY));

					transform.setTranslate(tranlsateX, tranlsateY);

					// update field.area
					Field field = (Field) ele.getUserData("field");

					field.setStartPosition(tranlsateX, tranlsateY);

					// update focus
					SvgObject svgObj = scene.getSvgObject(field.getTarget()
							.getId());

					String transformStr = svgObj.getObjectNode().getAttribute(
							"transform");

					scene.getTransformBox().setRectangle(transformStr,
							svgObj.getBorder());

				}

			}
		}, false);
	}

	public void addMouseListener(org.w3c.dom.events.EventTarget target) {
		target.addEventListener("mousedown", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				if (selectedTarget == null)
					return;

				// can only move the selected target
				if (selectedTarget != evt.getCurrentTarget())
					return;

				// store the cursor offset.

				DOMMouseEvent elEvt = (DOMMouseEvent) evt;
				int nowToX = elEvt.getClientX();
				int nowToY = elEvt.getClientY();

				// if (logger.isDebugEnabled())
				// logger.debug(String.format("client xy: %d,%d", nowToX,
				// nowToY));

				Element ele = (Element) selectedTarget;

				SVGOMPoint screenPt = new SVGOMPoint(nowToX, nowToY);

				long objectId = SVGUtils.getObjectId(ele);

				Element objBorder = scene.getElement(objectId,
						SvgElementType.Border);

				// elem -> screen
				SVGMatrix mat = ((SVGLocatable) objBorder).getScreenCTM();

				mat = mat.inverse(); // screen -> elem
				SVGOMPoint svgPt = (SVGOMPoint) screenPt.matrixTransform(mat);

				SVGOMPoint startPosition = SVGUtils.getStartPoint(objBorder);

				float offsetX = svgPt.getX() - startPosition.getX();
				float offsetY = svgPt.getY() - startPosition.getY();

				int minOffset = 80;
				offsetX = offsetX < minOffset ? minOffset : offsetX;
				offsetY = offsetY < minOffset ? minOffset : offsetY;

				dragOffset = new Point2D.Float(svgPt.getX()
						- startPosition.getX(), svgPt.getY()
						- startPosition.getY());

				dragging = true;
			}
		}, false);

		addMouseMoveListener(target);

		target.addEventListener("mouseup", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				if (dragging  ) {
					Element ele =   selectedTarget;
					long objId = SVGUtils.getObjectId(ele);
					Element border = scene.getElement(objId,
							SvgElementType.Border);
					// System.out.println(objId);
					CSSStyleDeclaration style = ((SVGStylable) border)
							.getStyle();
					style.removeProperty("stroke-dasharray");

					
					dragging = false;
				}
				
			}
		}, false);

		target.addEventListener("click", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				DOMMouseEvent mouseEvent = (DOMMouseEvent) evt;

				if (mouseEvent.getButton() == 2) {
					popupTarget = (Element) evt.getCurrentTarget();
					objectPopupMenu.show(canvas, mouseEvent.getClientX(),
							mouseEvent.getClientY());
				} else if (mouseEvent.getButton() == 0) {
					// selected current object
					selectedTarget = (Element) evt.getCurrentTarget();
					// show transform box

					/**
					 * idea: selected object left-top point -> screen position
					 * -> transform box element position.
					 */
					Field f = (Field) selectedTarget.getUserData("field");
					if (f.getTarget() != null) {
						SvgObject svgObj = scene.getSvgObject(f.getTarget()
								.getId());

						String transformStr = svgObj.getObjectNode()
								.getAttribute("transform");

						scene.getTransformBox().setRectangle(transformStr,
								svgObj.getBorder());

						scene.getTransformBox().setVisible(true);
						svgObj.invokeScriptEvent("valueChanged");

					}
				}

			}
		}, false);
	}

	public void addBackgroundPopupMenuListener(
			org.w3c.dom.events.EventTarget target) {

		target.addEventListener("click", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				DOMMouseEvent mouseEvent = (DOMMouseEvent) evt;

				if (mouseEvent.getButton() == 2) {
					popupMenuPosition = new Point(mouseEvent.getClientX(),
							mouseEvent.getClientY());
					backgroundPopupMenu.show(canvas, mouseEvent.getClientX(),
							mouseEvent.getClientY());

				}

				// hide transform box;
				scene.getTransformBox().setVisible(false);
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

	public Element getpopupTarget() {
		return popupTarget;
	}

	public Field getpopupTargetField() {
		return (Field) popupTarget.getUserData("field");
	}

	public void reload() {
		mainWindow.reloadDiagram();
	}

	public Point getPopupMenuPosition() {
		return popupMenuPosition;
	}

}
