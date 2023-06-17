package com.elham.bankproject.loader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPool {
    private static final Logger logger = LogManager.getLogger(ThreadPool.class);
    private BlockingQueue<Runnable> taskQueue = null;
    private final List<WorkerThread> runnables = new ArrayList<>();
    private boolean isStopped = false;

    public ThreadPool(int noOfThreads, int maxNoOfTasks) {
        taskQueue = new ArrayBlockingQueue<>(maxNoOfTasks);

        for (int i = 0; i < noOfThreads; i++) {
            WorkerThread poolThreadRunnable = new WorkerThread(taskQueue);
            runnables.add(poolThreadRunnable);
        }
        for (WorkerThread runnable : runnables) {
            new Thread(runnable).start();
        }
    }

    public synchronized void execute(Runnable task) throws Exception {
        if (this.isStopped) {
            logger.error("can not execute task, thread pool has been stopped.");
            throw new IllegalStateException("ThreadPool is stopped");
        }
        this.taskQueue.offer(task);
    }

    public synchronized void stop() {
        this.isStopped = true;
        for (WorkerThread runnable : runnables) {
            runnable.doStop();
        }
    }

    public synchronized void waitUntilAllTasksFinished() {
        while (this.taskQueue.size() > 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                logger.warn("issue at waiting until all tasks are finished.");
            }
        }
    }
}