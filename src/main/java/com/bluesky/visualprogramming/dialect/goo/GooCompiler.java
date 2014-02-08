package com.bluesky.visualprogramming.dialect.goo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
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
import com.bluesky.visualprogramming.dialect.goo.parser.GooLexer;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.AccessFieldContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.AssigneeContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.AssigneeFieldContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.AssigneeVariableContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.AssignmentContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.AssignmentStatementContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.AutoAssignOperatorContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.BlockContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.BlockOrStatmentContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.BooleanContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.BreakStatementContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.CommentContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.ConstantExprContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.ContinueStatementContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.EmptyStatementContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.ExpressionStatementContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.FalseBranchContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.ForAfterthoughtContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.ForConditionContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.ForInitContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.ForStatementContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.HeaderContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.IdFieldContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.IdMessageSubjectContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.IfStatementContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.LinkContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.NameValueContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.NamedParamListContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.NullValueContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.NumberContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.ObjectConstContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.ObjectContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.ObjectLinkContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.OrderedParamListContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.OwnAssignOperatorContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.ParamDeclareListContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.ProcedureConstContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.ProcedureContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.RefAssignOperatorContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.ReturnStatementContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.SendMessageContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.StatementContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.StringContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.StringFieldContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.StringMessageSubjectContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.TrueBranchContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.VarFieldContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.VarMessageSubjectContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.VariableContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.VariableExprContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooParser.WhileStatementContext;
import com.bluesky.visualprogramming.dialect.goo.parser.GooVisitor;
import com.bluesky.visualprogramming.vm.CompiledProcedure;
import com.bluesky.visualprogramming.vm.Compiler;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
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

	private static String PROCEDURE_END_LABEL = "procedureEnd";

	private List<String> parameters = new ArrayList<String>();
	private List<Instruction> instructions = new ArrayList<Instruction>();

	// let the child node know the current block name, so that it knows how to
	// goto begin/end of block
	private Stack<String> blockStack = new Stack<String>();

	private int tempVarCount = 0;
	private int blockCount = 0;

	/**
	 * variable name stack. rarely used.
	 */
	private Stack<String> VarNameStack = new Stack<String>();

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

	private void pushBlock(String blockName,ParserRuleContext ctx) {
		instructions.add(new PushBlock(ctx.start.getLine()));
		blockStack.push(blockName);
	}

	private void popBlock(ParserRuleContext ctx) {
		instructions.add(new PopBlock(ctx.start.getLine()));
		blockStack.pop();
	}

	public static void main(String[] args) {
		InputStream is = GooCompiler.class
				.getResourceAsStream("/sample-code/sample-2-2-assignment.goo");
		GooCompiler compiler = new GooCompiler();
		try{
			compiler.compile(is);
			for (Instruction ins : compiler.getInstructions()) {
				System.out.println(ins.toString());
			}
		}catch(Exception e){
			System.out.println("parsing error: "+e.getMessage());
			//e.printStackTrace();
		}
		
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
		//throw parsing exceptions
		//parser.setErrorHandler(new BailErrorStrategy());
		
		lexer.removeErrorListeners();
		lexer.addErrorListener(DescriptiveErrorListener.INSTANCE);
		parser.removeErrorListeners();
		parser.addErrorListener(DescriptiveErrorListener.INSTANCE);
		
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

		pushBlock(blockName,ctx);

		getLastInstruction().comment = String.format("for(%s;%s;%s)", ctx
				.forInit() == null ? "" : ctx.forInit().getText(), ctx
				.forCondition().getText(), ctx.forAfterthought() == null ? ""
				: ctx.forAfterthought().getText());

		if (ctx.forInit() != null)
			ctx.forInit().accept(this);

		NoOperation entry = new NoOperation(ctx.start.getLine());
		entry.label = blockName + "Entry";
		addInstruction(entry);

		String conditionVar = (String) ctx.forCondition().accept(this);

		GotoIf gotoEnd = new GotoIf(ctx.start.getLine());
		gotoEnd.expected = false;
		gotoEnd.actualVarName = conditionVar;
		gotoEnd.destinationLabel = blockName + "End";

		addInstruction(gotoEnd);

		ctx.blockOrStatment().accept(this);

		if (ctx.forAfterthought() != null)
			ctx.forAfterthought().accept(this);

		// goto begin
		Goto gotoEntry = new Goto(ctx.start.getLine());
		gotoEntry.destinationLabel = blockName + "Entry";
		addInstruction(gotoEntry);

		NoOperation end = new NoOperation(ctx.start.getLine());
		end.label = blockName + "End";
		addInstruction(end);

		popBlock(ctx);

		return null;
	}

	@Override
	public Object visitVariable(VariableContext ctx) {
		System.out.println("visitVariableExpr");
		return null;
	}

	@Override
	public Object visitNumber(NumberContext ctx) {

		CreateObject ins = new CreateObject(ctx.start.getLine());

		ins.varName = getNextTempVar("number");
		ins.objType = ObjectType.INTEGER;
		ins.value = ctx.getText();

		addInstruction(ins);
		return ins.varName;
	}

	@Override
	public Object visitReturnStatement(ReturnStatementContext ctx) {

		String var = (String) ctx.expr().accept(this);

		VariableAssignment ins = new VariableAssignment(ctx.start.getLine());
		ins.left =  ProcedureExecutionContext.VAR_RESULT;
		ins.right = var;
		ins.comment = "return " + ctx.expr().getText();

		addInstruction(ins);

		Goto gotoEnd = new Goto(ctx.start.getLine());
		gotoEnd.destinationLabel = PROCEDURE_END_LABEL;

		addInstruction(gotoEnd);

		return null;
	}

	@Override
	public Object visitNamedParamList(NamedParamListContext ctx) {
		String parametersVarName = VarNameStack.pop();

		if (ctx.nameValue().size() == 1
				&& ctx.nameValue(0).ID().getText().equals("_body")) {
			NameValueContext nvc = ctx.nameValue(0);
			/**
			 * if a parameter named '_body' and it is the only one, that means
			 * the parameter itself is the message body. no array will
			 * constructed.
			 */

			String paramVar = (String) nvc.accept(this);
			return null;
		} else {

			for (int i = 0; i < ctx.nameValue().size(); i++) {
				NameValueContext nvc = ctx.nameValue(i);

				String paramVar = (String) nvc.accept(this);

				// create field name var
				CreateObject insFieldName = new CreateObject(ctx.start.getLine());

				insFieldName.varName = getNextTempVar("varField");
				insFieldName.objType = ObjectType.STRING;
				insFieldName.value = nvc.ID().getText();

				addInstruction(insFieldName);

				FieldAssignment ins2 = new FieldAssignment(ctx.start.getLine());
				ins2.ownerVar = parametersVarName;
				ins2.fieldNameVar = insFieldName.varName;
				ins2.rightVar = paramVar;
				ins2.assignmenType = AssignmentType.AUTO;

				addInstruction(ins2);
			}

			return null;
		}
	}

	@Override
	public Object visitOrderedParamList(OrderedParamListContext ctx) {

		String parametersVarName = VarNameStack.pop();

		Object[] paramVars = (Object[]) visitEach(ctx.expr());

		// add parameters one by one

		for (int i = 0; i < paramVars.length; i++) {
			String paramVar = (String) paramVars[i];

			CreateObject createField = new CreateObject(ctx.start.getLine());

			createField.varName = getNextTempVar("string");
			createField.objType = ObjectType.STRING;
			createField.value = "idx_" + i;

			addInstruction(createField);

			FieldAssignment ins2 = new FieldAssignment(ctx.start.getLine());
			ins2.ownerVar = parametersVarName;
			ins2.fieldNameVar = createField.varName;
			ins2.rightVar = paramVar;
			ins2.assignmenType = AssignmentType.AUTO;

			addInstruction(ins2);
		}

		return null;
	}

	@Override
	public Object visitBlock(BlockContext ctx) {
		// System.out.println("block");

		visitChildren(ctx);

		return null;
	}

	@Override
	public Object visitBoolean(BooleanContext ctx) {

		CreateObject ins = new CreateObject(ctx.start.getLine());

		ins.varName = getNextTempVar("boolean");
		ins.objType = ObjectType.BOOLEAN;
		ins.value = ctx.getText();

		addInstruction(ins);
		return ins.varName;
	}

	@Override
	public Object visitAccessField(AccessFieldContext ctx) {

		String var = getNextTempVar("accessField");

		AccessField ins = new AccessField(ctx.start.getLine());
		ins.objName = (String) ctx.expr().accept(this);		
		ins.fieldName = (String)ctx.field().getText();		
		//TODO obj.$field statment is not supported yet.
		//ins.fieldNameVar = (String)ctx.field().accept(this);		
		ins.varName = var;

		Interval sourceInterval = ctx.getSourceInterval();
		
		int line = ctx.start.getLine();

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

		pushBlock(blockName,ctx);

		String conditionVar = (String) ctx.expr().accept(this);

		// goto FalseBegin
		GotoIf gotoFalse = new GotoIf(ctx.start.getLine());
		gotoFalse.expected = false;
		gotoFalse.actualVarName = conditionVar;
		gotoFalse.destinationLabel = blockName + "FalseBegin";

		addInstruction(gotoFalse);

		// true begin
		ctx.trueBranch().accept(this);

		// label
		NoOperation trueEnd = new NoOperation(ctx.start.getLine());
		trueEnd.label = blockName + "TrueEnd";
		addInstruction(trueEnd);

		// goto FalseBegin
		Goto gotoFalseEnd = new Goto(ctx.start.getLine());
		gotoFalseEnd.destinationLabel = blockName + "FalseEnd";
		addInstruction(gotoFalseEnd);

		// label
		NoOperation falseBegin = new NoOperation(ctx.start.getLine());
		falseBegin.label = blockName + "FalseBegin";
		addInstruction(falseBegin);

		if (ctx.falseBranch() != null)
			ctx.falseBranch().accept(this);

		// label
		NoOperation falseEnd = new NoOperation(ctx.start.getLine());
		falseEnd.label = blockName + "FalseEnd";
		addInstruction(falseEnd);

		popBlock(ctx);

		return null;
	}

	@Override
	public Object visitSendMessage(SendMessageContext ctx) {
		// System.out.println("visitSendMessage");
		String paramVar = null;

		// default
		ParameterStyle paramStyle = null;

		if (ctx.fieldList() != null) {

			// create the root parameter object
			CreateObject createBody = new CreateObject(ctx.start.getLine());
			createBody.objType = ObjectType.NORMAL;
			paramVar = getNextTempVar("paramFieldList");
			createBody.varName = paramVar;

			addInstruction(createBody);

			VarNameStack.push(paramVar);

			ctx.fieldList().accept(this);

			createBody.varName = paramVar;

			if (ctx.fieldList() instanceof OrderedParamListContext)
				paramStyle = ParameterStyle.ByOrder;
			else
				paramStyle = ParameterStyle.ByName;
		}

		SendMessage ins = new SendMessage(ctx.start.getLine());

		String tempVar = (String) ctx.expr().accept(this);

		ins.receiverVar = tempVar;
				
		ins.sync = (ctx.DOTDOT() == null);
		
		// only one child, either Id or String. return procedure name
		ins.messageSubjectVar = (String) ctx.messgeSubject().accept(this);
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

		// reset to 0.
		tempVarCount = 0;

		visitChildren(ctx);

		return null;
	}

	@Override
	public Object visitAssignment(AssignmentContext ctx) {
		// System.out.println("assignment");

		AssigneeContext assignee = ctx.assignee();
		if (assignee instanceof AssigneeVariableContext) {
			VariableAssignment ins = new VariableAssignment(ctx.start.getLine());
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

			FieldAssignment ins = new FieldAssignment(ctx.start.getLine());
			ins.ownerVar = tempVar;

			ins.fieldNameVar = (String) assigneeFieldContext.field().accept(
					this);

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

		pushBlock(blockName,ctx);

		NoOperation entry = new NoOperation(ctx.start.getLine());
		entry.label = blockName + "Entry";
		addInstruction(entry);

		String conditionVar = (String) ctx.expr().accept(this);

		// goto FalseBegin
		GotoIf gotoFalse = new GotoIf(ctx.start.getLine());
		gotoFalse.expected = false;
		gotoFalse.actualVarName = conditionVar;
		gotoFalse.destinationLabel = blockName + "Begin";

		addInstruction(gotoFalse);

		// true begin
		ctx.blockOrStatment().accept(this);

		// label
		NoOperation trueEnd = new NoOperation(ctx.start.getLine());
		trueEnd.label = blockName + "End";
		addInstruction(trueEnd);

		popBlock(ctx);

		return null;
	}

	@Override
	public Object visitString(StringContext ctx) {
		// System.out.println("visitString");

		CreateObject ins = new CreateObject(ctx.start.getLine());

		ins.varName = getNextTempVar("string");
		ins.objType = ObjectType.STRING;

		ins.value = StringEscapeUtils.unescapeJava(trimQuotationMarks(ctx
				.getText()));

		addInstruction(ins);
		return ins.varName;
	}

	protected String trimQuotationMarks(String s) {
		return s.substring(1, s.length() - 1);
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

		ProcedureEnd end = new ProcedureEnd(ctx.start.getLine());
		end.label = PROCEDURE_END_LABEL;

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

		VariableAssignment ins = new VariableAssignment(ctx.start.getLine());
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

		Goto ins = new Goto(ctx.start.getLine());
		ins.destinationLabel = block + "Entry";

		addInstruction(ins);

		return null;
	}

	@Override
	public Object visitBreakStatement(BreakStatementContext ctx) {
		String block = blockStack.peek();

		Goto ins = new Goto(ctx.start.getLine());
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
		CreateObject ins = new CreateObject(ctx.start.getLine());

		ins.varName = getNextTempVar("link");
		ins.objType = ObjectType.LINK;
		ins.value = ctx.getText();

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

		CreateObject ins = new CreateObject(ctx.start.getLine());

		ins.varName = getNextTempVar("link");
		ins.objType = ObjectType.PROCEDURE;
		ins.value = rawText;

		addInstruction(ins);
		return ins.varName;

	}

	@Override
	public Object visitStringField(StringFieldContext ctx) {

		String str = StringEscapeUtils.unescapeJava(trimQuotationMarks(ctx
				.getText()));

		CreateObject ins = new CreateObject(ctx.start.getLine());

		ins.varName = getNextTempVar("varField");
		ins.objType = ObjectType.STRING;
		ins.value = str;

		addInstruction(ins);
		return ins.varName;

	}

	@Override
	public Object visitStringMessageSubject(StringMessageSubjectContext ctx) {

		String str = StringEscapeUtils.unescapeJava(trimQuotationMarks(ctx
				.getText()));

		CreateObject ins = new CreateObject(ctx.start.getLine());

		ins.varName = getNextTempVar("varField");
		ins.objType = ObjectType.STRING;
		ins.value = str;

		addInstruction(ins);
		return ins.varName;

	}

	@Override
	public Object visitIdMessageSubject(IdMessageSubjectContext ctx) {
		CreateObject ins = new CreateObject(ctx.start.getLine());

		ins.varName = getNextTempVar("varField");
		ins.objType = ObjectType.STRING;
		ins.value = ctx.getText();

		addInstruction(ins);
		return ins.varName;

	}

	@Override
	public Object visitIdField(IdFieldContext ctx) {
		CreateObject ins = new CreateObject(ctx.start.getLine());

		ins.varName = getNextTempVar("varField");
		ins.objType = ObjectType.STRING;
		ins.value = ctx.getText();

		addInstruction(ins);
		return ins.varName;
	}

	@Override
	public Object visitVarField(VarFieldContext ctx) {

		/**
		 * return the varName
		 */

		return ctx.ID().getText();

	}

	@Override
	public Object visitVarMessageSubject(VarMessageSubjectContext ctx) {

		return ctx.ID().getText();
	}

	@Override
	public Object visitObjectConst(ObjectConstContext ctx) {

		return ctx.object().accept(this);
	}

	@Override
	public Object visitObject(ObjectContext ctx) {
		// create the root parameter object
		CreateObject createBody = new CreateObject(ctx.start.getLine());
		createBody.objType = ObjectType.NORMAL;
		createBody.varName = getNextTempVar("createObject");

		addInstruction(createBody);

		if (ctx.fieldList() != null) {
			VarNameStack.push(createBody.varName);
			ctx.fieldList().accept(this);
		}

		return createBody.varName;
	}

}
