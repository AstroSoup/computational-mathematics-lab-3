package ru.astrosoup.models.ast;

import ru.astrosoup.exceptions.EvaluatingException;
import ru.astrosoup.exceptions.NoSuchUnaryOperatorException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * Class that represents unary operator node in the AST.
 * @author astrosoup
 */
public class UnaryOperatorNode extends Node {
    private final String operator;
    private final Node child;

    private final static Map<String, UnaryOperator<Object>> unaryOperators = new HashMap<>();

    public UnaryOperatorNode(String operator, Node child) {
        this.operator = operator;
        this.child = child;
    }

    public static Map<String, UnaryOperator<Object>> getUnaryOperators() {
        return unaryOperators;
    }

    public static void addUnaryOperator(String literal, UnaryOperator<Object> operation) {
        unaryOperators.put(literal, operation);
    }

    @Override
    public Object evaluate(Map<String, Object> variables) throws EvaluatingException {
        if (!unaryOperators.containsKey(operator)) {
            throw new NoSuchUnaryOperatorException(operator);
        }

        return unaryOperators.get(operator).apply(child.evaluate(variables));
    }
}
