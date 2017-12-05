package ru.spbau.annikura.stack;

import java.util.ArrayList;
import java.util.EmptyStackException;

/**
 * Stack data structure working according the rule "First in, last out".
 * @param <E> type of the elements stored in the stack.
 */
public class MyStack<E> {
    private ArrayList<E> list = new ArrayList<>();

    /**
     * Emptiness checker.
     * @return true is the stack is empty, false otherwise.
     */
    public boolean empty() {
        return list.size() == 0;
    }

    /**
     * Puts an element of the top of the stack.
     * @param e element to be put on the top of the stack.
     * @return given element.
     */
    public E push(E e) {
        list.add(e);
        return e;
    }

    /**
     * Erases element from the top of the stack.
     * @return popped element.
     * @throws EmptyStackException if the stack was empty.
     */
    public E pop() throws EmptyStackException {
        if (list.isEmpty()) {
            throw new EmptyStackException();
        }
        return list.remove(list.size() - 1);
    }

    /**
     * Allows to get an element from the top of the stack without popping it out from the stack.
     * @return an element from the top of the stack.
     * @throws EmptyStackException if the stack was empty.
     */
    public E peek() throws EmptyStackException {
        if (list.isEmpty()) {
            throw new EmptyStackException();
        }
        return list.get(list.size() - 1);
    }

    /**
     * Size getter.
     * @return size of the stack.
     */
    public int size() {
        return list.size();
    }
}
