package com.bluesky.my4gl.core.flow.block;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bluesky.my4gl.core.instruction.InstructionType;
import com.bluesky.my4gl.core.parser.java.RegExpUtils;

public enum BlockType {

	For {
		@Override
		public String getPattern() {
			// for(a>1){}
			return "^[\\t\\s]*for\\((.*)\\)[\\t\\s]*\\{[\\t\\s]*$";
		}

		@Override
		public Block createBlock() {
			// TODO Auto-generated method stub
			return null;
		}
	},
	If {
		@Override
		public String getPattern() {
			// if(a>1){}
			return "^[\\t\\s]*if\\((.*)\\)[\\t\\s]*\\{[\\t\\s]*$";
		}

		@Override
		public Block createBlock() {
			// TODO Auto-generated method stub
			return null;
		}
	},
	While {
		@Override
		public String getPattern() {
			// while(a>1){}
			return "^[\\t\\s]*while\\((.*)\\)[\\t\\s]*\\{[\\t\\s]*$";
		}

		@Override
		public Block createBlock() {

			// WhileBlock block = new WhileBlock();

			return null;
		}
	};

	abstract public String getPattern();

	abstract public Block createBlock();

	/**
	 * since all block are similar
	 * 
	 * @param s
	 * @return
	 */
	public static String getCondition(String s) {
		Pattern pattern = Pattern
				.compile("^[\\t\\s]*\\w+\\((.*)\\)[\\t\\s]*\\{[\\t\\s]*$");
		Matcher matcher = pattern.matcher(s);
		if (matcher.matches()) {
			//RegExpUtils.printGroups("BlockType", matcher);
			
			String condition = matcher.group(1);
			return condition;
		} else
			throw new RuntimeException("no condition found");
	}

	public static BlockType getType(String line) {
		String s = line.trim();
		for (BlockType it : BlockType.values()) {
			if (s.matches(it.getPattern()))
				return it;

		}
		throw new RuntimeException("unknown block type:" + s);

	}

	public static void main(String[] args) {

		String[] ss = new String[] { " 		while(i.lt(10)) { " };
		for (String s : ss) {
			BlockType tt = BlockType.getType(s);
			System.out.println(s + "  --  " + tt + " condition --  "
					+ getCondition(s));

			// Instruction ins = tt.parse(s);
			// System.out.println(s + "  --  " + tt + " -- " + ins);
		}

	}
}
