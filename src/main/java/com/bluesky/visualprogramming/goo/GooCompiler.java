package com.bluesky.visualprogramming.goo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.goo.parser.GooLexer;
import com.bluesky.visualprogramming.goo.parser.GooParser;
import com.bluesky.visualprogramming.goo.parser.GooParser.AccessFieldContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.AssigneeContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.AssigneeFieldContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.AssigneeVariableContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.AssignmentContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.AssignmentStatementContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.AutoAssignOperatorContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.BlockContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.BlockOrStatmentContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.BooleanContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.BreakStatementContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.CommentContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ConstantExprContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ContinueStatementContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.EmptyStatementContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ExpressionStatementContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.FalseBranchContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.FieldContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ForAfterthoughtContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ForConditionContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ForInitContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ForStatementContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.HeaderContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.IdFieldContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.IdMessageSubjectContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.IfStatementContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.LinkContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.NameValueContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.NamedParamListContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.NullValueContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.NumberContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ObjectLinkContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.OrderedParamListContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.OwnAssignOperatorContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ParamDeclareListContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ProcedureConstContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ProcedureContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.RefAssignOperatorContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ReturnStatementContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.SendMessageContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.StatementContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.StringContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.StringFieldContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.StringMessageSubjectContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.TrueBranchContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.VariableContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.VariableExprContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.WhileStatementContext;
import com.bluesky.visualprogramming.goo.parser.GooVisitor;
import com.bluesky.visualprogramming.vm.CompiledProcedure;
import com.bluesky.visualprogramming.vm.Compiler;
import com.bluesky.visualprogramming.vm.instruction.AccessField;
import com.bluesky.visualprogramming.vm.instruction.AssignmentType;
import com.bluesky.visualprogramming.vm.instruction.CreateObject;
import com.bluesky.visualprogramming.vm.instruction.FieldAssignment;
import com.bluesky.visualprogramming.vm.instruction.Goto;
import com.bluesky.visualprogramming.vm.instruction.GotoIf;
import com.bluesky.visualprogramming.vm.instruction.Instruction;
import com.bluesky.visualprogramming.vm.instruction.NoOperation;
import com.bluesky.visualprogramming.vm.instruction.PopBlock;
import com.bluesky.visualprogramming.vm.instruction.ProcedureEnd;
import com.bluesky.visualprogramming.vm.instruction.PushBlock;
import com.bluesky.visualprogramming.vm.instruction.SendMessage;
import com.bluesky.visualprogramming.vm.instruction.VariableAssignment;

/**
 * convert goo code to VM instructions
 * 
 * @author jackding
 * 
 */
public class GooCompiler implements GooVisitor<Object>, Compiler {

	static Logger logger = Logger.getLogger(GooCompiler.class);

	private List<String> parameters = new ArrayList<String>();
	private List<Instruction> instructions = new ArrayList<Instruction>();

	// let the child node know the current block name, so that it knows how to
	// goto begin/end of block
	private Stack<String> blockStack = new Stack<String>();

	private int tempVarCount = 0;
	private int blockCount = 0;

	private String getNextTempVar(String type) {
		// String name = "temp_" + type + "_" + tempVarCount;
		String name = "t_" + tempVarCount;
		tempVarCount++;

		return name;
	}

	private String getNextBlockName(String type) {
		// String name = "temp_" + type + "_" + tempVarCount;
		String name = "blk_" + blockCount;
		blockCount++;

		return name;
	}

	private Instruction getLastInstruction() {
		return instructions.get(instructions.size() - 1);
	}

	private void addInstruction(Instruction ins) {
		instructions.add(ins);
	}

	private void pushBlock(String blockName) {
		instructions.add(new PushBlock());
		blockStack.push(blockName);
	}

	private void popBlock() {
		instructions.add(new PopBlock());
		blockStack.pop();
	}

	public CompiledProcedure compile(String code) {
		InputStream stream = null;
		try {
			stream = new ByteArrayInputStream(code.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);

		}
		return compile(stream);
	}

