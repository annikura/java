package ru.spbau.annikura.calculator;

import java.math.BigDecimal;
import org.jetbrains.annotations.NotNull;

class ExpressionToken {
    private enum ExpressionTokenType {
        OPERATOR(0),
        NUMBER(Integer.MAX_VALUE),
        OPENING_BRACKET(Integer.MAX_VALUE - 1),
        CLOSING_BRACKET(0);

        private int priority;

        ExpressionTokenType(int priority) {
            this.priority = priority;
        }
    }

    private final ExpressionTokenType tokenType;
    private BigDecimal number = null;
    private OperatorType operator = null;

    public ExpressionToken(char operator) {
        switch (operator) {
            case '(':
                tokenType = ExpressionTokenType.OPENING_BRACKET;
                break;
            case ')':
                tokenType = ExpressionTokenType.CLOSING_BRACKET;
                break;
            default:
                tokenType = ExpressionTokenType.OPERATOR;
                this.operator = OperatorType.createOperatorTypeInstance(operator);
                break;
        }
    }

    public ExpressionToken(@NotNull BigDecimal number) {
        tokenType = ExpressionTokenType.NUMBER;
        this.number = number;
    }

    public boolean isNumber() {
        return tokenType == ExpressionTokenType.NUMBER;
    }

    public boolean isOperator() {
        return tokenType == ExpressionTokenType.OPERATOR;
    }

    public boolean isOpeningBracket() {
        return tokenType == ExpressionTokenType.OPENING_BRACKET;
    }


    public boolean isClosingBracket() {
        return tokenType == ExpressionTokenType.CLOSING_BRACKET;
    }

    @NotNull
    public BigDecimal getNumber() throws Exception {
        if (isNumber()) {
            return number;
        }
        // TODO: replace with my exception.
        throw new Exception("Illegal 'getNumber' call. Token is not a number.");
    }

    @NotNull
    public OperatorType getOperator() throws Exception {
        if (isOperator()) {
            return operator;
        }
        // TODO: replace with my exception.
        throw new Exception("Illegal 'getOperator' call. Token is not an operator.");
    }

    public int getPriority() {
        if (tokenType == ExpressionTokenType.OPERATOR) {
            return operator.getPriority();
        }
        return tokenType.priority;
    }
}