package com.bluesky.visualprogramming.flowchart;

import java.awt.Graphics;

public class Procedure extends AbstractSymbol {
	int width = 30;
	int height = 20;

	public Procedure(int x, int y) {
		super(x, y);
	}

	@Override
	public void draw(Graphics g) {g.setColor(color);
		g.drawRect(pos.x - width / 2, pos.y - height / 2, width, height);

	}
}
