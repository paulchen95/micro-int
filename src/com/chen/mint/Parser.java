package com.chen.mint;

import java.util.List;

import com.chen.mint.Nodes.LetNode;
import com.chen.mint.Nodes.Node;
import com.chen.mint.Nodes.ViewNode;

public class Parser {
    public static Node parse(List<Token> tokens) {
        if(tokens.get(0).type.equals(TokenType.LET)){
            return new LetNode(tokens);
        }
        else if(tokens.get(0).type.equals(TokenType.VIEW)){
            return new ViewNode(tokens);
        }
        else{
            return null;
        }
    }
}
