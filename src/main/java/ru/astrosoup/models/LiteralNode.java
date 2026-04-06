package ru.astrosoup.models;


import java.util.Map;

/**
 * Node that contains a literal value.
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
