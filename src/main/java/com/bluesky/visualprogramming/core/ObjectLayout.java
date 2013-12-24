package com.bluesky.visualprogramming.core;

public enum ObjectLayout {
	XY, List {
		public void preprocess(_Object owner) {
			//re-arrange positions
			int unitHeight=50;
			int i=0;
			for(Field f : owner.getFields())
			{
				f.getArea().x=0;
				f.getArea().y = i*unitHeight;				
				
				i++;
			}
		}
	};

	public void preprocess(_Object owner) {
		
	}
}
