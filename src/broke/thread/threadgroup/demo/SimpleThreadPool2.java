package broke.thread.threadgroup.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class SimpleThreadPool2 {

    private final int size;

    private final static int DEFAULT_SIZE = 10;

    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    private volatile static AtomicInteger seq = new AtomicInteger(0);

    private final static String THREAD_PREFIX = "SIMPLE_THREAD_POOL-";

    private final static ThreadGroup GROUP = new ThreadGroup("Pool_Group");

    private final static List<WorkTask> THREAD_QUEUE = new ArrayList<>();

    private final static int DEFAULT_TASK_QUEUE_SIZE = 2000;

    private final int queueSize;

    public final DiscardPolicy discardPolicy;

    public final static DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {
        throw new DiscardException(" Discard This Task.");
    };

    private volatile boolean destory = false;

    public SimpleThreadPool2() {
        this(DEFAULT_SIZE, DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPool2(int size, int queueSize, DiscardPolicy discardPolicy) {
        this.size = size;
        this.queueSize = queueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    private void init() {
        for(int i = 0; i < size; i ++) {
            createWorkTask();
        }
    }

    private void submit(Runnable runnable) {
        if(destory) {
            throw new IllegalStateException("The thread pool already destory.");
        }
        synchronized (TASK_QUEUE) {
            if(TASK_QUEUE.size() > queueSize) {
                discardPolicy.discard();
            }
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    private void createWorkTask() {
        WorkTask task = new WorkTask(GROUP, THREAD_PREFIX + (seq.incrementAndGet()));
        task.start();
        THREAD_QUEUE.add(task);
    }

    private enum TaskState {
        FREE, RUNNING, BLOCKED, DEAD
    }

    private static class WorkTask extends Thread {

        private volatile TaskState taskState = TaskState.FREE;

        public WorkTask(ThreadGroup group, String name) {
            super(group, name);
        }

        public TaskState getTaskState() {
            return this.taskState;
        }

        @Override
        public void run() {
            OUTER:
            while(this.taskState != TaskState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    while(TASK_QUEUE.isEmpty()) {
                        try {
                            taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            System.out.println("caught interruptException");
                            break OUTER;
                        }
                    }

                    runnable = TASK_QUEUE.remove();
                }

                if(runnable != null) {
                    taskState = TaskState.RUNNING;
                    runnable.run();
                    taskState = TaskState.FREE;
                }
            }
        }

        public void close() {
            this.taskState = TaskState.DEAD;
        }
    }

    public static class DiscardException extends RuntimeException {

        public DiscardException(String message) {
            super(message);
        }
    }

    public interface DiscardPolicy {

        void discard() throws DiscardException;
    }

    public void shutdown() {
        while(!TASK_QUEUE.isEmpty()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int initVal = THREAD_QUEUE.size();
        while(initVal > 0) {
            for(WorkTask task : THREAD_QUEUE) {
                if(task.getTaskState() == TaskState.BLOCKED) {
                    task.interrupt();
                    task.close();
                    initVal --;
                }else {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        this.destory = true;
        System.out.println("The thread pool disposed.");
    }

    public int getSize() {
        return size;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public boolean destory() {
        return this.destory;
    }

    public static void main(String[] args) {
        SimpleThreadPool2 simpleThreadPool = new SimpleThreadPool2();
        IntStream.rangeClosed(0, 40).forEach(i -> {
            simpleThreadPool.submit(() -> {
                System.out.println("the runnable " + i + " be serviced by " + Thread.currentThread().getName() + " started.");
                try {
                    Thread.sleep(1_000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("the runnable " + i + " be serviced by " + Thread.currentThread().getName() + " finished.");
            });
        });

        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        simpleThreadPool.shutdown();


//        simpleThreadPool.submit(() -> System.out.println("============="));
    }
}
