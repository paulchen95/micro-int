package com.chen.mint.Nodes;

import java.util.List;

import com.chen.mint.Token;
import com.chen.mint.TokenType;

public class ViewNode extends Node{
    
    public ViewNode(){}
    
    public ViewNode(List<Token> input){
        if(input.get(0).type.equals(TokenType.VIEW)){
            input.remove(0);
            this.left = new ExpNode(input);
        }
    }
}
