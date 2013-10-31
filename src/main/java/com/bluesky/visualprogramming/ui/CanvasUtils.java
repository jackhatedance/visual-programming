package com.bluesky.visualprogramming.ui;

import java.awt.Point;

import org.apache.batik.dom.svg.SVGOMPoint;

public class CanvasUtils {
	static public Point scaleBack(Point point, double scaleRate) {
		Point newP = new Point((int) (point.x / scaleRate),
				(int) (point.y / scaleRate));

		return newP;
	}

	static public Point getOffset(Point p1, Point p2) {
		return new Point(p2.x - p1.x, p2.y - p1.y);
	}
	
	static public SVGOMPoint getOffset2(Point p1, Point p2) {
		return new SVGOMPoint(p2.x - p1.x, p2.y - p1.y);
	}
	 
	static public Point move(Point p1, Point offset) {
		return new Point(p1.x + offset.x, p1.y + offset.y);
	}
	
}
