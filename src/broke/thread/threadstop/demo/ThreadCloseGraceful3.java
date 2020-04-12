package broke.thread.threadstop.demo;

public class ThreadCloseGraceful3 {

    private static class WorkThread extends Thread {

        private volatile static boolean flag = true;

        @Override
        public void run() {
            while(flag) {
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    System.out.println("caught exception right now");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        WorkThread workThread = new WorkThread();
        workThread.start();
        Thread.sleep(3_000);
        WorkThread.flag = false;
        workThread.interrupt();
        System.out.println("main end");
    }
}
