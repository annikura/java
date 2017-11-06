package ru.spbau.annikura.list;

/**
 * Bidirected list node class
 *
 * @param <ValueType>
 */
class ListNode<ValueType> {
  private ListNode<ValueType> next = null;
  private ListNode<ValueType> previous = null;
  private final ValueType value;

  ListNode(ValueType value) {
    this.value = value;
  }

  /**
   * @return value which is stored in the list node.
   */
  ValueType getValue() {
    return value;
  }
  ListNode<ValueType> next() {
    return next;
  }

  /**
   * @return the next list node or null if the next node doesn't exist.
   */
  ListNode<ValueType> previous() {
    return previous;
  }

  /** Inserts a given node next to the current one.
   *
   * @param otherNode a node to be placed next after the current
   * @return new list node value.
   */
  ValueType insertAfter(ListNode<ValueType> otherNode) {
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
  ValueType eraseAfter() {
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
