package broke.thread.produceconsumer.demo;

/**
 * 版本1：没有多线程之间的通信
 */
public class ProduceComsumer1 {

    private int i = 1;

    private final Object LOCK = new Object();

    private void produce() {
        synchronized(LOCK) {
            System.out.println("P --> " + (i++));
        }
    }

    private void consume() {
        synchronized(LOCK) {
            System.out.println("C --> " + i);
        }
    }

    public static void main(String[] args) {
        ProduceComsumer1 pc = new ProduceComsumer1();

        new Thread("P") {
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
