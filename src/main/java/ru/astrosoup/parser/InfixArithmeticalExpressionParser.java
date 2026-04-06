package ru.astrosoup.parser;

import ru.astrosoup.models.Node;

/**
 * Class implementing parser for infix expressions.
 * @author astrosoup
 */
public class InfixArithmeticalExpressionParser implements Parser {

    /**
     * An implementation of shunting yard algorithm for parsing infix arithmetic expression.
     * The variables can be named using alphanumeric symbols.
     *
     * @param input a string representing infix expression.
     * @return Node representing the root of AST.
     */
    public Node parse(String input) {

        StringBuilder number = new StringBuilder();
        StringBuilder variable = new StringBuilder();
        StringBuilder operator = new StringBuilder();
        Node root;
        for (char lit : input.toCharArray()) {
            if (lit == ' ') continue;

        }
        return root;
    }
}
