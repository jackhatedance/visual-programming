// Generated from Goo.g4 by ANTLR 4.1
package com.bluesky.visualprogramming.goo.parser;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GooParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GooVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GooParser#nameValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNameValue(@NotNull GooParser.NameValueContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(@NotNull GooParser.ForStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(@NotNull GooParser.ExpressionStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#link}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLink(@NotNull GooParser.LinkContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(@NotNull GooParser.ReturnStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(@NotNull GooParser.BlockContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#falseBranch}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseBranch(@NotNull GooParser.FalseBranchContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#blockOrStatment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockOrStatment(@NotNull GooParser.BlockOrStatmentContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#paramDeclareList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamDeclareList(@NotNull GooParser.ParamDeclareListContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#forAfterthought}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForAfterthought(@NotNull GooParser.ForAfterthoughtContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#procedure}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedure(@NotNull GooParser.ProcedureContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#namedParamList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedParamList(@NotNull GooParser.NamedParamListContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#ConstantExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantExpr(@NotNull GooParser.ConstantExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#trueBranch}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueBranch(@NotNull GooParser.TrueBranchContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#AssigneeField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssigneeField(@NotNull GooParser.AssigneeFieldContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#VariableExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableExpr(@NotNull GooParser.VariableExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#Number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(@NotNull GooParser.NumberContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#messgeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMessgeName(@NotNull GooParser.MessgeNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#RefAssignOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRefAssignOperator(@NotNull GooParser.RefAssignOperatorContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#SendMessage}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSendMessage(@NotNull GooParser.SendMessageContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#NullValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullValue(@NotNull GooParser.NullValueContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#AutoAssignOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAutoAssignOperator(@NotNull GooParser.AutoAssignOperatorContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#forCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForCondition(@NotNull GooParser.ForConditionContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#continueStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStatement(@NotNull GooParser.ContinueStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#Boolean}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean(@NotNull GooParser.BooleanContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#AccessField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccessField(@NotNull GooParser.AccessFieldContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#assignmentStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStatement(@NotNull GooParser.AssignmentStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#header}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeader(@NotNull GooParser.HeaderContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(@NotNull GooParser.IfStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#emptyStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyStatement(@NotNull GooParser.EmptyStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField(@NotNull GooParser.FieldContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#OwnAssignOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOwnAssignOperator(@NotNull GooParser.OwnAssignOperatorContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(@NotNull GooParser.StatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#ObjectLink}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectLink(@NotNull GooParser.ObjectLinkContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(@NotNull GooParser.AssignmentContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#orderedParamList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrderedParamList(@NotNull GooParser.OrderedParamListContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(@NotNull GooParser.WhileStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#String}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(@NotNull GooParser.StringContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(@NotNull GooParser.BreakStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(@NotNull GooParser.ForInitContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#comment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComment(@NotNull GooParser.CommentContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(@NotNull GooParser.VariableContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#AssigneeVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssigneeVariable(@NotNull GooParser.AssigneeVariableContext ctx);
}