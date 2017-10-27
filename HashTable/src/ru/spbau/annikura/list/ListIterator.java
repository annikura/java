package ru.spbau.annikura.list;

import java.util.Iterator;

/**
 * Simple head-to-tail list iterator.
 * @param <T>
 */
class ListIterator<T> implements Iterator<T>{
    private ListNode<T> node;

    /**
     * @param node a node right before the first node. Usually head or a fake node.
     */
    ListIterator(ListNode<T> node) {
        this.node = node;
    }

    /**
     * Iterator's next.
     * @return next element from the list in head-to-tail order.
     */
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