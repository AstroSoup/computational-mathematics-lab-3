package ru.astrosoup;

import ru.astrosoup.exceptions.TypeNotSupportedByOperatorException;
import ru.astrosoup.models.ast.BinaryOperatorNode;
import ru.astrosoup.models.ast.Node;
import ru.astrosoup.models.ast.UnaryOperatorNode;
import ru.astrosoup.parser.InfixArithmeticalExpressionParser;
import ru.astrosoup.service.OperatorInfo;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Integer i = 100;

        BinaryOperatorNode.addBinaryOperator("+", (Object a, Object b) -> {
            if (a instanceof Double && b instanceof Double) {
                return (double)a + (double)b;
            }
            if (!(a instanceof Double)) {
                throw new TypeNotSupportedByOperatorException("This operator does not support operations on type " + a.getClass() + ".");
            }

            throw new TypeNotSupportedByOperatorException("This operator does not support operations on type " + b.getClass() + ".");
        });

        BinaryOperatorNode.addBinaryOperator("-", (Object a, Object b) -> {
            if (a instanceof Double && b instanceof Double) {
                return (double)a - (double)b;
            }
            if (!(a instanceof Double)) {
                throw new TypeNotSupportedByOperatorException("This operator does not support operations on type " + a.getClass() + ".");
            }

            throw new TypeNotSupportedByOperatorException("This operator does not support operations on type " + b.getClass() + ".");
        });

        BinaryOperatorNode.addBinaryOperator("*", (Object a, Object b) -> {
            if (a instanceof Double && b instanceof Double) {
                return (double)a * (double)b;
            }
            if (!(a instanceof Double)) {
                throw new TypeNotSupportedByOperatorException("This operator does not support operations on type " + a.getClass() + ".");
            }

            throw new TypeNotSupportedByOperatorException("This operator does not support operations on type " + b.getClass() + ".");
        });

        BinaryOperatorNode.addBinaryOperator("/", (Object a, Object b) -> {
            if (a instanceof Double && b instanceof Double) {
                return (double)a / (double)b;
            }
            if (!(a instanceof Double)) {
                throw new TypeNotSupportedByOperatorException("This operator does not support operations on type " + a.getClass() + ".");
            }

            throw new TypeNotSupportedByOperatorException("This operator does not support operations on type " + b.getClass() + ".");
        });

        BinaryOperatorNode.addBinaryOperator("^", (Object a, Object b) -> {
            if (a instanceof Double && b instanceof Double) {
                return Math.pow((Double)a, (Double)b);
            }
            if (!(a instanceof Double)) {
                throw new TypeNotSupportedByOperatorException("This operator does not support operations on type " + a.getClass() + ".");
            }

            throw new TypeNotSupportedByOperatorException("This operator does not support operations on type " + b.getClass() + ".");
        });

        UnaryOperatorNode.addUnaryOperator("-", (Object a) -> {
            if (a instanceof Double) {
                return -(Double) a;
            }
            throw new TypeNotSupportedByOperatorException("This operator does not support operations on type " + a.getClass() + ".");
        });

        UnaryOperatorNode.addUnaryOperator("sin", (Object a) -> {
            if (a instanceof Double) {
                return Math.sin((Double) a);
            }
            throw new TypeNotSupportedByOperatorException("This operator does not support operations on type " + a.getClass() + ".");
        });

        UnaryOperatorNode.addUnaryOperator("cos", (Object a) -> {
            if (a instanceof Double) {
                return Math.cos((Double) a);
            }
            throw new TypeNotSupportedByOperatorException("This operator does not support operations on type " + a.getClass() + ".");
        });

        UnaryOperatorNode.addUnaryOperator("tan", (Object a) -> {
            if (a instanceof Double) {
                return Math.tan((Double) a);
            }
            throw new TypeNotSupportedByOperatorException("This operator does not support operations on type " + a.getClass() + ".");
        });

        UnaryOperatorNode.addUnaryOperator("ctg", (Object a) -> {
            if (a instanceof Double) {
                return 1D / Math.tan((Double) a);
            }
            throw new TypeNotSupportedByOperatorException("This operator does not support operations on type " + a.getClass() + ".");
        });

        InfixArithmeticalExpressionParser.addOperator("+", new OperatorInfo(1, false));
        InfixArithmeticalExpressionParser.addOperator("-", new OperatorInfo(1, false));

        Node root = new InfixArithmeticalExpressionParser().parse("cos(3.14 / 2)");
        System.out.println(root.evaluate(Map.of("x1", 1D)));


    }
}

