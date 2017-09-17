/**
 * Created by annikura on 9/16/17.
 * @author Anna Shvetsova
 */

package ru.spbau.annikura.hashtable;

import java.util.ArrayList;
import org.testng.internal.collections.Pair;
import ru.spbau.annikura.list.KeyValueList;

public class HashTable {
  private ArrayList<KeyValueList<String, String>> tableContent;
  private int tableSize;

  // Returns a number of keys in a current table.
  public int size() {
    return tableSize;
  }
  // Returns true if such a key exists in the hash table.
  boolean contains(String key) {
    return get(key) != null;
  }

  // Returns value if such a key exists in the hash table, null otherwise.
  String get(String key) {
    return tableContent.get(key.hashCode() % tableContent.size()).find(key);
  }

  // Puts value under the given key into the hash table.
  // If there was such a key in the table, returns a previous value, null if not.
  String put(String key, String value) {
    String oldValue = tableContent.get(key.hashCode() % tableContent.size()).addOrAssign(key, value);
    if (oldValue == null) {
      tableSize++;
    }
    return oldValue;
  }

  // Removes key-value pair with the given key from the hash table.
  // If such a key existed, returns the deleted value. Otherwise return null.
  String remove(String key) {
    String oldValue = tableContent.get(key.hashCode() % tableContent.size()).remove(key);
    if (oldValue != null) {
      tableSize--;
    }
    return oldValue;
  }

  // Clears the hash table.
  void clear() {
    tableContent = new ArrayList<KeyValueList<String, String>>();
  }
}
