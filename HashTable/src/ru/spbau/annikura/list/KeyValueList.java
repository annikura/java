package ru.spbau.annikura.list;

/**
 * A list storing key-value pairs, can search through itself, add or erase elements by key.
 *
 * @param <KeyType>
 * @param <ValueType>
 */
public class KeyValueList<KeyType, ValueType> {
  private ListNode<Pair<KeyType, ValueType>> head =
      new ListNode<>(null);

  /**
   *
   * @return first list node.
   */
  public ListNode<Pair<KeyType, ValueType>> begin() {
    return head.next();
  }

  /**
   * @param key
   * @return a Pair instance with a corresponding key-value pair.
   * Returns null if such an element does not exist in the list.
   */
  public Pair<KeyType, ValueType> find(KeyType key) {
    ListNode<Pair<KeyType, ValueType>> foundNode = findListNode(key);
    if (foundNode == null) {
      return null;
    }
    return foundNode.getValue();
  }

  /** Finds a list containing the required key.
   *
   * @param key
   * @return a list node containing the given key.
   * Returns null if there is no such a key in the list.
   */
  private ListNode<Pair<KeyType, ValueType>> findListNode(KeyType key) {
    for (ListNode<Pair<KeyType, ValueType>> it = head.next(); it != null; it = it.next()) {
      if (it.getValue().GetKey() == key)
        return it;
    }
    return null;
  }

  /** Inserts Pair with given data into the list. If such a key already existed in the list,
   * replaces the value.
   *
   * @param key
   * @param value
   * @return null if key didn't exist in the list or the previous value if it did.
   */
  public ValueType addOrAssign(KeyType key, ValueType value) {
    ListNode<Pair<KeyType, ValueType>> foundNode = findListNode(key);
    if (foundNode != null) {
      ValueType oldValue = foundNode.getValue().GetValue();
      foundNode.getValue().SetValue(value);
      return oldValue;
    }
    ListNode<Pair<KeyType, ValueType>> newNode =
        new ListNode<>(new Pair<>(key, value));
    head.insertAfter(newNode);
    return null;
  }

  /**
   * Removes an Pair with a given key from the list.
   * @param key
   * @return value of the erased pair. Returns null is such a key didn't exist.
   */
  public ValueType remove(KeyType key) {
    ListNode<Pair<KeyType, ValueType>> foundNode = findListNode(key);
    if (foundNode != null) {
      return foundNode.previous().eraseAfter().GetValue();
    }
    return null;
  }

}
