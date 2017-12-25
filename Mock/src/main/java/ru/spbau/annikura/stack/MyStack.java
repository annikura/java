package ru.spbau.annikura.stack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EmptyStackException;

/**
 * Stack data structure working according the rule "First in, last out".
 * @param <E> type of the elements stored in the stack.
 */
public class MyStack<E> {
    private int size = 0;
    private StackNode head = new StackNode();

    private class StackNode {
        E value = null;
        StackNode next = null;
        StackNode() {}
        StackNode(E val) {
            value = val;
        }

        void insertAfter(@NotNull E e) {
            insertAfter(new StackNode(e));
        }
        void insertAfter(StackNode node) {
            node.next = next;
            next = node;
        }

        @Nullable
        E eraseAfter() {
            if (next == null) {
                return null;
            }
            StackNode deletedNode = next;
            next = deletedNode.next;
            return deletedNode.value;
        }

        @Nullable
        E getAfter() {
            if (next == null) {
                return null;
            }
            return next.value;
        }
    }
    /**
     * Emptiness checker.
     * @return true is the stack is empty, false otherwise.
     */
    public boolean empty(){ return size == 0; }


    /**
     * Puts an element of the top of the stack.
     * @param e element to be put on the top of the stack.
     * @return given element.
     */
    @NotNull
    public E push(@NotNull E e) {
        head.insertAfter(e);
        size++;
        return e;
    }

    /**
     * Erases element from the top of the stack.
     * @return popped element.
     * @throws EmptyStackException if the stack was empty.
     */
    @NotNull
    public E pop() throws EmptyStackException {
        if (empty()) {
            throw new EmptyStackException();
        }
        size--;
        return head.eraseAfter();
    }

    /**
     * Allows to get an element from the top of the stack without popping it out from the stack.
     * @return an element from the top of the stack.
     * @throws EmptyStackException if the stack was empty.
     */
    public E peek() throws EmptyStackException {
        if (empty()) {
            throw new EmptyStackException();
        }
        return head.getAfter();
    }

    /**
     * Size getter.
     * @return size of the stack.
     */
    public int size() {
        return size;
    }
}
