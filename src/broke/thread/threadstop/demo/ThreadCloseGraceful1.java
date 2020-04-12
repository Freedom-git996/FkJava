package broke.thread.threadstop.demo;

/**
 * 使用标记方法结束线程
 */
public class ThreadCloseGraceful1 {


    private static class Worker extends Thread {

        private volatile boolean start = true;

        @Override
        public void run() {
            while (start) {
                // 操作
            }

            // 操作
        }

        public void shutdown() {
            this.start = false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        worker.start();

        Thread.sleep(3000);

        worker.shutdown();
    }
}
