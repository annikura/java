package ru.spbau.annikura.list;

import java.util.Iterator;

class ListIterator<T> implements Iterator<T>{
    private ListNode<T> node;

    ListIterator(ListNode<T> node) {
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