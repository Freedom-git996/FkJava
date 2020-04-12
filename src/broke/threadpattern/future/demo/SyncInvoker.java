package broke.threadpattern.future.demo;

import java.util.concurrent.ThreadPoolExecutor;

public class SyncInvoker {

    public static void main(String[] args) throws InterruptedException {
        FutureService futureService = new FutureService();
        Future<String> future = futureService.submit(() -> {
            Thread.sleep(10000);
            return "FINISH";
        }, System.out::println);

        System.out.println("===========");
        System.out.println(" do other things.");
        Thread.sleep(1000);
        System.out.println("===========");
//        System.out.println(future.get());

    }
}
