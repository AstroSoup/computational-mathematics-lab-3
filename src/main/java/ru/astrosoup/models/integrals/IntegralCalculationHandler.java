package ru.astrosoup.models.integrals;

import ru.astrosoup.models.ast.Node;

import java.util.Map;

public class IntegralCalculationHandler {


    public enum Method {
        RECTANGLE_LEFT,
        RECTANGLE_MIDDLE,
        RECTANGLE_RIGHT,
        TRAPEZOID,
        SIMPSON
    }

    public static Double calculate(Node function, Double a, Double b, Double eps, Method type) {
        Double i0 = null;
        Double i1 = null;
        int k;
        long n = 4;
        double h = (b - a) / n;

        switch (type) {
            case RECTANGLE_LEFT -> {
                k = 2;
                System.out.print("\rTrying n = " + n + "...");
                i1 = 0D;
                i0 = 0D;
                for (long i = 0; i < n - 1; i++) {
                    i0 += h * (Double) function.evaluate(Map.of("x", a + h * i));
                }
                for (long i = 0; i < n * 2 - 1; i++) {
                    i1 += (h / 2) * (Double) function.evaluate(Map.of("x", a + (h / 2) * i));
                }
                n *= 2;
                h = (b - a) / n;
                while (Math.abs(i1 - i0) / (Math.pow(2, k) - 1) > eps) {
                    System.out.print("\rTrying n = " + n + "...");
                    i0 = i1;
                    i1 = 0D;
                    for (long i = 0; i < n * 2 - 1; i++) {
                        i1 += (h / 2) * (Double) function.evaluate(Map.of("x", a + (h / 2) * i));
                    }
                    n *= 2;
                    h = (b - a) / n;
                }
            }
            case RECTANGLE_RIGHT -> {
                k = 2;
                System.out.print("\rTrying n = " + n + "...");
                i1 = 0D;
                i0 = 0D;
                for (long i = 1; i < n; i++) {
                    i0 += h * (Double) function.evaluate(Map.of("x", a + h * i));
                }
                for (long i = 1; i < n * 2; i++) {
                    i1 += (h / 2) * (Double) function.evaluate(Map.of("x", a + (h / 2) * i));
                }
                n *= 2;
                h = (b - a) / n;
                while (Math.abs(i1 - i0) / (Math.pow(2, k) - 1) > eps) {
                    System.out.print("\rTrying n = " + n + "...");
                    i0 = i1;
                    i1 = 0D;
                    for (long i = 1; i < n * 2; i++) {
                        i1 += (h / 2) * (Double) function.evaluate(Map.of("x", a + (h / 2) * i));
                    }
                    n *= 2;
                    h = (b - a) / n;
                }
            }
            case RECTANGLE_MIDDLE -> {
                k = 2;

                System.out.print("\rTrying n = " + n + "...");
                i1 = 0D;
                i0 = 0D;
                for (long i = 1; i < n; i++) {
                    i0 += h * (Double) function.evaluate(Map.of("x", a + h * (i - 1) + h / 2));
                }
                for (long i = 1; i < n * 2; i++) {
                    i1 += (h / 2) * (Double) function.evaluate(Map.of("x", a + (h / 2) * (i - 1) + (h / 2) / 2));
                }
                n *= 2;
                h = (b - a) / n;

                while (Math.abs(i1 - i0) / (Math.pow(2, k) - 1) > eps) {
                    System.out.print("\rTrying n = " + n + "...");

                    i0 = i1;
                    i1 = 0D;
                    for (long i = 1; i < n * 2; i++) {
                        i1 += (h / 2) * (Double) function.evaluate(Map.of("x", a + (h / 2) * (i - 1) + (h / 2) / 2));
                    }
                    n *= 2;
                    h = (b - a) / n;
                }
            }
            case TRAPEZOID -> {
                k = 2;
                double y0 = (Double) function.evaluate(Map.of("x", a));
                double yn = (Double) function.evaluate(Map.of("x", b));
                System.out.print("\rTrying n = " + n + "...");
                i1 = (y0 + yn) / 2;
                i0 = (y0 + yn) / 2;


                for (long i = 1; i < n - 1; i++) {
                    i0 += (Double) function.evaluate(Map.of("x", a + h * i));
                }
                i0 *= h;
                for (long i = 1; i < n * 2 - 1; i++) {
                    i1 += (Double) function.evaluate(Map.of("x", a + h / 2 * i));
                }
                i1 *= h / 2;

                n *= 2;
                h = (b - a) / n;

                while (Math.abs(i1 - i0) / (Math.pow(2, k) - 1) > eps) {
                    System.out.print("\rTrying n = " + n + "...");
                    i0 = i1;
                    i1 = (y0 + yn) / 2;
                    for (long i = 1; i < n * 2 - 1; i++) {
                        i1 += (Double) function.evaluate(Map.of("x", a + h / 2 * i));
                    }
                    i1 *= h / 2;
                    n *= 2;
                    h = (b - a) / n;
                }
            }
            case SIMPSON -> {
                k = 4;
                System.out.print("\rTrying n = " + n + "...");
                i1 = 0D;
                i0 = 0D;
                for (long i = 1; i < n - 1; i++) {
                    i0 += (i % 2 == 0 ? 2 : 4) * (Double) function.evaluate(Map.of("x", a + h * i));
                }
                i0 += (Double) function.evaluate(Map.of("x", a));
                i0 += (Double) function.evaluate(Map.of("x", b));
                i0 *= h / 3;

                for (long i = 1; i < n * 2 - 1; i++) {
                    i1 += (i % 2 == 0 ? 2 : 4) * (Double) function.evaluate(Map.of("x", a + (h / 2) * i));
                }
                i1 += (Double) function.evaluate(Map.of("x", a));
                i1 += (Double) function.evaluate(Map.of("x", b));
                i1 *= (h / 2) / 3;
                n *= 2;
                h = (b - a) / n;
                while (Math.abs(i1 - i0) / (Math.pow(2, k) - 1) > eps) {
                    System.out.print("\rTrying n = " + n + "...");
                    i0 = i1;
                    i1 = 0D;
                    for (long i = 1; i < n * 2 - 1; i++) {
                        i1 += (i % 2 == 0 ? 2 : 4) * (Double) function.evaluate(Map.of("x", a + (h / 2) * i));
                    }
                    i1 += (Double) function.evaluate(Map.of("x", a));
                    i1 += (Double) function.evaluate(Map.of("x", b));
                    i1 *= (h / 2) / 3;
                    n *= 2;
                    h = (b - a) / n;
                }
            }
        }
        System.out.println("\rValue calculated with n = " + n + ".");
        return i1;
    }
}