	public CompiledProcedure compile(InputStream is) {

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

		CompiledProcedure cp = new CompiledProcedure(parameters, instructions);
		return cp;
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
		String blockName = getNextBlockName("For");

		pushBlock(blockName);

		getLastInstruction().comment = String.format("for(%s;%s;%s)", ctx
				.forInit() == null ? "" : ctx.forInit().getText(), ctx
				.forCondition().getText(), ctx.forAfterthought() == null ? ""
				: ctx.forAfterthought().getText());

		if (ctx.forInit() != null)
			ctx.forInit().accept(this);

		NoOperation entry = new NoOperation();
		entry.label = blockName + "Entry";
		addInstruction(entry);

		String conditionVar = (String) ctx.forCondition().accept(this);

		GotoIf gotoEnd = new GotoIf();
		gotoEnd.expected = false;
		gotoEnd.actualVarName = conditionVar;
		gotoEnd.destinationLabel = blockName + "End";

		addInstruction(gotoEnd);

		ctx.blockOrStatment().accept(this);

		if (ctx.forAfterthought() != null)
			ctx.forAfterthought().accept(this);

		// goto begin
		Goto gotoEntry = new Goto();
		gotoEntry.destinationLabel = blockName + "Entry";
		addInstruction(gotoEntry);

		NoOperation end = new NoOperation();
		end.label = blockName + "End";
		addInstruction(end);

		popBlock();

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
		ins.objType = ObjectType.INTEGER;
		ins.literal = ctx.getText();

		addInstruction(ins);
		return ins.varName;
	}

	@Override
	public Object visitReturnStatement(ReturnStatementContext ctx) {

		String var = (String) ctx.expr().accept(this);

		VariableAssignment ins = new VariableAssignment();
		ins.left = "result";
		ins.right = var;
		ins.comment = "return " + ctx.expr().getText();

		addInstruction(ins);

		Goto gotoEnd = new Goto();
		gotoEnd.destinationLabel = "procedureEnd";

		addInstruction(gotoEnd);

		return null;
	}

