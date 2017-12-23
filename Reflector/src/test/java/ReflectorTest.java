import org.junit.Test;

import static org.junit.Assert.*;

public class ReflectorTest {
    public void checkEquals(Class<?> cls, String... strs) {
        int cnt = 0;
        for (String str : Reflector.convertClassToStrings(cls)) {
            assert cnt < strs.length;
            assertEquals(strs[cnt], str);
            cnt++;
        }
        assertEquals(strs.length, cnt);
    }

    @Test
    public void printEmptyClass() {
        class EmptyClass {
        }
        Reflector.printStructure(EmptyClass.class);
    }

    @Test
    public void checkEmptyClass() {
        checkEquals(EmptyClass.class,
                "class EmptyClass  {",
                "}");
    }
}

class EmptyClass {
}