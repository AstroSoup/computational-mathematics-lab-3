package ru.astrosoup.exceptions;

public class EvaluatingException extends RuntimeException {
    public EvaluatingException(String message) {
        super("While evaluating AST, an exception was encountered: " + message);
    }
    public EvaluatingException(String message, Throwable e) {
        super("While evaluating AST, an exception was encountered: " + message, e);
    }
}
