/**
 * Created by annikura on 9/16/17.
 * @author Anna Shvetsova
 */

package ru.spbau.annikura.list;


import ru.spbau.annikura.list.ListNode;
import ru.spbau.annikura.list.Entry;

public class KeyValueList<KeyType, ValueType> {
  private ListNode<Entry<KeyType, ValueType>> head =
      new ListNode<Entry<KeyType, ValueType>>(null);

  public ValueType find(KeyType key) {
    ListNode<Entry<KeyType, ValueType>> foundNode = findListNode(key);
    if (foundNode == null) {
      return null;
    }
    return foundNode.getValue().value;
  }

  private ListNode<Entry<KeyType, ValueType>> findListNode(KeyType key) {
    for (ListNode<Entry<KeyType, ValueType>> it = head.next(); it != null; it = it.next()) {
      if (it.getValue().key == key)
        return it;
    }
    return null;
  }

  public ValueType addOrAssign(KeyType key, ValueType value) {
    ListNode<Entry<KeyType, ValueType>> foundNode = findListNode(key);
    if (foundNode != null) {
      ValueType oldValue = foundNode.getValue().value;
      foundNode.getValue().value = value;
      return oldValue;
    }
    ListNode<Entry<KeyType, ValueType>> newNode =
        new ListNode<Entry<KeyType, ValueType>>(new Entry<KeyType, ValueType>(key, value));
    head.insertAfter(newNode);
    return null;
  }

  public ValueType remove(KeyType key) {
    ListNode<Entry<KeyType, ValueType>> foundNode = findListNode(key);
    if (foundNode != null) {
      return foundNode.previous().eraseAfter().value;
    }
    return null;
  }

}
