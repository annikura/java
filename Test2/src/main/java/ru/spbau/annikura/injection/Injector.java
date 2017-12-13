package ru.spbau.annikura.injection;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class provides Injection dependency initializations tool.
 */
public class Injector {
    /**
     * Given a name of the class to be initialized and a set of classes, creates an instance of the root class.
     * It is guaranteed that every class will be initialized not more than once.
     * @param rootClassName name of the class to be created
     * @param classes set of the classes that can be used to create a root instance.
     * @return a root class instance.
     * @throws InjectionCycleException if a circle dependency was found
     * @throws AmbiguousImplementationException if some of the required instance can be created ambiguously
     * @throws ImplementationNotFoundException if its impossible to create a head class instance
     * with only used of the given set of classes
     * @throws ClassNotFoundException if rootClassName class was not found
     */
    @NotNull
    public static Object initialize(final @NotNull String rootClassName,
                                    final @NotNull Class<?>... classes) throws AmbiguousImplementationException,
            InjectionCycleException, ImplementationNotFoundException, ClassNotFoundException,
            IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<?> cls = Class.forName(rootClassName);
        return createInstances(cls, classes, new HashMap<>(), new HashSet<>());
    }

    @NotNull
    private static Object createInstances(
            final @NotNull Class<?> cls, final @NotNull Class<?>[] classes,
            final @NotNull HashMap<Class<?>, Object> instances,
            final @NotNull HashSet<Class<?>> used)
            throws InjectionCycleException,
            AmbiguousImplementationException,
            ImplementationNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        used.add(cls);
        Constructor<?> constructor = cls.getDeclaredConstructors()[0];
        Class<?>[] parameters = constructor.getParameterTypes();
        Object[] args =  new Object[constructor.getParameterCount()];
        for (int i = 0; i < parameters.length; i++) {
            int cnt = 0;
            Class<?> nextClass = null;
            for (Class<?> other : classes) {
                if (parameters[i].isAssignableFrom(other)) {
                    cnt++;
                    nextClass = other;
                }
            }
            if (cnt > 1) {
                throw new AmbiguousImplementationException("AmbiguousImplementationException");
            }
            if (cnt == 0) {
                throw new ImplementationNotFoundException("ImplementationNotFoundException");
            }
            if (used.contains(nextClass)) {

                throw new InjectionCycleException("InjectionCycleException");
            }

            if (instances.get(nextClass) != null) {
                args[i] = instances.get(parameters[i]);
                continue;
            }
            args[i] = createInstances(nextClass, classes, instances, used);
        }
        return constructor.newInstance(args);
    }
}
