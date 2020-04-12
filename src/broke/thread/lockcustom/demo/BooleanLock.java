package broke.thread.lockcustom.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public class BooleanLock implements Lock {

    private boolean initValue;

    private Collection<Thread> blockedThreadCollection = new ArrayList<>();

    private Thread currentThread;

    public BooleanLock() {
        this.initValue = false;
    }

    @Override
    public synchronized void lock() throws InterruptedException {
        while(initValue) {
            blockedThreadCollection.add(Thread.currentThread());
            this.wait();
        }
        blockedThreadCollection.remove(Thread.currentThread());
        this.initValue = true;
        this.currentThread = Thread.currentThread();
    }

    @Override
    public synchronized void lock(long millis) throws InterruptedException, TimeoutException {
        if(millis <= 0) {
            lock();
        }
        long hasRemaining = millis;
        long endTime = System.currentTimeMillis() + millis;
        while(initValue) {
            if(hasRemaining <= 0) {
                throw new TimeoutException("time out");
            }
            blockedThreadCollection.add(Thread.currentThread());
            this.wait();
            hasRemaining = endTime - System.currentTimeMillis();
        }
        this.initValue = true;
        this.currentThread = Thread.currentThread();
    }

    @Override
    public synchronized void unlock() {
        if(Thread.currentThread() == currentThread) {
            this.initValue = false;
            Optional.of(Thread.currentThread().getName() + " release the lock monitor.")
                    .ifPresent(System.out::println);
            this.notifyAll();
        }
    }

    @Override
    public Collection<Thread> getBlockedThread() {
        return Collections.unmodifiableCollection(blockedThreadCollection);
    }

    @Override
    public int getBlockedSize() {
        return blockedThreadCollection.size();
    }
}
