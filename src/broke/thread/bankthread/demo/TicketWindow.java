package broke.thread.bankthread.demo;

/**
 * 版本一
 */
public class TicketWindow extends Thread {

    private final int MAX = 500;

    private final String name;

    // 曾尝试仅仅通过在index上加volatile修饰达到线程间同步的目的，但是失败了，
    // 这也验证了volatile不能保证操作的原子性
    private static int index = 1;

    public TicketWindow(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            if(index > MAX) {
                break;
            }
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("柜台：" + name + "正在服务，当前的号码是：" + index ++);
        }
    }
}
