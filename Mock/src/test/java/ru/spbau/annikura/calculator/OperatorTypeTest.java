package ru.spbau.annikura.calculator;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.Assert.*;

public class OperatorTypeTest {
    @Test
    public void createPlusInstance() {
        OperatorType operator = OperatorType.createOperatorTypeInstance('+');
    }

    @Test
    public void createMinusInstance() {
        OperatorType operator = OperatorType.createOperatorTypeInstance('-');
    }

    @Test
    public void createMulInstance() {
        OperatorType operator = OperatorType.createOperatorTypeInstance('*');
    }

    @Test
    public void createDivInstance() {
        OperatorType operator = OperatorType.createOperatorTypeInstance('/');
    }

    @Test(expected = IllegalArgumentException.class)
    public void createUnknownInstance() {
        OperatorType operator = OperatorType.createOperatorTypeInstance('(');
    }

    @Test
    public void applyPlus() {
        OperatorType operator = OperatorType.createOperatorTypeInstance('+');
        BigDecimal a = BigDecimal.TEN;
        BigDecimal b = BigDecimal.ONE;
        assertEquals(a.add(b), operator.evaluate(a, b));
    }

    @Test
    public void applyMinus() {
        OperatorType operator = OperatorType.createOperatorTypeInstance('-');
        BigDecimal a = BigDecimal.TEN;
        BigDecimal b = BigDecimal.TEN.add(a);
        assertEquals(a.subtract(b, MathContext.DECIMAL128), operator.evaluate(a, b));
    }

    @Test
    public void applyMul() {
        OperatorType operator = OperatorType.createOperatorTypeInstance('*');
        BigDecimal a = BigDecimal.TEN;
        BigDecimal b = BigDecimal.TEN.add(a);
        assertEquals(a.multiply(b), operator.evaluate(a, b));
    }

    @Test
    public void applyDiv() {
        OperatorType operator = OperatorType.createOperatorTypeInstance('/');
        BigDecimal a = BigDecimal.TEN;
        BigDecimal b = BigDecimal.TEN.add(a);
        assertEquals(a.divide(b, MathContext.DECIMAL128), operator.evaluate(a, b));
    }

    @Test(expected = ArithmeticException.class)
    public void zeroDivision() {
        OperatorType operator = OperatorType.createOperatorTypeInstance('/');
        BigDecimal a = BigDecimal.TEN;
        BigDecimal b = BigDecimal.ZERO;
        assertEquals(a.divide(b, MathContext.DECIMAL128), operator.evaluate(a, b));
    }

    @Test
    public void checkIsOperator() {
        assertTrue(OperatorType.isOperator('-'));
        assertTrue(OperatorType.isOperator('+'));
        assertTrue(OperatorType.isOperator('*'));
        assertTrue(OperatorType.isOperator('/'));
        assertFalse(OperatorType.isOperator('7'));
        assertFalse(OperatorType.isOperator('i'));
    }

    @Test
    public void checkMinusPriority() {
        assertEquals(0,
                OperatorType.createOperatorTypeInstance('-').getPriority());
    }

    @Test
    public void checkPlusPriority() {
        assertEquals(0,
                OperatorType.createOperatorTypeInstance('+').getPriority());
    }

    @Test
    public void checkMulPriority() {
        assertEquals(1,
                OperatorType.createOperatorTypeInstance('*').getPriority());
    }

    @Test
    public void checkDivPriority() {
        assertEquals(1,
                OperatorType.createOperatorTypeInstance('/').getPriority());
    }
}