package ru.spbau.annikura.threadpool;

import javafx.scene.effect.Light;
import org.junit.After;
import org.junit.Test;
import ru.spbau.annikura.lazy.TestUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.*;


public class PoolTest {
    Pool pool;

    boolean waitFor(long ms, LightFuture... tasks) {
        long time = System.currentTimeMillis();
        for (LightFuture task : tasks) {
            while (!task.isReady()) {
                if (time + ms < System.currentTimeMillis())
                    return false;
            }
        }
        return true;
    }

    @After
    public void tearDown() throws Exception {
        pool.shutdown();
    }

    @Test
    public void createInstance() {
        pool = new Pool(1);
    }

    @Test
    public void noThreadsOneTask() throws LightExecutionException {
        pool = new Pool(0);
        final String result = "Success";
        LightFuture<String> task = pool.createNewTask(() -> result);
        assertEquals(result, task.get());
    }


    @Test
    public void oneThreadOneTask() throws LightExecutionException {
        pool = new Pool(1);
        final String result = "Success";
        LightFuture<String> task = pool.createNewTask(() -> result);
        waitFor(5000, task);
        assertEquals(result, task.get());
    }

    @Test
    public void oneThreadManyTasks() {
        pool = new Pool(1);
        final int numOfTasks = 10;
        final int[] resource = new int[numOfTasks];
        final LightFuture[] tasks = new LightFuture[numOfTasks];

        MemorizingSupplierCreator<Integer> msc = new MemorizingSupplierCreator<>();
        for (int i = 0; i < numOfTasks; i++) {
            resource[i] = i;
            int finalI = i;
            tasks[i] = pool.createNewTask(msc.createMemorizingSupplier(() -> resource[finalI]));
        }
        waitFor(5000, tasks);
        for (int i = 0; i < numOfTasks; i++) {
            assertEquals(i, (int) msc.getMemory().get(i));
        }
    }

    @Test
    public void someThreadsSomeTasks() {
        pool = new Pool(10);
        final int numOfTasks = 20;
        final int[] resource = new int[numOfTasks];
        final LightFuture[] tasks = new LightFuture[numOfTasks];

        MemorizingSupplierCreator<Integer> msc = new MemorizingSupplierCreator<>();
        for (int i = 0; i < numOfTasks; i++) {
            resource[i] = i;
            int finalI = i;
            tasks[i] = pool.createNewTask(msc.createMemorizingSupplier(() -> resource[finalI]));
        }

        waitFor(5000, tasks);
        msc.getMemory().sort(Comparator.naturalOrder());
        for (int i = 0; i < numOfTasks; i++) {
            assertEquals(i, (int) msc.getMemory().get(i));
        }
    }

    @Test
    public void checkShuffleOnManyTasks() {
        pool = new Pool(100);
        final int numOfTasks = 10000;
        final int[] resource = new int[numOfTasks];
        final LightFuture[] tasks = new LightFuture[numOfTasks];

        MemorizingSupplierCreator<Integer> msc = new MemorizingSupplierCreator<>();
        for (int i = 0; i < numOfTasks; i++) {
            resource[i] = i;
            int finalI = i;
            tasks[i] = pool.createNewTask(msc.createMemorizingSupplier(() -> resource[finalI]));
        }

        waitFor(5000, tasks);
        boolean isShuffled = false;
        for (int i = 0; i < numOfTasks; i++) {
            isShuffled = isShuffled || (msc.getMemory().get(i) != i);
        }
        assertTrue(isShuffled);
    }

    @Test
    public void manyThreadsManyTasks() {
        pool = new Pool(100);
        final int numOfTasks = 20000;
        final int[] resource = new int[numOfTasks];
        final LightFuture[] tasks = new LightFuture[numOfTasks];

        MemorizingSupplierCreator<Integer> msc = new MemorizingSupplierCreator<>();
        for (int i = 0; i < numOfTasks; i++) {
            resource[i] = i;
            int finalI = i;
            tasks[i] = pool.createNewTask(msc.createMemorizingSupplier(() -> resource[finalI]));
        }

        waitFor(10000, tasks);
        msc.getMemory().sort(Comparator.naturalOrder());
        for (int i = 0; i < numOfTasks; i++) {
            assertEquals(i, (int) msc.getMemory().get(i));
        }
    }

