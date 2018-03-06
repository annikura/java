package ru.spbau.annikura.threadpool;

import com.sun.istack.internal.NotNull;
import ru.spbau.annikura.lazy.Lazy;
import ru.spbau.annikura.lazy.LazyFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * This class owns a pool of threads and, given a set of tasks, executes them in parallel using Pool's threads.
 */
public class Pool {
    private final ArrayList<LightFuture> tasks = new ArrayList<>();
    private final ArrayList<Thread> threads = new ArrayList<>();

    /**
     * Creates new LightFuture out of supplier and inserts it into the task queue.
     * @param supplier new task to be executed
     * @param <T> task output result
     * @return LightFuture holding the given task.
     */
    public <T> LightFuture<T> createNewTask(final @NotNull Supplier<T> supplier) {
        return new LightFutureImpl<T>(supplier);
    }

    /**
     * Creates Pool with the given number of threads.
     * @param numberOfThreads number of threads in the pool
     */
    public Pool(int numberOfThreads) {
        Runnable runnable = () -> {
            LightFuture currentTask = null;
            while (true) {
                synchronized (tasks) {
                    if (Thread.interrupted()) {
                        break;
                    }
                    if (tasks.isEmpty()) {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                    if (tasks.isEmpty()) {
                        Logger.getAnonymousLogger().warning("Notified on empty tasks list. Sleeping...");
                        continue;
                    }
                    currentTask = tasks.get(0);
                    tasks.remove(0);
                }

                try {
                    currentTask.get();
                } catch (LightExecutionException e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(runnable);
            threads.add(thread);
            thread.start();
        }
    }

    /**
     * Interrupts all the threads in the pool.
     * It is guaranteed that the thread will be killed as soon as it will
     * finish the task it was executed at the moment of interruption.
     * If there was no such a task, it will be killed immedeately.
     */
    public void shutdown() {
        synchronized (tasks) {
            for (Thread thread : threads) {
                thread.interrupt();
            }
            tasks.notifyAll();
        }
    }

    /**
     * Adds new LightFuture task to the task queue.
     * @param task
     */
    private void registerTask(final @NotNull LightFuture task) {
        synchronized (tasks) {
            tasks.add(task);
            tasks.notify();
        }
    }



    /**
     * Standart LightFuture implementation.
     */
    class LightFutureImpl<T> implements LightFuture<T> {
        private boolean ready = false;
        private final Lazy<T> lazy;

        LightFutureImpl(final @NotNull Supplier<T> supplier) {
            lazy = LazyFactory.createThreadSafeLazy(supplier);
            registerTask(this);
        }

        /**
         * @see LightFuture#isReady()
         */
        @Override
        public boolean isReady() {
            return ready;
        }

        /**
         * @see LightFuture#get()
         */
        @Override
        public T get() {
            lazy.get();
            ready = true;
            return lazy.get();
        }

        /**
         * @see LightFuture#thenApply(Function)
         */
        @Override
        public <Y> LightFuture<Y> thenApply(Function<? super T, Y> function) {
            return new LightFutureImpl<>(() -> function.apply(LightFutureImpl.this.get()));
        }
    }

}
