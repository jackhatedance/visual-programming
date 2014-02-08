package com.bluesky.visualprogramming.dialect.goo;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class DescriptiveErrorListener extends BaseErrorListener {
    public static DescriptiveErrorListener INSTANCE = new DescriptiveErrorListener();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e)
    {
//        if (!REPORT_SYNTAX_ERRORS) {
//            return;
//        }

        String sourceName = recognizer.getInputStream().getSourceName();
        if (sourceName!=null) {
            sourceName = String.format("%s:%d:%d: ", sourceName, line, charPositionInLine);
        }

        StringBuilder sb = new StringBuilder();
        if(sourceName!=null)
        	sb.append(sourceName);
        
         sb.append("line "+line+":"+charPositionInLine+" "+msg);
         
         String errMsg = sb.toString();
        //System.err.println(errMsg);
        throw new RuntimeException(errMsg);
    }
}
