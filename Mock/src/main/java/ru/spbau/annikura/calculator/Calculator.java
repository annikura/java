package ru.spbau.annikura.calculator;

import java.math.BigDecimal;
import org.apache.commons.validator.routines.BigDecimalValidator;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Character.isDigit;

/**
 *
 */
public class Calculator {
    private Stack<ExpressionToken> stack;

    /**
     *
     * @param stack
     */
    public Calculator(@NotNull final Stack<ExpressionToken> stack) {
        if (!stack.empty()) {
            throw new IllegalArgumentException("Given stack should be empty.");
        }
        this.stack = stack;
    }

    /**
     *
     * @param expression
     * @return
     * @throws IllegalArgumentException
     */
    @NotNull
    public BigDecimal evaluate(@NotNull String expression) throws Exception {
        if (expression.length() == 0) {
            throw new IllegalArgumentException("Empty string is an invalid expression.");
        }
        List<ExpressionToken> tokens = convertToRPN(splitIntoTokens(expression));
        return evaluateRPN(tokens);
    }

    /**
     *
     * @param tokens
     * @return
     */
    @NotNull
    private List<ExpressionToken> convertToRPN(@NotNull List<ExpressionToken> tokens) {
        assert stack.empty();
        List<ExpressionToken> rpn = new ArrayList<>();
        for (ExpressionToken token : tokens) {
            if (token.isClosingBracket()) {
                while (!stack.empty() && !stack.peek().isOpeningBracket()) {
                    rpn.add(stack.pop());
                }
                if (stack.empty()) {
                    throw new IllegalArgumentException("Expression is invalid. Brackets do not pair.");
                } else {
                    stack.pop();
                }
                continue;
            }
            while (!stack.empty() && !stack.peek().isOpeningBracket() &&
                    token.getPriority() <= stack.peek().getPriority()) {
                rpn.add(stack.pop());
            }
            stack.push(token);
        }
        while (!stack.empty()) {
            rpn.add(stack.pop());
        }
        return rpn;
    }

    /**
     *
     * @param tokens
     * @return
     */
    @NotNull
    private BigDecimal evaluateRPN(@NotNull final List<ExpressionToken> tokens) throws Exception {
        assert stack.empty();

        for (ExpressionToken token : tokens) {
            assert token.isNumber() || token.isOperator();

            if (token.isNumber()) {
                stack.push(token);
            } else {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("An error occurred. The expression is invalid.");
                }
                BigDecimal b = stack.pop().getNumber();
                BigDecimal a = stack.pop().getNumber();
                stack.push(new ExpressionToken(token.getOperator().evaluate(a, b)));
            }
        }
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Could not correctly evaluate the expression. Expression is invalid.");
        }
        return stack.pop().getNumber();
    }

    /**
     *
     * @param number
     * @return
     */
    @NotNull
    private List<ExpressionToken> singleTokenList(@NotNull final BigDecimal number) {
        return singleTokenList(new ExpressionToken(number));
    }

    /**
     *
     * @param symbol
     * @return
     */
    @NotNull
    private List<ExpressionToken> singleTokenList(char symbol) {
        return singleTokenList(new ExpressionToken(symbol));
    }

    /**
     *
     * @param token
     * @return
     */
    @NotNull
    private List<ExpressionToken> singleTokenList(@NotNull final ExpressionToken token) {
        List<ExpressionToken> list = new ArrayList<>();
        list.add(token);
        return list;
    }

    /**
     *
     * @param expression
     * @return
     */
    @NotNull
    private List<ExpressionToken> splitIntoTokens(@NotNull final String expression) {
        if (expression.length() == 0) {
            throw new IllegalArgumentException("An error occurred. The expression is invalid. (1)");
        }

        BigDecimal number = new BigDecimalValidator().validate(expression);
        if (number != null) {
            return singleTokenList(number);
        }
        switch (expression.charAt(0)) {
            case '-':
                return splitIntoTokens("0" + expression);
            case '(':
                int pos = findFirstBracketsBlock(expression);
                if (pos == expression.length()) {  // Expression looks like "(.*)"
                    return Stream
                            .of(
                                    singleTokenList('('),
                                    splitIntoTokens(expression.substring(1, pos - 1)),
                                    singleTokenList(')'))
                            .flatMap(Collection::stream)
                            .collect(Collectors.toList());
                }
                if (!OperatorType.isOperator(expression.charAt(pos))) {
                    throw new IllegalArgumentException("An error occurred. The expression is invalid.");
                }
                return Stream
                        .of(
                                splitIntoTokens(expression.substring(0, pos)),
                                singleTokenList(expression.charAt(pos)),
                                splitIntoTokens(expression.substring(pos + 1)))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
            default:
                int opPos = 0;
                while (opPos < expression.length() &&
                        isDigit(expression.charAt(opPos)) ||
                        expression.charAt(opPos) == '.') {
                    opPos++;
                }
                number = new BigDecimalValidator().validate(expression.substring(0, opPos));
                if (number == null || !OperatorType.isOperator(expression.charAt(opPos))) {
                    throw new IllegalArgumentException("An error occurred. The expression is invalid.(2)");
                }
                try {

                    return Stream
                            .of(
                                    singleTokenList(number),
                                    singleTokenList(expression.charAt(opPos)),
                                    splitIntoTokens(expression.substring(opPos + 1)))
                            .flatMap(Collection::stream)
                            .collect(Collectors.toList());

                } catch (NoSuchElementException exception) {
                    throw new IllegalArgumentException("An error occurred. The expression is invalid.(3)");
                }
        }
    }

    /**
     *
     * @param expression
     * @return
     */
    private int findFirstBracketsBlock(@NotNull String expression) {
        assert expression.length() > 0 && expression.charAt(0) == '(';
        int balance = 0;
        for (int i = 0; i < expression.length(); i++) {
            switch (expression.charAt(i)) {
                case '(':
                    balance++;
                    break;
                case ')':
                    balance--;
                    break;
                default:
                    break;
            }
            if (balance == 0) {
                return i + 1;
            }
        }
        throw new IllegalArgumentException("The expression is invalid. Brackets do not pair.");
    }
}
