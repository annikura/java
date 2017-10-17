package ru.spbau.annikura.list;

import java.util.Iterator;

public class List<T> implements Iterable<T> {
    private ListNode<T> head = new ListNode<>();
    private ListNode<T> tail = new ListNode<>();
    private int size = 0;

    public List() {
        head.insertAfter(tail);
    }

    public void add(ListNode<T> node) {
        tail.previous().insertAfter(node);
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator<>(head);
    }
}
