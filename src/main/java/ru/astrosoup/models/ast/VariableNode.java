package ru.astrosoup.models.ast;

import ru.astrosoup.exceptions.EvaluatingException;
import ru.astrosoup.exceptions.VariableNotSpecifiedException;

import java.util.Map;

/**
 * Class that represents variable node in the AST.
 * @author astrosoup
 */
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
