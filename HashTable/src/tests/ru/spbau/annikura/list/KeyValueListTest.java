package tests.ru.spbau.annikura.list;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import ru.spbau.annikura.list.KeyValueList;
import ru.spbau.annikura.list.Pair;

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
    assertEquals(new Pair<>("a", "b").GetValue(), list.find("a").GetValue());
    assertEquals(new Pair<>("a", "b").GetKey(), list.find("a").GetKey());  // Check a -> <a, b>
    assertEquals(new Pair<>("xxx", "yyy").GetValue(), list.find("xxx").GetValue());
    assertEquals(new Pair<>("xxx", "yyy").GetKey(), list.find("xxx").GetKey());  // Check xxx -> <xxx, yyy>
    assertEquals(new Pair<>("c", "d").GetValue(), list.find("c").GetValue());
    assertEquals(new Pair<>("c", "d").GetKey(), list.find("c").GetKey());  // Check c -> <c, d>
    assertEquals(null , list.find("xx"));  // Check xx -> not found = null.
  }

  @Test
  public static void addOrAssign() {
    KeyValueList<String, String> list = new KeyValueList<>();
    assertEquals(null, list.addOrAssign("a", "bb"));
    assertEquals(null, list.addOrAssign("bb", "bbb"));  // <a, bb>, <bb, bbb>
    assertEquals("bb", list.addOrAssign("a", "x"));  // <a, x>, <bb, bbb> (<a, b> is overwritten with <a, x>)
    assertEquals(new Pair<>("a", "x").GetValue(), list.find("a").GetValue());
    assertEquals(new Pair<>("a", "x").GetKey(), list.find("a").GetKey());  // Check a -> <a, x>
    assertEquals(new Pair<>("bb", "bbb").GetKey(), list.find("bb").GetKey());
    assertEquals(new Pair<>("bb", "bbb").GetValue(), list.find("bb").GetValue());  // Check bb -> <bb, bbb>
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
    assertEquals(new Pair<>("a", "tt").GetValue(), list.find("a").GetValue());
    assertEquals(new Pair<>("a", "tt").GetKey(), list.find("a").GetKey());  // Check a -> <a, tt>
  }

}