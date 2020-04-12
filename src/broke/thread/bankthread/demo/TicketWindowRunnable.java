package broke.thread.bankthread.demo;

/**
 * 版本二：分离出线程执行体
 */
public class TicketWindowRunnable implements Runnable {

    private int index = 1;

    private final static int MAX = 500;

    private final Object MONITOR = new Object();

    @Override
    public void run() {
        while(true) {
            synchronized (MONITOR) {
                if (index > MAX)
                    break;
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "正在服务，当前的号码是：" + index++);
            }
        }
    }
}
