package broke.thread.deadlock.demo;

public class DeadLock {

    private final Object lock = new Object();

    private OtherService otherService;

    private DeadLock(OtherService otherService) {
        this.otherService = otherService;
    }

    public void m1() {
        synchronized (lock) {
            System.out.println("m1 ==========");
            otherService.s2();
        }
    }

    public void m2() {
        synchronized (lock) {
            System.out.println("m1 ==========");
        }
    }

    public static void main(String[] args) {
        OtherService otherService = new OtherService();
        DeadLock deadLock = new DeadLock(otherService);
        otherService.setDeadLock(deadLock);

        new Thread(() -> {
            while(true) {
                deadLock.m1();
            }
        }).start();

        new Thread(() -> {
            while(true) {
                otherService.s1();
            }
        }).start();
    }
}
