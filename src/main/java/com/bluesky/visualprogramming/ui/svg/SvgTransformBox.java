package com.bluesky.visualprogramming.ui.svg;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.apache.batik.dom.svg.AbstractSVGPathSegList.SVGPathSegMovetoLinetoItem;
import org.apache.batik.dom.svg.SVGOMGElement;
import org.apache.batik.dom.svg.SVGOMPathElement;
import org.apache.batik.dom.svg.SVGOMPoint;
import org.apache.batik.dom.svg.SVGOMRectElement;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGLocatable;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGPoint;
import org.w3c.dom.svg.SVGStylable;
import org.w3c.dom.svg.SVGTransform;

/**
 * this transform box won't scale itself, because that will change the size of
 * the border width. so the transform box is dynamically adjusted.
 * 
 * @author jack
 * 
 */
public class SvgTransformBox {
	static Logger logger = Logger.getLogger(SvgTransformBox.class);

	Document doc;

	SVGOMGElement objectElement;
	SVGOMRectElement borderElement;
	SVGElement leftTopArrow;
	SVGElement topArrow;
	SVGElement rightTopArrow;
	SVGElement rightArrow;
	SVGElement rightBottomArrow;
	SVGElement bottomArrow;
	SVGElement leftBottomArrow;
	SVGElement leftArrow;

	float borderPadding = 5;
	float arrowPadding = 20;
	float arrowHeight = 50;

	boolean rightBottomArrowSelected;

	boolean visible;

	/**
	 * the latest screen rectangle, used to calculate the scale rate.
	 */
	private Rectangle2D.Float screenRect;

	public SvgTransformBox(Document doc, SVGOMGElement objectElement) {
		this.doc = doc;
		this.objectElement = objectElement;

		borderElement = (SVGOMRectElement) doc
				.getElementById("transform-border");

		leftTopArrow = (SVGElement) doc
				.getElementById("transform-lefttoparrow");
		topArrow = (SVGElement) doc.getElementById("transform-toparrow");
		rightTopArrow = (SVGElement) doc
				.getElementById("transform-righttoparrow");
		rightArrow = (SVGElement) doc.getElementById("transform-rightarrow");
		rightBottomArrow = (SVGElement) doc
				.getElementById("transform-rightbottomarrow");
		bottomArrow = (SVGElement) doc.getElementById("transform-bottomarrow");
		leftBottomArrow = (SVGElement) doc
				.getElementById("transform-leftbottomarrow");
		leftArrow = (SVGElement) doc.getElementById("transform-leftarrow");

		rightBottomArrowSelected = false;

		visible = false;

		addMouseListener((EventTarget) rightBottomArrow);

	}

	private void addMouseListener(org.w3c.dom.events.EventTarget target) {
		target.addEventListener("mouseover", new EventListener() {

			@Override
			public void handleEvent(Event arg0) {

				setRightBottomArrowSelected(true);

			}
		}, false);

		target.addEventListener("mouseout", new EventListener() {

			@Override
			public void handleEvent(Event arg0) {

				setRightBottomArrowSelected(false);

			}
		}, false);

	}

	public void setVisible(boolean visible) {
		this.visible = visible;

		CSSStyleDeclaration css = objectElement.getStyle();
		if (visible)
			css.setProperty("visibility", "visible", "");
		else
			css.setProperty("visibility", "hidden", "");

	}

	public boolean getVisible() {
		return visible;
	}

	public void setScreenRect(Rectangle2D.Float rect) {
		this.screenRect = rect;

		SVGMatrix matrix = objectElement.getScreenCTM();

		SVGPoint leftTopScreenPoint = new SVGOMPoint(rect.x, rect.y);
		SVGPoint leftTopCoorindatePoint = leftTopScreenPoint
				.matrixTransform(matrix.inverse());

		SVGPoint rightBottomScreenPoint = new SVGOMPoint(rect.x + rect.width,
				rect.y + rect.height);
		SVGPoint rightBottomCoorindatePoint = rightBottomScreenPoint
				.matrixTransform(matrix.inverse());

		Rectangle2D.Float coordinateRect = new Rectangle2D.Float(
				leftTopCoorindatePoint.getX(), leftTopCoorindatePoint.getY(),
				rightBottomCoorindatePoint.getX()
						- leftTopCoorindatePoint.getX(),
				rightBottomCoorindatePoint.getY()
						- leftTopCoorindatePoint.getY());

		setCoordinateRectangle("", coordinateRect);

	}

