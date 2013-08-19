package com.bluesky.visualprogramming.goo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.goo.parser.GooLexer;
import com.bluesky.visualprogramming.goo.parser.GooParser;
import com.bluesky.visualprogramming.goo.parser.GooParser.AccessFieldContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.AssignOperatorContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.AssigneeContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.AssigneeFieldContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.AssigneeVariableContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.AssignmentContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.AutoAssignOperatorContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.BlockContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.BooleanContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.CommentContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ConstantExprContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.FieldContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ForStatementContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.HeaderContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.IfStatementContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.MessgeNameContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.NameValueContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.NamedParamListContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.NumberContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.OrderedParamListContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.OwnAssignOperatorContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ParamDeclareListContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ParamListContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ProcedureContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.RefAssignOperatorContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ReturnStatementContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.SendMessageContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.StatementContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.StringContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.VariableContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.VariableExprContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.WhileStatementContext;
import com.bluesky.visualprogramming.goo.parser.GooVisitor;
import com.bluesky.visualprogramming.vm.instruction.AssignmentType;
import com.bluesky.visualprogramming.vm.instruction.CreateValueObject;
import com.bluesky.visualprogramming.vm.instruction.FieldAssignment;
import com.bluesky.visualprogramming.vm.instruction.Instruction;
import com.bluesky.visualprogramming.vm.instruction.PopBlock;
import com.bluesky.visualprogramming.vm.instruction.PushBlock;
import com.bluesky.visualprogramming.vm.instruction.VariableAssignment;

/**
 * convert goo code to VM instructions
 * 
 * @author jackding
 * 
 */
public class GooCompiler implements GooVisitor<_Object> {

	private List<Instruction> instructions = new ArrayList<Instruction>();

	// used to pass data between child node and parent node
	private Stack<Object> stack = new Stack<Object>();

	private int tempVarCount = 0;

	private String getNextTempVar() {
		String name = "tempVar" + tempVarCount;

		tempVarCount++;

		return name;
	}

	private void addInstruction(Instruction ins) {
		instructions.add(ins);
	}

	public void compile(InputStream is) {

		ANTLRInputStream input;
		try {
			input = new ANTLRInputStream(is);
		} catch (IOException e) {

			throw new RuntimeException(e);
		}

		GooLexer lexer = new GooLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		GooParser parser = new GooParser(tokens);
		ParseTree tree = parser.procedure();
		// System.out.println(tree.toStringTree(parser)); // print tree as text

		visit(tree);

	}

	@Override
	public _Object visit(@NotNull ParseTree tree) {
		// System.out.println("visit tree");
		tree.accept(this);

		return null;
	}

	@Override
	public _Object visitChildren(@NotNull RuleNode node) {
		for (int i = 0; i < node.getChildCount(); i++)
			node.getChild(i).accept(this);

		return null;
	}

