package ru.spbau.annikura.calculator;

import org.apache.commons.validator.routines.BigDecimalValidator;
import org.jetbrains.annotations.NotNull;
import ru.spbau.annikura.stack.MyStack;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Character.isDigit;

/**
 * A class calculating the expression using the Reverse Polish notation. Works in O(n).
 */
public class Calculator {
    private MyStack<ExpressionToken> stack;

    /**
     * Class constructor.
     * @param stack an empty stack of Expression tokens.
     */
    public Calculator(@NotNull final MyStack<ExpressionToken> stack) {
        if (!stack.empty()) {
            throw new IllegalArgumentException("Given stack should be empty.");
        }
        this.stack = stack;
    }

    /**
     * Given an expression, evaluates it and returns the result.
     * @param expression string containing valid expression.
     * @return calculated value of the expression.
     * @throws IllegalArgumentException if the given expression is invalid.
     */
    @NotNull
    public BigDecimal evaluate(@NotNull final String expression) throws Exception {
        if (expression.length() == 0) {
            throw new IllegalArgumentException("Empty string is an invalid expression.");
        }
        List<ExpressionToken> tokens = convertToRPN(splitIntoTokens(expression));
        return evaluateRPN(tokens);
    }

    /**
     * Converts a given sequence of tokens to the Reverse Polish nonation.
     * @param tokens tokenized expression.
     * @return list of tokens representing a given expression in RPN.
     */
    @NotNull
    List<ExpressionToken> convertToRPN(@NotNull final List<ExpressionToken> tokens) {
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
     * Evaluates given RPN sequence of tokens.
     * @param tokens an expression in RPN.
     * @return the calculated value of the expression.
     */
    @NotNull
    BigDecimal evaluateRPN(@NotNull final List<ExpressionToken> tokens) throws Exception {
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
     * Creates a list containing single number token.
     * @param number a number to be stored by list.
     * @return list containing a number token.
     */
    @NotNull
    List<ExpressionToken> singletonList(@NotNull final BigDecimal number) {
        return Collections.singletonList(new ExpressionToken(number));
    }

    /**
     * Creates a list containing single operator token.
     * @param symbol a operator to be stored by list.
     * @return list containing an operator token.
     */
    @NotNull
    List<ExpressionToken> singletonList(char symbol) {
        return Collections.singletonList(new ExpressionToken(symbol));
    }

    /**
     * Given a string expression, splits it into tokens.
     * @param expression an expression to be parsed.
     * @return list of tokens.
     */
    @NotNull
    List<ExpressionToken> splitIntoTokens(@NotNull final String expression) {
        if (expression.length() == 0) {
            throw new IllegalArgumentException("An error occurred. The expression is invalid. (0)");
        }

        BigDecimal number = new BigDecimalValidator().validate(expression);
        if (number != null) {
            return singletonList(number);
        }
        switch (expression.charAt(0)) {
            case '-':
                return splitIntoTokens("0" + expression);
            case '(':
                int pos = findFirstBracketsBlock(expression);
                if (pos == expression.length()) {  // Expression looks like "(.*)"
                    return Stream
                            .of(
                                    singletonList('('),
                                    splitIntoTokens(expression.substring(1, pos - 1)),
                                    singletonList(')'))
                            .flatMap(Collection::stream)
                            .collect(Collectors.toList());
                }
                if (!OperatorType.isOperator(expression.charAt(pos))) {
                    throw new IllegalArgumentException("An error occurred. The expression is invalid. (1)");
                }
                return Stream
                        .of(
                                splitIntoTokens(expression.substring(0, pos)),
                                singletonList(expression.charAt(pos)),
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
                return Stream
                        .of(
                                singletonList(number),
                                singletonList(expression.charAt(opPos)),
                                splitIntoTokens(expression.substring(opPos + 1)))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
        }
    }

    /**
     * Given an expressing that starts with opening bracket, finds a place where this first bracket was closed.
     * @param expression arithmetic expression.
     * @return position right after the place where the first bracket was closed.
     */
    int findFirstBracketsBlock(@NotNull final String expression) {
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
