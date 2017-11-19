package ru.spbau.annikura.treeset;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractSet;
import java.util.NoSuchElementException;

/**
 * Implementation of my TreeSet.
 * @param <E> extends comparable. Objects that will be stored in the container.
 */
abstract class AbstractTreeSetImpl<E> extends AbstractSet<E> implements MyTreeSet<E> {
    TreeSetData<E> data;

    /** {@link java.util.TreeSet#add(Object)} **/
    public boolean add(@NotNull E e) {
        TreeSetData<E>.SetNode node = findClosest(data.getRoot(), e);
        if (node != null && node.compareTo(e) == 0) {
            return false;
        }
        TreeSetData<E>.SetNode newNode = data.new SetNode(e);
        if (node == null) {
            data.setRoot(newNode);
            return true;
        }
        if (node.compareTo(e) < 0) {
            node.setRightNode(newNode);
        } else {
            node.setLeftNode(newNode);
        }
        return true;
    }

    public boolean containing(@NotNull E e) {
        return findExact(data.getRoot(), e) != null;
    }


    /** {@link java.util.TreeSet#size()} **/
    @Override
    public int size() {
        return data.size();
    }

    /**
     * Finds the node in the node subtree with a given value, if exists.
     * @param node a node in which subtree the value will be searched.
     * @param e value of the searched node.
     * @return node if it existed in the given tree, null otherwise.
     */
    @Nullable
    private TreeSetData<E>.SetNode findExact(@Nullable TreeSetData<E>.SetNode node, @NotNull E e) {
        if (node == null) {
            return null;
        }
        if (node.compareTo(e) == 0)
            return node;
        return findExact(node.down(e), e);
    }

    /**
     * Looks for the node, closest to the one that should store a given value:
     * either a node with this valiue itself (if this value exists in the given subtree),
     * or a node which son the given value is expected to be.
     * @param node the root of the subtree where the node with the given value is searched.
     * @param e value of the searched node.
     * @return null is given node is null, closest to the given value vertex otherwise.
     */
    @Nullable
    private TreeSetData<E>.SetNode findClosest(@Nullable TreeSetData<E>.SetNode node, @NotNull E e) {
        if (node == null) {
            return null;
        }
        if (node.compareTo(e) == 0) {
            return node;
        }
         return node.down(e) != null ? findClosest(node.down(e), e) : node;
    }


    /** {@link java.util.TreeSet#floor(Object)} **/
    @Nullable
    @Override
    public E floor(@NotNull final E e) {
        TreeSetData<E>.SetNode node = findClosest(data.getRoot(), e);
        if (node == null) {
            return null;
        }
        if (node.compareTo(e) <= 0) {
            return node.getValue();
        }
        node = node.prev();
        return node == null ? null : node.getValue();
    }


    /** {@link java.util.TreeSet#lower(Object)} **/
    @Override
    @Nullable
    public E lower(@NotNull E e) {
        E floorRes = floor(e);
        if (floorRes == null) {
            return null;
        }
        TreeSetData<E>.SetNode node = findExact(data.getRoot(), floorRes);  // Won't be null because
        // this element was found in the Set by floor.
        if (node.compareTo(e) == 0) {
            node = node.prev();
        }
        return node == null ? null : node.getValue();
    }


    /** {@link java.util.TreeSet#ceiling(Object)} **/
    @Override
    @Nullable
    public E ceiling(@NotNull E e) {
        TreeSetData<E>.SetNode node = findClosest(data.getRoot(), e);
        if (node == null) {
            return null;
        }
        if (node.compareTo(e) >= 0) {
            return node.getValue();
        }
        node = node.next();
        return node == null ? null : node.getValue();
    }


    /** {@link java.util.TreeSet#higher(Object)} **/
    @Override
    @Nullable
    public E higher(@NotNull E e) {
        E ceilingRes = ceiling(e);
        if (ceilingRes == null) {
            return null;
        }
        TreeSetData<E>.SetNode node = findExact(data.getRoot(), ceilingRes);  // Won't be null because
        // this element was found in the Set by ceiling.
        if (node.compareTo(e) == 0) {
            node = node.next();
        }
        return node == null ? null : node.getValue();
    }


    /** {@link java.util.TreeSet#first()} **/
    @Override
    @NotNull
    public E first() {
        if (isEmpty()) {
            throw new NoSuchElementException("Attempting to get the first element of the empty TreeSet");
        }
        return this.iterator().next();
    }


    /** {@link java.util.TreeSet#last()} **/
    @Override
    @NotNull
    public E last() {
        if (isEmpty()) {
            throw new NoSuchElementException("Attempting to get the last element of the empty TreeSet");
        }
        return this.descendingIterator().next();
    }
}
