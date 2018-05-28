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

    @org.junit.Test
    public void simpleFailedTest() throws InstantiationException, IllegalAccessException, StateException {
        TestRunner tester = new TestRunner(SimpleFailedTest.class);
        tester.runTests();

        assertNotNull(tester.getResults());
        assertEquals(1, tester.getResults().size());
        TestRunner.TestResult result = tester.getResults().get(0);

        assertFalse(result.hasPassed());
        assertTrue(result.hasFailed());
        assertFalse(result.isIgnored());

        assertEquals(Exception.class, result.caughtException.getClass());
        assertNull(result.expectedException);
        assertNull(result.ignoreReason);
    }

    @org.junit.Test
    public void simpleExpectedFailedTest() throws InstantiationException, IllegalAccessException, StateException {
        TestRunner tester = new TestRunner(SimpleExpectedFailedTest.class);
        tester.runTests();

        assertNotNull(tester.getResults());
        assertEquals(1, tester.getResults().size());
        TestRunner.TestResult result = tester.getResults().get(0);

        assertTrue(result.hasPassed());
        assertFalse(result.hasFailed());
        assertFalse(result.isIgnored());

        assertEquals(NullPointerException.class, result.caughtException.getClass());
        assertEquals(NullPointerException.class, result.caughtException.getClass());
        assertNull(result.ignoreReason);
    }

    @org.junit.Test
    public void simpleBeforeClassTest() throws InstantiationException, IllegalAccessException, StateException {
        int originalA = SimpleBeforeClassTest.a;
        new TestRunner(SimpleBeforeClassTest.class).runTests();
        assertEquals(originalA + 1, SimpleBeforeClassTest.a);
    }

    @org.junit.Test
    public void simpleAfterClassTest() throws InstantiationException, IllegalAccessException, StateException {
        int originalA = SimpleAfterClassTest.a;
        new TestRunner(SimpleAfterClassTest.class).runTests();
        assertEquals(originalA + 1, SimpleAfterClassTest.a);
    }

    @org.junit.Test
    public void simpleBeforeWithNoTests() throws InstantiationException, IllegalAccessException, StateException {
        int originalA = NoTestsBefore.a;
        new TestRunner(NoTestsBefore.class).runTests();
        assertEquals(originalA, NoTestsBefore.a);
    }

    @org.junit.Test
    public void simpleAfterWithNoTests() throws InstantiationException, IllegalAccessException, StateException {
        int originalA = NoTestsAfter.a;
        new TestRunner(NoTestsAfter.class).runTests();
        assertEquals(originalA, NoTestsAfter.a);
    }

    @org.junit.Test
    public void simpleBeforeWithTwoTests() throws InstantiationException, IllegalAccessException, StateException {
        int originalA = TwoTestsBefore.a;
        new TestRunner(TwoTestsBefore.class).runTests();
        assertEquals(originalA + 2, TwoTestsBefore.a);
    }

    @org.junit.Test
    public void simpleAfterWithTwoTests() throws InstantiationException, IllegalAccessException, StateException {
        int originalA = TwoTestsAfter.a;
        new TestRunner(TwoTestsAfter.class).runTests();
        assertEquals(originalA + 2, TwoTestsAfter.a);
    }

    @org.junit.Test
    public void exceptionInBeforeClass() throws InstantiationException, IllegalAccessException {
        TestRunner tester = new TestRunner(BeforeClassException.class);
        boolean caught = false;
        try {
            tester.runTests();
        } catch (StateException e) {
            caught = true;
        }
        assertEquals(0, tester.getResults().size());
        assertEquals(true, caught);
    }

    @org.junit.Test
    public void exceptionInAfterClass() throws InstantiationException, IllegalAccessException {
        TestRunner tester = new TestRunner(AfterClassException.class);
        boolean caught = false;

        try {
            tester.runTests();
        } catch (StateException e) {
            caught = true;
        }
        assertEquals(true, caught);
        assertEquals(0, tester.getResults().size());
    }

    @org.junit.Test
    public void exceptionInBefore() throws InstantiationException, IllegalAccessException {
        TestRunner tester = new TestRunner(BeforeException.class);
        boolean caught = false;
        try {
            tester.runTests();
        } catch (StateException e) {
            caught = true;
        }
        assertEquals(0, tester.getResults().size());
        assertEquals(true, caught);
    }

    @org.junit.Test
    public void exceptionInAfter() throws InstantiationException, IllegalAccessException {
        TestRunner tester = new TestRunner(AfterException.class);
        boolean caught = false;
        try {
            tester.runTests();
        } catch (StateException e) {
            caught = true;
        }
        assertEquals(1, tester.getResults().size());
        assertTrue(tester.getResults().get(0).hasPassed());
        assertEquals(true, caught);
    }

    @org.junit.Test
    public void exceptionAfterSecondTest() throws InstantiationException, IllegalAccessException {
        TestRunner tester = new TestRunner(AfterSecondTest.class);
        boolean caught = false;
        try {
            tester.runTests();
        } catch (StateException e) {
            caught = true;
        }
        assertEquals(2, tester.getResults().size());
        assertTrue(tester.getResults().get(0).hasPassed());
        assertTrue(tester.getResults().get(1).hasPassed());
        assertEquals(true, caught);
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

class SimpleFailedTest {
    @Test
    public void failedTest() throws Exception {
        throw new Exception();
    }
}

class SimpleExpectedFailedTest {
    @Test(expected = NullPointerException.class)
    public void failedTest() throws Exception {
        throw new NullPointerException();
    }
}

class SimpleBeforeClassTest {
    public static int a = 1;

    @BeforeClass
    public void beforeClass() {
        a++;
    }
}

class SimpleAfterClassTest {
    public static int a = 2;

    @AfterClass
    public void afterClass() {
        a++;
    }
}

class NoTestsBefore {
    public static int a = 3;

    @Before
    public void before() {
        a++;
    }
}

class NoTestsAfter {
    public static int a = 4;

    @After
    public void after() {
        a++;
    }
}

class TwoTestsBefore {
    public static int a = 3;

    @Before
    public void before() {
        a++;
    }

    @Test
    public void test1() {}
    @Test
    public void test2() {}
}

class TwoTestsAfter {
    public static int a = 4;

    @After
    public void after() {
        a++;
    }
    @Test
    public void test1() {}
    @Test
    public void test2() {}
}

class BeforeClassException {
    @BeforeClass
    void exception() throws Exception {
        throw new Exception();
    }
}


class AfterClassException {
    @AfterClass
    void exception() throws Exception {
        throw new Exception();
    }
}

class AfterException {
    @After
    void exception() throws Exception {
        throw new Exception();
    }

    @Test
    void test() {}
}


class BeforeException {
    @Before
    void exception() throws Exception {
        throw new Exception();
    }

    @Test
    void test() {}
}

class AfterSecondTest {
    int i = 0;
    @After
    void after() throws Exception {
        i++;
        if (i == 2) {
            throw new Exception();
        }
    }

    @Test
    void test1() {}
    @Test
    void test2() {}
    @Test
    void test3() {}
}