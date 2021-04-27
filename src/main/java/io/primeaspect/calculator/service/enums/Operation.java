package io.primeaspect.calculator.service.enums;

import java.util.Arrays;

public enum Operation {
    SUM("+") {
        public double action(double x, double y) {
            return x + y;
        }

        public int getPriority() {
            return 2;
        }
    },
    SUBTRACT("-") {
        public double action(double x, double y) {
            return x - y;
        }

        public int getPriority() {
            return 2;
        }
    },
    MULTIPLY("*") {
        public double action(double x, double y) {
            return x * y;
        }

        public int getPriority() {
            return 3;
        }
    },
    DIVISION("/") {
        public double action(double x, double y) {
            return x / y;
        }

        public int getPriority() {
            return 3;
        }
    },
    POW("^") {
        public double action(double x, double y) {
            return Math.pow(x, y);
        }

        public int getPriority() {
            return 4;
        }
    };

    private final String value;
    private static String operations;

    Operation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public abstract double action(double x, double y);

    public abstract int getPriority();

    public static boolean isOperation(String token) {
        return getOperations().contains(token);
    }

    public static Operation getOperation(String token) {
        if (token.equals("+")) {
            return SUM;
        }
        if (token.equals("-")) {
            return SUBTRACT;
        }
        if (token.equals("*")) {
            return MULTIPLY;
        }
        if (token.equals("/")) {
            return DIVISION;
        }
        return POW;
    }

    public static String getOperations() {
        if (operations == null) {
            StringBuilder result = new StringBuilder();
            Arrays.asList(Operation.values()).forEach(value -> result.append(value.getValue()));
            operations = result.toString();
        }
        return operations;
    }
}