	/**
	 * make the transform box as same as the selected object's border
	 * 
	 * @param rect
	 *            , is element unit, not screen length unit.
	 */
	public void setCoordinateRectangle(String transformStr, Rectangle2D border) {

		objectElement.setAttribute("transform", transformStr);

		Rectangle2D grownBorder = new Rectangle2D.Double(border.getX()
				- borderPadding, border.getY() - borderPadding,
				border.getWidth() + borderPadding * 2, border.getHeight()
						+ borderPadding * 2);

		// 2. sync border parameters
		borderElement.setAttribute("x", String.valueOf(grownBorder.getX()));
		borderElement.setAttribute("y", String.valueOf(grownBorder.getY()));
		borderElement.setAttribute("width",
				String.valueOf(grownBorder.getWidth()));
		borderElement.setAttribute("height",
				String.valueOf(grownBorder.getHeight()));

		// update arrows
		SVGOMPathElement path = (SVGOMPathElement) leftTopArrow;
		SVGPathSegMovetoLinetoItem moveLineTo = (SVGPathSegMovetoLinetoItem) path
				.getAnimatedPathSegList().getItem(0);
		moveLineTo.setX((float) (grownBorder.getX() - arrowPadding));
		moveLineTo.setY((float) (grownBorder.getY() - arrowPadding));

		path = (SVGOMPathElement) topArrow;
		moveLineTo = (SVGPathSegMovetoLinetoItem) path.getAnimatedPathSegList()
				.getItem(0);
		moveLineTo
				.setX((float) (grownBorder.getX() + grownBorder.getWidth() / 2));
		moveLineTo.setY((float) (grownBorder.getY()) - arrowPadding);

		path = (SVGOMPathElement) rightTopArrow;
		moveLineTo = (SVGPathSegMovetoLinetoItem) path.getAnimatedPathSegList()
				.getItem(0);
		moveLineTo
				.setX((float) (grownBorder.getX() + grownBorder.getWidth() + arrowPadding));
		moveLineTo.setY((float) (grownBorder.getY() - arrowPadding));

		path = (SVGOMPathElement) rightArrow;
		moveLineTo = (SVGPathSegMovetoLinetoItem) path.getAnimatedPathSegList()
				.getItem(0);
		moveLineTo
				.setX((float) (grownBorder.getX() + grownBorder.getWidth() + arrowPadding));
		moveLineTo
				.setY((float) (grownBorder.getY() + grownBorder.getHeight() / 2));

		path = (SVGOMPathElement) rightBottomArrow;
		moveLineTo = (SVGPathSegMovetoLinetoItem) path.getAnimatedPathSegList()
				.getItem(0);
		moveLineTo
				.setX((float) (grownBorder.getX() + grownBorder.getWidth() + arrowPadding));
		moveLineTo
				.setY((float) (grownBorder.getY() + grownBorder.getHeight() + arrowPadding));

		path = (SVGOMPathElement) bottomArrow;
		moveLineTo = (SVGPathSegMovetoLinetoItem) path.getAnimatedPathSegList()
				.getItem(0);
		moveLineTo
				.setX((float) (grownBorder.getX() + grownBorder.getWidth() / 2));
		moveLineTo
				.setY((float) (grownBorder.getY() + grownBorder.getHeight() + arrowPadding));

		path = (SVGOMPathElement) leftBottomArrow;
		moveLineTo = (SVGPathSegMovetoLinetoItem) path.getAnimatedPathSegList()
				.getItem(0);
		moveLineTo.setX((float) (grownBorder.getX() - arrowPadding));
		moveLineTo
				.setY((float) (grownBorder.getY() + grownBorder.getHeight() + arrowPadding));

		path = (SVGOMPathElement) leftArrow;
		moveLineTo = (SVGPathSegMovetoLinetoItem) path.getAnimatedPathSegList()
				.getItem(0);
		moveLineTo.setX((float) (grownBorder.getX() - arrowPadding));
		moveLineTo
				.setY((float) (grownBorder.getY() + grownBorder.getHeight() / 2));

	}

	public void setRightBottomArrowSelected(boolean selected) {
		this.rightBottomArrowSelected = selected;

		String fillColor = "";
		if (selected)
			fillColor = "green";
		else
			fillColor = "black";

		((SVGStylable) rightBottomArrow).getStyle().setProperty("fill",
				fillColor, "");

	}

	public Rectangle2D.Float getScreenRect() {
		return screenRect;
	}

	public SVGOMPoint getCoordinatePoint(Point2D rightBottomScreenPoint) {
		// convert it to a point for use with the Matrix
		SVGPoint pt = new SVGOMPoint((float) rightBottomScreenPoint.getX(),
				(float) rightBottomScreenPoint.getY());
		// Get the items screen coordinates, and apply the
		// transformation
		// elem -> screen
		SVGMatrix mat = ((SVGLocatable) borderElement).getScreenCTM();

		mat = mat.inverse(); // screen -> elem
		SVGOMPoint screenPoint = (SVGOMPoint) pt.matrixTransform(mat);

		return screenPoint;
	}

}
