package ru.spbau.annikura.calculator;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

enum OperatorType {
    PLUS(0) {
        @NotNull
        public BigDecimal evaluate(@NotNull BigDecimal a, @NotNull BigDecimal b)
                throws IllegalArgumentException {
            return a.add(b);
        }
    },
    MINUS(0) {
        public BigDecimal evaluate(@NotNull BigDecimal a, @NotNull BigDecimal b)
                throws IllegalArgumentException {
            return a.subtract(b);
        }
    },
    MUL(1) {
        @NotNull
        public BigDecimal evaluate(@NotNull BigDecimal a, @NotNull BigDecimal b)
                throws IllegalArgumentException {
            return a.multiply(b);
        }
    },
    DIV(1) {
        @NotNull
        public BigDecimal evaluate(@NotNull BigDecimal a, @NotNull BigDecimal b)
                throws IllegalArgumentException {
            if (b.equals(BigDecimal.ZERO)) {
                throw new IllegalArgumentException("Zero division happened.");
            }
            return a.divide(b, 10, BigDecimal.ROUND_HALF_UP);
        }
    };

    private final int priority;

    OperatorType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public static boolean isOperator(char op) {
        return op == '-' || op == '+' || op == '*' || op == '/';
    }

    static OperatorType createOperatorTypeInstance(char operator) {
        switch (operator) {
            case '+':
                return PLUS;
            case '-':
                return MINUS;
            case '*':
                return MUL;
            case '/':
                return DIV;
            default:
                throw new IllegalArgumentException(String.format("Unknown operator: %c", operator));
        }
    }

    public abstract BigDecimal evaluate(BigDecimal a, BigDecimal b) throws IllegalArgumentException;
}
