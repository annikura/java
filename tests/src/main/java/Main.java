import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public class Main {
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
            System.out.println("Class or its nullary constructor is not accessible");
            return;
        }

        Method[] methods = cls.getMethods();

    }
}
