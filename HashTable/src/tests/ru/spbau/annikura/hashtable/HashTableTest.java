package tests.ru.spbau.annikura.hashtable;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import ru.spbau.annikura.hashtable.HashTable;

/**
 * Created by ashvet on 9/17/17.
 * @author Anna Shvetsova
 */

public class HashTableTest {
  @Test
  public void createInstance() throws Exception {
    HashTable hashTable = new HashTable();
    assertEquals(0, hashTable.size());
  }

  @Test
  public void size() throws Exception {
    HashTable hashTable = new HashTable();
    assertEquals(0, hashTable.size());
    hashTable.put("x", "y");
    assertEquals(1, hashTable.size());
    hashTable.put("x", "r");
    assertEquals(1, hashTable.size());
    hashTable.put("prr", "r");
    assertEquals(2, hashTable.size());
    hashTable.remove("tr");
    assertEquals(2, hashTable.size());
    hashTable.remove("x");
    assertEquals(1, hashTable.size());
    hashTable.remove("prr");
    assertEquals(0, hashTable.size());
    hashTable.remove("prr");
    assertEquals(0, hashTable.size());

  }

  @Test
  public void contains() throws Exception {
    HashTable hashTable = new HashTable();
    assertEquals(false, hashTable.contains("xx"));
    hashTable.put("x", "y");
    hashTable.put("a", null);
    assertEquals(true, hashTable.contains("x"));
    assertEquals(false, hashTable.contains("xx"));
    assertEquals(true, hashTable.contains("a"));
  }

  @Test
  public void getFromEmptyTable() throws Exception {
    HashTable hashTable = new HashTable();
    assertEquals(null, hashTable.get("a"));
    assertEquals(null, hashTable.get(null));
  }

  @Test
  public void getAndPut() throws Exception {
    HashTable hashTable = new HashTable();
    assertEquals(null, hashTable.put("a", "b"));  // Simple insert
    assertEquals("b", hashTable.get("a"));
    assertEquals("b", hashTable.put("a", "x"));  // Replacing <"a", "b"> with <"a", "x">
    assertEquals(null, hashTable.get("x"));  // Not existing key
    assertEquals("x", hashTable.get("a"));
    assertEquals(null, hashTable.put("x", "a"));
    assertEquals("a", hashTable.get("x"));
    assertEquals(2, hashTable.size());
    assertEquals(null, hashTable.put(null, null)); // Trying null key insertion.
    assertEquals(null, hashTable.get(null));
    assertEquals("a", hashTable.get("x"));
    assertEquals(null, hashTable.put(null, "c"));
    assertEquals("c", hashTable.get(null));
    assertEquals(3, hashTable.size());
  }

  @Test
  public void removeFromEmptyTable() throws Exception {
    HashTable hashTable = new HashTable();
    assertEquals(null, hashTable.remove("key"));
    assertEquals(null, hashTable.remove(null));
  }

  @Test
  public void remove() throws Exception {
    HashTable hashTable = new HashTable();
    assertEquals(null, hashTable.put("key", "value"));
    assertEquals(null, hashTable.put(null, "x"));
    assertEquals("value", hashTable.remove("key"));
    assertEquals("x", hashTable.remove(null));
    assertEquals(null, hashTable.remove(null));
    assertEquals(0, hashTable.size());
  }


  @Test
  public void clear() throws Exception {
    HashTable hashTable = new HashTable();
    hashTable.clear();
    hashTable.put("a", "a");
    hashTable.put(null, null);
    hashTable.clear();
    assertEquals(0, hashTable.size());
    assertEquals(null, hashTable.put("a", "b"));
    assertEquals("b", hashTable.get("a"));
  }

}