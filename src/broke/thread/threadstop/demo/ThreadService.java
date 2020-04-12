package broke.thread.threadstop.demo;

public class ThreadService {

    private Thread executeThread;

    // 没加volatile修饰，可能导致并发错误
    private boolean finished = false;

    public void execute(Runnable task) {
        executeThread = new Thread(() -> {
            Thread runner = new Thread(task);
            runner.setDaemon(true);

            runner.start();

            try {
                runner.join();
                finished = true;
            } catch (InterruptedException e) {

            }
        });
        executeThread.start();
    }

    public void shutdown(long millis) {
        long currentTime = System.currentTimeMillis();
        while(!finished) {
            if((System.currentTimeMillis() - currentTime) > millis) {
                System.out.println("任务超时，需要结束它");
                executeThread.interrupt();
                break;
            }

            try {
                executeThread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("执行线程被打断");
                break;
            }
        }

        finished = false;
    }
}
