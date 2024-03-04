package com.chen.mint;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    public static List<Token> tokenize(String line) {
        ArrayList<Token> tokenized = new ArrayList<Token>();

        int i = 0;
        while(i < line.length()){

            // Let 
            if(i+3 < line.length() && line.substring(i,i+3).equals("let")){                     
                tokenized.add(new Token(TokenType.LET));
                i += 3;
            }
            // View
            else if(i+4 < line.length() && line.substring(i,i+4).equals("view")){             
                tokenized.add(new Token(TokenType.VIEW));
                i += 4;
            }
            // Num
            else if(Character.isDigit(line.charAt(i))){                          
                String numbers = "";    
                while(i < line.length() && Character.isDigit(line.charAt(i))){
                    numbers += line.charAt(i);
                    i++;
                }
                int num = Integer.parseInt(numbers);                  
                tokenized.add(new Token(TokenType.NUM, num));
            }
            // Var
            else if(Character.isLetter(line.charAt(i))){                                              
                String letters = "";
                while(i < line.length() && Character.isLetter(line.charAt(i))){
                    letters += line.substring(i,i+1);
                    i++;
                }                  
                tokenized.add(new Token(TokenType.VAR, letters));
            }
            // Equal
            else if(line.substring(i,i+1).equals("=")){              
                tokenized.add(new Token(TokenType.EQUAL));
                i += 1;
            }
            // Left Paran
            else if(line.substring(i,i+1).equals("(")){           
                tokenized.add(new Token(TokenType.LEFT_PARAN));
                i += 1;
            }
            // Right Paran
            else if(line.substring(i,i+1).equals(")")){          
                tokenized.add(new Token(TokenType.RIGHT_PARAN));
                i += 1;
            }
            // Plus
            else if(line.substring(i,i+1).equals("+")){          
                tokenized.add(new Token(TokenType.PLUS));
                i += 1;
            }
            // Star
            else if(line.substring(i,i+1).equals("*")){     
                tokenized.add(new Token(TokenType.STAR));
                i += 1;
            }
            // Space
            else{
                i++;
            }

        }
        return tokenized;
    }
}
