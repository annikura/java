package ru.spbau.annikura.tester;

import static org.junit.Assert.*;

public class TestRunnerTest {

    @org.junit.Test
    public void simpleOkTest() throws InstantiationException, IllegalAccessException, StateException {
        TestRunner tester = new TestRunner(SimpleOkTest.class);
        tester.runTests();

        assertNotNull(tester.getResults());
        assertEquals(1, tester.getResults().size());
        TestRunner.TestResult result = tester.getResults().get(0);

        assertTrue(result.hasPassed());
        assertFalse(result.hasFailed());
        assertFalse(result.isIgnored());

        assertNull(result.caughtException);
        assertNull(result.expectedException);
        assertNull(result.ignoreReason);
    }

    @org.junit.Test
    public void simpleIgnoredTest() throws InstantiationException, IllegalAccessException, StateException {
        TestRunner tester = new TestRunner(SimpleIgnoredTest.class);
        tester.runTests();

        assertNotNull(tester.getResults());
        assertEquals(1, tester.getResults().size());
        TestRunner.TestResult result = tester.getResults().get(0);

        assertFalse(result.hasPassed());
        assertFalse(result.hasFailed());
        assertTrue(result.isIgnored());

        assertNull(result.caughtException);
        assertNull(result.expectedException);
        assertEquals("foo", result.ignoreReason);
    }
}

class SimpleOkTest {
    @Test
    public void okTest() {
    }
}

class SimpleIgnoredTest {
    @Test(ignored = "foo")
    public void ignoredTest() {
    }
}