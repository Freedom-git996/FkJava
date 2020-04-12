package broke.threadpattern.readwritesplit.demo;

public class ReadWritLockClient {

    public static void main(String[] args) {
        final ShareData shareData = new ShareData(10);
        new ReaderWorker(shareData).start();
//        new WriterWorker(shareData, "qwertyuiopasdfg").start();
        new WriterWorker(shareData, "QWERTYUIOPASDFG").start();
    }
}
