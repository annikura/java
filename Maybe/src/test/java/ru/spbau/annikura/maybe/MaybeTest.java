package ru.spbau.annikura.maybe;

import org.junit.Test;

import static org.junit.Assert.*;

public class MaybeTest {
    @Test
    public void justCreation() throws Exception {
        Maybe<String> maybe = Maybe.just("abacaba");
        assertEquals(true, maybe.isPresent());
    }

    @Test
    public void nothingCreation() throws Exception {
        Maybe<String> maybe = Maybe.nothing();
        assertEquals(false, maybe.isPresent());
    }

    @Test
    public void getJust() throws Exception {
        String str = "nobody reads tests";
        Maybe<String> maybe = Maybe.just(str);
        assertEquals(str, maybe.get());
    }


    @Test (expected = MaybeException.class)
    public void getNothing() throws Exception {
        Maybe<String> maybe = Maybe.nothing();
        try {
            maybe.get();
        } catch (MaybeException ex) {
            assertEquals(ex.getMessage(), "Invalid get. Maybe does not contain a value");
            throw ex;
        }
    }

    @Test
    public void nullMaybe() throws Exception {
        Maybe<Integer> maybe = Maybe.just(null);
        assertEquals(true, maybe.isPresent());
        assertEquals(null, maybe.get());
    }

    @Test
    public void mapNothing() throws Exception {
        Maybe<Integer> maybe = Maybe.nothing();
        assertEquals(false, maybe.map(integer -> "a").isPresent());
    }


    @Test
    public void mapSimple() throws Exception {
        Maybe<Integer> maybe = Maybe.just(5);
        assertEquals("a", maybe.map(integer -> "a").get());
    }


    @Test
    public void map() throws Exception {
        Maybe<Integer> maybe = Maybe.just(5);
        assertEquals("15",
                maybe.map(integer -> "1" + integer.toString()).get());
    }

}