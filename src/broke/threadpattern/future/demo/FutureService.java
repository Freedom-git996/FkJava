package broke.threadpattern.future.demo;

import java.util.function.Consumer;

public class FutureService {

    public <T> Future<T> submit(final FutureTask<T> task) {
        AsynFuture<T> asynFuture = new AsynFuture<>();
        new Thread(() -> {
            T result = null;
            try {
                result = task.call();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            asynFuture.done(result);
        }).start();
        return asynFuture;
    }

    // 有回调的方式
    public <T> Future<T> submit(final FutureTask<T> task, Consumer<T> consumer) {
        AsynFuture<T> asynFuture = new AsynFuture<>();
        new Thread(() -> {
            T result = null;
            try {
                result = task.call();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            asynFuture.done(result);
            consumer.accept(result);
        }).start();
        return asynFuture;
    }
}
