package ru.spbau.annikura.hashtable;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HashMapTest {
    @Test
    public void createInstance() {
        HashMap hashMap = new HashMap();
        assertEquals(0, hashMap.size());
    }

    @Test
    public void size() {
        HashMap hashMap = new HashMap();
        assertEquals(0, hashMap.size());
        hashMap.put("x", "y");
        assertEquals(1, hashMap.size());
        hashMap.put("x", "r");
        assertEquals(1, hashMap.size());
        hashMap.put("prr", "r");
        assertEquals(2, hashMap.size());
        hashMap.remove("tr");
        assertEquals(2, hashMap.size());
        hashMap.remove("x");
        assertEquals(1, hashMap.size());
        hashMap.remove("prr");
        assertEquals(0, hashMap.size());   // Checking that
        hashMap.remove("prr");
        assertEquals(0, hashMap.size());

    }

    @Test
    public void contains() {
        HashMap hashMap = new HashMap();
        assertEquals(false, hashMap.contains("xx"));
        hashMap.put("x", "y");
        hashMap.put("a", null);
        assertEquals(true, hashMap.contains("x"));
        assertEquals(false, hashMap.contains("xx"));
        assertEquals(true, hashMap.contains("a"));
    }

    @Test
    public void getFromEmptyTable() {
        HashMap hashMap = new HashMap();
        assertEquals(null, hashMap.get("a"));
        assertEquals(null, hashMap.get(null));
    }

    @Test
    public void getAndPut() {
        HashMap hashMap = new HashMap();
        assertEquals(null, hashMap.put("a", "b"));  // Simple insert
        assertEquals("b", hashMap.get("a"));
        assertEquals("b", hashMap.put("a", "x"));  // Replacing <"a", "b"> with <"a", "x">
        assertEquals(null, hashMap.get("x"));  // Not existing key
        assertEquals("x", hashMap.get("a"));
        assertEquals(null, hashMap.put("x", "a"));
        assertEquals("a", hashMap.get("x"));
        assertEquals(2, hashMap.size());
        assertEquals(null, hashMap.put(null, null)); // Trying null key insertion.
        assertEquals(null, hashMap.get(null));
        assertEquals("a", hashMap.get("x"));
        assertEquals(null, hashMap.put(null, "c"));
        assertEquals("c", hashMap.get(null));
        assertEquals(3, hashMap.size());
    }

    @Test
    public void removeFromEmptyTable() {
        HashMap hashMap = new HashMap();
        assertEquals(null, hashMap.remove("key"));
        assertEquals(null, hashMap.remove(null));
    }

    @Test
    public void remove() {
        HashMap hashMap = new HashMap();
        assertEquals(null, hashMap.put("key", "value"));
        assertEquals(null, hashMap.put(null, "x"));
        assertEquals("value", hashMap.remove("key"));
        assertEquals("x", hashMap.remove(null));
        assertEquals(null, hashMap.remove(null));
        assertEquals(0, hashMap.size());
    }


    @Test
    public void clear() {
        HashMap hashMap = new HashMap();
        hashMap.clear();
        hashMap.put("a", "a");
        hashMap.put(null, null);
        hashMap.clear();
        assertEquals(0, hashMap.size());
        assertEquals(null, hashMap.put("a", "b"));
        assertEquals("b", hashMap.get("a"));
    }

    @Test
    public void collision() {
        HashMap hashMap = new HashMap();
        String str1 = "Aa";
        String str2 = "BB";
        if (str1.hashCode() != str2.hashCode()) {
            System.out.println("WARNING: Collision test is broken. " +
                    "Hash table is not being tested for collisions now. " +
                    "Change strings to ones that match by hash.");
            return;
        }
        assertEquals(null, hashMap.put(str1, "x"));
        assertEquals("x", hashMap.get(str1));
        assertEquals(null, hashMap.put(str2, "y"));
        assertEquals("y", hashMap.get(str2));
        assertEquals("x", hashMap.get(str1));
    }

    @Test
    public void bigTest() {
        HashMap hashMap = new HashMap();
        String str = "1";

        // Will also generate strings with a negative hash.
        for (int i = 0; i < 100; i++) {
            assertEquals(null, hashMap.put(str, str + "a"));
            str += "1";
        }
        str = "1";
        for (int i = 0; i < 100; i++) {
            assertEquals(str + "a", hashMap.get(str));
        }
    }
}