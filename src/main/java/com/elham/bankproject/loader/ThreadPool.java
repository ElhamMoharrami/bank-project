package com.elham.bankproject.loader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private final Queue<Callable> taskQueue = new LinkedBlockingQueue<>();
    private final List<WorkerThread> threads = new ArrayList<>();

    public ThreadPool(int numThreads) {
        for (int i = 0; i < numThreads; i++) {
            WorkerThread thread = new WorkerThread(taskQueue);
            thread.start();
            threads.add(thread);
        }
    }

    public List<Future<Long>> invokeAll(Collection<? extends Callable> tasks) throws InterruptedException {
        for (Callable<Long> task : tasks) {
            taskQueue.offer(task);
        }
        List<Future<Long>> results = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            FutureTask<Long> futureTask = new FutureTask<>(taskQueue.poll());
            results.add(futureTask);
            new Thread(futureTask).start();
        }
        return results;
    }

    public synchronized void stop() {
        for (WorkerThread thread : threads) {
            thread.doStop();
        }
    }
}