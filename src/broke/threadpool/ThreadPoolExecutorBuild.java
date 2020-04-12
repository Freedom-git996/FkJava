package broke.threadpool;

import java.util.concurrent.*;

public class ThreadPoolExecutorBuild {

    public static void main(String[] args) {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) buildThreadPoolExecutor();

        ExecutorService executorService1 = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });

        ExecutorService executorService2 = Executors.newFixedThreadPool(10);

        ExecutorService executorService3 = Executors.newSingleThreadExecutor();

        ExecutorService executorService4 = Executors.newWorkStealingPool();

        ExecutorCompletionService executorCompletionService = new ExecutorCompletionService<Integer>(executorService2);

        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(2);

        ScheduledExecutorService scheduledExecutorService1 = Executors.newSingleThreadScheduledExecutor();

        Task task = new Task();
        executorService.submit(task);
        executorService.submit(task);
        executorService.submit(task);

        int activeCount = -1;
        int queueSize = -1;
        while (true) {
            if (activeCount != executorService.getActiveCount() || queueSize != executorService.getQueue().size()) {
                System.out.println(executorService.getActiveCount());
                System.out.println(executorService.getCorePoolSize());
                System.out.println(executorService.getQueue().size());
                System.out.println(executorService.getMaximumPoolSize());
                activeCount = executorService.getActiveCount();
                queueSize = executorService.getQueue().size();
                System.out.println("=====================================");
            }
        }
    }

    private static ExecutorService buildThreadPoolExecutor() {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), r -> {
            Thread t = new Thread(r);
            return t;
        }, new ThreadPoolExecutor.AbortPolicy());

        System.out.println("the threadpoolexecutor create done");
        return executorService;
    }

    private static class Task implements Runnable {

        @Override
        public void run() {
            sleep();
        }
    }

    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
