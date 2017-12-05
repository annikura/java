package ru.spbau.annikura.calculator;

import org.junit.Test;
import org.mockito.InOrder;
import ru.spbau.annikura.stack.MyStack;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CalculatorTest {
    private final static boolean loggingOn = false;
    private final ExpressionToken tokenPlus = new ExpressionToken('+');
    private final ExpressionToken tokenMinus = new ExpressionToken('-');
    private final ExpressionToken tokenMul = new ExpressionToken('*');
    private final ExpressionToken tokenDiv = new ExpressionToken('/');
    private final ExpressionToken tokenZero = new ExpressionToken(BigDecimal.ZERO);
    private final ExpressionToken tokenOpBracket = new ExpressionToken('(');
    private final ExpressionToken tokenClBracket = new ExpressionToken(')');

    @Test
    public void createInstance() {
        FakeStack fake = new FakeStack(loggingOn);
        Calculator calculator = new Calculator(spy(fake));
    }

    @Test
    public void singleNumberCalculatingTest() throws Exception {
        FakeStack fake = new FakeStack(loggingOn);
        FakeStack spy = spy(fake);
        Calculator calculator = new Calculator(spy);

        // 5 = 5
        BigDecimal a = BigDecimal.valueOf(5);
        ExpressionToken token = new ExpressionToken(a);
        assertEquals(a, calculator.evaluateRPN(Collections.singletonList(token)));

        InOrder inOrder = inOrder(spy);
        inOrder.verify(spy).push(token);
        inOrder.verify(spy).pop();
        inOrder.verify(spy, never()).push(any());
        inOrder.verify(spy, never()).pop();
    }

    @Test
    public void simplePlusCalculatingTest() throws Exception {
        FakeStack fake = new FakeStack(loggingOn);
        FakeStack spy = spy(fake);
        Calculator calculator = new Calculator(spy);

        // -5 + 6 = 1
        BigDecimal a = BigDecimal.valueOf(-5);
        BigDecimal b = BigDecimal.valueOf(6);
        BigDecimal result = a.add(b);
        ExpressionToken tokenA = new ExpressionToken(a);
        ExpressionToken tokenB = new ExpressionToken(b);
        ExpressionToken tokenResult = new ExpressionToken(result);
        assertEquals(result, calculator.evaluateRPN(Arrays.asList(tokenA, tokenB, tokenPlus)));

        InOrder inOrder = inOrder(spy);
        inOrder.verify(spy).push(tokenA);
        inOrder.verify(spy).push(tokenB);
        inOrder.verify(spy, times(2)).pop();
        inOrder.verify(spy).push(tokenResult);
        inOrder.verify(spy).pop();
        inOrder.verify(spy, never()).push(any());
        inOrder.verify(spy, never()).pop();
    }


    @Test
    public void twoOperationsExpressionCalculatingTest() throws Exception {
        FakeStack fake = new FakeStack(loggingOn);
        FakeStack spy = spy(fake);
        Calculator calculator = new Calculator(spy);

        // 2 + 2 * 2 = 6
        BigDecimal a = BigDecimal.valueOf(2);
        BigDecimal result = a.add(a.multiply(a));
        ExpressionToken tokenA = new ExpressionToken(a);
        ExpressionToken tokenMulResult = new ExpressionToken(a.multiply(a));
        ExpressionToken tokenResult = new ExpressionToken(result);
        assertEquals(result, calculator.evaluateRPN(Arrays.asList(tokenA, tokenA, tokenA, tokenMul, tokenPlus)));

        InOrder inOrder = inOrder(spy);
        inOrder.verify(spy, times(3)).push(tokenA);
        inOrder.verify(spy, times(2)).pop();
        inOrder.verify(spy).push(tokenMulResult);
        inOrder.verify(spy, times(2)).pop();
        inOrder.verify(spy).push(tokenResult);
        inOrder.verify(spy).pop();
        inOrder.verify(spy, never()).push(any());
        inOrder.verify(spy, never()).pop();
    }

    @Test
    public void simpleParseTest() {
        FakeStack fake = new FakeStack(loggingOn);
        FakeStack spy = spy(fake);
        Calculator calculator = new Calculator(spy);

        //5
        BigDecimal a = BigDecimal.valueOf(5);
        ExpressionToken tokenA = new ExpressionToken(a);
        assertEquals(Collections.singletonList(tokenA), calculator.splitIntoTokens("5"));
    }


    @Test
    public void unaryMinusParseTest() {
        FakeStack fake = new FakeStack(loggingOn);
        FakeStack spy = spy(fake);
        Calculator calculator = new Calculator(spy);

        //5
        BigDecimal a = BigDecimal.valueOf(-5);
        ExpressionToken tokenA = new ExpressionToken(a);
        assertEquals(Collections.singletonList(tokenA), calculator.splitIntoTokens("-5"));
    }


    @Test
    public void unaryMinusExpressionParseTest() {
        FakeStack fake = new FakeStack(loggingOn);
        FakeStack spy = spy(fake);
        Calculator calculator = new Calculator(spy);

        // - 5 + 3
        BigDecimal a = BigDecimal.valueOf(5);
        BigDecimal b = BigDecimal.valueOf(3);
        ExpressionToken tokenA = new ExpressionToken(a);
        ExpressionToken tokenB = new ExpressionToken(b);

        assertEquals(Arrays.asList(tokenZero, tokenMinus, tokenA, tokenPlus, tokenB), calculator.splitIntoTokens("-5+3"));
    }

    @Test
    public void simpleBracketsExpressionParseTest() {
        FakeStack fake = new FakeStack(loggingOn);
        FakeStack spy = spy(fake);
        Calculator calculator = new Calculator(spy);

        //5
        BigDecimal a = BigDecimal.valueOf(5);
        ExpressionToken tokenA = new ExpressionToken(a);

        assertEquals(Arrays.asList(tokenOpBracket, tokenA, tokenClBracket), calculator.splitIntoTokens("(5)"));
    }


    @Test
    public void singleNumberConvertToRPNTest() {
        FakeStack fake = new FakeStack(loggingOn);
        FakeStack spy = spy(fake);
        Calculator calculator = new Calculator(spy);

        //5
        BigDecimal a = BigDecimal.valueOf(5);
        ExpressionToken tokenA = new ExpressionToken(a);
        List<ExpressionToken> list = Collections.singletonList(tokenA);
        assertEquals(list, calculator.convertToRPN(list));

        InOrder inOrder = inOrder(spy);
        inOrder.verify(spy).push(tokenA);
        inOrder.verify(spy).pop();
        inOrder.verify(spy, never()).push(any());
        inOrder.verify(spy, never()).pop();
        //assertEquals(Arrays.asList(tokenZero, tokenA, tokenMinus, tokenB, tokenPlus), calculator.splitIntoTokens("-5+3"));
    }


    @Test
    public void simpleConvertToRPNTest() throws Exception {
        FakeStack fake = new FakeStack(loggingOn);
        FakeStack spy = spy(fake);
        Calculator calculator = new Calculator(spy);

        //5 + 3
        BigDecimal a = BigDecimal.valueOf(5);
        BigDecimal b = BigDecimal.valueOf(3);
        ExpressionToken tokenA = new ExpressionToken(a);
        ExpressionToken tokenB = new ExpressionToken(b);
        List<ExpressionToken> list = Arrays.asList(tokenA, tokenPlus, tokenB);
        List<ExpressionToken> rpn = Arrays.asList(tokenA, tokenB, tokenPlus);

        assertEquals(rpn, calculator.convertToRPN(list));

        InOrder inOrder = inOrder(spy);
        inOrder.verify(spy).push(tokenA);
        inOrder.verify(spy).pop();
        inOrder.verify(spy).push(tokenPlus);
        inOrder.verify(spy).push(tokenB);
        inOrder.verify(spy, times(2)).pop();
        inOrder.verify(spy, never()).push(any());
        inOrder.verify(spy, never()).pop();
    }

    @Test
    public void priorityConvertToRPN() throws Exception {
        FakeStack fake = new FakeStack(loggingOn);
        FakeStack spy = spy(fake);
        Calculator calculator = new Calculator(spy);

        //5 + 3
        BigDecimal a = BigDecimal.valueOf(5);
        BigDecimal b = BigDecimal.valueOf(3);
        ExpressionToken tokenA = new ExpressionToken(a);
        ExpressionToken tokenB = new ExpressionToken(b);
        List<ExpressionToken> list = Arrays.asList(tokenA, tokenPlus, tokenB, tokenMul, tokenZero);
        List<ExpressionToken> rpn = Arrays.asList(tokenA, tokenB, tokenZero, tokenMul, tokenPlus);

        assertEquals(rpn, calculator.convertToRPN(list));

        InOrder inOrder = inOrder(spy);
        inOrder.verify(spy).push(tokenA);
        inOrder.verify(spy).pop();
        inOrder.verify(spy).push(tokenPlus);
        inOrder.verify(spy).push(tokenB);
        inOrder.verify(spy).pop();
        inOrder.verify(spy).push(tokenMul);
        inOrder.verify(spy).push(tokenZero);
        inOrder.verify(spy, times(3)).pop();
        inOrder.verify(spy, never()).push(any());
        inOrder.verify(spy, never()).pop();
    }
}

