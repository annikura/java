package ru.spbau.annikura.treeset;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public interface MyTreeSet<E> extends Set<E> {

    /** {@link java.util.TreeSet#descendingIterator()} **/
    Iterator<E> descendingIterator();

    /** {@link java.util.TreeSet#descendingSet()} **/
    MyTreeSet<E> descendingSet();


    /** {@link java.util.TreeSet#first()} **/
    E first();

    /** {@link java.util.TreeSet#last()} **/
    E last();


    /** {@link java.util.TreeSet#lower(Object)} **/
    E lower(E e);

    /** {@link java.util.TreeSet#floor(Object)} **/
    E floor(E e);


    /** {@link java.util.TreeSet#ceiling(Object)} **/
    E ceiling(E e);

    /** {@link java.util.TreeSet#higher(Object)} **/
    E higher(E e);
}