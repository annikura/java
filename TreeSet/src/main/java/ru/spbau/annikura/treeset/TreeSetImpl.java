package ru.spbau.annikura.treeset;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractSet;
import java.util.NoSuchElementException;

abstract class TreeSetImpl<E> extends AbstractSet<E> implements MyTreeSet<E> {
    TreeSetData<E> data;

    public boolean add(@NotNull E e) {
        TreeSetData<E>.SetNode node = findClosest(data.getRoot(), e);
        if (node != null && data.getComparator().compare(node.getValue(), e) == 0) {
            return false;
        }
        TreeSetData<E>.SetNode newNode = data.new SetNode(e);
        if (node == null) {
            data.setRoot(newNode);
            data.getList().add(newNode);
            return true;
        }
        if (data.getComparator().compare(node.getValue(), e) < 0) {
            node.setRightNode(newNode);
        } else {
            node.setLeftNode(newNode);
        }
        return true;
    }

    @Override
    public int size() {
        return data.getList().size();
    }

    @Nullable
    private TreeSetData<E>.SetNode findExact(@Nullable TreeSetData<E>.SetNode node, @NotNull E e) {
        if (node == null) {
            return null;
        }
        if (data.getComparator().compare(e, node.getValue()) == 0)
            return node;
        return findExact(node.down(e), e);
    }

    @Nullable
    private TreeSetData<E>.SetNode findClosest(@Nullable TreeSetData<E>.SetNode node, @NotNull E e) {
        if (node == null) {
            return null;
        }
        if (data.getComparator().compare(e, node.getValue()) == 0) {
            return node;
        }
         return node.down(e) != null ? findClosest(node.down(e), e) : node;
    }

    @Nullable
    @Override
    public E floor(@NotNull final E e) {
        TreeSetData<E>.SetNode node = findClosest(data.getRoot(), e);
        if (node == null) {
            return null;
        }
        if (data.getComparator().compare(node.getValue(), e) <= 0) {
            return node.getValue();
        }
        node = node.left();
        return node == null ? null : node.getValue();
    }

    @Override
    @Nullable
    public E lower(@NotNull E e) {
        E floorRes = floor(e);
        if (floorRes == null) {
            return null;
        }
        TreeSetData<E>.SetNode node = findExact(data.getRoot(), floorRes).left();  // Won't be null because
        // this element was found in the Set by floor.
        return node == null ? null : node.getValue();
    }

    @Override
    @Nullable
    public E ceiling(@NotNull E e) {
        TreeSetData<E>.SetNode node = findClosest(data.getRoot(), e);
        if (node == null) {
            return null;
        }
        if (data.getComparator().compare(node.getValue(), e) >= 0) {
            return node.getValue();
        }
        node = node.right();
        return node == null ? null : node.getValue();
    }

    @Override
    @Nullable
    public E higher(@NotNull E e) {
        E ceilingRes = ceiling(e);
        if (ceilingRes == null) {
            return null;
        }
        TreeSetData<E>.SetNode node = findExact(data.getRoot(), ceilingRes).right();  // Won't be null because
        // this element was found in the Set by ceiling.
        return node == null ? null : node.getValue();
    }

    @Override
    public E first() {
        if (isEmpty()) {
            throw new NoSuchElementException("Attempting to get the first element of the empty TreeSet");
        }
        return this.iterator().next();
    }

    @Override
    public E last() {
        if (isEmpty()) {
            throw new NoSuchElementException("Attempting to get the last element of the empty TreeSet");
        }
        return this.descendingIterator().next();
    }


}
