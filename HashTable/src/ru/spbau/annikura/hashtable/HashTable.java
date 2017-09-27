/**
 * Created by annikura on 9/16/17.
 * @author Anna Shvetsova
 */

package ru.spbau.annikura.hashtable;

import java.util.Vector;
import ru.spbau.annikura.list.Pair;
import ru.spbau.annikura.list.KeyValueList;
import ru.spbau.annikura.list.ListNode;

/**
 * String hash table. Allows containing string values as the counterparts to the string keys.
 * Elements can be added to, found in or removed from the table.
 */
public class HashTable {
  private Vector<KeyValueList<String, String>> tableContent = new Vector<>();
  private int tableSize = 0;

  private int countIndex(String str) {
    if (str != null)
      return str.hashCode() % tableContent.size();
    return 0;
  }

  /** If newSize > 0, resizes the table to newSize size. If newSize equals 0, nothing will be done.
   *
   * @param newSize new size of the table
   */
  private void resizeTable(int newSize) {
    if (newSize == 0) {
      return;
    }
    Vector<KeyValueList<String, String>> oldTable = tableContent;
    tableContent = new Vector<>(newSize);
    for (int i = 0; i < newSize; i++)
      tableContent.add(new KeyValueList<>());

    for (KeyValueList<String, String> list : oldTable) {
      for (ListNode<Pair<String, String>> it = list.begin(); it != null; it = it.next()) {
          tableContent.get(countIndex(it.getValue().GetKey())).addOrAssign(
              it.getValue().GetKey(),
              it.getValue().GetValue());
      }
    }
  }

  public HashTable() {
    tableContent.add(new KeyValueList<>());
  }

  /**
   * @return Number of keys in a current table
   */
  public int size() {
    return tableSize;
  }

  /**
   * @param key
   * @return Returns true if such a key exists in the hash table.
   */
  public boolean contains(String key) {
    return tableContent.get(countIndex(key)).find(key) != null;
  }

  /**
   * @param key
   * @return Value if such a key exists in the hash table, null otherwise.
   */
  public String get(String key) {
    if (contains(key)) {
      return tableContent.get(countIndex(key)).find(key).GetValue();
    }
    return null;
  }

  /**
   * Puts value under the given key into the hash table.
   *
   * @param key
   * @param value
   * @return If there was such a key in the table, returns a previous value, null if not.
   */
  public String put(String key, String value) {
    if (tableContent.get(countIndex(key)).find(key) == null) {
      tableSize++;
    }
    if (2 * tableSize >= tableContent.size()) {
      resizeTable(tableContent.size() * 2);
    }
    return tableContent.get(countIndex(key)).addOrAssign(key, value);
  }

  /**
   * Removes key-value pair with the given key from the hash table.
   *
   * @param key
   * @return If such a key existed, returns the deleted value. Otherwise return null.
   */
  public String remove(String key) {
    if (tableContent.get(countIndex(key)).find(key) != null) {
      tableSize--;
    }
    return tableContent.get(countIndex(key)).remove(key);
  }

  /**
   * Clears the hash table.
   */
  public void clear() {
    tableContent = new Vector<>();
    tableContent.add(new KeyValueList<>());
    tableSize = 0;
  }
}
