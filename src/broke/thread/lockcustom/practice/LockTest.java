package broke.thread.lockcustom.practice;

import java.util.Optional;
import java.util.stream.Stream;

public class LockTest {

    public static void main(String[] args) throws InterruptedException {
        final BooleanLock booleanLock = new BooleanLock();
        Stream.of("T1", "T2", "T3", "T4", "T5")
                .forEach(name -> new Thread(() -> {
                    try {
                        booleanLock.lock(8_000L);
                        Optional.of(Thread.currentThread().getName() + " have the lock monitor")
                                .ifPresent(System.out::println);
                        work();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Lock.TimeOutException e) {
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

        Thread.sleep(30_000L);
        System.out.println(booleanLock.getBlockedSize());
    }

    private static void work() throws InterruptedException {
        Optional.of(Thread.currentThread().getName() + " is working...")
                .ifPresent(System.out::println);
        Thread.sleep(4_000);
    }
}
