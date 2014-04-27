package com.bluesky.visualprogramming.core;


public enum Prototype {

	Map {
		@Override
		public String getPath() {

			return ObjectRepository.PROTOTYPE_PATH + ".map";
		}
	},
	List {
		@Override
		public String getPath() {

			return ObjectRepository.PROTOTYPE_PATH + ".list";
		}
	};

	abstract public String getPath();


}
