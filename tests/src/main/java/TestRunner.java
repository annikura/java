import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestRunner {
    private final Class<?> cls;
    private final Object instance;

    private final List<TestResult> results;

    public TestRunner(@NotNull Class<?> cls) throws IllegalAccessException, InstantiationException, BeforeClassException, AfterClassException {
        this.cls = cls;
        instance = cls.newInstance();


        List<Method> beforeClassMethods = classifyByAnnotation(cls.getDeclaredMethods(), BeforeClass.class);
        List<Method> afterClassMethods = classifyByAnnotation(cls.getDeclaredMethods(), AfterClass.class);
        List<Method> beforeMethods = classifyByAnnotation(cls.getDeclaredMethods(), Before.class);
        List<Method> afterMethods = classifyByAnnotation(cls.getDeclaredMethods(), After.class);
        List<Method> testMethods = classifyByAnnotation(cls.getDeclaredMethods(), Test.class);

        try {
            runMethods(instance, beforeClassMethods);
        } catch (InvocationTargetException e) {
            results = Collections.emptyList();
            throw new BeforeClassException(
                    "Exception occurred while executing @beforeClass methods. " +
                            "No testing was performed.\n" + Arrays.toString(e.getTargetException().getStackTrace()));
        }

        results = runTests(instance, beforeMethods, afterMethods, testMethods);

        try {
            runMethods(instance, afterClassMethods);
        } catch (InvocationTargetException e) {
            throw new AfterClassException(
                    "Exception occurred while executing @afterClass methods. "
                            + Arrays.toString(e.getTargetException().getStackTrace()));

        }
    }

    static void runMethods(@NotNull Object obj, @NotNull List<Method> methods)
            throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            method.invoke(obj, method);
        }
    }

    @NotNull
    static List<Method> classifyByAnnotation(Method[] methods, Class<? extends Annotation> annotation) {
        ArrayList<Method> result = new ArrayList<>();
        for (Method method : methods) {
            if (method.getAnnotation(annotation) != null) {
                result.add(method);
            }
        }
        return result;
    }

    static @NotNull List<TestResult> runTests(@NotNull Object obj,
                                              @NotNull List<Method> beforeMethods, @NotNull List<Method> afterMethods,
                                              @NotNull List<Method> testMethods) throws IllegalAccessException {
        List<TestResult> results = new ArrayList<>();
        for (Method method : testMethods) {
            TestResult result = null;
            try {
                runMethods(obj, beforeMethods);
            } catch (InvocationTargetException e) {

            }
            result = new TestResult(obj, method));
            try {
                runMethods(obj, afterMethods);
            } catch (InvocationTargetException e) {

            }
        }
    }

    List<TestResult> getResults() {
        return results;
    }

    static class TestResult {
        Throwable caughtException = null;
        Class<? extends Exception> expectedException = null;
        long executionTime = 0;

        String ignoreReason = null;
        final Method method;

        TestResult(@NotNull Object obj, @NotNull Method method) throws IllegalAccessException {
            this.method = method;
            unpackTestAnnotation(method);
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

        private void unpackTestAnnotation(@NotNull Method method) {
            Test test = method.getAnnotation(Test.class);
            assert test != null;

            if (!test.expected().equals(NoException.class)) {
                expectedException = test.expected();
            }
            if (!test.ignored().equals("")) {
                ignoreReason = test.ignored();
            }
        }

        boolean hasFailed() {
            return !isIgnored() && !hasPassed();
        }

        boolean isIgnored() {
            return ignoreReason != null;
        }
        boolean hasPassed() {
            if (isIgnored()) return false;
            if (caughtException != null) {
                return caughtException.getClass().equals(expectedException);
            }
            return expectedException == null;
        }

        String generateReport() {
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
