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

statement 
         : assignment ';'       
	| expr ';'             
        |  ifStatement     
        |   whileStatement 
        |   forStatement   
        |   returnStatement ';'  
        |   BREAK ';'   
        |   CONTINUE ';' 
        | comment           
        | ';'               
	;
	
//varDeclare : 'var' varList;
//varList : ID (',' ID)*;
                     
assignment : assignee '=' expr ;

assignee : ID		#AssigneeVariable
	| expr DOT field	#AssigneeField
	;

expr: constant				#Constant
	| expr DOT field              #AccessField
    | expr DOT messgeName '(' paramList? ')' #sendMessage
    ;

constant :ID                                #Variable
	| NUMBER			#Number
	| STRING			#String
	| BOOLEAN                      #Boolean 



field   :   ID;
messgeName : ID;

paramList : expr (',' expr)*;

ifStatement : IF '(' expr ')' block (ELSE block)?;
whileStatement : WHILE '(' expr ')' block;
forStatement : FOR '(' (assignment|expr)';'expr ';' expr ')' block;
returnStatement : RETURN expr;

                   
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


ID : [a-zA-Z][a-zA-Z0-9]*;

NUMBER : [0-9]+
	| [0-9]+ '.' [0-9]+;
	
STRING : '"' ('\\"'|~["])* '"';	

WS : [ \t\n\r]+ -> skip ;
	
	