    @Test
    public void checkShutdown() {
        pool = new Pool(2);
        final int numOfTasks = 10;
        final LightFuture[] tasks = new LightFuture[numOfTasks];

        MemorizingSupplierCreator<Integer> msc = new MemorizingSupplierCreator<>();
        for (int i = 0; i < numOfTasks; i++) {
            tasks[i] = pool.createNewTask(msc.createMemorizingSupplier(new SlowSupplier<>(2000)));
        }
        pool.shutdown();
        assertNotEquals(numOfTasks, msc.getMemory().size());
    }

    @Test
    public void thenApplySimple() throws LightExecutionException {
        pool = new Pool(1);
        String result = pool
                .createNewTask(() -> 5)
                .thenApply((Function<Object, String>) Object::toString)
                .get();
        assertEquals("5", result);
    }


    @Test
    public void thenApplyCheckBlock() throws LightExecutionException {
        pool = new Pool(2);
        long time = System.currentTimeMillis();
        LightFuture task = pool
                .createNewTask(new SlowSupplier<>(1000))
                .thenApply(o -> "Wow!");
        boolean readiness = task.isReady();
        if (time + 1000 > System.currentTimeMillis())
            assertEquals(false, readiness);
        assertEquals("Wow!", task.get());
    }

    @Test
    public void thenApplyWithNoThreads() throws LightExecutionException {
        pool = new Pool(0);
        String result = pool
                .createNewTask(() -> 5)
                .thenApply((Function<Object, String>) Object::toString)
                .get();
        assertEquals("5", result);
    }

    @Test
    public void multipleThenApplyForOneTask() throws LightExecutionException {
        pool = new Pool(4);
        TestUtils.CountingSupplier supplier = new TestUtils.CountingSupplier();
        LightFuture<Integer> mainTask = pool.createNewTask(supplier);
        LightFuture<Integer> task1 = mainTask.thenApply(i -> i + 5);
        LightFuture<Integer> task2 = mainTask.thenApply(i -> i + 10);
        LightFuture<Integer> task3 = mainTask.thenApply(i -> i + 15);

        assertEquals(6, (int) task1.get());
        assertEquals(11, (int) task2.get());
        assertEquals(16, (int) task3.get());

        assertEquals(1, supplier.getCnt());
    }
    
    @Test
    public void checkTaskExecutedOnce() throws LightExecutionException {
        pool = new Pool(0);
        TestUtils.CountingSupplier supplier = new TestUtils.CountingSupplier();
        LightFuture<Integer> task1 = pool.createNewTask(supplier);
        LightFuture<Integer> task2 = task1.thenApply(i -> i + 2);
        assertEquals(1, (int) task1.get());
        assertEquals(1, (int) task1.get());
        assertEquals(3, (int) task2.get());

        assertEquals(1, supplier.getCnt());
    }

    @Test(expected = LightExecutionException.class)
    public void simplyGetException() throws LightExecutionException {
        pool = new Pool(1);
        LightFuture<Integer> task = pool.createNewTask(() -> 1 / 0);
        task.get();
    }

    @Test(expected = LightExecutionException.class)
    public void getExceptionFromThenApply() throws LightExecutionException {
        pool = new Pool(1);
        LightFuture<Integer> task = pool.createNewTask(() -> 1 / 0);
        LightFuture<String> task1 = task.thenApply(Object::toString);
        task1.get();
    }

    @Test
    public void simplyGetNoException() {
        pool = new Pool(1);
        LightFuture<Integer> task = pool.createNewTask(() -> 1 / 0);
    }
}

class MemorizingSupplierCreator<T> {
    private final ArrayList<T> memory = new ArrayList<>();

    public ArrayList<T> getMemory() {
        return memory;
    }

    public Supplier<T> createMemorizingSupplier(Supplier<T> supplier) {
        return new MemorizingSupplier(supplier);
    }

    private class MemorizingSupplier implements Supplier<T> {
        Supplier<T> supplier;
        MemorizingSupplier(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        @Override
        public T get() {
            T result = supplier.get();
            synchronized (memory) {
                memory.add(result);
            }
            return result;
        }
    }
}

class SlowSupplier<T> implements Supplier<T> {
    private long delay;

    public SlowSupplier(long delay) {
        this.delay = delay;
    }

    @Override
    public T get() {
        long time = System.currentTimeMillis();
        while (time + delay > System.currentTimeMillis()) {}
        return null;
    }
}