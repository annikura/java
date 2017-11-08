package ru.spbau.annikura.collections;

import org.junit.Test;
import ru.spbau.annikura.function.Function1;
import ru.spbau.annikura.function.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class CollectionsTest {
    @Test
    public void map() throws Exception {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(5); arr.add(8); arr.add(19); arr.add(7); arr.add(4); arr.add(11);
        List<Integer> funcRes = Collections.map(arr, (num -> num * num));
        List<Integer> expected = new ArrayList<>();
        for (int el : arr)
            expected.add(el * el);
        assertEquals(expected, funcRes);
    }

    @Test
    public void filter() throws Exception {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(5); arr.add(8); arr.add(19); arr.add(7); arr.add(4); arr.add(11);
        List<Integer> funcRes = Collections.filter(arr, (num -> num % 2 == 1));
        List<Integer> expected = new ArrayList<>();
        for (int el : arr)
            if (el % 2 == 1)
            expected.add(el);
        assertEquals(expected, funcRes);

    }

    @Test
    public void takeWhile() throws Exception {
    }

    @Test
    public void takeUntil() throws Exception {
    }

    @Test
    public void foldR() throws Exception {
    }

    @Test
    public void foldL() throws Exception {
    }

}