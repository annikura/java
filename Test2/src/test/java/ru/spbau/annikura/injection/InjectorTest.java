package ru.spbau.annikura.injection;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InjectorTest {
    @Before
    public void clearInstanceCounter() {
        InstanceCounter.cnt = 0;
    }

    @Test
    public void createInjectorInstance() {
        Injector injector = new Injector();
    }

    @Test(expected = InjectionCycleException.class)
    public void selfDependency() throws Exception {
        Injector.initialize("ru.spbau.annikura.injection.SelfDependent", SelfDependent.class);
    }

    @Test(expected = ImplementationNotFoundException.class)
    public void noDepedencies() throws Exception {
        Injector.initialize("ru.spbau.annikura.injection.DependingOnSimple");
    }

    @Test(expected = AmbiguousImplementationException.class)
    public void ambiguousCall() throws Exception {
        Injector.initialize("ru.spbau.annikura.injection.DependingOnSimple",
                SimpleClass.class,
                ExtendingSimple.class);
    }

    @Test
    public void simpleDependencyTest() throws Exception {
        DependingOnSimple cls = (DependingOnSimple) Injector.initialize(
                "ru.spbau.annikura.injection.DependingOnSimple",
                SimpleClass.class);
        assertEquals(cls.getNum(), 6);
    }


    @Test
    public void simpleDependencyWithInheritanceTest() throws Exception {
        DependingOnSimple cls = (DependingOnSimple) Injector.initialize(
                "ru.spbau.annikura.injection.DependingOnSimple",
                ExtendingSimple.class);
        assertEquals(cls.getNum(), 2);
    }

    @Test
    public void notConnectedLoop() throws Exception {
        DependingOnSimple cls = (DependingOnSimple) Injector.initialize(
                "ru.spbau.annikura.injection.DependingOnSimple",
                ExtendingSimple.class, FirstInLoop.class, SecondInLoop.class, ThirdInLoop.class);
        assertEquals(cls.getNum(), 2);
    }

    @Test(expected = InjectionCycleException.class)
    public void complexLoop() throws Exception {
        Injector.initialize(
                "ru.spbau.annikura.injection.DependingOnLoop",
                ExtendingSimple.class, FirstInLoop.class, SecondInLoop.class, ThirdInLoop.class);
    }

    @Test
    public void testInstantiatedOnce() throws Exception {
        Injector.initialize(
                "ru.spbau.annikura.injection.DoubleDepending",
                InstanceCounter.class);
        assertEquals(1, InstanceCounter.cnt);
    }


    @Test
    public void complexTestInstantiatedOnce() throws Exception {
        Injector.initialize(
                "ru.spbau.annikura.injection.DoubleDoubleDepending",
                InstanceCounter.class, DoubleDepending.class);
        assertEquals(1, InstanceCounter.cnt);
    }

    @Test(expected = ImplementationNotFoundException.class)
    public void severalLeveledNotFound() throws Exception {
        Injector.initialize(
                "ru.spbau.annikura.injection.FirstInLoop",
                ExtendingSimple.class, SecondInLoop.class);
    }
}

class DoubleDoubleDepending {
    DoubleDoubleDepending(DoubleDepending a, InstanceCounter b) {}
}

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
    protected int num = 4;
    public int getNum() {
        return num;
    }
}

class ExtendingSimple extends SimpleClass {
    ExtendingSimple() {
        num = 0;
    }
}

class DependingOnSimple {
    private int num;
    DependingOnSimple(SimpleClass simpleClass) {
        num = simpleClass.getNum() + 2;
    }
    public int getNum() {
        return num;
    }
}


class SelfDependent {
    SelfDependent(SelfDependent selfDependent) {}
}