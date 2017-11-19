package ru.spbau.annikura.treeset;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * Tree set which iterates through the values in the reverse order.
 * @param <E>
 */
class ReverseTreeSet<E> extends AbstractTreeSetImpl<E> {
    private final StraightTreeSet<E> STRAIGHT_SET;

    /**
     * Constructs the tree set with a given data set.
     * @param data pointers to the data available to the set.
     * @param es Tree set with a straight iteration which should be returned by the descendingSet method.
     */
    public ReverseTreeSet(@NotNull TreeSetData<E> data, @NotNull StraightTreeSet<E> es) {
        STRAIGHT_SET = es;
        this.data = data;
    }

    /** {@link TreeSet#descendingIterator()} **/
    @NotNull
    @Override
    public Iterator<E> descendingIterator() {
        return STRAIGHT_SET.iterator();
    }

    /** {@link TreeSet#descendingSet()} **/
    @NotNull
    @Override
    public MyTreeSet<E> descendingSet() {
        return STRAIGHT_SET;
    }

    /** {@link TreeSet#iterator()} **/
    @NotNull
    @Override
    public Iterator<E> iterator() {
        return data.descendingIterator();
    }
}
