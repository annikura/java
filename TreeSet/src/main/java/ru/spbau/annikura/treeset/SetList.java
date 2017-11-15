package ru.spbau.annikura.treeset;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

class SetList<E> implements Iterable<E> {
    private SetListNode head = new SetListNode(null);
    private SetListNode tail = new SetListNode(null);
    private int listSize = 0;

    public SetList() {
        head.next = tail;
        tail.prev = head;
    }

    public void add (@NotNull TreeSetData<E>.SetNode node) {
        head.insertAfter(new SetListNode(node));
    }

    public int size() {
        return listSize;
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new SetListStraightIterator();
    }

    @NotNull
    public Iterator<E> descendingIterator() {
        return new SetListReverseIterator();
    }

    private class SetListStraightIterator implements Iterator<E> {
        SetListNode currentNode = head;

        @Override
        public boolean hasNext() {
            return !currentNode.next.equals(tail);
        }

        @NotNull
        @Override
        public E next() {
            currentNode = currentNode.next;
            return currentNode.node.getValue();
        }
    }

    private class SetListReverseIterator implements Iterator<E> {
        SetListNode currentNode = tail;

        @Override
        public boolean hasNext() {
            return !currentNode.prev.equals(head);
        }

        @NotNull
        @Override
        public E next() {
            currentNode = currentNode.prev;
            return currentNode.node.getValue();
        }
    }

    class SetListNode {
        private TreeSetData<E>.SetNode node;
        private SetListNode next = null;
        private SetListNode prev = null;

        public SetListNode(@Nullable final TreeSetData<E>.SetNode node) {
            this.node = node;
            if (node != null) {
                node.setListNode(this);
            }
        }

        public void insertAfter(SetListNode setListNode) {
            setListNode.next = next;
            setListNode.prev = this;
            next = setListNode;
            listSize++;
        }

        public void insertBefore(SetListNode setListNode) {
            setListNode.next = this;
            setListNode.prev = prev;
            prev = setListNode;
            listSize++;
        }

        public TreeSetData<E>.SetNode getNextSetNode() {
            if (next == tail)
                return null;
            return next.node;
        }

        public TreeSetData<E>.SetNode getPrevSetNode() {
            if (prev == head)
                return null;
            return prev.node;
        }
    }

}
