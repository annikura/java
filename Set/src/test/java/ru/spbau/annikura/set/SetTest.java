package ru.spbau.annikura.set;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class SetTest {
    @Test
    public void createInstance() {
        Set<Integer> intSet = new Set<Integer>();
        assertEquals(0, intSet.size());
    }

    @Test
    public void simpleAdd() {
        SetWrapper<String> setWrapper = new SetWrapper<String>();
        setWrapper.add("a");
        setWrapper.check();
    }


    @Test
    public void doubleAdd() {
        SetWrapper<String> setWrapper = new SetWrapper<String>();
        setWrapper.add("a");
        setWrapper.add("a");
        setWrapper.check();
    }


    @Test
    public void addThree() {
        SetWrapper<String> setWrapper = new SetWrapper<String>();
        setWrapper.add("a");
        setWrapper.add("bb");
        setWrapper.add("ccc");
        setWrapper.check();
    }

    @Test
    public void addFive() {
        SetWrapper<String> setWrapper = new SetWrapper<String>();
        setWrapper.add("bb");
        setWrapper.add("abacaba dabacaba");
        setWrapper.add("ccc");
        setWrapper.add("abba");
        setWrapper.add("abacabad abacaba");
        setWrapper.check();
    }


    @Test
    public void addManyInOrder() {
        SetWrapper<Integer> setWrapper = new SetWrapper<Integer>();
        for (int i = 0; i < 1000; i++)
            setWrapper.add(i);
        setWrapper.check();
    }


    @Test
    public void addManyRandom() {
        SetWrapper<Integer> setWrapper = new SetWrapper<Integer>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++)
            setWrapper.add(random.nextInt());
        setWrapper.check();
    }

    @Test
    public void deleteOne() {
        SetWrapper<String> setWrapper = new SetWrapper<String>();
        setWrapper.add("abba");
        setWrapper.check();
        setWrapper.delete("abba");
        setWrapper.check();
    }

    @Test
    public void deleteTwo() {
        SetWrapper<String> setWrapper = new SetWrapper<String>();
        setWrapper.add("abba");
        setWrapper.add("ab");
        setWrapper.delete("abba");
        setWrapper.check();
        setWrapper.delete("ab");
        setWrapper.check();
    }

    @Test
    public void deleteEqual() {
        SetWrapper<String> setWrapper = new SetWrapper<String>();
        setWrapper.add("abba");
        setWrapper.add("ab");
        setWrapper.add("ab");
        setWrapper.delete("ab");
        setWrapper.check();
    }

    @Test
    public void deleteFromEmpty() {
        SetWrapper<String> setWrapper = new SetWrapper<String>();
        setWrapper.delete("ab");
        setWrapper.check();
    }


    @Test
    public void deleteNotExisting() {
        SetWrapper<String> setWrapper = new SetWrapper<String>();
        setWrapper.add("ab");
        setWrapper.delete("ab");
        setWrapper.delete("ab");
        setWrapper.check();
    }

    @Test
    public void deleteManySimple() {
        SetWrapper<Integer> setWrapper = new SetWrapper<Integer>();
        for (int i = 0; i < 1007; i++)
            setWrapper.add(i);
        for (int i = 1; i < 1007; i += 2)
            setWrapper.delete(i);
        setWrapper.check();
    }

    @Test
    public void deleteManyRandom() {
        SetWrapper<Integer> setWrapper = new SetWrapper<Integer>();
        Random random = new Random();
        for (int i = 0; i < 10007; i++)
            setWrapper.add(random.nextInt() % 1000);
        for (int i = 1; i < 10007; i++)
            setWrapper.delete(random.nextInt() % 1000);
        setWrapper.check();
    }

}