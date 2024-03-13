package com.chen.mint.Nodes;

import java.util.List;

import com.chen.mint.NodeType;
import com.chen.mint.Token;
import com.chen.mint.TokenType;

public class ExpNode extends Node{
    
    public ExpNode(){}

    // Assuming whole input is EXP
    public ExpNode(List<Token> input) {
        // Case 1: NUM or Var
        if(input.size() == 1){
            if(input.get(0).type.equals(TokenType.NUM)){
                NumNode newNum = new NumNode(input);
                this.left = newNum;
            }
            else if(input.get(0).type.equals(TokenType.VAR)){
                VarNode newVar = new VarNode(input);
                this.left = newVar;
            }
        }

        // Case 2: OP or ()
        else{

            // Case 2a: LEFT_PARAN
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
                        input.remove(i);
                        input.remove(0);
                        List<Token> newList = input.subList(0, i-1);
                        if (isOpNode(newList)) {
                            this.left = new OpNode(newList);
                        } else {
                            //make a new Exp Node
                            ExpNode newExp = new ExpNode(newList);
                            //clone back to this one
                            this.left = newExp.left;
                            this.right = newExp.right;
                        }
                        break;
                    }
                    i++;
                }
            }
            // Case 2b: OP with VAR or NUM
            else if(input.get(1).type.equals(TokenType.STAR) || input.get(1).type.equals(TokenType.PLUS)){
                this.left = new OpNode(input);
            }

        }
    }

    private boolean isOpNode(List<Token> input) {
        int nestedParan = 0;        // Tracks multiple left parans
        int i = 0;              // Tracks paran length
        while(i < input.size()){
            // Nested Left paran
            if(input.get(i).type.equals(TokenType.LEFT_PARAN)){
                nestedParan++;
            }
            // Right paran belonging to nested paran
            else if(input.get(i).type.equals(TokenType.RIGHT_PARAN)){
                nestedParan--;
            }
            if((nestedParan==0) && 
               (input.get(i).type.equals(TokenType.STAR) || input.get(i).type.equals(TokenType.PLUS))) {
                return true;
            }
            i++;
        }
        return false;
    }
}
