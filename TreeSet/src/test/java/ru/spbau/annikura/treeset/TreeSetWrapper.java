package ru.spbau.annikura.treeset;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

import static junit.framework.TestCase.*;

public class TreeSetWrapper<E extends Comparable<E>> {
    private TreeSet<E> treeSet = new TreeSet<>();
    private java.util.TreeSet<E> tree = new java.util.TreeSet<>();

    void add(@NotNull E e) {
        assertEquals(tree.add(e), treeSet.add(e));
        assertEquals(false, treeSet.add(e));
        assertEquals(tree.size(), treeSet.size());

        Iterator<E> it = tree.iterator();
        for (E el : treeSet) {
            assertEquals(it.next(), el);
        }

        it = tree.descendingIterator();
        for (E el : treeSet.descendingSet()) {
            assertEquals(it.next(), el);
        }

        it = tree.iterator();
        for (E el : treeSet.descendingSet().descendingSet()) {
            assertEquals(it.next(), el);
        }
    }
}
