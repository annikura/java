import org.jetbrains.annotations.NotNull;
import ru.spbau.annikura.tester.StateException;
import ru.spbau.annikura.tester.TestRunner;

import java.util.List;

/**
 * Class running an application that executes tests located in the given class
 */
public class Tester {
    /**
     * Application main method.
     * @param args single argument representing the class with the tests is expected.
     */
    public static void main(@NotNull String[] args) {
        if (args.length != 1) {
            System.out.println("Usage:\n\tparam: 'some.class.to.be.Tested'");
            return;
        }
        @NotNull Class cls;
        try {
            cls = Class.forName(args[0]);
        } catch (ClassNotFoundException e) {
            System.out.println("Class " + args[0] + " cannot be found");
            return;
        }

        @NotNull TestRunner runner;
        try {
            runner = new TestRunner(cls);
        } catch (InstantiationException e) {
            System.out.println("Failed to instantiate class.\n\t" +
                    "Check that the class is not abstract or interface or primitive type " +
                    "and it contains nullary constructor");
            return;
        } catch (IllegalAccessException e) {
            System.out.println("Class nullary constructor is not accessible");
            return;
        }
        try {
            runner.runTests();
        } catch (IllegalAccessException e) {
            printReport(runner);
            System.out.println("Some of the test-related annotated methods is not accessible: " + e.getMessage());
            return;
        } catch (StateException e) {
            printReport(runner);
            e.printStackTrace();
            return;
        }
        printReport(runner);
    }

    /**
     * Prints tests report.
     * @param runner TestRunner to retrieve reports from.
     */
    private static void printReport(@NotNull TestRunner runner) {
        List<TestRunner.TestResult> results = runner.getResults();
        assert results != null;
        System.out.println("==================================");
        for (TestRunner.TestResult result : results) {
            System.out.println(result.generateReport());
            System.out.println("---");
        }
    }
}
