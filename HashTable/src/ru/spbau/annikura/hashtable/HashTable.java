/**
 * Created by annikura on 9/16/17.
 * @author Anna Shvetsova
 */

package ru.spbau.annikura.hashtable;

import java.util.ArrayList;
import ru.spbau.annikura.list.Entry;
import ru.spbau.annikura.list.KeyValueList;

public class HashTable {
  private ArrayList<KeyValueList<String, String>> tableContent = new ArrayList<>();
  private int tableSize = 0;

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
    return get(key) != null;
  }

  /**
   * @param key
   * @return Value if such a key exists in the hash table, null otherwise.
   */
  public Entry get(String key) {
    return tableContent.get(key.hashCode() % tableContent.size()).find(key);
  }

  /**
   * Puts value under the given key into the hash table.
   *
   * @param key
   * @param value
   * @return If there was such a key in the table, returns a previous value, null if not.
   */
  public String put(String key, String value) {
    String oldValue = tableContent.get(key.hashCode() % tableContent.size()).addOrAssign(key, value);
    if (oldValue == null) {
      tableSize++;
    }
    return oldValue;
  }

  /**Removes key-value pair with the given key from the hash table.
   *
   * @param key
   * @return If such a key existed, returns the deleted value. Otherwise return null.
   */
  public String remove(String key) {
    String oldValue = tableContent.get(key.hashCode() % tableContent.size()).remove(key);
    if (oldValue != null) {
      tableSize--;
    }
    return oldValue;
  }

  /**
   * Clears the hash table.
   */
  public void clear() {
    tableContent = new ArrayList<KeyValueList<String, String>>();
    tableSize = 0;
  }
}
