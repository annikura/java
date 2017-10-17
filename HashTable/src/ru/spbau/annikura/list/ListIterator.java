package ru.spbau.annikura.list;

import java.util.Iterator;

public class ListIterator<T> implements Iterator<T>{
    private ListNode<T> node;

    public ListIterator(ListNode<T> node) {
        this.node = node;
    }

    @Override
    public T next() {
        node = node.next();
        return node.getValue();
    }

    @Override
    public boolean hasNext() {
        return node.next() != null;
    }

}