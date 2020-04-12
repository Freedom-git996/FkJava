package broke.threadpattern.readwritesplit.demo;

public class ReaderWorker extends Thread {

    private final ShareData data;

    public ReaderWorker(ShareData data) {
        this.data = data;
    }

    @Override
    public void run() {
        while(true) {
            char[] readBuf = data.read();
            System.out.println(Thread.currentThread().getName() + " reads " + String.valueOf(readBuf));
        }
    }
}
