package com.bluesky.visualprogramming.ui.diagram;

import java.awt.Graphics;

import javax.swing.JPanel;


public class DiagramPanel extends JPanel {
	public DiagramPanel() {
	}
	
	private Painter painter;

	@Override
	protected void paintComponent(Graphics g) {	
		super.paintComponent(g);
		
		if(painter!=null)
			painter.paint(g);		
	}	

	public Painter getPainter() {
		return painter;
	}

	public void setPainter(Painter painter) {
		this.painter = painter;
	}
	
	
}
