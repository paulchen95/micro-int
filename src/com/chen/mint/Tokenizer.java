package com.chen.mint;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    public static boolean check(String s) {                         // Checks if string is made up of letters
        if (s == null){
           return false;
        }
        int len = s.length();
        for(int i = 0; i < len; i++) {
           if ((Character.isLetter(s.charAt(i)) == false)) {
              return false;
           }
        }
        return true;
     }


    public static List<Token> tokenize(String line) {               // One line
        ArrayList<Token> tokenized = new ArrayList<Token>();

        int i = 0;
        while(i < line.length()){
            //char current = line.charAt(i);
            if(line.substring(i,i+3) == "let"){                     // Let Token
                tokenized.add(new Token(TokenType.LET));
                i += 3;
            }
            else if(line.substring(i,i+4) == "view"){               // View Token
                tokenized.add(new Token(TokenType.VIEW));
                i += 4;
            }
            else if(check(line.substring(i,i+1))){                  // Var Token
                String letters = "";
                while(check(line.substring(i,i+1))){
                    letters += line.substring(i,i+1);
                    i++;
                }                  
                tokenized.add(new Token(TokenType.VAR, letters));
            }

            else if(Character.isDigit(line.charAt(i))){              // Num Token
                String numbers = "";
                while(Character.isDigit(line.charAt(i))){
                    numbers += line.substring(i,i+1);
                    i++;
                }
                int num = Integer.parseInt(numbers);                  
                tokenized.add(new Token(TokenType.VAR, num));
            }

            else if(line.substring(i,i+1) == "="){                  // Equal Token
                tokenized.add(new Token(TokenType.EQUAL));
                i += 1;
            }
            else if(line.substring(i,i+1) == "("){                  // Left Token
                tokenized.add(new Token(TokenType.LEFT_PARAN));
                i += 1;
            }
            else if(line.substring(i,i+1) == ")"){                  // Right Token
                tokenized.add(new Token(TokenType.RIGHT_PARAN));
                i += 1;
            }
            else if(line.substring(i,i+1) == "+"){                  // Plus Token
                tokenized.add(new Token(TokenType.PLUS));
                i += 1;
            }
            else if(line.substring(i,i+1) == "*"){                  // Star Token
                tokenized.add(new Token(TokenType.STAR));
                i += 1;
            }

        }
        return tokenized;
    }
}
