package broke.thread.lockcustom.demo;

import java.util.Collection;
import java.util.concurrent.TimeoutException;

public interface Lock {

    class TimeOutException extends Exception {
        public TimeOutException(String message) {
            super(message);
        }
    }

    void lock() throws InterruptedException;

    void lock(long millis) throws InterruptedException, TimeoutException;

    void unlock();

    Collection<Thread> getBlockedThread();

    int getBlockedSize();
}
