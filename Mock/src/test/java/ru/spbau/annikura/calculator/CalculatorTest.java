package ru.spbau.annikura.calculator;

import org.junit.Test;
import ru.spbau.annikura.stack.MyStack;

import java.util.Stack;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CalculatorTest {
    @Test
    public void createInstance() {
        Calculator calculator = new Calculator(mock(MyStack.class));
    }
}