package broke.thread.deadlock.practice;

public class DeadLock {

    private OtherService otherService;

    public synchronized void method1() {
        System.out.println(Thread.currentThread().getName() + " invoke deadlock's method1");
        otherService.method2();
    }

    public synchronized void method2() {
        System.out.println(Thread.currentThread().getName() + " invoke deadlock's method2");
    }

    public void setOtherService(OtherService otherService) {
        this.otherService = otherService;
    }

    public static void main(String[] args) {
        DeadLock deadLock = new DeadLock();
        OtherService otherService = new OtherService(deadLock);
        deadLock.setOtherService(otherService);

        new Thread(() -> {
            while(true) {
                deadLock.method1();
            }}, "deadlock").start();

        new Thread(() -> {
            try {
                otherService.method1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "otherservice").start();
    }
}
