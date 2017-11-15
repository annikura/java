package ru.spbau.annikura.treeset;

import java.util.*;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;


public class TreeSet<E extends Comparable<E>> extends AbstractSet<E> implements MyTreeSet<E> {
    private final TreeSetData<E> data;
    private TreeSetImpl<E> treeSet;

    TreeSet() {
        data = TreeSetData.genNewData();
        treeSet = new StraightTreeSet<>(data);
    }

    @Override
    @NotNull
    public boolean add(@NotNull E e) {
        return treeSet.add(e);
    }

    @Override
    @NotNull
    public Iterator<E> iterator() {
        return treeSet.iterator();
    }

    @Override
    public int size() {
        return treeSet.size();
    }

    TreeSet(Comparator<E> cmp) {
        data = TreeSetData.genNewData(cmp);
        treeSet = new StraightTreeSet<>(data);
    }

    @Override
    @NotNull
    public Iterator<E> descendingIterator() {
        return treeSet.iterator();
    }

    @Override
    @NotNull
    public MyTreeSet<E> descendingSet() {
        return treeSet.descendingSet();
    }

    @Override
    @NotNull
    public E first() {
        return treeSet.first();
    }

    @Override
    @NotNull
    public E last() {
        return treeSet.last();
    }

    @Override
    @Nullable
    public E lower(@NotNull E e) {
        return treeSet.lower(e);
    }

    @Override
    @Nullable
    public E floor(@NotNull E e) {
        return treeSet.floor(e);
    }

    @Override
    @Nullable
    public E ceiling(@NotNull E e) {
        return ceiling(e);
    }

    @Override
    @Nullable
    public E higher(@NotNull E e) {
        return higher(e);
    }
}