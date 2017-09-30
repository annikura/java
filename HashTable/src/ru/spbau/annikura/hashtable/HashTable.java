package ru.spbau.annikura.hashtable;

import java.security.Key;
import java.util.Vector;
import ru.spbau.annikura.list.Pair;
import ru.spbau.annikura.list.KeyValueList;
import ru.spbau.annikura.list.ListNode;

/**
 * String hash table. Allows containing string values as the counterparts to the string keys.
 * Elements can be added to, found in or removed from the table.
 */
public class HashTable {
  private KeyValueList<String, String>[] tableContent = new KeyValueList[1];
  private int tableSize = 0;

  public HashTable() {
    initList();
  }

  private void initList() {
    for (int i = 0; i < tableContent.length; i++) {
      if (tableContent[i] == null) {
        tableContent[i] = new KeyValueList<>();
      }
    }
  }

  private int countIndex(String str) {
    if (str != null)
      return (str.hashCode() % tableContent.length + tableContent.length) % tableContent.length;
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
    KeyValueList<String, String>[] oldTable = tableContent;
    tableContent = new KeyValueList[newSize];
    initList();
    for (KeyValueList<String, String> list : oldTable) {
      for (ListNode<Pair<String, String>> it = list.begin(); it != null; it = it.next()) {
          tableContent[countIndex(it.getValue().GetKey())].addOrAssign(
              it.getValue().GetKey(),
              it.getValue().GetValue());
      }
    }
  }

  /**
   * @return number of keys in a current table
   */
  public int size() {
    return tableSize;
  }

  /**
   * @param key
   * @return true if such a key exists in the hash table.
   */
  public boolean contains(String key) {
    return tableContent[countIndex(key)].find(key) != null;
  }

  /**
   * @param key
   * @return value if such a key exists in the hash table, null otherwise.
   */
  public String get(String key) {
    if (contains(key)) {
      return tableContent[countIndex(key)].find(key).GetValue();
    }
    return null;
  }

  /**
   * Puts value under the given key into the hash table.
   *
   * @param key
   * @param value
   * @return a previous value, if there was such a key in the table, null if not.
   */
  public String put(String key, String value) {
    if (tableContent[countIndex(key)].find(key) == null) {
      tableSize++;
    }
    if (2 * tableSize >= tableContent.length) {
      resizeTable(tableContent.length * 2);
    }
    return tableContent[countIndex(key)].addOrAssign(key, value);
  }

  /**
   * Removes key-value pair with the given key from the hash table.
   *
   * @param key
   * @return the deleted value, if such a key existed. Otherwise returns null.
   */
  public String remove(String key) {
    if (tableContent[countIndex(key)].find(key) != null) {
      tableSize--;
    }
    return tableContent[countIndex(key)].remove(key);
  }

  /**
   * Clears the hash table.
   */
  public void clear() {
    tableContent = new KeyValueList[1];
    initList();
    tableSize = 0;
  }
}
