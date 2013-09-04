package com.bluesky.visualprogramming.dialect.flowChart;

import java.awt.Graphics;

public class If extends AbstractSymbol {
	int width = 20, height = 20;

	public If(int x, int y) {
		super(x, y);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		int[] xPoints = { pos.x - width / 2, pos.x, pos.x + width / 2, pos.x };
		int[] yPoints = { pos.y, pos.y - height / 2, pos.y, pos.y + height / 2 };
		g.drawPolygon(xPoints, yPoints, 4);
	}
}
