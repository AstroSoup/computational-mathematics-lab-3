package ru.astrosoup.parser;

import ru.astrosoup.models.ast.*;
import ru.astrosoup.service.OperatorInfo;
import ru.astrosoup.service.Token;

import java.util.*;

import static java.lang.Character.isAlphabetic;
import static java.lang.Character.isDigit;

/**
 * Class implementing parser for infix expressions.
 * @author astrosoup
 */
public class InfixArithmeticalExpressionParser implements Parser {


    private final static Map<String, OperatorInfo> operatorsInfo = new HashMap<>();

    public static Map<String, OperatorInfo> getOperatorInfo() {
        return operatorsInfo;
    }
    public static void addOperator(String operator, OperatorInfo info) {
        operatorsInfo.put(operator, info);
    }

    /**
     * An implementation of shunting yard algorithm for parsing infix arithmetic expression.
     * The variables can be named using alphanumeric symbols.
     * (Currently only parses numbers as doubles)
     * @param input a string representing infix expression.
     * @return Node representing the root of AST.
     */
    public Node parse(String input) {

        List<Token> tokens = tokenize(input);
        Stack<Node> output = new Stack<>();
        Stack<Token> operators = new Stack<>();

        for (Token token : tokens) {

            switch (token.getType()) {

                case NUMBER -> {
                    if (token.getText().contains(".")) {
                        output.push(new LiteralNode(Double.parseDouble(token.getText())));
                    } else {
                        output.push(new LiteralNode(Double.parseDouble(token.getText())));
                        //output.push(new LiteralNode(Long.parseLong(token.getText())));
                    }
                }

                case VARIABLE -> {
                    output.push(new VariableNode(token.getText()));
                }

                case UNARY_OP -> {
                    operators.push(token);
                }

                case BINARY_OP -> {
                    while (!operators.isEmpty() && operators.peek().getType() != Token.TokenType.LEFT_PAREN) {

                        if (operators.peek().getType() == Token.TokenType.UNARY_OP) {
                            popOperator(output, operators.pop());
                            continue;
                        }

                        if (operators.peek().getType() == Token.TokenType.BINARY_OP) {
                            OperatorInfo o1 = operatorsInfo.get(token.getText());
                            OperatorInfo o2 = operatorsInfo.get(operators.peek().getText());

                            if ((!o1.isRightAssociative() && o1.getPrecedence() <= o2.getPrecedence()) ||
                                    ( o1.isRightAssociative() && o1.getPrecedence() <  o2.getPrecedence())) {

                                popOperator(output, operators.pop());
                                continue;
                            }
                        }

                        break;
                    }

                    operators.push(token);
                }

                case LEFT_PAREN -> {
                    operators.push(token);
                }

                case RIGHT_PAREN -> {
                    while (!operators.isEmpty() &&
                            operators.peek().getType() != Token.TokenType.LEFT_PAREN) {
                        popOperator(output, operators.pop());
                    }

                    operators.pop(); // remove '('

                    // function handling
                    if (!operators.isEmpty() &&
                            operators.peek().getType() == Token.TokenType.UNARY_OP) {
                        popOperator(output, operators.pop());
                    }
                }
            }
        }

        while (!operators.isEmpty()) {
            popOperator(output, operators.pop());
        }

        return output.pop();
    }


    /**
     * Helper function for updating the output Stack when popping operators.
     * @param output The stack on which current operand nodes are.
     * @param token The operation token.
     */
    private void popOperator(Stack<Node> output, Token token) {

        switch (token.getType()) {

            case BINARY_OP -> {
                Node right = output.pop();
                Node left = output.pop();
                output.push(new BinaryOperatorNode(token.getText(), left, right));
            }

            case UNARY_OP -> {
                Node arg = output.pop();
                output.push(new UnaryOperatorNode(token.getText(), arg));
            }

            default -> throw new RuntimeException("Invalid operator: " + token);
        }
    }

    /**
     * Helper function for tokenizing the input to then process in shunting yard algorithm.
     * @param input input string.
     * @return List of tokens representing tokenized input.
     */
    private List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        input = input.replaceAll(" ", "");

        int i = 0;
        boolean expectOperand = true;

        while (i < input.length()) {
            char c = input.charAt(i);

            // NUMBER
            if (isDigit(c)) {
                StringBuilder number = new StringBuilder();
                boolean dot = false;

                while (i < input.length() &&
                        (isDigit(input.charAt(i)) || input.charAt(i) == '.')) {

                    if (input.charAt(i) == '.') {
                        if (dot) throw new RuntimeException("Invalid number");
                        dot = true;
                    }

                    number.append(input.charAt(i++));
                }

                tokens.add(new Token(Token.TokenType.NUMBER, number.toString()));
                expectOperand = false;
                continue;
            }

            // IDENTIFIER (variable or function)
            if (isAlphabetic(c)) {
                StringBuilder id = new StringBuilder();

                while (i < input.length() && (isAlphabetic(input.charAt(i)) || isDigit(input.charAt(i)) || input.charAt(i) == '_')) {
                    id.append(input.charAt(i++));
                }

                if (i < input.length() && input.charAt(i) == '(') {
                    tokens.add(new Token(Token.TokenType.UNARY_OP, id.toString())); // function
                } else {
                    tokens.add(new Token(Token.TokenType.VARIABLE, id.toString()));
                    expectOperand = false;
                }
                continue;
            }

            // OPERATORS
            if ("+-*/^".indexOf(c) >= 0) {

                if (UnaryOperatorNode.getUnaryOperators().containsKey(String.valueOf(c)) && expectOperand) {
                    tokens.add(new Token(Token.TokenType.UNARY_OP, String.valueOf(c)));
                } else {
                    tokens.add(new Token(Token.TokenType.BINARY_OP, String.valueOf(c)));
                    expectOperand = true;
                }

                i++;
                continue;
            }

            // LEFT PAREN
            if (c == '(') {
                tokens.add(new Token(Token.TokenType.LEFT_PAREN, "("));
                i++;
                expectOperand = true;
                continue;
            }

            // RIGHT PAREN
            if (c == ')') {
                tokens.add(new Token(Token.TokenType.RIGHT_PAREN, ")"));
                i++;
                expectOperand = false;
                continue;
            }

            throw new RuntimeException("Unexpected character: " + c);
        }

        return tokens;
    }

}
