package com.bluesky.visualprogramming.dialect.flowChart;

import java.awt.Graphics;
import java.util.Set;

public class End extends AbstractSymbol {
	int radius = 10;

	public End(int x, int y) {
		super(x, y);
	}

	@Override
	public void draw(Graphics g) {g.setColor(color);
		g.fillOval(pos.x - radius, pos.y - radius, radius * 2, radius * 2);

	}
}
