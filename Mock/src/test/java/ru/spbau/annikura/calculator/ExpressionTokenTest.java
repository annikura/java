package ru.spbau.annikura.calculator;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ExpressionTokenTest {
    enum TokenTypes {
        NUMBER(Integer.MAX_VALUE),
        MINUS(0),
        PLUS(0),
        DIV(1),
        MUL(1),
        CLOSE(0),
        OPEN(Integer.MAX_VALUE - 1),
        OPERATOR(Integer.MIN_VALUE);

        private int priority;

        TokenTypes(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }
    }

    public void checkTypes(ExpressionToken token, TokenTypes type) {
        boolean isOperator = type == TokenTypes.OPERATOR;
        boolean isNumber = type == TokenTypes.NUMBER;
        boolean isClosed = type == TokenTypes.CLOSE;
        boolean isOpen = type == TokenTypes.OPEN;
        assertEquals(isOperator, token.isOperator());
        assertEquals(isNumber, token.isNumber());
        assertEquals(isClosed, token.isClosingBracket());
        assertEquals(isOpen, token.isOpeningBracket());
    }

    @Test
    public void createBracketsInstances() {
        ExpressionToken token = new ExpressionToken(')');
        token = new ExpressionToken('(');
    }

    @Test(expected = IllegalArgumentException.class)
    public void createIllegalInstances() {
        ExpressionToken token = new ExpressionToken('^');
    }

    @Test
    public void createNumberInstance() {
        ExpressionToken token = new ExpressionToken(BigDecimal.TEN);
        token = new ExpressionToken(BigDecimal.ZERO);
    }

    @Test
    public void createOperatorsInstances() {
        ExpressionToken token = new ExpressionToken('-');
        token = new ExpressionToken('+');
        token = new ExpressionToken('*');
        token = new ExpressionToken('/');
    }

    @Test
    public void checkTypeMethodsForMinus() {
        ExpressionToken token = new ExpressionToken('-');
        checkTypes(token, TokenTypes.OPERATOR);
    }


    @Test
    public void checkTypeMethodsForPlus() {
        ExpressionToken token = new ExpressionToken('+');
        checkTypes(token, TokenTypes.OPERATOR);
    }


    @Test
    public void checkTypeMethodsForMul() {
        ExpressionToken token = new ExpressionToken('*');
        checkTypes(token, TokenTypes.OPERATOR);
    }

    @Test
    public void checkTypeMethodsForDiv() {
        ExpressionToken token = new ExpressionToken('/');
        checkTypes(token, TokenTypes.OPERATOR);
    }

    @Test
    public void checkTypeMethodsForClosingBracket() {
        ExpressionToken token = new ExpressionToken(')');
        checkTypes(token, TokenTypes.CLOSE);
    }

    @Test
    public void checkTypeMethodsForOpeningBracket() {
        ExpressionToken token = new ExpressionToken('(');
        checkTypes(token, TokenTypes.OPEN);
    }

    @Test
    public void checkPriorityForMinus() {
        ExpressionToken token = new ExpressionToken('-');
        assertEquals(token.getPriority(), TokenTypes.MINUS.getPriority());
    }


    @Test
    public void checkPriorityForPlus() {
        ExpressionToken token = new ExpressionToken('+');
        assertEquals(token.getPriority(), TokenTypes.PLUS.getPriority());
    }

    @Test
    public void checkPriorityForMul() {
        ExpressionToken token = new ExpressionToken('*');
        assertEquals(token.getPriority(), TokenTypes.MUL.getPriority());
    }

    @Test
    public void checkPriorityForDiv() {
        ExpressionToken token = new ExpressionToken('/');
        assertEquals(token.getPriority(), TokenTypes.DIV.getPriority());
    }

    @Test
    public void checkPriorityForOpeningBracket() {
        ExpressionToken token = new ExpressionToken('(');
        assertEquals(token.getPriority(), TokenTypes.OPEN.getPriority());
    }

    @Test
    public void getOperatorForMinus() throws Exception {
        ExpressionToken token = new ExpressionToken('-');
        assertEquals(OperatorType.MINUS, token.getOperator());
    }

    @Test
    public void getOperatorForPlus() throws Exception {
        ExpressionToken token = new ExpressionToken('+');
        assertEquals(OperatorType.PLUS, token.getOperator());
    }

    @Test
    public void getOperatorForMul() throws Exception {
        ExpressionToken token = new ExpressionToken('*');
        assertEquals(OperatorType.MUL, token.getOperator());
    }

    @Test
    public void getOperatorForDiv() throws Exception {
        ExpressionToken token = new ExpressionToken('/');
        assertEquals(OperatorType.DIV, token.getOperator());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getOperatorForNumber() throws Exception {
        ExpressionToken token = new ExpressionToken(BigDecimal.ONE);
        token.getOperator();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getOperatorForOpeningBracket() throws Exception {
        ExpressionToken token = new ExpressionToken('(');
        token.getOperator();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getOperatorForClosingBracket() throws Exception {
        ExpressionToken token = new ExpressionToken(')');
        token.getOperator();
    }

    @Test
    public void getNumber() throws Exception {
        ExpressionToken token = new ExpressionToken(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN, token.getNumber());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getNumberForOperator() throws Exception {
        ExpressionToken token = new ExpressionToken('+');
        token.getNumber();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getNumberForOpeningBracket() throws Exception {
        ExpressionToken token = new ExpressionToken(')');
        token.getNumber();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getNumberForClosingBracket() throws Exception {
        ExpressionToken token = new ExpressionToken('(');
        token.getNumber();
    }
}