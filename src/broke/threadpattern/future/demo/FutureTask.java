package broke.threadpattern.future.demo;

@FunctionalInterface
public interface FutureTask<T> {

    T call() throws InterruptedException;
}
