package ru.spbau.annikura.treeset;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * A tree set which main iteration order is natural.
 * @param <E> stored type.
 */
class StraightTreeSet<E> extends AbstractTreeSetImpl<E> {
    private final ReverseTreeSet<E> REVERSE_SET;

    /**
     * Constructs a tree set based of the given data set.
     * @param data pointers to the set data.
     */
    StraightTreeSet(@NotNull TreeSetData<E> data) {
        this.data = data;
        REVERSE_SET = new ReverseTreeSet<>(data, this);
    }


    /** {@link TreeSet#descendingIterator()} **/
    @Override
    @NotNull
    public Iterator<E> descendingIterator() {
        return REVERSE_SET.iterator();
    }

    /** {@link TreeSet#descendingSet()} **/
    @Override
    @NotNull
    public MyTreeSet<E> descendingSet() {
        return REVERSE_SET;
    }

    /** {@link TreeSet#iterator()} **/
    @Override
    @NotNull
    public Iterator<E> iterator() {
        return data.straightIterator();
    }
}
