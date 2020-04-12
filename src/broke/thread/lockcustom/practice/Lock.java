package broke.thread.lockcustom.practice;

import java.util.Collection;

public interface Lock {

    Collection<Thread> getBlockedThreads();

    void lock() throws InterruptedException;

    void lock(long millis) throws InterruptedException, TimeOutException;

    void unlock();

    int getBlockedSize();

    class TimeOutException extends Exception{
        public TimeOutException(String message){
            super(message);
        }
    }
}
