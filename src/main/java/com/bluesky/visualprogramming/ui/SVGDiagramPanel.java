package com.bluesky.visualprogramming.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import org.apache.batik.dom.events.DOMMouseEvent;
import org.apache.batik.dom.svg.SVGOMPoint;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.svg.SVGLocatable;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGPoint;
import org.w3c.dom.svg.SVGStylable;
import org.w3c.dom.svg.SVGTransform;

import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.Procedure;
import com.bluesky.visualprogramming.ui.svg.SVGUtils;
import com.bluesky.visualprogramming.ui.svg.SvgElementType;
import com.bluesky.visualprogramming.ui.svg.SvgObject;
import com.bluesky.visualprogramming.ui.svg.SvgScene;
import com.bluesky.visualprogramming.ui.svg.TransformIndex;
import com.bluesky.visualprogramming.vm.VirtualMachine;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
	 * move or scale. it is determined after mouse-down. if the cursor is on
	 * target then it is move, else if the cursor is on right-bottom arrow, then
	 * it is scale.
	 */
	private TransformType currentTransformType;

	/**
	 * element for popup menu, right clicked will select it.
	 */
	private Element popupTarget;

	private JSVGCanvas canvas;
	private SvgScene scene;
	private JTextArea textAreaScript;
	private JButton btnExecute;

	private SVGMainWindow mainWindow;
	private JPopupMenu objectPopupMenu;
	private JPopupMenu backgroundPopupMenu;
	private Point popupMenuPosition;

	public SVGDiagramPanel(SVGMainWindow mainWindow,
			JPopupMenu objectPopupMenu, JPopupMenu backgroundPopupMenu) {
		this.mainWindow = mainWindow;
		this.objectPopupMenu = objectPopupMenu;
		this.backgroundPopupMenu = backgroundPopupMenu;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(1.0);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

		add(splitPane);

		canvas = new JSVGCanvas();
		splitPane.setTopComponent(canvas);
		canvas.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
				null));
		canvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);

		JPanel BottomPanel = new JPanel();
		splitPane.setBottomComponent(BottomPanel);
		BottomPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		BottomPanel.setLayout(new BoxLayout(BottomPanel, BoxLayout.X_AXIS));
		BottomPanel.setPreferredSize(new Dimension(20, 50));

		textAreaScript = new JTextArea();
		textAreaScript.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);

				// if CTRL+Enter then execute.
				if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
					// System.out.println("execute");
					btnExecute.doClick();
				}

			}

		});
		BottomPanel.add(textAreaScript);
		textAreaScript.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		BottomPanel.add(separator);

		btnExecute = new JButton("Execute");
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// System.out.println("click send");
				// add a temporary procedure to the object, execute it. delete
				// it.
				/*
				 * target._test = procedure(){a=1;}; target._test();
				 * target._test=null; *
				 */

				String testProcedureName = "_test";

				Field field = SVGDiagramPanel.this.mainWindow
						.getSelectedTreeField();
				VirtualMachine vm = VirtualMachine.getInstance();
				ObjectRepository repo = vm.getObjectRepository();
				Procedure procedure = (Procedure) repo.createObject(
						field.target, testProcedureName, ObjectType.PROCEDURE);

				StringBuilder sb = new StringBuilder();
				sb.append("[native=false, language=goo]\r\n");
				sb.append("procedure(){\r\n");
				sb.append(SVGDiagramPanel.this.textAreaScript.getText());
				sb.append("\r\n}");

				procedure.setValue(sb.toString());

				// execute it
				vm.getPostService().sendMessageFromNobody(field.target,
						testProcedureName, null,null);

			}
		});
		BottomPanel.add(btnExecute);
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
				DOMMouseEvent elEvt = (DOMMouseEvent) evt;
				int mouseScreenX = elEvt.getClientX();
				int mouseScreenY = elEvt.getClientY();

				if (currentTransformType == TransformType.Move) {
					Element ele = (Element) selectedTarget;
					long objId = SVGUtils.getObjectId(ele);
					Element border = scene.getElement(objId,
							SvgElementType.Border);

					SvgObject svgObj = scene.getSvgObject(objId);

					SVGTransform transform = svgObj
							.getTransform(TransformIndex.Offset);

					// if (logger.isDebugEnabled())
					// logger.debug(String.format("client xy: %d,%d", nowToX,
					// nowToY));

					// convert it to a point for use with the Matrix
					SVGOMPoint pt = new SVGOMPoint(mouseScreenX, mouseScreenY);
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
						// logger.debug(String.format("old xy: %f,%f", oldX,
						// oldY));

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

						// update transform box
						svgObj = scene.getSvgObject(field.getTarget().getId());

						scene.getTransformBox().setScreenRect(
								svgObj.getBorderScreenPosition());

						scene.getTransformBox().setVisible(true);
						svgObj.invokeScriptEvent("valueChanged");

					}
				} else if (currentTransformType == TransformType.Scale) {
					Element ele = (Element) selectedTarget;
					long objId = SVGUtils.getObjectId(ele);
					Element border = scene.getElement(objId,
							SvgElementType.Border);

					SvgObject svgObj = scene.getSvgObject(objId);
					/*
					 * 1. get the screen point, calculate the scale value to
					 * grow the border.
					 * 
					 * 2. set it as new right bottom point. *
					 */
					/*
					 * get the coordinate point of the mouse
					 */
					Rectangle2D.Float screenRect = scene.getTransformBox()
							.getScreenRect();
					Point2D rightBottomScreenPoint = new Point2D.Float(
							screenRect.x + screenRect.width, screenRect.y
									+ screenRect.height);

					Rectangle2D.Float oldRect = scene.getTransformBox()
							.getScreenRect();
					SVGPoint newRightBottomCoordinatePoint = scene
							.getTransformBox().getCoordinatePoint(
									rightBottomScreenPoint);

					float newWidth = newRightBottomCoordinatePoint.getX()
							- oldRect.x;
					float newHeight = newRightBottomCoordinatePoint.getY()
							- oldRect.y;

					float scaleX = newWidth / oldRect.width;
					float scaleY = newHeight / oldRect.height;

					float newScale = Math.min(scaleX, scaleY)
							* svgObj.getScale();

					// set new scale
					SVGTransform transformScale = svgObj
							.getTransform(TransformIndex.Scale);
					transformScale.setScale(newScale, newScale);

					// update transform box by object again
					scene.getTransformBox().setScreenRect(
							svgObj.getBorderScreenPosition());

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
				if (selectedTarget == evt.getCurrentTarget()) {

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
					SVGOMPoint svgPt = (SVGOMPoint) screenPt
							.matrixTransform(mat);

					SVGOMPoint startPosition = SVGUtils
							.getStartPoint(objBorder);

					float offsetX = svgPt.getX() - startPosition.getX();
					float offsetY = svgPt.getY() - startPosition.getY();

					int minOffset = 80;
					offsetX = offsetX < minOffset ? minOffset : offsetX;
					offsetY = offsetY < minOffset ? minOffset : offsetY;

					dragOffset = new Point2D.Float(svgPt.getX()
							- startPosition.getX(), svgPt.getY()
							- startPosition.getY());

					currentTransformType = TransformType.Move;
				} else if (scene.getTransformBox().isRightBottomArrowSelected()) {
					currentTransformType = TransformType.Scale;
				}
			}
		}, false);

		addMouseMoveListener(target);

		target.addEventListener("mouseup", new EventListener() {

			@Override
			public void handleEvent(Event evt) {
				if (TransformType.Move == currentTransformType) {
					Element ele = selectedTarget;
					long objId = SVGUtils.getObjectId(ele);
					Element border = scene.getElement(objId,
							SvgElementType.Border);
					// System.out.println(objId);
					CSSStyleDeclaration style = ((SVGStylable) border)
							.getStyle();
					style.removeProperty("stroke-dasharray");

					currentTransformType = null;
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

						scene.getTransformBox().setScreenRect(
								svgObj.getBorderScreenPosition());

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
