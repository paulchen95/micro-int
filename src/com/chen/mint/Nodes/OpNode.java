package com.chen.mint.Nodes;

import java.util.List;

import com.chen.mint.Token;
import com.chen.mint.TokenType;

public class OpNode extends Node{
    
    public OpNode(){}

    // Need to account for 3 + 5, but also 3 + 5 + 7? what about 3 + (9 + 2)?
    public OpNode(List<Token> input){

        // Set Operation
        if(input.get(1).type.equals(TokenType.STAR)){
            type = TokenType.STAR;
        }
        else if(input.get(1).type.equals(TokenType.PLUS)){
            type = TokenType.PLUS;
        }
        
        this.left = new ExpNode(input.subList(0,1));
        input.remove(0);            // remove Operation
        this.right = new ExpNode(input.subList(0, input.size()));

    }
}
