package ru.spbau.annikura.lazy;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.ArrayList;
import java.util.function.Supplier;

import static org.junit.Assert.*;

public class ThreadSafeLazyTest {



    @Test
    public void createInstance() {
        Lazy<Integer> lazy = LazyFactory.createThreadSafeLazy(new TestUtils.LifeMeaningSupplier());
        assertEquals(42, (int) lazy.get());
    }

    @Test
    public void multipleCall() {
        Lazy<Integer> lazy = LazyFactory.createThreadSafeLazy(new TestUtils.CountingSupplier());
        for (int i = 0; i < 20; i++) {
            assertEquals(1, (int) lazy.get());
        }
    }

    private <T> void callMultipleThreads(@NotNull Supplier<T> supplier, int threadNum, T expectedResult) {
        final Lazy<T> lazy =
                LazyFactory.createThreadSafeLazy(supplier);
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            threads.add(new Thread(() -> assertEquals(expectedResult, lazy.get())));
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    @Test
    public void multipleSimpleThreadCall() {
        callMultipleThreads(new TestUtils.CountingSupplier(), 100, 1);
    }

    @Test
    public void multipleSlowThreadCall() {
        callMultipleThreads(new TestUtils.SlowCountingSupplier(), 100, 1);
    }
}