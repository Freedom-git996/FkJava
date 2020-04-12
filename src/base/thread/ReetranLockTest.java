package base.thread;

import java.util.concurrent.locks.ReentrantLock;

public class ReetranLockTest implements Runnable {

    private static ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        for (int i = 0; i < 100; i ++) {
            lock.lock();
            try{
                System.out.println(Thread.currentThread().getName() + "获得锁");
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReetranLockTest reetranLockTest = new ReetranLockTest();
        Thread t1 = new Thread(reetranLockTest);
        Thread t2 = new Thread(reetranLockTest);
        t1.start();
        t2.start();
    }
}
