package ru.spbau.annikura.treeset;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

/**
 * Wrapper for data stored in tree set.
 * @param <E> stored values.
 */
class TreeSetData<E> {
    private final Comparator<? super E> comparator;
    private SetList<E> list = new SetList<>();
    private SetNode root = null;

    /**
     * Constructs a data set containing a given comparator.
     * @param cmp
     */
    private TreeSetData(@NotNull Comparator<? super E> cmp) {
        comparator = cmp;
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
     * @return
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
     * List getter.
     * @return stored list.
     */
    @NotNull
    public SetList<E> getList() {
        return list;
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
     * Root setter.
     * @param root new root to be set.
     */
    public void setRoot(@NotNull SetNode root) {
        this.root = root;
    }

    /**
     * Structure storing the basic information about the set node and providing methods to walk through the set.
     */
    class SetNode {
        private E value;
        private SetList<E>.SetListNode listNode = null;
        private SetNode left = null, right = null;

        /**
         * Value setter.
         * @param value value to be set.
         */
        public SetNode(@NotNull final E value) {
            this.value = value;
        }

        /**
         * Right son getter.
         * @return right son.
         */
        @Nullable
        public SetNode right() {
            return listNode.getNextSetNode();
        }

        /**
         * Left son getter.
         * @return left son.
         */
        @Nullable
        public SetNode left() {
            return listNode.getPrevSetNode();
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
            return comparator.compare(value, e) < 0 ? left : right;
        }

        /**
         * List node setter.
         * @param listNode a list node which is pointing at the current node.
         */
        public void setListNode(@NotNull SetList<E>.SetListNode listNode) {
            this.listNode = listNode;
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
        public void setLeftNode(@NotNull SetNode newNode) {
            listNode.insertBefore(list.new SetListNode(newNode));
            left = newNode;
        }


        /**
         * Right son setter. Also adds right son right before to the current node in the node list.
         * @param newNode new right son.
         */
        public void setRightNode(@NotNull SetNode newNode) {
            listNode.insertAfter(list.new SetListNode(newNode));
            right = newNode;
        }
    }
}
