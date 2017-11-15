package ru.spbau.annikura.treeset;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

class TreeSetData<E> {
    private final Comparator<? super E> comparator;
    private SetList<E> list = new SetList<>();
    private SetNode root = null;

    private TreeSetData(Comparator<? super E> cmp) {
        comparator = cmp;
    }

    static <T extends Comparable<T>> TreeSetData<T> genNewData() {
        return new TreeSetData<>(Comparator.naturalOrder());
    }

    static <T> TreeSetData<T> genNewData(Comparator<? super T> cmp) {
        return new TreeSetData<>(cmp);
    }

    Comparator<? super E> getComparator() {
        return comparator;
    }

    public SetList<E> getList() {
        return list;
    }

    public SetNode getRoot() {
        return root;
    }

    public void setRoot(SetNode root) {
        this.root = root;
    }

    class SetNode {
        private E value;
        private SetList<E>.SetListNode listNode = null;
        private SetNode left = null, right = null;

        SetNode(@NotNull final E value) {
            this.value = value;
        }

        @Nullable
        public SetNode right() {
            return listNode.getNextSetNode();
        }

        @Nullable
        public SetNode left() {
            return listNode.getPrevSetNode();
        }

        @Nullable
        public SetNode down(@NotNull final E e) {
            if (comparator.compare(value, e) == 0) {
                return this;
            }
            return comparator.compare(value, e) < 0 ? left : right;
        }

        public void setListNode(@NotNull SetList<E>.SetListNode listNode) {
            this.listNode = listNode;
        }

        @NotNull
        public E getValue() {
            return value;
        }


        public void setLeftNode(SetNode newNode) {
            listNode.insertBefore(list.new SetListNode(newNode));
            left = newNode;
        }

        public void setRightNode(SetNode newNode) {
            listNode.insertAfter(list.new SetListNode(newNode));
            right = newNode;
        }
    }
}
