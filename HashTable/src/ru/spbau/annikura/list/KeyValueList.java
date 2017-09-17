/**
 * Created by annikura on 9/16/17.
 * @author Anna Shvetsova
 */

package ru.spbau.annikura.list;

/**
 * A list storing key-value pairs, can search through itself, add or erase elements by key.
 *
 * @param <KeyType>
 * @param <ValueType>
 */
public class KeyValueList<KeyType, ValueType> {
  private ListNode<Entry<KeyType, ValueType>> head =
      new ListNode<>(null);

  /** Returns a value of the element with equal to the given key.
   *
   * @param key
   * @return Returns null if such an element does not exist in the list.
   */
  public Entry<KeyType, ValueType> find(KeyType key) {
    ListNode<Entry<KeyType, ValueType>> foundNode = findListNode(key);
    if (foundNode == null) {
      return null;
    }
    return foundNode.getValue();
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
        new ListNode<>(new Entry<>(key, value));
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
