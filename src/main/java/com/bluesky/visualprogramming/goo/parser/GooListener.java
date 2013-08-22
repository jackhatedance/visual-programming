// Generated from Goo.g4 by ANTLR 4.1
package com.bluesky.visualprogramming.goo.parser;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GooParser}.
 */
public interface GooListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GooParser#nameValue}.
	 * @param ctx the parse tree
	 */
	void enterNameValue(@NotNull GooParser.NameValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#nameValue}.
	 * @param ctx the parse tree
	 */
	void exitNameValue(@NotNull GooParser.NameValueContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(@NotNull GooParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(@NotNull GooParser.ForStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#trueBlock}.
	 * @param ctx the parse tree
	 */
	void enterTrueBlock(@NotNull GooParser.TrueBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#trueBlock}.
	 * @param ctx the parse tree
	 */
	void exitTrueBlock(@NotNull GooParser.TrueBlockContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(@NotNull GooParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(@NotNull GooParser.ExpressionStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(@NotNull GooParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(@NotNull GooParser.ReturnStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(@NotNull GooParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(@NotNull GooParser.BlockContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#paramDeclareList}.
	 * @param ctx the parse tree
	 */
	void enterParamDeclareList(@NotNull GooParser.ParamDeclareListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#paramDeclareList}.
	 * @param ctx the parse tree
	 */
	void exitParamDeclareList(@NotNull GooParser.ParamDeclareListContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#falseBlock}.
	 * @param ctx the parse tree
	 */
	void enterFalseBlock(@NotNull GooParser.FalseBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#falseBlock}.
	 * @param ctx the parse tree
	 */
	void exitFalseBlock(@NotNull GooParser.FalseBlockContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#forAfterthought}.
	 * @param ctx the parse tree
	 */
	void enterForAfterthought(@NotNull GooParser.ForAfterthoughtContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#forAfterthought}.
	 * @param ctx the parse tree
	 */
	void exitForAfterthought(@NotNull GooParser.ForAfterthoughtContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#procedure}.
	 * @param ctx the parse tree
	 */
	void enterProcedure(@NotNull GooParser.ProcedureContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#procedure}.
	 * @param ctx the parse tree
	 */
	void exitProcedure(@NotNull GooParser.ProcedureContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#ConstantExpr}.
	 * @param ctx the parse tree
	 */
	void enterConstantExpr(@NotNull GooParser.ConstantExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#ConstantExpr}.
	 * @param ctx the parse tree
	 */
	void exitConstantExpr(@NotNull GooParser.ConstantExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#namedParamList}.
	 * @param ctx the parse tree
	 */
	void enterNamedParamList(@NotNull GooParser.NamedParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#namedParamList}.
	 * @param ctx the parse tree
	 */
	void exitNamedParamList(@NotNull GooParser.NamedParamListContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#AssigneeField}.
	 * @param ctx the parse tree
	 */
	void enterAssigneeField(@NotNull GooParser.AssigneeFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#AssigneeField}.
	 * @param ctx the parse tree
	 */
	void exitAssigneeField(@NotNull GooParser.AssigneeFieldContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#VariableExpr}.
	 * @param ctx the parse tree
	 */
	void enterVariableExpr(@NotNull GooParser.VariableExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#VariableExpr}.
	 * @param ctx the parse tree
	 */
	void exitVariableExpr(@NotNull GooParser.VariableExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#Number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(@NotNull GooParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#Number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(@NotNull GooParser.NumberContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#RefAssignOperator}.
	 * @param ctx the parse tree
	 */
	void enterRefAssignOperator(@NotNull GooParser.RefAssignOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#RefAssignOperator}.
	 * @param ctx the parse tree
	 */
	void exitRefAssignOperator(@NotNull GooParser.RefAssignOperatorContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#SendMessage}.
	 * @param ctx the parse tree
	 */
	void enterSendMessage(@NotNull GooParser.SendMessageContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#SendMessage}.
	 * @param ctx the parse tree
	 */
	void exitSendMessage(@NotNull GooParser.SendMessageContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#messgeName}.
	 * @param ctx the parse tree
	 */
	void enterMessgeName(@NotNull GooParser.MessgeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#messgeName}.
	 * @param ctx the parse tree
	 */
	void exitMessgeName(@NotNull GooParser.MessgeNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#AutoAssignOperator}.
	 * @param ctx the parse tree
	 */
	void enterAutoAssignOperator(@NotNull GooParser.AutoAssignOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#AutoAssignOperator}.
	 * @param ctx the parse tree
	 */
	void exitAutoAssignOperator(@NotNull GooParser.AutoAssignOperatorContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#forCondition}.
	 * @param ctx the parse tree
	 */
	void enterForCondition(@NotNull GooParser.ForConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#forCondition}.
	 * @param ctx the parse tree
	 */
	void exitForCondition(@NotNull GooParser.ForConditionContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(@NotNull GooParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(@NotNull GooParser.ContinueStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#Boolean}.
	 * @param ctx the parse tree
	 */
	void enterBoolean(@NotNull GooParser.BooleanContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#Boolean}.
	 * @param ctx the parse tree
	 */
	void exitBoolean(@NotNull GooParser.BooleanContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#AccessField}.
	 * @param ctx the parse tree
	 */
	void enterAccessField(@NotNull GooParser.AccessFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#AccessField}.
	 * @param ctx the parse tree
	 */
	void exitAccessField(@NotNull GooParser.AccessFieldContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#header}.
	 * @param ctx the parse tree
	 */
	void enterHeader(@NotNull GooParser.HeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#header}.
	 * @param ctx the parse tree
	 */
	void exitHeader(@NotNull GooParser.HeaderContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(@NotNull GooParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(@NotNull GooParser.IfStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#OwnAssignOperator}.
	 * @param ctx the parse tree
	 */
	void enterOwnAssignOperator(@NotNull GooParser.OwnAssignOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#OwnAssignOperator}.
	 * @param ctx the parse tree
	 */
	void exitOwnAssignOperator(@NotNull GooParser.OwnAssignOperatorContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(@NotNull GooParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(@NotNull GooParser.StatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#field}.
	 * @param ctx the parse tree
	 */
	void enterField(@NotNull GooParser.FieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#field}.
	 * @param ctx the parse tree
	 */
	void exitField(@NotNull GooParser.FieldContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(@NotNull GooParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(@NotNull GooParser.AssignmentContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#orderedParamList}.
	 * @param ctx the parse tree
	 */
	void enterOrderedParamList(@NotNull GooParser.OrderedParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#orderedParamList}.
	 * @param ctx the parse tree
	 */
	void exitOrderedParamList(@NotNull GooParser.OrderedParamListContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(@NotNull GooParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(@NotNull GooParser.WhileStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#String}.
	 * @param ctx the parse tree
	 */
	void enterString(@NotNull GooParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#String}.
	 * @param ctx the parse tree
	 */
	void exitString(@NotNull GooParser.StringContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(@NotNull GooParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(@NotNull GooParser.BreakStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInit(@NotNull GooParser.ForInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInit(@NotNull GooParser.ForInitContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#comment}.
	 * @param ctx the parse tree
	 */
	void enterComment(@NotNull GooParser.CommentContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#comment}.
	 * @param ctx the parse tree
	 */
	void exitComment(@NotNull GooParser.CommentContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(@NotNull GooParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(@NotNull GooParser.VariableContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#AssigneeVariable}.
	 * @param ctx the parse tree
	 */
	void enterAssigneeVariable(@NotNull GooParser.AssigneeVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#AssigneeVariable}.
	 * @param ctx the parse tree
	 */
	void exitAssigneeVariable(@NotNull GooParser.AssigneeVariableContext ctx);
}