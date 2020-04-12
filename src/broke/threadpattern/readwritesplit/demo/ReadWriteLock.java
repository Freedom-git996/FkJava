package broke.threadpattern.readwritesplit.demo;

public class ReadWriteLock {

    private int readingReader = 0;

    private int waitingReader = 0;

    private int writingWriter = 0;

    private int waitingWriter = 0;

    private boolean preferWrite = true;

    public ReadWriteLock() {
        this(true);
    }

    public ReadWriteLock(boolean preferWrite) {
        this.preferWrite = preferWrite;
    }

    public synchronized void readLock() {
        this.waitingReader++;
        try {
            while ((preferWrite && waitingWriter > 0) || writingWriter > 0) {
                this.wait();
            }
            this.readingReader++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.waitingReader--;
        }
    }

    public synchronized void readUnLock() {
        this.readingReader--;
        notifyAll();
    }

    public synchronized void writeLock() {
        this.waitingWriter++;
        try {
            while(readingReader > 0 || writingWriter > 0) {
                this.wait();
            }
            this.writingWriter++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.waitingWriter--;
        }
    }

    public synchronized void writeUnLock() {
        this.writingWriter--;
        notifyAll();
    }
}
