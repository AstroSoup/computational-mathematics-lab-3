package ru.astrosoup.service;



public class Token {

    public enum TokenType {
        NUMBER,
        VARIABLE,
        UNARY_OP,
        BINARY_OP,
        LEFT_PAREN,
        RIGHT_PAREN
    }


    private TokenType type;
    private String text;

    public TokenType getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public Token(TokenType type, String text) {
        this.type = type;
        this.text = text;
    }
}
