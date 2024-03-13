package com.chen.mint.Nodes;
import com.chen.mint.TokenType;


public abstract class Node {
    public String varName;
    public int num;
    public Node left;
    public Node right;
    public TokenType type;

    public abstract void eval();

}
