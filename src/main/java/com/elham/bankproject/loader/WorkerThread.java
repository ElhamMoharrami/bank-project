package com.elham.bankproject.loader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Queue;
import java.util.concurrent.Callable;

public class WorkerThread extends Thread {
    private static final Logger logger = LogManager.getLogger(WorkerThread.class);
    private Thread thread = null;
    private Queue<Callable> taskQueue = null;
    private boolean isStopped = false;

    public WorkerThread(Queue<Callable> queue) {
        taskQueue = queue;
    }

    public void run() {
        this.thread = Thread.currentThread();
        while (!isStopped()) {
            try {
                while (taskQueue == null) {
                    Callable runnable = taskQueue.poll();
                    runnable.call();
                }
            } catch (Exception e) {
                logger.warn("could not run the task. issue at WorkerThread run method.");
            }
        }
    }

    public synchronized void doStop() {
        isStopped = true;
        this.thread.interrupt();
    }

    public synchronized boolean isStopped() {
        return isStopped;
    }
}


