package com.chen.mint;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    public static List<Token> tokenize(String line) {       // One line
        ArrayList<Token> tokenized = new ArrayList<Token>();
        int i = 0;
        while(i < line.length()){
            //char current = line.charAt(i);
            if(line.substring(i,i+3) == "let"){
                tokenized.add(new Token());
                i += 3;
            }

        }
        return new ArrayList<Token>();
    }
}
