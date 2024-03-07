package com.chen.mint.Nodes;

import java.util.List;

import com.chen.mint.NodeType;
import com.chen.mint.Token;
import com.chen.mint.TokenType;

public class LetNode extends Node {

    // Always left child is VarNode and right is ExpNode
    public LetNode(List<Token> input){
        if(input.get(0).type.equals(TokenType.LET) && input.get(1).type.equals(TokenType.VAR)){
            input.remove(0);
            this.left = new VarNode(input);
            input.remove(0); // Equals
            this.right = new ExpNode(input);
        }

    }
}
