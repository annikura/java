package tests.ru.spbau.annikura.list;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import ru.spbau.annikura.list.KeyValueList;
import ru.spbau.annikura.list.Pair;

/**
 * Created by ashvet on 9/17/17.
 * @author Anna Shvetsova
 */

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
    assertEquals(new Pair<>("a", "b").value, list.find("a").value);
    assertEquals(new Pair<>("a", "b").key, list.find("a").key);  // Check a -> <a, b>
    assertEquals(new Pair<>("xxx", "yyy").value, list.find("xxx").value);
    assertEquals(new Pair<>("xxx", "yyy").key, list.find("xxx").key);  // Check xxx -> <xxx, yyy>
    assertEquals(new Pair<>("c", "d").value, list.find("c").value);
    assertEquals(new Pair<>("c", "d").key, list.find("c").key);  // Check c -> <c, d>
    assertEquals(null , list.find("xx"));  // Check xx -> not found = null.
  }

  @Test
  public static void addOrAssign() {
    KeyValueList<String, String> list = new KeyValueList<>();
    assertEquals(null, list.addOrAssign("a", "bb"));
    assertEquals(null, list.addOrAssign("bb", "bbb"));  // <a, bb>, <bb, bbb>
    assertEquals("bb", list.addOrAssign("a", "x"));  // <a, x>, <bb, bbb> (<a, b> is overwritten with <a, x>)
    assertEquals(new Pair<>("a", "x").value, list.find("a").value);
    assertEquals(new Pair<>("a", "x").key, list.find("a").key);  // Check a -> <a, x>
    assertEquals(new Pair<>("bb", "bbb").key, list.find("bb").key);
    assertEquals(new Pair<>("bb", "bbb").value, list.find("bb").value);  // Check bb -> <bb, bbb>
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
    assertEquals(new Pair<>("a", "tt").value, list.find("a").value);
    assertEquals(new Pair<>("a", "tt").key, list.find("a").key);  // Check a -> <a, tt>
  }

}