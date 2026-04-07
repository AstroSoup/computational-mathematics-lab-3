package ru.astrosoup.service;

public class OperatorInfo {
    private int precedence;
    private boolean rightAssociative;

    public int getPrecedence() {
        return precedence;
    }
    public boolean isRightAssociative() {
        return rightAssociative;
    }

    public OperatorInfo(int precedence, boolean rightAssociative) {
        this.precedence = precedence;
        this.rightAssociative = rightAssociative;
    }
}

