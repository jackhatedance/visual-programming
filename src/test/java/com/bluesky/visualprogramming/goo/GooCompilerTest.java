package com.bluesky.visualprogramming.goo;

import java.io.InputStream;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bluesky.visualprogramming.vm.instruction.Instruction;

public class GooCompilerTest {
	GooCompiler compiler;

	@Before
	public void setup() {
		compiler = new GooCompiler();

	}

	@Ignore
	@Test
	public void testAssignment() {
		InputStream is = GooCompilerTest.class
				.getResourceAsStream("/sample-code/sample-2-assignment.goo");
		compiler.compile(is);

		for (Instruction ins : compiler.getInstructions()) {
			System.out.println(ins.toString());
		}

	}

	@Ignore
	@Test
	public void testSendMessage() {
		InputStream is = GooCompilerTest.class
				.getResourceAsStream("/sample-code/sample-3-send_message.goo");
		compiler.compile(is);

		for (Instruction ins : compiler.getInstructions()) {
			System.out.println(ins.toString());
		}

	}

	@Ignore
	@Test
	public void testIf() {
		InputStream is = GooCompilerTest.class
				.getResourceAsStream("/sample-code/sample-4-if.goo");
		compiler.compile(is);

		for (Instruction ins : compiler.getInstructions()) {
			String lbl = ins.label == null ? "\t" : ins.label;
			System.out.println(lbl + "\t\t" + ins.toString());
		}

	}

	@Ignore
	@Test
	public void testWhile() {
		InputStream is = GooCompilerTest.class
				.getResourceAsStream("/sample-code/sample-5-while.goo");
		compiler.compile(is);

		for (Instruction ins : compiler.getInstructions()) {
			String lbl = ins.label == null ? "\t" : ins.label;
			System.out.println(lbl + "\t\t" + ins.toString());
		}

	}

	
	@Test
	public void testFor() {
		InputStream is = GooCompilerTest.class
				.getResourceAsStream("/sample-code/sample-6-for.goo");
		compiler.compile(is);

		for (Instruction ins : compiler.getInstructions()) {
			String lbl = ins.label == null ? "" : ins.label;
			String comment = ins.comment == null ? "" : ins.comment;
			
			System.out
					.println(String.format("%1$-20s%2$-50s%3$-50s", lbl,ins,comment));
		}

	}
	
	@Ignore
	@Test
	public void testReturn() {
		InputStream is = GooCompilerTest.class
				.getResourceAsStream("/sample-code/sample-7-return.goo");
		compiler.compile(is);

		for (Instruction ins : compiler.getInstructions()) {
			String lbl = ins.label == null ? "" : ins.label;
			String comment = ins.comment == null ? "" : ins.comment;
			
			System.out
					.println(String.format("%1$-20s%2$-50s%3$-50s", lbl,ins,comment));
		}

	}
	
	@Ignore
	@Test
	public void testFieldAccess() {
		InputStream is = GooCompilerTest.class
				.getResourceAsStream("/sample-code/sample-8-field_access.goo");
		compiler.compile(is);

		for (Instruction ins : compiler.getInstructions()) {
			String lbl = ins.label == null ? "" : ins.label;
			String comment = ins.comment == null ? "" : ins.comment;
			
			System.out
					.println(String.format("%1$-20s%2$-50s%3$-50s", lbl,ins,comment));
		}

	}
}
