package ru.astrosoup.exceptions;

import ru.astrosoup.models.ast.UnaryOperatorNode;

public class NoSuchUnaryOperatorException extends EvaluatingException {
    public NoSuchUnaryOperatorException(String operator) {
        super("An operation for " + operator + " was not defined. Found definitions for unary operators" + UnaryOperatorNode.getUnaryOperators().keySet());
    }
}
