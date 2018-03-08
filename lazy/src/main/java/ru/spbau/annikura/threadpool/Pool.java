package ru.spbau.annikura.threadpool;

import org.jetbrains.annotations.NotNull;
import ru.spbau.annikura.lazy.Lazy;
import ru.spbau.annikura.lazy.LazyFactory;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * This class owns a pool of threads and, given a set of tasks, executes them in parallel using Pool's threads.
 */
public class Pool {
    private final static boolean QUIET = true;
    private final ArrayList<LightFuture> tasks = new ArrayList<>();
    private final ArrayList<Thread> threads = new ArrayList<>();

    /**
     * Creates new LightFuture out of supplier and inserts it into the task queue.
     * @param supplier new task to be executed
     * @param <T> task output result
     * @return LightFuture holding the given task.
     */
    public <T> LightFuture<T> createNewTask(final @NotNull Supplier<T> supplier) {
        return new LightFutureImpl<>(supplier);
    }

    /**
     * Creates Pool with the given number of threads.
     * @param numberOfThreads number of threads in the pool
     */
    public Pool(int numberOfThreads) {
        Runnable runnable = new Runnable() {
            private void log(String message) {
                if (!QUIET) {
                    Logger.getAnonymousLogger().info(Long.toString(Thread.currentThread().getId()) + " " + message);
                }
            }

            @Override
            public void run() {
                LightFuture currentTask;
                while (true) {
                    log("waiting to lock tasks");
                    synchronized (tasks) {
                        log("locked tasks");
                        if (Thread.interrupted()) {
                            log("interrupted, shutting down...");
                            return;
                        }
                        if (tasks.isEmpty()) {
                            try {
                                log("going to wait");
                                tasks.wait();
                            } catch (InterruptedException e) {
                                log("wait -> shutting down...");
                                return;
                            }
                        }
                        log("woke up, getting task");
                        if (tasks.isEmpty()) {
                            if (!Thread.currentThread().isInterrupted()) {
                                Logger.getAnonymousLogger().warning(Thread.currentThread().toString() +
                                        "was notified on empty tasks list.");
                                // not very healthy, but it sometimes happens when the active thread took task
                                // before it was found by the notified thread.
                            }
                            continue;
                        }
                        currentTask = tasks.get(0);
                        tasks.remove(0);

                    }

                    log("ready for task");
                    currentTask.get();
                    log("completed task");
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
        }
    }

    /**
     * Adds new LightFuture task to the task queue.
     * @param task task that will be added
     */
    private void registerTask(final @NotNull LightFuture task) {
        synchronized (tasks) {
            tasks.add(task);
            tasks.notify();
        }
    }



    /**
     * Standard LightFuture implementation.
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
