package ru.astrosoup.models;

import ru.astrosoup.exceptions.EvaluatingException;
import ru.astrosoup.exceptions.VariableNotSpecifiedException;

import java.util.Map;

public class VariableNode extends Node {
    String name;

    public VariableNode(String name) {
        this.name = name;
    }

    public Object evaluate(Map<String, Object> variables) throws EvaluatingException {
        if (variables.containsKey(name)) {
            return variables.get(name);

        }
        throw new VariableNotSpecifiedException(this.name,  variables);
    }
}
