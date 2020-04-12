package broke.thread.threadstop.demo;

/**
 * 使用 后台线程 结束线程
 */
public class ThreadCloseGraceful4 {

    public static void main(String[] args) {
        ThreadService service = new ThreadService();
        long start = System.currentTimeMillis();
        service.execute(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.shutdown(10000);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
