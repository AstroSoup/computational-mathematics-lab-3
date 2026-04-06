package ru.astrosoup.models;


import java.util.Map;

/**
 * Class that represents a node in the AST that contains a literal value.
 * @author astrosoup
 */
public class LiteralNode extends Node {

    private final Object value;

    public LiteralNode(Object value) {
        this.value = value;
    }

    public Object evaluate(Map<String, Object> variables) {
        return value;
    }
}
