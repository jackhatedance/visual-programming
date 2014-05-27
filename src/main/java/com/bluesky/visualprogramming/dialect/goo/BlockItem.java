package com.bluesky.visualprogramming.dialect.goo;

import com.bluesky.visualprogramming.vm.instruction.BlockType;

public class BlockItem {

	public BlockType type;
	public String name;

	public BlockItem(BlockType type, String name) {
		this.type = type;
		this.name = name;
	}

	private String getLabel() {

		return type.name() + "_" + name;
	}
}
