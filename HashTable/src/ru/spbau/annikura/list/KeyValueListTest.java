package ru.spbau.annikura.list;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeyValueListTest {

  @Test
  public static void createInstance() {
    KeyValueList<String, String> list;
  }

  @Test
  public static void find() {
    KeyValueList<String, String> list = new KeyValueList<>();
    assertEquals(null, list.addOrAssign("a", "b"));
    assertEquals(null, list.addOrAssign("c", "d"));
    assertEquals(null, list.addOrAssign("xxx", "yyy"));  // <a, b>, <c d>, <xxx, yyy>
    assertEquals(new Pair<>("a", "b").getValue(), list.find("a").getValue());
    assertEquals(new Pair<>("a", "b").getKey(), list.find("a").getKey());  // Check a -> <a, b>
    assertEquals(new Pair<>("xxx", "yyy").getValue(), list.find("xxx").getValue());
    assertEquals(new Pair<>("xxx", "yyy").getKey(), list.find("xxx").getKey());  // Check xxx -> <xxx, yyy>
    assertEquals(new Pair<>("c", "d").getValue(), list.find("c").getValue());
    assertEquals(new Pair<>("c", "d").getKey(), list.find("c").getKey());  // Check c -> <c, d>
    assertEquals(null , list.find("xx"));  // Check xx -> not found = null.
  }

  @Test
  public static void addOrAssign() {
    KeyValueList<String, String> list = new KeyValueList<>();
    assertEquals(null, list.addOrAssign("a", "bb"));
    assertEquals(null, list.addOrAssign("bb", "bbb"));  // <a, bb>, <bb, bbb>
    assertEquals("bb", list.addOrAssign("a", "x"));  // <a, x>, <bb, bbb> (<a, b> is overwritten with <a, x>)
    assertEquals(new Pair<>("a", "x").getValue(), list.find("a").getValue());
    assertEquals(new Pair<>("a", "x").getKey(), list.find("a").getKey());  // Check a -> <a, x>
    assertEquals(new Pair<>("bb", "bbb").getKey(), list.find("bb").getKey());
    assertEquals(new Pair<>("bb", "bbb").getValue(), list.find("bb").getValue());  // Check bb -> <bb, bbb>
  }

  @Test
  public static void remove() {
    KeyValueList<String, String> list = new KeyValueList<>();
    assertEquals(null, list.addOrAssign("a", "bb"));
    assertEquals(null, list.addOrAssign("bb", "bbb"));  // <a, bb>, <bb, bbb>
    assertEquals("bb", list.addOrAssign("a", "x"));  // <a, x>, <bb, bbb>
    assertEquals("x", list.remove("a"));  // <bb, bbb>
    assertEquals(null, list.find("a"));
    assertEquals(null, list.addOrAssign("a", "tt")); // <a, tt>, <bb, bbb>
    assertEquals(new Pair<>("a", "tt").getValue(), list.find("a").getValue());
    assertEquals(new Pair<>("a", "tt").getKey(), list.find("a").getKey());  // Check a -> <a, tt>
  }

}