/**
 * Created by annikura on 9/16/17.
 * @author Anna Shvetsova
 */

package ru.spbau.annikura.list;

import java.util.List;

/**
 * A list storing key-value pairs, can search through itself, add or erase elements by key.
 *
 * @param <KeyType>
 * @param <ValueType>
 */
public class KeyValueList<KeyType, ValueType> {
  private ListNode<Entry<KeyType, ValueType>> head =
      new ListNode<>(null);

  /**
   *
   * @return First list node.
   */
  public ListNode<Entry<KeyType, ValueType>> begin() {
    return head.next();
  }

  /**
   * @param key
   * @return Returns an Entry instance with a corresponding key-value pair.
   * Returns null if such an element does not exist in the list.
   */
  public Entry<KeyType, ValueType> find(KeyType key) {
    ListNode<Entry<KeyType, ValueType>> foundNode = findListNode(key);
    if (foundNode == null) {
      return null;
    }
    return foundNode.getValue();
  }

  /** Finds a list containing the required key.
   *
   * @param key
   * @return Returns a list node containing the given key.
   * Returns null if there is no such a key in the list.
   */
  private ListNode<Entry<KeyType, ValueType>> findListNode(KeyType key) {
    for (ListNode<Entry<KeyType, ValueType>> it = head.next(); it != null; it = it.next()) {
      if (it.getValue().key == key)
        return it;
    }
    return null;
  }

  /** Inserts Entry with given data into the list. If such a key already existed in the list,
   * replaces the value.
   *
   * @param key
   * @param value
   * @return Null if key didn't exist in the list or the previous value if it did.
   */
  public ValueType addOrAssign(KeyType key, ValueType value) {
    ListNode<Entry<KeyType, ValueType>> foundNode = findListNode(key);
    if (foundNode != null) {
      ValueType oldValue = foundNode.getValue().value;
      foundNode.getValue().value = value;
      return oldValue;
    }
    ListNode<Entry<KeyType, ValueType>> newNode =
        new ListNode<>(new Entry<>(key, value));
    head.insertAfter(newNode);
    return null;
  }

  /**
   * Removes an Entry with a given key from the list.
   * @param key
   * @return Returns value of the erased pair. Returns null is such a key didn't exist.
   */
  public ValueType remove(KeyType key) {
    ListNode<Entry<KeyType, ValueType>> foundNode = findListNode(key);
    if (foundNode != null) {
      return foundNode.previous().eraseAfter().value;
    }
    return null;
  }

}
