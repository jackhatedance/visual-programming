package com.bluesky.visualprogramming.ui.svg;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.apache.batik.dom.svg.SVGOMGElement;
import org.apache.batik.dom.svg.SVGOMPoint;
import org.apache.batik.dom.svg.SVGOMRect;
import org.apache.batik.dom.svg.SVGOMRectElement;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGPoint;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.svg.SVGStylable;
import org.w3c.dom.svg.SVGTransform;

public class SvgTransformBox {
	static Logger logger = Logger.getLogger(SvgTransformBox.class);

	// original box size
	Rectangle rectangleOrignal;

	Document doc;

	SVGOMGElement objectElement;
	SVGOMRectElement borderElement;
	SVGElement rightBottomArrow;
	boolean rightBottomArrowSelected;

	boolean visible;

	public SvgTransformBox(Document doc, SVGOMGElement objectElement) {
		this.doc = doc;
		this.objectElement = objectElement;

		borderElement = (SVGOMRectElement) doc
				.getElementById("transform-border");

		rightBottomArrow = (SVGElement) doc
				.getElementById("transform-rightbottomarrow");
		rightBottomArrowSelected=false;
		
		visible = true;

		rectangleOrignal = new Rectangle(500, 500);
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		
		if (visible)
			objectElement.setAttribute("display", "block");
		else
			objectElement.setAttribute("display", "none");
	}

	public boolean getVisible() {
		return visible;
	}

	public void setScreenPosition(SVGPoint screenPos) {
		SVGMatrix matrix = borderElement.getScreenCTM();
		SVGPoint elePos = screenPos.matrixTransform(matrix.inverse());

		SVGTransform transform = objectElement.getTransform().getBaseVal()
				.getItem(TransformIndex.Offset.getIndex());

		float borderX = Float.valueOf(borderElement.getAttribute("x"));
		float borderY = Float.valueOf(borderElement.getAttribute("y"));

		float x = transform.getMatrix().getE() + elePos.getX() - borderX;
		float y = transform.getMatrix().getF() + elePos.getY() - borderY;

		transform.setTranslate(x, y);
	}

	public void setScale(float scale) {
		SVGMatrix matrix = borderElement.getScreenCTM();

		SVGTransform transform = objectElement.getTransform().getBaseVal()
				.getItem(TransformIndex.Scale.getIndex());

		transform.setScale(scale, scale);
	}

	public void setBorder(SVGOMRect rect) {

	}

	/**
	 * make the transform box as same as the selected object's border
	 * 
	 * @param rect
	 *            , is element unit, not screen length unit.
	 */
	public void setRectangle1(String transformStr, Rectangle2D border) {
		// System.out.println(String.format("x=%f,y=%f,width=%f,height=%f",
		// rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight()));

		// 1. sync group object
		/*
		 * String scaleStr = String.format("scale(%f,%f)", scale, scale);
		 * 
		 * String translateStr = String.format("translate(%f,%f)",
		 * translate.getX(), translate.getY());
		 * 
		 * String transformStr = String.format("%s %s", scaleStr, translateStr);
		 * objectElement.setAttribute("transform", transformStr);
		 */
		objectElement.setAttribute("transform", transformStr);

		// 2. sync border parameters
		borderElement.setAttribute("x", String.valueOf(border.getX()));
		borderElement.setAttribute("y", String.valueOf(border.getY()));
		borderElement.setAttribute("width", String.valueOf(border.getWidth()));
		borderElement
				.setAttribute("height", String.valueOf(border.getHeight()));

	}
	
	public void setRightBottomArrowSelected(boolean selected){
		this.rightBottomArrowSelected = selected;
		
		
		((SVGStylable) rightBottomArrow).getStyle().setProperty(
				"stroke", "green", "");

	}

}
