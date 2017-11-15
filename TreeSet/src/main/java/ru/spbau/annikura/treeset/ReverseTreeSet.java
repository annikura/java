package ru.spbau.annikura.treeset;

import java.util.Iterator;

public class ReverseTreeSet<E> extends TreeSetImpl<E> {
    private final StraightTreeSet<E> STRAIGHT_SET;

    public ReverseTreeSet(TreeSetData<E> data, StraightTreeSet<E> es) {
        STRAIGHT_SET = es;
        this.data = data;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return STRAIGHT_SET.iterator();
    }

    @Override
    public MyTreeSet<E> descendingSet() {
        return STRAIGHT_SET;
    }

    @Override
    public Iterator<E> iterator() {
        return data.getList().descendingIterator();
    }

    @Override
    public int size() {
        return 0;
    }
}
