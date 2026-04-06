package ru.astrosoup.models;

import ru.astrosoup.Main;
import ru.astrosoup.exceptions.EvaluatingException;
import ru.astrosoup.exceptions.NoSuchBinaryOperatorException;


import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;

public class BinaryOperatorNode extends Node {

    private final String operator;
    private final Node left;
    private final Node right;

    private final static Map<String, BinaryOperator<Object>> binaryOperators = new HashMap<>();

    public BinaryOperatorNode(String operator, Node left, Node right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public Object evaluate(Map<String, Object> variables) throws EvaluatingException {
        if (!binaryOperators.containsKey(operator)) {
            throw new NoSuchBinaryOperatorException(operator);
        }

        return binaryOperators.get(operator).apply(left.evaluate(variables), right.evaluate(variables));
    }

    public static Map<String, BinaryOperator<Object>> getBinaryOperators() {
        return binaryOperators;
    }

    public static void addBinaryOperator(String literal, BinaryOperator<Object> operation) {
        binaryOperators.put(literal, operation);
    }


}
