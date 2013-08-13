package com.bluesky.visualprogramming.flowchart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

public class Arrow extends AbstractSymbol {
	Point posFrom, posTo;

	AbstractSymbol from, to;

	public Arrow(Point posFrom, Point posTo) {
		this.posFrom = posFrom;
		this.posTo = posTo;
	}

	public Arrow(AbstractSymbol from, AbstractSymbol to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public void draw(Graphics g) {

		color=Color.red;
		
		Point from = getFrom();
		Point to = getTo();
		
		
		drawArrow(g,from.x, from.y, to.x, to.y);
	}

	Point getFrom() {
		return from != null ? from.pos : posFrom;
	}

	Point getTo() {
		return to != null ? to.pos : posTo;
	}
	private final int ARR_SIZE = 10;

    void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
        Graphics2D g = (Graphics2D) g1.create();

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len, 0);
        g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                      new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
    }
}