	@Override
	public Object visitNamedParamList(NamedParamListContext ctx) {
		String parametersVarName = getNextTempVar("namedParam");

		// create the root parameter object
		CreateObject ins = new CreateObject();
		ins.objType = ObjectType.NORMAL;
		ins.varName = parametersVarName;

		addInstruction(ins);

		for (int i = 0; i < ctx.nameValue().size(); i++) {
			NameValueContext nvc = ctx.nameValue(i);

			String paramVar = (String) nvc.accept(this);

			FieldAssignment ins2 = new FieldAssignment();
			ins2.ownerVar = parametersVarName;
			ins2.fieldName = nvc.ID().getText();
			ins2.rightVar = paramVar;
			ins2.assignmenType = AssignmentType.AUTO;

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
		ins.objType = ObjectType.NORMAL;
		ins.varName = parametersVarName;

		addInstruction(ins);

		// add parameters one by one

		for (int i = 0; i < paramVars.length; i++) {
			String paramVar = (String) paramVars[i];
			FieldAssignment ins2 = new FieldAssignment();
			ins2.ownerVar = parametersVarName;
			ins2.fieldName = "idx_" + i;
			ins2.rightVar = paramVar;
			ins2.assignmenType = AssignmentType.AUTO;

			addInstruction(ins2);
		}

		return parametersVarName;
	}

	@Override
	public Object visitBlock(BlockContext ctx) {
		// System.out.println("block");

		visitChildren(ctx);

		return null;
	}

	@Override
	public Object visitBoolean(BooleanContext ctx) {

		CreateObject ins = new CreateObject();

		ins.varName = getNextTempVar("boolean");
		ins.objType = ObjectType.BOOLEAN;
		ins.literal = ctx.getText();

		addInstruction(ins);
		return ins.varName;
	}

	@Override
	public Object visitAccessField(AccessFieldContext ctx) {

		String var = getNextTempVar("accessField");

		AccessField ins = new AccessField();
		ins.objName = (String) ctx.expr().accept(this);
		ins.fieldName = ctx.field().getText();
		ins.varName = var;

		addInstruction(ins);

		return var;
	}

	@Override
	public Object visitHeader(HeaderContext ctx) {
		// System.out.println("header");
		if (ctx.paramDeclareList() != null)
			return ctx.paramDeclareList().accept(this);

		return null;
	}

	@Override
	public Object visitIfStatement(IfStatementContext ctx) {
		// System.out.println("if");

		String blockName = getNextBlockName("If");

		pushBlock(blockName);

		String conditionVar = (String) ctx.expr().accept(this);

		// goto FalseBegin
		GotoIf gotoFalse = new GotoIf();
		gotoFalse.expected = false;
		gotoFalse.actualVarName = conditionVar;
		gotoFalse.destinationLabel = blockName + "FalseBegin";

		addInstruction(gotoFalse);

		// true begin
		ctx.trueBranch().accept(this);

		// label
		NoOperation trueEnd = new NoOperation();
		trueEnd.label = blockName + "TrueEnd";
		addInstruction(trueEnd);

		// goto FalseBegin
		Goto gotoFalseEnd = new Goto();
		gotoFalseEnd.destinationLabel = blockName + "FalseEnd";
		addInstruction(gotoFalseEnd);

		// label
		NoOperation falseBegin = new NoOperation();
		falseBegin.label = blockName + "FalseBegin";
		addInstruction(falseBegin);

		ctx.falseBranch().accept(this);

		// label
		NoOperation falseEnd = new NoOperation();
		falseEnd.label = blockName + "FalseEnd";
		addInstruction(falseEnd);

		popBlock();

		return null;
	}

	@Override
	public Object visitSendMessage(SendMessageContext ctx) {
		// System.out.println("visitSendMessage");
		String paramVar = null;

		// default
		ParameterStyle paramStyle = ParameterStyle.ByName;

		if (ctx.paramList() != null) {
			paramVar = (String) ctx.paramList().accept(this);

			if (ctx.paramList() instanceof OrderedParamListContext)
				paramStyle = ParameterStyle.ByOrder;
			else
				paramStyle = ParameterStyle.ByName;
		}

		SendMessage ins = new SendMessage();

		String tempVar = (String) ctx.expr().accept(this);

		ins.receiverVar = tempVar;

		// only one child, either Id or String. return procedure name
		ins.messageSubject = (String) ctx.messgeSubject().accept(this);
		ins.messageBodyVar = paramVar;

		String tempVar2 = getNextTempVar("sendMsgReply");
		ins.replyVar = tempVar2;

		ins.paramStyle = paramStyle;

		addInstruction(ins);

		getLastInstruction().comment = ctx.getText();

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

			ins.assignmenType = (AssignmentType) ctx.assignOperator().accept(
					this);

			addInstruction(ins);

		}

		return null;
	}

	@Override
	public Object visitParamDeclareList(ParamDeclareListContext ctx) {
		for (TerminalNode node : ctx.ID()) {
			parameters.add(node.getText());

			if (logger.isDebugEnabled())
				logger.debug("procedure parameter:" + node.getText());
		}
		return null;
	}

	@Override
	public Object visitWhileStatement(WhileStatementContext ctx) {

		// System.out.println("while");

		String blockName = getNextBlockName("While");

		pushBlock(blockName);

		NoOperation entry = new NoOperation();
		entry.label = blockName + "Entry";
		addInstruction(entry);

		String conditionVar = (String) ctx.expr().accept(this);

		// goto FalseBegin
		GotoIf gotoFalse = new GotoIf();
		gotoFalse.expected = false;
		gotoFalse.actualVarName = conditionVar;
		gotoFalse.destinationLabel = blockName + "Begin";

		addInstruction(gotoFalse);

		// true begin
		ctx.blockOrStatment().accept(this);

		// label
		NoOperation trueEnd = new NoOperation();
		trueEnd.label = blockName + "End";
		addInstruction(trueEnd);

		popBlock();

		return null;
	}

