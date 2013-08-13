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
	 * Visit a parse tree produced by {@link GooParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(@NotNull GooParser.ForStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#Variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(@NotNull GooParser.VariableContext ctx);

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
	 * Visit a parse tree produced by {@link GooParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(@NotNull GooParser.ReturnStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(@NotNull GooParser.ParamListContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(@NotNull GooParser.BlockContext ctx);

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
	 * Visit a parse tree produced by {@link GooParser#field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField(@NotNull GooParser.FieldContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(@NotNull GooParser.StatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#sendMessage}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSendMessage(@NotNull GooParser.SendMessageContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(@NotNull GooParser.AssignmentContext ctx);

	/**
	 * Visit a parse tree produced by {@link GooParser#paramDeclareList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamDeclareList(@NotNull GooParser.ParamDeclareListContext ctx);

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
	 * Visit a parse tree produced by {@link GooParser#procedure}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedure(@NotNull GooParser.ProcedureContext ctx);
}