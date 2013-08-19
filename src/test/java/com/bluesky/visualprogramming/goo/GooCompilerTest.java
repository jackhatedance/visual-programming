package com.bluesky.visualprogramming.goo;

import java.io.InputStream;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.bluesky.visualprogramming.vm.instruction.Instruction;


public class GooCompilerTest  {
	GooCompiler compiler;

	@Before
	public void setup() {
		compiler = new GooCompiler();

	}

	@Test
	public void test() {
		InputStream is = GooCompilerTest.class
				.getResourceAsStream("/sample-code/sample-2-assignment.goo");
		compiler.compile(is);
		
		for(Instruction ins : compiler.getInstructions()){
			System.out.println(ins.toString());
		}
		
	}
}
