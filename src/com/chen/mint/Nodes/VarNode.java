package com.chen.mint.Nodes;

import java.util.List;

import com.chen.mint.Token;
import com.chen.mint.TokenType;
import com.chen.mint.VarStore;

// Leaf Class
public class VarNode extends Node {
    
    public VarNode(){}
    // Leaf Class
    public VarNode(List<Token> input){
        if(input.get(0).type.equals(TokenType.VAR)){
            this.varName = input.get(0).name;
            this.left = null;
            this.right = null;
            input.remove(0);
        }
        else{
            System.out.println("VarNode creation failed: Expected VAR Token from first element in input");
        }
    }
    @Override
    public void eval() {
        this.num = VarStore.getInstance().get(varName);
        
    }
}   
