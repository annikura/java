import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import ru.spbau.annikura.list.KeyValueList;
import ru.spbau.annikura.list.Entry;

/**
 * Created by ashvet on 9/17/17.
 * @author Anna Shvetsova
 */

public class KeyValueListTest {

  @Test
  public void createInstance() throws Exception {
    KeyValueList<String, String> list;
  }

  @Test
  public void find() throws Exception {
    KeyValueList<String, String> list = new KeyValueList<>();
    assertEquals(null, list.addOrAssign("a", "b"));
    assertEquals(null, list.addOrAssign("c", "d"));
    assertEquals(null, list.addOrAssign("xxx", "yyy"));
    assertEquals(new Entry<>("a", "b"), list.find("a"));
    assertEquals(new Entry<>("xxx", "yyy"), list.find("xxx"));
    assertEquals(new Entry<>("c", "d"), list.find("c"));
    assertEquals(null , list.find("xx"));
  }

  @Test
  public void addOrAssign() throws Exception {
    KeyValueList<String, String> list = new KeyValueList<>();
    assertEquals(null, list.addOrAssign("a", "bb"));
    assertEquals(null, list.addOrAssign("bb", "bbb"));
    assertEquals("bb", list.addOrAssign("a", "x"));
    assertEquals(new Entry<>("a", "x"), list.find("a"));
    assertEquals(new Entry<>("bb", "bbb"), list.find("bb"));
  }

  @Test
  public void remove() throws Exception {
    KeyValueList<String, String> list = new KeyValueList<>();
    assertEquals(null, list.addOrAssign("a", "bb"));
    assertEquals(null, list.addOrAssign("bb", "bbb"));
    assertEquals("bb", list.addOrAssign("a", "x"));
    assertEquals("x", list.remove("a"));
    assertEquals(null, list.addOrAssign("a", "tt"));
    assertEquals(new Entry<>("tt", "a"), list.find("a"));
  }

}