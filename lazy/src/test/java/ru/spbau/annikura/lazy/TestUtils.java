package ru.spbau.annikura.lazy;

import java.util.function.Supplier;

public class TestUtils {
    public static class LifeMeaningSupplier implements Supplier<Integer> {
        @Override
        public Integer get() {
            return 42;
        }
    }
    public static class CountingSupplier implements Supplier<Integer> {
        int cnt = 0;
        public int getCnt() {
            return cnt;
        }
        @Override
        public Integer get() {
            cnt++;
            return cnt;
        }
    }

    public static class SlowCountingSupplier extends CountingSupplier {
        @Override
        public Integer get() {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ignored) {}
            return super.get();
        }
    }
}
