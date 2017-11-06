package ru.spbau.annikura.set;

/**
 * Tree container class. Stores data in a search tree structure.
 * @param <T>
 */
public class Set<T extends Comparable<T>> {
    /**
     * Search tree node class.
     */
    private class SetNode {
        private SetNode left = null;
        private SetNode right = null;
        private final T value;

        SetNode(final T t) {
            value = t;
        }
    }

    private int size = 0;
    private SetNode root = null;

    private SetNode merge(SetNode left, SetNode right) {
        if (left == null)
            return right;
        if (right == null)
            return left;
        SetNode rightest = left;
        while (rightest.right != null) {
            rightest = rightest.right;
        }
        rightest.right = right;
        return left;
    }

    private SetNode add(final T t, SetNode currentNode) {
        if (currentNode == null) {
            size++;
            return new SetNode(t);
        }
        if (currentNode.value.compareTo(t) == 0)
            return currentNode;
        if (currentNode.value.compareTo(t) < 0) {
            currentNode.right = add(t, currentNode.right);
        } else {
            currentNode.left = add(t, currentNode.left);
        }
        return currentNode;
    }

    private SetNode delete(final T t, SetNode currentNode) {
        if (currentNode == null) {
            return null;
        }
        if (currentNode.value.compareTo(t) == 0) {
            size--;
            return merge(currentNode.left, currentNode.right);
        }
        if (currentNode.value.compareTo(t) < 0) {
            currentNode.right = delete(t, currentNode.right);
        } else {
            currentNode.left = delete(t, currentNode.left);
        }
        return currentNode;
    }

    private boolean contains(final T t, SetNode currentNode) {
        if (currentNode == null) {
            return false;
        }
        if (currentNode.value.compareTo(t) == 0) {
            return true;
        }
        if (currentNode.value.compareTo(t) < 0) {
            return contains(t, currentNode.right);
        } else {
            return contains(t, currentNode.left);
        }
    }

    /**
     * Size getter method
     * @return number of elements in the set.
     */
    public int size() {
        return size;
    }

    /**
     * Adds a value to the set. Does nothing if this value already existed.
     * @param t a value to be added.
     */
    public void add(final T t) {
        root = add(t, root);
    }

    /**
     * Deletes value from the list. Does nothing if the value didn't exist.
     * @param t a value to be deleted.
     */
    public void delete(final T t) {
        root = delete(t, root);
    }

    /**
     * Checks whether the value is stored by the set.
     * @param t element whose presence in this set is to be tested.
     * @return true if t is in the set, false otherwise.
     */
    public boolean contains(final T t) {
        return contains(t, root);
    }
}