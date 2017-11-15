package ru.spbau.annikura.treeset;

import java.util.Iterator;

class StraightTreeSet<E> extends TreeSetImpl<E> {
    private final ReverseTreeSet<E> REVERSE_SET;

    StraightTreeSet(TreeSetData<E> data) {
        this.data = data;
        REVERSE_SET = new ReverseTreeSet<>(data, this);
    }

    /**
     * {@link TreeSet#descendingIterator()}
     **/
    @Override
    public Iterator<E> descendingIterator() {
        return REVERSE_SET.iterator();
    }

    /**
     * {@link TreeSet#descendingSet()}
     **/
    @Override
    public MyTreeSet<E> descendingSet() {
        return REVERSE_SET;
    }

    @Override
    public Iterator<E> iterator() {
        return data.getList().iterator();
    }
}
