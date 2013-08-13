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
	 * Enter a parse tree produced by {@link GooParser#Variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(@NotNull GooParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#Variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(@NotNull GooParser.VariableContext ctx);

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
	 * Enter a parse tree produced by {@link GooParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(@NotNull GooParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(@NotNull GooParser.ParamListContext ctx);

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
	 * Enter a parse tree produced by {@link GooParser#sendMessage}.
	 * @param ctx the parse tree
	 */
	void enterSendMessage(@NotNull GooParser.SendMessageContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#sendMessage}.
	 * @param ctx the parse tree
	 */
	void exitSendMessage(@NotNull GooParser.SendMessageContext ctx);

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
	 * Enter a parse tree produced by {@link GooParser#procedure}.
	 * @param ctx the parse tree
	 */
	void enterProcedure(@NotNull GooParser.ProcedureContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#procedure}.
	 * @param ctx the parse tree
	 */
	void exitProcedure(@NotNull GooParser.ProcedureContext ctx);
}