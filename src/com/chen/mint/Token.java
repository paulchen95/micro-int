package com.chen.mint;

public class Token {
    TokenType type;
    int number;
    String name;

    public Token(TokenType type) {
        this.type = type;
    }

    public Token(TokenType type, int number) {
        this.type = type;
        this.number = number;
    }

    public Token(TokenType type, String name) {
        this.type = type;
        this.name = name;
    }
}
