package ru.spbau.annikura.calculator;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * Operators enum.
 * Can store '+', '-', '*' or '/' operations and evaluate them.
 */
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

    /**
     * Sets the priority of the operation.
     * @param priority operation priority. Operations with
     *                 lower priority will be executed later then the
     *                 operations with higher priority.
     */
    OperatorType(int priority) {
        this.priority = priority;
    }

    /**
     * Priority getter.
     * @return operator priority.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Validates a given char for being an operator.
     * @param op char to be validated.
     * @return true if a given char is operator, false otherwise.
     */
    public static boolean isOperator(char op) {
        return op == '-' || op == '+' || op == '*' || op == '/';
    }

    /**
     * OperatorType Factory function. Creates operator out of char.
     * @param operator an operator to be created.
     * @return OperatorType instance if the given char was a valid operator.
     */
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

    /**
     * Given two numbers, evaluates on them the stored operation.
     * @param a first argument.
     * @param b second argument.
     * @return result of the operation.
     * @throws IllegalArgumentException if zero division happened.
     */
    public abstract BigDecimal evaluate(BigDecimal a, BigDecimal b) throws IllegalArgumentException;
}