	@Override
	public _Object visitTerminal(@NotNull TerminalNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitErrorNode(@NotNull ErrorNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitForStatement(ForStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitVariable(VariableContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitNumber(NumberContext ctx) {

		CreateValueObject ins = new CreateValueObject();

		ins.varName = (String) stack.pop();
		ins.type = ObjectType.INTEGER;
		ins.literalValue = ctx.getText();

		addInstruction(ins);
		return null;
	}

	@Override
	public _Object visitMessgeName(MessgeNameContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitReturnStatement(ReturnStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitNamedParamList(NamedParamListContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitOrderedParamList(OrderedParamListContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitBlock(BlockContext ctx) {
		System.out.println("block");

		addInstruction(new PushBlock());

		visitChildren(ctx);

		addInstruction(new PopBlock());

		return null;
	}

	@Override
	public _Object visitBoolean(BooleanContext ctx) {

		CreateValueObject ins = new CreateValueObject();

		ins.varName = (String) stack.pop();
		ins.type = ObjectType.BOOLEAN;
		ins.literalValue = ctx.getText();

		addInstruction(ins);
		return null;
	}

	@Override
	public _Object visitAccessField(AccessFieldContext ctx) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public _Object visitHeader(HeaderContext ctx) {
		System.out.println("header");
		return null;
	}

	@Override
	public _Object visitIfStatement(IfStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitField(FieldContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitSendMessage(SendMessageContext ctx) {
		// System.out.println("visitSendMessage");

		return null;
	}

	@Override
	public _Object visitStatement(StatementContext ctx) {
		// System.out.println("statement");
		visitChildren(ctx);
		return null;
	}

	@Override
	public _Object visitAssignment(AssignmentContext ctx) {
		// System.out.println("assignment");

		AssigneeContext assignee = ctx.assignee();
		if (assignee instanceof AssigneeVariableContext) {
			VariableAssignment ins = new VariableAssignment();
			ins.left = assignee.getText();

			if (ctx.expr() instanceof VariableExprContext) {
				ins.right = ctx.expr().getText();

			} else {
				String tempVar = getNextTempVar();
				ins.right = tempVar;

				stack.push(tempVar);
				ctx.expr().accept(this);

			}

			addInstruction(ins);
		} else {
			AssigneeFieldContext assigneeFieldContext = (AssigneeFieldContext) assignee;

			// visitAssigneeField

			String tempVar2 = getNextTempVar();
			stack.push(tempVar2);
			ctx.expr().accept(this);

			String tempVar = getNextTempVar();
			stack.push(tempVar);
			assigneeFieldContext.expr().accept(this);

			FieldAssignment ins = new FieldAssignment();
			ins.ownerVar = tempVar;
			ins.fieldName = assigneeFieldContext.field().getText();
			ins.rightVar = tempVar2;

			ctx.assignOperator().accept(this);
			ins.type = (AssignmentType) stack.pop();

			addInstruction(ins);

		}

		return null;
	}

	@Override
	public _Object visitParamDeclareList(ParamDeclareListContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitWhileStatement(WhileStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitString(StringContext ctx) {
		// System.out.println("visitString");

		CreateValueObject ins = new CreateValueObject();

		ins.varName = (String) stack.pop();
		ins.type = ObjectType.STRING;
		ins.literalValue = ctx.getText();

		addInstruction(ins);
		return null;
	}

	@Override
	public _Object visitComment(CommentContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitProcedure(ProcedureContext ctx) {
		// System.out.println("visit procedure");

		ctx.block().accept(this);

		return null;
	}

	@Override
	public _Object visitConstantExpr(ConstantExprContext ctx) {
		// System.out.println("visitConstantExpr");
		visitChildren(ctx);

		return null;
	}

	@Override
	public _Object visitAssigneeField(AssigneeFieldContext ctx) {
		System.out.println("visitAssigneeField");
		return null;
	}

	@Override
	public _Object visitVariableExpr(VariableExprContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitAssigneeVariable(AssigneeVariableContext ctx) {
		System.out.println("visitAssigneeVariable");
		return null;
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}

	public static void main(String[] args) {
		InputStream is = GooCompiler.class
				.getResourceAsStream("/sample-code/sample-2-assignment.goo");
		GooCompiler compiler = new GooCompiler();
		compiler.compile(is);

		for (Instruction ins : compiler.getInstructions()) {
			System.out.println(ins.toString());
		}
	}

	@Override
	public _Object visitRefAssignOperator(RefAssignOperatorContext ctx) {

		stack.push(AssignmentType.REF);
		return null;
	}

	@Override
	public _Object visitAutoAssignOperator(AutoAssignOperatorContext ctx) {
		stack.push(AssignmentType.AUTO);
		return null;
	}

	@Override
	public _Object visitOwnAssignOperator(OwnAssignOperatorContext ctx) {
		stack.push(AssignmentType.OWN);
		return null;
	}

	@Override
	public _Object visitNameValue(NameValueContext ctx) {
		 
		return null;
	}
}
