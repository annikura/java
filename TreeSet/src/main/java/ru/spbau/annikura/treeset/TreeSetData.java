package ru.spbau.annikura.treeset;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Wrapper for data stored in tree set.
 * @param <E> stored values.
 */
class TreeSetData<E> {
    private final Comparator<? super E> comparator;
    private SetNode head = new SetNode(null);  // Head and tail are exceptional vertices.
    private SetNode tail = new SetNode(null);  // Only values stored in them can be null.
    private SetNode root = null;
    private int treeSize = 0;

    /**
     * Size getter.
     * @return size of the tree.
     */
    public int size() {
        return treeSize;
    }

    /**
     * Constructs a data set containing a given comparator.
     * @param cmp comparator for the tree elements.
     */
    private TreeSetData(@NotNull Comparator<? super E> cmp) {
        comparator = cmp;
        head.next = tail;
        tail.prev = head;
    }

    /**
     * Factory function for constructing TreeSetData instance containing a naturalOrder comparator.
     * @param <T> comparable type.
     * @return TreeSetData instance.
     */
    @NotNull
    static <T extends Comparable<T>> TreeSetData<T> genNewData() {
        return new TreeSetData<>(Comparator.naturalOrder());
    }

    /**
     * Factory function for constructing TreeSetData instance containing a custom comparator.
     * @param cmp comparator for objects of type T.
     * @param <T> stored type.
     * @return constructed TreeSetData instance.
     */
    @NotNull
    static <T> TreeSetData<T> genNewData(@NotNull Comparator<? super T> cmp) {
        return new TreeSetData<>(cmp);
    }

    /**
     * Comparator getter.
     * @return stored comparator.
     */
    @NotNull
    Comparator<? super E> getComparator() {
        return comparator;
    }

    /**
     * Root getter.
     * @return root of the tree.
     */
    @Nullable
    public SetNode getRoot() {
        return root;
    }

    /**
     * Root setter. Resets the tree to contain only root value.
     * @param root new root to be set.
     */
    public void setRoot(@NotNull SetNode root) {
        this.root = root;
        treeSize = 1;
        head.next = root;
        tail.prev = root;
        root.prev = head;
        root.next = tail;
    }

    /**
     * Iterator object generator. This iterator will iterate in a straight order.
     * @return an iterator instance.
     */
    public Iterator<E> straightIterator() {
        return new Iterator<E>() {
            SetNode currentNode = head;
            @Override
            public boolean hasNext() {
                return currentNode.next != tail;
            }

            @NotNull
            @Override
            public E next() {
                currentNode = currentNode.next;
                return currentNode.value;
            }
        };
    }


    /**
     * Iterator object generator. This iterator will iterate in a reverse order.
     * @return an iterator instance.
     */
    public Iterator<E> descendingIterator() {
        return new Iterator<E>() {
            SetNode currentNode = tail;
            @Override
            public boolean hasNext() {
                return currentNode.prev != head;
            }

            @Override
            @NotNull
            public E next() {
                currentNode = currentNode.prev;
                return currentNode.value;
            }
        };
    }

    /**
     * Structure storing the basic information about the set node and providing methods to walk through the set.
     */
    class SetNode implements Comparable<E> {
        private E value;
        private SetNode left = null, right = null;
        private SetNode prev = null, next = null;

        /**
         * Node constructor.
         * @param value value to be set.
         */
        public SetNode(final E value) {
            this.value = value;
        }

        /**
         * Right son getter.
         * @return right son.
         */
        @Nullable
        public SetNode next() {
            if (next == tail) {
                return null;
            }
            return next;
        }

        /**
         * Left son getter.
         * @return left son.
         */
        @Nullable
        public SetNode prev() {
            if (prev == head) {
                return null;
            }
            return prev;
        }

        /**
         * Returns next node on the way closest to the one storing a given value.
         * @param e value of the searched node.
         * @return itself, if current node already stores e. Otherwise returns left son if e is
         * less than the value in the current node, right son otherwise.
         */
        @Nullable
        public SetNode down(@NotNull final E e) {
            if (comparator.compare(value, e) == 0) {
                return this;
            }
            return comparator.compare(value, e) < 0 ? right : left;
        }

        /**
         * Value getter.
         * @return stored value.
         */
        @NotNull
        public E getValue() {
            return value;
        }

        /**
         * Left son setter. Also adds left son next to the current node in the node list.
         * @param newNode new left son.
         */
        public void setRightNode(@NotNull SetNode newNode) {
            right = newNode;
            newNode.next = next;
            newNode.prev = this;
            if (next != null) {
                next.prev = newNode;
                assert(comparator.compare(newNode.value, next.value) < 0);
            }
            next = newNode;
            treeSize++;

            assert(comparator.compare(newNode.value, value) > 0);
        }


        /**
         * Right son setter. Also adds right son right before to the current node in the node list.
         * @param newNode new right son.
         */
        public void setLeftNode(@NotNull SetNode newNode) {
            left = newNode;
            newNode.prev = prev;
            newNode.next = this;
            if (prev != null) {
                prev.next = newNode;
                assert(comparator.compare(newNode.value, prev.value) > 0);
            }
            prev = newNode;
            treeSize++;

            assert(comparator.compare(newNode.value, value) < 0);
        }

        /**
         * Method that allows straight comparison of the value stored in the node and a given value
         * @param e a value to compare the stored one with.
         * @return number < 0 if stored value is less than a given one, 0 if they are equal
         * and a number > 0 if stored value is greater than a given one.
         */
        @Override
        public int compareTo(@NotNull E e) {
            return comparator.compare(value, e);
        }
    }
}
