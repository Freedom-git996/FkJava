package broke.thread.produceconsumer.demo;

/**
 * 版本3 notifyAll
 */
public class ProduceComsumer3 {

    private int i = 0;

    private final Object LOCK = new Object();

    private volatile boolean isProduce = false;

    private void produce() {
        synchronized(LOCK) {
            // 只能用while，不能用if的原因
            // 有点自旋的味道在里面
            while(isProduce) {
                try {
                    System.out.println(Thread.currentThread().getName() + " wait");
                    LOCK.wait();
                    System.out.println(Thread.currentThread().getName() + " wait 结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i ++;
            System.out.println(Thread.currentThread().getName() + " P --> " + i);
            LOCK.notifyAll();
            isProduce = true;
        }
    }

    private void consume() {
        synchronized(LOCK) {
            while(!isProduce) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread().getName() + " C --> " + i);
            LOCK.notifyAll();
            isProduce = false;
        }
    }

    public static void main(String[] args) {
        ProduceComsumer3 pc = new ProduceComsumer3();

        new Thread("P") {
            @Override
            public void run() {
                while(true)
                    pc.produce();
            }
        }.start();

        new Thread("P2") {
            @Override
            public void run() {
                while(true)
                    pc.produce();
            }
        }.start();

        new Thread("C") {
            @Override
            public void run() {
                while(true)
                    pc.consume();
            }
        }.start();

    }
}
