package broke.threadpattern.future.demo;

public interface Future<T> {

    T get() throws InterruptedException;
}
