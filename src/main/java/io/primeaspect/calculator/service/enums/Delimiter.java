package io.primeaspect.calculator.service.enums;

import java.util.Arrays;

public enum Delimiter {
    LEFT("(") {
        public int getPriority() {
            return 1;
        }
    },
    RIGHT(")") {
        public int getPriority() {
            return 5;
        }
    };

    private final String value;
    private static String operations;

    Delimiter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public abstract int getPriority();

    public static boolean isDelimiter(String token) {
        return getDelimiters().contains(token);
    }

    public static Delimiter getDelimiter(String token) {
        return token.equals("(") ? LEFT : RIGHT;
    }

    public static String getDelimiters() {
        if (operations == null) {
            StringBuilder result = new StringBuilder();
            Arrays.asList(Delimiter.values()).forEach(value -> result.append(value.getValue()));
            operations = result.toString();
        }
        return operations;
    }
}