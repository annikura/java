package sp;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() {
        List<String> strings = Arrays.asList(
                "best quote",
                "magnificent QUOTE!",
                "",
                "no quote :(",
                "quotes",
                "one more string");
        List<String> answer = Arrays.asList(
                "best quote",
                "no quote :(",
                "quotes"
        );
        assertEquals(
                answer,
                SecondPartTasks.findQuotes(strings, "quote"));
    }

    @Test
    public void testEmptyFindQuotes() {
        assertEquals(
                Collections.emptyList(),
                SecondPartTasks.findQuotes(Collections.emptyList(), "some quote"));
    }

    @Test
    public void testPiDividedBy4() {
        for (int i = 0; i < 10; i++)
            assertEquals(Math.PI / 4, SecondPartTasks.piDividedBy4(), 0.01);
    }

    @Test
    public void testFindPrinter() {
        HashMap<String, List<String>> map = new HashMap<>();

        map.put("Pushkin", Arrays.asList("poem1", "poem2", "very long poem"));
        map.put("Tushkin", Arrays.asList("not a poem1", "not", "a", "poem2", "not a very long poem", "a", ""));
        map.put("MrPrinter", Arrays.asList("I", "am", "printing", "this", "test message", "to be sure I work"));
        map.put("Dushkin", Arrays.asList("a", "bc", "def"));

        assertEquals("MrPrinter", SecondPartTasks.findPrinter(map));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindPrinterOnEmptyMap() {
        HashMap<String, List<String>> map = new HashMap<>();
        SecondPartTasks.findPrinter(map);
    }

    @Test
    public void testCalculateGlobalOrder() {
        Map<String, Integer> first = new HashMap<>();
        first.put("tomato", 3);
        first.put("test", 97);
        first.put("java", 9);
        Map<String, Integer> second = new HashMap<>();
        second.put("life", 1);
        second.put("tomato", 2);
        second.put("cat", 4);
        second.put("test", 99);
        Map<String, Integer> third = new HashMap<>();
        third.put("cat", 2);
        third.put("tomato", 1);
        third.put("nothing", 1);

        HashMap<String, Integer> result = new HashMap<>();
        result.put("tomato", 6);
        result.put("cat", 6);
        result.put("test", 196);
        result.put("nothing", 1);
        result.put("life", 1);
        result.put("java", 9);

        assertEquals(result, SecondPartTasks.calculateGlobalOrder(Arrays.asList(first, second, third)));
    }
}