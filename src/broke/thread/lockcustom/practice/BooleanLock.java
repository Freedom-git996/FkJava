package broke.thread.lockcustom.practice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BooleanLock implements Lock {

    private boolean flag;

    private Thread currentThread;

    private List<Thread> blockedThreads = new ArrayList<>();

    public BooleanLock() {
        this.flag = true;
    }

    @Override
    public Collection<Thread> getBlockedThreads() {
        return blockedThreads;
    }

    @Override
    public synchronized void lock() throws InterruptedException {
        while(!flag) {
            if(!blockedThreads.contains(Thread.currentThread())) {
                blockedThreads.add(Thread.currentThread());
            }
            wait();
        }
        this.blockedThreads.remove(Thread.currentThread());
        this.flag = false;
        currentThread = Thread.currentThread();
    }

    @Override
    public synchronized void lock(long millis) throws InterruptedException, TimeOutException {
        if(millis < 0) {
            lock();
        }
        long endTime = System.currentTimeMillis() + millis;
        while(!flag) {
            if(endTime - System.currentTimeMillis() <= 0) {
                blockedThreads.remove(Thread.currentThread());
                throw new TimeOutException("time out");
            }
            if(!blockedThreads.contains(Thread.currentThread())) {
                blockedThreads.add(Thread.currentThread());
            }
            wait();
        }
        this.blockedThreads.remove(Thread.currentThread());
        this.flag = false;
        currentThread = Thread.currentThread();
    }

    @Override
    public synchronized void unlock() {
        if(Thread.currentThread() == currentThread) {
            this.flag = true;
            this.notifyAll();
        }else {
            System.out.println("cannot release the lock");
        }
    }

    @Override
    public int getBlockedSize() {
        return blockedThreads.size();
    }

    public static void main(String[] args) {

    }
}
