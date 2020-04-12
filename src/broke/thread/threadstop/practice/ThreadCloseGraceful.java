package broke.thread.threadstop.practice;

public class ThreadCloseGraceful {

    private Thread executeThread;

    private volatile boolean finished = false;

    public void execute(Runnable target) {
        executeThread = new Thread(() -> {
            Thread task = new Thread(target, "task");
            task.setDaemon(true);
            task.start();
            try {
                task.join();
                System.out.println(Thread.currentThread().getName() + " finished");
                finished = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "executeThread");
        executeThread.start();
    }

    public void shutdown(long millis) {
        long endTime = System.currentTimeMillis() + millis;
        while(!finished) {
            if(endTime - System.currentTimeMillis() <= 0) {
                executeThread.interrupt();
                break;
            }
        }
        finished = false;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ThreadCloseGraceful threadCloseGraceful = new ThreadCloseGraceful();
        threadCloseGraceful.execute(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadCloseGraceful.shutdown(10000);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
