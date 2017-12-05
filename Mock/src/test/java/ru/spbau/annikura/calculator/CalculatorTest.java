package ru.spbau.annikura.calculator;

import org.junit.Test;
import ru.spbau.annikura.stack.MyStack;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CalculatorTest {
    @Test
    public void createInstance() {
        FakeStack fake = new FakeStack();
        Calculator calculator = new Calculator(spy(fake));
    }
}

class FakeStack extends MyStack<ExpressionToken> {
    private int size = 0;
    private ExpressionToken[] stack = new ExpressionToken[100];

    @Override
    public int size() {
        return size;
    }

    @Override
    public ExpressionToken pop() {
        assert size > 0;
        return stack[--size];
    }

    @Override
    public ExpressionToken peek() {
        assert size > 0;
        return stack[size - 1];
    }

    @Override
    public ExpressionToken push(ExpressionToken expressionToken) {
        return (stack[size++] = expressionToken);
    }

    @Override
    public boolean empty() {
        return size == 0;
    }
}