package broke.thread.deadlock.practice;

public class OtherService {

    private DeadLock deadLock;

    public OtherService(DeadLock deadLock) {
        this.deadLock = deadLock;
    }

    public synchronized void method1() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " invoke otherservice's method1");
        deadLock.method2();
    }

    public synchronized void method2() {
        System.out.println(Thread.currentThread().getName() + " invoke otherservice's method2");
    }
}
