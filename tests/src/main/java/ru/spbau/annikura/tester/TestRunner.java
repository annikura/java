package ru.spbau.annikura.tester;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class providing tester implementation. Taken a .class instance it
 * instantiates the class and runs methods in it according to the following rules:
 *
 *      {@link BeforeClass}-- methods marked with @BeforeClass annotation are run right after the class
 *          instance generated before running any @Before-marked method
 *      {@link Before} Before -- methods marked with @Before annotation are run before each test but after @BeforeClass-annotated
 *          methods and only after the previous test's invocations have finished their execution.
 *      {@link Test} Test -- methods marked with @Test annotation are run after @Before methods and before @After methods.
 *      {@link After} -- methods marked with @After annotation are run after each test before staring new test.
 *
 *      WARNING: any exception thrown from the before* /after* will be thrown as StateException.
 *          All data from already run test is being saved and can be accessed on {@link #getResults()} method
 */
public class TestRunner {
    private final Class<?> cls;
    private final Object instance;

    private List<TestResult> results;

    /**
     * Creates new TestRunner instance for running tests in the given class.
     * @param cls class to run tests from. It must have a nullary constructor and all its test-related
     *            methods should be accessible for proper work.
     * @throws IllegalAccessException if class nullary constructor is not accessible.
     * @throws InstantiationException if class cannot be instantiated for some reason: e.g. it's interface/abstract, it doesnt have a nullary constructor.
     */
    public TestRunner(@NotNull Class<?> cls) throws IllegalAccessException, InstantiationException {
        this.cls = cls;
        instance = cls.newInstance();
    }

    /**
     * Runs tests in the created instance.
     *
     * WARNING: running this method for the second time will erase all previous results.
     * WARNING: the same class instance is being used in all invocations of this method.
     * @throws IllegalAccessException if any of the test-related methods cannot be accessed.
     * @throws StateException if an exception occurred while running before* /after* annotated methods.
     */
    public void runTests() throws IllegalAccessException, StateException {
        List<Method> beforeClassMethods = classifyByAnnotation(cls.getDeclaredMethods(), BeforeClass.class);
        List<Method> afterClassMethods = classifyByAnnotation(cls.getDeclaredMethods(), AfterClass.class);
        List<Method> beforeMethods = classifyByAnnotation(cls.getDeclaredMethods(), Before.class);
        List<Method> afterMethods = classifyByAnnotation(cls.getDeclaredMethods(), After.class);
        List<Method> testMethods = classifyByAnnotation(cls.getDeclaredMethods(), Test.class);

        try {
            runMethods(instance, beforeClassMethods);
        } catch (InvocationTargetException e) {
            results = Collections.emptyList();
            throw new StateException(
                    "Exception occurred while executing @BeforeClass method. No testing was performed.", e);
        }

        results = new ArrayList<>();
        for (Method method : testMethods) {
            try {
                runMethods(instance, beforeMethods);
            } catch (InvocationTargetException e) {
                throw new StateException("An error occurred while running @Before method", e);
            }
            results.add(new TestResult(instance, method));
            try {
                runMethods(instance, afterMethods);
            } catch (InvocationTargetException e) {
                throw new StateException("An error occurred while running @After method", e);
            }
        }

        try {
            runMethods(instance, afterClassMethods);
        } catch (InvocationTargetException e) {
            throw new StateException(
                    "Exception occurred while executing @AfterClass method.", e);

        }
    }

    /**
     * Runs given set of the methods in the given instance.
     * @param obj instance to run methods from.
     * @param methods methods to be run
     * @throws InvocationTargetException if exception was thrown while executing the method from the set
     * @throws IllegalAccessException if any of the methods in the list cannot be accessed.
     */
    static void runMethods(@NotNull Object obj, @NotNull List<Method> methods)
            throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            method.invoke(obj, method);
        }
    }

    /**
     * Filters methods from the list returning the list of methods containing only the ones that are marked with the given annotation.
     * @param methods list to be filtered.
     * @param annotation filtering annotation.
     * @return list of methods marked with the given annotation.
     */
    @NotNull
    static List<Method> classifyByAnnotation(@NotNull Method[] methods, @NotNull Class<? extends Annotation> annotation) {
        ArrayList<Method> result = new ArrayList<>();
        for (Method method : methods) {
            if (method.getAnnotation(annotation) != null) {
                result.add(method);
            }
        }
        return result;
    }

    /**
     * Returns results of the test execution.
     * @return list of test results or null if {@link #runTests()} method was never invoked.
     */
    @Nullable
    public List<TestResult> getResults() {
        return results;
    }

    /**
     * Structure that runs the given method annotated with @Test and stores the results of its execution.
     */
    static public class TestResult {
        Throwable caughtException = null;
        Class<? extends Exception> expectedException = null;
        long executionTime = 0;

        String ignoreReason = null;
        final Method method;

        /**
         * Creates new TestResult instance by running test method and recording the results.
         * @param obj object instance where the method should be run from.
         * @param method test method that should be invoked.
         * @throws IllegalAccessException if method is not accessible.
         */
        TestResult(@NotNull Object obj, @NotNull Method method) throws IllegalAccessException {
            this.method = method;
            unpackTestAnnotation();
            if (isIgnored()) return;

            long startTime = System.currentTimeMillis();
            try {
                method.invoke(obj);
            } catch (InvocationTargetException e) {
                caughtException = e.getTargetException();
            }
            long finishTime = System.currentTimeMillis();
            executionTime = finishTime - startTime;
        }

        /**
         * Checks that stored method is test-annotated.
         * Retrieves data from test annotation and records it into the fields.
         */
        private void unpackTestAnnotation() {
            Test test = method.getAnnotation(Test.class);
            assert test != null;

            if (!test.expected().equals(NoException.class)) {
                expectedException = test.expected();
            }
            if (!test.ignored().equals("")) {
                ignoreReason = test.ignored();
            }
        }

        /**
         * Failure indicator.
         * @return false if test worked as expected or it isn't ignored. True if it failed during execution ans no exception was expected or if the thrown exception didn't match with expected one.
         */
        public boolean hasFailed() {
            return !isIgnored() && !hasPassed();
        }

        /**
         * Ignore indicator.
         * @return true if test is ignored. False otherwise.
         */
        public boolean isIgnored() {
            return ignoreReason != null;
        }

        /**
         * Success indicator.
         * @return true if test worked as expected. false if it is ignored or it failed during execution ans no exception was expected or if the thrown exception didn't match with expected one.
         */
        public boolean hasPassed() {
            if (isIgnored()) return false;
            if (caughtException != null) {
                return caughtException.getClass().equals(expectedException);
            }
            return expectedException == null;
        }

        /**
         * Generated string report about the test execution.
         * @return report.
         */
        public String generateReport() {
            String title = "Test '" + method.getName() + "'";
            String content = null;
            if (isIgnored()) {
                content = "was ignored: " + ignoreReason;
            } else if (hasPassed()) {
                content = "has passed in " + executionTime + "ms";
            } else if (hasFailed()) {
                content = "has failed in " + executionTime + "ms";
                String failReason = "exception was caught:\n" + Arrays.toString(caughtException.getStackTrace());
                if (expectedException != null) {
                    failReason = expectedException.getName() + " was expected, but " + failReason;
                }
                content = content + "\n\t\t" + failReason;
            }
            assert content != null;
            return title + "\n\t" + content;
        }
    }
}
