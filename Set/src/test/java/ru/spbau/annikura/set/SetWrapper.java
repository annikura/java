package ru.spbau.annikura.set;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class SetWrapper<T extends Comparable<T>> {
    private Set<T> set = new Set<T>();
    private HashSet<T> dictionary = new HashSet<T>();
    private HashSet<T> container = new HashSet<T>();

    public void add(T t) {
        dictionary.add(t);
        container.add(t);
        set.add(t);
    }

    public void delete(T t) {
        container.remove(t);
        set.delete(t);
    }

    public boolean contains(T t) {
        assertEquals(container.contains(t), set.contains(t));
        return set.contains(t);
    }

    public void check() {
        for (T el : dictionary) {
            assertEquals(container.contains(el), set.contains(el));
        }
        assertEquals(container.size(), set.size());
    }
}
