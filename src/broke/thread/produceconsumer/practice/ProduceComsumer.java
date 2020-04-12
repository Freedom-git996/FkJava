package broke.thread.produceconsumer.practice;

public class ProduceComsumer {

    private int count = 0;

    // 可以不使用volatile修饰
    private boolean flag = true;

    public synchronized void produce() throws InterruptedException {
        while(!flag) {
            wait();
        }
        count += 1;
        System.out.println(Thread.currentThread().getName() + " produce " + count);
        flag = false;
        notifyAll();
    }

    public synchronized void comsumer() throws InterruptedException {
        while(flag) {
            wait();
        }
        System.out.println(Thread.currentThread().getName() + " comsumer " + count);
        flag = true;
        notifyAll();
    }

    public static void main(String[] args) {
        ProduceComsumer produceComsumer = new ProduceComsumer();

        new Thread(() -> {
            try {
                while(true)
                    produceComsumer.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "P1").start();

        new Thread(() -> {
            try {
                while(true)
                    produceComsumer.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "P2").start();

        new Thread(() -> {
            try {
                while(true)
                    produceComsumer.comsumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "C1").start();
    }
}
