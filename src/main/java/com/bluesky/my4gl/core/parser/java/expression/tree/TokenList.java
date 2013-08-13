package com.bluesky.my4gl.core.parser.java.expression.tree;

import java.util.ArrayList;
import java.util.List;

public class TokenList {
	private List<String> list;
	private int index;

	public TokenList(List<String> list) {
		this.list = list;
		index = 0;

	}

	public int getIndex() {
		return index;
	}

	public boolean eof() {
		return index >= list.size();
	}

	public String poll() {

		if (eof())
			throw new RuntimeException("EOF");

		String s = list.get(index);
		index++;
		return s;
	}

	public String peek() {
		if (eof())
			throw new RuntimeException("EOF");

		return list.get(index);
	}

	public String last() {
		return list.get(index - 1);
	}

}
