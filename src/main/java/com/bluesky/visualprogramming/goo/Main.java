package com.bluesky.visualprogramming.goo;
import java.io.FileInputStream;
import java.io.InputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import com.bluesky.visualprogramming.goo.parser.GooLexer;
import com.bluesky.visualprogramming.goo.parser.GooParser;


public class Main {
	public static void main(String[] args) throws Exception {
		String inputFile = null;
		if (args.length > 0)
			inputFile = args[0];
		InputStream is = System.in;
		if (inputFile != null)
			is = new FileInputStream(inputFile);
		ANTLRInputStream input = new ANTLRInputStream(is);
		GooLexer lexer = new GooLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		GooParser parser = new GooParser(tokens);
		ParseTree tree = parser.procedure(); 
		System.out.println(tree.toStringTree(parser)); // print tree as text
		
		GooCompiler visitor = new GooCompiler();
		visitor.visit(tree);
	}
}
