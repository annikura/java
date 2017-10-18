package ru.spbau.annikura.set;

public class Set<T extends Comparable<T>> {
    private class SetNode {
        private SetNode left = null;
        private SetNode right = null;
        T value;

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

    public int size() {
        return size;
    }

    public void add(final T t) {
        root = add(t, root);
    }

    public void delete(final T t) {
        root = delete(t, root);
    }

    public boolean contains(final T t) {
        return contains(t, root);
    }
}