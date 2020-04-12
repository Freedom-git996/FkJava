package broke.thread;

/**
 * 因为线程有创建和上下文切换的开销，所以当并发操作达到一定并发数时，并发反倒没有串行快
 */
public class ConcurrencyTest {

    private static final long count = 100001;

    private static final Object LOCK = new Object();

    public static void main (String[] args) throws InterruptedException {
        concurrency();
        serial();

//        T t = new T();
//        t.start();
//        Thread.sleep(3_000);
//        synchronized(LOCK) {
//            LOCK.notifyAll();
//        }
//        System.out.println("=====");
    }

    private static class T extends Thread {

        @Override
        public void run() {
            synchronized (LOCK) {
                while (true) {
                    System.out.println("the program running.");
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        System.out.println("caught interruptException");
                        break;
                    }
                }
            }
            System.out.println("break the loop");
        }
    }

    private static void concurrency () throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(() -> {
            int a = 0;
            for (long i = 0; i < count; i ++) {
                a += 5;
            }
        });
        thread.start();
        int b = 0;
        for (long i = 0; i < count; i ++) {
            b --;
        }
        long time = System.currentTimeMillis() - start;
        thread.join();
        System.out.println("concurrency : " + time + "ms, b = " + b);
    }

    private static void serial () {
        long start = System.currentTimeMillis();
        int a = 0;
        for (long i = 0; i < count; i ++) {
            a += 5;
        }
        int b = 0;
        for(long i = 0; i < count; i ++) {
            b --;
        }

        long time = System.currentTimeMillis() - start;
        System.out.println("concurrency : " + time + "ms, b = " + b);
    }
}
