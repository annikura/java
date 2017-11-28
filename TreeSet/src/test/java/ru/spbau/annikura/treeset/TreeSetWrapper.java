package ru.spbau.annikura.treeset;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Iterator;

import static junit.framework.TestCase.*;

public class TreeSetWrapper<E extends Comparable<E>> {
    private TreeSet<E> myTree = new TreeSet<>();
    private java.util.TreeSet<E> tree;

    TreeSetWrapper() {
        tree = new java.util.TreeSet<>();
    }

    TreeSetWrapper(Comparator<E> cmp) {
        tree = new java.util.TreeSet<>(cmp);
    }

    void add(@NotNull E e) {
        assertEquals(tree.add(e), myTree.add(e));
        assertEquals(false, myTree.add(e));
    }

    void first() {
        assertEquals(tree.first(), myTree.first());
    }

    void last() {
        assertEquals(tree.last(), myTree.last());
    }

    void lower(@NotNull E e) {
        assertEquals(tree.lower(e), myTree.lower(e));
    }

    void higher(@NotNull E e) {
        assertEquals(tree.higher(e), myTree.higher(e));
    }

    void ceiling(@NotNull E e) {
        assertEquals(tree.ceiling(e), myTree.ceiling(e));
    }

    void floor(@NotNull E e) {
        assertEquals(tree.floor(e), myTree.floor(e));
    }

    void check() {

        assertEquals(tree.size(), myTree.size());

        Iterator<E> it = tree.iterator();
        for (E el : myTree) {
            assertEquals(it.next(), el);
        }
        assertEquals(false, it.hasNext());

        it = tree.descendingIterator();
        for (E el : myTree.descendingSet()) {
            assertEquals(it.next(), el);
        }
        assertEquals(false, it.hasNext());

        it = tree.iterator();
        for (E el : myTree.descendingSet().descendingSet()) {
            assertEquals(it.next(), el);
        }
        assertEquals(false, it.hasNext());

        for (E el : tree) {
            assertEquals(true, myTree.containing(el));
        }
    }
}
