package com.bluesky.visualprogramming.flowchart;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Draw {
	JFrame f = new JFrame();

	public void disp() {
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(100, 100, 1600, 900);
		MosL ml = new MosL();
		Canvas c = new Canvas();
		f.add(c);
		c.addMouseListener(ml);
		c.addMouseMotionListener(ml);

		f.setVisible(true);

		createSymbols();

	}

	List<AbstractSymbol> symbols = new ArrayList<AbstractSymbol>();

	public static void main(String[] args) {

		Draw d = new Draw();
		d.disp();
	}

	private void createSymbols() {
		Start start = new Start(200, 200);
		symbols.add(start);

		End end = new End(200, 300);
		symbols.add(end);

		If _if = new If(200, 400);
		symbols.add(_if);

		Procedure procedure = new Procedure(200, 500);
		symbols.add(procedure);

		Arrow arrow = new Arrow(start, null);
		arrow.posTo = new Point(300, 300);
		symbols.add(arrow);
	}

	public class MosL extends MouseAdapter {
		int sx = 0;
		int sy = 0;
		boolean onDrag = false;

		@Override
		public void mouseDragged(MouseEvent e) {
			if (onDrag) {
				int x = e.getX();
				int y = e.getY();

				Canvas comp = (Canvas) e.getSource();
				Graphics g = comp.getGraphics();
				// comp.repaint(); << for cleaning up the intermediate lines :
				// doesnt work :(
				g.drawLine(sx, sy, x, y);
				return;
			}
			onDrag = true;
			sx = e.getX();
			sy = e.getY();

			System.out.println("Draggg");

		}

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("Pressed");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("Released");
			if (onDrag)
				onDrag = false;
			int x = e.getX();
			int y = e.getY();

			Canvas comp = (Canvas) e.getSource();
			Graphics g = comp.getGraphics();
			g.setColor(Color.red);
			g.drawLine(sx, sy, x, y);

			// Graphics g = comp.getGraphics();
			// c.getGraphics().drawString("abc", 200, 200);
			for (AbstractSymbol as : symbols)
				as.draw(g);
			return;

		}

	}

}
