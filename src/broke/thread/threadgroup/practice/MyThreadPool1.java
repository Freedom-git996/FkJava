package broke.thread.threadgroup.practice;

import java.util.LinkedList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class MyThreadPool1 {

    private static final int DEFAULT_MIN = 4;

    private static final int DEFAULT_ACTIVE = 8;

    private static final int DEFAULT_MAX = 12;

    private AtomicInteger size;

    private final int min;

    private final int active;

    private final int max;

    private LinkedList<Runnable> taskRunnableQueue = new LinkedList<>();

    private LinkedList<WorkThread> workThreadQueue = new LinkedList<>();

    private final static ThreadGroup group = new ThreadGroup("Pool_Group");

    private volatile static AtomicInteger seq = new AtomicInteger(0);

    private final static String THREAD_PREFIX = "SIMPLE_THREAD_POOL-";

    private final Object LOCK = new Object();

    public MyThreadPool1() {
        this(DEFAULT_MIN, DEFAULT_ACTIVE, DEFAULT_MAX);
    }

    public MyThreadPool1(int min, int active, int max) {
        this.min = min;
        this.active = active;
        this.max = max;
        this.size = new AtomicInteger(0);
        init();
    }

    public void init() {
        for(int i = 0; i < min; i++) {
            createTask();
            size.incrementAndGet();
        }
    }

    public void createTask() {
        WorkThread workThread = new WorkThread(group, THREAD_PREFIX + (seq.incrementAndGet()));
        workThread.start();
        workThreadQueue.add(workThread);
    }

    public void submit(Runnable task) {
        synchronized (taskRunnableQueue) {
            taskRunnableQueue.add(task);
            taskRunnableQueue.notifyAll();
        }
    }

    public void shutdown() {
        while(!taskRunnableQueue.isEmpty()) {
            try {
                Thread.sleep(5_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        synchronized(workThreadQueue) {
            int initVal = workThreadQueue.size();
            while(initVal > 0) {
                for(WorkThread workThread : workThreadQueue) {
                    workThread.interrupt();
                    initVal --;
                }
            }
        }
        System.out.println("The thread pool disposed.");
    }

    private class WorkThread extends Thread {

        public WorkThread(ThreadGroup group, String name) {
            super(group, name);
        }

        @Override
        public void run() {
            Runnable task = null;
            OUTER:
            while (true) {
                synchronized (taskRunnableQueue) {
                    while (taskRunnableQueue.isEmpty()) {
                        try {
                            taskRunnableQueue.wait();
                        } catch (InterruptedException e) {
                            System.out.println(Thread.currentThread().getName() + " be caught interruptException");
                            break OUTER;
                        }
                    }
                    task = taskRunnableQueue.remove();
                }

                if (task != null) {
                    task.run();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThreadPool1 myThreadPool1 = new MyThreadPool1();
        IntStream.rangeClosed(0, 40).forEach(i -> {
            myThreadPool1.submit(() -> {
                System.out.println("the runnable " + i + " be serviced by " + Thread.currentThread().getName() + " started.");
                try {
                    System.out.println("the runnable " + i + " is running.");
                    Thread.sleep(1_000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("the runnable " + i + " be serviced by " + Thread.currentThread().getName() + " finished.");
            });
        });

        try {
            Thread.sleep(15_000L);
            System.out.println("==== the pool has " + myThreadPool1.size + " thread is running. ====");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        myThreadPool1.shutdown();

        try {
            Thread.sleep(1_000L);
            System.out.println("==== the pool has " + myThreadPool1.size + " thread is running. ====");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
