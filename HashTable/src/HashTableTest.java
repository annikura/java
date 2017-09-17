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

  }

  @Test
  public void contains() throws Exception {
    HashTable hashTable = new HashTable();
    hashTable.put("x", "y");
    assertEquals(true, hashTable.contains("x"));
    assertEquals(true, hashTable.contains("x"));
  }

  @Test
  public void get() throws Exception {
  }

  @Test
  public void put() throws Exception {
  }

  @Test
  public void remove() throws Exception {
  }

  @Test
  public void clear() throws Exception {
  }

}