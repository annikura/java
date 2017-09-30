package tests.ru.spbau.annikura;

import ru.spbau.annikura.hashtable.HashTable;
import tests.ru.spbau.annikura.hashtable.HashTableTest;
import tests.ru.spbau.annikura.list.KeyValueListTest;
import tests.ru.spbau.annikura.list.ListNodeTest;

public class TestRunner {
  public static void main(String[] args) {
    ListNodeTest.createNode();
    ListNodeTest.insertAfterSimple();
    ListNodeTest.insertAfter();
    ListNodeTest.eraseAfter();

    KeyValueListTest.createInstance();
    KeyValueListTest.find();
    KeyValueListTest.addOrAssign();
    KeyValueListTest.remove();

    HashTableTest.clear();
    HashTableTest.contains();
    HashTableTest.createInstance();
    HashTableTest.getAndPut();
    HashTableTest.getFromEmptyTable();
    HashTableTest.remove();
    HashTableTest.removeFromEmptyTable();
    HashTableTest.size();
    HashTableTest.collision();
    HashTableTest.bigTest();

    System.out.println("OK. All tests passed");
  }
}
