package ru.astrosoup;

import ru.astrosoup.exceptions.TypeNotSupportedByOperatorException;
import ru.astrosoup.models.BinaryOperatorNode;
import ru.astrosoup.models.LiteralNode;
import ru.astrosoup.models.Node;
import ru.astrosoup.models.VariableNode;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
        Integer i = 100;

        BinaryOperatorNode.addBinaryOperator("+", (Object a, Object b) -> {
            if (a instanceof Integer && b instanceof Integer) {
                return (Integer)a + (Integer)b;
            }
            if (!(a instanceof Integer)) {
                throw new TypeNotSupportedByOperatorException("This operator does not support operations on type " + a.getClass() + ".");
            }

            throw new TypeNotSupportedByOperatorException("This operator does not support operations on type " + b.getClass() + ".");
        });

        Node literal = new LiteralNode(i);
        Node literal2 = new LiteralNode(i);
        Node sum = new BinaryOperatorNode("+", literal, literal2);
        Node x = new VariableNode("x");
        Node sum2 = new BinaryOperatorNode("+", sum, x);

        Map<String, Object> variables = new HashMap<>();
        variables.put("x", i);

        System.out.println(sum2.evaluate(variables));

    }
}