class FakeStack extends MyStack<ExpressionToken> {
    private int size = 0;
    private ExpressionToken[] stack = new ExpressionToken[100];
    private final boolean loggingOn;

    FakeStack(boolean log) {
        loggingOn = log;
        if (loggingOn)
            System.out.println("------ Fake stack created, started test ------");
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ExpressionToken pop() {
        assert size > 0;
        if (loggingOn) {
            if (stack[size - 1].isNumber()) {
                try {
                    System.out.println(String.format("Popped number %s", stack[size - 1].getNumber().toString()));
                } catch (Exception ignored) {
                }
            } else if (stack[size - 1].isClosingBracket()) {
                System.out.println("Popped closing bracket");
            } else if (stack[size - 1].isOpeningBracket()) {
                System.out.println("Popped opening bracket");
            } else {
                System.out.println("Popped some operator");
            }
        }
        return stack[--size];
    }

    @Override
    public ExpressionToken peek() {
        assert size > 0;
        return stack[size - 1];
    }

    @Override
    public ExpressionToken push(ExpressionToken expressionToken) {
        if (loggingOn) {
            if (expressionToken.isOpeningBracket()) {
                System.out.println("Pushed opening bracket");
            } else if (expressionToken.isClosingBracket()) {
                System.out.println("Pushed closing bracket");
            } else if (expressionToken.isNumber()) {
                try {
                    System.out.println(String.format("Pushed number %s", expressionToken.getNumber().toString()));
                } catch (Exception ignored) {
                }
            } else {
                System.out.println("Pushed some operator");
            }
        }
        return (stack[size++] = expressionToken);
    }

    @Override
    public boolean empty() {
        return size == 0;
    }
}