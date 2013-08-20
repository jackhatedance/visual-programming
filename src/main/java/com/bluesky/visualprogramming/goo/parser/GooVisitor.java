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
	 * Visit a parse tree produced by {@link GooParser#trueBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueBlock(@NotNull GooParser.TrueBlockContext ctx);

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
	 * Visit a parse tree produced by {@link GooParser#paramDeclareList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamDeclareList(@NotNull GooParser.ParamDeclareListContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#falseBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseBlock(@NotNull GooParser.FalseBlockContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#procedure}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedure(@NotNull GooParser.ProcedureContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#ConstantExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantExpr(@NotNull GooParser.ConstantExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#namedParamList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedParamList(@NotNull GooParser.NamedParamListContext ctx);

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
	 * Visit a parse tree produced by {@link GooParser#messgeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMessgeName(@NotNull GooParser.MessgeNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#AutoAssignOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAutoAssignOperator(@NotNull GooParser.AutoAssignOperatorContext ctx);

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
	 * Visit a parse tree produced by {@link GooParser#field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField(@NotNull GooParser.FieldContext ctx);

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