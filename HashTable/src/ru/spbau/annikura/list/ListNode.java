/**
 * Created by annikura on 9/17/17.
 * @author Anna Shvetsova
 */

package ru.spbau.annikura.list;

// Bidirected list node class
public class ListNode<ValueType> {
  private ListNode<ValueType> next = null;
  private ListNode<ValueType> previous = null;
  private final ValueType value;

  public ListNode(ValueType value) {
    this.value = value;
  }

  public ValueType getValue() {
    return value;
  }
  public ListNode<ValueType> next() {
    return next;
  }

  public ListNode<ValueType> previous() {
    return previous;
  }

  public ValueType insertAfter(ListNode<ValueType> otherNode) {
    otherNode.next = next;
    next = otherNode;
    otherNode.previous = this;
    if (otherNode.next != null) {
      otherNode.next.previous = otherNode;
    }
    return next.value;
  }

  public ValueType eraseAfter() {
    if (next == null) {
      return null;
    }
    ValueType erasedValue = next.value;
    next = next.next;
    next.previous = this;
    return erasedValue;
  }

}
