package ru.spbau.annikura.injection;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

class DoubleDepending {
    DoubleDepending(InstanceCounter a, InstanceCounter b) {}
}

class InstanceCounter {
    static int cnt = 0;
    InstanceCounter() {
        cnt++;
    }
}

class DependingOnLoop {
    DependingOnLoop(SecondInLoop secondInLoop) {}
}

class FirstInLoop {
    FirstInLoop(SecondInLoop secondInLoop) {}
}

class SecondInLoop {
    SecondInLoop(ThirdInLoop ThirdInLoop) {}
}


class ThirdInLoop {
    ThirdInLoop(FirstInLoop firstInLoop) {}
}


class SimpleClass {
    int num = 4;
}

class ExtendingSimple extends SimpleClass {
    ExtendingSimple() {
        num = 0;
    }
}

class DependingOnSimple {
    int num;
    DependingOnSimple(SimpleClass simpleClass) {
        num = simpleClass.num + 2;
    }
}


class SelfDependent {
    SelfDependent(SelfDependent selfDependent) {}
}

public class InjectorTest {

    @Test(expected = InjectionCycleException.class)
    public void selfDependency() throws Exception {
        Injector.initialize("ru.spbau.annikura.injection.SelfDependent", SelfDependent.class);
    }

    @Test(expected = ImplementationNotFoundException.class)
    public void noDepedencies() throws Exception {
        Injector.initialize("ru.spbau.annikura.injection.DependingOnSimple");
    }

    @Test(expected = AmbiguousImplementationException.class)
    public void ambigiousCall() throws Exception {
        Injector.initialize("ru.spbau.annikura.injection.DependingOnSimple",
                SimpleClass.class,
                ExtendingSimple.class);
    }

    @Test
    public void simpleDependencyTest() throws Exception {
        DependingOnSimple cls = (DependingOnSimple) Injector.initialize(
                "ru.spbau.annikura.injection.DependingOnSimple",
                SimpleClass.class);
        assertEquals(cls.num, 6);
    }


    @Test
    public void simpleDependencyWithInheritanceTest() throws Exception {
        DependingOnSimple cls = (DependingOnSimple) Injector.initialize(
                "ru.spbau.annikura.injection.DependingOnSimple",
                ExtendingSimple.class);
        assertEquals(cls.num, 2);
    }

    @Test
    public void notConnectedLoop() throws Exception {
        DependingOnSimple cls = (DependingOnSimple) Injector.initialize(
                "ru.spbau.annikura.injection.DependingOnSimple",
                ExtendingSimple.class, FirstInLoop.class, SecondInLoop.class, ThirdInLoop.class);
        assertEquals(cls.num, 2);
    }

    @Test(expected = InjectionCycleException.class)
    public void complexLoop() throws Exception {
        Injector.initialize(
                "ru.spbau.annikura.injection.DependingOnLoop",
                ExtendingSimple.class, FirstInLoop.class, SecondInLoop.class, ThirdInLoop.class);
    }

    @Test
    public void testInstanciatedOnce() throws Exception {
        Injector.initialize(
                "ru.spbau.annikura.injection.DoubleDepending",
                InstanceCounter.class);
        assertEquals(1, InstanceCounter.cnt);
    }

}