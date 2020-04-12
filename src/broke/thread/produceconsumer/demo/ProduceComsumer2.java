package broke.thread.produceconsumer.demo;

/**
 * 版本2：notify()唤醒的线程具体是哪个是未知的 导致多生产者多消费者会假死
 */
public class ProduceComsumer2 {

    private int i = 0;

    private final Object LOCK = new Object();

    private volatile boolean isProduce = false;

    private void produce() {
        synchronized(LOCK) {
            System.out.println("wait to running");
            if(isProduce) {
                try {
                    System.out.println(Thread.currentThread().getName() + " wait");
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                i ++;
                System.out.println(Thread.currentThread().getName() + " P --> " + i);
                LOCK.notify();
                isProduce = true;
            }
        }
    }

    private void consume() {
        synchronized(LOCK) {
            System.out.println("wait to running");
            if(isProduce) {
                System.out.println(Thread.currentThread().getName() + " C --> " + i);
                LOCK.notify();
                isProduce = false;
            }else {
                try {
                    System.out.println(Thread.currentThread().getName() + " wait");
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ProduceComsumer2 pc = new ProduceComsumer2();

        new Thread("P") {
            @Override
            public void run() {
                while(true)
                    pc.produce();
            }
        }.start();

//        new Thread("P2") {
//            @Override
//            public void run() {
//                while(true)
//                    pc.produce();
//            }
//        }.start();

        new Thread("C") {
            @Override
            public void run() {
                while(true)
                    pc.consume();
            }
        }.start();

//        new Thread("C2") {
//            @Override
//            public void run() {
//                while(true)
//                    pc.consume();
//            }
//        }.start();
    }
}
