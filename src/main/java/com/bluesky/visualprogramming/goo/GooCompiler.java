package com.bluesky.visualprogramming.goo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.bluesky.my4gl.core.instruction.CreateObjectInstruction;
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
import com.bluesky.visualprogramming.vm.instruction.CreateObject;
import com.bluesky.visualprogramming.vm.instruction.FieldAssignment;
import com.bluesky.visualprogramming.vm.instruction.Instruction;
import com.bluesky.visualprogramming.vm.instruction.PopBlock;
import com.bluesky.visualprogramming.vm.instruction.PushBlock;
import com.bluesky.visualprogramming.vm.instruction.SendMessage;
import com.bluesky.visualprogramming.vm.instruction.VariableAssignment;

/**
 * convert goo code to VM instructions
 * 
 * @author jackding
 * 
 */
public class GooCompiler implements GooVisitor<Object> {

	private List<Instruction> instructions = new ArrayList<Instruction>();

	// used to pass data between child node and parent node
	// private Stack<Object> stack = new Stack<Object>();

	private int tempVarCount = 0;

	private String getNextTempVar(String type) {
		//String name = "temp_" + type + "_" + tempVarCount;
		String name = "t_" + tempVarCount;
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
	public Object visit(@NotNull ParseTree tree) {
		// System.out.println("visit tree");
		tree.accept(this);

		return null;
	}

	@Override
	public Object[] visitChildren(@NotNull RuleNode node) {

		List<Object> results = new ArrayList<Object>();
		for (int i = 0; i < node.getChildCount(); i++) {
			Object result = node.getChild(i).accept(this);
			results.add(result);
		}

		return results.toArray(new Object[0]);
	}

	public Object[] visitEach(@NotNull List<? extends RuleContext> nodeList) {

		List<Object> results = new ArrayList<Object>();
		for (int i = 0; i < nodeList.size(); i++) {
			Object result = nodeList.get(i).accept(this);
			results.add(result);
		}

		return results.toArray(new Object[0]);
	}

	@Override
	public Object visitTerminal(@NotNull TerminalNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitErrorNode(@NotNull ErrorNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitForStatement(ForStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitVariable(VariableContext ctx) {
		System.out.println("visitVariableExpr");
		return null;
	}

	@Override
	public Object visitNumber(NumberContext ctx) {

		CreateObject ins = new CreateObject();

		ins.varName = getNextTempVar("number");
		ins.type = ObjectType.INTEGER;
		ins.literal = ctx.getText();

		addInstruction(ins);
		return ins.varName;
	}

	@Override
	public Object visitMessgeName(MessgeNameContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitReturnStatement(ReturnStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitNamedParamList(NamedParamListContext ctx) {
		String parametersVarName = getNextTempVar("namedParam");

		// create the root parameter object
		CreateObject ins = new CreateObject();
		ins.type = ObjectType.DEFAULT;
		ins.varName = parametersVarName;

		addInstruction(ins);

		for (int i = 0; i < ctx.nameValue().size(); i++) {
			NameValueContext nvc = ctx.nameValue(i);

			String paramVar = (String) nvc.accept(this);

			FieldAssignment ins2 = new FieldAssignment();
			ins2.ownerVar = parametersVarName;
			ins2.fieldName = nvc.ID().getText();
			ins2.rightVar = paramVar;
			ins2.type = AssignmentType.AUTO;

			addInstruction(ins2);
		}

		return parametersVarName;
	}

	@Override
	public Object visitOrderedParamList(OrderedParamListContext ctx) {

		String parametersVarName = getNextTempVar("orderedParam");

		Object[] paramVars = (Object[]) visitEach(ctx.expr());

		// create the root parameter object
		CreateObject ins = new CreateObject();
		ins.type = ObjectType.DEFAULT;
		ins.varName = parametersVarName;

		addInstruction(ins);

		// add parameters one by one

		for (int i = 0; i < paramVars.length; i++) {
			String paramVar = (String) paramVars[i];
			FieldAssignment ins2 = new FieldAssignment();
			ins2.ownerVar = parametersVarName;
			ins2.fieldName = "ele" + i;
			ins2.rightVar = paramVar;
			ins2.type = AssignmentType.AUTO;

			addInstruction(ins2);
		}

		return parametersVarName;
	}

	@Override
	public Object visitBlock(BlockContext ctx) {
		System.out.println("block");

		addInstruction(new PushBlock());

		visitChildren(ctx);

		addInstruction(new PopBlock());

		return null;
	}

	@Override
	public Object visitBoolean(BooleanContext ctx) {

		CreateObject ins = new CreateObject();

		ins.varName = getNextTempVar("boolean");
		ins.type = ObjectType.BOOLEAN;
		ins.literal = ctx.getText();

		addInstruction(ins);
		return ins.varName;
	}

	@Override
	public Object visitAccessField(AccessFieldContext ctx) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public Object visitHeader(HeaderContext ctx) {
		System.out.println("header");
		return null;
	}

	@Override
	public Object visitIfStatement(IfStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitField(FieldContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitSendMessage(SendMessageContext ctx) {
		// System.out.println("visitSendMessage");
		String paramVar = null;
		if (ctx.paramList() != null)
			paramVar = (String) ctx.paramList().accept(this);

		SendMessage ins = new SendMessage();

		String tempVar = (String) ctx.expr().accept(this);

		ins.receiverVar = tempVar;
		ins.messageSubject = ctx.messgeName().getText();
		ins.messageBodyVar = paramVar;

		String tempVar2 = getNextTempVar("sendMsgReply");
		ins.replyVar = tempVar2;
		addInstruction(ins);

		return tempVar2;
	}

	@Override
	public Object visitStatement(StatementContext ctx) {
		// System.out.println("statement");
		visitChildren(ctx);
		return null;
	}

	@Override
	public Object visitAssignment(AssignmentContext ctx) {
		// System.out.println("assignment");

		AssigneeContext assignee = ctx.assignee();
		if (assignee instanceof AssigneeVariableContext) {
			VariableAssignment ins = new VariableAssignment();
			ins.left = assignee.getText();

			if (ctx.expr() instanceof VariableExprContext) {
				ins.right = ctx.expr().getText();

			} else {

				ins.right = (String) ctx.expr().accept(this);

			}

			addInstruction(ins);
		} else {
			AssigneeFieldContext assigneeFieldContext = (AssigneeFieldContext) assignee;

			// visitAssigneeField

			String tempVar2 = (String) ctx.expr().accept(this);

			String tempVar = (String) assigneeFieldContext.expr().accept(this);

			FieldAssignment ins = new FieldAssignment();
			ins.ownerVar = tempVar;
			ins.fieldName = assigneeFieldContext.field().getText();
			ins.rightVar = tempVar2;

			ins.type = (AssignmentType) ctx.assignOperator().accept(this);

			addInstruction(ins);

		}

		return null;
	}

	@Override
	public Object visitParamDeclareList(ParamDeclareListContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitWhileStatement(WhileStatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitString(StringContext ctx) {
		// System.out.println("visitString");

		CreateObject ins = new CreateObject();

		ins.varName = getNextTempVar("string");
		ins.type = ObjectType.STRING;
		ins.literal = ctx.getText();

		addInstruction(ins);
		return ins.varName;
	}

	@Override
	public Object visitComment(CommentContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitProcedure(ProcedureContext ctx) {
		// System.out.println("visit procedure");

		ctx.block().accept(this);

		return null;
	}

	@Override
	public Object visitConstantExpr(ConstantExprContext ctx) {
		// System.out.println("visitConstantExpr");

		return visitChildren(ctx)[0];
	}

	@Override
	public Object visitAssigneeField(AssigneeFieldContext ctx) {
		System.out.println("visitAssigneeField");
		return null;
	}

	@Override
	public Object visitVariableExpr(VariableExprContext ctx) {
		// System.out.println("visitVariableExpr");

		// VariableAssignment ins = new VariableAssignment();
		// ins.left = (String) stack.pop();
		// ins.right = ctx.getText();
		//
		// addInstruction(ins);

		return ctx.getText();
	}

	@Override
	public Object visitAssigneeVariable(AssigneeVariableContext ctx) {
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
	public Object visitRefAssignOperator(RefAssignOperatorContext ctx) {

		return AssignmentType.REF;
	}

	@Override
	public Object visitAutoAssignOperator(AutoAssignOperatorContext ctx) {

		return AssignmentType.AUTO;
	}

	@Override
	public Object visitOwnAssignOperator(OwnAssignOperatorContext ctx) {

		return AssignmentType.OWN;
	}

	@Override
	public Object visitNameValue(NameValueContext ctx) {
		String tempVar = getNextTempVar("namedParamExpr");
		
		VariableAssignment ins = new VariableAssignment();
		ins.left=tempVar;
		ins.right = (String)ctx.expr().accept(this);
		
		addInstruction(ins);
		
		return tempVar;
	}
}
