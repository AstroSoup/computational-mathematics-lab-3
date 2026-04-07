package ru.astrosoup.exceptions;

import ru.astrosoup.models.ast.BinaryOperatorNode;

public class NoSuchBinaryOperatorException extends EvaluatingException {
    public NoSuchBinaryOperatorException(String operator) {
        super("An operation for " + operator + " was not defined. Found definitions for binary operators" + BinaryOperatorNode.getBinaryOperators().keySet());
    }
}
