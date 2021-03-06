// Generated from Goo.g4 by ANTLR 4.1
package com.bluesky.visualprogramming.dialect.goo.parser;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GooParser}.
 */
public interface GooListener extends ParseTreeListener {
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
	 * Enter a parse tree produced by {@link GooParser#ObjectConst}.
	 * @param ctx the parse tree
	 */
	void enterObjectConst(@NotNull GooParser.ObjectConstContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#ObjectConst}.
	 * @param ctx the parse tree
	 */
	void exitObjectConst(@NotNull GooParser.ObjectConstContext ctx);

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
	 * Enter a parse tree produced by {@link GooParser#VarMessageSubject}.
	 * @param ctx the parse tree
	 */
	void enterVarMessageSubject(@NotNull GooParser.VarMessageSubjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#VarMessageSubject}.
	 * @param ctx the parse tree
	 */
	void exitVarMessageSubject(@NotNull GooParser.VarMessageSubjectContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#VarField}.
	 * @param ctx the parse tree
	 */
	void enterVarField(@NotNull GooParser.VarFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#VarField}.
	 * @param ctx the parse tree
	 */
	void exitVarField(@NotNull GooParser.VarFieldContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#IdMessageSubject}.
	 * @param ctx the parse tree
	 */
	void enterIdMessageSubject(@NotNull GooParser.IdMessageSubjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#IdMessageSubject}.
	 * @param ctx the parse tree
	 */
	void exitIdMessageSubject(@NotNull GooParser.IdMessageSubjectContext ctx);

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
	 * Enter a parse tree produced by {@link GooParser#emptyStatement}.
	 * @param ctx the parse tree
	 */
	void enterEmptyStatement(@NotNull GooParser.EmptyStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#emptyStatement}.
	 * @param ctx the parse tree
	 */
	void exitEmptyStatement(@NotNull GooParser.EmptyStatementContext ctx);

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
	 * Enter a parse tree produced by {@link GooParser#replySubject}.
	 * @param ctx the parse tree
	 */
	void enterReplySubject(@NotNull GooParser.ReplySubjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#replySubject}.
	 * @param ctx the parse tree
	 */
	void exitReplySubject(@NotNull GooParser.ReplySubjectContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#ObjectLink}.
	 * @param ctx the parse tree
	 */
	void enterObjectLink(@NotNull GooParser.ObjectLinkContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#ObjectLink}.
	 * @param ctx the parse tree
	 */
	void exitObjectLink(@NotNull GooParser.ObjectLinkContext ctx);

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
	 * Enter a parse tree produced by {@link GooParser#link}.
	 * @param ctx the parse tree
	 */
	void enterLink(@NotNull GooParser.LinkContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#link}.
	 * @param ctx the parse tree
	 */
	void exitLink(@NotNull GooParser.LinkContext ctx);

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
	 * Enter a parse tree produced by {@link GooParser#object}.
	 * @param ctx the parse tree
	 */
	void enterObject(@NotNull GooParser.ObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#object}.
	 * @param ctx the parse tree
	 */
	void exitObject(@NotNull GooParser.ObjectContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#falseBranch}.
	 * @param ctx the parse tree
	 */
	void enterFalseBranch(@NotNull GooParser.FalseBranchContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#falseBranch}.
	 * @param ctx the parse tree
	 */
	void exitFalseBranch(@NotNull GooParser.FalseBranchContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#blockOrStatment}.
	 * @param ctx the parse tree
	 */
	void enterBlockOrStatment(@NotNull GooParser.BlockOrStatmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#blockOrStatment}.
	 * @param ctx the parse tree
	 */
	void exitBlockOrStatment(@NotNull GooParser.BlockOrStatmentContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#StringField}.
	 * @param ctx the parse tree
	 */
	void enterStringField(@NotNull GooParser.StringFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#StringField}.
	 * @param ctx the parse tree
	 */
	void exitStringField(@NotNull GooParser.StringFieldContext ctx);

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
	 * Enter a parse tree produced by {@link GooParser#StringMessageSubject}.
	 * @param ctx the parse tree
	 */
	void enterStringMessageSubject(@NotNull GooParser.StringMessageSubjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#StringMessageSubject}.
	 * @param ctx the parse tree
	 */
	void exitStringMessageSubject(@NotNull GooParser.StringMessageSubjectContext ctx);

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
	 * Enter a parse tree produced by {@link GooParser#trueBranch}.
	 * @param ctx the parse tree
	 */
	void enterTrueBranch(@NotNull GooParser.TrueBranchContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#trueBranch}.
	 * @param ctx the parse tree
	 */
	void exitTrueBranch(@NotNull GooParser.TrueBranchContext ctx);

	/**
	 * Enter a parse tree produced by {@link GooParser#NullValue}.
	 * @param ctx the parse tree
	 */
	void enterNullValue(@NotNull GooParser.NullValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#NullValue}.
	 * @param ctx the parse tree
	 */
	void exitNullValue(@NotNull GooParser.NullValueContext ctx);

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
	 * Enter a parse tree produced by {@link GooParser#IdField}.
	 * @param ctx the parse tree
	 */
	void enterIdField(@NotNull GooParser.IdFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#IdField}.
	 * @param ctx the parse tree
	 */
	void exitIdField(@NotNull GooParser.IdFieldContext ctx);

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
	 * Enter a parse tree produced by {@link GooParser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(@NotNull GooParser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(@NotNull GooParser.AssignmentStatementContext ctx);

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
	 * Enter a parse tree produced by {@link GooParser#ProcedureConst}.
	 * @param ctx the parse tree
	 */
	void enterProcedureConst(@NotNull GooParser.ProcedureConstContext ctx);
	/**
	 * Exit a parse tree produced by {@link GooParser#ProcedureConst}.
	 * @param ctx the parse tree
	 */
	void exitProcedureConst(@NotNull GooParser.ProcedureConstContext ctx);

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