package com.elham.bankproject.loader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Queue;
import java.util.concurrent.Callable;

public class WorkerThread<T> extends Thread {
    private static final Logger logger = LogManager.getLogger(WorkerThread.class);
    private Thread thread = null;
    private Queue<Callable<T>> taskQueue = null;
    private boolean isStopped = false;

    public WorkerThread(Queue<Callable<T>> queue) {
        taskQueue = queue;
    }

    public void run() {
        this.thread = Thread.currentThread();
        while (!isStopped()) {
            try {
                while (taskQueue == null) {
                    Callable<T> runnable = taskQueue.poll();
                    runnable.call();
                }
            } catch (Exception e) {
                logger.warn("could not run the task. issue at WorkerThread run method.");
            }
        }
    }
//todo why here we have synch
    public synchronized void doStop() {
        isStopped = true;
        this.thread.interrupt();
    }

    public synchronized boolean isStopped() {
        return isStopped;
    }
}


