package com.bluesky.visualprogramming.ui.diagram;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.batik.dom.events.DOMMouseEvent;
import org.apache.batik.dom.svg.SVGOMGElement;
import org.apache.batik.dom.svg.SVGOMPoint;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.svg.SVGLocatable;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGStylable;
import org.w3c.dom.svg.SVGTransform;

import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.ui.SVGMainWindow;
import com.bluesky.visualprogramming.ui.avatar.SVGUtils;
import com.bluesky.visualprogramming.ui.avatar.SvgElementType;
import com.bluesky.visualprogramming.ui.avatar.SvgScene;
import com.bluesky.visualprogramming.ui.avatar.TransformIndex;
import com.bluesky.visualprogramming.ui.dialog.ObjectPropertyDialog;

public class SVGDiagramPanel extends JPanel {
	static Logger logger = Logger.getLogger(SVGDiagramPanel.class);
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

	private SVGMainWindow mainWindow;
	private JPopupMenu popupMenu;

	public SVGDiagramPanel(SVGMainWindow mainWindow, JPopupMenu popupMenu) {
		this.mainWindow = mainWindow;
		this.popupMenu = popupMenu;

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

	public void addMouseListener(org.w3c.dom.events.EventTarget target) {
		target.addEventListener("mousedown", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				currentElement = evt.getCurrentTarget();
				DOMMouseEvent elEvt = (DOMMouseEvent) evt;
				int nowToX = elEvt.getClientX();
				int nowToY = elEvt.getClientY();

				if (logger.isDebugEnabled())
					logger.debug(String.format("client xy: %d,%d", nowToX,
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

				SVGOMPoint borderPosition = SVGUtils.getStartPoint(objBorder);

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
				// System.out.println(objId);
				CSSStyleDeclaration style = ((SVGStylable) border).getStyle();
				style.setProperty("stroke", "red", "");

				SVGOMGElement object = (SVGOMGElement) scene.getElement(objId,
						SvgElementType.Object);
				SVGTransform transform = scene.getTransform(objId,
						TransformIndex.Offset);

				DOMMouseEvent elEvt = (DOMMouseEvent) evt;
				int nowToX = elEvt.getClientX();
				int nowToY = elEvt.getClientY();

				if (logger.isDebugEnabled())
					logger.debug(String.format("client xy: %d,%d", nowToX,
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
					if (logger.isDebugEnabled())
						logger.debug(String.format("drop xy: %f,%f",
								droppt.getX(), droppt.getY()));

					float oldX = transform.getMatrix().getE();
					float oldY = transform.getMatrix().getF();

					if (logger.isDebugEnabled())
						logger.debug(String.format("old xy: %f,%f", oldX, oldY));

					float tranlsateX = droppt.getX() + oldX - dragOffsetX;
					float tranlsateY = droppt.getY() + oldY - dragOffsetY;

					if (logger.isDebugEnabled())
						logger.debug(String.format("translate xy: %f,%f",
								tranlsateX, tranlsateY));

					transform.setTranslate(tranlsateX, tranlsateY);

					// update field.area
					Field field = (Field) ele.getUserData("field");

					field.getArea().x = (int) tranlsateX;
					field.getArea().y = (int) tranlsateY;

				}

			}
		}, false);

		target.addEventListener("click", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				DOMMouseEvent mouseEvent = (DOMMouseEvent) evt;

				// 2 is right button
				if (mouseEvent.getButton() == 2) {
					popupMenu.show(canvas, mouseEvent.getClientX(),
							mouseEvent.getClientY());
				} else {

					ObjectPropertyDialog dialog = new ObjectPropertyDialog();
					dialog.setLocationRelativeTo(getParent());
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

					Element ele = (Element) evt.getCurrentTarget();
					Field activeChildField = (Field) ele.getUserData("field");
					dialog.setField(activeChildField);

					dialog.setVisible(true);

					if (dialog.isUpdated()) {
						TreeNode selectedChildNode = mainWindow.findChildNode(
								mainWindow.getSelectedTreeNode(),
								activeChildField);
						mainWindow.treeModel.valueForPathChanged(new TreePath(
								selectedChildNode), activeChildField);
						// diagram.repaint();
					}

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

}