	@Override
	public Object visitString(StringContext ctx) {
		// System.out.println("visitString");

		CreateObject ins = new CreateObject();

		ins.varName = getNextTempVar("string");
		ins.objType = ObjectType.STRING;
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

		ctx.header().accept(this);

		ctx.block().accept(this);

		ProcedureEnd end = new ProcedureEnd();
		end.label = "ProcedureEnd";

		addInstruction(end);

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
		ins.left = tempVar;
		ins.right = (String) ctx.expr().accept(this);

		addInstruction(ins);

		return tempVar;
	}

	@Override
	public Object visitExpressionStatement(ExpressionStatementContext ctx) {

		return ctx.expr().accept(this);
	}

	@Override
	public Object visitContinueStatement(ContinueStatementContext ctx) {
		String block = blockStack.peek();

		Goto ins = new Goto();
		ins.destinationLabel = block + "Entry";

		addInstruction(ins);

		return null;
	}

	@Override
	public Object visitBreakStatement(BreakStatementContext ctx) {
		String block = blockStack.peek();

		Goto ins = new Goto();
		ins.destinationLabel = block + "End";

		addInstruction(ins);

		return null;
	}

	@Override
	public Object visitForAfterthought(ForAfterthoughtContext ctx) {
		return ctx.expr().accept(this);

	}

	@Override
	public Object visitForCondition(ForConditionContext ctx) {
		String var = (String) ctx.expr().accept(this);

		return var;
	}

	@Override
	public Object visitForInit(ForInitContext ctx) {
		return visitChildren(ctx);

	}

	@Override
	public Object visitFalseBranch(FalseBranchContext ctx) {

		return visitChildren(ctx);
	}

	@Override
	public Object visitTrueBranch(TrueBranchContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Object visitAssignmentStatement(AssignmentStatementContext ctx) {

		return ctx.assignment().accept(this);
	}

	@Override
	public Object visitEmptyStatement(EmptyStatementContext ctx) {

		return null;
	}

	@Override
	public Object visitBlockOrStatment(BlockOrStatmentContext ctx) {

		return visitChildren(ctx);
	}

	@Override
	public Object visitObjectLink(ObjectLinkContext ctx) {
		return ctx.link().accept(this);

	}

	@Override
	public Object visitLink(LinkContext ctx) {
		CreateObject ins = new CreateObject();

		ins.varName = getNextTempVar("link");
		ins.objType = ObjectType.LINK;
		ins.literal = ctx.getText();

		addInstruction(ins);
		return ins.varName;

	}

	@Override
	public Object visitNullValue(NullValueContext ctx) {

		return "null";
	}

	@Override
	public Object visitProcedureConst(ProcedureConstContext ctx) {

		Token startToken = ctx.getStart();
		Token stopToken = ctx.getStop();

		// we need skipped token, such as WhiteSpace
		String rawText = stopToken
				.getTokenSource()
				.getInputStream()
				.getText(
						Interval.of(startToken.getStartIndex(),
								stopToken.getStopIndex()));

		CreateObject ins = new CreateObject();

		ins.varName = getNextTempVar("link");
		ins.objType = ObjectType.PROCEDURE;
		ins.literal = rawText;

		addInstruction(ins);
		return ins.varName;

	}

	@Override
	public Object visitStringField(StringFieldContext ctx) {

		String wrapped = ctx.getText();
		String unwrapped = wrapped.substring(1, wrapped.length() - 1);
		return StringEscapeUtils.unescapeJava(unwrapped);

	}

	@Override
	public Object visitStringMessageSubject(StringMessageSubjectContext ctx) {
		String wrapped = ctx.getText();
		String unwrapped = wrapped.substring(1, wrapped.length() - 1);
		return StringEscapeUtils.unescapeJava(unwrapped);

	}
	
	@Override
	public Object visitIdMessageSubject(IdMessageSubjectContext ctx) {

		return ctx.getText();
	}

	@Override
	public Object visitIdField(IdFieldContext ctx) {

		return ctx.getText();
	}
}
