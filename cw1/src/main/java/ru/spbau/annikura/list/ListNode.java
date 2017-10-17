package ru.spbau.annikura.list;

/**
 * Bidirected list node class
 *
 * @param <ValueType>
 */
public class ListNode<ValueType> {
    private ListNode<ValueType> next = null;
    private ListNode<ValueType> previous = null;
    private ValueType value = null;

    /**
     * @return value which is stored in the list node.
     */
    public ValueType getValue() {
        return value;
    }
    public void setValue(ValueType value) { this.value = value; }
    public ListNode<ValueType> next() {
        return next;
    }

    /**
     * @return the next list node or null if the next node doesn't exist.
     */
    public ListNode<ValueType> previous() {
        return previous;
    }

    /** Inserts a given node next to the current one.
     *
     * @param otherNode a node to be placed next after the current
     * @return new list node value.
     */
    public ValueType insertAfter(ListNode<ValueType> otherNode) {
        otherNode.next = next;
        next = otherNode;
        otherNode.previous = this;
        if (otherNode.next != null) {
            otherNode.next.previous = otherNode;
        }
        return next.value;
    }

    /**
     * Erases the vertex next to current one.
     *
     * @return the value of the deleted vertex. Null if the next vertex does not exist.
     */
    public ValueType eraseAfter() {
        if (next == null) {
            return null;
        }
        ValueType erasedValue = next.value;
        next = next.next;
        if (next != null) {
            next.previous = this;
        }
        return erasedValue;
    }

}