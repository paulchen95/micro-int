package com.chen.mint.Nodes;

import java.util.List;

import com.chen.mint.Token;
import com.chen.mint.TokenType;

public class OpNode extends Node{
    
    public OpNode(){}

    // Need to account for 3 + 5, but also 3 + 5 + 7? what about 3 + (9 + 2)?
    public OpNode(List<Token> input){

        // Case 1: ExpNode Paran
        if(input.get(0).type.equals(TokenType.LEFT_PARAN)){
            int another = 0;        // Tracks multiple left parans
            int i = 1;              // Tracks paran length
            while(true){
                // Nested Left paran
                if(input.get(i).type.equals(TokenType.LEFT_PARAN)){
                    another++;
                }
                // Right paran belonging to nested paran
                else if(input.get(i).type.equals(TokenType.RIGHT_PARAN) && another > 0){
                    another--;
                }
                // Sublist paran portion to new ExpNode
                else if(input.get(i).type.equals(TokenType.RIGHT_PARAN) && another == 0){
                    this.left = new ExpNode(input.subList(0, i+1));
                    break;
                }
                i++;
            }
            i = 0;
            if(input.get(i).type.equals(TokenType.STAR)){
                type = TokenType.STAR;
            }
            else if(input.get(i).type.equals(TokenType.PLUS)){
                type = TokenType.PLUS;
            }

            input.remove(0);            // remove Operation
            this.right = new ExpNode(input.subList(0, input.size()));
            
        }
        // Case 2: Single ExpNode
        else{
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
}
