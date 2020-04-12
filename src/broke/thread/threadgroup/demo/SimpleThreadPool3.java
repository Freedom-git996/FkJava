package broke.thread.threadgroup.demo;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class SimpleThreadPool3 extends Thread {

    private int size;

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

    private int min;

    private int max;

    private int active;

    public SimpleThreadPool3() {
        this(4, 8, 12, DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPool3(int min, int active, int max, int queueSize, DiscardPolicy discardPolicy) {
        this.min = min;
        this.active = active;
        this.max = max;
        this.queueSize = queueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    private void init() {
        for (int i = 0; i < this.min; i++) {
            createWorkTask();
        }
        this.size = min;
        this.start();
    }

    private void submit(Runnable runnable) {
        if (destory) {
            throw new IllegalStateException("The thread pool already destory.");
        }
        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.size() > queueSize) {
                discardPolicy.discard();
            }
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    @Override
    public void run() {
        while (!destory) {
            System.out.printf("Pool#Min: %d, Active: %d, Max: %d, Current: %d, QueueSize: %d\n",
                    this.min, this.active, this.max, this.size, TASK_QUEUE.size());
            try {
                Thread.sleep(3_000);
                if (TASK_QUEUE.size() > active && size < active) {
                    for (int i = size; i < active; i++) {
                        createWorkTask();
                    }
                    System.out.println("The pool increment to active.");
                    size = active;
                } else if (TASK_QUEUE.size() > max && size < max) {
                    for (int i = size; i < active; i++) {
                        createWorkTask();
                    }
                    System.out.println("The pool increment to max.");
                    size = max;
                }
                synchronized (THREAD_QUEUE) {
                    if (TASK_QUEUE.isEmpty() && size > active) {
                        int releaseSize = size - active;
                        for (Iterator<WorkTask> it = THREAD_QUEUE.iterator(); it.hasNext(); ) {
                            if (releaseSize <= 0) {
                                break;
                            }
                            WorkTask task = it.next();
                            task.close();
                            task.interrupt();
                            it.remove();
                            releaseSize--;
                        }
                        size = active;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
            while (this.taskState != TaskState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            break OUTER;
                        }
                    }

                    runnable = TASK_QUEUE.remove();
                }

                if (runnable != null) {
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
        while (!TASK_QUEUE.isEmpty()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        synchronized(THREAD_QUEUE) {
            int initVal = THREAD_QUEUE.size();
            while (initVal > 0) {
                for (WorkTask task : THREAD_QUEUE) {
                    if (task.getTaskState() == TaskState.BLOCKED) {
                        task.interrupt();
                        task.close();
                        initVal--;
                    } else {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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

    public boolean isDestory() {
        return this.destory;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getActive() {
        return active;
    }

    public static void main(String[] args) {
        SimpleThreadPool3 simpleThreadPool = new SimpleThreadPool3();
        IntStream.rangeClosed(0, 80).forEach(i -> {
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
