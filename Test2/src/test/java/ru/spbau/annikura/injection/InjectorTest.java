package ru.spbau.annikura.injection;

import org.junit.Test;

import static org.junit.Assert.*;

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
        Injector.initialize("int");
    }



}