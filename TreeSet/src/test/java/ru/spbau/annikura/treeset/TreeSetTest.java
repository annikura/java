package ru.spbau.annikura.treeset;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class TreeSetTest {
    @Test
    public void createInstance() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>();
    }

    @Test
    public void createInstanceWithComparator() throws Exception {
        TreeSet<Integer> tree = new TreeSet<>((a, b) -> b - a);
    }

    @Test
    public void simpleAdd() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        tree.add(5);
    }


    @Test
    public void randomAdd() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        for (int i = 0; i < 1000; i++) {
            tree.add(i);
        }
    }

    @Test
    public void iterator() throws Exception {

    }

    @org.junit.Test
    public void size() throws Exception {
    }

    @org.junit.Test
    public void descendingIterator() throws Exception {
    }

    @org.junit.Test
    public void descendingSet() throws Exception {
    }

    @org.junit.Test
    public void first() throws Exception {
    }

    @org.junit.Test
    public void last() throws Exception {
    }

    @org.junit.Test
    public void lower() throws Exception {
    }

    @org.junit.Test
    public void floor() throws Exception {
    }

    @org.junit.Test
    public void ceiling() throws Exception {
    }

    @org.junit.Test
    public void higher() throws Exception {
    }

}