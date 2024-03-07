package com.chen.mint.Nodes;

import java.util.List;

import com.chen.mint.Token;
import com.chen.mint.TokenType;

// Leaf Class
public class NumNode extends Node {
    
    public NumNode(List<Token> input){
        if(input.get(0).type.equals(TokenType.NUM)){
            this.num = input.get(0).number;
            this.left = null;
            this.right = null;
            input.remove(0);
        }
        else{
            System.out.println("NumNode creation failed: Expected NUM Token from first element in input");
        }
    }
}
