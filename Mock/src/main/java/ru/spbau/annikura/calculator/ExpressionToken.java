package ru.spbau.annikura.calculator;

import java.math.BigDecimal;
import org.jetbrains.annotations.NotNull;

/**
 * Expression token class. An abstraction for working with operators, brackets and numbers.
 */
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

    /**
     * Expression token constructor. Creates a bracket or an operator token out of character.
     * @param operator a symbol to create token from.
     */
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


    /**
     * Expression token constructor. Creates a number token.
     * @param number a number to create token from.
     */
    public ExpressionToken(@NotNull BigDecimal number) {
        tokenType = ExpressionTokenType.NUMBER;
        this.number = number;
    }

    /**
     * Content checker.
     * @return true if stored token is a number, false otherwise.
     */
    public boolean isNumber() {
        return tokenType == ExpressionTokenType.NUMBER;
    }

    /**
     * Content checker.
     * @return true if stored token is an operator, false otherwise.
     */
    public boolean isOperator() {
        return tokenType == ExpressionTokenType.OPERATOR;
    }

    /**
     * Content checker.
     * @return true if stored token is an opening bracket, false otherwise.
     */

    public boolean isOpeningBracket() {
        return tokenType == ExpressionTokenType.OPENING_BRACKET;
    }

    /**
     * Content checker.
     * @return true if stored token is a closing bracket, false otherwise.
     */
    public boolean isClosingBracket() {
        return tokenType == ExpressionTokenType.CLOSING_BRACKET;
    }

    /**
     * Stored number getter.
     * @return stored number.
     * @throws Exception if token doesn't store a number.
     */
    @NotNull
    public BigDecimal getNumber() throws Exception {
        if (isNumber()) {
            return number;
        }
        // TODO: replace with my exception.
        throw new Exception("Illegal 'getNumber' call. Token is not a number.");
    }

    /**
     * Stored operator getter.
     * @return stored operator.
     * @throws Exception if token doesn't store an operator.
     */
    @NotNull
    public OperatorType getOperator() throws Exception {
        if (isOperator()) {
            return operator;
        }
        // TODO: replace with my exception.
        throw new Exception("Illegal 'getOperator' call. Token is not an operator.");
    }

    /**
     * Priority getter.
     * @return priority of the stored token.
     */
    public int getPriority() {
        if (tokenType == ExpressionTokenType.OPERATOR) {
            return operator.getPriority();
        }
        return tokenType.priority;
    }
}