package broke.thread.threadstop.demo;

/**
 * 遇到耗时或者阻塞的操作，线程没办法读到flag或者interrupt信号
 *
 * 使用保护线程
 */
public class ThreadCloseGraceful2 {


    private static class Worker extends Thread {

        private volatile boolean start = true;

        @Override
        public void run() {
            while (start) {
                if(Thread.interrupted()) {
                    break;
                }
            }

            // 操作
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        worker.start();
        Thread.sleep(3000);
        worker.interrupt();
    }
}
