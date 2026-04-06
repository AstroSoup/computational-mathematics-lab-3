package ru.astrosoup.models;

import ru.astrosoup.exceptions.EvaluatingException;

import java.util.Map;

/**
 * Class that represents an elementary node of an AST.
 * @author astrosoup
 */
public abstract class Node {

    /**
     * Evaluates content of the Node.
     * @return evaluated Node.
     */
    public abstract Object evaluate(Map<String, Object> variables) throws EvaluatingException;
}
