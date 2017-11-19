package ru.spbau.annikura.treeset;

import java.util.*;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

/**
 * Tree-structured storage of elements of type E.
 * Stores its elements in a binary tree.
 * All operations work in a O(height).
 * @param <E> type of comparable elements to be stored.
 */
public class TreeSet<E extends Comparable<E>> extends AbstractSet<E> implements MyTreeSet<E> {
    private final TreeSetData<E> data;
    private AbstractTreeSetImpl<E> treeSet;

    /**
     * Constructor of the tree set supporting natural ordering.
     */
    public TreeSet() {
        data = TreeSetData.genNewData();
        treeSet = new StraightTreeSet<>(data);
    }

    /**
     * Adds into the tree an element e.
     * @param e Value to be added to the tree.
     * @return false if this value already was in the tree. Returns true and adds a value to the tree otherwise.
     */
    @Override
    public boolean add(@NotNull E e) {
        return treeSet.add(e);
    }


    /**
     * Checks element's presence in the tree.
     * @param e Value to be searched in the tree.
     * @return false if this value already was in the tree, true otherwise.
     */
    public boolean containing(@NotNull E e) {
        return treeSet.containing(e);
    }


    /**
     * Iterator for the Tree set. Note, that if the current tree set is a result of "descending set" method,
     * this iterator will iterate in the reverse order in comparison to its ancestor.
     * @return straight iterator.
     */
    @Override
    @NotNull
    public Iterator<E> iterator() {
        return treeSet.iterator();
    }

    /**
     * Size of the tree set.
     * @return number of elements in the set.
     */
    @Override
    public int size() {
        return treeSet.size();
    }


    /**
     * Constructor of the tree set which structure is based on the comparator.
     */
    public TreeSet(@NotNull Comparator<E> cmp) {
        data = TreeSetData.genNewData(cmp);
        treeSet = new StraightTreeSet<>(data);
    }

    /**
     * Provides a reverse tree set iterator.
     * @return iterator to iterate in reverse in relation to the iterator() order.
     */
    @Override
    @NotNull
    public Iterator<E> descendingIterator() {
        return treeSet.iterator();
    }

    /**
     * Provides a tree set all iterators of which are reversed.
     * @return a tree set all iterators of which are reversed.
     */
    @Override
    @NotNull
    public MyTreeSet<E> descendingSet() {
        return treeSet.descendingSet();
    }

    /**
     * First element of the tree set.
     * @return fist element in iteration order.
     */
    @Override
    @NotNull
    public E first() {
        return treeSet.first();
    }


    /**
     * Last element of the tree set.
     * @return last element in iteration order.
     */
    @Override
    @NotNull
    public E last() {
        return treeSet.last();
    }

    /**
     * Given an element, returns the biggest element strictly less than a given one contained in the tree set.
     * @param e an element to compare with.
     * @return biggest element in the tree set strictly less than a given one.
     */
    @Override
    @Nullable
    public E lower(@NotNull E e) {
        return treeSet.lower(e);
    }


    /**
     * Given an element, returns the greatest element less or equal to a given one contained in the tree set.
     * @param e an element to compare with.
     * @return greatest element in the tree set less or equal to a given one.
     */
    @Override
    @Nullable
    public E floor(@NotNull E e) {
        return treeSet.floor(e);
    }

    /**
     * Given an element, returns the least element greater or equal to a given one contained in the tree set.
     * @param e an element to compare with.
     * @return least element in the tree set greater or equal to a given one.
     */
    @Override
    @Nullable
    public E ceiling(@NotNull E e) {
        return treeSet.ceiling(e);
    }


    /**
     * Given an element, returns the least element strictly greater than a given one contained in the tree set.
     * @param e an element to compare with.
     * @return least element in the tree set strictly greater than a given one.
     */
    @Override
    @Nullable
    public E higher(@NotNull E e) {
        return treeSet.higher(e);
    }
}