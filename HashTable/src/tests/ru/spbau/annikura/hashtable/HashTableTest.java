package tests.ru.spbau.annikura.hashtable;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import ru.spbau.annikura.hashtable.HashTable;

public class HashTableTest {
  @Test
  public static void createInstance() {
    HashTable hashTable = new HashTable();
    assertEquals(0, hashTable.size());
  }

  @Test
  public static void size() {
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
    assertEquals(0, hashTable.size());   // Checking that
    hashTable.remove("prr");
    assertEquals(0, hashTable.size());

  }

  @Test
  public static void contains() {
    HashTable hashTable = new HashTable();
    assertEquals(false, hashTable.contains("xx"));
    hashTable.put("x", "y");
    hashTable.put("a", null);
    assertEquals(true, hashTable.contains("x"));
    assertEquals(false, hashTable.contains("xx"));
    assertEquals(true, hashTable.contains("a"));
  }

  @Test
  public static void getFromEmptyTable() {
    HashTable hashTable = new HashTable();
    assertEquals(null, hashTable.get("a"));
    assertEquals(null, hashTable.get(null));
  }

  @Test
  public static void getAndPut() {
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
  public static void removeFromEmptyTable() {
    HashTable hashTable = new HashTable();
    assertEquals(null, hashTable.remove("key"));
    assertEquals(null, hashTable.remove(null));
  }

  @Test
  public static void remove() {
    HashTable hashTable = new HashTable();
    assertEquals(null, hashTable.put("key", "value"));
    assertEquals(null, hashTable.put(null, "x"));
    assertEquals("value", hashTable.remove("key"));
    assertEquals("x", hashTable.remove(null));
    assertEquals(null, hashTable.remove(null));
    assertEquals(0, hashTable.size());
  }


  @Test
  public static void clear() {
    HashTable hashTable = new HashTable();
    hashTable.clear();
    hashTable.put("a", "a");
    hashTable.put(null, null);
    hashTable.clear();
    assertEquals(0, hashTable.size());
    assertEquals(null, hashTable.put("a", "b"));
    assertEquals("b", hashTable.get("a"));
  }

  @Test
  public static void collision() {
    HashTable hashTable = new HashTable();
    String str1 = "Aa";
    String str2 = "BB";
    if (str1.hashCode() != str2.hashCode()) {
      System.out.println("WARNING: Collision test is broken. " +
              "Hash table is not being tested for collisions now. " +
              "Change strings to ones that match by hash.");
      return;
    }
    assertEquals(null, hashTable.put(str1, "x"));
    assertEquals("x", hashTable.get(str1));
    assertEquals(null, hashTable.put(str2, "y"));
    assertEquals("y", hashTable.get(str2));
    assertEquals("x", hashTable.get(str1));
  }

  @Test
  public static void bigTest() {
    HashTable hashTable = new HashTable();
    String str = "1";

    // Will also generate strings with a negative hash.
    for (int i = 0; i < 100; i++) {
      assertEquals(null, hashTable.put(str, str + "a"));
      str += "1";
    }
    str = "1";
    for (int i = 0; i < 100; i++) {
      assertEquals(str + "a", hashTable.get(str));
    }
  }
}