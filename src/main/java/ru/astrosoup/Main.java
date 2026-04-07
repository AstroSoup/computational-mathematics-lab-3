package ru.astrosoup;

import ru.astrosoup.exceptions.BadContentException;
import ru.astrosoup.exceptions.TypeNotSupportedByOperatorException;
import ru.astrosoup.models.ast.BinaryOperatorNode;
import ru.astrosoup.models.ast.Node;
import ru.astrosoup.models.ast.UnaryOperatorNode;
import ru.astrosoup.models.integrals.IntegralCalculationHandler;
import ru.astrosoup.parser.InfixArithmeticalExpressionParser;
import ru.astrosoup.service.OperatorInfo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
        InfixArithmeticalExpressionParser.addOperator("*", new OperatorInfo(2, false));
        InfixArithmeticalExpressionParser.addOperator("/", new OperatorInfo(2, false));
        InfixArithmeticalExpressionParser.addOperator("^", new OperatorInfo(3, true));

        if (args.length > 0) {
            for (String p : args) {

                if (p.equals("--help")) {
                    System.out.println("Any argument to the program except \"--help\" is treated as a file path. If no arguments are given the input is taken from console.\n\nFor a file to be a valid input it must follow <name>=<value> argument structure. It also must include the following parameters:\n    a       lower integration bound \n    b       higher integration bound \n    eps     accuracy of calculation needed \n    f       function that needs to be integrated (currently program only works for one variable functions with a variable of name x). \n    m       a numerical method with which the integral should be calculated (currently program supports left(1), middle(2) or right(3) rectangle methods, trapezoid(4) method and simpson(5) method).\n    To input the method write the number corresponding to it.");
                    continue;
                }

                Path in = Path.of(p);

                Double a = null;
                Double b = null;
                Node f = null;
                Double eps = null;
                IntegralCalculationHandler.Method m = null;

                if (Files.exists(in)) {
                    try (BufferedReader inReader = Files.newBufferedReader(in);){
                        List<String> lines = inReader.lines().toList();
                        for (String line : lines) {
                            String[] parts = line.split("=");

                            if (parts.length != 2) {
                                throw new BadContentException("Line \"" + line + "\" in file \"" + p + "\" is not formatted properly. See \"--help\" for more information.");
                            }
                            parts[0] = parts[0].trim();
                            parts[1] = parts[1].trim();

                            switch (parts[0]) {
                                case "a" -> a = Double.parseDouble(parts[1]);
                                case "b" -> b = Double.parseDouble(parts[1]);
                                case "eps" -> eps = Double.parseDouble(parts[1]);
                                case "f" -> f = InfixArithmeticalExpressionParser.parse(parts[1]);
                                case "m" -> m = IntegralCalculationHandler.Method.values()[Integer.parseInt(parts[1]) - 1];
                            }
                        }
                        System.out.println("For file \"" + p + "\" the resulting integral is: " + IntegralCalculationHandler.calculate(f, a, b, eps, m));
                    } catch (IOException e) {
                        System.out.println("An exception occurred while reading the file.");
                    } catch (BadContentException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Path \"" + p + "\" does not exist.");
                }
            }
        } else {
            try (BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.print("Please input the function that you want to calculate integral of (use \"x\" as a variable name):\nf(x) = ");
                Node f = InfixArithmeticalExpressionParser.parse(inReader.readLine());
                System.out.print("Please input the lower bound for integral calculation:\na = ");
                Double a = Double.parseDouble(inReader.readLine());
                System.out.print("Please input the higher bound for integral calculation:\nb = ");
                Double b = Double.parseDouble(inReader.readLine());
                System.out.print("Please input the accuracy of calculation:\neps = ");
                Double eps = Double.parseDouble(inReader.readLine());
                System.out.print("Please input the selected method for calculating the integral left rectangle method(1), middle rectangle method(2), right rectangle method(3), trapezoid method(4), simpson method(5):\nm = ");
                IntegralCalculationHandler.Method m = IntegralCalculationHandler.Method.values()[Integer.parseInt(inReader.readLine()) - 1];
                System.out.println("The resulting integral is: " + IntegralCalculationHandler.calculate(f, a, b, eps, m));

            } catch (IOException e) {
                System.out.print("An exception during handling IO operations occurred.");
            }


        }

    }
}

