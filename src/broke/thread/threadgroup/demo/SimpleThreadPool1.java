package broke.thread.threadgroup.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class SimpleThreadPool1 {

    private final int size;

    private final static int DEFAULT_SIZE = 10;

    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    private volatile static AtomicInteger seq = new AtomicInteger(0);

    private final static String THREAD_PREFIX = "SIMPLE_THREAD_POOL-";

    private final static ThreadGroup GROUP = new ThreadGroup("Pool_Group");

    private final static List<WorkTask> THREAD_QUEUE = new ArrayList<>();

    public SimpleThreadPool1() {
        this(DEFAULT_SIZE);
    }

    public SimpleThreadPool1(int size) {
        this.size = size;
        init();
    }

    private void init() {
        for(int i = 0; i < size; i ++) {
            createWorkTask();
        }
    }

    private void submit(Runnable runnable) {
        synchronized (TASK_QUEUE) {
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

    public static void main(String[] args) {
        SimpleThreadPool1 simpleThreadPool = new SimpleThreadPool1();
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
    }
}
