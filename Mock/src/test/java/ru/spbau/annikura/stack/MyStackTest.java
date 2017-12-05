package ru.spbau.annikura.stack;

import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.*;

public class MyStackTest {
    @Test
    public void createInstance() {
        MyStack<Integer> stack = new MyStack<>();
        assertEquals(0, stack.size());
    }

    @Test
    public void addingOneElement() {
        MyStack<String> stack = new MyStack<>();
        final String str = "test string";
        assertEquals(str, stack.push(str));
        assertEquals(1, stack.size());
        assertEquals(str, stack.peek());
    }

    @Test(expected = EmptyStackException.class)
    public void popFromEmpty() {
        MyStack<String> stack = new MyStack<>();
        stack.pop();
    }


    @Test(expected = EmptyStackException.class)
    public void peekFromEmpty() {
        MyStack<String> stack = new MyStack<>();
        stack.peek();
    }

    @Test
    public void removingOneElement() {
        MyStack<String> stack = new MyStack<>();
        final String str = "test string";
        stack.push(str);
        assertEquals(str, stack.pop());
        assertEquals(0, stack.size());
    }

    @Test
    public void addingSeveralElements() {
        MyStack<Integer> stack = new MyStack<>();
        for (int i = 0; i < 5; i++) {
            assertEquals(i, (int) stack.push(i));
            assertEquals(i, (int) stack.peek());
        }
        assertEquals(5, stack.size());
    }

    @Test
    public void removingSeveralElements() {
        MyStack<Integer> stack = new MyStack<>();
        for (int i = 0; i < 5; i++) {
            assertEquals(i, (int) stack.push(i));
            assertEquals(i, (int) stack.peek());
        }
        for (int i = 4; i >= 0; i--) {
            assertEquals(i, (int) stack.pop());
            if (i > 0)
                assertEquals(i - 1, (int) stack.peek());
        }
        assertEquals(0, stack.size());
    }

    @Test
    public void testEmpty() {
        MyStack<Integer> stack = new MyStack<>();
        assertTrue(stack.empty());
        stack.push(1);
        assertFalse(stack.empty());
    }
}