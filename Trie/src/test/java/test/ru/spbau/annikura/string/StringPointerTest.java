package test.ru.spbau.annikura.string;

import org.junit.Test;
import ru.spbau.annikura.string.StringPointer;

import static org.junit.Assert.*;

public class StringPointerTest {
    @Test
    public void createInstance() {
        StringPointer strPtr = new StringPointer("");
        assertEquals(true, strPtr.pointsAtEnd());
    }

    @Test
    public void usageTest() {
        String str = "aabracadabra simsalabim";
        StringPointer strPtr = new StringPointer(str);
        for (int i = 0; i < str.length(); i++) {
            assertEquals(false, strPtr.pointsAtEnd());
            assertEquals(str.charAt(i), strPtr.symbol());
            if (i + 1 < str.length()) {
                assertEquals(str.charAt(i + 1), strPtr.next().symbol());
                assertEquals(str.charAt(i + 1), strPtr.symbol());
            } else {
                assertEquals(true, strPtr.next().pointsAtEnd());
                assertEquals(true, strPtr.pointsAtEnd());
            }
        }
    }
}