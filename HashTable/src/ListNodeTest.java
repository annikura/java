import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import ru.spbau.annikura.list.ListNode;

/**
 * Created by annikura on 9/17/17.
 * @author Anna Shvetsova
 */
public class ListNodeTest {
  @Test
  public void createListNode() throws Exception {
    ListNode<String> node = new ListNode<>("abc");
  }

  @Test
  public void getValue() throws Exception {
  }

  @Test
  public void next() throws Exception {
    ListNode<String> node = new ListNode<String>("");
    assertEquals(null, node.next());
  }

  @Test
  public void previous() throws Exception {
  }

  @Test
  public void insertAfter() throws Exception {
  }

  @Test
  public void eraseAfter() throws Exception {
  }

}