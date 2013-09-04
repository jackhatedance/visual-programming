package com.bluesky.visualprogramming.dialect.flowChart;

import java.awt.Graphics;

public class Start extends AbstractSymbol {
	int radius=10;
	public Start(int x, int y) {
		super(x, y);
	}
	@Override
	public void draw(Graphics g) {g.setColor(color);
		g.drawOval(pos.x-radius, pos.y-radius, radius*2,radius*2  );

	}
}
