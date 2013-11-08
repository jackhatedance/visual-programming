package com.bluesky.visualprogramming.ui.svg;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGRect;

public class SvgTransformBox {
	static Logger logger = Logger.getLogger(SvgTransformBox.class);

	// original box size
	Rectangle rectangleOrignal;

	Document doc;

	Element transform;
	boolean visible;

	public SvgTransformBox(Document doc, Element transform) {
		this.doc = doc;
		this.transform = transform;

		visible = true;

		rectangleOrignal = new Rectangle(500, 500);
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		if (visible)
			transform.removeAttribute("display");
		else
			transform.setAttribute("display", "none");
	}

	public boolean getVisible() {
		return visible;
	}

	/**
	 * change the box
	 * 
	 * @param rect
	 *            , is element unit, not screen length unit.
	 */
	public void setRectangle(Rectangle2D rect) {
		float scaleX = (float) (rect.getWidth() / rectangleOrignal.width);
		float scaleY = (float) (rect.getHeight() / rectangleOrignal.height);

		String scaleStr = String.format("scale(%f,%f)", scaleX, scaleY);

		String translateStr = String.format("translate(%f,%f)", rect.getX(), rect.getY());

		String transformStr = String.format("%s %s", scaleStr, translateStr);
		transform.setAttribute("transform", transformStr);
	}

}
