package tests.ru.spbau.annikura;

import tests.ru.spbau.annikura.hashtable.HashTableTest;
import tests.ru.spbau.annikura.list.KeyValueListTest;
import tests.ru.spbau.annikura.list.ListNodeTest;

/**
 * Created by ashvet on 9/18/17.
 *
 * @author Anna Shvetsova
 */
public class TestRunner {
  public static void main(String[] args) {
    HashTableTest.clear();
    HashTableTest.contains();
    HashTableTest.createInstance();
    HashTableTest.getAndPut();
    HashTableTest.getFromEmptyTable();
    HashTableTest.remove();
    HashTableTest.removeFromEmptyTable();
    HashTableTest.size();

    KeyValueListTest.createInstance();
    KeyValueListTest.find();
    KeyValueListTest.addOrAssign();
    KeyValueListTest.remove();

    ListNodeTest.createNode();
    ListNodeTest.insertAfterSimple();
    ListNodeTest.insertAfter();
    ListNodeTest.eraseAfter();

    System.out.println("OK. All tests passed");
  }
}
