package com.bluesky.visualprogramming.goo;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.goo.parser.GooParser.AccessFieldContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.Assign_opContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.AssigneeFieldContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.AssigneeVariableContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.AssignmentContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.BlockContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.BooleanContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.CommentContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ConstantExprContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.FieldContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ForStatementContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.HeaderContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.IfStatementContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.MessgeNameContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.NumberContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ParamDeclareListContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ParamListContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ProcedureContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.ReturnStatementContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.SendMessageContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.StatementContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.StringContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.VariableContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.VariableExprContext;
import com.bluesky.visualprogramming.goo.parser.GooParser.WhileStatementContext;
import com.bluesky.visualprogramming.goo.parser.GooVisitor;
import com.bluesky.visualprogramming.vm.instruction.Instruction;

/**
 * convert goo code to VM instructions
 * 
 * @author jackding
 * 
 */
public class GooCompiler implements GooVisitor<_Object> {

	private List<Instruction> instructions = new ArrayList<Instruction>();

	@Override
	public _Object visit(@NotNull ParseTree tree) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitChildren(@NotNull RuleNode node) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
	public _Object visitParamList(ParamListContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitBlock(BlockContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitBoolean(BooleanContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitAccessField(AccessFieldContext ctx) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public _Object visitHeader(HeaderContext ctx) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitStatement(StatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitAssignment(AssignmentContext ctx) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitComment(CommentContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitProcedure(ProcedureContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitConstantExpr(ConstantExprContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitAssigneeField(AssigneeFieldContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitVariableExpr(VariableExprContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitAssigneeVariable(AssigneeVariableContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public _Object visitAssign_op(Assign_opContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}

}
