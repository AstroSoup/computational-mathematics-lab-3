package ru.astrosoup.exceptions;

import java.util.Map;

public class VariableNotSpecifiedException extends EvaluatingException {

    public VariableNotSpecifiedException(String name, Map<String, Object> variables) {
        super("Value for variable \"" + name + "\" was not specified. Found variables: " + variables + ".");
    }


}
