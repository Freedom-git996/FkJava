package broke.threadpool.completableFuture;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static sun.net.InetAddressCachePolicy.get;

public class CompletableFutureExample1 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 没有使用completableFuture
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//
//        Future<?> future = executorService.submit(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//        while(!future.isDone()) {
//
//        }
//        System.out.println("DONE");
//
//        executorService.shutdown();

        // 没有使用completableFuture
//        CompletableFuture.runAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//         }).whenComplete((v, t) -> {
//            System.out.println("DONE");
//        });
//
//        Thread.currentThread().join();

        // 没有使用completableFuture
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        List<Callable<Integer>> tasks = IntStream.range(0, 10).boxed()
//                .map(i -> (Callable<Integer>) () -> get()).collect(toList());
//        executorService.invokeAll(tasks).stream().map(future -> {
//            try {
//                return future.get();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }).parallel().forEach(CompletableFutureExample1::display);

        // 没有使用completableFuture
//        IntStream.range(0, 10).boxed()
//                .forEach(i -> CompletableFuture.supplyAsync(CompletableFutureExample1::get)
//                .thenAccept(CompletableFutureExample1::display)
//                .whenComplete((v, t) -> System.out.println(i + " DONE")));

        // 链式调用
//        supplyAsync();

        // anyof
        System.out.println("......... " + anyOf().get());

        Thread.currentThread().join();
    }

    private static Future<?> anyOf() {
        return CompletableFuture.anyOf(
                CompletableFuture.supplyAsync(() -> {
                    try {
                        System.out.println("2===========start");
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println("2===========end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "Hello";
                }).whenComplete((v, t) -> System.out.println(v + "============over============"))
                ,CompletableFuture.supplyAsync(() -> {
                    try {
                        System.out.println("1===========start");
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println("1===========end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "World";
                }).whenComplete((v, t) -> System.out.println("==========over===========")));
    }

    private static void supplyAsync() {
        CompletableFuture.supplyAsync(Object::new)
                .thenAcceptAsync(obj -> {
                    try {
                        System.out.println("obj ========= start");
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println("obj ========= end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).runAfterBoth(CompletableFuture.supplyAsync(() -> "Hello")
                                    .thenAccept(s -> {
                                        try {
                                            System.out.println("String ========== start");
                                            TimeUnit.SECONDS.sleep(1);
                                            System.out.println("String ========== end");
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }), () -> System.out.println("======== Finished ========="));
    }

    private static void display(int data) {
        int value = ThreadLocalRandom.current().nextInt(20);
        try {
            System.out.println(Thread.currentThread().getName() + " display will be sleep " + value);
            TimeUnit.SECONDS.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " display execute done " + data);
    }

    private static int get() {
        int value = ThreadLocalRandom.current().nextInt(20);
        try {
            System.out.println(Thread.currentThread().getName() + " will be sleep " + value);
            TimeUnit.SECONDS.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " execute done " + value);
        return value;
    }
}
