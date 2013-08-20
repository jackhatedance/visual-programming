/*
	generic object-oriented programming language
	
	a sample procedure:
	
	rtn = foo.bar()
	a.b.c().d().e.f()
	a
	a.b
	a.b()
	callback = foo:bar()
*/
grammar Goo;

//this language don't support class

procedure : header block;	

header : PROCEDURE ID '(' paramDeclareList? ')';	

paramDeclareList : ID (',' ID)*;

block : '{' statement* '}';

statement : 
          assignment ';'       
	| expressionStatement            
        | ifStatement     
        | whileStatement 
        | forStatement   
        | returnStatement   
        | breakStatement    
        | continueStatement  
        | comment           
        | ';'               
	;
	
//varDeclare : 'var' varList;
//varList : ID (',' ID)*;
expressionStatement : expr ';';                     
assignment : assignee assignOperator expr;

assignOperator : REF_ASSIGN  #RefAssignOperator
          | OWN_ASSIGN      #OwnAssignOperator
          | AUTO_ASSIGN     #AutoAssignOperator
          ;


assignee : ID		#AssigneeVariable
	| expr DOT field	#AssigneeField
	;

expr: constant				#ConstantExpr
    | variable                          #VariableExpr
    | expr DOT field              #AccessField
    | expr DOT messgeName '(' paramList? ')' #SendMessage
    ;

constant : NUMBER			#Number
	| STRING			#String
	| BOOLEAN                      #Boolean 
         ;
variable : ID;
field   :   ID;
messgeName : ID;

paramList : expr (',' expr)*            #orderedParamList  
          | nameValue (',' nameValue)*  #namedParamList
          ;

nameValue : ID ':' expr;


ifStatement : IF '(' expr ')' trueBlock (ELSE falseBlock)?;
trueBlock : block;
falseBlock: block;

whileStatement : WHILE '(' expr ')' block;
forStatement : FOR '(' forInit ';' forCondition ';' forAfterthought ')' block;


forInit: assignment|expr;
forCondition : expr;
forAfterthought : expr;

returnStatement : RETURN expr ';';

breakStatement : BREAK ';';
continueStatement : CONTINUE ';';
                   
comment : LINE_COMMENT
        | BLOCK_COMMENT;




PROCEDURE : 'procedure';
    
IF : 'if';
ELSE:'else';
WHILE : 'while';
FOR : 'for';
RETURN : 'return';

BOOLEAN : 'true' | 'false';
BREAK: 'break';
CONTINUE : 'continue';

DOT : '.';
          
LINE_COMMENT : '//' .*? '\r'? '\n';
BLOCK_COMMENT : '/*' .*? '*/';


REF_ASSIGN : '->';
OWN_ASSIGN : '=>';
AUTO_ASSIGN: '='| '~>';
    
    
    
ID : [a-zA-Z][a-zA-Z0-9]*;

NUMBER : [0-9]+
	| [0-9]+ '.' [0-9]+;
	
STRING : '"' ('\\"'|~["])* '"';	

WS : [ \t\n\r]+ -> skip ;
	
	