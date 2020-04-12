package broke.thread.lockcustom.demo;

import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

public class LockTest {

    public static void main(String[] args) {
        final BooleanLock booleanLock = new BooleanLock();
        Stream.of("T1", "T2", "T3", "T4")
                .forEach(name -> new Thread(() -> {
                    try {
                        booleanLock.lock(16_000L);
                        Optional.of(Thread.currentThread().getName() + " have the lock monitor")
                                .ifPresent(System.out::println);
                        work();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        Optional.of(Thread.currentThread().getName() + " get lock time out")
                                .ifPresent(System.out::println);
                    } finally {
                        booleanLock.unlock();
                    }
                }, name).start());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        booleanLock.unlock();
    }

    private static void work() throws InterruptedException {
        Optional.of(Thread.currentThread().getName() + " is working...")
                .ifPresent(System.out::println);
        Thread.sleep(4_000);
    }
}
