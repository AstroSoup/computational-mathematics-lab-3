package ru.astrosoup.exceptions;

public class OperatorException extends EvaluatingException {
    public OperatorException (String name, Throwable e) {
        super("Operator " + name + "could not been applied to the value.", e);
    }
}
