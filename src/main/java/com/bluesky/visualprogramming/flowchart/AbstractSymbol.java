package com.bluesky.visualprogramming.flowchart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Set;

public abstract class AbstractSymbol {
	Point pos;
	Set<Arrow> inbounds;
	Set<Arrow> outbounds;

	final int width = 50;
	
	Color color=Color.black;

	public AbstractSymbol() {

	}

	public AbstractSymbol(int x, int y) {
		this.pos = new Point(x, y);
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.drawRect(pos.x - width, pos.y - width, width * 2, width * 2);
	}
}
